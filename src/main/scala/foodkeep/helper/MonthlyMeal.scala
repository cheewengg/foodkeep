package foodkeep.helper

import scala.scalajs.js
import foodkeep.helper._

trait MonthlyMeal extends js.Object {
	val monthYear: String
	val record: js.Array[DailyMeal]
	val totalExpenses: Double
	val totalCalories: Int   
}

object MonthlyMeal {
    def apply(monthYear: String, record: js.Array[DailyMeal], totalExpenses: Double, totalCalories: Int): MonthlyMeal = 
        js.Dynamic.literal(monthYear = monthYear, record = record, totalExpenses = totalExpenses, totalCalories = totalCalories).asInstanceOf[MonthlyMeal]
    
    def apply(m: js.Dynamic): MonthlyMeal= {
        val monthYear = m.monthYear.asInstanceOf[String]
        val record = m.record.asInstanceOf[js.Array[DailyMeal]]
        val totalExpenses = m.totalExpenses.asInstanceOf[Double]
        val totalCalories = m.totalCalories.asInstanceOf[Int]

        MonthlyMeal(monthYear, record, totalExpenses, totalCalories)
    }
}