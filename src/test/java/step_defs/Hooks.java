package step_defs;

import java.io.IOException;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.PageFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import pages.BaseClass;
import pages.Checkout;
import pages.CosellerOnboard;
import pages.Login;
import pages.NetworkOnboard;
import pages.SignUp;
import pages.VendorOnboard;
import pages.Notifications;
import utilities.Utilities;

public class Hooks extends BaseClass{
	
	@Before("@user_creation")
	public void beforeHook(Scenario scenario) throws IOException {
		
		prop = Utilities.readPropertiesFiles("betaConfig.properties");
		scenarioName = scenario.getName();
		BaseClass.initChromeBrowser(prop.getProperty("frontend_beta_url").toString());
		je = (JavascriptExecutor) driver;
		signUp = PageFactory.initElements(driver, SignUp.class);
		login = PageFactory.initElements(driver, Login.class);
		vendorOnboard = PageFactory.initElements(driver, VendorOnboard.class);
		cosellerOnboard = PageFactory.initElements(driver, CosellerOnboard.class);
		networkOnboard = PageFactory.initElements(driver, NetworkOnboard.class);
		checkout = PageFactory.initElements(driver, Checkout.class);
		notifications = PageFactory.initElements(driver, Notifications.class);
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");

	}
	
	@After("@user_creation")
	public void afterHook(Scenario scenario) throws IOException, InterruptedException {
		
		if (scenario.isFailed()) {
			
			TakesScreenshot ss = (TakesScreenshot) driver;
			byte[] image = ss.getScreenshotAs(OutputType.BYTES);
		    scenario.attach(image, "image/png", scenario.getName().toString());
			
		}
		
		driver.quit();

	}
		
	@Before("@api")
	public void apiBeforehook(Scenario scenario) {
		
		scenarioName = scenario.getName();
		
	}
	
	@After("@api")
	public void apiAfterHook() {}

}
