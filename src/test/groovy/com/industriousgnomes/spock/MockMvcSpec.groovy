package com.industriousgnomes.spock

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject

class MockMvcSpec extends Specification {

    @Subject
    MyRestController myRestController

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(myRestController).build()
    }

    def "Should test mockMvc"() {
        given:
            //

        when:
            def response = mockMvc.perform(get("/myendpoint")
                    .sessionAttr("myContext", "myContextValue")
                    .param("field1", "value1")
                    .param("field2", "value2"))

        then:
            def content = response
                    .addExpect(status().is2xxSuccessful())
                    .andReturn()
    }
}
