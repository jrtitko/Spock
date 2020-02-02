package com.industriousgnomes.spock

import groovy.sql.Sql
import org.h2.jdbc.JdbcSQLSyntaxErrorException
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class DatabaseDrivenSpec extends Specification {

    @Shared sql = Sql.newInstance("jdbc:h2:mem:", "org.h2.Driver")

    def setupSpec() {
        sql.execute("create table maxdata (id int primary key, a int, b int, c int)")
        sql.execute("insert into maxdata values " +
                "(1, 3, 1, 3), " +
                "(2, 6, 8, 8), " +
                "(3, 9, 9, 9)")
    }

    @Unroll
    def "Max value: a=#a, b=#b, c=#c"() {
        expect:
            c == Math.max(a, b)

        where:
            [a, b, c] << sql.rows("select a, b, c from maxdata")
    }

    def "Successfully insert a record"() {
        when:
            sql.execute("insert into maxdata values (4, 10, 10, 10)")

        then:
            noExceptionThrown()

        when:
            def result = sql.firstRow("select a from maxdata where id = 4").get("a")

        then:
            result == 10;

    }

    def "Fail to insert a record"() {
        when:
            sql.execute("insert into maxdata values (5, 10)")

        then:
            thrown(JdbcSQLSyntaxErrorException)
    }
}
