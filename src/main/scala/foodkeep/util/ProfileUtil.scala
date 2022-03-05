package foodkeep.util

import scala.scalajs.js
import foodkeep.helper._
import foodkeep.util.util._

package object ProfileUtil {
    implicit class ProfileUtil[Profile](p: js.Object) {
        def updateCaloriesTarget = {
            val pC = Profile(p)
            // KIV logic to calculate calories target
            val caloriesTarget: Int = calculateCalorieTarget(pC.height, pC.weight, pC.birthDate, pC.levelOfActivity, pC.gender, pC.startDate).toInt

            Profile(pC.startDate, pC.endDate, pC.name, pC.birthDate, pC.gender, pC.weight, pC.height, pC.levelOfActivity, caloriesTarget)
        }

        def updateEndDate(date: String = "99999999") = {
            val pC = Profile(p)
            Profile(pC.startDate, date, pC.name, pC.birthDate, pC.gender, pC.weight, pC.height, pC.levelOfActivity, pC.caloriesTarget)
        }

        def updateStartDate(date: String = getDateDMY) = {
            val pC = Profile(p)
            Profile(date, pC.endDate, pC.name, pC.birthDate, pC.gender, pC.weight, pC.height, pC.levelOfActivity, pC.caloriesTarget)
        }

        def getCaloriesTarget: Int = Profile(p).caloriesTarget

    }


    private def calculateCalorieTarget(height:Double, weight:Double, birthDate:String, levelOfActivity:String, gender:String, startDate:String): Double = {
        val age = {
            if (startDate.slice(4,8).toInt - birthDate.slice(4,8).toInt >= 0)
                startDate.slice(0,4).toInt-birthDate.slice(0,4).toInt
            else
                startDate.slice(0,4).toInt - birthDate.slice(0,4).toInt - 1
        }

        val calorie = {
            if (gender == "F") {
                0.9 * weight * calculateBodyFatPct(height, weight, gender, age) * 24 * activityLevelMultiplier(levelOfActivity)
            }
            else {
                weight * calculateBodyFatPct(height, weight, gender, age) * 24 * activityLevelMultiplier(levelOfActivity)
            }
        }

        calorie
    }

    private def calculateBodyFatPct(height:Double, weight:Double, gender:String, age:Int):Double = {
        val bmi:Double = weight/(height*height)
        var leanFactorMultiplier:Double = 0
        if (gender == "F"){
            val bfc:Double = 1.20 * bmi + 0.23 * age - 5.4
            if ((14.0<= bfc) && (bfc<=18.0)) {leanFactorMultiplier = 1.0} //case it if 14 until 18 contains it => 1.0
            else if ((19.0<=bfc) && (bfc<=28.0))  {leanFactorMultiplier = 0.95} //case it if 19 until 28 contains it => 0.95
            else if ((29.0<=bfc) && (bfc<=38.0))  {leanFactorMultiplier = 0.90} //case it if 29 until 38 contains it => 0.90
            else {0.85} //case x:Double if x > 38 => 0.85
        }
        else{
            val bfc:Double = 1.20 * bmi + 0.23 * age - 16.2
            if ((10.0<= bfc) && (bfc<=14.0)) {leanFactorMultiplier = 1.0}//case it if 10 until 14 contains it => 1.0
            else if ((15.0<=bfc) && (bfc<=20.0)) {leanFactorMultiplier = 0.95}//case it if 15 until 20 contains it => 0.95
            else if ((21.0<=bfc) && (bfc<=28.0))  {leanFactorMultiplier = 0.90}//case it if 21 until 28 contains it => 0.90
            else {leanFactorMultiplier = 0.85} //case x:Double if x > 28 => 0.85
        }
        leanFactorMultiplier
    }

    private def activityLevelMultiplier(levelOfActivity:String):Double = levelOfActivity match {
        case "Light" => 1.55
        case "Moderate" => 1.65
        case "Heavy" => 1.80
    }


}