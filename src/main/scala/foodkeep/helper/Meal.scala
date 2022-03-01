package foodkeep.helper

import scala.scalajs.js
import foodkeep.helper._

trait Meal extends js.Object {
	val dateTime: String
	val foodId: Int
	val foodName: String
	val expense: Double
	val caloriesContent: Int
}

object Meal {
    def apply(dateTime: String, foodId: Int, foodName: String, expense: Double, caloriesContent: Int) = 
            js.Dynamic.literal(dateTime = dateTime, 
                            foodId = foodId,
                            foodName = foodName,
                            expense = expense,
                            caloriesContent = caloriesContent).asInstanceOf[Meal]         
}