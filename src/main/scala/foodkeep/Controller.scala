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

        // add event handlers to DOM
        ProfileUpdateView.addHandlerToggleUpdateProfile
        ProfileUpdateView.addHandlerSubmitUpdateProfile(controlSubmitUpdateProfile)

        SearchMealView.addHandlerStartSearchTimer(controlSearchMeal)
        SearchMealView.addHandlerClearSearchTimer
    }

    def controlSubmitUpdateProfile(profile: Profile): Unit = {
        Model.pushNewProfileToState(profile)
        ProfileView.render(Model.getCurrentProfileFromState)
        ProfileUpdateView.render(Model.getCurrentProfileFromState)
    }

    def controlSearchMeal(query: String): Unit = {
        Model.pushNewSearchResultsToState(query).foreach{
            results => {
            dom.console.log(results)
            SearchMealResultsView.render(results)}
        }
    }

}