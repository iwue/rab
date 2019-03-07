package rab.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class RabConfigs {
	private static Properties prop;
	private static OutputStream outputFileStream = null;
	private static InputStream inputStream = null;
	
	public static String getProperty(String valueName) {
		try {
			prop = new Properties();
			File propertyFIle = new File(RabStatics.getPropertyFile());
			
			if (propertyFIle.exists()) {
				inputStream = new FileInputStream(propertyFIle);
				prop.load(inputStream);
			}
						
			return prop.getProperty(valueName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void saveProperty(String valueName, String value) {
		try {
			prop = new Properties();
			File propertyFIle = new File(RabStatics.getPropertyFile());
			
			if (propertyFIle.exists()) {
				inputStream = new FileInputStream(propertyFIle);
				prop.load(inputStream);
			}
			
			outputFileStream = new FileOutputStream(propertyFIle);
			prop.setProperty(valueName, value);
			prop.store(outputFileStream, "properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
