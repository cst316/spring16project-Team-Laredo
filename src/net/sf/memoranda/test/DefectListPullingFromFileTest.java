package net.sf.memoranda.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.DefectList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.FileStorage;
import net.sf.memoranda.util.Util;

public class DefectListPullingFromFileTest {
	static CalendarDate today;
    static CalendarDate tomorrow;
    static CalendarDate dayAfterTomorrow;
    static FileStorage fileStorage;
	static String JN_DOCPATH = Util.getEnvDir();

    static Project pulledFirstProject;
    static DefectList pulledFirstDefectList;
    
    static Project pulledSecondProject;
    static DefectList pulledSecondDefectList;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		today = new CalendarDate().today();
		tomorrow = new CalendarDate().tomorrow();
		dayAfterTomorrow = new CalendarDate().tomorrow().tomorrow();
		
		fileStorage = new FileStorage();
		pulledFirstProject = ProjectManager.getProject("FirstTestProject");
		pulledFirstDefectList = fileStorage.openDefectList(pulledFirstProject);
		
		pulledSecondProject = ProjectManager.getProject("SecondTestProject");
		pulledSecondDefectList = fileStorage.openDefectList(pulledSecondProject);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ProjectManager.removeProject("FirstTestProject");
		ProjectManager.removeProject("SecondTestProject");
	}
	
	@Test
	public void testPullingFromFile() {
		assertTrue(pulledFirstDefectList.getDefect(1).getDateFound().equals(today));
    	assertTrue(pulledFirstDefectList.getDefect(1).getDateRemoved().equals(today));
    	assertTrue(pulledFirstDefectList.getDefect(1).getPhaseOfInjection() == 0);
    	assertTrue(pulledFirstDefectList.getDefect(1).getPhaseOfRemoval() == 0);
    	assertTrue(pulledFirstDefectList.getDefect(1).getTypeOfDefect() == 0);
    	assertTrue(pulledFirstDefectList.getDefect(1).getDescription().equals("First Defect"));
    	
    	assertTrue(pulledFirstDefectList.getDefect(2).getDateFound().equals(tomorrow));
    	assertTrue(pulledFirstDefectList.getDefect(2).getDateRemoved().equals(dayAfterTomorrow));
    	assertTrue(pulledFirstDefectList.getDefect(2).getPhaseOfInjection() == 6);
    	assertTrue(pulledFirstDefectList.getDefect(2).getPhaseOfRemoval() == 6);
    	assertTrue(pulledFirstDefectList.getDefect(2).getTypeOfDefect() == 10);
    	assertTrue(pulledFirstDefectList.getDefect(2).getDescription().equals("Third Defect"));
    	
    	assertTrue(pulledSecondDefectList.getNumberOfDefects() == 0);
	}

}
