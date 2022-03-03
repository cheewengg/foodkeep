package foodkeep.util 

import scala.scalajs.js
import foodkeep.helper._
import foodkeep.util.util._

package object StateUtil {
    import foodkeep.util.ProfileUtil._
    import foodkeep.util.DailyMealUtil._
    import foodkeep.util.MonthlyMealUtil._

    implicit class StateUtil[State](s: js.Object) {
        
        def pushNewProfile(profile: Profile) = {
            val sC = State(s)
            val updatedProfileHistory = 
                if (sC.profileHistory.length > 0) 
                    sC.profileHistory.dropRight(1) :+ sC.profileHistory.last.updateEndDate(getDateDMY) :+ profile.updateStartDate(getCustomDateDMY(1)).updateEndDate().updateCaloriesTarget 
                
                else sC.profileHistory :+ profile.updateStartDate().updateEndDate().updateCaloriesTarget
            
            State(updatedProfileHistory, sC.mealHistory, sC.searchResults)
        }

        def pushNewMealState(meal: Meal) = {
            val sC = State(s)
            val updatedMealHistory = sC.mealHistory.dropRight(1) :+ sC.mealHistory.last.pushNewMealMonthly(meal)

            State(sC.profileHistory, updatedMealHistory, sC.searchResults)
        }

        def pushNewDailyMealState(caloriesTarget: Int) = {
            val sC = State(s)
            val updatedMealHistory = sC.mealHistory.dropRight(1) :+ sC.mealHistory.last.pushNewDailyMealMonthly(caloriesTarget)

            State(sC.profileHistory, updatedMealHistory, sC.searchResults)
        }

        def pushNewMonthlyMealState = {
            val sC = State(s)
            val updatedMealHistory = sC.mealHistory :+ MonthlyMeal(getDateMY, js.Array[DailyMeal](), 0, 0)

            State(sC.profileHistory, updatedMealHistory, sC.searchResults)
        }

        def pushNewSearchResults(results: js.Array[SearchResult]) = {
            val sC = State(s)
            State(sC.profileHistory, sC.mealHistory, results)
        }   

        def getCurrentProfile: Option[Profile] = {
            val sC = State(s)
            sC.profileHistory.lastOption match {
                case Some(p) if (p.startDate.toInt <= getDateDMY.toInt) => Some(p)
                case Some(p) => State(sC.profileHistory.dropRight(1), sC.mealHistory, sC.searchResults).getCurrentProfile
                case _ => None
            }           
        }

        def getCurrentCaloriesTarget: Option[Int] = {
            getCurrentProfile match {
                case Some(p) => Some(p.getCaloriesTarget)
                case _ => None
            }
        }

        def getFilterQuery(date: String): Option[(DailyMeal, MonthlyMeal)] = {
            val sC = State(s)
            
            sC.mealHistory.filter(monthly => monthly.monthYear == date.dropRight(2)).lastOption match {
                case Some(m) => (Some(m), m.getDailyMeal(date)) match {
                    case (Some(m), Some(d)) => Some(d, m)
                    case _ => None
                }
                case _ => None
            }
        }

        def getSearchResults: Option[js.Array[SearchResult]] = State(s).searchResults match {
            case r if r.length > 0 => Some(r)
            case _ => None
        }

        def getProfileHistory: Option[js.Array[Profile]] = State(s).profileHistory match {
            case r if r.length > 0 => Some(r)
            case _ => None
        }

        def checkDailyMonthlyPresent(date: String): (Boolean, Boolean) = {
            val sC = State(s)

            sC.mealHistory.filter(monthly => monthly.monthYear == date.dropRight(2)).lastOption match {
                case Some(m) => (Some(m), m.getDailyMeal(date)) match {
                    case (Some(m), Some(d)) => (true, true)
                    case _ => (false, true)
                }
                case _ => (false, false)
            }
        }
        
    }
}