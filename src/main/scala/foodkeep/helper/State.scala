package foodkeep.helper

import scala.scalajs.js
import foodkeep.helper._

trait State extends js.Object {
	val profileHistory: js.Array[Profile]		
	val mealHistory: js.Array[MonthlyMeal]		
	val searchResults: js.Array[SearchResult] 
}

object State {
    def apply(profileHistory: js.Array[Profile]	, mealHistory: js.Array[MonthlyMeal], searchResults: js.Array[SearchResult]) = 
        js.Dynamic.literal(profileHistory = profileHistory, mealHistory = mealHistory, searchResults = searchResults).asInstanceOf[State]

    def apply(s: js.Dynamic): State = {
        val profileHistory = s.profileHistory.asInstanceOf[js.Array[Profile]]
        val mealHistory = s.mealHistory.asInstanceOf[js.Array[MonthlyMeal]]
        val searchResults = s.searchResults.asInstanceOf[js.Array[SearchResult] ]

        State(profileHistory, mealHistory, searchResults)
    }
}