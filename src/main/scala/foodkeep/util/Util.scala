package foodkeep.util

import scala.language.implicitConversions
import scala.scalajs.js

object util {
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


}