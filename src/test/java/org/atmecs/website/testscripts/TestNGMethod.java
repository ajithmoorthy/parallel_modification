package org.atmecs.website.testscripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.atmecs.website.constants.FileConstants;
import org.atmecs.website.utils.ExcelReader;
import org.atmecs.website.utils.PropertiesReader;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

public class TestNGMethod {
	ExcelReader excelread=new ExcelReader();
	PropertiesReader propread=new PropertiesReader();
	public List<XmlSuite> suiteXml(String[] obj) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException 
	{
		Properties props = propread.KeyValueLoader(FileConstants.CONFIG_PATH);
		List<String> names = new ArrayList<String>();
		String[] arr = props.getProperty("webdrivername").split(",");
		String arr1[]=arr[1].split(":");
		for (String name : arr1)
		{
			names.add(name);
		}
		XmlSuite xmlSuite = new XmlSuite();
		xmlSuite.setName("mysuite");
		xmlSuite.setParallel(ParallelMode.TESTS);
		xmlSuite.setThreadCount(15);
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		for(int initial=0; initial<obj.length; initial++) 
		{
		for (int count=0; count<names.size(); count++) 
		{
			XmlTest xmlTest1 = new XmlTest(xmlSuite);
			Map<String, String> parameter1 = new HashMap<String, String>();
			parameter1.put("Name", names.get(count));
			xmlTest1.setParameters(parameter1);
			xmlTest1.setName("Test validate " +names.get(count)+obj[initial]);
			Class<?> class1 = Class.forName(obj[initial]);  
			XmlClass myClass = new XmlClass(class1);
			List<XmlClass> xmlClassList1 = new ArrayList<XmlClass>();
			xmlClassList1.add(myClass);
			xmlTest1.setXmlClasses(xmlClassList1);
		}
		}
		suites.add(xmlSuite);
		return suites;
	}
	@Test
	public void xmlsuite() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String[] classes=excelread.excelDataProviderArray(FileConstants.CLASS_NAME_PATH, 0, "classname");
		List<XmlSuite> suites = suiteXml(classes);
		TestNG testng = new TestNG();
		testng.setXmlSuites(suites);
		testng.run();
	}

}
