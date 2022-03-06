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

import scala.util.{Try,Success,Failure}

object AddMealView {
    private val queryFieldExpense = document.querySelector("#expense").asInstanceOf[html.Input]
    private val searchResultsContainer = document.querySelector(".hero-search-results")
    

    def addHandlerAddMeal(handler: (Int, Double) => Boolean): Unit = {
        searchResultsContainer.addEventListener("click", (e: dom.Event) => {
            val eventTarget = e.target.asInstanceOf[html.Element]
            val expense = queryFieldExpense.value 

            if (eventTarget.classList.contains("button-addmeal") && validateQueryFieldExpense(expense)) {
                eventTarget.dataset.get("idx") match {
                    case Some(i) => handler(i.toInt, expense.toDouble) match {
                        case false => dom.window.alert("Cannot find valid profile!")
                        case _ => dom.window.alert(s"Meal added at ${renderTime(getDateDMYT)}!")
                    }
                    case _ =>   
                }
            }
        })    
    }

    private def validateQueryFieldExpense(expense: String): Boolean = 
        Try(expense.toDouble) match {
            case Success(v) => true
            case _ => {
                dom.window.alert("Invalid value detected!")
                false
            }
        }

    private def renderTime(date: String): String = s"${date.drop(8).dropRight(2)}${date.drop(10)} h"
     
}