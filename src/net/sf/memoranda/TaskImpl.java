/**
 * DefaultTask.java
 * Created on 12.02.2003, 15:30:40 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;

/**
 *
 */
/*$Id: TaskImpl.java,v 1.15 2005/12/01 08:12:26 alexeya Exp $*/
public class TaskImpl implements Task, Comparable {

    // declaring XML name strings as class constants
    private static final String LOC_BASE_ATTR = "loc_base";
    private static final String LOC_ADDED_ATTR = "loc_added";
    private static final String LOC_MODIFIED_ATTR = "loc_modified";
    private static final String LOC_DELETED_ATTR = "loc_deleted";
    private static final String LOC_NEW_AND_CHANGED_ATTR = "loc_new_and_changed";
    private static final String LOC_REUSED_ATTR = "loc_reused";
    private static final String LOC_NEW_REUSE_ATTR = "loc_new_reuse";
    private static final String LOC_TOTAL_ATTR = "loc_total";
    private static final String END_DATE_ATTR = "endDate";
    private static final String ELEM_TYPE = "task";
    private static final String START_DATE_ATTR = "startDate";
    private static final String EST_EFFORT_ATTR = "estEffort";
    private static final String ACT_EFFORT_ATTR = "actEffort";
    private static final String PHASE_ATTR = "phase";
    private static final String PRIORITY_ATTR = "priority";
    private static final String PROGRESS_ATTR = "progress";
    private static final String FROZEN_ATTR = "frozen";
    private static final String YES = "yes";
    private static final String TEXT_CHILD = "text";
    private static final String ID_ATTR = "id";
    private static final String DESCRIPTION = "description";

    private Element _element = null;
    private TaskList _tl = null;

    /**
     * Constructor for DefaultTask.
     */
    public TaskImpl(Element taskElement, TaskList tl) {
        _element = taskElement;
        _tl = tl;
    }

    public Element getContent() {
        return _element;
    }

    @Override
    public int getLocBase() {
        return getAttributeValueInt(LOC_BASE_ATTR);
    }

    @Override
    public void setLocBase(int loc) {
        setAttribute(LOC_BASE_ATTR, Integer.toString(loc));
    }

    @Override
    public int getLocAdded() {
        return getAttributeValueInt(LOC_ADDED_ATTR);
    }

    @Override
    public void setLocAdded(int loc) {
        setAttribute(LOC_ADDED_ATTR, Integer.toString(loc));
    }

    @Override
    public int getLocModified() {
        return getAttributeValueInt(LOC_MODIFIED_ATTR);
    }

    @Override
    public void setLocModified(int loc) {
        setAttribute(LOC_MODIFIED_ATTR, Integer.toString(loc));
    }

    @Override
    public int getLocDeleted() {
        return getAttributeValueInt(LOC_DELETED_ATTR);
    }

    @Override
    public void setLocDeleted(int loc) {
        setAttribute(LOC_DELETED_ATTR, Integer.toString(loc));
    }

    @Override
    public int getLocNewAndChanged() {
        return getAttributeValueInt(LOC_NEW_AND_CHANGED_ATTR);
    }

    @Override
    public void setLocNewAndChanged(int loc) {
        setAttribute(LOC_NEW_AND_CHANGED_ATTR, Integer.toString(loc));
    }

    @Override
    public int getLocReused() {
        return getAttributeValueInt(LOC_REUSED_ATTR);
    }

    @Override
    public void setLocReused(int loc) {
        setAttribute(LOC_REUSED_ATTR, Integer.toString(loc));
    }

    @Override
    public int getLocNewReuse() {
        return getAttributeValueInt(LOC_NEW_REUSE_ATTR);
    }

    @Override
    public void setLocNewReuse(int loc) {
        setAttribute(LOC_NEW_REUSE_ATTR, Integer.toString(loc));
    }

    @Override
    public int getLocTotal() {
        return getAttributeValueInt(LOC_TOTAL_ATTR);
    }

    @Override
    public void setLocTotal(int loc) {
        setAttribute(LOC_TOTAL_ATTR, Integer.toString(loc));
    }

