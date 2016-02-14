package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EtchedBorder;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.date.CalendarDate;

/**
 * JDialog box that allows users to enter in information
 * about defects that are injected into a project.
 * 
 * @author Benjamin Paothatat
 * @since  12/14/2016
 */
@SuppressWarnings("serial")
public class DefectDialog extends JDialog{
  	private final SimpleDateFormat sdf = 
  			(SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT);
  	private final String[] phases = {"", "Planning", "Design", "Code", "Review", 
  			"Compile", "Testing"};
  	private final String[] types = {"", "Documentation", "Syntax", "Build", "Assignment",
  			"Interface", "Checking", "Data", "Function", "System", "Enviroment"};
  	
  	//private CalendarDate startDateMin = CurrentProject.get().getStartDate();
  	//private CalendarDate startDateMax = CurrentProject.get().getEndDate();
  	//private CalendarDate endDateMin = startDateMin;
  	//private CalendarDate endDateMax = startDateMax;
  	
  	private final int WINDOW_WIDTH = 450;
  	private final int WINDOW_HEIGHT = 450;
  	private final int TEXT_AREA_WIDTH = 150;
    private final int TEXT_AREA_HEIGHT = 150;
    private final int COMBO_BOX_WIDTH = 50;
    private final int COMBO_BOX_HEIGHT = 25;
    private final int SPINNER_WIDTH = 75;
    private final int SPINNER_HEIGHT = 25;
    private final int CAL_FRAME_WIDTH = 200;
    private final int CAL_FRAME_HEIGHT = 200;
    
    public boolean CANCELLED = false;
    public boolean noDateRemoved = true;
  			
	JPanel mainPanel = new JPanel();
	
	JPanel descriptionPanel = new JPanel();
	
	JLabel descriptionLabel = new JLabel("Description");
	JTextArea descriptionTextArea = new JTextArea();
	
	JPanel dateFoundInputPanel = new JPanel();
	
	JLabel dateFoundLabel = new JLabel("Date Found:");
	JSpinner dateFoundSpinner = new JSpinner();
	JButton dateFoundButton = new JButton();
	CalendarFrame dateFoundCalFrame = new CalendarFrame();
	
	JPanel dateRemovedInputPanel = new JPanel();
	
	JCheckBox dateRemovedCheckBox = new JCheckBox("");
	JLabel dateRemovedLabel = new JLabel("Date Removed:");
	JSpinner dateRemovedSpinner = new JSpinner();
	JButton dateRemovedButton = new JButton();
	CalendarFrame dateRemovedCalFrame = new CalendarFrame();
	
	JPanel phasesPanel = new JPanel();
	
	JLabel injectionPhaseLabel = new JLabel("Injection Phase:");
	public JComboBox<String> injectionPhaseComboBox = new JComboBox<String>(phases);
	
	JLabel removalPhaseLabel = new JLabel("Removal Phase:");
	JComboBox<String> removalPhaseComboBox = new JComboBox<String>(phases);
	
	JPanel typePanel = new JPanel();
	
	JLabel typeLabel = new JLabel("Type of Defect:");
	JComboBox<String> typeOfDefect = new JComboBox<String>(types);
	
	JPanel buttonPanel = new JPanel();
	
	JButton okButton = new JButton("Ok");
	JButton cancelButton = new JButton("Cancel");
	
    public DefectDialog(Frame frame, String title){
    	super(frame, title, true);
    	try{
    		init();
    		pack();
    	}
    	catch(Exception ex){
    		new ExceptionDialog(ex);
    	}
    }
    
    public void init(){
    	setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
    	setResizable(false);
    	getContentPane().add(mainPanel);
    	
    	mainPanel.setLayout(new GridLayout(6, 1, 0, 0));
    	
    	descriptionTextArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    	descriptionTextArea.setSize(new Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT));
    	
    	descriptionPanel.add(descriptionLabel);
    	descriptionPanel.add(descriptionTextArea);
    	
    	descriptionPanel.setLayout(new GridLayout(2, 1, 5, 5));
    	mainPanel.add(descriptionPanel);
    
