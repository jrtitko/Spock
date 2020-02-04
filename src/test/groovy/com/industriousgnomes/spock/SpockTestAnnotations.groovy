package com.industriousgnomes.spock

import groovy.sql.Sql
import spock.lang.*

import java.util.concurrent.TimeUnit

@Ignore     // Skips all of the tests in this class
@IgnoreIf({ os.windows })   // Skips all of the tests in this class if the condition is true
@Issue("http://my.issues.org/FOO-1")    // Tie an issue to this test
@Requires({ os.windows })   // Run all of the tests in this class if the condition is true, otherwise skip these tests
@Retry                      // Retry any tests that fail.  See method on retry for options
@Stepwise   // Run all of the tests in order.  First failure stops remaining tests
@Unroll     // Reports on each set of tests in where statements in all methods (no need for @Unroll on each method)
class SpockTestAnnotations extends Specification {

    @Subject    // Declares what class is being tested
    Math math

    @Shared     // Declares a class level variable that is set up only once before any tests are run
    sql = Sql.newInstance("jdbc:h2:mem:", "org.h2.Driver")

    @Ignore("give a reason")     // Skips this test.  Optionally a reason can be passed to @Ignore
    def "Ignored test"() { expect: false }

    @IgnoreRest     // Skips all of the other tests except this one or other tests also marked as @IgnoreRest
    def "Only run this test"() { expect: true }

    @IgnoreIf({ System.getProperty("os.name").contains("windows") })    // Skips this test if the condition is true
    def "Skip this test if on a windows machine"() { expect: true }

    @Issue("http://my.issues.org/FOO-1")    // Tie an issue to this test
    def "Indicate which issue this test is for"() { expect: true }

    @Requires({ System.getProperty("os.name").contains("windows") })    // Run this test if the condition is true
    def "Run this test if on a windows machine"() { expect: true }

/*
    @Retry(exceptions = [IOException])  // Only retries if one of these exceptions is thrown
    @Retry(condition = { failure.message.contains('foo')})  // Only retries if the condition is true
*/
    @Retry(count = 5, delay = 1000)     // Retries the test several times.  This is for flaky tests.  Defaults to 3 tries at 0ms.
    def "Retry this flaky test"() { expect: true }

    @Timeout(value = 500, unit = TimeUnit.MICROSECONDS) // Puts a time limit on how long a test can take to run, units defaults to seconds
    def "Fail test if it takes too long"() { expect: true }

    @Unroll         // Reports on each row of tests independently.  Default is to report on the test as a whole
    def "Should test a 'expect..where' scenario: a=#a, b=#b, c=#c"() {
        expect: Math.max(a, b) == c

        where:
            a | b || c
            3 | 1 || 1
            6 | 8 || 6
            9 | 9 || 9
    }
}
