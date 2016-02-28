package net.sf.memoranda.ui;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: JNCalendarPanel.java,v 1.9 2004/04/05 10:05:44 alexeya Exp $*/
public class JNCalendarPanel extends JPanel {

    final JNCalendar jnCalendar = new JNCalendar(CurrentDate.get());
    private final JToolBar navigationBar = new JToolBar();
    private final JPanel mntyPanel = new JPanel(new BorderLayout());
    private final JPanel navbPanel = new JPanel(new BorderLayout());
    private final JButton dayForwardB = new JButton();
    private final JPanel dayForwardBPanel = new JPanel();
    private final JButton todayB = new JButton();
    private final JPanel todayBPanel = new JPanel();
    private final JPanel dayBackBPanel = new JPanel();
    private final JButton dayBackB = new JButton();
    private final JComboBox<? extends String> monthsCB = new JComboBox<>(Local.getMonthNames());
    private final JPanel jnCalendarPanel = new JPanel();
    private final BorderLayout borderLayout5 = new BorderLayout();
    private final JSpinner yearSpin = new JSpinner(new SpinnerNumberModel(jnCalendar.get().getYear(), 1980, 2999, 1));
    private final JSpinner.NumberEditor yearSpinner = new JSpinner.NumberEditor(yearSpin, "####");
    private final Vector<ActionListener> selectionListeners = new Vector<>();
    private CalendarDate _date = CurrentDate.get();
    private boolean ignoreChange = false;
    public final Action dayBackAction =
            new AbstractAction(
                    "Go one day back",
                    new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/back16.png"))) {
                public void actionPerformed(ActionEvent e) {
                    dayBackB_actionPerformed();
                }
            };
    public final Action dayForwardAction =
            new AbstractAction(
                    "Go one day forward",
                    new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/forward16.png"))) {
                public void actionPerformed(ActionEvent e) {
                    dayForwardB_actionPerformed();
                }
            };
    public final Action todayAction =
            new AbstractAction(
                    "Go to today",
                    new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/today16.png"))) {
                public void actionPerformed(ActionEvent e) {
                    todayB_actionPerformed();
                }
            };

