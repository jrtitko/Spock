package com.industriousgnomes.spock

import com.industriousgnomes.spock.mock.Invoice
import com.industriousgnomes.spock.mock.Pricing
import org.codehaus.groovy.util.ListHashMap
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.security.InvalidParameterException
import java.util.logging.Logger

class MockSpec extends Specification {

    @Subject
    Invoice invoice

    Logger logger = Mock()

    def pricing = Mock(Pricing)

    Map<String, Double> itemList = new ListHashMap<>();

    def setup() {
        itemList.put("crackers", 2.0d)
        itemList.put("milk", 3.0d)

        invoice = new Invoice(
                logger: logger,
                pricing: pricing,
                itemList: itemList
        )
    }

    def "Add item to invoice #1"() {
        given:
            def item = "Bananas"
            def quantity = 1.0d

        when:
            def success = invoice.add(item, quantity)

        then:
            noExceptionThrown()
            1 * logger.info("Added item: " + item + " at quantity: " + quantity)
    }

    @Unroll
    def "Add item to invoice #2 - #item qty: #quantity"() {
        when:
            def success = invoice.add(item, quantity)

        then:
            noExceptionThrown()

        and:
            1 * logger.info("Added item: " + item + " at quantity: " + quantity)

        where:
            item        | quantity
            "Bananas"   | 1.0d
            "Apples"    | 3.0d
    }

    def "Fail to log add item to invoice"() {
        given:
            def item = "Tofu"
            def quantity = 1

            logger.info(_) >> { throw new InvalidParameterException() }

        when:
            def success = invoice.add(item, quantity)

        then:
            thrown(InvalidParameterException)
    }

    def "Mutliple when then sections in a test"() {
        given:
            invoice.add("bananas", 2.0d)

        when:
            def total = invoice.getTotal()

        then:
            3 * pricing.getPrice(_ as String) >>> [ 0.50d, 1.00d, 2.00d ]
            total == 7.5d

        then:
            invoice.add("apples", 3.0d)

        when:
            total = invoice.getTotal()

        then:
            4 * pricing.getPrice(_ as String) >>> [ 0.50d, 1.00d, 2.00d, 3.00d ]
            total == 16.5d
    }

    def "Return multiple values from the same method using a String parameter"() {
        given:

        when:
            def total = invoice.getTotal()

        then:
            2 * pricing.getPrice(_ as String) >>> [ 0.50d, 1.00d ]

        then:
            total == 3.50d
    }

    def "Return multiple values from the same method including exceptions"() {
        given:
            invoice.add("bananas", 2.0d)
            invoice.add("apples", 3.0d)

        when:
            def total = invoice.getTotal()

        then:
            3 * pricing.getPrice(_) >>> [ 0.50d, 1.00d ] >> { throw new InvalidParameterException()}
            thrown InvalidParameterException
    }

    def "Capture a parameter to interrogate where method returns a value"() {

        Double price

        given:
            invoice.add("bananas", 2.0d)
            invoice.add("apples", 3.0d)

        when:
            def total = invoice.lookupPriceOfLastItem()

        then:
            pricing.getPrice(_) >> { args -> price = args[0] == "apples" ? 1.0d : 99.0d }
            price == 1.0d
            total == 1.00d;
    }

    def "Analyzing an assert failure"() {

        expect:
            Math.max(a, b) == c

        where:
            a | b || c
            3 | 5 || 1
    }

    def "Analyzing an invocation failure"() {
        given:
            invoice.add("bananas", 2.0d)

        when:
            def total = invoice.getTotal()

        then:
            4 * pricing.getPrice(_ as String) >>> [0.50d, 1.00d, 2.00d]
            total == 7.5d
    }


/*
Alternate ways to describe method arguments
1 * subscriber.receive("hello")     // an argument that is equal to the String "hello"
1 * subscriber.receive(!"hello")    // an argument that is unequal to the String "hello"
1 * subscriber.receive()            // the empty argument list
1 * subscriber.receive(_)           // any single argument (including null)
1 * subscriber.receive(*_)          // any argument list (including the empty argument list)
1 * subscriber.receive(!null)       // any non-null argument
1 * subscriber.receive(_ as String) // any non-null argument that is-a String
1 * subscriber.receive({ it.size() > 3 }) // an argument that satisfies the given predicate
*/
}
