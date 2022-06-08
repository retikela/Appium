package Common;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Setup {
	public static AndroidDriver<MobileElement> driver;
	public static DesiredCapabilities cap = new DesiredCapabilities();
	public ObjectMap obj = new ObjectMap();
	public Configure config = new Configure();
	

	@BeforeSuite
	public void startAppium() throws Exception {
		File file = new File(config.getAppName());
		cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, config.getPlatform());
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.getMobileOSVersion());
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, config.getdeviceName());
		cap.setCapability(MobileCapabilityType.APP, file);
		cap.setCapability("appPackage", config.getAppPackage());
		cap.setCapability("appActivity", config.getAppActivity());
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, config.getAutomationName());
		cap.setCapability(MobileCapabilityType.NO_RESET, Boolean.valueOf(config.getNoReset()));
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600);
		URL url = config.getURL();
		driver = new AndroidDriver<MobileElement>(url, cap);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getDefaultWait()), TimeUnit.SECONDS);
	}

	

	static Dimension resolution;
	public SoftAssert sa = new SoftAssert();
	
	@BeforeMethod
	public void beforeMethod(Method method) throws Exception {
		String methodName = "ErrNotSetInBeforeMethod";
		if (method != null) {
			methodName = method.getName();
		}
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out
				.println("Info Test Method Name = " + method.getDeclaringClass().getCanonicalName() + "." + methodName);
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
		System.out.println("tearDown() :: driver.quit() executed");
	}

}