    	phasesPanel.add(injectionPhaseLabel);
    	phasesPanel.add(injectionPhaseComboBox);
    	phasesPanel.add(removalPhaseLabel);
    	phasesPanel.add(removalPhaseComboBox);
    	
    	mainPanel.add(phasesPanel);
    	
    	typeOfDefect.setSize(new Dimension(COMBO_BOX_WIDTH, COMBO_BOX_HEIGHT));
  
    	
    	typePanel.add(typeLabel);
    	typePanel.add(typeOfDefect);
    	mainPanel.add(typePanel);
    	
    	dateFoundSpinner.setSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
    	dateFoundSpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_WEEK));
    	dateFoundSpinner.setEditor(new JSpinner.DateEditor(dateFoundSpinner, sdf.toPattern()));
    	
    	dateFoundButton.setIcon(new ImageIcon(TaskDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/calendar.png")));
    	dateFoundButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDateFoundCal(e);
	        }
	    });
    	
    	dateFoundCalFrame.cal.addSelectionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dateFoundSpinner.getModel().setValue(dateFoundCalFrame.cal.get().getCalendar().getTime());
			}
    		
    	});
    	
    	dateRemovedInputPanel.add(dateFoundLabel);
    	dateRemovedInputPanel.add(dateFoundSpinner);
    	dateRemovedInputPanel.add(dateFoundButton);
    	mainPanel.add(dateRemovedInputPanel);
    	
    	dateRemovedCheckBox.setSelected(false);
    	
    	dateRemovedLabel.setEnabled(false);
    	
    	dateRemovedSpinner.setEnabled(false);
    	dateRemovedSpinner.setSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
    	dateRemovedSpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_WEEK));
    	dateRemovedSpinner.setEditor(new JSpinner.DateEditor(dateFoundSpinner, sdf.toPattern()));
    	
    	dateRemovedButton.setEnabled(false);
    	dateRemovedButton.setIcon(new ImageIcon(TaskDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/calendar.png")));
    	dateRemovedButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDateRemovedCal(e);
	        }
	    });
    	
    	dateRemovedCalFrame.cal.addSelectionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			dateRemovedSpinner.getModel().setValue(dateRemovedCalFrame.cal.get().getCalendar().getTime());
    		}
    	});
    	
    	dateRemovedCheckBox.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			checkBox_actionPerformed(e);
    		}
    	});
    	
    	dateFoundInputPanel.add(dateRemovedCheckBox);
    	dateFoundInputPanel.add(dateRemovedLabel);
    	dateFoundInputPanel.add(dateRemovedSpinner);
    	dateFoundInputPanel.add(dateRemovedButton);
    	mainPanel.add(dateFoundInputPanel);
    	
    	okButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			addDefect(e);
    		}
    	});
    	
    	cancelButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			ignoreAddingDefect(e);
    		} 	
    	});

    	buttonPanel.add(okButton);
    	buttonPanel.add(cancelButton);
    	mainPanel.add(buttonPanel);
    }
    
	void checkBox_actionPerformed(ActionEvent e) {
		dateRemovedLabel.setEnabled(dateRemovedCheckBox.isSelected());
		dateRemovedSpinner.setEnabled(dateRemovedCheckBox.isSelected());
		dateRemovedButton.setEnabled(dateRemovedCheckBox.isSelected());
	}

	void openDateFoundCal(ActionEvent e) {
		dateFoundCalFrame.setSize(CAL_FRAME_WIDTH, CAL_FRAME_HEIGHT);
		this.getLayeredPane().add(dateFoundCalFrame);
		dateFoundCalFrame.show();
	}
	
	void openDateRemovedCal(ActionEvent e) {
		dateRemovedCalFrame.setSize(CAL_FRAME_WIDTH, CAL_FRAME_HEIGHT);
		this.getLayeredPane().add(dateRemovedCalFrame);
		dateRemovedCalFrame.show();
	}
	
	void addDefect(ActionEvent e) { this.dispose(); }
	
	void ignoreAddingDefect(ActionEvent e) {
		CANCELLED = true;
		this.dispose();
	}
}
