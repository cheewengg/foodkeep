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

object SummaryView {
    private val dailyDayDesc = document.querySelector(".day--desc")
    private val dailyDayDate = document.querySelector(".day--date")
    private val dailyDayCalories = document.querySelector(".day--calories")
    private val dailyDayExpenses = document.querySelector(".day--expenses")
    private val dailyDayTable = document.querySelector(".day--table")
    
    private val monthlyMonthDesc = document.querySelector(".month--desc")
    private val monthlyMonthDate = document.querySelector(".month--date")
    private val monthlyMonthTarget = document.querySelector(".month--target")
    private val monthlyMonthAvgCalories = document.querySelector(".month--avgcalories")
    private val monthlyMonthAllExpenses = document.querySelector(".month--allexpenses")
    private val monthlyMonthAvgExpenses = document.querySelector(".month--avgexpenses")
    
    def render(mealsOption: Option[(DailyMeal, MonthlyMeal)]): Unit = mealsOption match {
        case Some(meals) => {
            renderDailySummary(meals._1)
            renderMonthlySummary(meals._2)
        }
        case _ => {
            renderDailyNoData()
            renderMonthlyNoData()
        }
    }
    
    private def renderDailySummary(dailyMeal: DailyMeal): Unit = {
        val date = dailyMeal.date
        val record = dailyMeal.record
        val totalExpenses = dailyMeal.totalExpenses
        val totalCalories = dailyMeal.totalCalories
        val caloriesTarget = dailyMeal.caloriesTarget

        dailyDayDesc.textContent = renderDayDesc(date)
        dailyDayDate.textContent = renderDateDOM(date)
        dailyDayCalories.textContent = renderCalories(totalCalories, caloriesTarget)
        dailyDayExpenses.textContent = renderExpense(totalExpenses)
        dailyDayTable.innerHTML = generateMarkUpSummaryTable(record)
    }

    private def renderMonthlySummary(monthlyMeal: MonthlyMeal): Unit = {
        val monthYear = monthlyMeal.monthYear
        val record = monthlyMeal.record
        val totalExpenses = monthlyMeal.totalExpenses
        val totalCalories = monthlyMeal.totalCalories
        val avgCaloriesTarget = getAvgCaloriesTarget(record)

        monthlyMonthDesc.textContent = renderMonthDesc(monthYear)
        monthlyMonthDate.textContent = renderMonthDate(monthYear)
        monthlyMonthTarget.textContent = renderMonthTarget(record)
        monthlyMonthAvgCalories.textContent = renderCalories(totalCalories/record.length, avgCaloriesTarget)
        monthlyMonthAllExpenses.textContent = renderExpense(totalExpenses)
        monthlyMonthAvgExpenses.textContent = renderExpense(totalExpenses/record.length) 
    }

    private def renderDailyNoData(): Unit = {
        dailyDayDesc.textContent = "Not Available"
        dailyDayDate.textContent = "Not Available"
        dailyDayCalories.textContent = "Not Available"
        dailyDayExpenses.textContent = "Not Available"
        dailyDayTable.innerHTML = ""
    }
    
    private def renderMonthlyNoData(): Unit = {
        monthlyMonthDesc.textContent = "Not Available"
        monthlyMonthDate.textContent = "Not Available"
        monthlyMonthTarget.textContent = "Not Available"
        monthlyMonthAvgCalories.textContent = "Not Available"
        monthlyMonthAllExpenses.textContent = "Not Available"
        monthlyMonthAvgExpenses.textContent = "Not Available"
    }

    private def generateMarkUpSummaryTable(record: js.Array[Meal]): String = {
        record.map(meal => {
            val time = renderTime(meal.dateTime)
            val foodName = renderFoodName(meal.foodName)
            val expense = renderExpense(meal.expense)

            s"""<div class="table-row">
              <div class="row-time">${time}</div>
              <div class="row-meal">${foodName}</div>
              <div class="row-expense">${expense}</div>
            </div>"""
        }).join("")
    }

    private def renderDayDesc(date: String) = 
        if (date == getDateDMY) "Today" else "History"
    
    private def renderMonthDesc(monthYear: String) = 
        if (monthYear == getDateMY) "This month" else "History"

    private def renderTime(dateTime: String): String = dateTime.drop(8) + " h"

    private def renderFoodName(foodName: String): String = if (foodName.length > 16) foodName.slice(0, 13) + "..." else foodName

    private def renderExpense(expense: Double): String = "$ " + expense match {
        case s if s.contains(".") => s
        case j => j + ".00"
    }

    private def renderCalories(calories: Int, caloriesTarget: Int): String = {
        val fieldCalories = js.Array(dailyDayCalories, monthlyMonthAvgCalories)
        if (calories <= caloriesTarget) fieldCalories.foreach(field => {
            field.classList.add("on-target")
            field.classList.remove("off-target")
        })
        else fieldCalories.foreach(field => {
            field.classList.remove("on-target")
            field.classList.add("off-target")
        })
        calories + " kCal"}

    private def renderMonthTarget(record: js.Array[DailyMeal]): String = {
        val target = record.filter(daily => daily.totalCalories <= daily.caloriesTarget).length

        if (target.toFloat/record.length >= 0.6) {
            monthlyMonthTarget.classList.add("on-target")
            monthlyMonthTarget.classList.remove("off-target")
        }
        else {
            monthlyMonthTarget.classList.add("off-target")
            monthlyMonthTarget.classList.remove("on-target")
        }

        s"${target}/${record.length} day${if (record.length > 1) "s" else ""}"
    }

    private def getAvgCaloriesTarget(record: js.Array[DailyMeal]): Int =    
        record.foldLeft(0)((sum, daily) => sum + daily.caloriesTarget)/record.length
    
    private def renderMonthDate(monthYear: String): String = 
        monthList(monthYear.drop(4).toInt - 1) + " " + monthYear.dropRight(2)
         
}