package foodkeep.helper

import scala.scalajs.js
import foodkeep.helper._

trait SearchResult extends js.Object {
	val foodId: Int
	val foodName: String
	val caloriesContent: Int
	val carbContent: Int
	val proteinContent: Int
	val fatContent: Int
}

object SearchResult {
    def apply(foodId: Int, foodName: String, caloriesContent: Int, carbContent: Int, proteinContent: Int, fatContent: Int) = 
            js.Dynamic.literal(foodId = foodId, foodName = foodName,
                            caloriesContent = caloriesContent,
                            carbContent = carbContent,
                            proteinContent = proteinContent,
                            fatContent = fatContent
                            ).asInstanceOf[SearchResult]         
}