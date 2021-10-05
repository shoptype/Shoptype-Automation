package step_defs;

import java.io.File;
import org.junit.After;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import com.cucumber.listener.Reporter;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		
		features = "src/test/resources/features", 
		glue = { "step_defs" }, 
		plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/Test_Reports/report.html" }
		
	)

public class TestRunner {

	@After
	public void after() {

		Reporter.loadXMLConfig(new File("extent-config.xml"));
		Reporter.setSystemInfo("Tester", "Kiran");
		Reporter.setSystemInfo("Environment", "JENKINS");

	}

}
