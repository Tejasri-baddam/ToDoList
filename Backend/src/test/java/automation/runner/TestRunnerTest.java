package automation.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/automation/features", 
    glue = "automation.stepdefinitions", 
    plugin = {"pretty", "html:target/cucumber-reports.xml"},
    monochrome = true
)
public class TestRunnerTest {
}


