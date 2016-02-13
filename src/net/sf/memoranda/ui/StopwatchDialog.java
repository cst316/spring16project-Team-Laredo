package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
//import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Stopwatch;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;
import java.awt.CardLayout;

public class StopwatchDialog extends JDialog 
{
	public Stopwatch stopwatch = new Stopwatch();
	public boolean isRunning = false;
	JPanel mainPanel = new JPanel();
	Panel txtPanel = new Panel();
	JTextField txtTime = new JTextField();
	Panel comboPanel = new Panel();
	JComboBox comboBox = new JComboBox();
	Panel buttonPanel = new Panel();
	JButton btnRestart = new JButton("");
	JButton btnStartStop = new JButton("");
	JButton btnAdd = new JButton("");
	JButton btnRemove = new JButton("");
	JPanel panDialogTitle = new JPanel();
	public StopwatchDialog() {
		getContentPane().setMaximumSize(new Dimension(315, 155));
		getContentPane().setMinimumSize(new Dimension(315, 155));
		setMaximumSize(new Dimension(315, 155));
		setMinimumSize(new Dimension(315, 155));
		setSize(new Dimension(315, 155));
		setResizable(false);
		
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
		txtTime.setText(stopwatch.getTimeString());
		txtTime.setColumns(10);
		
		mainPanel.add(comboPanel, BorderLayout.CENTER);
		comboPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Event1 - Task1", "Event1 - Task2", "Event2 - Task1", "Event3 - Task1", "Event3 - Task2", "Event3 - Task3", "Event3 - Task4"}));
		comboBox.setPreferredSize(new Dimension(200, 20));
		comboBox.setMaximumSize(new Dimension(200, 20));
		comboBox.setMinimumSize(new Dimension(200, 20));
		comboPanel.add(comboBox);
		comboBox.setBorder(null);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		btnRestart.setToolTipText("Restart");
		btnRestart.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/restart.png")));
		btnRestart.setMargin(new Insets(2, 4, 2, 4));
		btnRestart.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRestart_actionPerformed(e);
	        }
	    });
		buttonPanel.add(btnRestart);
		
		btnStartStop.setToolTipText("Start");
		btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/play.png")));
		btnStartStop.setMargin(new Insets(2, 4, 2, 4));
		btnStartStop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartStop_actionPerformed(e);
	        }
	    });
		buttonPanel.add(btnStartStop);
		
		btnAdd.setToolTipText("Add");
		btnAdd.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/add.png")));
		btnAdd.setMargin(new Insets(2, 4, 2, 4));
		buttonPanel.add(btnAdd);
		btnAdd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAdd_actionPerformed(e);
	        }
	    });
		
		btnRemove.setToolTipText("Remove");
		btnRemove.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/remove.png")));
		btnRemove.setMargin(new Insets(2, 4, 2, 4));
		buttonPanel.add(btnRemove);
		btnRemove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRemove_actionPerformed(e);
	        }
	    });
	}
	
  	public StopwatchDialog(Frame frame, String title) {
        super(frame, title, true);
        setTitle("");
        try {
            jbInit();
            
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
  	
	public void jbInit() throws Exception {
		setPreferredSize(new Dimension(450, 475));
		setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));
		panDialogTitle.setBorder(new EmptyBorder(0, 5, 0, 5));
		panDialogTitle.setBackground(Color.WHITE);
		getContentPane().add(panDialogTitle, BorderLayout.NORTH);
		panDialogTitle.setLayout(new BorderLayout(0, 0));
	}

	
	final Timer updater = new Timer(500, new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	        txtTime.setText(stopwatch.getTimeString());
	    }
	});
	
	void btnStartStop_actionPerformed(ActionEvent e) {
		if(isRunning) {
			stopwatch.stopStopwatch();
			btnStartStop.setToolTipText("Start");
			btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/play.png")));
			isRunning = false;
			updater.restart();
		}
		else {
			stopwatch.startStopwatch();
			btnStartStop.setToolTipText("Stop");
			btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/pause.png")));
			isRunning = true;
			updater.start();
		}
	}
	
	void btnRestart_actionPerformed(ActionEvent e) {
		if(isRunning) {
			btnStartStop.setToolTipText("Start");
			btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/play.png")));
			isRunning = false;
			updater.restart();
		}
		stopwatch = new Stopwatch();
		txtTime.setText(stopwatch.getTimeString());
	}
	
	void btnAdd_actionPerformed(ActionEvent e) {
		AddRemoveDialog dlg = new AddRemoveDialog();
		Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
		dlg.setVisible(true);
		dlg.btnConfirm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!dlg.txtValue.getText().equals("")) {
					int time = Integer.parseInt(dlg.txtValue.getText());
					TimeUnit u = TimeUnit.SECONDS;
					switch(dlg.comboBox.getSelectedIndex()) {
					case 0:
						u = TimeUnit.SECONDS;
						break;
					case 1:
						u = TimeUnit.MINUTES;
						break;
					case 2:
						u = TimeUnit.HOURS;
						break;
					}
					stopwatch.addTime(time, u);
					dlg.dispose();
					txtTime.setText(stopwatch.getTimeString());
				}
			}
		});
	}
	
	void btnRemove_actionPerformed(ActionEvent e) {
		AddRemoveDialog dlg = new AddRemoveDialog();
		Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
		dlg.setVisible(true);
		dlg.btnConfirm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!dlg.txtValue.getText().equals("")) {
					int time = Integer.parseInt(dlg.txtValue.getText());
					TimeUnit u = TimeUnit.SECONDS;
					switch(dlg.comboBox.getSelectedIndex()) {
					case 0:
						u = TimeUnit.SECONDS;
						break;
					case 1:
						u = TimeUnit.MINUTES;
						break;
					case 2:
						u = TimeUnit.HOURS;
						break;
					}
					stopwatch.removeTime(time, u);
					dlg.dispose();
					txtTime.setText(stopwatch.getTimeString());
				}
			}
		});
	}
}