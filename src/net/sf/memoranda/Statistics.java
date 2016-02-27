package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

import java.util.*;

/**
 * An aggregator for read-only statistics of various PSP attributes.
 *
 * @author Adam Perry
 * @since 02/13/2016
 */
public class Statistics {

    private final int numDefects;
    private int numEvents;
    private int totalEventDurationMinutes;
    //PSP Phases
    private int numPlanningPhaseDefectsInjected;
    private int numDesignPhaseDefectsInjected;
    private int numCodePhaseDefectsInjected;
    private int numReviewPhaseDefectsInjected;
    private int numCompilePhaseDefectsInjected;
    private int numTestingPhaseDefectsInjected;
    private int numNoPhaseDefectsInjected;

    private int numPlanningPhaseDefectsRemoved;
    private int numDesignPhaseDefectsRemoved;
    private int numCodePhaseDefectsRemoved;
    private int numReviewPhaseDefectsRemoved;
    private int numCompilePhaseDefectsRemoved;
    private int numTestingPhaseDefectsRemoved;
    private int numNoPhaseDefectsRemoved;

    // PSP defect types
    private int numDocumentationDefects;
    private int numSyntaxDefects;
    private int numBuildDefects;
    private int numAssignmentDefects;
    private int numInterfaceDefects;
    private int numCheckingDefects;
    private int numDataDefects;
    private int numFunctionDefects;
    private int numSystemDefects;
    private int numEnvironmentDefects;
    private int numMiscDefects;

    private int numTasks;
    private int totalTasksEffortActual;
    private int totalTasksEffortEstimated;

    /**
     * Calculate a new set of statistics for the project.
     *
     * @param project The project for which the statistics are created.
     */
    public Statistics(Project project) {

        DefectList defects = CurrentProject.getDefectList();
        TaskList tasks = CurrentProject.getTaskList();

        CalendarDate projectStartDate = project.getStartDate();
        CalendarDate projectEndDate = project.getEndDate();
        List<Date> projectDates = getDaysBetweenDates(projectStartDate.getDate(), projectEndDate.getDate());

        for (Date d : projectDates) {
            Collection<Event> events = EventsManager.getEventsForDate(new CalendarDate(d));

            numEvents += events.size();

            for (Event e : events) {
                totalEventDurationMinutes += e.getDurationMinutes();
            }
        }

        numDefects = defects.getNumberOfDefects();
        for (Defect defect : defects.getAllDefects()) {

            switch (defect.getPhaseOfInjection()) {
                case Defect.PLANNING:
                    numPlanningPhaseDefectsInjected += 1;
                    break;
                case Defect.DESIGN:
                    numDesignPhaseDefectsInjected += 1;
                    break;
                case Defect.CODE:
                    numCodePhaseDefectsInjected += 1;
                    break;
                case Defect.REVIEW:
                    numReviewPhaseDefectsInjected += 1;
                    break;
                case Defect.COMPILE:
                    numCompilePhaseDefectsInjected += 1;
                    break;
                case Defect.TESTING:
                    numTestingPhaseDefectsInjected += 1;
                    break;
                default:
                    numNoPhaseDefectsInjected += 1;
                    break;
            }

            switch (defect.getPhaseOfRemoval()) {
                case Defect.PLANNING:
                    numPlanningPhaseDefectsRemoved += 1;
                    break;
                case Defect.DESIGN:
                    numDesignPhaseDefectsRemoved += 1;
                    break;
                case Defect.CODE:
                    numCodePhaseDefectsRemoved += 1;
                    break;
                case Defect.REVIEW:
                    numReviewPhaseDefectsRemoved += 1;
                    break;
                case Defect.COMPILE:
                    numCompilePhaseDefectsRemoved += 1;
                    break;
                case Defect.TESTING:
                    numTestingPhaseDefectsRemoved += 1;
                    break;
                default:
                    numNoPhaseDefectsRemoved += 1;
                    break;
            }

            switch (defect.getTypeOfDefect()) {
                case Defect.DOCUMENTATION:
                    numDocumentationDefects += 1;
                    break;
                case Defect.SYNTAX:
                    numSyntaxDefects += 1;
                    break;
                case Defect.BUILD:
                    numBuildDefects += 1;
                    break;
                case Defect.ASSIGNMENT:
                    numAssignmentDefects += 1;
                    break;
                case Defect.INTERFACE:
                    numInterfaceDefects += 1;
                    break;
                case Defect.CHECKING:
                    numCheckingDefects += 1;
                    break;
                case Defect.DATA:
                    numDataDefects += 1;
                    break;
                case Defect.FUNCTION:
                    numFunctionDefects += 1;
                    break;
                case Defect.SYSTEM:
                    numSystemDefects += 1;
                    break;
                case Defect.ENVIROMENT:
                    numEnvironmentDefects += 1;
                    break;
                case Defect.NON_TYPE:
                    numMiscDefects += 1;
                    break;
            }

        }

        for (Task t : tasks.getTopLevelTasks()) {
            System.out.println(t);
            numTasks++;

            totalTasksEffortActual += t.getActEffort();
            totalTasksEffortEstimated += t.getEstEffort();
        }

    }

