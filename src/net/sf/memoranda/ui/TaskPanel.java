package net.sf.memoranda.ui;

import net.sf.memoranda.*;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;

/*$Id: TaskPanel.java,v 1.27 2007/01/17 20:49:12 killerjoe Exp $*/
@SuppressWarnings("serial")
class TaskPanel extends JPanel {
    final TaskTable taskTable = new TaskTable();
    private final Dimension buttonDimension = new Dimension(24, 24);
    private final ImageIcon newTaskImage = new ImageIcon(AppFrame.class.getResource
            ("resources/icons/todo_new.png"));
    private final ImageIcon newSubTaskImage = new ImageIcon(AppFrame.class.getResource
            ("resources/icons/todo_new_sub.png"));
    private final ImageIcon editTaskImage = new ImageIcon(AppFrame.class.getResource
            ("resources/icons/todo_edit.png"));
    private final ImageIcon removeTaskImage = new ImageIcon(AppFrame.class.getResource
            ("resources/icons/todo_remove.png"));
    private final ImageIcon completeTaskImage = new ImageIcon(AppFrame.class.getResource
            ("resources/icons/todo_complete.png"));
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JButton historyBackB = new JButton();
    private final JToolBar tasksToolBar = new JToolBar();
    private final JButton historyForwardB = new JButton();
    private final JButton newTaskB = new JButton();
    private final JButton subTaskB = new JButton();
    private final JButton editTaskB = new JButton();
    private final JButton removeTaskB = new JButton();
    private final JButton completeTaskB = new JButton();
    private final JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();
    private final JScrollPane scrollPane = new JScrollPane();
    private final JMenuItem ppEditTask = new JMenuItem();
    private final JPopupMenu taskPPMenu = new JPopupMenu();
    private final JMenuItem ppRemoveTask = new JMenuItem();
    private final JMenuItem ppNewTask = new JMenuItem();
    private final JMenuItem ppCompleteTask = new JMenuItem();
    private final JMenuItem ppAddSubTask = new JMenuItem();
    private final JMenuItem ppCalcTask = new JMenuItem();
    private DailyItemsPanel parentPanel = null;

