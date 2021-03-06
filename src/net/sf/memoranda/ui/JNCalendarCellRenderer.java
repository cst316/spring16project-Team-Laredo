/**
 * JNCalendarCellRenderer.java
 * Created on 14.02.2003, 0:09:11 Alex
 * Package: net.sf.memoranda.ui
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.ui;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CalendarDate;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
/*$Id: JNCalendarCellRenderer.java,v 1.5 2004/10/11 08:48:20 alexeya Exp $*/
public class JNCalendarCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
    private final ImageIcon evIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/en.png"));
    boolean disabled = false;
    private Task t = null;
    private CalendarDate d = null;

    public Task getTask() {
        return t;
    }

    public void setTask(Task _t) {
        t = _t;
    }

    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String currentPanel = App.getFrame().workPanel.dailyItemsPanel.getCurrentPanel();

        if (d == null) {
            label.setEnabled(false);
            label.setIcon(null);
            label.setBackground(new Color(0xE0, 0xE0, 0xE0));
            return label;
        }

        if (!isSelected) {
            CalendarDate cpsd = CurrentProject.get().getStartDate();
            CalendarDate cped = CurrentProject.get().getEndDate();
            if (!(((d.after(cpsd)) && (d.before(cped))) || (d.equals(cpsd)) || (d.equals(cped)))) {
                label.setBackground(new Color(0xF0, 0xF0, 0xF0));
                return label;
            }
        }


        label.setHorizontalTextPosition(2);
        label.setEnabled(true);


        if (d.equals(CalendarDate.today())) {
            label.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 128)));
        }

        // set foreground color
        label.setForeground(Color.BLACK);

        // set background color
        if (currentPanel == null)
            label.setBackground(Color.WHITE);

        else if (currentPanel.equals("TASKS") && (t != null) &&
                (d.inPeriod(t.getStartDate(), t.getEndDate())))
            label.setBackground(new Color(230, 255, 230));

        else if (currentPanel.equals("NOTES") &&
                CurrentProject.getNoteList().getNoteForDate(d) != null)
            label.setBackground(new Color(255, 245, 200));

        else if (currentPanel.equals("EVENTS") &&
                (!(EventsManager.getEventsForDate(d).isEmpty())))
            label.setBackground(new Color(255, 230, 230));

        else if (!isSelected)
            label.setBackground(Color.WHITE);

        // always display NREvents
        if (EventsManager.isNREventsForDate(d))
            label.setIcon(evIcon);
        else
            label.setIcon(null);

        return label;
    }

    public void setDate(CalendarDate date) {
        d = date;
    }
}
