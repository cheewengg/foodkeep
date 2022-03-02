package foodkeep.view

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html
import org.scalajs.dom.ext
import scala.scalajs.js
import js.annotation._

import foodkeep.helper._
import foodkeep.util.util._
import foodkeep.util.ProfileUtil._
import foodkeep.util.DailyMealUtil._
import foodkeep.util.MonthlyMealUtil._
import foodkeep.util.StateUtil._

object SearchMealResultsView {
    private val searchResultsContainer = document.querySelector(".hero-search-results")
    
    def render(searchResults: Option[js.Array[SearchResult]]) = searchResults match {
        case Some(results) => {
            var index = -1
            searchResultsContainer.innerHTML = results.map(result => {
                index += 1
                generateMarkUpSearchResult(result, index)  
            }).join("")
        }
        case _ => 
    }

    private def generateMarkUpSearchResult(result: SearchResult, index: Int): String = {
	    val foodName = renderFoodName(result.foodName)
	    val calories = result.caloriesContent
	    val carb = result.carbContent
	    val protein = result.proteinContent
	    val fat = result.fatContent

        s"""<div class="search-result">
            <div class="search-result-content">
              <div class="result-title">${foodName}</div>
              <div class="result-details">${calories}kCal ${protein}P/${carb}C/${fat}F</div>
            </div>
            <button data-idx="${index}" class="button button-addmeal">Add meal</button>
          </div>"""
    }

    private def renderFoodName(name: String) = {
        if (name.length > 28) name.dropRight(name.length - 28) + "..."
        else name
    }
}