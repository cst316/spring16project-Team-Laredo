/**
 * EventsTable.java
 * Created on 09.03.2003, 9:52:02 Alex
 * Package: net.sf.memoranda.ui
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.ui;

import net.sf.memoranda.Event;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Local;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Date;
import java.util.Vector;

/**
 *
 */
/*$Id: EventsTable.java,v 1.6 2004/10/11 08:48:20 alexeya Exp $*/
class EventsTable extends JTable {

    public static final int EVENT = 100;
    private static final int EVENT_ID = 101;

    private Vector events = new Vector();

    /**
     * Constructor for EventsTable.
     */
    public EventsTable() {
        super();
        setModel(new EventsTableModel());
        initTable(CurrentDate.get());
        this.setShowGrid(false);
        CurrentDate.addDateListener(this::initTable);
    }

    public void initTable(CalendarDate d) {
        events = (Vector) EventsManager.getEventsForDate(d);
        getColumnModel().getColumn(0).setPreferredWidth(60);
        getColumnModel().getColumn(0).setMaxWidth(60);

        getColumnModel().getColumn(1).setPreferredWidth(60);
        getColumnModel().getColumn(1).setMaxWidth(60);

        clearSelection();
        updateUI();
    }

    public void refresh() {
        initTable(CurrentDate.get());
    }

    public TableCellRenderer getCellRenderer(int row, int column) {
        return new javax.swing.table.DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                Component comp;
                comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Event ev = (Event) getModel().getValueAt(row, EVENT);
                comp.setForeground(java.awt.Color.gray);
                if (ev.isRepeatable())
                    comp.setFont(comp.getFont().deriveFont(Font.ITALIC));
                if (CurrentDate.get().after(CalendarDate.today())) {
                    comp.setForeground(java.awt.Color.black);
                } else if (CurrentDate.get().equals(CalendarDate.today())) {
                    if (ev.getTime().after(new Date())) {
                        comp.setForeground(java.awt.Color.black);
                        comp.setFont(comp.getFont().deriveFont(Font.BOLD));
                    }
                }
                return comp;
            }
        };

    }

    class EventsTableModel extends AbstractTableModel {

        final String[] columnNames = {
                Local.getString("Time"),
                Local.getString("Duration"),
                Local.getString("Text")
        };

        EventsTableModel() {
            super();
        }

        public int getColumnCount() {
            return 3;
        }

        public int getRowCount() {
            int i;
            try {
                i = events.size();
            } catch (NullPointerException e) {
                i = 1;
            }
            return i;
        }

        public Object getValueAt(int row, int col) {
            Event ev = (Event) events.get(row);
            if (col == 0) {
                return ev.getTimeString();
            } else if (col == 1) {
                return ev.getDurationString();
            } else if (col == 2) {
                return ev.getText();
            } else if (col == EVENT_ID) {
                return ev.getId();
            } else {
                return ev;
            }
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }
    }
}