    public JNCalendarPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    private void jbInit() {
        todayAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_HOME, KeyEvent.ALT_MASK));

        monthsCB.setRequestFocusEnabled(false);
        monthsCB.setMaximumRowCount(12);
        monthsCB.setPreferredSize(new Dimension(50, 20));
        Border border1 = BorderFactory.createEmptyBorder(0, 0, 5, 0);
        Border border2 = BorderFactory.createEmptyBorder();
        this.setLayout(new BorderLayout());
        navigationBar.setFloatable(false);
        dayForwardB.setAction(dayForwardAction);
        dayForwardB.setMinimumSize(new Dimension(24, 24));
        dayForwardB.setOpaque(false);
        dayForwardB.setPreferredSize(new Dimension(24, 24));
        dayForwardB.setRequestFocusEnabled(false);
        dayForwardB.setBorderPainted(false);
        dayForwardB.setFocusPainted(false);
        dayForwardB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/forward.png")));
        dayForwardB.setText("");
        dayForwardB.setToolTipText(Local.getString("One day forward"));

        dayForwardBPanel.setAlignmentX((float) 0.0);
        dayForwardBPanel.setMinimumSize(new Dimension(40, 24));
        dayForwardBPanel.setOpaque(false);
        dayForwardBPanel.setPreferredSize(new Dimension(40, 24));

        todayB.setAction(todayAction);
        todayB.setMinimumSize(new Dimension(24, 24));
        todayB.setOpaque(false);
        todayB.setPreferredSize(new Dimension(24, 24));
        todayB.setRequestFocusEnabled(false);
        todayB.setBorderPainted(false);
        todayB.setFocusPainted(false);
        todayB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/today.png")));
        todayB.setText("");
        todayB.setToolTipText(Local.getString("To today"));

        dayBackBPanel.setAlignmentX((float) 1.5);
        dayBackBPanel.setMinimumSize(new Dimension(40, 24));
        dayBackBPanel.setOpaque(false);
        dayBackBPanel.setPreferredSize(new Dimension(40, 24));

        dayBackB.setAction(dayBackAction);
        dayBackB.setMinimumSize(new Dimension(24, 24));
        dayBackB.setOpaque(false);
        dayBackB.setPreferredSize(new Dimension(24, 24));
        dayBackB.setRequestFocusEnabled(false);
        dayBackB.setToolTipText("");
        dayBackB.setBorderPainted(false);
        dayBackB.setFocusPainted(false);
        dayBackB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/back.png")));
        dayBackB.setText("");
        dayBackB.setToolTipText(Local.getString("One day back"));

        yearSpin.setPreferredSize(new Dimension(70, 20));
        yearSpin.setRequestFocusEnabled(false);
        yearSpin.setEditor(yearSpinner);
        navbPanel.setMinimumSize(new Dimension(202, 30));
        navbPanel.setOpaque(false);
        navbPanel.setPreferredSize(new Dimension(155, 30));
        jnCalendar.getTableHeader().setFont(new java.awt.Font("Dialog", 1, 10));
        jnCalendar.setFont(new java.awt.Font("Dialog", 0, 10));
        jnCalendar.setGridColor(Color.lightGray);
        jnCalendarPanel.setLayout(borderLayout5);
        todayBPanel.setMinimumSize(new Dimension(68, 24));
        todayBPanel.setOpaque(false);
        todayBPanel.setPreferredSize(new Dimension(51, 24));
        this.add(navigationBar, BorderLayout.NORTH);
        navigationBar.add(navbPanel, null);
        navbPanel.add(dayBackBPanel, BorderLayout.WEST);
        dayBackBPanel.add(dayBackB, null);
        navbPanel.add(todayBPanel, BorderLayout.CENTER);
        todayBPanel.add(todayB, null);
        navbPanel.add(dayForwardBPanel, BorderLayout.EAST);
        dayForwardBPanel.add(dayForwardB, null);
        this.add(mntyPanel, BorderLayout.SOUTH);
        mntyPanel.add(monthsCB, BorderLayout.CENTER);
        mntyPanel.add(yearSpin, BorderLayout.EAST);
        this.add(jnCalendarPanel, BorderLayout.CENTER);
        jnCalendar.getTableHeader().setPreferredSize(new Dimension(200, 15));
        jnCalendarPanel.add(jnCalendar.getTableHeader(), BorderLayout.NORTH);
        jnCalendarPanel.add(jnCalendar, BorderLayout.CENTER);
        jnCalendar.addSelectionListener(e -> setCurrentDateDay(jnCalendar.get(), jnCalendar.get().getDay()));

        monthsCB.setFont(new java.awt.Font("Dialog", 0, 11));

        monthsCB.addActionListener(this::monthsCB_actionPerformed);

        yearSpin.addChangeListener(e -> yearSpin_actionPerformed());
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange() {
            }

            public void projectWasChanged() {
                jnCalendar.updateUI();
            }
        });


        refreshView();
        yearSpin.setBorder(border2);

    }

    public void set(CalendarDate date) {
        _date = date;
        refreshView();
    }

    public CalendarDate get() {
        return _date;
    }

    public void addSelectionListener(ActionListener al) {
        selectionListeners.add(al);
    }

    private void notifyListeners() {
        for (Enumeration<ActionListener> en = selectionListeners.elements(); en.hasMoreElements(); )
            (en.nextElement()).actionPerformed(new ActionEvent(this, 0, "Calendar event"));
    }

    private void setCurrentDateDay(CalendarDate dt, int d) {
        if (ignoreChange) return;
        if (_date.equals(dt)) return;
        _date = new CalendarDate(d, _date.getMonth(), _date.getYear());
        notifyListeners();
    }

    private void refreshView() {
        ignoreChange = true;
        jnCalendar.set(_date);
        monthsCB.setSelectedIndex(_date.getMonth());
        yearSpin.setValue(_date.getYear());
        ignoreChange = false;
    }

    private void monthsCB_actionPerformed(ActionEvent e) {
        if (ignoreChange) return;
        _date = new CalendarDate(_date.getDay(), monthsCB.getSelectedIndex(), _date.getYear());
        jnCalendar.set(_date);
        notifyListeners();
    }

    private void yearSpin_actionPerformed() {
        if (ignoreChange) return;
        _date = new CalendarDate(_date.getDay(), _date.getMonth(), (Integer) yearSpin.getValue());
        jnCalendar.set(_date);
        notifyListeners();
    }

    private void dayBackB_actionPerformed() {
        Calendar cal = _date.getCalendar();
        cal.add(Calendar.MONTH, -1);
        cal.getTime();
        _date = new CalendarDate(cal);
        refreshView();
        notifyListeners();
    }

    private void todayB_actionPerformed() {
        _date = CalendarDate.today();
        refreshView();
        notifyListeners();
    }

    private void dayForwardB_actionPerformed() {
        Calendar cal = _date.getCalendar();
        cal.add(Calendar.MONTH, 1);
        cal.getTime();
        _date = new CalendarDate(cal);
        refreshView();
        notifyListeners();
    }


}