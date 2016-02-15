package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class StatisticsTest {

    public static Project project = null;
    public static TaskList tasks = null;
    public static DefectList defects = null;
    public static Statistics statistics = null;

    @Before
    public void setUp() throws Exception {
        project = ProjectManager.createProject("testproject", new CalendarDate(), CalendarDate.tomorrow());
        CurrentProject.set(project);
        tasks = CurrentProject.getTaskList();
        defects = CurrentProject.getDefectList();

        tasks.createTask(new CalendarDate(), CalendarDate.tomorrow(), "gfdsarewc", 1, Task.COMPILE, 10, "kjkjfg", null);

        defects.addDefect(new CalendarDate(), new CalendarDate(), Defect.NO_PHASE, Defect.NO_PHASE, Defect.DOCUMENTATION, "testdefect");

        CurrentProject.save();
        statistics = new Statistics(project);
    }

    @After
    public void tearDown() throws Exception {
        ProjectManager.removeProject(project.getID());
        project = null;
        tasks = null;
        defects = null;
        statistics = null;
    }

    @Test
    public void testDefects() throws Exception {
        assertEquals(3, statistics.getNumDefects());
    }

    @Test
    public void testTasks() throws Exception {
        assertEquals(1, statistics.getNumTasks());
        assertEquals(0, statistics.getTotalTasksEffortActual());
        assertEquals(10, statistics.getTotalTasksEffortEstimated());
    }
}