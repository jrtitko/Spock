package com.industriousgnomes.spock

import spock.lang.Specification
import static org.hamcrest.Matchers.*


class HamcrestMatchersSpec extends Specification {

    def "Compare two decimal numbers that are relatively close"() {
        setup:
            def pi = 3.1415

        expect:
            pi closeTo(Math.PI, 0.01)
    }

    def "Various checks on maps"() {
        setup:
            Map<String, String> myMap = new HashMap<>()
            myMap.put("Hello", "World")
            myMap.put("Goodbye", "Y'all")

        expect:
            myMap aMapWithSize(2)
            myMap hasEntry("Goodbye", "Y'all")
            myMap hasKey("Goodbye")
            myMap hasValue("Y'all")
            myMap not(anEmptyMap())
            myMap notNullValue()

            myMap allOf(hasKey("Goodbye"), hasKey("Hello"))
            myMap anyOf(hasKey("Goodbye"), hasKey("Guten tag"))
    }

    def "Checks for an empty string"() {
        setup:
            String myString = null

        expect:
            myString blankOrNullString()
    }


}
