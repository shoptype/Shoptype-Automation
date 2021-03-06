package step_defs;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(

		features = "src/test/resources/features", 
		glue = { "step_defs" }, 
		plugin = {
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" 
				},
		monochrome = true,
		dryRun = false

)

public class TestRunner {

}
