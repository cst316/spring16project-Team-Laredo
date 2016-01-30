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
//import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;

public class TaskDialog extends JDialog 
{
	JPanel panDialogTitle = new JPanel();
		JLabel lblNewTask = new JLabel("New Task");
	JPanel panMain = new JPanel();
		JPanel panArea = new JPanel();
			JPanel panText = new JPanel();
				JLabel lblTaskName = new JLabel("Task Name");
				JTextField tfTaskName;
				JLabel lblDescription = new JLabel("Description");
				JTextArea taDescription = new JTextArea();
			JPanel panInfo = new JPanel();
				JPanel panDates = new JPanel();
					JPanel panStartDate = new JPanel();
						JLabel lblStartDate = new JLabel("Start Date");
						JSpinner spnStartDate = new JSpinner();
						JButton btnStartCal = new JButton("");
					JPanel panEndDate = new JPanel();
						JCheckBox chkEndDate = new JCheckBox("");
						JLabel lblEndDate = new JLabel("End Date");
						JSpinner spnEndDate = new JSpinner();
						JButton btnEndCal = new JButton("");
				JPanel panEffort = new JPanel();
					JLabel lblEffort = new JLabel("Est Effort (Hrs)");
					JTextField tfEffort;
				JPanel panPhase = new JPanel();
					JLabel lblPhase = new JLabel("Phase");
					JComboBox cmbPhase = new JComboBox();
				JPanel panPriority = new JPanel();
					JLabel lblPriority = new JLabel("Priority");
					JComboBox cmbPriority = new JComboBox();
				JPanel panNotifProg = new JPanel();
					JPanel panNotify = new JPanel();
						JButton btnNotify = new JButton("Set Notification");
					JPanel panProgress = new JPanel();
						JLabel lblProgress = new JLabel("Progress");
						JSpinner spnProgress = new JSpinner();
		JPanel panButtons = new JPanel();
			JButton btnOK = new JButton("OK");
			JButton btnCancel = new JButton("Cancel");
	CalendarFrame startCalFrame = new CalendarFrame();
    CalendarFrame endCalFrame = new CalendarFrame();
    String[] priority = {Local.getString("Lowest"), Local.getString("Low"),
        Local.getString("Normal"), Local.getString("High"),
        Local.getString("Highest")};
    boolean ignoreStartChanged = false;
    boolean ignoreEndChanged = false;
    public boolean CANCELLED = true;
    
  //Forbid to set dates outside the bounds
  	CalendarDate startDateMin = CurrentProject.get().getStartDate();
  	CalendarDate startDateMax = CurrentProject.get().getEndDate();
  	CalendarDate endDateMin = startDateMin;
  	CalendarDate endDateMax = startDateMax;
	
