package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Stopwatch;
import net.sf.memoranda.Task;
import net.sf.memoranda.util.CurrentStorage;

import java.awt.Panel;
import java.awt.Point;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class StopwatchDialog extends JDialog 
{
	private Collection<Task> tasks;
	private Task currentTask;
	public Stopwatch stopwatch;
	public boolean isRunning = false;
	JPanel mainPanel = new JPanel();
	Panel txtPanel = new Panel();
	JTextField txtTime = new JTextField();
	Panel comboPanel = new Panel();
	JComboBox<String> comboBox;
	Panel buttonPanel = new Panel();
	JButton btnRestart = new JButton("");
	JButton btnStartStop = new JButton("");
	JButton btnAdd = new JButton("");
	JButton btnRemove = new JButton("");
	JPanel panDialogTitle = new JPanel();
	public StopwatchDialog(Frame frame) {
		super(frame, "Stopwatch");
		getContentPane().setMaximumSize(new Dimension(315, 155));
		getContentPane().setMinimumSize(new Dimension(315, 155));
		setMaximumSize(new Dimension(315, 155));
		setMinimumSize(new Dimension(315, 155));
		setSize(new Dimension(315, 155));
		setResizable(false);
		addWindowListener(new WindowAdapter(){
	        @Override
	        public void windowClosing(WindowEvent e){
	        	updateTasksAcutualTime(stopwatch.getTime(TimeUnit.MILLISECONDS));
	        }
		});
		
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		mainPanel.add(txtPanel, BorderLayout.NORTH);
		txtTime.setEditable(false);
	
		txtPanel.add(txtTime);
		txtTime.setMinimumSize(new Dimension(400, 40));
		txtTime.setMaximumSize(new Dimension(400, 40));
		txtTime.setPreferredSize(new Dimension(400, 40));
		txtTime.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtTime.setHorizontalAlignment(SwingConstants.CENTER);
		txtTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
	
		txtTime.setColumns(10);
		
		mainPanel.add(comboPanel, BorderLayout.CENTER);
		comboPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tasks = CurrentProject.getTaskList().getAllTasks();
		String[] listOfTasks = new String[tasks.size()];
		int counter = 0;
		for(Task t: tasks){
			listOfTasks[counter] = t.getText();
			counter++;
		}
		currentTask = (Task)tasks.toArray()[0];
		stopwatch = new Stopwatch(currentTask.getActEffort(), TimeUnit.MILLISECONDS);
		txtTime.setText(stopwatch.getTimeString());
		comboBox = new JComboBox<String>(listOfTasks);
		comboBox.setPreferredSize(new Dimension(200, 20));
		comboBox.setMaximumSize(new Dimension(200, 20));
		comboBox.setMinimumSize(new Dimension(200, 20));
		comboBox.addItemListener(new ItemChangeListener());
		comboPanel.add(comboBox);
		comboBox.setBorder(null);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		btnRestart.setToolTipText("Restart");
		btnRestart.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/restart.png")));
		btnRestart.setMargin(new Insets(2, 4, 2, 4));
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRestartActionPerformed(e);
	        }
	    });
		buttonPanel.add(btnRestart);
		
		btnStartStop.setToolTipText("Start");
		btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/play.png")));
		btnStartStop.setMargin(new Insets(2, 4, 2, 4));
		btnStartStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartStopActionPerformed(e);
	        }
	    });
		buttonPanel.add(btnStartStop);
		
		btnAdd.setToolTipText("Add");
		btnAdd.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/add.png")));
		btnAdd.setMargin(new Insets(2, 4, 2, 4));
		buttonPanel.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddActionPerformed(e);
	        }
	    });
		
		btnRemove.setToolTipText("Remove");
		btnRemove.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/remove.png")));
		btnRemove.setMargin(new Insets(2, 4, 2, 4));
		buttonPanel.add(btnRemove);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRemoveActionPerformed(e);
	        }
	    });
	}
	
	final Timer updater = new Timer(500, new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	        txtTime.setText(stopwatch.getTimeString());
	    }
	});
	
	void btnStartStopActionPerformed(ActionEvent e) {
		if(isRunning) {
			stopwatch.stopStopwatch();
			updater.stop();
			btnStartStop.setToolTipText("Start");
			btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/play.png")));
			updateTasksAcutualTime(stopwatch.getTime(TimeUnit.MILLISECONDS));
			isRunning = false;
		}
		else {
			stopwatch.startStopwatch();
			updater.start();
			btnStartStop.setToolTipText("Stop");
			btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/pause.png")));
			isRunning = true;
		}
	}
	
	void btnRestartActionPerformed(ActionEvent e) {
		if(isRunning) {
			updater.stop();
			btnStartStop.setToolTipText("Start");
			btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/play.png")));
			isRunning = false;
		}
		stopwatch = new Stopwatch();
		txtTime.setText(stopwatch.getTimeString());
	}
	
	void btnAddActionPerformed(ActionEvent e) {
		AddRemoveDialog dlg = new AddRemoveDialog();
		Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
		dlg.setVisible(true);
		dlg.btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!dlg.txtValue.getText().equals("")) {
					try {
						long time = Long.parseLong(dlg.txtValue.getText());
						TimeUnit u = selectedTimeUnit(dlg.comboBox.getSelectedIndex());
						stopwatch.addTime(time, u);
						dlg.dispose();
						txtTime.setText(stopwatch.getTimeString());
					} catch (NumberFormatException ex){
						JOptionPane.showMessageDialog(null, "Input is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
	void btnRemoveActionPerformed(ActionEvent e) {
		AddRemoveDialog dlg = new AddRemoveDialog();
		Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
		dlg.setVisible(true);
		dlg.btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!dlg.txtValue.getText().equals("")) {
					try {
						long time = Long.parseLong(dlg.txtValue.getText());
						TimeUnit u = selectedTimeUnit(dlg.comboBox.getSelectedIndex());
						stopwatch.removeTime(time, u);
						dlg.dispose();
						txtTime.setText(stopwatch.getTimeString());
					} catch (NumberFormatException ex){
						JOptionPane.showMessageDialog(null, "Input is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
	class ItemChangeListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(isRunning) {
				stopwatch.stopStopwatch();
				btnStartStop.setToolTipText("Start");
				btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/play.png")));
				isRunning = false;
				updater.stop();
			}
			updateTasksAcutualTime(stopwatch.getTime(TimeUnit.MILLISECONDS));
			currentTask = (Task) tasks.toArray()[comboBox.getSelectedIndex()];
			stopwatch = new Stopwatch(currentTask.getActEffort(), TimeUnit.MILLISECONDS);
			txtTime.setText(stopwatch.getTimeString());
		}	
	}
	
	private void updateTasksAcutualTime(long actualTime){
		currentTask.setActEffort(actualTime);
		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
	}
	
	private TimeUnit selectedTimeUnit(int selectedIndex){
		TimeUnit u = TimeUnit.SECONDS;
		switch(selectedIndex) {
		case 0:
			u = TimeUnit.SECONDS;
			break;
		case 1:
			u = TimeUnit.MINUTES;
			break;
		case 2:
			u = TimeUnit.HOURS;
			break;
		default:
			break;
		}
		return u;
	}
}