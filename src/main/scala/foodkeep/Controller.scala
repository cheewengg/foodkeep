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

object Controller {
    def main(args: Array[String]): Unit = {
        val meal1 = Meal(getDateDMYT, 1, "Chicken", 1, 1000)
        val meal2 = Meal(getDateDMYT, 2, "Rice", 2, 500)
        val meal3 = Meal(getDateDMYT, 3, "Pepsi", 3, 300)

        val daily1 = DailyMeal(getDateDMY, js.Array[Meal](), 0, 0, 1000)
        dom.window.localStorage.setItem("daily1", js.JSON.stringify(daily1))

        val daily1FromStorage = js.JSON.parse(dom.window.localStorage.getItem("daily1")).asInstanceOf[DailyMeal]
        // dom.console.log(daily1FromStorage.pushNewMealDaily(meal1).pushNewMealDaily(meal1).pushNewMealDaily(meal1))

        val monthly1 = MonthlyMeal(getDateMY, js.Array[DailyMeal](), 0, 0)
        // dom.console.log(monthly1.pushNewDailyMealMonthly(1000).pushNewMealMonthly(meal1).pushNewMealMonthly(meal2).pushNewDailyMealMonthly(1000).pushNewMealMonthly(meal3))

        // dom.console.log(monthly1.pushNewDailyMealMonthly(1000).getDailyMeal("20220302"))

        val profile1 = Profile("", "", "john", getDateDMY, "M", 65.0, 2.00, "moderate", 0)
        val profile2 = Profile(getDateDMY, "99999999", "john", getDateDMY, "M", 65.0, 185, "moderate", 0)
        
        dom.console.log(profile1.updateCaloriesTarget.updateEndDate("20230101").getCaloriesTarget)
        
        val state1 = State(js.Array[Profile](), js.Array[MonthlyMeal](), js.Array[SearchResult]())

        val state2 = state1.pushNewProfile(profile1).pushNewMonthlyMealState.pushNewDailyMealState(1000).pushNewMealState(meal1).pushNewProfile(profile1)

        dom.window.localStorage.setItem("state", js.JSON.stringify(state2))

        val stateFromStorage = js.JSON.parse(dom.window.localStorage.getItem("state")).asInstanceOf[State]

        dom.console.log(stateFromStorage)
        dom.console.log(stateFromStorage.getCurrentProfile)
        dom.console.log(state1.getCurrentProfile)
    }
}