    public CalendarDate getStartDate() {
        return new CalendarDate(_element.getAttribute(START_DATE_ATTR).getValue());
    }

    public void setStartDate(CalendarDate date) {
        setAttribute(START_DATE_ATTR, date.toString());
    }

    public CalendarDate getEndDate() {
        String ed = _element.getAttribute(END_DATE_ATTR).getValue();
        if (!ed.equals("")) {
            return new CalendarDate(_element.getAttribute(END_DATE_ATTR).getValue());
        }

        Task parent = this.getParentTask();
        if (parent != null) {
            return parent.getEndDate();
        }

        Project pr = this._tl.getProject();
        if (pr.getEndDate() != null) {
            return pr.getEndDate();
        }

        return this.getStartDate();
    }

    public void setEndDate(CalendarDate date) {
        if (date == null)
            setAttribute(END_DATE_ATTR, "");
        else
            setAttribute(END_DATE_ATTR, date.toString());
    }

    public long getEstEffort() {
        return getAttributeValueLong(EST_EFFORT_ATTR);
    }

    public void setEstEffort(long estEffort) {
        setAttribute(EST_EFFORT_ATTR, Long.toString(estEffort));
    }

    public long getActEffort() {
        return getAttributeValueLong(ACT_EFFORT_ATTR);
    }

    public void setActEffort(long actEffort) {
        setAttribute(ACT_EFFORT_ATTR, Long.toString(actEffort));
    }

    /**
     * @see net.sf.memoranda.Task#getParentTask()
     */
    public Task getParentTask() {
        Node parentNode = _element.getParent();
        if (parentNode instanceof Element) {
            Element parent = (Element) parentNode;
            if (parent.getLocalName().equalsIgnoreCase(ELEM_TYPE))
                return new TaskImpl(parent, _tl);
        }
        return null;
    }

    public String getParentId() {
        Task parent = this.getParentTask();
        if (parent != null)
            return parent.getID();
        return null;
    }

    public String getDescription() {
        Element thisElement = _element.getFirstChildElement(DESCRIPTION);
        if (thisElement == null) {
            return null;
        } else {
            return thisElement.getValue();
        }
    }

    public void setDescription(String s) {
        Element desc = _element.getFirstChildElement(DESCRIPTION);
        if (desc == null) {
            desc = new Element(DESCRIPTION);
            desc.appendChild(s);
            _element.appendChild(desc);
        } else {
            desc.removeChildren();
            desc.appendChild(s);
        }
    }

    public int getStatus(CalendarDate date) {
        CalendarDate start = getStartDate();
        CalendarDate end = getEndDate();
        if (isFrozen())
            return Task.FROZEN;
        if (isCompleted())
            return Task.COMPLETED;

        if (date.inPeriod(start, end)) {
            if (date.equals(end))
                return Task.DEADLINE;
            else
                return Task.ACTIVE;
        } else if (date.before(start)) {
            return Task.SCHEDULED;
        }

        if (start.after(end)) {
            return Task.ACTIVE;
        }

        return Task.FAILED;
    }

    private boolean isFrozen() {
        return _element.getAttribute(FROZEN_ATTR) != null;
    }

    private boolean isCompleted() {
        return getProgress() == 100;
    }

    /**
     * @see net.sf.memoranda.Task#getID()
     */
    public String getID() {
        return _element.getAttribute(ID_ATTR).getValue();
    }

    /**
     * @see net.sf.memoranda.Task#getText()
     */
    public String getText() {
        return _element.getFirstChildElement(TEXT_CHILD).getValue();
    }

    public void setText(String s) {
        _element.getFirstChildElement(TEXT_CHILD).removeChildren();
        _element.getFirstChildElement(TEXT_CHILD).appendChild(s);
    }

    public String toString() {
        return getText();
    }

    /**
     * @see net.sf.memoranda.Task#freeze()
     */
    public void freeze() {
        setAttribute(FROZEN_ATTR, YES);
    }

    /**
     * @see net.sf.memoranda.Task#unfreeze()
     */
    public void unfreeze() {
        if (this.isFrozen())
            _element.removeAttribute(new Attribute(FROZEN_ATTR, YES));
    }

