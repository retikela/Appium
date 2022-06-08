package Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class Configure {
	FileInputStream config;
	Properties props;

	public Configure() {
		try {
			config = new FileInputStream("Config.properties");
			props = new Properties();
			props.load(config);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getKeyValue(String key) {
		return props.getProperty(key);
	}

	public String getPlatform() {
		return getKeyValue("platformName");
	}
	
	public String getNoReset() {
		return getKeyValue("noReset");
	}
	
	public String getAppActivity() {
		return getKeyValue("appActivity");
	}
	
	public String getMobileOSVersion() {
		return getKeyValue("platformVersion");
	}

	public String getAppDir() {
		return getKeyValue("appDir");
	}

	public String getAppName() {
		return getKeyValue("appName");
	}

	public String getdeviceName() {
		return getKeyValue("deviceName");
	}

	

	public String getDefaultWait() {
		return getKeyValue("appWaitTime");
	}

	public String getAppPackage() {
		return getKeyValue("appPackage");
	}

	public String getAutomationName() {
		if (getPlatform().equalsIgnoreCase("iOS"))
			return "XCUITest";
		else if (getPlatform().equalsIgnoreCase("Android"))
			return "uiautomator2";
		return null;
	}

	public boolean isAndroid() {
		if (getPlatform().equalsIgnoreCase("Android"))
			return true;
		return false;
	}

	public boolean isiOS() {
		if (getPlatform().equalsIgnoreCase("iOS"))
			return true;
		return false;
	}

	public String getAppPath() {
		File appDir = new File(getAppDir());
		File app = new File(appDir, getAppName());
		return app.getAbsolutePath();
	}

	public String getHost() {
		return getKeyValue("Host");
	}

	public String getPort() {
		return getKeyValue("Port");
	}

	public URL getURL() throws MalformedURLException {
		return new URL("http://" + getHost() + ":" + getPort() + "/wd/hub");
	}


	public String getLanguage() {
		return getKeyValue("language");
	}

}
