package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.date.CalendarDate;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

/**
 * JDialog box that allows users to enter in information
 * about defects that are injected into a project.
 * 
 * @author Benjamin Paothatat and Tyler Cole
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
  	
  	private CalendarDate foundDateMin = CurrentProject.get().getStartDate();
  	private CalendarDate foundDateMax = CurrentProject.get().getEndDate();
 	private CalendarDate removedDateMin = foundDateMin;
 	private CalendarDate removedDateMax = foundDateMax;
  	
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
    private boolean ignoreDateFoundChange = false;
    private boolean ignoreDateRemovedChange = false;
  			
	JPanel mainPanel = new JPanel();
	
	JPanel descriptionPanel = new JPanel();
	
	JLabel descriptionLabel = new JLabel("Defect Description:");
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
    JComboBox<String> injectionPhaseComboBox = new JComboBox<String> (phases);
	
	JLabel removalPhaseLabel = new JLabel("Removal Phase:");
	JComboBox<String>  removalPhaseComboBox = new JComboBox<String>(phases);
	
	JLabel typeLabel = new JLabel("Type of Defect:");
	JComboBox<String>  typeOfDefect = new JComboBox<String>(types);
	
	JPanel buttonPanel = new JPanel();
	
	JButton okButton = new JButton("Ok");
	JButton cancelButton = new JButton("Cancel");
	private final JPanel southPanel = new JPanel();
	
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
    //GridBag Stuff
    	GridBagConstraints gbc_descriptionLabel = new GridBagConstraints();
			gbc_descriptionLabel.anchor = GridBagConstraints.WEST;
			gbc_descriptionLabel.fill = GridBagConstraints.VERTICAL;
			gbc_descriptionLabel.insets = new Insets(0, 0, 5, 0);
			gbc_descriptionLabel.gridx = 0;
			gbc_descriptionLabel.gridy = 0;
		GridBagLayout gbl_descriptionPanel = new GridBagLayout();
			gbl_descriptionPanel.columnWidths = new int[]{586, 0};
			gbl_descriptionPanel.rowHeights = new int[]{32, 232, 0};
			gbl_descriptionPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_descriptionPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		GridBagConstraints gbc_descriptionTextArea = new GridBagConstraints();
	    	gbc_descriptionTextArea.fill = GridBagConstraints.BOTH;
	    	gbc_descriptionTextArea.gridx = 0;
	    	gbc_descriptionTextArea.gridy = 1;
	    GridBagLayout gbl_phasesPanel = new GridBagLayout();
    	  	gbl_phasesPanel.columnWidths = new int[]{86, 90, 82, 93, 86, 79, 0};
    	  	gbl_phasesPanel.rowHeights = new int[]{69, 0};
    	  	gbl_phasesPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    	  	gbl_phasesPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
    	GridBagConstraints gbc_injectionPhaseLabel = new GridBagConstraints();
    	  	gbc_injectionPhaseLabel.anchor = GridBagConstraints.EAST;
    	  	gbc_injectionPhaseLabel.insets = new Insets(0, 0, 0, 5);
    	  	gbc_injectionPhaseLabel.gridx = 0;
    	  	gbc_injectionPhaseLabel.gridy = 0;
    	GridBagConstraints gbc_injectionPhaseComboBox = new GridBagConstraints();
    	  	gbc_injectionPhaseComboBox.fill = GridBagConstraints.HORIZONTAL;
    	  	gbc_injectionPhaseComboBox.insets = new Insets(0, 0, 0, 5);
    	  	gbc_injectionPhaseComboBox.gridx = 1;
    	  	gbc_injectionPhaseComboBox.gridy = 0;
    	GridBagConstraints gbc_removalPhaseLabel = new GridBagConstraints();
    	  	gbc_removalPhaseLabel.anchor = GridBagConstraints.EAST;
    	  	gbc_removalPhaseLabel.insets = new Insets(0, 0, 0, 5);
    	  	gbc_removalPhaseLabel.gridx = 2;
    	  	gbc_removalPhaseLabel.gridy = 0;
    	GridBagConstraints gbc_removalPhaseComboBox = new GridBagConstraints();
    	  	gbc_removalPhaseComboBox.insets = new Insets(0, 0, 0, 5);
    	  	gbc_removalPhaseComboBox.fill = GridBagConstraints.HORIZONTAL;
    	  	gbc_removalPhaseComboBox.gridx = 3;
    	  	gbc_removalPhaseComboBox.gridy = 0;
    	GridBagConstraints gbc_typeLabel = new GridBagConstraints();
    	  	gbc_typeLabel.anchor = GridBagConstraints.EAST;
    	  	gbc_typeLabel.insets = new Insets(0, 0, 0, 5);
    	  	gbc_typeLabel.gridx = 4;
    	  	gbc_typeLabel.gridy = 0;
    	GridBagConstraints gbc_typeOfDefect = new GridBagConstraints();
    	  	gbc_typeOfDefect.anchor = GridBagConstraints.WEST;
    	  	gbc_typeOfDefect.gridx = 5;
    	  	gbc_typeOfDefect.gridy = 0;
    	GridBagLayout gbl_dateFoundInputPanel = new GridBagLayout();
	    	gbl_dateFoundInputPanel.columnWidths = new int[]{86, 94, 49, 0};
	    	gbl_dateFoundInputPanel.rowHeights = new int[]{25, 0};
	    	gbl_dateFoundInputPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
	    	gbl_dateFoundInputPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
	    GridBagConstraints gbc_dateFoundLabel = new GridBagConstraints();
	    	gbc_dateFoundLabel.insets = new Insets(0, 0, 0, 5);
	    	gbc_dateFoundLabel.gridx = 0;
	    	gbc_dateFoundLabel.gridy = 0;
	    GridBagConstraints gbc_dateFoundSpinner = new GridBagConstraints();
	    gbc_dateFoundSpinner.anchor = GridBagConstraints.EAST;
	    	gbc_dateFoundSpinner.insets = new Insets(0, 0, 0, 5);
	    	gbc_dateFoundSpinner.gridx = 1;
	    	gbc_dateFoundSpinner.gridy = 0;
	    GridBagConstraints gbc_dateFoundButton = new GridBagConstraints();
	    	gbc_dateFoundButton.anchor = GridBagConstraints.NORTH;
	    	gbc_dateFoundButton.gridx = 2;
	    	gbc_dateFoundButton.gridy = 0;
	    GridBagLayout gbl_dateRemovedInputPanel = new GridBagLayout();
	    	gbl_dateRemovedInputPanel.columnWidths = new int[]{98, 86, 107, 33, 0};
	    	gbl_dateRemovedInputPanel.rowHeights = new int[]{25, 0};
	    	gbl_dateRemovedInputPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	    	gbl_dateRemovedInputPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
	    GridBagConstraints gbc_dateRemovedCheckBox = new GridBagConstraints();
	    	gbc_dateRemovedCheckBox.anchor = GridBagConstraints.EAST;
	    	gbc_dateRemovedCheckBox.insets = new Insets(0, 0, 0, 5);
	    	gbc_dateRemovedCheckBox.gridx = 0;
	    	gbc_dateRemovedCheckBox.gridy = 0;
	    GridBagConstraints gbc_dateRemovedLabel = new GridBagConstraints();
	    	gbc_dateRemovedLabel.insets = new Insets(0, 0, 0, 5);
	    	gbc_dateRemovedLabel.gridx = 1;
	    	gbc_dateRemovedLabel.gridy = 0;
	    GridBagConstraints gbc_dateRemovedSpinner = new GridBagConstraints();
	    gbc_dateRemovedSpinner.anchor = GridBagConstraints.EAST;
	    	gbc_dateRemovedSpinner.insets = new Insets(0, 0, 0, 5);
	    	gbc_dateRemovedSpinner.gridx = 2;
	    	gbc_dateRemovedSpinner.gridy = 0;
	    GridBagConstraints gbc_dateRemovedButton = new GridBagConstraints();
	    	gbc_dateRemovedButton.anchor = GridBagConstraints.NORTHWEST;
	    	gbc_dateRemovedButton.gridx = 3;
	    	gbc_dateRemovedButton.gridy = 0;
	    	
	descriptionLabel.setFont(new Font("Dialog", Font.BOLD, 14));
    	
    	setPreferredSize(new Dimension(600, 400));
    	setResizable(false);
    	getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
    	getContentPane().add(mainPanel);
    	
    	mainPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
    	mainPanel.setLayout(new BorderLayout(0, 0));
    	mainPanel.add(descriptionPanel, BorderLayout.NORTH);
    	mainPanel.add(phasesPanel, BorderLayout.CENTER);
    	mainPanel.add(southPanel, BorderLayout.SOUTH);
    	
    	descriptionPanel.setLayout(gbl_descriptionPanel);
    	descriptionPanel.add(descriptionLabel, gbc_descriptionLabel);
    	descriptionPanel.add(descriptionTextArea, gbc_descriptionTextArea);
    	
    	descriptionTextArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    	descriptionTextArea.setSize(new Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT));
    	
    	phasesPanel.setBorder(null);
	  	phasesPanel.setLayout(gbl_phasesPanel);	  	
	  	phasesPanel.add(injectionPhaseLabel, gbc_injectionPhaseLabel);	  	
	  	phasesPanel.add(injectionPhaseComboBox, gbc_injectionPhaseComboBox);	  	
	  	phasesPanel.add(removalPhaseLabel, gbc_removalPhaseLabel);	  	
	  	phasesPanel.add(removalPhaseComboBox, gbc_removalPhaseComboBox);	  	
	  	phasesPanel.add(typeLabel, gbc_typeLabel);	  	
	  	phasesPanel.add(typeOfDefect, gbc_typeOfDefect);
    	  	
    	typeOfDefect.setSize(new Dimension(COMBO_BOX_WIDTH, COMBO_BOX_HEIGHT));
    	    	
    	southPanel.setLayout(new BorderLayout(0, 0));
    	southPanel.add(buttonPanel, BorderLayout.SOUTH);
    	southPanel.add(dateFoundInputPanel, BorderLayout.WEST);
    	southPanel.add(dateRemovedInputPanel, BorderLayout.CENTER);
    	
    	buttonPanel.add(okButton);
    	buttonPanel.add(cancelButton);
    	    	
    	dateFoundInputPanel.setBorder(null);    	    	    	    	
    	dateFoundInputPanel.setLayout(gbl_dateFoundInputPanel);
    	dateFoundInputPanel.add(dateFoundLabel, gbc_dateFoundLabel);    	    	
    	dateFoundSpinner.setPreferredSize(new Dimension(100, 25));
    	dateFoundInputPanel.add(dateFoundSpinner, gbc_dateFoundSpinner);
    	    	
    	dateFoundSpinner.setSize(new Dimension(100, 25));
    	dateFoundSpinner.setModel(new SpinnerDateModel(new Date(1455498893453L), null, null, Calendar.DAY_OF_WEEK));
    	dateFoundSpinner.setEditor(new JSpinner.DateEditor(dateFoundSpinner, sdf.toPattern()));
    	dateFoundSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				SpinnerDateModel sdm = new SpinnerDateModel((Date)dateFoundSpinner.getModel().getValue(),
						null,null,Calendar.DAY_OF_WEEK);
				dateFoundSpinner.setModel(sdm);

                if (ignoreDateFoundChange)
                    return;
                ignoreDateFoundChange = true;
                Date sd = (Date) dateFoundSpinner.getModel().getValue();
                Date ed = (Date) dateRemovedSpinner.getModel().getValue();
                if (sd.after(ed) && dateRemovedCheckBox.isSelected()) {
                	dateFoundSpinner.getModel().setValue(ed);
                    sd = ed;
                }
				if ((foundDateMax != null) && sd.after(foundDateMax.getDate())) {
					dateFoundSpinner.getModel().setValue(foundDateMax.getDate());
                    sd = foundDateMax.getDate();
				}
                if ((foundDateMin != null) && sd.before(foundDateMin.getDate())) {
                	dateFoundSpinner.getModel().setValue(foundDateMin.getDate());
                    sd = foundDateMin.getDate();
                }
                dateFoundCalFrame.cal.set(new CalendarDate(sd));
                ignoreDateFoundChange = false;	
			}
    	});
    	    	
    	dateFoundInputPanel.add(dateFoundButton, gbc_dateFoundButton);    	    	
    	dateFoundButton.setIcon(new ImageIcon(TaskDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/calendar.png")));
    	dateFoundButton.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			openDateFoundCal(e);
    		}
    	});
    	    	
    	    	
    	dateRemovedInputPanel.setLayout(gbl_dateRemovedInputPanel);
    	dateRemovedCheckBox.setHorizontalAlignment(SwingConstants.LEFT);    	    	
    	dateRemovedInputPanel.add(dateRemovedCheckBox, gbc_dateRemovedCheckBox);    	    	
    	dateRemovedCheckBox.setSelected(false);    	    	
    	dateRemovedCheckBox.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			checkBox_actionPerformed(e);
    		}
    	});
    	    	
    	dateRemovedLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	dateRemovedInputPanel.add(dateRemovedLabel, gbc_dateRemovedLabel);
    	
    	dateRemovedLabel.setEnabled(false);
    	
    	dateRemovedSpinner.setEnabled(false);
    	dateRemovedSpinner.setPreferredSize(new Dimension(100, 25));
    	dateRemovedInputPanel.add(dateRemovedSpinner, gbc_dateRemovedSpinner);
    	dateRemovedSpinner.setSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
    	dateRemovedSpinner.setModel(new SpinnerDateModel(new Date(1455498633534L), null, null, Calendar.DAY_OF_WEEK));
    	dateRemovedSpinner.setEditor(new JSpinner.DateEditor(dateRemovedSpinner, sdf.toPattern()));
    	dateRemovedSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				SpinnerDateModel sdm = new SpinnerDateModel((Date)dateRemovedSpinner.getModel().getValue(),null,null,Calendar.DAY_OF_WEEK);
				dateRemovedSpinner.setModel(sdm);
            	
                if (ignoreDateRemovedChange)
                    return;
                ignoreDateRemovedChange = true;
                Date sd = (Date) dateFoundSpinner.getModel().getValue();
                Date ed = (Date) dateRemovedSpinner.getModel().getValue();				
				if (ed.before(sd)) {
					dateRemovedSpinner.getModel().setValue(sd);
                }
				if ((removedDateMax != null) && ed.after(removedDateMax.getDate())) {
					dateRemovedSpinner.getModel().setValue(removedDateMax.getDate());
                    ed = removedDateMax.getDate();
				}
				if ((removedDateMin != null) && ed.before(removedDateMin.getDate())) {
                	dateRemovedSpinner.getModel().setValue(removedDateMin.getDate());
                    ed = removedDateMin.getDate();
                }
                dateRemovedCalFrame.cal.set(new CalendarDate(ed));
                ignoreDateRemovedChange = false;
			}
    	});
    	    	
    	dateRemovedInputPanel.add(dateRemovedButton, gbc_dateRemovedButton);
    	
    	dateRemovedButton.setEnabled(false);
    	dateRemovedButton.setIcon(new ImageIcon(TaskDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/calendar.png")));
    	dateRemovedButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDateRemovedCal(e);
	        }
	    });
    	
    	dateFoundCalFrame.cal.addSelectionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(ignoreDateFoundChange){
					return;
				}
				dateFoundSpinner.getModel().setValue(dateFoundCalFrame.cal.get().getCalendar().getTime());
			}
    		
    	});
    	
    	dateRemovedCalFrame.cal.addSelectionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			if(ignoreDateRemovedChange){
    				return;
    			}
    			dateRemovedSpinner.getModel().setValue(dateRemovedCalFrame.cal.get().getCalendar().getTime());
    		}
    	});
    	
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
    }
    
	void checkBox_actionPerformed(ActionEvent e) {
		dateRemovedLabel.setEnabled(dateRemovedCheckBox.isSelected());
		dateRemovedSpinner.setEnabled(dateRemovedCheckBox.isSelected());
		dateRemovedButton.setEnabled(dateRemovedCheckBox.isSelected());
		if(dateRemovedCheckBox.isSelected()){
			Date currentRemovedDate = (Date) dateRemovedSpinner.getModel().getValue();
			Date currentFoundDate = (Date) dateFoundSpinner.getModel().getValue();
			if(currentRemovedDate.before(currentFoundDate)){
				dateRemovedSpinner.getModel().setValue(currentFoundDate);
			}
		}
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
