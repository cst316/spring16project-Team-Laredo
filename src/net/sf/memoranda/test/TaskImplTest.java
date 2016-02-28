package net.sf.memoranda.test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
public class TaskImplTest {

    private static final String TEST_PROJECT_TITLE = "testProjectAAAAAAAAAAAAAAAAAA";
    private static Project project = null;
    private static TaskList tasks = null;

    @Before
    public void setUp() throws Exception {
        ProjectManager.removeProject(TEST_PROJECT_TITLE);
        project = ProjectManager.createProject(TEST_PROJECT_TITLE, new CalendarDate(), new CalendarDate());
        tasks = CurrentStorage.get().openTaskList(project);
    }

    @After
    public void tearDown() throws Exception {
        ProjectManager.removeProject(TEST_PROJECT_TITLE);
    }

    @Test
    public void testLocStorage() throws Exception {
        Task task = tasks.createTask(new CalendarDate(), new CalendarDate(), "", 1, 1, 1, "", null);
        assertEquals(0, task.getLocBase());
        task.setLocBase(10);
        assertEquals(10, task.getLocBase());

        assertEquals(0, task.getLocAdded());
        task.setLocAdded(10);
        assertEquals(10, task.getLocAdded());

        assertEquals(0, task.getLocModified());
        task.setLocModified(10);
        assertEquals(10, task.getLocModified());

        assertEquals(0, task.getLocDeleted());
        task.setLocDeleted(10);
        assertEquals(10, task.getLocDeleted());

        assertEquals(0, task.getLocNewAndChanged());
        task.setLocNewAndChanged(10);
        assertEquals(10, task.getLocNewAndChanged());

        assertEquals(0, task.getLocReused());
        task.setLocReused(10);
        assertEquals(10, task.getLocReused());

        assertEquals(0, task.getLocNewReuse());
        task.setLocNewReuse(10);
        assertEquals(10, task.getLocNewReuse());

        assertEquals(0, task.getLocTotal());
        task.setLocTotal(10);
        assertEquals(10, task.getLocTotal());
    }
}