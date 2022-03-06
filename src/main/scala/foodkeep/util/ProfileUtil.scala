package foodkeep.util 

import scala.scalajs.js
import foodkeep.helper._
import foodkeep.util.util._

package object ProfileUtil {
    implicit class ProfileUtil[Profile](p: js.Object) {
        def updateCaloriesTarget = {
            val pC = Profile(p)
            // KIV logic to calculate calories target
            val caloriesTarget: Int = calculateCaloriesTarget(pC.height, pC.weight, pC.birthDate, pC.levelOfActivity, pC.gender).toInt

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

        private def calculateCaloriesTarget(height: Double, weight: Double, birthDate: String, levelOfActivity: String, gender: String): Double = {
            val dateNow = getDateDMY
            val diffYear = dateNow.dropRight(4).toInt - birthDate.dropRight(4).toInt
            val age = if (dateNow.drop(4).toInt > birthDate.drop(4).toInt) diffYear else diffYear + 1

            val baseTarget = weight * leanFactorMultiplier(height, weight, gender, age) * 24 * activityLvlMultiplier(levelOfActivity)

            if (gender == "F") 0.9 * baseTarget else baseTarget
        }

        private def leanFactorMultiplier(height: Double, weight: Double, gender: String, age: Int): Double = {
            val bmi = weight/ (height * height)
            
            gender match {
                case "F" => {
                    val bfc = 1.20 * bmi + 0.23 * age - 5.4
                    bfc match {
                        case _ if (bfc <= 19) => 1
                        case _ if (19 < bfc && bfc <= 29) => 0.95
                        case _ if (29 < bfc && bfc <= 39) => 0.9
                        case _ if (39 < bfc) => 0.85
                    }
                }
                case _ => {
                    val bfc = 1.20 * bmi + 0.23 * age - 16.2
                    bfc match {
                        case _ if (bfc <= 15) => 1
                        case _ if (15 < bfc && bfc <= 21) => 0.95
                        case _ if (21 < bfc && bfc <= 29) => 0.9
                        case _ if (29 < bfc) => 0.85
                    }
                }
            }
        }

        private def activityLvlMultiplier(levelOfActivity: String): Double = levelOfActivity match {
            case "Light" => 1.55
            case "Moderate" => 1.65
            case "Heavy" => 1.80
        }
    }
}