    public TaskPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() {
        tasksToolBar.setFloatable(false);

        buttonInitialization(historyBackB, Local.getString("History back"));
        historyBackB.setAction(History.historyBackAction);

        buttonInitialization(historyForwardB, Local.getString("History forward"));
        historyForwardB.setAction(History.historyForwardAction);

        buttonInitialization(newTaskB, Local.getString("Create new task"));
        newTaskB.setIcon(newTaskImage);
        newTaskB.addActionListener(e -> newTaskB_actionPerformed());

        buttonInitialization(subTaskB, Local.getString("Add subtask"));
        subTaskB.setIcon(newSubTaskImage);
        subTaskB.addActionListener(e -> addSubTask_actionPerformed());

        buttonInitialization(editTaskB, Local.getString("Edit task"));
        editTaskB.setIcon(editTaskImage);
        editTaskB.addActionListener(e -> editTaskB_actionPerformed());

        buttonInitialization(removeTaskB, Local.getString("Remove task"));
        removeTaskB.setIcon(removeTaskImage);
        removeTaskB.addActionListener(e -> removeTaskB_actionPerformed());

        buttonInitialization(completeTaskB, Local.getString("Complete task"));
        completeTaskB.setIcon(completeTaskImage);
        completeTaskB.addActionListener(e -> ppCompleteTask_actionPerformed());

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        taskPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));

        ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
        ppShowActiveOnlyChB.setText(
                Local.getString("Show Active only"));
        ppShowActiveOnlyChB
                .addActionListener(e -> toggleShowActiveOnly_actionPerformed());
        boolean isShao =
                (Context.get("SHOW_ACTIVE_TASKS_ONLY") != null)
                        && (Context.get("SHOW_ACTIVE_TASKS_ONLY").equals("true"));
        ppShowActiveOnlyChB.setSelected(isShao);
        toggleShowActiveOnly_actionPerformed();


        menuItemInitialization(ppEditTask, Local.getString("Edit task") + "...", editTaskImage);
        ppEditTask.addActionListener(e -> editTaskB_actionPerformed());

        menuItemInitialization(ppRemoveTask, Local.getString("Remove task"), removeTaskImage);
        ppRemoveTask.addActionListener(e -> removeTaskB_actionPerformed());

        menuItemInitialization(ppNewTask, Local.getString("New task") + "...", newTaskImage);
        ppNewTask.addActionListener(e -> newTaskB_actionPerformed());

        menuItemInitialization(ppAddSubTask, Local.getString("Add subtask"), newSubTaskImage);
        ppAddSubTask.addActionListener(e -> addSubTask_actionPerformed());

        menuItemInitialization(ppCompleteTask, Local.getString("Complete task"), completeTaskImage);
        ppCompleteTask.addActionListener(e -> ppCompleteTask_actionPerformed());

        menuItemInitialization(ppCalcTask, Local.getString("Calculate task data"), completeTaskImage);
        ppCalcTask.addActionListener(e -> ppCalcTask_actionPerformed());


        scrollPane.getViewport().add(taskTable, null);
        this.add(scrollPane, BorderLayout.CENTER);
        tasksToolBar.add(historyBackB, null);
        tasksToolBar.add(historyForwardB, null);
        tasksToolBar.addSeparator(new Dimension(8, 24));

        tasksToolBar.add(newTaskB, null);
        tasksToolBar.add(subTaskB, null);
        tasksToolBar.add(removeTaskB, null);
        tasksToolBar.addSeparator(new Dimension(8, 24));
        tasksToolBar.add(editTaskB, null);
        tasksToolBar.add(completeTaskB, null);

        this.add(tasksToolBar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        taskTable.addMouseListener(ppListener);


        CurrentDate.addDateListener(d -> newTaskB.setEnabled(d.inPeriod(CurrentProject.get().getStartDate(),
                CurrentProject.get().getEndDate())));

        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange() {
                newTaskB.setEnabled(
                        CurrentDate.get().inPeriod(CurrentProject.get().getStartDate(), CurrentProject.get().getEndDate()));
            }

            public void projectWasChanged() {
            }
        });

        taskTable.getSelectionModel().addListSelectionListener(e -> {
            boolean enbl = (taskTable.getRowCount() > 0) && (taskTable.getSelectedRow() > -1);
            editTaskB.setEnabled(enbl);
            ppEditTask.setEnabled(enbl);
            removeTaskB.setEnabled(enbl);
            ppRemoveTask.setEnabled(enbl);

            ppCompleteTask.setEnabled(enbl);
            completeTaskB.setEnabled(enbl);
            ppAddSubTask.setEnabled(enbl);
            ppCalcTask.setEnabled(enbl);

            if (enbl) {
                String thisTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();
                boolean hasSubTasks = CurrentProject.getTaskList().hasSubTasks(thisTaskId);
                ppCalcTask.setEnabled(hasSubTasks);
                Task t = CurrentProject.getTaskList().getTask(thisTaskId);
                parentPanel.calendar.jnCalendar.renderer.setTask(t);
                parentPanel.calendar.jnCalendar.updateUI();
            } else {
                parentPanel.calendar.jnCalendar.renderer.setTask(null);
                parentPanel.calendar.jnCalendar.updateUI();
            }
        });

        editTaskB.setEnabled(false);
        removeTaskB.setEnabled(false);
        completeTaskB.setEnabled(false);
        ppAddSubTask.setEnabled(false);

        taskPPMenu.add(ppEditTask);

        taskPPMenu.addSeparator();
        taskPPMenu.add(ppNewTask);
        taskPPMenu.add(ppAddSubTask);
        taskPPMenu.add(ppRemoveTask);

        taskPPMenu.addSeparator();
        taskPPMenu.add(ppCompleteTask);
        taskPPMenu.add(ppCalcTask);

        taskPPMenu.addSeparator();
        taskPPMenu.add(ppShowActiveOnlyChB);


        // define key actions in TaskPanel:
        // - KEY:DELETE => delete tasks (recursivly).
        // - KEY:INTERT => insert new Subtask if another is selected.
        // - KEY:INSERT => insert new Task if nothing is selected.
        // - KEY:SPACE => finish Task.
        taskTable.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (taskTable.getSelectedRows().length > 0
                        && e.getKeyCode() == KeyEvent.VK_DELETE)
                    removeTaskB_actionPerformed();

                else if (e.getKeyCode() == KeyEvent.VK_INSERT) {
                    if (taskTable.getSelectedRows().length > 0) {
                        addSubTask_actionPerformed();
                    } else {
                        newTaskB_actionPerformed();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE
                        && taskTable.getSelectedRows().length > 0) {
                    ppCompleteTask_actionPerformed();
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

    }

    private void editTaskB_actionPerformed() {
        Task t =
                CurrentProject.getTaskList().getTask(
                        taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString());
        TaskDialog dlg = createTaskDialog(Local.getString("Edit task"));
        dlg.tfTaskName.setText(t.getText());
        dlg.taDescription.setText(t.getDescription());
        dlg.spnStartDate.getModel().setValue(t.getStartDate().getDate());
        dlg.spnEndDate.getModel().setValue(t.getEndDate().getDate());
        dlg.cmbPriority.setSelectedIndex(t.getPriority());
        dlg.cmbPhase.setSelectedIndex(t.getPhase());
        dlg.tfEffort.setText(Util.getHoursFromMillis(t.getEstEffort()));
        if (t.getEndDate().getDate().equals(t.getStartDate().getDate()))
            dlg.chkEndDate.setSelected(false);
        else
            dlg.chkEndDate.setSelected(true);
        dlg.spnProgress.setValue(t.getProgress());
        dlg.chkEndDate_actionPerformed(null);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        CalendarDate sd = new CalendarDate((Date) dlg.spnStartDate.getModel().getValue());
        CalendarDate ed;
        if (dlg.chkEndDate.isSelected())
            ed = new CalendarDate((Date) dlg.spnEndDate.getModel().getValue());
        else
            ed = null;
        t.setStartDate(sd);
        t.setEndDate(ed);
        t.setText(dlg.tfTaskName.getText());
        t.setDescription(dlg.taDescription.getText());
        t.setPriority(dlg.cmbPriority.getSelectedIndex());
        t.setPhase(dlg.cmbPhase.getSelectedIndex());
        t.setEstEffort(Util.getMillisFromHours(dlg.tfEffort.getText()));
        t.setProgress((Integer) dlg.spnProgress.getValue());

        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
        parentPanel.updateIndicators();
    }

    private void newTaskB_actionPerformed() {
        TaskDialog dlg = createTaskDialog(Local.getString("New task"));
        dlg.spnStartDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.spnEndDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        CalendarDate sd = new CalendarDate((Date) dlg.spnStartDate.getModel().getValue());
        CalendarDate ed;
        if (dlg.chkEndDate.isSelected())
            ed = new CalendarDate((Date) dlg.spnEndDate.getModel().getValue());
        else
            ed = null;
        long effort = Util.getMillisFromHours(dlg.tfEffort.getText());
        Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.tfTaskName.getText(), dlg.cmbPriority.getSelectedIndex(), dlg.cmbPhase.getSelectedIndex(), effort, dlg.taDescription.getText(), null);
        newTask.setProgress((Integer) dlg.spnProgress.getValue());
        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
        parentPanel.updateIndicators();
    }

    private void addSubTask_actionPerformed() {
        TaskDialog dlg = createTaskDialog(Local.getString("New Task"));
        try {
            String parentTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();
            Task parent = CurrentProject.getTaskList().getTask(parentTaskId);
            CalendarDate todayD = CurrentDate.get();
            if (todayD.after(parent.getStartDate()))
                dlg.setStartDate(todayD);
            else
                dlg.setStartDate(parent.getStartDate());
            if (parent.getEndDate() != null)
                dlg.setEndDate(parent.getEndDate());
            else
                dlg.setEndDate(CurrentProject.get().getEndDate());
            dlg.setStartDateLimit(parent.getStartDate(), parent.getEndDate());
            dlg.setEndDateLimit(parent.getStartDate(), parent.getEndDate());
            dlg.setVisible(true);
            if (dlg.CANCELLED)
                return;
            CalendarDate sd = new CalendarDate((Date) dlg.spnStartDate.getModel().getValue());
            CalendarDate ed;
            if (dlg.chkEndDate.isSelected())
                ed = new CalendarDate((Date) dlg.spnEndDate.getModel().getValue());
            else
                ed = null;
            long effort = Util.getMillisFromHours(dlg.tfEffort.getText());
            Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.tfTaskName.getText(), dlg.cmbPriority.getSelectedIndex(), dlg.cmbPhase.getSelectedIndex(), effort, dlg.taDescription.getText(), parentTaskId);
            newTask.setProgress((Integer) dlg.spnProgress.getValue());

            CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
            taskTable.tableChanged();
            parentPanel.updateIndicators();
        } catch (NullPointerException exception) {
            return;
        }
    }

    private void calcTask_actionPerformed() {
        TaskCalcDialog dlg = new TaskCalcDialog(App.getFrame());
        dlg.pack();
        Task t = CurrentProject.getTaskList().getTask(taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString());

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED) {
            return;
        }

        TaskList tl = CurrentProject.getTaskList();
        if (dlg.calcEffortChB.isSelected()) {
            t.setEstEffort(tl.calculateTotalEstimatedEffortFromSubTasks(t));
        }

        if (dlg.compactDatesChB.isSelected()) {
            t.setStartDate(tl.getEarliestStartDateFromSubTasks(t));
            t.setEndDate(tl.getLatestEndDateFromSubTasks(t));
        }

        if (dlg.calcCompletionChB.isSelected()) {
            long[] res = tl.calculateCompletionFromSubTasks(t);
            int thisProgress = (int) Math.round((((double) res[0] / (double) res[1]) * 100));
            t.setProgress(thisProgress);
        }

        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
    }

    void listSubTasks_actionPerformed() {
        taskTable.tableChanged();
    }

    private void removeTaskB_actionPerformed() {
        String msg;
        String thisTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();

        if (taskTable.getSelectedRows().length > 1)
            msg = Local.getString("Remove") + " " + taskTable.getSelectedRows().length + " " + Local.getString("tasks") + "?"
                    + "\n" + Local.getString("Are you sure?");
        else {
            Task t = CurrentProject.getTaskList().getTask(thisTaskId);
            // check if there are subtasks
            if (CurrentProject.getTaskList().hasSubTasks(thisTaskId)) {
                msg = Local.getString("Remove task") + "\n'" + t.getText() + Local.getString("' and all subtasks") + "\n" + Local.getString("Are you sure?");
            } else {
                msg = Local.getString("Remove task") + "\n'" + t.getText() + "'\n" + Local.getString("Are you sure?");
            }
        }
        int n =
                JOptionPane.showConfirmDialog(
                        App.getFrame(),
                        msg,
                        Local.getString("Remove task"),
                        JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;
        Vector<Task> toremove = new Vector<>();
        for (int i = 0; i < taskTable.getSelectedRows().length; i++) {
            Task t =
                    CurrentProject.getTaskList().getTask(
                            taskTable.getModel().getValueAt(taskTable.getSelectedRows()[i], TaskTable.TASK_ID).toString());
            if (t != null)
                toremove.add(t);
        }
        for (Task aToremove : toremove) {
            CurrentProject.getTaskList().removeTask(aToremove);
        }
        taskTable.tableChanged();
        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        parentPanel.updateIndicators();
    }

    private void ppCompleteTask_actionPerformed() {
        Vector<Task> tocomplete = new Vector<>();
        for (int i = 0; i < taskTable.getSelectedRows().length; i++) {
            Task t =
                    CurrentProject.getTaskList().getTask(
                            taskTable.getModel().getValueAt(taskTable.getSelectedRows()[i], TaskTable.TASK_ID).toString());
            if (t != null)
                tocomplete.add(t);
        }
        for (Task t : tocomplete) {
            t.setProgress(100);
        }
        taskTable.tableChanged();
        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        parentPanel.updateIndicators();
    }

    // toggle "show active only"
    private void toggleShowActiveOnly_actionPerformed() {
        Context.put(
                "SHOW_ACTIVE_TASKS_ONLY",
                Boolean.valueOf(ppShowActiveOnlyChB.isSelected()));
        taskTable.tableChanged();
    }

    private void ppCalcTask_actionPerformed() {
        calcTask_actionPerformed();
    }

    private void buttonInitialization(JButton button, String toolTipText) {
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setRequestFocusEnabled(false);
        button.setBorderPainted(false);
        button.setToolTipText(toolTipText);
        button.setPreferredSize(buttonDimension);
        button.setMinimumSize(buttonDimension);
        button.setMaximumSize(buttonDimension);
        button.setText("");
    }

    private void menuItemInitialization(JMenuItem menuItem, String text, ImageIcon imageIcon) {
        menuItem.setFont(new java.awt.Font("Dialog", 1, 11));
        menuItem.setText(text);
        menuItem.setEnabled(false);
        menuItem.setIcon(imageIcon);
    }

    private TaskDialog createTaskDialog(String windowHeader) {
        TaskDialog taskDialog = new TaskDialog(App.getFrame(), windowHeader);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        taskDialog.setLocation((frmSize.width - taskDialog.getSize().width) / 2 + loc.x,
                (frmSize.height - taskDialog.getSize().height) / 2 + loc.y);
        return taskDialog;
    }

    class PopupListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if ((e.getClickCount() == 2) && (taskTable.getSelectedRow() > -1)) {
                editTaskB_actionPerformed();
            }
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                taskPPMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}