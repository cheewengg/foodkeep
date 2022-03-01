package foodkeep.util 

import scala.scalajs.js
import foodkeep.helper._
import foodkeep.util.util._

package object ProfileUtil {
    implicit class ProfileUtil[Profile](p: js.Object) {
        def updateCaloriesTarget = {
            val pC = Profile(p)
            // KIV logic to calculate calories target
            val caloriesTarget: Int = (pC.height * pC.weight * 10).toInt

            Profile(pC.startDate, pC.endDate, pC.name, pC.birthDate, pC.gender, pC.weight, pC.height, pC.levelOfActivity, caloriesTarget)
        }

        def getCaloriesTarget = Profile(p).caloriesTarget
        
        def updateEndDate(date: String) = {
            val pC = Profile(p)
            Profile(pC.startDate, date, pC.name, pC.birthDate, pC.gender, pC.weight, pC.height, pC.levelOfActivity, pC.caloriesTarget)
        }
    }
}