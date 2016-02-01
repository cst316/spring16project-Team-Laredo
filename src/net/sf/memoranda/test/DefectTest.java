package net.sf.memoranda.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.FileStorage;
import net.sf.memoranda.util.Util;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.DefectListImpl;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectImpl;
import nu.xom.Attribute;
import nu.xom.Element;

public class DefectTest {
	static Project project;
	static DefectList defectlist;
	static FileStorage filestorage;
	static String JN_DOCPATH = Util.getEnvDir();
	static CalendarDate dateFound;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Element el = new Element("Test");
		el.addAttribute(new Attribute("id", "Test"));
	    project = new ProjectImpl(el);
	    defectlist = new DefectListImpl(project);
	    filestorage = new FileStorage();
	    filestorage.createProjectStorage(project);
	    dateFound = new CalendarDate();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	    defectlist.removeDefect(1);
	    defectlist.removeDefect(2);
	    defectlist.removeDefect(3);
	}
	
	@Test
	public void testFileCreation() {
		defectlist.addDefect(dateFound, dateFound, 0, 0, 0, "Testing");
		defectlist.addDefect(dateFound, dateFound, 0, 0, 0, "Testing");
		defectlist.addDefect(dateFound, dateFound, 1, 1, 1, "Testing");
		defectlist.addDefect(dateFound, dateFound, 1, 1, 1, "Testing");
		filestorage.storeDefectList(defectlist, project);
		File file = new File(JN_DOCPATH + "Test/.defectlist");
		assertTrue(file.exists());
	}
	
	@Test
	public void testContentOfFile(){
		DefectList testDefectList = filestorage.openDefectList(project);
		assertTrue(dateFound.equals(testDefectList.getDefect(1).getDateFound()));
		assertTrue(2 == testDefectList.getDefect(2).getDefectNumber());
		testDefectList.removeDefect(2);
		assertTrue(testDefectList.getDefect(3).getPhaseOfInjection() == 1);
		System.out.println(testDefectList.getDefect(3));
	}

}
