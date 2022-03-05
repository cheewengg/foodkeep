package foodkeep.util

import scala.language.implicitConversions
import scala.scalajs.js

object util {
    val monthList = js.Array("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")

    implicit def convertToJSDynamic(obj: js.Object): js.Dynamic = js.JSON.parse(js.JSON.stringify(obj))

    def padZero(num: Double): String = num.toString().reverse.padTo(2, '0').reverse

    def getDateDMYT: String = {
        val dateNow = new js.Date()
        val hour = padZero(dateNow.getHours())
        val minute = padZero(dateNow.getMinutes())
        
        getDateDMY + hour + minute
    }

    def getDateDMY: String = {
        val dateNow = new js.Date()
        val day = padZero(dateNow.getDate())

        getDateMY + day 
    }

    def getDateMY: String = {
        val dateNow = new js.Date()
        val month = padZero(dateNow.getMonth() + 1)
        val year = padZero(dateNow.getFullYear())

        year + month
    }   

    def getCustomDateDMY(daysAhead: Int): String = {
        val date = new js.Date()
        date.setDate(date.getDate() + daysAhead)

        val day = padZero(date.getDate())
        val month = padZero(date.getMonth() + 1)
        val year = padZero(date.getFullYear())

        year + month + day
    }

    def renderDateDOM(date: String): String = 
        //19940102 --> 02/01/1994
        s"${date.drop(6)}/${date.drop(4).dropRight(2)}/${date.dropRight(4)}"

    def formatDateState(date: String): String = {
        // 02011994 --> 19940102
        date.drop(4) + date.drop(2).dropRight(4) + date.dropRight(6)
    }
}