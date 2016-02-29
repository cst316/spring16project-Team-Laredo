/*
 * AgendaGenerator.java Package: net.sf.memoranda.util Created on 13.01.2004
 * 5:52:54 @author Alex
 */
package net.sf.memoranda.util;

import net.sf.memoranda.*;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.AppFrame;
import nu.xom.Element;

import java.util.*;

/**
 *
 */

/*$Id: AgendaGenerator.java,v 1.12 2005/06/13 21:25:27 velhonoja Exp $*/

public class AgendaGenerator {

    private static final String HEADER =
            "<html><head><title></title>\n"
                    + "<style>\n"
                    + "    body, td {font: 12pt sans-serif}\n"
                    + "    h1 {font:20pt sans-serif; background-color:#E0E0E0; margin-top:0}\n"
                    + "    h2 {font:16pt sans-serif; margin-bottom:0}\n"
                    + "    li {margin-bottom:5px}\n"
                    + " a {color:black; text-decoration:none}\n"
                    + "</style></head>\n"
                    + "<body><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n"
                    + "<tr>\n";
    private static final String FOOTER = "</td></tr></table></body></html>";

    private static String generateTasksInfo(Project p, CalendarDate date, Collection expandedTasks) {
        TaskList tl;
        if (p.getID().equals(CurrentProject.get().getID())) {
            tl = CurrentProject.getTaskList();
        } else {
            tl = CurrentStorage.get().openTaskList(p);
        }
        String s = "";
        int k = getProgress(tl);
        if (k > -1) {
            s += "<br>" + Local.getString("Total progress") + ": " + k + "%";
        }
        s += "</td></tr></table>\n";

        Vector tasks = (Vector) tl.getActiveSubTasks(null, date);
        if (tasks.size() == 0) {
            s += "<p>" + Local.getString("No actual tasks") + ".</p>\n";
        } else {
            s += Local.getString("Actual tasks") + ":<br>\n<ul>\n";

            Collections.sort(tasks);
            for (Object task : tasks) {
                Task t = (Task) task;
                // Always show active tasks only on agenda page from now on.
                // If it's not active, then it's probably "not on the agenda" isn't it?
                //        		if(Context.get("SHOW_ACTIVE_TASKS_ONLY").equals(new Boolean(true))) {
                //                    if (!((t.getStatus() == Task.ACTIVE) || (t.getStatus() == Task.DEADLINE) || (t.getStatus() == Task.FAILED))) {
                //                    	continue;
                //                	}
                //        		}
                // ignore if it's a sub-task, iterate over ROOT tasks here only
                if (tl.hasParentTask(t.getID())) {
                    continue;
                }

                s = s + renderTask(p, date, tl, t, expandedTasks);
                if (expandedTasks.contains(t.getID())) {
                    s = s + expandRecursively(p, date, tl, t, expandedTasks, 1);
                }
            }
            s += "\n</ul>\n";
        }

        return s;
    }

    private static String expandRecursively(Project p, CalendarDate date, TaskList tl, Task t, Collection expandedTasks, int level) {
        Util.debug("Expanding task " + t.getText() + " level " + level);

        Collection st = tl.getActiveSubTasks(t.getID(), date);

        Util.debug("number of subtasks " + st.size());

        String s = "\n<ul>\n";

        for (Object aSt : st) {
            Task subTask = (Task) aSt;
            s = s + renderTask(p, date, tl, subTask, expandedTasks);
            if (expandedTasks.contains(subTask.getID())) {
                s = s + expandRecursively(p, date, tl, subTask, expandedTasks, level + 1);
            }
        }
        s += "\n</ul>\n";

        return s;
    }

