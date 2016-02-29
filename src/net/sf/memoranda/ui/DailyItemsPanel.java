package net.sf.memoranda.ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.sf.memoranda.*;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: DailyItemsPanel.java,v 1.22 2005/02/13 03:06:10 rawsushi Exp $*/
public class DailyItemsPanel extends JPanel {
    public final EditorPanel editorPanel = new EditorPanel(this);
    final EventsPanel eventsPanel = new EventsPanel(this);
    final JNCalendarPanel calendar = new JNCalendarPanel();
    final JButton alarmB = new JButton();
    final JButton taskB = new JButton();
    final NotesControlPanel notesControlPane = new NotesControlPanel();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JSplitPane splitPane = new JSplitPane();
    private final JPanel controlPanel = new JPanel(); /* Contains the calendar */
    private final JPanel mainPanel = new JPanel();
    private final BorderLayout borderLayout2 = new BorderLayout();
    private final JPanel statusPanel = new JPanel();
    private final BorderLayout borderLayout3 = new BorderLayout();
    private final JPanel editorsPanel = new JPanel();
    private final CardLayout cardLayout1 = new CardLayout();
    private final JLabel currentDateLabel = new JLabel();
    private final BorderLayout borderLayout4 = new BorderLayout();
    private final TaskPanel tasksPanel = new TaskPanel(this);
    private final AgendaPanel agendaPanel = new AgendaPanel(this);
    private final DefectPanel defectPanel = new DefectPanel(this);
    private final ImageIcon expIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/exp_right.png"));
    private final ImageIcon collIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/exp_left.png"));
    private final ImageIcon bookmarkIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/star8.png"));
    private final JPanel cmainPanel = new JPanel();
    private final JToolBar toggleToolBar = new JToolBar();
    private final BorderLayout borderLayout5 = new BorderLayout();
    private final JButton toggleButton = new JButton();
    private final JPanel indicatorsPanel = new JPanel();
    private final FlowLayout flowLayout1 = new FlowLayout();
    private final JPanel mainTabsPanel = new JPanel();
    private final CardLayout cardLayout2 = new CardLayout();
    private final JTabbedPane tasksTabbedPane = new JTabbedPane();
    private final JTabbedPane eventsTabbedPane = new JTabbedPane();
    private final JTabbedPane agendaTabbedPane = new JTabbedPane();
    private final JTabbedPane defectTabbedPane = new JTabbedPane();
    private final Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);
    private boolean expanded = true;
    private Note currentNote;
    private CalendarDate currentDate;
    private boolean calendarIgnoreChange = false;
    private boolean dateChangedByCalendar = false;
    private boolean changedByHistory = false;
    private WorkPanel parentPanel = null;
    private String CurrentPanel;

    public DailyItemsPanel(WorkPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    @SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
    private void jbInit() {
        BorderFactory.createEtchedBorder(Color.white, Color.gray);
        Border border2 = BorderFactory.createEtchedBorder(Color.white, new Color(161, 161, 161));
        this.setLayout(borderLayout1);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setBorder(null);
        splitPane.setDividerSize(2);
        controlPanel.setLayout(borderLayout2);
        mainPanel.setLayout(borderLayout3);
        editorsPanel.setLayout(cardLayout1);
        statusPanel.setBackground(Color.black);
        statusPanel.setForeground(Color.white);
        statusPanel.setMinimumSize(new Dimension(14, 24));
        statusPanel.setPreferredSize(new Dimension(14, 24));
        statusPanel.setLayout(borderLayout4);
        currentDateLabel.setFont(new java.awt.Font("Dialog", 0, 16));
        currentDateLabel.setForeground(Color.white);
        currentDateLabel.setText(CurrentDate.get().getFullDateString());
        borderLayout4.setHgap(4);
        controlPanel.setBackground(new Color(230, 230, 230));
        controlPanel.setBorder(border2);
        controlPanel.setMinimumSize(new Dimension(20, 170));
        controlPanel.setPreferredSize(new Dimension(205, 170));
        //controlPanel.setMaximumSize(new Dimension(206, 170));
        //controlPanel.setSize(controlPanel.getMaximumSize());
        calendar.setFont(new java.awt.Font("Dialog", 0, 11));
        calendar.setMinimumSize(new Dimension(0, 168));
        toggleToolBar.setBackground(new Color(215, 225, 250));
        toggleToolBar.setRequestFocusEnabled(false);
        toggleToolBar.setFloatable(false);
        cmainPanel.setLayout(borderLayout5);
        cmainPanel.setBackground(SystemColor.desktop);
        cmainPanel.setMinimumSize(new Dimension(0, 168));
        cmainPanel.setOpaque(false);
        toggleButton.setMaximumSize(new Dimension(32767, 32767));
        toggleButton.setMinimumSize(new Dimension(16, 16));
        toggleButton.setOpaque(false);
        toggleButton.setPreferredSize(new Dimension(16, 16));
        toggleButton.setBorderPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setMargin(new Insets(0, 0, 0, 0));
        toggleButton.addActionListener(e -> toggleButton_actionPerformed());
        toggleButton.setIcon(collIcon);
        indicatorsPanel.setOpaque(false);
        indicatorsPanel.setLayout(flowLayout1);
        alarmB.setMaximumSize(new Dimension(24, 24));
        alarmB.setOpaque(false);
        alarmB.setPreferredSize(new Dimension(24, 24));
        alarmB.setToolTipText(Local.getString("Active events"));
        alarmB.setBorderPainted(false);
        alarmB.setMargin(new Insets(0, 0, 0, 0));
        alarmB.addActionListener(e -> alarmB_actionPerformed(e));
        alarmB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/alarm.png")));
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        flowLayout1.setVgap(0);
        taskB.setMargin(new Insets(0, 0, 0, 0));
        taskB.addActionListener(e -> taskB_actionPerformed(e));
        taskB.setPreferredSize(new Dimension(24, 24));
        taskB.setToolTipText(Local.getString("Active to-do tasks"));
        taskB.setBorderPainted(false);
        taskB.setMaximumSize(new Dimension(24, 24));
        taskB.setOpaque(false);
        taskB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/task.png")));

        notesControlPane.setFont(new java.awt.Font("Dialog", 1, 10));
        mainTabsPanel.setLayout(cardLayout2);
        this.add(splitPane, BorderLayout.CENTER);

        controlPanel.add(cmainPanel, BorderLayout.CENTER);
        cmainPanel.add(calendar, BorderLayout.NORTH);

        mainPanel.add(statusPanel, BorderLayout.NORTH);
        statusPanel.add(currentDateLabel, BorderLayout.CENTER);
        statusPanel.add(indicatorsPanel, BorderLayout.EAST);

        mainPanel.add(editorsPanel, BorderLayout.CENTER);

        editorsPanel.add(agendaPanel, "AGENDA");
        editorsPanel.add(eventsPanel, "EVENTS");
        editorsPanel.add(tasksPanel, "TASKS");
        editorsPanel.add(editorPanel, "NOTES");
        editorsPanel.add(defectPanel, "DEFECTS");

        splitPane.add(mainPanel, JSplitPane.RIGHT);
        splitPane.add(controlPanel, JSplitPane.LEFT);
        controlPanel.add(toggleToolBar, BorderLayout.SOUTH);
        toggleToolBar.add(toggleButton, null);

        splitPane.setDividerLocation((int) controlPanel.getPreferredSize().getWidth());

        CurrentDate.addDateListener(this::currentDateChanged);

        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange() {
                currentProjectChanged(CurrentProject.get(), CurrentProject.getTaskList());
            }

            public void projectWasChanged() {
                // cannot save note here, changing to new project
                currentNote = CurrentProject.getNoteList().getNoteForDate(CurrentDate.get());
                CurrentNote.set(currentNote, false);
                editorPanel.setDocument(currentNote);
            }
        });

        CurrentNote.addNoteListener(this::currentNoteChanged);

        calendar.addSelectionListener(e -> {
            if (calendarIgnoreChange)
                return;
            dateChangedByCalendar = true;
            CurrentDate.set(calendar.get());
            dateChangedByCalendar = false;
        });

        AppFrame.addExitListener(e -> {
            if (editorPanel.isDocumentChanged()) {
                saveNote();
                CurrentStorage.get().storeNoteList(CurrentProject.getNoteList(), CurrentProject.get());
            }
        });

        History.addHistoryListener(this::historyChanged);

        EventsScheduler.addListener(new EventNotificationListener() {
            public void eventIsOccured(net.sf.memoranda.Event ev) {
                /*DEBUG*/
                System.out.println(ev.getTimeString() + " " + ev.getText());
                updateIndicators();
            }

            public void eventsChanged() {
                updateIndicators();
            }
        });

        currentDate = CurrentDate.get();
        currentNote = CurrentProject.getNoteList().getNoteForDate(CurrentDate.get());
        CurrentNote.set(currentNote, true);
        editorPanel.setDocument(currentNote);
        History.add(new HistoryItem(CurrentDate.get(), CurrentProject.get()));
        cmainPanel.add(mainTabsPanel, BorderLayout.CENTER);
        mainTabsPanel.add(eventsTabbedPane, "EVENTSTAB");
        mainTabsPanel.add(tasksTabbedPane, "TASKSTAB");
        mainTabsPanel.add(notesControlPane, "NOTESTAB");
        mainTabsPanel.add(agendaTabbedPane, "AGENDATAB");
        mainTabsPanel.add(defectTabbedPane, "DEFECTSTAB");
        updateIndicators(CurrentDate.get(), CurrentProject.getTaskList());
        mainPanel.setBorder(null);
    }


    private void currentDateChanged(CalendarDate newdate) {
        Cursor cur = App.getFrame().getCursor();
        App.getFrame().setCursor(waitCursor);
        if (!changedByHistory) {
            History.add(new HistoryItem(newdate, CurrentProject.get()));
        }
        if (!dateChangedByCalendar) {
            calendarIgnoreChange = true;
            calendar.set(newdate);
            calendarIgnoreChange = false;
        }

        currentNoteChanged(currentNote, true);
        currentNote = CurrentProject.getNoteList().getNoteForDate(newdate);
        CurrentNote.set(currentNote, true);
        currentDate = CurrentDate.get();

        currentDateLabel.setText(newdate.getFullDateString());
        if ((currentNote != null) && (currentNote.isMarked())) {
            currentDateLabel.setIcon(bookmarkIcon);
            currentDateLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        } else {
            currentDateLabel.setIcon(null);
        }

        updateIndicators(newdate, CurrentProject.getTaskList());
        App.getFrame().setCursor(cur);
    }

    private void currentNoteChanged(Note note, boolean toSaveCurrentNote) {

        if (editorPanel.isDocumentChanged()) {
            if (toSaveCurrentNote) {
                saveNote();
            }
            notesControlPane.refresh();
        }
        currentNote = note;
        editorPanel.setDocument(currentNote);
        calendar.set(CurrentDate.get());
        editorPanel.editor.requestFocus();
    }

    private void currentProjectChanged(Project newprj, TaskList tl) {

        Cursor cur = App.getFrame().getCursor();
        App.getFrame().setCursor(waitCursor);
        if (!changedByHistory)
            History.add(new HistoryItem(CurrentDate.get(), newprj));
        if (editorPanel.isDocumentChanged())
            saveNote();
        CurrentProject.save();

        updateIndicators(CurrentDate.get(), tl);
        App.getFrame().setCursor(cur);
    }

    private void historyChanged(HistoryItem hi) {
        changedByHistory = true;
        CurrentProject.set(hi.getProject());
        CurrentDate.set(hi.getDate());
        changedByHistory = false;
    }

    private void saveNote() {
        if (currentNote == null)
            currentNote = CurrentProject.getNoteList().createNoteForDate(currentDate);
        currentNote.setTitle(editorPanel.titleField.getText());
        currentNote.setId(Util.generateId());
        CurrentStorage.get().storeNote(currentNote, editorPanel.getDocument());
    }

    private void toggleButton_actionPerformed() {
        if (expanded) {
            expanded = false;
            toggleButton.setIcon(expIcon);
            controlPanel.remove(toggleToolBar);
            controlPanel.add(toggleToolBar, BorderLayout.EAST);
            splitPane.setDividerLocation((int) controlPanel.getMinimumSize().getWidth());

        } else {
            expanded = true;
            toggleButton.setIcon(collIcon);
            controlPanel.remove(toggleToolBar);
            controlPanel.add(toggleToolBar, BorderLayout.SOUTH);
            splitPane.setDividerLocation((int) controlPanel.getPreferredSize().getWidth());
        }
    }

    private void updateIndicators(CalendarDate date, TaskList tl) {
        indicatorsPanel.removeAll();
        if (date.equals(CalendarDate.today())) {
            if (tl.getActiveSubTasks(null, date).size() > 0)
                indicatorsPanel.add(taskB, null);
            if (EventsScheduler.isEventScheduled()) {
                net.sf.memoranda.Event ev = EventsScheduler.getFirstScheduledEvent();
                if (ev != null) {
                    alarmB.setToolTipText(ev.getTimeString() + " - " + ev.getText());
                }
                indicatorsPanel.add(alarmB, null);
            }
        }
        indicatorsPanel.updateUI();
    }

    public void updateIndicators() {
        updateIndicators(CurrentDate.get(), CurrentProject.getTaskList());
    }

    public void selectPanel(String pan) {
        if (calendar.jnCalendar.renderer.getTask() != null) {
            calendar.jnCalendar.renderer.setTask(null);
            //   calendar.jnCalendar.updateUI();
        }
        if (pan.equals("TASKS") && (tasksPanel.taskTable.getSelectedRow() > -1)) {
            Task t =
                    CurrentProject.getTaskList().getTask(
                            tasksPanel
                                    .taskTable
                                    .getModel()
                                    .getValueAt(tasksPanel.taskTable.getSelectedRow(), TaskTable.TASK_ID)
                                    .toString());
            calendar.jnCalendar.renderer.setTask(t);
            //     calendar.jnCalendar.updateUI();
        }
        boolean isAg = pan.equals("AGENDA");
        agendaPanel.setActive(isAg);
        if (isAg)
            agendaPanel.refresh(CurrentDate.get());
        cardLayout1.show(editorsPanel, pan);
        cardLayout2.show(mainTabsPanel, pan + "TAB");
        calendar.jnCalendar.updateUI();
        CurrentPanel = pan;
    }

    public String getCurrentPanel() {
        return CurrentPanel;
    }

    void taskB_actionPerformed(ActionEvent e) {
        parentPanel.tasksBActionPerformed(null);
    }

    void alarmB_actionPerformed(ActionEvent e) {
        parentPanel.eventsBActionPerformed(null);
    }
}