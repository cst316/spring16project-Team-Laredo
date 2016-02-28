package net.sf.memoranda.test;

import net.sf.memoranda.DefectList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.FileStorage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefectListPullingFromFileTest {
    private static CalendarDate today;
    private static CalendarDate tomorrow;
    private static FileStorage fileStorage;

    private static DefectList pulledFirstDefectList;

    private static DefectList pulledSecondDefectList;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        today = CalendarDate.today();
        tomorrow = CalendarDate.tomorrow();

        fileStorage = new FileStorage();
        Project pulledFirstProject = ProjectManager.getProject("FirstTestProject");
        pulledFirstDefectList = fileStorage.openDefectList(pulledFirstProject);

        Project pulledSecondProject = ProjectManager.getProject("SecondTestProject");
        pulledSecondDefectList = fileStorage.openDefectList(pulledSecondProject);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        ProjectManager.removeProject("FirstTestProject");
        ProjectManager.removeProject("SecondTestProject");
        fileStorage.storeProjectManager();
    }

    @Test
    public void testPullingFromFile() {
        assertEquals(today, pulledFirstDefectList.getDefect(1).getDateFound());
        assertEquals(tomorrow, pulledFirstDefectList.getDefect(1).getDateRemoved());
        assertEquals(3, pulledFirstDefectList.getDefect(1).getPhaseOfInjection());
        assertEquals(4, pulledFirstDefectList.getDefect(1).getPhaseOfRemoval());
        assertEquals(5, pulledFirstDefectList.getDefect(1).getTypeOfDefect());
        assertEquals("Second Defect", pulledFirstDefectList.getDefect(1).getDescription());
        assertEquals(1, pulledSecondDefectList.getNumberOfDefects());
    }

}
