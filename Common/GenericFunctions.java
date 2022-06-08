package Common;

import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidKeyCode;

public class GenericFunctions {
	MobileElement element;
	List<MobileElement> elements;

	public void pressEnter() {
		Setup.driver.pressKeyCode(AndroidKeyCode.ENTER);
	}

	public void clearText(MobileElement m) {
		m.clear();
	}

	public void enterText(MobileElement m, String txt) {
		m.sendKeys(txt);
	}

	public void waitforProgressbarDismiss() {
		while (waitforClassName("android.widget.ProgressBar", 1)) {
		}
	}

	private MobileElement findElement(String locatorType, String locator) {
		switch (locatorType) {
		case "xpath":
			element = Setup.driver.findElementByXPath(locator);
			break;
		case "id":
			element = Setup.driver.findElementById(locator);
			break;
		case "name":
			element = Setup.driver.findElementByName(locator);
			break;
		case "class":
			element = Setup.driver.findElementByClassName(locator);
			break;
		}
		return element;

	}

	private List<MobileElement> findElements(String locatorType, String locator) {
		switch (locatorType) {
		case "xpath":
			elements = Setup.driver.findElementsByXPath(locator);
			break;
		case "id":
			elements = Setup.driver.findElementsById(locator);
			break;
		case "name":
			elements = Setup.driver.findElementsByName(locator);
			break;
		case "class":
			elements = Setup.driver.findElementsByClassName(locator);
			break;
		}

		return elements;

	}

	private boolean waitForElement(String locatorType, String locator, int time) {
		WebDriverWait wait = new WebDriverWait(Setup.driver, time);
		try {
			switch (locatorType) {
			case "xpath":
				wait.until(ExpectedConditions.elementToBeClickable(MobileBy.xpath(locator)));
				break;
			case "id":
				wait.until(ExpectedConditions.elementToBeClickable(MobileBy.id(locator)));
				break;
			case "name":
				wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name(locator)));
				break;
			case "class":
				wait.until(ExpectedConditions.elementToBeClickable(MobileBy.className(locator)));
				break;
			}
		} catch (Exception e) {
			System.out.println("Element not visible :" + locator);
			return false;
		}
		return true;
	}

	public MobileElement findElementById(String id) {
		return findElement("id", id);
	}

	public MobileElement findElementByXpath(String xpath) {
		return findElement("xpath", xpath);
	}

	public MobileElement findElementByName(String name) {
		return findElement("name", name);
	}

	public MobileElement findElementByClassName(String className) {
		return findElement("class", className);
	}

	public List<MobileElement> findElementsById(String id) {
		return findElements("id", id);
	}

	public List<MobileElement> findElementsByXpath(String xpath) {
		return findElements("xpath", xpath);
	}

	public List<MobileElement> findElementsByName(String name) {
		return findElements("name", name);
	}

	public List<MobileElement> findElementsByClassName(String className) {
		return findElements("class", className);
	}

	public boolean waitforId(String id, int time) {
		return waitForElement("id", id, time);
	}

	public boolean waitforXpath(String xpath, int time) {
		return waitForElement("xpath", xpath, time);
	}

	public boolean waitforName(String name, int time) {
		return waitForElement("name", name, time);
	}

	public boolean waitforClassName(String className, int time) {
		return waitForElement("class", className, time);
	}

	public void swipeUp() {
		Dimension resolution = Setup.driver.manage().window().getSize();
		int startx = resolution.width / 2;
		int starty = resolution.height / 2;
		int endx = 0;
		int endy = (int) (starty * (-0.1));
		swipe(startx, starty, endx, endy);
	}

	private static void swipe(int startx, int starty, int endx, int endy) {
		TouchAction swipe = new TouchAction(Setup.driver).press(startx, starty).moveTo(endx, endy).release();
		swipe.perform();
	}


}
