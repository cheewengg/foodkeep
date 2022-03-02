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

object ProfileView {
    private val profileName = document.querySelector(".profile--name")
    private val profileBirthDate = document.querySelector(".profile--birthdate")
    private val profileGender = document.querySelector(".profile--gender")
    private val profileWeight = document.querySelector(".profile--weight")
    private val profileHeight = document.querySelector(".profile--height")
    private val profileActivityLvl = document.querySelector(".profile--activitylvl")
    private val profileCalories = document.querySelector(".profile--calories")

    def render(profileOption: Option[Profile]): Unit = profileOption match {
        case Some(profile) => {
            profileName.textContent = profile.name
            profileBirthDate.textContent = renderBirthDate(profile.birthDate)
            profileGender.textContent = profile.gender
            profileWeight.textContent = profile.weight.toString + " kg"
            profileHeight.textContent = profile.height.toString + " m"
            profileActivityLvl.textContent = profile.levelOfActivity
            profileCalories.textContent = profile.caloriesTarget.toString + " kCal"
        } 
        case _ => {
            profileName.textContent = "Not Available"
            profileBirthDate.textContent = "Not Available"
            profileGender.textContent = "Not Available"
            profileWeight.textContent = "Not Available"
            profileHeight.textContent = "Not Available"
            profileActivityLvl.textContent = "Not Available"
            profileCalories.textContent = "Not Available"
        }
    }

    private def renderBirthDate(date: String): String = 
        //19940102 --> 02/01/1994
        s"${date.drop(6)}/${date.drop(4).dropRight(2)}/${date.dropRight(4)}"
    
}