package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "@reports/rerun.txt",   // <--- rerun only failed scenarios
    glue = {"stepdefinitions", "hooks"},
    plugin = {
        "pretty",
        "html:reports/rerun-reports.html",
        "json:reports/rerun.json"
    },
    monochrome = true,
    publish = false
)
public class RerunFailedTests {
}
