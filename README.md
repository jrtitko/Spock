# Spock

# Documenation
**Official Site for Spock:** http://spockframework.org/  
**Web Testing with GEB/Spock:** https://gebish.org/  

**HamcrestMatcher:** http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/Matchers.html

**GEB:** <br/>
Install: brew tap homebrew/cask && brew cask install chromedriver <br/>
Documentation: https://gebish.org/manual/current/ <br/>
Example: https://github.com/geb/geb-example-gradle <br/>

# IntelliJ IDEA Commands & Shortcuts

## Commands
**Ctl+Enter** within the class braces brings up the 'Generate' menu to generate testing templates  
**Opt+Enter** with cursor on an unimplemented field or method brings up a menu to create a field or method by that name

## Shortcuts
To set up a template for Spock tests, do the following:  

    1. IntelliJ IDEA > Preferences... > File and Code Templates  
    2. Select the 'Code' tab  
    3. Select Spock Test Method   
    4. Modify the template  

    def "${NAME}"() {
    given:
        //
        
    when:
        //
        
    then:
        true
    ${BODY}
    }

## Test the setup
After cloning the project and importing it into IntelliJ, try running the tests in the test directory.  I have found that often IntelliJ is not making the .idea/modules directory in the project.  This can be corrected by going to the project root directory and typing:

    mkdir -p .idea/modules
