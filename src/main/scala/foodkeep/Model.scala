package foodkeep.model

import org.scalajs.dom
import org.scalajs.dom.ext
import scala.scalajs.js

// for making fetch request
// import concurrent.ExecutionContext.Implicits.global
import js.Thenable.Implicits._
import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits._
import scala.concurrent.Future

import foodkeep.config.Config._

import foodkeep.helper._
import foodkeep.util.util._
import foodkeep.util.SearchResultUtil._
import foodkeep.util.ProfileUtil._
import foodkeep.util.DailyMealUtil._
import foodkeep.util.MonthlyMealUtil._
import foodkeep.util.StateUtil._

object Model {
    var state = State(js.Array[Profile](), js.Array[MonthlyMeal](), js.Array[SearchResult]())

    init()

    private def setState(newState: State): Unit = {
        state = newState
        dom.window.localStorage.setItem("state", js.JSON.stringify(state))
    }

    private def getStateFromLocalStorage: Option[State] = js.JSON.parse(dom.window.localStorage.getItem("state")).asInstanceOf[State] match {
        case n if (n == null) => None
        case s => Some(s)
    }
    
    private def clearStateFromLocalStorage: Unit = dom.window.localStorage.clear()

    private def init(): Unit = {
        getStateFromLocalStorage match {
            case Some(s) => setState(s)
            case _ => 
        }
    }

    def getProfileHistoryFromState: Option[js.Array[Profile]] = state.getProfileHistory

    def getCurrentProfileFromState: Option[Profile] = state.getCurrentProfile

    def pushNewProfileToState(profile: Profile): Unit = setState(state.pushNewProfile(profile))

    def getSearchResultsFromState: Option[js.Array[SearchResult]] = state.getSearchResults

    def pushNewSearchResultsToState(query: String): Future[Option[js.Array[SearchResult]]] = {
        val url = s"https://api.nal.usda.gov/fdc/v1/foods/search?query=$query&pageSize=$MAX_NBR_QUERY&api_key=$API_KEY"

        dom.fetch(url).flatMap(
            response => response.text()
        ).map(
            responseText => {
                val foods = js.JSON.parse(responseText).foods.asInstanceOf[js.Array[js.Dynamic]]
                val searchResults = generateSearchResults(foods)
                setState(state.pushNewSearchResults(searchResults))
                state.getSearchResults   
            })      
    }

    private def generateSearchResults(foods: js.Array[js.Dynamic]): js.Array[SearchResult] = {
        foods.map(food => {
            val foodId = food.fdcId.asInstanceOf[Int]
            val foodName = food.description.asInstanceOf[String]
            val nutrients = food.foodNutrients.asInstanceOf[js.Array[js.Dynamic]]

            val caloriesContent = findNutrientValue(1008, nutrients)
            val carbContent = findNutrientValue(1005, nutrients)
            val proteinContent = findNutrientValue(1003, nutrients)
            val fatContent = findNutrientValue(1004, nutrients)

            SearchResult(foodId, foodName,caloriesContent, carbContent, proteinContent, fatContent)
        })
    }

    private def findNutrientValue(nutrientId: Int, nutrients: js.Array[js.Dynamic]): Int = {
        nutrients.filter(nutrient => nutrient.nutrientId.asInstanceOf[Int] == nutrientId).lastOption match {
            case Some(n) => n.value.asInstanceOf[Double].toInt
            case None => 0
        }
    }

    def pushNewMealToState(index: Int, currentCaloriesTarget: Int, expense: Double): Unit = {
        state.checkDailyMonthlyPresent(getDateDMY) match {
            case (false, false) => {
                setState(state.pushNewMonthlyMealState)
                setState(state.pushNewDailyMealState(currentCaloriesTarget))
            }
            case (false, true) => {
                setState(state.pushNewDailyMealState(currentCaloriesTarget))
            }
            case _ => 
        }

        val selectedMeal: Meal = state.getSearchResults.get(index).parseSearchResultAsMeal(expense)

        setState(state.pushNewMealState(selectedMeal))
    }

    def getCurrentCaloriesTargetFromState: Option[Int] = state.getCurrentCaloriesTarget
}