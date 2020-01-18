package com.industriousgnomes.spock

import spock.lang.Specification
import spock.lang.Unroll

class SpockTestStructures extends Specification {

    def setupSpec() {
        // Class level setup that occurs only once before any tests run
        // This is an appropriate place to set up:
        //      - Network connections (System Integration Testing)
        //      - In memory database inital data (Application Integration Testing)
    }

    def setup() {
        // Test level setup that occurs once before each test
        // This is an appropriate place to set up:
        //      - Mock classes
        //      - Constant responses from calls to Mock methods
    }

    void cleanup() {
        // Test level cleanup that occurs once after each test
        // This is an appropriate place to clean up:
        //      - Data inserted into in memory databases
    }

    void cleanupSpec() {
        // Class level cleanup that occurs only once after all tests run
        // This is an appropriate place to clean up:
        //      - Network connections
    }

    def "Should test a 'given..when..then' scenario"() {    // A string is required to name the test
        given: "Optional string for documentation"
            // Optional section for setting up this specific test
            // This section is used for declaring:
            //      - Variables
            //      - Mock responses

        when: "Optional string for documentation"
            // Required section for executing the test
            def result = Math.min(a, b)

        then: "Optional string for documentation"
            // Required section for evaluating the test results
            // This section is used for evaluating:
            //      - Variables
            //      - The number of times a line of code was called
            result == c

        // The when..there combination can be repeated as many times as desired to evaluate
        // multiple steps of a larger test.  However, every test should only test one thing.
        // An example would be an open, update, close of a database table where every step
        // is required to run the test.

        where:
            // Optional section for supplying data for tests
            a | b || c
            3 | 1 || 1
            6 | 8 || 6
            9 | 9 || 9
    }

    @Unroll
    def "Should test a 'expect..where' scenario: a=#a, b=#b, c=#c"() {
        expect: "Optional string for documentation"
            // Required section for executing the test
            // This section performs both the execution of a method and evaluats the results
            Math.max(a, b) == c

        where:
            // Optional section for supplying data for tests
            a << [3, 6, 9]
            b << [1, 8, 9]
            c << [3, 8, 9]
    }


}
