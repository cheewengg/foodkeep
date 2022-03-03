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

object ProfileUpdateView {
    private val btnOpenUpdateProfile = document.querySelector(".button-update")
    private val btnCloseUpdateProfile = document.querySelector(".button-close")
    private val btnSubmitUpdateProfile = document.querySelector(".button-submit")
    private val modalUpdateProfile = document.querySelector(".modal")
    private val overlayUpdateProfile = document.querySelector(".overlay")

    // form fields
    private val fieldName = document.querySelector("#name").asInstanceOf[html.Input]
    private val fieldBirthDate = document.querySelector("#birthdate").asInstanceOf[html.Input]
    private val fieldGender = document.querySelector("#gender").asInstanceOf[html.Input]
    private val fieldWeight = document.querySelector("#weight").asInstanceOf[html.Input]
    private val fieldHeight = document.querySelector("#height").asInstanceOf[html.Input]
    private val fieldActivityLvl = document.querySelector("#activitylvl").asInstanceOf[html.Select]

    
    def render(profileOption: Option[Profile]): Unit = profileOption match {
        case Some(profile) => {
            fieldName.value = profile.name
            fieldBirthDate.value = renderBirthDate(profile.birthDate)
            fieldGender.value = profile.gender
            fieldWeight.value = profile.weight.toString
            fieldHeight.value = profile.height.toString
            fieldActivityLvl.value = profile.levelOfActivity
        } 
        case _ => 
    }

    def addHandlerToggleUpdateProfile: Unit = {
        js.Array(btnOpenUpdateProfile, btnCloseUpdateProfile).foreach(_.addEventListener("click", (e: dom.Event) => {
            modalUpdateProfile.classList.toggle("hidden")
            overlayUpdateProfile.classList.toggle("hidden")
        }))
    }

    def addHandlerSubmitUpdateProfile(handler: Profile => Boolean): Unit = {
        btnSubmitUpdateProfile.addEventListener("click", (e: dom.Event) => {
            e.preventDefault()       
            if (validateFormInput) {
                val newProfile = Profile("", "", fieldName.value, formatBirthDate(fieldBirthDate.value), fieldGender.value, fieldWeight.value.toDouble, fieldHeight.value.toDouble, fieldActivityLvl.value, 0)
                
                if (!handler(newProfile)) dom.window.alert("Updated profile will take effect tomorrow!")

                modalUpdateProfile.classList.toggle("hidden")
                overlayUpdateProfile.classList.toggle("hidden")
            } 
        })
    }

    private def validateFormInput: Boolean = {
        val birthDate = fieldBirthDate.value
        val weight = fieldWeight.value
        val height = fieldHeight.value
        
        (Try(birthDate.toInt), Try(weight.toDouble), Try(height.toDouble)) match {
            case (Success(_), Success(_), Success(_)) if birthDate.length == 8 => true
            case _ => {
                dom.window.alert("Invalid input detected!")
                false 
            }
        }    
    }

    private def formatBirthDate(date: String): String = {
        // 02011994 --> 19940102
        date.drop(4) + date.drop(2).dropRight(4) + date.dropRight(6)
    }

    private def renderBirthDate(date: String): String = 
        //19940102 --> 02011994
        date.drop(6) + date.drop(4).dropRight(2) + date.dropRight(4)
}
     
