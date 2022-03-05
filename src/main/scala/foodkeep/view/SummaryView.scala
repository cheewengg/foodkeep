package foodkeep.view

import foodkeep.helper._
import org.scalajs.dom.document


object SummaryView {
  private val dailySummaryContainer = document.querySelector("div.content-box.content--day.content-box--hover")
  private val monthlySummaryContainer = document.querySelector("div.content-box.content--month.content-box--hover")

  //private val dailySummaryContainer= document.querySelector(".summary-content").asInstanceOf[html.Input]
  //private val monthlySummaryContainer = document.querySelector(".summary-content").asInstanceOf[html.Input]

  private def generateMarkupContentHeaderDaily(dailyMeal: DailyMeal):String = {
    val day = dailyMeal.date.slice(6,8)
    val month = dailyMeal.date.slice(4,6)
    val year = dailyMeal.date.slice(0,4)

    s"""<div class="content-header">
    <div class="header-desc day--desc">Today</div>
    <div class="header-date day--date">${day}/${month}/${year}</div>
    </div>"""
  }

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
      val rowTime = meal.dateTime.slice(8,12)
      val rowMeal = meal.foodName
      val rowExpense = meal.expense

      s"""<div class="table-row">
          <div class="row-time">${rowTime}h</div>
          <div class="row-meal">${rowMeal}</div>
          <div class="row-expense">$$ ${rowExpense}</div>
        </div>"""
    }).join("")
  }

  private def generateMarkUpSummaryDaily(dailyMeal: DailyMeal): String = {
    val markUp = List(
      generateMarkupContentHeaderDaily(dailyMeal),
      generateMarkUpSummaryDailyCalories(dailyMeal),
      generateMarkUpSummaryDailyExpense(dailyMeal),
      generateMarkUpSummaryDailyTable(dailyMeal)
    ).mkString("")

    markUp
  }

  private def generateMarkUpSummaryMonthly(monthlyMeal: MonthlyMeal): String = {
    val markUp = List(
      generateMarkupContentHeaderMonthly(monthlyMeal),
      generateOnTargetMarkup(monthlyMeal),
      generateAverageCaloriesMarkup(monthlyMeal),
      generateExpensesMarkup(monthlyMeal)
    ).mkString("")

    markUp
  }


  private def generateMarkupContentHeaderMonthly(monthlyMeal: MonthlyMeal):String = {
    val monthNum = monthlyMeal.monthYear.slice(4,6)
    val nameOfMonth = Map("01"->"January","02"->"February","03"->"March","04"->"April","05"->"May","06"->"June","07"->"July","08"->"August","09"->"September","10"->"October","11"->"November","12"->"December")
    val year =  monthlyMeal.monthYear.slice(0,4)

    s"""<div class="content-header">
        <div class="header-desc month--desc">This month</div>
        <div class="header-date month--date">${nameOfMonth(monthNum)} ${year}</div>
      </div>"""
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