    /**
     * @see net.sf.memoranda.Task#getProgress()
     */
    public int getProgress() {
        return Integer.parseInt(_element.getAttribute(PROGRESS_ATTR).getValue());
    }

    /**
     * @see net.sf.memoranda.Task#setProgress(int)
     */
    public void setProgress(int p) {
        if ((p >= 0) && (p <= 100))
            setAttribute(PROGRESS_ATTR, Integer.toString(p));
    }

    /**
     * @see net.sf.memoranda.Task#getPriority()
     */
    public int getPriority() {
        Attribute pa = _element.getAttribute(PRIORITY_ATTR);
        if (pa == null)
            return Task.PRIORITY_NORMAL;
        return Integer.parseInt(pa.getValue());
    }

    /**
     * @see net.sf.memoranda.Task#setPriority(int)
     */
    public void setPriority(int p) {
        setAttribute(PRIORITY_ATTR, String.valueOf(p));
    }

    /**
     * @see net.sf.memoranda.Task#getPhase()
     */
    public int getPhase() {
        Attribute pa = _element.getAttribute(PHASE_ATTR);
        if (pa == null)
            return Task.NO_PHASE;
        return Integer.parseInt(pa.getValue());
    }

    public void setPhase(int p) {
        setAttribute(PHASE_ATTR, String.valueOf(p));
    }

    private String getAttributeValueString(String a) {
        Attribute attr = _element.getAttribute(a);

        if (attr != null) {
            return attr.getValue();
        } else {
            return null;
        }
    }

    private long getAttributeValueLong(String attributeName) {
        Attribute attr = _element.getAttribute(attributeName);
        if (attr == null) {
            return 0;
        } else {
            try {
                return Long.parseLong(attr.getValue());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    private int getAttributeValueInt(String attributeName) {
        String attr_value = getAttributeValueString(attributeName);
        if (attr_value != null) {
            try {
                return Integer.parseInt(attr_value);
            } catch (NumberFormatException nfe) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private void setAttribute(String a, String value) {
        Attribute attr = _element.getAttribute(a);
        if (attr == null)
            _element.addAttribute(new Attribute(a, value));
        else
            attr.setValue(value);
    }

    private long calcTaskRate(CalendarDate d) {
        Calendar endDateCal = getEndDate().getCalendar();
        Calendar dateCal = d.getCalendar();

        int endDateRaw = endDateCal.get(Calendar.YEAR) * 365 + endDateCal.get(Calendar.DAY_OF_YEAR);
        int startDateRaw = dateCal.get(Calendar.YEAR) * 365 + dateCal.get(Calendar.DAY_OF_YEAR);
        int numOfDays = endDateRaw - startDateRaw;

        if (numOfDays < 0) return -1;

        return (100 - getProgress()) / (numOfDays + 1) * (getPriority() + 1);
    }

    /**
     * @see net.sf.memoranda.Task#getRate()
     */

    public long getRate() {
        return -1 * calcTaskRate(CurrentDate.get());
    }

    public int compareTo(Object o) {
        Task task = (Task) o;
        if (getRate() > task.getRate())
            return 1;
        else if (getRate() < task.getRate())
            return -1;
        else
            return 0;
    }

    public boolean equals(Object o) {
        return ((o instanceof Task) && (((Task) o).getID().equals(this.getID())));
    }

    @Override
    public int hashCode() {
        int result = _element != null ? _element.hashCode() : 0;
        result = 31 * result + (_tl != null ? _tl.hashCode() : 0);
        return result;
    }

    /**
     * @see net.sf.memoranda.Task#getSubTasks()
     */
    public Collection getSubTasks() {
        Elements subTasks = _element.getChildElements(ELEM_TYPE);
        return convertToTaskObjects(subTasks);
    }

    private Collection<Task> convertToTaskObjects(Elements tasks) {
        Vector<Task> v = new Vector<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = new TaskImpl(tasks.get(i), _tl);
            v.add(t);
        }
        return v;
    }

}
