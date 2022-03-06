package foodkeep.view

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html
import org.scalajs.dom.ext
import scala.scalajs.js
import js.annotation._

object NavBarView {
    private val sectionHeader = document.querySelector(".section-header")
    private val buttonMobileNav = document.querySelector(".button-mobile-nav")
    private val linkContainerNavBar = document.querySelector(".header-nav-list")
    
    def addHandlerNavBar: Unit = {
        buttonMobileNav.addEventListener("click", (e: dom.Event) => {
            sectionHeader.classList.toggle("nav-open")
        })

        linkContainerNavBar.addEventListener("click", (e: dom.Event) => {
            val eventTarget = e.target.asInstanceOf[html.Element]
            if (eventTarget.classList.contains("nav-link") && sectionHeader.classList.contains("nav-open")) 
                sectionHeader.classList.remove("nav-open")
            }
        )
    }
}