package org.atmecs.website.testscripts;

import java.util.Collections;
import java.util.List;

import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class TestNGModifier implements IAlterSuiteListener{

	  @Override
	    public void alter(List<XmlSuite> suites) {
		  System.out.println(suites.size());
	        XmlSuite suite = suites.get(0);
	        XmlTest xmlTest = new XmlTest(suite);
	        xmlTest.setName("CommandLine_Test");
	        String packages = System.getProperty("package", suite.getParameter("package"));
	        XmlPackage xmlPackage = new XmlPackage(packages);
	        xmlTest.setXmlPackages(Collections.singletonList(xmlPackage));
	    }
}
