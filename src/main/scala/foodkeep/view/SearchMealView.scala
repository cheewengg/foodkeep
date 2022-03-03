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

object SearchMealView {
    private var timerId: Int = 9999
    private var lastSearchQuery: String = ""
    private val queryFieldFood = document.querySelector("#food").asInstanceOf[html.Input]

    def addHandlerStartSearchTimer(handler: String => Unit): Unit = {
        queryFieldFood.addEventListener("keyup", (e: dom.Event) => {
            timerId = dom.window.setTimeout(() => {
                    if (queryFieldFood.value != lastSearchQuery && !queryFieldFood.value.isEmpty) {
                        lastSearchQuery = queryFieldFood.value
                        handler(queryFieldFood.value)
                    }     
            }, 1000)
        })
    }

    def addHandlerClearSearchTimer: Unit = {
        queryFieldFood.addEventListener("keydown", (e: dom.Event) => {
            dom.window.clearTimeout(timerId)
        })
    }

}