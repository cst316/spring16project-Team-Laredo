/**
 * JNCalendar.java Created on 13.02.2003, 21:26:38 Alex Package:
 * net.sf.memoranda.ui
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net Copyright (c) 2003
 * Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.ui;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Configuration;
import net.sf.memoranda.util.Local;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;

/**
 *
 */
/*$Id: JNCalendar.java,v 1.8 2004/11/05 07:38:10 pbielen Exp $*/
class JNCalendar extends JTable {

    public final JNCalendarCellRenderer renderer = new JNCalendarCellRenderer();
    private final Vector<ActionListener> selectionListeners = new Vector<>();
    private int firstDay;
    private int daysInMonth;
    private CalendarDate _date = null;
    private boolean ignoreChange = false;

    public JNCalendar() {
        this(CurrentDate.get());
    }

    /**
     * Constructor for JNCalendar.
     */
    public JNCalendar(CalendarDate date) {
        super();
        /* table properties */
        setCellSelectionEnabled(true);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);
        set(date);

		/* selection listeners */
        final ListSelectionModel rowSM = getSelectionModel();
        final ListSelectionModel colSM = getColumnModel().getSelectionModel();
        ListSelectionListener lsl = e -> {
            //Ignore extra messages.
            if (e.getValueIsAdjusting())
                return;
            if (ignoreChange)
                return;
            int row = getSelRow();
            int col = getSelCol();
            Object val = getModel().getValueAt(row, col);
            if (val != null) {
                if (val
                        .toString()
                        .equals(Integer.toString(_date.getDay())))
                    return;
                _date =
                        new CalendarDate(
                                Integer.parseInt(val.toString()),
                                _date.getMonth(),
                                _date.getYear());
                notifyListeners();
            } else {
                //getSelectionModel().clearSelection();
                doSelection();
            }
        };
        rowSM.addListSelectionListener(lsl);
        colSM.addListSelectionListener(lsl);
    }

    public JNCalendar(CalendarDate date, CalendarDate sd, CalendarDate ed) {
        this(date);
        setSelectablePeriod(sd, ed);
    }

    private int getSelRow() {
        return this.getSelectedRow();
    }

    private int getSelCol() {
        return this.getSelectedColumn();
    }

    public void set(CalendarDate date) {
        _date = date;
        setCalendarParameters();
        ignoreChange = true;
        this.setModel(new JNCalendarModel());
        ignoreChange = false;
        doSelection();
    }

    public CalendarDate get() {
        return _date;
    }

    public void addSelectionListener(ActionListener al) {
        selectionListeners.add(al);
    }

    private void setSelectablePeriod(CalendarDate sd, CalendarDate ed) {
        CalendarDate startPeriod = sd;
        CalendarDate endPeriod = ed;
    }

    private void notifyListeners() {
        for (ActionListener selectionListener : selectionListeners) {
            selectionListener.actionPerformed(
                    new ActionEvent(this, 0, "Calendar event"));
        }
    }

    public TableCellRenderer getCellRenderer(int row, int column) {
        Object d = this.getModel().getValueAt(row, column);
        if (d != null)
            renderer.setDate(
                    new CalendarDate(
                            new Integer(d.toString()),
                            _date.getMonth(),
                            _date.getYear()));
        else
            renderer.setDate(null);
        return renderer;
    }

    private void doSelection() {
        ignoreChange = true;
        int selRow = getRow(_date.getDay());
        int selCol = getCol(_date.getDay());
        this.setRowSelectionInterval(selRow, selRow);
        this.setColumnSelectionInterval(selCol, selCol);
        ignoreChange = false;
    }

    private int getRow(int day) {
        return ((day - 1) + firstDay) / 7;
    }

    private int getCol(int day) {
        return ((day - 1) + firstDay) % 7;
    }

    private void setCalendarParameters() {
        int d = 1;

        Calendar cal = _date.getCalendar();

        if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            d = 2;
        } else
            cal.setFirstDayOfWeek(Calendar.SUNDAY);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.getTime();
        firstDay = cal.get(Calendar.DAY_OF_WEEK) - d;
        if (firstDay == -1)
            firstDay = 6;
        daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /*$Id: JNCalendar.java,v 1.8 2004/11/05 07:38:10 pbielen Exp $*/
    public class JNCalendarModel extends AbstractTableModel {

        private final String[] dayNames = Local.getWeekdayNames();

        public JNCalendarModel() {
            super();
        }

        public int getColumnCount() {
            return 7;
        }

        public Object getValueAt(int row, int col) {
            //int pos = (row * 7 + col) - firstDay + 1;
            int pos = (row * 7 + (col + 1)) - firstDay;
            if ((pos > 0) && (pos <= daysInMonth))
                return pos;
            else
                return null;

        }

        public int getRowCount() {
            return 6;
        }

        public String getColumnName(int col) {
            return dayNames[col];
        }

    }

}
