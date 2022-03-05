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


object SummarySearchView{
  private val queryFieldDate = document.querySelector("#filter").asInstanceOf[html.Input]
  private val btnFilterDate = document.querySelector(".button-filter")

  def addHandlerSummarySearch(handler: String => Boolean): Unit = {
    btnFilterDate.addEventListener("click", (e: dom.Event) => {
      e.preventDefault()
      val dateQuery = formatDateState(queryFieldDate.value)
      if (validateQueryFieldDate(dateQuery)) 
        handler(dateQuery) match {
          case false => dom.window.alert("Unable to find data corresponding to date entered!")
          case _ => dom.window.alert(s"Data for ${renderDateDOM(dateQuery)} has been loaded")
        }
    })
  }

  private def validateQueryFieldDate(dateQuery: String): Boolean = {
    Try(dateQuery.toInt) match {
      case Success(v) if (dateQuery.length == 8) => true
      case _ => {
        dom.window.alert("Invalid value detected!")
        false
      }
    }
  }


}