    private static String renderTask(Project p, CalendarDate date, TaskList tl, Task t, Collection expandedTasks) {
        String s = "";

        int pg = t.getProgress();
        String progress;
        if (pg == 100)
            progress = "<font color=\"green\">" + Local.getString("Completed") + "</font>";
        else
            progress = pg + Local.getString("% done");

        String subTaskOperation = "";
        if (tl.hasSubTasks(t.getID())) {

            if (expandedTasks.contains(t.getID())) {

                subTaskOperation = "<a href=\"memoranda:closesubtasks#" + t.getID() + "\">(-)</a>";
            } else {

                subTaskOperation = "<a href=\"memoranda:expandsubtasks#" + t.getID() + "\">(+)</a>";
            }
        }

        s += "<a name=\"" + t.getID() + "\"><li><p>" + subTaskOperation + "<a href=\"memoranda:tasks#"
                + p.getID()
                + "\"><b>"
                + t.getText()
                + "</b></a> : "
                + progress
                + "</p>"
                + "<p>"
                + Local.getString("Priority")
                + ": "
                + getPriorityString(t.getPriority())
                + "</p>";
        if (t.getEndDate().equals(date))
            s += "<p><font color=\"#FF9900\"><b>"
                    + Local.getString("Should be done today")
                    + ".</b></font></p>";
        else {
            Calendar endDateCal = t.getEndDate().getCalendar();
            Calendar dateCal = date.getCalendar();
            int numOfDays = (endDateCal.get(Calendar.YEAR) * 365 + endDateCal.get(Calendar.DAY_OF_YEAR)) -
                    (dateCal.get(Calendar.YEAR) * 365 + dateCal.get(Calendar.DAY_OF_YEAR));
            String days = "";
            if (numOfDays > 0) {
                if (numOfDays > 1) {
                    days = Local.getString("in") + " " + numOfDays + " " + Local.getString("day(s)");
                } else {
                    days = Local.getString("tomorrow");
                }
                s += "<p>"
                        + Local.getString("Deadline")
                        + ": <i>"
                        + t.getEndDate().getMediumDateString()
                        + "</i> (" + days + ")</p>";
            } else if ((numOfDays < 0) && (numOfDays > -10000)) {
                String overdueDays = String.valueOf(-1 * numOfDays);
                s += "<p><font color=\"#FF9900\"><b>"
                        + overdueDays + " "
                        + Local.getString("days overdue")
                        + ".</b></font></p>";
            } else {
                // tasks that have no deadline
                s += "<p>"
                        + Local.getString("No Deadline")
                        + "</p>";
            }
        }
        //>>>>>>> 1.4
        s += "</li>\n";
        return s;
    }

    private static int getProgress(TaskList tl) {
        Vector v = (Vector) tl.getAllSubTasks(null);
        if (v.size() == 0)
            return -1;
        int p = 0;
        for (Enumeration en = v.elements(); en.hasMoreElements(); ) {
            Task t = (Task) en.nextElement();
            p += t.getProgress();
        }
        return (p * 100) / (v.size() * 100);
    }

    private static String getPriorityString(int p) {
        switch (p) {
            case Task.PRIORITY_NORMAL:
                return "<font color=\"green\">" + Local.getString("Normal") + "</font>";
            case Task.PRIORITY_LOW:
                return "<font color=\"#3333CC\">" + Local.getString("Low") + "</font>";
            case Task.PRIORITY_LOWEST:
                return "<font color=\"#666699\">" + Local.getString("Lowest") + "</font>";
            case Task.PRIORITY_HIGH:
                return "<font color=\"#FF9900\">" + Local.getString("High") + "</font>";
            case Task.PRIORITY_HIGHEST:
                return "<font color=\"red\">" + Local.getString("Highest") + "</font>";
        }
        return "";
    }

    private static String generateProjectInfo(Project p, CalendarDate date, Collection expandedTasks) {
        String s = "<h2><a href=\"memoranda:project#"
                + p.getID()
                + "\">"
                + p.getTitle()
                + "</a></h2>\n"
                + "<table border=\"0\" width=\"100%\" cellpadding=\"2\" bgcolor=\"#EFEFEF\"><tr><td>"
                + Local.getString("Start date") + ": <i>" + p.getStartDate().getMediumDateString() + "</i>\n";
        if (p.getEndDate() != null)
            s += "<br>" + Local.getString("End date") + ": <i>" + p.getEndDate().getMediumDateString()
                    + "</i>\n";
        return s + generateTasksInfo(p, date, expandedTasks);
    }

    private static String generateAllProjectsInfo(CalendarDate date, Collection expandedTasks) {
        String s =
                "<td width=\"66%\" valign=\"top\">"
                        + "<h1>"
                        + Local.getString("Projects and tasks")
                        + "</h1>\n";
        s += generateProjectInfo(CurrentProject.get(), date, expandedTasks);
        for (Object o : ProjectManager.getActiveProjects()) {
            Project p = (Project) o;
            if (!p.getID().equals(CurrentProject.get().getID()))
                s += generateProjectInfo(p, date, expandedTasks);
        }
        return s + "</td>";
    }

