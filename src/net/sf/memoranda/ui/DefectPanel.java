package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;

/**
 * Defect panel that allows creation and editing of defects.
 * Uses defect table to display current defects. In order to 
 * edit or remove a defect, the row that the defect is listed
 * in needs to be selected. Can only select one row at a time.
 * 
 * @author Benjamin Paothatat
 * @since 2/19/2016
 */
@SuppressWarnings("serial")
public class DefectPanel extends JPanel{
	DailyItemsPanel parentPanel = null;
	
	Dimension buttonDimension = new Dimension(24, 24);
	
	JToolBar defectToolBar = new JToolBar();
	JButton newDefectButton = new JButton();
	JButton editDefectButton = new JButton();
	JButton removeDefectButton = new JButton();
	
	JScrollPane scrollPane = null;
	DefectTable defectTable = new DefectTable();
	
    public DefectPanel(DailyItemsPanel _parentPanel){
    	try{
    		parentPanel = _parentPanel;
    		init();
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    void init() throws Exception{
    	//Button image created by Tyler Cole
    	newDefectButton.setIcon(new ImageIcon(AppFrame.class.getResource
    			("resources/icons/adddefect.png")));
    	newDefectButton.setToolTipText("Create new defect");
    	newDefectButton.setEnabled(true);
    	newDefectButton.setFocusable(false);
    	newDefectButton.setRequestFocusEnabled(false);
    	newDefectButton.setToolTipText("Create new defect");
    	newDefectButton.setPreferredSize(buttonDimension);
    	newDefectButton.setMinimumSize(buttonDimension);
    	newDefectButton.setMaximumSize(buttonDimension);
    	newDefectButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewDefect();
			}
    		
    	});
    	
