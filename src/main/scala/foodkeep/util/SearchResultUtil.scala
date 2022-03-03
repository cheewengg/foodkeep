package foodkeep.util 

import scala.scalajs.js
import foodkeep.helper._
import foodkeep.util.util._

package object SearchResultUtil {
    implicit class SearchResultUtil[SearchResult](s: js.Object) {
        def parseSearchResultAsMeal(spending: Double): Meal = {
            val sC = SearchResult(s)
	        
            val dateTime: String = getDateDMYT
            val foodId: Int = sC.foodId
	        val foodName: String = sC.foodName
            val expense: Double = spending
	        val caloriesContent: Int = sC.caloriesContent

            Meal(dateTime, foodId, foodName, expense, caloriesContent)
        }
    }
}