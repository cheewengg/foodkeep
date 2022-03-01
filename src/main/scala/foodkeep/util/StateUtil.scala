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
                if (sC.profileHistory.length > 0) sC.profileHistory.dropRight(1) :+ sC.profileHistory.last.updateEndDate(getDateDMY) :+ profile 
                else sC.profileHistory :+ profile
            
            State(updatedProfileHistory, sC.mealHistory, sC.searchResults)
        }

        def pushNewMealState(meal: Meal) = {
            val sC = State(s)
            val updatedMealHistory = sC.mealHistory.slice(0, sC.mealHistory.length-1) :+ sC.mealHistory(sC.mealHistory.length-1).pushNewMealMonthly(meal)

            State(sC.profileHistory, updatedMealHistory, sC.searchResults)
        }

        def pushNewDailyMealState(caloriesTarget: Int) = {
            val sC = State(s)
            val updatedMealHistory = sC.mealHistory.slice(0, sC.mealHistory.length-1) :+ sC.mealHistory(sC.mealHistory.length-1).pushNewDailyMealMonthly(caloriesTarget)

            State(sC.profileHistory, updatedMealHistory, sC.searchResults)
        }

        def pushNewMonthlyMealState = {
            val sC = State(s)
            val updatedMealHistory = sC.mealHistory :+ MonthlyMeal(getDateMY, js.Array[DailyMeal](), 0, 0)

            State(sC.profileHistory, updatedMealHistory, sC.searchResults)
        }

        def parseSearchResults(results: js.Array[SearchResult]) = {
            val sC = State(s)
            State(sC.profileHistory, sC.mealHistory, results)
        }

        def getCurrentCaloriesTarget = {
            val sC = State(s)
            // > 1 profile in profile history 
            // 1 profile in profile history 
            // no profile in profile history
            sC.profileHistory.lastOption match {
                case Some(p) if p.startDate.toInt > getDateDMY.toInt => sC.profileHistory(sC.profileHistory.length - 2).getCaloriesTarget
                case Some(p) if p.startDate.toInt <= getDateDMY.toInt => p.getCaloriesTarget
                case _ => js.undefined
            }
        }

        def getFilterQuery(date: String) = {
            val sC = State(s)

            sC.mealHistory.filter(monthly => monthly.monthYear == date.dropRight(2)).lastOption match {
                case Some(m) => js.Array(m, m.getDailyMeal(date))
                case _ => js.undefined
            }
        }

        def getSearchResults = State(s).searchResults
        
    }
}