    	//Button image created by Tyler Cole
    	removeDefectButton.setIcon(new ImageIcon(AppFrame.class.getResource
    			("resources/icons/removedefect.png")));
    	removeDefectButton.setToolTipText("Remove defect");
    	removeDefectButton.setEnabled(true);
    	removeDefectButton.setFocusable(false);
    	removeDefectButton.setRequestFocusEnabled(false);
    	removeDefectButton.setPreferredSize(buttonDimension);
    	removeDefectButton.setMinimumSize(buttonDimension);
    	removeDefectButton.setMaximumSize(buttonDimension);
    	removeDefectButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removeDefect();
			}
    		
    	});
    	
    	//Button image created by Tyler Cole
    	editDefectButton.setIcon(new ImageIcon(AppFrame.class.getResource
    			("resources/icons/editdefect.png")));
    	editDefectButton.setToolTipText("Edit defect");
    	editDefectButton.setEnabled(true);
    	editDefectButton.setFocusable(false);
    	editDefectButton.setRequestFocusEnabled(false);
    	editDefectButton.setToolTipText("Edit defect");
    	editDefectButton.setPreferredSize(buttonDimension);
    	editDefectButton.setMinimumSize(buttonDimension);
    	editDefectButton.setMaximumSize(buttonDimension);
    	editDefectButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editDefect();
			}
    		
    	});
    	
    	defectToolBar.setEnabled(true);
    	defectToolBar.setFloatable(false);
    	defectToolBar.add(newDefectButton, null);
    	defectToolBar.add(removeDefectButton, null);
    	defectToolBar.addSeparator(new Dimension(8, 24));
    	defectToolBar.add(editDefectButton, null);
    	setLayout(new BorderLayout(0, 0));
    	
    	this.add(defectToolBar, BorderLayout.NORTH);
    	defectTable.update();
    	scrollPane = new JScrollPane(defectTable);
    	this.add(scrollPane, BorderLayout.CENTER);
    }

	

	private void addNewDefect() {
		DefectDialog dd = setUpDefectDialog();
		dd.setVisible(true);
		if(dd.CANCELLED){
			return;
		}
		Defect defect = null;
		updateDefect(dd, defect);
        CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
        defectTable.update();
	}
	
	private void removeDefect() {
		int row = defectTable.getSelectedRow();
		if(row == -1){
		    return;
		}
		String defectId = defectTable.getModel().getValueAt(defectTable.getSelectedRow(), 0).toString();
		int n = JOptionPane.showConfirmDialog(App.getFrame(), "Are you sure you want to remove this defect?",
				Local.getString("Remove Defect"), JOptionPane.YES_NO_OPTION);
		if(n != JOptionPane.YES_OPTION){
			return;
		}
		else{
            CurrentProject.getDefectList().removeDefect(Integer.parseInt(defectId) - 1);
		}
		CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
		defectTable.update();
	}
	
	private void editDefect() {
		int row = defectTable.getSelectedRow();
		if(row == -1){
		    return;
		}
		DefectDialog dd = setUpDefectDialog();
		String defectId = defectTable.getModel().getValueAt(defectTable.getSelectedRow(), 0).toString();
		Defect defect = CurrentProject.getDefectList().getDefect(Integer.parseInt(defectId) - 1);
		defectInfo(dd, defect);
		dd.setVisible(true);
		updateDefect(dd, defect);
		CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
		defectTable.update();
	}
	
	private int convertPhasesToInt(String phase){
		int intPhase = 0;
		if(phase.equals("Planning")){
			intPhase = 1;
		}else if(phase.equals("Design")){
			intPhase = 2;
		}else if(phase.equals("Code")){
			intPhase = 3;
		}else if(phase.equals("Review")){
	        intPhase = 4;
		}else if(phase.equals("Compile")){
			intPhase = 5;
		}else if(phase.equals("Testing")){
			intPhase = 6;
		}
		return intPhase;
	}
	
	private int convertTypeToInt(String type){
		int intType = 0;
		if(type.equals("Documentation")){
			intType = 1;
		}else if(type.equals("Syntax")){
			intType = 2;
		}else if(type.equals("Build")){
			intType = 3;
		}else if(type.equals("Assignment")){
	        intType = 4;
		}else if(type.equals("Interface")){
			intType = 5;
		}else if(type.equals("Checking")){
			intType = 6;
		}else if(type.equals("Data")){
			intType = 7;
		}else if(type.equals("Function")){
			intType = 8;
		}else if(type.equals("System")){
			intType = 9;
		}else if(type.equals("Enviroment")){
			intType = 10;
		}
		return intType;
	}	
	
	private DefectDialog setUpDefectDialog(){
		DefectDialog defectDialog = new DefectDialog(App.getFrame(), "New Defect");
		Dimension frameSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		defectDialog.setLocation((frameSize.width - defectDialog.getSize().width)/2 + loc.x,
				(frameSize.height - defectDialog.getSize().height)/2 + loc.y);
		return defectDialog;
	}
	
	
	private void updateDefect(DefectDialog defectDailog, Defect defect){
		if(defect != null){
			defect.setDateFound(new CalendarDate((Date) defectDailog.dateFoundSpinner.getModel().getValue()));
			defect.setDateRemoved(new CalendarDate((Date) defectDailog.dateRemovedSpinner.getModel().getValue()));
			defect.setPhaseOfInjection(defectDailog.injectionPhaseComboBox.getSelectedIndex());
			defect.setPhaseOfRemoval(defectDailog.removalPhaseComboBox.getSelectedIndex());
			defect.setTypeOfDefect(defectDailog.typeOfDefect.getSelectedIndex());
			defect.setDescription(defectDailog.descriptionTextArea.getText());	
		}
		else{
			CalendarDate rd;
	        if(defectDailog.dateRemovedCheckBox.isSelected()){
	        	if(((Date)defectDailog.dateRemovedSpinner.getModel().getValue()).before(
	        			(Date)defectDailog.dateFoundSpinner.getModel().getValue())){
					rd = new CalendarDate((Date) defectDailog.dateFoundSpinner.getModel().getValue());
				}
	        	else{
	        	    rd = new CalendarDate((Date) defectDailog.dateRemovedSpinner.getModel().getValue());
	        	}
			}
			else{
				rd = null;
			}
			CalendarDate fd = new CalendarDate((Date) defectDailog.dateFoundSpinner.getModel().getValue());
			int injection = convertPhasesToInt((String)defectDailog.injectionPhaseComboBox.getSelectedItem());
			int removal = convertPhasesToInt((String)defectDailog.removalPhaseComboBox.getSelectedItem());
			int type = convertTypeToInt((String)defectDailog.typeOfDefect.getSelectedItem());
			String description = defectDailog.descriptionTextArea.getText();
			CurrentProject.getDefectList().addDefect(fd, rd, injection, removal, type, description);
		}
	}
	
	private void defectInfo(DefectDialog defectDailog, Defect defect){
		defectDailog.descriptionTextArea.setText(defect.getDescription());
		defectDailog.dateFoundSpinner.getModel().setValue(defect.getDateFound().getDate());
		defectDailog.dateRemovedSpinner.getModel().setValue(defect.getDateRemoved().getDate());
		defectDailog.injectionPhaseComboBox.setSelectedIndex(defect.getPhaseOfInjection());
		defectDailog.removalPhaseComboBox.setSelectedIndex(defect.getPhaseOfRemoval());
		defectDailog.typeOfDefect.setSelectedIndex(defect.getTypeOfDefect());
	}
}
