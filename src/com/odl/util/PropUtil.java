package com.odl.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropUtil {
	
	

	public static String getProperty(String property) {
		try {
			Resource resource = new ClassPathResource("/config.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			
			String getFromProp = props.getProperty(property).trim();
			
			if (getFromProp.equals("") || getFromProp == null) {
				return null;
			} else {
				return getFromProp;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