  	public TaskDialog(Frame frame, String title) {
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
  	
	public void jbInit() throws Exception
	{
		setPreferredSize(new Dimension(450, 475));
		setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panDialogTitle.setBorder(new EmptyBorder(0, 5, 0, 5));
		panDialogTitle.setBackground(Color.WHITE);
		getContentPane().add(panDialogTitle, BorderLayout.NORTH);
		panDialogTitle.setLayout(new BorderLayout(0, 0));
		
		lblNewTask.setBorder(new EmptyBorder(5, 0, 5, 0));
		lblNewTask.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewTask.setForeground(new Color(0, 0, 124));
		lblNewTask.setFont(new Font("Dialog", lblNewTask.getFont().getStyle(), 20));
		lblNewTask.setIcon(new ImageIcon(TaskDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/task48.png")));
		panDialogTitle.add(lblNewTask, BorderLayout.WEST);
		
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setLayout(new BorderLayout(0, 0));
		
		panArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panMain.add(panArea, BorderLayout.CENTER);
		panArea.setLayout(new BorderLayout(0, 0));
		
		panText.setBorder(new EmptyBorder(2, 2, 2, 2));
		panArea.add(panText, BorderLayout.NORTH);
		GridBagLayout gbl_panText = new GridBagLayout();
		gbl_panText.rowHeights = new int[] {0, 0, 0, 142};
		gbl_panText.columnWidths = new int[] {414};
		gbl_panText.columnWeights = new double[]{0.0};
		gbl_panText.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		panText.setLayout(gbl_panText);
		
		GridBagConstraints gbc_lblTaskName = new GridBagConstraints();
		gbc_lblTaskName.anchor = GridBagConstraints.WEST;
		gbc_lblTaskName.insets = new Insets(0, 0, 5, 0);
		gbc_lblTaskName.gridx = 0;
		gbc_lblTaskName.gridy = 0;
		panText.add(lblTaskName, gbc_lblTaskName);
		
		tfTaskName = new JTextField();
		tfTaskName.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbc_tfTaskName = new GridBagConstraints();
		gbc_tfTaskName.insets = new Insets(0, 0, 5, 0);
		gbc_tfTaskName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTaskName.gridx = 0;
		gbc_tfTaskName.gridy = 1;
		panText.add(tfTaskName, gbc_tfTaskName);
		tfTaskName.setColumns(10);
		
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.anchor = GridBagConstraints.WEST;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 0);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 2;
		panText.add(lblDescription, gbc_lblDescription);
		
		taDescription.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		taDescription.setPreferredSize(new Dimension(175, 150));
		GridBagConstraints gbc_taDescription = new GridBagConstraints();
		gbc_taDescription.gridheight = 0;
		gbc_taDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_taDescription.gridx = 0;
		gbc_taDescription.gridy = 3;
		panText.add(taDescription, gbc_taDescription);
		
		panArea.add(panInfo, BorderLayout.CENTER);
		panInfo.setLayout(new BorderLayout(0, 0));
		
		panInfo.add(panDates, BorderLayout.NORTH);
		panDates.setLayout(new BorderLayout(0, 0));
		
		panDates.add(panStartDate, BorderLayout.WEST);
		panStartDate.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panStartDate.add(lblStartDate);
		
		spnStartDate.setPreferredSize(new Dimension(80, 24));
		spnStartDate.setMaximumSize(new Dimension(20, 32767));
		spnStartDate.setMinimumSize(new Dimension(20, 20));
		spnStartDate.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_WEEK));
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
		// //Added by (jcscoobyrs) on 14-Nov-2003 at 10:45:16 PM
		spnStartDate.setEditor(new JSpinner.DateEditor(spnStartDate, sdf.toPattern()));

        spnStartDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	// it's an ugly hack so that the spinner can increase day by day
            	SpinnerDateModel sdm = new SpinnerDateModel((Date)spnStartDate.getModel().getValue(),null,null,Calendar.DAY_OF_WEEK);
            	spnStartDate.setModel(sdm);

                if (ignoreStartChanged)
                    return;
                ignoreStartChanged = true;
                Date sd = (Date) spnStartDate.getModel().getValue();
                Date ed = (Date) spnEndDate.getModel().getValue();
                if (sd.after(ed) && chkEndDate.isSelected()) {
                    spnStartDate.getModel().setValue(ed);
                    sd = ed;
                }
				if ((startDateMax != null) && sd.after(startDateMax.getDate())) {
					spnStartDate.getModel().setValue(startDateMax.getDate());
                    sd = startDateMax.getDate();
				}
                if ((startDateMin != null) && sd.before(startDateMin.getDate())) {
                    spnStartDate.getModel().setValue(startDateMin.getDate());
                    sd = startDateMin.getDate();
                }
                startCalFrame.cal.set(new CalendarDate(sd));
                ignoreStartChanged = false;
            }
        });
		panStartDate.add(spnStartDate);
		
		btnStartCal.setMargin(new Insets(2, 2, 2, 2));
		btnStartCal.setIcon(new ImageIcon(TaskDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/calendar.png")));
		btnStartCal.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartCal_actionPerformed(e);
	        }
	    });
		startCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreStartChanged)
                    return;
                spnStartDate.getModel().setValue(startCalFrame.cal.get().getCalendar().getTime());
            }
        });
		panStartDate.add(btnStartCal);
		
		panDates.add(panEndDate, BorderLayout.EAST);
		
		panEndDate.add(chkEndDate);
		chkEndDate.setSelected(false);
		chkEndDate_actionPerformed(null);
		chkEndDate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkEndDate_actionPerformed(e);
			}
		});
		
		lblEndDate.setEnabled(false);
		panEndDate.add(lblEndDate);
		
		spnEndDate.setEnabled(false);
		spnEndDate.setPreferredSize(new Dimension(80, 24));
		spnEndDate.setModel(new SpinnerDateModel(new Date(1454050800000L), null, null, Calendar.DAY_OF_WEEK));
		spnEndDate.setEditor(new JSpinner.DateEditor(spnEndDate, sdf.toPattern())); //Added by (jcscoobyrs) on 14-Nov-2003 at 10:45:16PM
		spnEndDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	// it's an ugly hack so that the spinner can increase day by day
            	SpinnerDateModel sdm = new SpinnerDateModel((Date)spnEndDate.getModel().getValue(),null,null,Calendar.DAY_OF_WEEK);
            	spnEndDate.setModel(sdm);
            	
                if (ignoreEndChanged)
                    return;
                ignoreEndChanged = true;
                Date sd = (Date) spnStartDate.getModel().getValue();
                Date ed = (Date) spnEndDate.getModel().getValue();				
				if (ed.before(sd)) {
					spnEndDate.getModel().setValue(ed);
                    ed = sd;
                }
				if ((endDateMax != null) && ed.after(endDateMax.getDate())) {
					spnEndDate.getModel().setValue(endDateMax.getDate());
                    ed = endDateMax.getDate();
				}
                if ((endDateMin != null) && ed.before(endDateMin.getDate())) {
                	spnEndDate.getModel().setValue(endDateMin.getDate());
                    ed = endDateMin.getDate();
                }
				endCalFrame.cal.set(new CalendarDate(ed));
                ignoreEndChanged = false;
            }
        });
		panEndDate.add(spnEndDate);
		
		btnEndCal.setEnabled(false);
		btnEndCal.setMargin(new Insets(2, 2, 2, 2));
		btnEndCal.setIcon(new ImageIcon(TaskDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/calendar.png")));
		btnEndCal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	btnEndCal_actionPerformed(e);
            }
        });
        endCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreEndChanged)
                    return;
                spnEndDate.getModel().setValue(endCalFrame.cal.get().getCalendar().getTime());
            }
        });
		panEndDate.add(btnEndCal);
		
		panEffort.setPreferredSize(new Dimension(135, 10));
		panInfo.add(panEffort, BorderLayout.WEST);
		panEffort.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblEffort.setMinimumSize(new Dimension(36, 16));
		lblEffort.setMaximumSize(new Dimension(36, 16));
		panEffort.add(lblEffort);
		
		tfEffort = new JTextField();
		tfEffort.setPreferredSize(new Dimension(4, 25));
		panEffort.add(tfEffort);
		tfEffort.setColumns(3);
		
		panPhase.setPreferredSize(new Dimension(100, 10));
		panInfo.add(panPhase, BorderLayout.CENTER);
		
		panPhase.add(lblPhase);
		
		cmbPhase.setModel(new DefaultComboBoxModel(new String[] {"Planning", "Design", "Coding", "Review", "Compile", "Testing", "Postmortem", "None"}));
		cmbPhase.setSelectedIndex(7);
		cmbPhase.setPreferredSize(new Dimension(100, 25));
		panPhase.add(cmbPhase);
		
		panPriority.setPreferredSize(new Dimension(135, 10));
		panInfo.add(panPriority, BorderLayout.EAST);
		
		panPriority.add(lblPriority);
		
		cmbPriority.setMaximumRowCount(7);
		cmbPriority.setModel(new DefaultComboBoxModel(new String[] {"Lowest", "Low", "Normal", "High", "Highest"}));
		cmbPriority.setPreferredSize(new Dimension(80, 25));
		panPriority.add(cmbPriority);
		
		panInfo.add(panNotifProg, BorderLayout.SOUTH);
		panNotifProg.setLayout(new BorderLayout(0, 0));
		
		
		panNotifProg.add(panNotify, BorderLayout.WEST);
		
		btnNotify.setIcon(new ImageIcon(TaskDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/notify.png")));
		btnNotify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	btnNotify_actionPerformed(e);
            }
        });
		panNotify.add(btnNotify);
		
		
		panNotifProg.add(panProgress, BorderLayout.EAST);
		
		lblProgress.setPreferredSize(new Dimension(53, 26));
		panProgress.add(lblProgress);
		
		spnProgress.setPreferredSize(new Dimension(50, 26));
		spnProgress.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		panProgress.add(spnProgress);
		
		
		panMain.add(panButtons, BorderLayout.SOUTH);
		
		panButtons.add(btnCancel);
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	btnCancel_actionPerformed(e);
            }
        });

		panButtons.add(btnOK);
		btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnOK_actionPerformed(e);
            }
        });
	}
	
	public void setStartDate(CalendarDate d) {
		this.spnStartDate.getModel().setValue(d.getDate());
	}
	
	public void setEndDate(CalendarDate d) {		
		if (d != null) 
			this.spnEndDate.getModel().setValue(d.getDate());
	}
	
	public void setStartDateLimit(CalendarDate min, CalendarDate max) {
		this.startDateMin = min;
		this.startDateMax = max;
	}
	
	public void setEndDateLimit(CalendarDate min, CalendarDate max) {
		this.endDateMin = min;
		this.endDateMax = max;
	}
	
    void btnOK_actionPerformed(ActionEvent e) {
	CANCELLED = false;
        this.dispose();
    }

    void btnCancel_actionPerformed(ActionEvent e) {
        this.dispose();
    }
	
	void chkEndDate_actionPerformed(ActionEvent e) {
		spnEndDate.setEnabled(chkEndDate.isSelected());
		btnEndCal.setEnabled(chkEndDate.isSelected());
		lblEndDate.setEnabled(chkEndDate.isSelected());
		if(chkEndDate.isSelected()) {
			Date currentEndDate = (Date) spnEndDate.getModel().getValue();
			Date currentStartDate = (Date) spnStartDate.getModel().getValue();
			if(currentEndDate.getTime() < currentStartDate.getTime()) {
				spnEndDate.getModel().setValue(currentStartDate);
			}
		}
	}

    void btnStartCal_actionPerformed(ActionEvent e) {
        startCalFrame.setLocation(btnStartCal.getLocation());
        startCalFrame.setSize(200, 200);
        this.getLayeredPane().add(startCalFrame);
        startCalFrame.show();
    }

    void btnEndCal_actionPerformed(ActionEvent e) {
        endCalFrame.setLocation(btnEndCal.getLocation());
        endCalFrame.setSize(200, 200);
        this.getLayeredPane().add(endCalFrame);
        endCalFrame.show();
    }
    
    void btnNotify_actionPerformed(ActionEvent e) {
    	((AppFrame)App.getFrame()).workPanel.dailyItemsPanel.eventsPanel.newEventB_actionPerformed(e, 
			this.tfTaskName.getText(), (Date)spnStartDate.getModel().getValue(),(Date)spnEndDate.getModel().getValue());
    }

}