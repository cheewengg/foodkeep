package foodkeep.helper

import scala.scalajs.js
import foodkeep.helper._

trait DailyMeal extends js.Object {
	val date: String 
	val record: js.Array[Meal]
	val totalExpenses: Double
	val totalCalories: Int
	val caloriesTarget: Int
}

object DailyMeal {
    def apply(date: String, record: js.Array[Meal], totalExpenses: Double, totalCalories: Int, caloriesTarget: Int): DailyMeal = 
            js.Dynamic.literal(date = date, record = record, totalExpenses = totalExpenses, totalCalories = totalCalories, caloriesTarget = caloriesTarget).asInstanceOf[DailyMeal]

    def apply(d: js.Dynamic): DailyMeal = {
        val dC = js.JSON.parse(js.JSON.stringify(d))
        val date: String = dC.date.asInstanceOf[String]
        val record = dC.record.asInstanceOf[js.Array[Meal]]
	    val totalExpenses = dC.totalExpenses.asInstanceOf[Double]
	    val totalCalories = dC.totalCalories.asInstanceOf[Int]
	    val caloriesTarget = dC.caloriesTarget.asInstanceOf[Int]
            
        DailyMeal(date, record, totalExpenses, totalCalories, caloriesTarget)
    }
}