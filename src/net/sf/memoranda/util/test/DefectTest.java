package net.sf.memoranda.util.test;

import static org.junit.Assert.*;

import java.io.File;

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
	
	@Test
	public void testFileCreation() {
		defectlist.addDefect(dateFound, dateFound, "Planning", "Testing", "Function", "Testing");
		defectlist.addDefect(dateFound, dateFound, "Planning", "Testing", "Function", "Testing");
		filestorage.storeDefectList(defectlist, project);
		File file = new File(JN_DOCPATH + "Test/.defectlist");
		assertTrue(file.exists());
	}
	
	@Test
	public void testContentOfFile(){
		DefectList testDefectList = filestorage.openDefectList(project);
		assertTrue(dateFound.equals(testDefectList.getDefect(1).getDateFound()));
		assertTrue(2 == (testDefectList).getDefect(2).getDefectNumber());
	}

}
