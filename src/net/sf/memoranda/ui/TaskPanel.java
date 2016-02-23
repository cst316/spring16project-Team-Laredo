package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.History;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

/*$Id: TaskPanel.java,v 1.27 2007/01/17 20:49:12 killerjoe Exp $*/
@SuppressWarnings("serial")
public class TaskPanel extends JPanel {
	Dimension buttonDimension = new Dimension(24,24);
	
    ImageIcon newTaskImage = new ImageIcon(AppFrame.class.getResource
    		("resources/icons/todo_new.png"));
	ImageIcon newSubTaskImage = new ImageIcon(AppFrame.class.getResource
			("resources/icons/todo_new_sub.png"));
	ImageIcon editTaskImage = new ImageIcon(AppFrame.class.getResource
			("resources/icons/todo_edit.png"));
	ImageIcon removeTaskImage = new ImageIcon(AppFrame.class.getResource
			("resources/icons/todo_remove.png"));
	ImageIcon completeTaskImage = new ImageIcon(AppFrame.class.getResource
			("resources/icons/todo_complete.png"));
	
    BorderLayout borderLayout1 = new BorderLayout();
    JButton historyBackB = new JButton();
    JToolBar tasksToolBar = new JToolBar();
    JButton historyForwardB = new JButton();
    JButton newTaskB = new JButton();
    JButton subTaskB = new JButton();
    JButton editTaskB = new JButton();
    JButton removeTaskB = new JButton();
    JButton completeTaskB = new JButton();
    
	JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();
		
    JScrollPane scrollPane = new JScrollPane();
    TaskTable taskTable = new TaskTable();
	JMenuItem ppEditTask = new JMenuItem();
	JPopupMenu taskPPMenu = new JPopupMenu();
	JMenuItem ppRemoveTask = new JMenuItem();
	JMenuItem ppNewTask = new JMenuItem();
	JMenuItem ppCompleteTask = new JMenuItem();
	JMenuItem ppAddSubTask = new JMenuItem();
	JMenuItem ppCalcTask = new JMenuItem();
	DailyItemsPanel parentPanel = null;

