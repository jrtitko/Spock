package com.industriousgnomes.spock

import groovy.sql.Sql
import spock.lang.*

@Ignore     // Skips all of the tests in this class
@IgnoreIf({ os.windows })   // Skips all of the tests in this class if the condition is true
@Requires({ os.windows })   // Run all of the tests in this class if the condition is true, otherwise skip these tests
@Stepwise   // Run all of the tests in order
@Unroll     // Reports on each set of tests in where statements in all methods (no need for @Unroll on each method)
class SpockTestAnnotations extends Specification {

    @Subject    // Declares what class is being tested
    Math math

    @Shared     // Declares a class level variable that is set up only once before any tests are run
    sql = Sql.newInstance("jdbc:h2:mem:", "org.h2.Driver")

    @Ignore     // Skips this test.  Optionally a reason can be passed to @Ignore
    def "Ignored test"() { expect: 1 == 2 }

    @IgnoreRest     // Skips all of the other tests except this one or other tests also marked as @IgnoreRest
    def "Only run this test"() { expect: 1 == 1 }

    @IgnoreIf({ System.getProperty("os.name").contains("windows") })    // Skips this test if the condition is true
    def "Skip this test if on a windows machine"() { expect: 1 == 1 }

    @Requires({ System.getProperty("os.name").contains("windows") })    // Run this test if the condition is true
    def "Run this test if on a windows machine"() { expect: 1 == 1 }

    @Timeout(5)     // Puts a time limit on how long a test can take to run
    def "Fail test if it takes too long"() { expect: 1 == 1 }

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
