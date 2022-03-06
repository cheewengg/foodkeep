// Scala.js
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html
import org.scalajs.dom.ext
import scala.scalajs.js
import js.annotation._

// for making fetch request
// import concurrent.ExecutionContext.Implicits.global
import js.Thenable.Implicits._
import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits._

import foodkeep.helper._
import foodkeep.util.util._
import foodkeep.util.ProfileUtil._
import foodkeep.util.DailyMealUtil._
import foodkeep.util.MonthlyMealUtil._
import foodkeep.util.StateUtil._

import foodkeep.model._
import foodkeep.view._

object Controller {
    def main(args: Array[String]): Unit = {
        // render views
        ProfileView.render(Model.getCurrentProfileFromState)
        ProfileUpdateView.render(Model.getCurrentProfileFromState)

        SearchMealResultsView.render(Model.getSearchResultsFromState)
        
        SummaryView.render(Model.getMealDataFromState(getDateDMY))

        // add event handlers to DOM
        NavBarView.addHandlerNavBar
        ProfileUpdateView.addHandlerToggleUpdateProfile
        ProfileUpdateView.addHandlerSubmitUpdateProfile(controlSubmitUpdateProfile)

        SearchMealView.addHandlerStartSearchTimer(controlSearchMeal)
        SearchMealView.addHandlerClearSearchTimer
        
        AddMealView.addHandlerAddMeal(controlAddMeal)

        SummarySearchView.addHandlerSummarySearch(controlSummarySearch)
    }

    def controlSubmitUpdateProfile(profile: Profile): Boolean = {
        Model.pushNewProfileToState(profile)
        ProfileView.render(Model.getCurrentProfileFromState)
        ProfileUpdateView.render(Model.getCurrentProfileFromState)

        Model.getProfileHistoryFromState match {
            case Some(r) if r.length == 1 => true
            case _ => false
        }
    }

    def controlSearchMeal(query: String): Unit = {
        Model.pushNewSearchResultsToState(query).foreach{
            results => {
            SearchMealResultsView.render(results)}
        }
    }

    def controlAddMeal(index: Int, expense: Double): Boolean = {
        Model.getCurrentCaloriesTargetFromState match {
            case Some(calories) => {
                Model.pushNewMealToState(index, calories, expense)
                SummaryView.render(Model.getMealDataFromState(getDateDMY))
                true
            }
            case _ => false
        }
    }

    def controlSummarySearch(dateQuery: String): Boolean = {
        Model.getMealDataFromState(dateQuery) match {
            case None => {
                SummaryView.render(Model.getMealDataFromState(getDateDMY))
                false
            }
            case mealsOption => {
                SummaryView.render(mealsOption)
                true
            }

        }
    }

}