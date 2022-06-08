package Common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class ObjectMap {
	HashMap<String, String> objectmap = new HashMap<String, String>();

	public ObjectMap() {
		System.out.println("In readPropertyFile method");
		new Properties();

		Properties property = new Properties();
		try {
				FileInputStream androidfile = new FileInputStream(
						"AndroidWidget.properties");
				property.load(androidfile);
				Set<String> android = property.stringPropertyNames();
				for (String Property : android) {
					objectmap.put(Property, new Configure().getAppPackage()
							+ ":id/" + property.getProperty(Property));
				}
			System.out.println(objectmap.values());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void setvalue(String key, String value) {
		// TODO Auto-generated method stub
		objectmap.put(key, value);
	}

	public String getvalue(String key) {
		// TODO Auto-generated method stub
		return objectmap.get(key).trim();
	}

}
