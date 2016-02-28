package net.sf.memoranda.test;

import net.sf.memoranda.DefectList;
import net.sf.memoranda.DefectListImpl;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.FileStorage;
import net.sf.memoranda.util.Util;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test defect, defect list and File storage for defects.
 *
 * @author Benjamin Paothatat
 * @since 2/9/2016
 */
public class DefectListCreationTest {
    private static final String JN_DOCPATH = Util.getEnvDir();
    private static CalendarDate today;
    private static CalendarDate tomorrow;
    private static CalendarDate dayAfterTomorrow;
    private static FileStorage fileStorage;
    private static Project firstProject;
    private static DefectList firstDefectList;

    private static Project secondProject;
    private static DefectList secondDefectList;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        today = CalendarDate.today();
        tomorrow = CalendarDate.tomorrow();
        dayAfterTomorrow = CalendarDate.tomorrow();

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
    public void testFileCreation() {
        fileStorage.storeDefectList(firstDefectList, firstProject);
        File firstFileName = new File(JN_DOCPATH + firstProject.getID() + File.separator + ".defectlist");
        assertTrue(firstFileName.exists());

        fileStorage.storeDefectList(secondDefectList, secondProject);
        File secondFileName = new File(JN_DOCPATH + secondProject.getID() + File.separator + ".defectlist");
        assertTrue(secondFileName.exists());
    }


    @Test
    public void testContentOfDefectList() {
        assertEquals(today, firstDefectList.getDefect(0).getDateFound());
        assertEquals(today, firstDefectList.getDefect(0).getDateRemoved());
        assertEquals(0, firstDefectList.getDefect(0).getPhaseOfInjection());
        assertEquals(0, firstDefectList.getDefect(0).getPhaseOfRemoval());
        assertEquals(0, firstDefectList.getDefect(0).getTypeOfDefect());
        assertEquals("First Defect", firstDefectList.getDefect(0).getDescription());

        assertEquals(today, firstDefectList.getDefect(1).getDateFound());
        assertEquals(tomorrow, firstDefectList.getDefect(1).getDateRemoved());
        assertEquals(3, firstDefectList.getDefect(1).getPhaseOfInjection());
        assertEquals(4, firstDefectList.getDefect(1).getPhaseOfRemoval());
        assertEquals(5, firstDefectList.getDefect(1).getTypeOfDefect());
        assertEquals("Second Defect", firstDefectList.getDefect(1).getDescription());

        assertEquals(tomorrow, firstDefectList.getDefect(2).getDateFound());
        assertEquals(dayAfterTomorrow, firstDefectList.getDefect(2).getDateRemoved());
        assertEquals(6, firstDefectList.getDefect(2).getPhaseOfInjection());
        assertEquals(6, firstDefectList.getDefect(2).getPhaseOfRemoval());
        assertEquals(10, firstDefectList.getDefect(2).getTypeOfDefect());
        assertEquals("Third Defect", firstDefectList.getDefect(2).getDescription());

        assertEquals(today, secondDefectList.getDefect(0).getDateFound());
        assertEquals(today, secondDefectList.getDefect(0).getDateRemoved());
        assertEquals(1, secondDefectList.getDefect(0).getPhaseOfInjection());
        assertEquals(2, secondDefectList.getDefect(0).getPhaseOfRemoval());
        assertEquals(3, secondDefectList.getDefect(0).getTypeOfDefect());
        assertEquals("Second Defect List: First Defect", secondDefectList.getDefect(0).getDescription());

    }

    @Test
    public void testRemovalOfDefects() {
        firstDefectList.removeDefect(2);
        assertEquals(2, firstDefectList.getNumberOfDefects());
        fileStorage.storeDefectList(firstDefectList, firstProject);

        assertEquals(today, firstDefectList.getDefect(1).getDateFound());
        assertEquals(tomorrow, firstDefectList.getDefect(1).getDateRemoved());
        assertEquals(3, firstDefectList.getDefect(1).getPhaseOfInjection());
        assertEquals(4, firstDefectList.getDefect(1).getPhaseOfRemoval());
        assertEquals(5, firstDefectList.getDefect(1).getTypeOfDefect());
        assertEquals("Second Defect", firstDefectList.getDefect(1).getDescription());

        secondDefectList.removeDefect(1);
        assertEquals(1, secondDefectList.getNumberOfDefects());
        fileStorage.storeDefectList(secondDefectList, secondProject);
    }
}
