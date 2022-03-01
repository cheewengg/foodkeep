package foodkeep.helper

import scala.scalajs.js
import foodkeep.helper._

trait Profile extends js.Object {
	val startDate: String
	val endDate: String
	val name: String
    val birthDate: String
    val gender: String
    val weight: Double
    val height: Double
    val levelOfActivity: String
	val caloriesTarget: Int
}

object Profile {
    def apply(startDate: String, endDate: String, name: String, birthDate: String, gender: String, weight: Double, height: Double, levelOfActivity: String, caloriesTarget: Int) = 
            js.Dynamic.literal(startDate = startDate,
                            endDate = endDate,
                            name = name,
                            birthDate = birthDate,
                            gender = gender,
                            weight = weight,
                            height = height,
                            levelOfActivity = levelOfActivity,
                            caloriesTarget = caloriesTarget).asInstanceOf[Profile] 

    def apply(p: js.Dynamic): Profile = {
        val pC = js.JSON.parse(js.JSON.stringify(p))
        val startDate = pC.startDate.asInstanceOf[String]
        val endDate = pC.endDate.asInstanceOf[String]
        val name = pC.name.asInstanceOf[String]
        val birthDate = pC.birthDate.asInstanceOf[String]
        val gender = pC.gender.asInstanceOf[String]
        val weight = pC.weight.asInstanceOf[Double]
        val height = pC.height.asInstanceOf[Double]
        val levelOfActivity = pC.levelOfActivity.asInstanceOf[String]
        val caloriesTarget = pC.caloriesTarget.asInstanceOf[Int]
            
        Profile(startDate, endDate, name, birthDate, gender, weight, height, levelOfActivity, caloriesTarget)
    }
}