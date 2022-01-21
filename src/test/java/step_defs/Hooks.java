package step_defs;

import java.io.IOException;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import pages.Admin;
import pages.BaseClass;
import pages.Checkout;
import pages.CosellerOnboard;
import pages.CosellerRegistration;
import pages.Login;
import pages.NetworkOnboard;
import pages.SignUp;
import pages.VendorOnboard;
import pages.Notifications;
import utilities.Utilities;

public class Hooks extends BaseClass{
	
	@Before("@ui")
	public void beforeHook(Scenario scenario) throws IOException {
		
		prop = Utilities.readPropertiesFiles("betaConfig.properties");
		scenarioName = scenario.getName();
		BaseClass.initChromeBrowser(prop.getProperty("frontend_beta_url").toString());
		je = (JavascriptExecutor) driver;
		actions = new Actions(driver);
		signUp = PageFactory.initElements(driver, SignUp.class);
		login = PageFactory.initElements(driver, Login.class);
		vendorOnboard = PageFactory.initElements(driver, VendorOnboard.class);
		cosellerOnboard = PageFactory.initElements(driver, CosellerOnboard.class);
		networkOnboard = PageFactory.initElements(driver, NetworkOnboard.class);
		checkout = PageFactory.initElements(driver, Checkout.class);
		notifications = PageFactory.initElements(driver, Notifications.class);
		cosellerRegistration = PageFactory.initElements(driver, CosellerRegistration.class);
		admin = PageFactory.initElements(driver, Admin.class);
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");

	}
	
	@After("@ui")
	public void afterHook(Scenario scenario) throws IOException, InterruptedException {
		
		if (scenario.isFailed()) {
			
			TakesScreenshot ss = (TakesScreenshot) driver;
			byte[] image = ss.getScreenshotAs(OutputType.BYTES);
		    scenario.attach(image, "image/png", scenario.getName().toString());
			
		}
		
		driver.quit();

	}
	
	@Before("@api")
	public void apiBeforehook(Scenario scenario) throws IOException {
		
		scenarioName = scenario.getName();
		prop = Utilities.readPropertiesFiles("betaConfig.properties");
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");
		
	}
	
	@After("@api")
	public void apiAfterHook() {}

}
