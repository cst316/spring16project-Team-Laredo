/**
 * TaskListImpl.java
 * Created on 21.02.2003, 12:29:54 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;
import nu.xom.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 *
 */
/*$Id: TaskListImpl.java,v 1.14 2006/07/03 11:59:19 alexeya Exp $*/
public class TaskListImpl implements TaskList {

    /*
     * Hastable of "task" XOM elements for quick searching them by ID's
     * (ID => element)
     */
    private final Hashtable<String, Element> elements = new Hashtable<>();
    private Project _project = null;
    private Document _doc = null;
    private Element _root = null;

    /**
     * Constructor for TaskListImpl.
     */
    public TaskListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
        buildElements(_root);
    }

    public TaskListImpl(Project prj) {
        _root = new Element("tasklist");
        _doc = new Document(_root);
        _project = prj;
    }

    public Project getProject() {
        return _project;
    }

    /*
     * Build the hashtable recursively
     */
    private void buildElements(Element parent) {
        Elements els = parent.getChildElements("task");
        for (int i = 0; i < els.size(); i++) {
            Element el = els.get(i);
            elements.put(el.getAttribute("id").getValue(), el);
            buildElements(el);
        }
    }

    /**
     * All methods to obtain list of tasks are consolidated under getAllSubTasks and getActiveSubTasks.
     * If a root task is required, just send a null taskId
     */
    public Collection<Task> getAllSubTasks(String taskId) {
        if ((taskId == null) || (taskId.length() == 0)) {
            return getAllRootTasks();
        } else {
            Element task = getTaskElement(taskId);
            if (task == null)
                return new Vector<>();
            Elements subTasks = task.getChildElements("task");
            return convertToTaskObjects(subTasks);
        }
    }

    public Collection<Task> getTopLevelTasks() {
        return getAllRootTasks();
    }

    public Collection<Task> getAllTasks() {
        Collection<Task> rootTasks = getAllRootTasks();

        Collection<Task> allTasks = new ArrayList<>();

        for (Task t : rootTasks) {
        	allTasks.add(t);
            allTasks.addAll(getAllSubTasksRecursive(t));
        }

        return allTasks;
    }

    private Collection<Task> getAllSubTasksRecursive(Task t) {
        Collection<Task> tasks = new ArrayList<>();

        for (Object tt : t.getSubTasks()) {
            Task ttt = (Task) tt;
            tasks.add(ttt);
            tasks.addAll(getAllSubTasksRecursive(ttt));
        }

        return tasks;
    }

    /**
     * All methods to obtain list of tasks are consolidated under getAllSubTasks and getActiveSubTasks.
     * If a root task is required, just send a null taskId
     */
    public Collection<Task> getActiveSubTasks(String taskId, CalendarDate date) {
        Collection<Task> allTasks = getAllSubTasks(taskId);
        return filterActiveTasks(allTasks, date);
    }

    public Task createTask(CalendarDate startDate, CalendarDate endDate, String text, int priority, int phase, long estEffort, String description, String parentTaskId) {
        Element el = new Element("task");
        el.addAttribute(new Attribute("startDate", startDate.toString()));
        el.addAttribute(new Attribute("endDate", endDate != null ? endDate.toString() : ""));
        String id = Util.generateId();
        el.addAttribute(new Attribute("id", id));
        el.addAttribute(new Attribute("progress", "0"));
        el.addAttribute(new Attribute("estEffort", String.valueOf(estEffort)));
        el.addAttribute(new Attribute("actEffort", "0"));
        el.addAttribute(new Attribute("priority", String.valueOf(priority)));
        el.addAttribute(new Attribute("phase", String.valueOf(phase)));

        Element txt = new Element("text");
        txt.appendChild(text);
        el.appendChild(txt);

        Element desc = new Element("description");
        desc.appendChild(description);
        el.appendChild(desc);

        if (parentTaskId == null) {
            _root.appendChild(el);
        } else {
            Element parent = getTaskElement(parentTaskId);
            parent.appendChild(el);
        }

        elements.put(id, el);

        Util.debug("Created task with parent " + parentTaskId);

        return new TaskImpl(el, this);
    }

    public void removeTask(Task task) {
        String parentTaskId = task.getParentId();
        if (parentTaskId == null) {
            _root.removeChild(task.getContent());
        } else {
            Element parentNode = getTaskElement(parentTaskId);
            parentNode.removeChild(task.getContent());
        }
        elements.remove(task.getID());
    }

    public boolean hasSubTasks(String id) {
        Element task = getTaskElement(id);
        if (task == null) return false;
        return task.getChildElements("task").size() > 0;
    }

    public Task getTask(String id) {
        Util.debug("Getting task " + id);
        return new TaskImpl(getTaskElement(id), this);
    }

    public boolean hasParentTask(String id) {
        Element t = getTaskElement(id);

        Node parentNode = t.getParent();
        if (parentNode instanceof Element) {
            Element parent = (Element) parentNode;
            return parent.getLocalName().equalsIgnoreCase("task");
        } else {
            return false;
        }
    }

    /**
     * @see net.sf.memoranda.TaskList#getXMLContent()
     */
    public Document getXMLContent() {
        return _doc;
    }

    /**
     * Recursively calculate total estimated effort based on subtasks for every node in the task tree
     * The values are saved as they are calculated as well
     *
     * @param t
     * @return
     */
    public long calculateTotalEstimatedEffortFromSubTasks(Task t) {
        long totalEffort = 0;
        if (hasSubTasks(t.getID())) {
            Collection<Task> subTasks = getAllSubTasks(t.getID());
            for (Task e : subTasks) {
                totalEffort = totalEffort + calculateTotalEstimatedEffortFromSubTasks(e);
            }
            t.setEstEffort(totalEffort);
            return totalEffort;
        } else {
            return t.getEstEffort();
        }
    }

    /**
     * Recursively calculate total actual effort based on subtasks for every node in the task tree
     * The values are saved as they are calculated as well
     *
     * @param t
     * @return
     */
    public long calculateTotalActualEffortFromSubTasks(Task t) {
        long totalEffort = 0;
        if (hasSubTasks(t.getID())) {
            Collection<Task> subTasks = getAllSubTasks(t.getID());
            for (Task e : subTasks) {
                totalEffort = totalEffort + calculateTotalActualEffortFromSubTasks(e);
            }
            t.setActEffort(totalEffort);
            return totalEffort;
        } else {
            return t.getActEffort();
        }
    }

    /**
     * Looks through the entire sub task tree and corrects any inconsistencies in start dates
     *
     * @param t
     * @return
     */
    public CalendarDate getEarliestStartDateFromSubTasks(Task t) {
        CalendarDate d = t.getStartDate();
        if (hasSubTasks(t.getID())) {
            Collection<Task> subTasks = getAllSubTasks(t.getID());
            for (Task e : subTasks) {
                CalendarDate dd = getEarliestStartDateFromSubTasks(e);
                if (dd.before(d)) {
                    d = dd;
                }
            }
            t.setStartDate(d);
            return d;
        } else {
            return t.getStartDate();
        }
    }

    /**
     * Looks through the entire sub task tree and corrects any inconsistencies in start dates
     *
     * @param t
     * @return
     */
    public CalendarDate getLatestEndDateFromSubTasks(Task t) {
        CalendarDate d = t.getEndDate();
        if (hasSubTasks(t.getID())) {
            Collection<Task> subTasks = getAllSubTasks(t.getID());
            for (Task e : subTasks) {
                CalendarDate dd = getLatestEndDateFromSubTasks(e);
                if (dd.after(d)) {
                    d = dd;
                }
            }
            t.setEndDate(d);
            return d;
        } else {
            return t.getEndDate();
        }
    }

    /**
     * Looks through the entire sub task tree and calculates progress on all parent task nodes
     *
     * @param t
     * @return long[] of size 2. First long is expended effort in milliseconds, 2nd long is total effort in milliseconds
     */
    public long[] calculateCompletionFromSubTasks(Task t) {
//        Util.debug("Task " + t.getText());

        long[] res = new long[2];
        long expendedEffort = 0; // milliseconds
        long totalEffort = 0; // milliseconds
        if (hasSubTasks(t.getID())) {
            Collection<Task> subTasks = getAllSubTasks(t.getID());
            for (Task e : subTasks) {
                long[] subTaskCompletion = calculateCompletionFromSubTasks(e);
                expendedEffort = expendedEffort + subTaskCompletion[0];
                totalEffort = totalEffort + subTaskCompletion[1];
            }

            int thisProgress = (int) Math.round((((double) expendedEffort / (double) totalEffort) * 100));
            t.setProgress(thisProgress);

            res[0] = expendedEffort;
            res[1] = totalEffort;
            return res;
        } else {
            long eff = t.getEstEffort();
            // if effort was not filled in, it is assumed to be "1 hr" for the purpose of calculation
            if (eff == 0) {
                eff = 1;
            }
            res[0] = Math.round((double) (t.getProgress() * eff) / 100d);
            res[1] = eff;
            return res;
        }
    }

    /*
     * private methods below this line
     */
    private Element getTaskElement(String id) {
        Element el = elements.get(id);
        if (el == null) {
            Util.debug("Task " + id + " cannot be found in project " + _project.getTitle());
        }
        return el;
    }

    private Collection<Task> getAllRootTasks() {
        Elements tasks = _root.getChildElements("task");
        return convertToTaskObjects(tasks);
    }

    private Collection<Task> convertToTaskObjects(Elements tasks) {
        Vector<Task> v = new Vector<>();

        for (int i = 0; i < tasks.size(); i++) {
            Task t = new TaskImpl(tasks.get(i), this);
            v.add(t);
        }
        return v;
    }

    private Collection<Task> filterActiveTasks(Collection<Task> tasks, CalendarDate date) {
        return tasks.stream().filter(t -> isActive(t, date)).collect(Collectors.toCollection(Vector::new));
    }

    private boolean isActive(Task t, CalendarDate date) {
        return (t.getStatus(date) == Task.ACTIVE)
                || (t.getStatus(date) == Task.DEADLINE)
                || (t.getStatus(date) == Task.FAILED);
    }
}
