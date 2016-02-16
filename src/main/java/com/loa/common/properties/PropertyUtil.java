package com.loa.common.properties;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.StringTokenizer;

public class PropertyUtil {
	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	private Properties prop = null;
	public PropertyUtil(String name) {
		prop = new Properties();
		load(name);
	}
	
	public void load(String name) {
		try {
			String classpath = System.getProperties().getProperty("java.class.path");
			StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator);
			while(st.hasMoreTokens()) {
				String path = st.nextToken();
				File f = new File(path + File.separator +"properties" +File.separator + name + ".properties");
				//System.out.println(f.getAbsolutePath());
				if(f.isFile()) {
					FileInputStream fis = new FileInputStream(f);
					prop.load(fis);
					fis.close();
				}
			}
			if(prop.size() == 0 ) {
				String path = getClass().getClassLoader().getResource("properties/" + name + ".properties" ).getPath();
				File f = new File(path );
				//System.out.println(f.getAbsolutePath());
				if(f.isFile()) {
					FileInputStream fis = new FileInputStream(f);
					prop.load(fis);
					fis.close();
				}
			}
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}
}