    private static List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public int getNumEvents() {
        return numEvents;
    }

    public int getTotalEventDurationMinutes() {
        return totalEventDurationMinutes;
    }

    public int getNumDefects() {
        return numDefects;
    }

    public int getNumPlanningPhaseDefectsInjected() {
        return numPlanningPhaseDefectsInjected;
    }

    public int getNumDesignPhaseDefectsInjected() {
        return numDesignPhaseDefectsInjected;
    }

    public int getNumCodePhaseDefectsInjected() {
        return numCodePhaseDefectsInjected;
    }

    public int getNumReviewPhaseDefectsInjected() {
        return numReviewPhaseDefectsInjected;
    }

    public int getNumCompilePhaseDefectsInjected() {
        return numCompilePhaseDefectsInjected;
    }

    public int getNumTestingPhaseDefectsInjected() {
        return numTestingPhaseDefectsInjected;
    }

    public int getNumNoPhaseDefectsInjected() {
        return numNoPhaseDefectsInjected;
    }

    public int getNumPlanningPhaseDefectsRemoved() {
        return numPlanningPhaseDefectsRemoved;
    }

    public int getNumDesignPhaseDefectsRemoved() {
        return numDesignPhaseDefectsRemoved;
    }

    public int getNumCodePhaseDefectsRemoved() {
        return numCodePhaseDefectsRemoved;
    }

    public int getNumReviewPhaseDefectsRemoved() {
        return numReviewPhaseDefectsRemoved;
    }

    public int getNumCompilePhaseDefectsRemoved() {
        return numCompilePhaseDefectsRemoved;
    }

    public int getNumTestingPhaseDefectsRemoved() {
        return numTestingPhaseDefectsRemoved;
    }

    public int getNumNoPhaseDefectsRemoved() {
        return numNoPhaseDefectsRemoved;
    }

    public int getNumDocumentationDefects() {
        return numDocumentationDefects;
    }

    public int getNumSyntaxDefects() {
        return numSyntaxDefects;
    }

    public int getNumBuildDefects() {
        return numBuildDefects;
    }

    public int getNumAssignmentDefects() {
        return numAssignmentDefects;
    }

    public int getNumInterfaceDefects() {
        return numInterfaceDefects;
    }

    public int getNumCheckingDefects() {
        return numCheckingDefects;
    }

    public int getNumDataDefects() {
        return numDataDefects;
    }

    public int getNumFunctionDefects() {
        return numFunctionDefects;
    }

    public int getNumSystemDefects() {
        return numSystemDefects;
    }

    public int getNumEnvironmentDefects() {
        return numEnvironmentDefects;
    }

    public int getNumMiscDefects() {
        return numMiscDefects;
    }

    public int getNumTasks() {
        return numTasks;
    }

    public int getTotalTasksEffortActual() {
        return totalTasksEffortActual;
    }

    public int getTotalTasksEffortEstimated() {
        return totalTasksEffortEstimated;
    }
}
