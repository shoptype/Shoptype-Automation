package pages;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import utilities.Utilities;

public class BaseClass {

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Properties prop;
	public static JavascriptExecutor je;
	public static Properties checkoutProp;
	public static String scenarioName;
	public static String payload;
	public static Response response;
	
	public static SignUp signUp;
	public static Login login;
	
	public static VendorOnboard vendorOnboard;
	public static CosellerOnboard cosellerOnboard;
	public static NetworkOnboard networkOnboard;
	public static Checkout checkout;
	
	public static String cosellerAuthToken;
	public static String networkAuthToken;
	public static String cosellerUserId;
	public static String networkUserId;
	public static String platformUrl;
	
	public static String shoptypeApiKey;
	public static String cartId;
	public static String deviceId = Utilities.getDeviceId();
	public static String currency;
	public static String checkoutId;
	public static String redirectUri;
	
	public static String firstVendorId;
	public static String firstVendorUserId;
	public static String firstVendorAuthToken;
	public static String firstVendorAttributionId;
	public static String firstVendorProductId;
	public static String firstVendorProductVariantId;
	public static String firstVendorPublishSlug;
	public static String firstVendorTrackerId;
	public static String firstVendorPublishProductUrl;
	
	public static String secondVendorId;
	public static String secondVendorUserId;
	public static String secondVendorAuthToken;
	public static String secondVendorAttributionId;
	public static String secondVendorProductId;
	public static String secondVendorProductVariantId;
	public static String secondVendorPublishSlug;
	public static String secondVendorTrackerId;
	public static String secondVendorPublishProductUrl;


	public static void initChromeBrowser(String url) throws IOException {
		
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-extensions");
		options.addArguments("--no-sandbox");
		options.addArguments("--allow-insecure-localhost");
		
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, 20);
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

}