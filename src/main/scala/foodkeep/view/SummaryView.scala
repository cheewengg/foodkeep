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
  private val dailySummaryContainer= document.querySelector("div.content-box.content--day.content-box--hover")
  private val monthlySummaryContainer = document.querySelector("div.content-box.content--month.content-box--hover")

  //private val dailySummaryContainer= document.querySelector(".summary-content").asInstanceOf[html.Input]
  //private val monthlySummaryContainer = document.querySelector(".summary-content").asInstanceOf[html.Input]

  private def generateMarkUpSummaryDailyCalories(dailyMeal: DailyMeal): String = {
    val sumDailyCalories = dailyMeal.totalCalories

    s"""<div class="content--inline">
        <div class="content-title">Calories</div>
        <div class="content--inline-details on-target day--calories">${sumDailyCalories}</div>
        </div>
      </div>"""
  }

  private def generateMarkUpSummaryDailyExpense(dailyMeal: DailyMeal): String = {
    val sumDailyExpenses = dailyMeal.totalExpenses

    s"""<div class="content--inline">
        <div class="content-title">Expenses</div>
        <div class="content--inline-details on-target day--calories">${sumDailyExpenses}</div>
      </div>"""
  }

  private def generateMarkUpSummaryDailyTable(dailyMeal: DailyMeal): String = {
    dailyMeal.record.map(meal => {
      val rowTime = meal.dateTime
      val rowMeal = meal.foodName
      val rowExpense = meal.expense

      s"""<div class="content--table day--table">
        <div class="table-row">
          <div class="row-time">${rowTime}h</div>
          <div class="row-meal">${rowMeal}</div>
          <div class="row-expense">$$ ${rowExpense}</div>
        </div>"""
    }).join("")
  }

  private def generateMarkUpSummaryDaily(dailyMeal: DailyMeal): String = {
    val markUp = List(
      generateMarkUpSummaryDailyCalories(dailyMeal),
      generateMarkUpSummaryDailyExpense(dailyMeal),
      generateMarkUpSummaryDailyTable(dailyMeal)
    ).mkString("")

    markUp
  }

  private def generateMarkUpSummaryMonthly(monthlyMeal: MonthlyMeal): String = {
    val markUp = List(
      generateOnTargetMarkup(monthlyMeal),
      generateAverageCaloriesMarkup(monthlyMeal),
      generateExpensesMarkup(monthlyMeal)
    ).mkString("")

    markUp
  }

  private def generateOnTargetMarkup(monthlyMeal: MonthlyMeal):String = {
    val month = monthlyMeal.monthYear.slice(4,6)
    val mealDailyRecord = monthlyMeal.record
    val onTarget = (mealDailyRecord.filter(record => (record.caloriesTarget >= record.totalCalories)).length)
    val daysInAMonth = Map("01"->31,"02"->28,"03"->31,"04"->30,"05"->31,"06"->30,"07"->31,"08"->31,"09"->30,"10"->31,"11"->30,"12"->31)

    s"""<div class="content--inline">
      <div class="content-title">You were on target</div>
      <div class="content--inline-details on-target month--target">
        ${onTarget}/${daysInAMonth(month)} days
      </div>
    </div>"""
  }

  private def generateAverageCaloriesMarkup(monthlyMeal: MonthlyMeal):String = {
    val totalCalories = monthlyMeal.totalCalories
    val monthRecordInDays = monthlyMeal.record.length
    val averageCalories = totalCalories/monthRecordInDays

    s"""<div class="content--multi-line">
      <div class="content-title">You consumed</div>
      <div class="content--multi-line-details">
        <div class="details-row">
          <div class="detail-row-title">Daily Average</div>
          <div class="detail-row-details on-target month--avgcalories">
            ${averageCalories} kCal
          </div>
        </div>
      </div>
    </div>"""
  }

  private def generateExpensesMarkup(monthlyMeal:MonthlyMeal):String = {
    val totalExpenses = monthlyMeal.totalExpenses
    val monthRecordInDays = monthlyMeal.record.length
    val averageExpenses = totalExpenses/monthRecordInDays

    s"""<div class="content--multi-line">
      <div class="content-title">You spent</div>
      <div class="content--multi-line-details">
        <div class="details-row">
          <div class="detail-row-title">Total</div>
          <div class="detail-row-details month--allexpenses">$$ ${totalExpenses.toInt}</div>
        </div>
        <div class="details-row">
          <div class="detail-row-title">Daily average</div>
          <div class="detail-row-details month--avgexpenses">$$ ${averageExpenses.toInt}</div>
        </div>
      </div>
    </div>"""
  }

  def render(selectedMeal: (DailyMeal, MonthlyMeal)): Unit = {
    val dailyMeal = selectedMeal._1
    val monthlyMeal = selectedMeal._2

    dailySummaryContainer.innerHTML = generateMarkUpSummaryDaily(dailyMeal)
    monthlySummaryContainer.innerHTML = generateMarkUpSummaryMonthly(monthlyMeal)

  }

}