    public TaskPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void jbInit() throws Exception {
        tasksToolBar.setFloatable(false);

        buttonInitialization(historyBackB, Local.getString("History back"));
        historyBackB.setAction(History.historyBackAction);
   
        buttonInitialization(historyForwardB, Local.getString("History forward"));
        historyForwardB.setAction(History.historyForwardAction);

        buttonInitialization(newTaskB, Local.getString("Create new task"));
        newTaskB.setIcon(newTaskImage);
        newTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newTaskB_actionPerformed(e);
            }
        });
       
        buttonInitialization(subTaskB, Local.getString("Add subtask"));
        subTaskB.setIcon(newSubTaskImage);
        subTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSubTask_actionPerformed(e);
            }
        });

        buttonInitialization(editTaskB, Local.getString("Edit task"));
        editTaskB.setIcon(editTaskImage);
        editTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editTaskB_actionPerformed(e);
            }
        });

        buttonInitialization(removeTaskB, Local.getString("Remove task"));
        removeTaskB.setIcon(removeTaskImage);
        removeTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeTaskB_actionPerformed(e);
            }
        });

        buttonInitialization(completeTaskB, Local.getString("Complete task"));
        completeTaskB.setIcon(completeTaskImage);
        completeTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppCompleteTask_actionPerformed(e);
            }
        });
     
        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        taskPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
		
		ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
		ppShowActiveOnlyChB.setText(
			Local.getString("Show Active only"));
		ppShowActiveOnlyChB
			.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleShowActiveOnly_actionPerformed(e);
			}
		});		
		boolean isShao =
			(Context.get("SHOW_ACTIVE_TASKS_ONLY") != null)
				&& (Context.get("SHOW_ACTIVE_TASKS_ONLY").equals("true"));
		ppShowActiveOnlyChB.setSelected(isShao);
		toggleShowActiveOnly_actionPerformed(null);

		
		menuItemInitialization(ppEditTask, Local.getString("Edit task")+ "...", editTaskImage); 
        ppEditTask.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	    editTaskB_actionPerformed(e);
            }
        });
        
        menuItemInitialization(ppRemoveTask, Local.getString("Remove task"), removeTaskImage);
        ppRemoveTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	removeTaskB_actionPerformed(e);
            }
        });

        menuItemInitialization(ppNewTask, Local.getString("New task")+ "...", newTaskImage);
        ppNewTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	newTaskB_actionPerformed(e);
            }
        });
        
        menuItemInitialization(ppAddSubTask, Local.getString("Add subtask"), newSubTaskImage);
        ppAddSubTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addSubTask_actionPerformed(e);
            }
        });

        menuItemInitialization(ppCompleteTask, Local.getString("Complete task"), completeTaskImage);
	    ppCompleteTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppCompleteTask_actionPerformed(e);
			}
		});

	    menuItemInitialization(ppCalcTask, Local.getString("Calculate task data"), completeTaskImage);
	    ppCalcTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppCalcTask_actionPerformed(e);
			}
		});


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



        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                newTaskB.setEnabled(d.inPeriod(CurrentProject.get().getStartDate(), CurrentProject.get().getEndDate()));
            }
        });
        
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(Project p, NoteList nl, TaskList tl, DefectList d1, ResourcesList rl) {
                newTaskB.setEnabled(
                    CurrentDate.get().inPeriod(p.getStartDate(), p.getEndDate()));
            }
            public void projectWasChanged() {}
        });
        
        taskTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = (taskTable.getRowCount() > 0)&&(taskTable.getSelectedRow() > -1);
                editTaskB.setEnabled(enbl);ppEditTask.setEnabled(enbl);
                removeTaskB.setEnabled(enbl);ppRemoveTask.setEnabled(enbl);
				
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
                }    
                else {
                    parentPanel.calendar.jnCalendar.renderer.setTask(null);
                    parentPanel.calendar.jnCalendar.updateUI();
                }
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
			public void keyPressed(KeyEvent e){
				if(taskTable.getSelectedRows().length>0 
					&& e.getKeyCode()==KeyEvent.VK_DELETE)
					removeTaskB_actionPerformed(null);
				
				else if(e.getKeyCode()==KeyEvent.VK_INSERT) {
					if(taskTable.getSelectedRows().length>0) {
						addSubTask_actionPerformed(null);
					}
					else {
						newTaskB_actionPerformed(null);						
					}
				}
				
				else if(e.getKeyCode()==KeyEvent.VK_SPACE
						&& taskTable.getSelectedRows().length>0) {
					ppCompleteTask_actionPerformed(null);
				}
			}
			public void	keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){} 
		});	

    }

    void editTaskB_actionPerformed(ActionEvent e) {
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
	    if(t.getEndDate().getDate().equals(t.getStartDate().getDate()))
		    dlg.chkEndDate.setSelected(false);
	    else
		    dlg.chkEndDate.setSelected(true);
	    dlg.spnProgress.setValue(new Integer(t.getProgress()));
 	    dlg.chkEndDate_actionPerformed(null);	
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        CalendarDate sd = new CalendarDate((Date) dlg.spnStartDate.getModel().getValue());
         CalendarDate ed;
 		if(dlg.chkEndDate.isSelected())
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
        t.setProgress(((Integer)dlg.spnProgress.getValue()).intValue());

        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
        parentPanel.updateIndicators();
    }

    void newTaskB_actionPerformed(ActionEvent e) {
        TaskDialog dlg = createTaskDialog(Local.getString("New task"));
        dlg.spnStartDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.spnEndDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        CalendarDate sd = new CalendarDate((Date) dlg.spnStartDate.getModel().getValue());
        CalendarDate ed;
 		if(dlg.chkEndDate.isSelected())
 			ed = new CalendarDate((Date) dlg.spnEndDate.getModel().getValue());
 		else
 			ed = null;
        long effort = Util.getMillisFromHours(dlg.tfEffort.getText());
		Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.tfTaskName.getText(), dlg.cmbPriority.getSelectedIndex(), dlg.cmbPhase.getSelectedIndex(), effort, dlg.taDescription.getText(),null);
		newTask.setProgress(((Integer)dlg.spnProgress.getValue()).intValue());
        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
        parentPanel.updateIndicators();
    }

    void addSubTask_actionPerformed(ActionEvent e) {
        TaskDialog dlg = createTaskDialog(Local.getString("New Task"));
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
 		if(dlg.chkEndDate.isSelected())
 			ed = new CalendarDate((Date) dlg.spnEndDate.getModel().getValue());
 		else
 			ed = null;
        long effort = Util.getMillisFromHours(dlg.tfEffort.getText());
		Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.tfTaskName.getText(), dlg.cmbPriority.getSelectedIndex(), dlg.cmbPhase.getSelectedIndex(), effort, dlg.taDescription.getText(),parentTaskId);
		newTask.setProgress(((Integer)dlg.spnProgress.getValue()).intValue());

		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
        parentPanel.updateIndicators();
    }

    void calcTask_actionPerformed(ActionEvent e) {
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
        if(dlg.calcEffortChB.isSelected()) {
            t.setEstEffort(tl.calculateTotalEstimatedEffortFromSubTasks(t));
        }
        
        if(dlg.compactDatesChB.isSelected()) {
            t.setStartDate(tl.getEarliestStartDateFromSubTasks(t));
            t.setEndDate(tl.getLatestEndDateFromSubTasks(t));
        }
        
        if(dlg.calcCompletionChB.isSelected()) {
            long[] res = tl.calculateCompletionFromSubTasks(t);
            int thisProgress = (int) Math.round((((double)res[0] / (double)res[1]) * 100));
            t.setProgress(thisProgress);
        }
        
        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
    }

    void listSubTasks_actionPerformed(ActionEvent e) {
		taskTable.tableChanged();
    }

    void removeTaskB_actionPerformed(ActionEvent e) {
        String msg;
        String thisTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();
        
        if (taskTable.getSelectedRows().length > 1)
            msg = Local.getString("Remove")+" "+taskTable.getSelectedRows().length +" "+Local.getString("tasks")+"?"
             + "\n"+Local.getString("Are you sure?");
        else {        	
        	Task t = CurrentProject.getTaskList().getTask(thisTaskId);
        	// check if there are subtasks
			if(CurrentProject.getTaskList().hasSubTasks(thisTaskId)) {
				msg = Local.getString("Remove task")+"\n'" + t.getText() + Local.getString("' and all subtasks") +"\n"+Local.getString("Are you sure?");
			}
			else {		            
				msg = Local.getString("Remove task")+"\n'" + t.getText() + "'\n"+Local.getString("Are you sure?");
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
        Vector<Task> toremove = new Vector<Task>();
        for (int i = 0; i < taskTable.getSelectedRows().length; i++) {
            Task t =
            CurrentProject.getTaskList().getTask(
                taskTable.getModel().getValueAt(taskTable.getSelectedRows()[i], TaskTable.TASK_ID).toString());
            if (t != null)
                toremove.add(t);
        }
        for (int i = 0; i < toremove.size(); i++) {
            CurrentProject.getTaskList().removeTask((Task)toremove.get(i));
        }
        taskTable.tableChanged();
        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        parentPanel.updateIndicators();
    }

	void ppCompleteTask_actionPerformed(ActionEvent e) {
		Vector<Task> tocomplete = new Vector<Task>();
		for (int i = 0; i < taskTable.getSelectedRows().length; i++) {
			Task t =
			CurrentProject.getTaskList().getTask(
				taskTable.getModel().getValueAt(taskTable.getSelectedRows()[i], TaskTable.TASK_ID).toString());
			if (t != null)
				tocomplete.add(t);
		}
		for (int i = 0; i < tocomplete.size(); i++) {
			Task t = (Task)tocomplete.get(i);
			t.setProgress(100);
		}
		taskTable.tableChanged();
		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
		parentPanel.updateIndicators();
	}

	// toggle "show active only"
	void toggleShowActiveOnly_actionPerformed(ActionEvent e) {
		Context.put(
			"SHOW_ACTIVE_TASKS_ONLY",
			new Boolean(ppShowActiveOnlyChB.isSelected()));
		taskTable.tableChanged();
	}

    class PopupListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
        	if ((e.getClickCount() == 2) && (taskTable.getSelectedRow() > -1)){
			    editTaskB_actionPerformed(null);
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

    void ppCalcTask_actionPerformed(ActionEvent e) {
        calcTask_actionPerformed(e);
    }
    
    void buttonInitialization(JButton button, String toolTipText){
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
    
    void menuItemInitialization(JMenuItem menuItem, String text, ImageIcon imageIcon){
    	menuItem.setFont(new java.awt.Font("Dialog", 1, 11));
    	menuItem.setText(text);
    	menuItem.setEnabled(false);
    	menuItem.setIcon(imageIcon);
    }
    
    TaskDialog createTaskDialog(String windowHeader){
    	 TaskDialog taskDialog = new TaskDialog(App.getFrame(), windowHeader);
         Dimension frmSize = App.getFrame().getSize();
         Point loc = App.getFrame().getLocation();
         taskDialog.setLocation((frmSize.width - taskDialog.getSize().width) / 2 + loc.x, 
        		 (frmSize.height - taskDialog.getSize().height) / 2 + loc.y);
         return taskDialog;
    }
}