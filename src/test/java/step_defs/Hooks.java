package step_defs;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.support.PageFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import pages.BaseClass;
import pages.CosellerOnboard;
import pages.Login;
import pages.NetworkOnboard;
import pages.SignUp;
import pages.VendorOnboard;
import utilities.Utilities;

public class Hooks extends BaseClass{
	
	@Before("@user_creation")
	public void beforeHook(Scenario scenario) throws IOException {
		
		prop = Utilities.readPropertiesFiles("betaConfig.properties");
		scenarioName = scenario.getName();
		BaseClass.initChromeBrowser(prop.getProperty("frontend_beta_url").toString());
		signUp = PageFactory.initElements(driver, SignUp.class);
		login = PageFactory.initElements(driver, Login.class);
		vendorOnboard = PageFactory.initElements(driver, VendorOnboard.class);
		cosellerOnboard = PageFactory.initElements(driver, CosellerOnboard.class);
		networkOnboard = PageFactory.initElements(driver, NetworkOnboard.class);
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");

	}
	
	@After("@user_creation")
	public void afterHook() throws IOException, InterruptedException {

		driver.quit();

	}

//	@Before("@network_url")
//	public void beforeHookNetwork(Scenario scenario) throws IOException {
//		
//		scenarioName = scenario.getName();
//		BaseClass.initChromeBrowser(platformUrl);
//		signUp = PageFactory.initElements(driver, SignUp.class);
//		login = PageFactory.initElements(driver, Login.class);
//		vendorOnboard = PageFactory.initElements(driver, VendorOnboard.class);
//		cosellerOnboard = PageFactory.initElements(driver, CosellerOnboard.class);
//		networkOnboard = PageFactory.initElements(driver, NetworkOnboard.class);
//		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");
//
//	}
		
	@Before("@api")
	public void apiBeforehook(Scenario scenario) {
		
		scenarioName = scenario.getName();
		
	}
	
	@After("@api")
	public void apiAfterHook() {}

}
