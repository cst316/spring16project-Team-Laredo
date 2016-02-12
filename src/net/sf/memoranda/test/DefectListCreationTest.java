package net.sf.memoranda.test;

import static org.junit.Assert.*;

import java.io.File;
import org.junit.BeforeClass;
import org.junit.Test;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.DefectListImpl;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.FileStorage;
import net.sf.memoranda.util.Util;
/**
 * Test defect, defect list and File storage for defects.
 * 
 * @author Benjamin Paothatat
 * @since 2/9/2016
 */
public class DefectListCreationTest {
	static CalendarDate today;
    static CalendarDate tomorrow;
    static CalendarDate dayAfterTomorrow;
    static FileStorage fileStorage;
	static String JN_DOCPATH = Util.getEnvDir();

    static Project firstProject;
    static DefectList firstDefectList;
    
    static Project secondProject;
    static DefectList secondDefectList;
   	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		today = CalendarDate.today();
		tomorrow = CalendarDate.tomorrow();
		dayAfterTomorrow = CalendarDate.tomorrow().tomorrow();
		
		ProjectManager.createProject("FirstTestProject", "FirstTestProject", today, dayAfterTomorrow);
		firstProject = ProjectManager.getProject("FirstTestProject");
		firstDefectList = new DefectListImpl(firstProject);
		firstDefectList.addDefect(today, today, 0, 0, 0, "First Defect");
		firstDefectList.addDefect(today, tomorrow, 3, 4, 5, "Second Defect");
		firstDefectList.addDefect(tomorrow, dayAfterTomorrow, 6, 6, 10, "Third Defect");
		
		ProjectManager.createProject("SecondTestProject", "SecondTestProject", today, dayAfterTomorrow);
		secondProject = ProjectManager.getProject("SecondTestProject");
		secondDefectList = new DefectListImpl(secondProject);
		secondDefectList.addDefect(today, today, 1, 2, 3, "Second Defect List: First Defect");
		
		fileStorage = new FileStorage();
		fileStorage.createProjectStorage(firstProject);
		fileStorage.storeProjectManager();
	}

	@Test
	public void testFileCreation(){
		fileStorage.storeDefectList(firstDefectList, firstProject);
		File firstFileName = new File(JN_DOCPATH + firstProject.getID() + File.separator + ".defectlist");
		assertTrue(firstFileName.exists());
		
		fileStorage.storeDefectList(secondDefectList, secondProject);
		File secondFileName = new File(JN_DOCPATH + secondProject.getID() + File.separator + ".defectlist");
		assertTrue(secondFileName.exists());
	}
	
	
    @Test
    public void testContentOfDefectList(){
    	assertTrue(firstDefectList.getDefect(1).getDateFound().equals(today));
    	assertTrue(firstDefectList.getDefect(1).getDateRemoved().equals(today));
    	assertTrue(firstDefectList.getDefect(1).getPhaseOfInjection() == 0);
    	assertTrue(firstDefectList.getDefect(1).getPhaseOfRemoval() == 0);
    	assertTrue(firstDefectList.getDefect(1).getTypeOfDefect() == 0);
    	assertTrue(firstDefectList.getDefect(1).getDescription().equals("First Defect"));
    	
    	assertTrue(firstDefectList.getDefect(2).getDateFound().equals(today));
    	assertTrue(firstDefectList.getDefect(2).getDateRemoved().equals(tomorrow));
    	assertTrue(firstDefectList.getDefect(2).getPhaseOfInjection() == 3);
    	assertTrue(firstDefectList.getDefect(2).getPhaseOfRemoval() == 4);
    	assertTrue(firstDefectList.getDefect(2).getTypeOfDefect() == 5);
    	assertTrue(firstDefectList.getDefect(2).getDescription().equals("Second Defect"));
    	
    	assertTrue(firstDefectList.getDefect(3).getDateFound().equals(tomorrow));
    	assertTrue(firstDefectList.getDefect(3).getDateRemoved().equals(dayAfterTomorrow));
    	assertTrue(firstDefectList.getDefect(3).getPhaseOfInjection() == 6);
    	assertTrue(firstDefectList.getDefect(3).getPhaseOfRemoval() == 6);
    	assertTrue(firstDefectList.getDefect(3).getTypeOfDefect() == 10);
    	assertTrue(firstDefectList.getDefect(3).getDescription().equals("Third Defect"));
    	
    	assertTrue(secondDefectList.getDefect(1).getDateFound().equals(today));
    	assertTrue(secondDefectList.getDefect(1).getDateRemoved().equals(today));
    	assertTrue(secondDefectList.getDefect(1).getPhaseOfInjection() == 1);
    	assertTrue(secondDefectList.getDefect(1).getPhaseOfRemoval() == 2);
    	assertTrue(secondDefectList.getDefect(1).getTypeOfDefect() == 3);
    	assertTrue(secondDefectList.getDefect(1).getDescription().equals("Second Defect List: First Defect"));
    	
    }
	
	@Test
	public void testRemovalOfDefects(){
		firstDefectList.removeDefect(2);
		assertTrue(firstDefectList.getNumberOfDefects() == 2);
		fileStorage.storeDefectList(firstDefectList, firstProject);
		
		assertTrue(firstDefectList.getDefect(1).getDateFound().equals(today));
    	assertTrue(firstDefectList.getDefect(1).getDateRemoved().equals(today));
    	assertTrue(firstDefectList.getDefect(1).getPhaseOfInjection() == 0);
    	assertTrue(firstDefectList.getDefect(1).getPhaseOfRemoval() == 0);
    	assertTrue(firstDefectList.getDefect(1).getTypeOfDefect() == 0);
    	assertTrue(firstDefectList.getDefect(1).getDescription().equals("First Defect"));
    	
    	assertTrue(firstDefectList.getDefect(2).getDateFound().equals(tomorrow));
    	assertTrue(firstDefectList.getDefect(2).getDateRemoved().equals(dayAfterTomorrow));
    	assertTrue(firstDefectList.getDefect(2).getPhaseOfInjection() == 6);
    	assertTrue(firstDefectList.getDefect(2).getPhaseOfRemoval() == 6);
    	assertTrue(firstDefectList.getDefect(2).getTypeOfDefect() == 10);
    	assertTrue(firstDefectList.getDefect(2).getDescription().equals("Third Defect"));
    	
    	secondDefectList.removeDefect(1);
    	assertTrue(secondDefectList.getNumberOfDefects() == 0);
		fileStorage.storeDefectList(secondDefectList, secondProject);
	}
}
