package foodkeep.util 

import scala.scalajs.js
import foodkeep.helper._
import foodkeep.util.util._

object MonthlyMealUtil {
    import foodkeep.util.DailyMealUtil._
    
    implicit class MonthlyMealUtil[MonthlyMeal](m: js.Object) {
        def pushNewMealMonthly(meal: Meal) = {
            val mC = MonthlyMeal(m)
            val updatedRecord = mC.record.dropRight(1) :+ mC.record.last.pushNewMealDaily(meal)
            val updatedExpenses = mC.totalExpenses + meal.expense
            val updatedCalories = mC.totalCalories + meal.caloriesContent
            
            MonthlyMeal(getDateMY, updatedRecord, updatedExpenses, updatedCalories)
        }
    
        def pushNewDailyMealMonthly(caloriesTarget: Int) = {
            val mC = MonthlyMeal(m)
            val updatedRecord = mC.record :+ DailyMeal(getDateDMY, js.Array[Meal](), 0, 0, caloriesTarget)
            
            MonthlyMeal(getDateMY, updatedRecord, mC.totalExpenses, mC.totalCalories)
        }

        def getDailyMeal(date: String): Option[DailyMeal] = {
            val mC = MonthlyMeal(m)
            mC.record.filter(daily => daily.date == date).lastOption match {
                case Some(d) => Some(d)
                case _ => None
            }     
        }
    }
}