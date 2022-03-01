package foodkeep.util 

import scala.scalajs.js
import foodkeep.helper._
import foodkeep.util.util._

object DailyMealUtil {

    implicit class DailyMealUtil[DailyMeal](d: js.Object) {
        def pushNewMealDaily(meal: Meal) = {
            val dC = DailyMeal(d)
            val updatedRecord = dC.record :+ meal
            val updatedExpenses = dC.totalExpenses + meal.expense
            val updatedCalories = dC.totalCalories + meal.caloriesContent
            
            DailyMeal(getDateDMY, updatedRecord, updatedExpenses, updatedCalories, dC.caloriesTarget)
        }
    }
}