    private static String generateEventsInfo(CalendarDate date) {
        String s =
                "<td width=\"34%\" valign=\"top\">"
                        + "<a href=\"memoranda:events\"><h1>"
                        + Local.getString("Events")
                        + "</h1></a>\n"
                        + "<table width=\"100%\" valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFF6\">\n";
        Vector v = (Vector) EventsManager.getEventsForDate(date);
        int n = 0;
        for (Object aV : v) {
            Event e = (Event) aV;
            String txt = e.getText();
            String iurl =
                    AppFrame
                            .class
                            .getResource("resources/agenda/spacer.gif")
                            .toExternalForm();
            if (date.equals(CalendarDate.today())) {
                if (e.getTime().after(new Date()))
                    txt = "<b>" + txt + "</b>";
                if ((EventsScheduler.isEventScheduled())
                        && (EventsScheduler
                        .getFirstScheduledEvent()
                        .getTime()
                        .equals(e.getTime()))) {
                    iurl =
                            AppFrame
                                    .class
                                    .getResource("resources/agenda/arrow.gif")
                                    .toExternalForm();
                }
            }
            String icon =
                    "<img align=\"right\" width=\"16\" height=\"16\" src=\""
                            + iurl
                            + "\" border=\"0\"  hspace=\"0\" vspace=\"0\" alt=\"\">";

            s += "<tr>\n<td>"
                    + icon
                    + "</td>"
                    + "<td nowrap class=\"eventtime\">"
                    + e.getTimeString()
                    + "</td>"
                    + "<td width=\"100%\" class=\"eventtext\">&nbsp;&nbsp;"
                    + txt
                    + "</td>\n"
                    + "</tr>";

        }
        return s + "</table>";
    }

    private static String generateStickers() {
        String iurl =
                net
                        .sf
                        .memoranda
                        .ui
                        .AppFrame
                        .class
                        .getResource("resources/agenda/addsticker.gif")
                        .toExternalForm();
        String iurl2 =
                net
                        .sf
                        .memoranda
                        .ui
                        .AppFrame
                        .class
                        .getResource("resources/agenda/removesticker.gif")
                        .toExternalForm();
        String s = "<hr><hr><table border=\"0\" cellpadding=\"0\" width=\"100%\"><tr><td><a href=\"memoranda:importstickers\"><b>" + Local.getString("Import Stickers") + "</b></a></td><td><a href=\"memoranda:exportstickerst\"><b>" + Local.getString("Export Stickers to Text File") + "</b></a><td><a href=\"memoranda:exportstickersh\"><b>" + Local.getString("Export Stickers to HTML File") + "</b></a></td></tr></table>"
                + "<table border=\"0\" cellpadding=\"0\" width=\"100%\"><tr><td><a href=\"memoranda:addsticker\"><img align=\"left\" width=\"22\" height=\"22\" src=\""
                + iurl
                + "\" border=\"0\"  hspace=\"0\" vspace=\"0\" alt=\"New sticker\"></a></td><td width=\"100%\"><a href=\"memoranda:addsticker\"><b>&nbsp;"
                + Local.getString("Add sticker") + "</b></a></td></tr></table>";
        PriorityQueue pQ = sortStickers();
        while (!pQ.Vacia()) {
            Element el = pQ.extraer();
            String id = el.getAttributeValue("id");
            String txt = el.getValue();
            s += "\n<table border=\"0\" cellpadding=\"0\" width=\"100%\"><table width=\"100%\"><tr bgcolor=\"#E0E0E0\"><td><a href=\"memoranda:editsticker#" + id + "\">" + Local.getString("EDIT") + "</a></td><td width=\"70%\"><a href=\"memoranda:expandsticker#" + id + "\">" + Local.getString("OPEN IN A NEW WINDOW") + "</></td><td align=\"right\">" +
                    "&nbsp;" + // without this removesticker link takes klicks from whole cell
                    "<a href=\"memoranda:removesticker#" + id + "\"><img align=\"left\" width=\"14\" height=\"14\" src=\""
                    + iurl2
                    + "\" border=\"0\"  hspace=\"0\" vspace=\"0\" alt=\"Remove sticker\"></a></td></table></tr><tr><td>" + txt + "</td></tr></table>";
        }
        s += "<hr>";
        return s;
    }

    private static PriorityQueue sortStickers() {
        Map stickers = EventsManager.getStickers();
        PriorityQueue pQ = new PriorityQueue(stickers.size());
        for (Object o : stickers.keySet()) {
            String id = (String) o;
            Element el = (Element) stickers.get(id);
            int j = Integer.parseInt(el.getAttributeValue("priority"));
            pQ.insertar(new Pair(el, j));
        }
        return pQ;
    }

    public static String getAgenda(CalendarDate date, Collection expandedTasks) {
        String s = HEADER;
        s += generateAllProjectsInfo(date, expandedTasks);
        s += generateEventsInfo(date);
        s += generateStickers();
        //        /*DEBUG*/System.out.println(s+FOOTER);
        return s + FOOTER;
    }
}
