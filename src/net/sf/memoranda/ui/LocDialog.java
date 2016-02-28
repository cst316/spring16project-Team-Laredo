package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.memoranda.Task;

@SuppressWarnings("serial")
public class LocDialog extends JDialog implements ItemListener{
	Task task = null;
	String[] panels = {"Base", "Added", "Modified", "Deleted",
			"Reused", "New Reuse", "Summary"};
	
	Dimension textFieldDimension = new Dimension(100, 25);
	
	JPanel mainPanel = new JPanel();
    JPanel displayPanel = new JPanel();
    JPanel comboBoxPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    
    JPanel basePanel = new JPanel();
	JPanel addedBaseLocPanel = new JPanel();
	JLabel addedBaseLocLabel = new JLabel("Add base lines of code: ");
	JTextField addedBaseLocTextField = new JTextField();
	JPanel totalBaseLocPanel = new JPanel();
	JLabel totalBaseLoc = new JLabel();
	
	JPanel addedPanel = new JPanel();
	JPanel addedLocPanel = new JPanel();
	JLabel addedLocLabel = new JLabel("Add lines of code: ");
	JTextField addedLocTextField = new JTextField();
	JPanel totalAddedLocPanel = new JPanel();
	JLabel totalAddedLoc = new JLabel();
	
	JPanel modifiedPanel = new JPanel();
	JPanel modifiedLocPanel = new JPanel();
	JLabel modifiedLocLabel = new JLabel("Add modified lines of code: ");
	JTextField modifiedLocTextField = new JTextField();
    JPanel totalModifiedLocPanel = new JPanel();
	JLabel totalModifiedLoc = new JLabel();
	
	JPanel deletedPanel = new JPanel();
	JPanel deletedLocPanel = new JPanel();
	JLabel deletedLocLabel = new JLabel("Add deleted lines of code: ");
	JTextField deletedLocTextField = new JTextField();
	JPanel totalDeletedLocPanel = new JPanel();
	JLabel totalDeletedLoc = new JLabel();
	
	JPanel reusedPanel = new JPanel();
	JPanel reusedLocPanel = new JPanel();
	JLabel reusedLocLabel = new JLabel("Add reused lines of code: ");
	JTextField reusedLocTextField = new JTextField();
	JPanel totalReusedLocPanel = new JPanel();
	JLabel totalReusedLoc = new JLabel();
	
	JPanel newReusePanel = new JPanel();
	JPanel newReuseLocPanel = new JPanel();
	JLabel newReuseLocLabel = new JLabel("Add new reused lines of code: ");
	JTextField newReuseLocTextField = new JTextField();
	JPanel totalNewReuseLocPanel = new JPanel();
	JLabel totalNewReuseLoc = new JLabel();
	
	JPanel summary = new JPanel();
	JLabel newAndChangedLocLabel = new JLabel();
	JLabel totalLocLabel = new JLabel();
	
	JComboBox<String> comboBox = new JComboBox<String>(panels);

	JButton add = new JButton("Add");
    JButton cancel = new JButton("Cancel");
    
    public LocDialog(Frame frame, Task task){
    	super(frame, task.getText() + ": Lines of Code", true);
    	this.task = task;
    	task.registerObservers();
    	try{
    		init();
    	}
    	catch(Exception ex){}
    }
    
    public void init(){
    	setPreferredSize(new Dimension(315, 175));
    	setMaximumSize(new Dimension(315, 175));
    	setMinimumSize(new Dimension(315, 175));
    	setResizable(false);
    	
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        totalBaseLoc.setText("Current base lines of code: " + task.getLocBase());
        initializeDisplayPanel(basePanel, addedBaseLocPanel, addedBaseLocLabel, 
        		addedBaseLocTextField, totalBaseLocPanel, totalBaseLoc);
        
        totalAddedLoc.setText("Current added lines of code: " + task.getLocAdded());
        initializeDisplayPanel(addedPanel, addedLocPanel, addedLocLabel, 
        		addedLocTextField, totalAddedLocPanel, totalAddedLoc);
        
        totalModifiedLoc.setText("Current modified lines of code: " + task.getLocModified());
        initializeDisplayPanel(modifiedPanel, modifiedLocPanel, modifiedLocLabel, 
        		modifiedLocTextField, totalModifiedLocPanel, totalModifiedLoc);
        
        totalDeletedLoc.setText("Current deleted lines of code: " + task.getLocDeleted());
        initializeDisplayPanel(deletedPanel, deletedLocPanel, deletedLocLabel, 
        		deletedLocTextField, totalDeletedLocPanel, totalDeletedLoc);
        
        totalReusedLoc.setText("Current reused lines of code: " + task.getLocReused());
        initializeDisplayPanel(reusedPanel, reusedLocPanel, reusedLocLabel, 
        		reusedLocTextField, totalReusedLocPanel, totalReusedLoc);
        
        totalNewReuseLoc.setText("Current new reuse lines of code: " + task.getLocNewReuse());
        initializeDisplayPanel(newReusePanel, newReuseLocPanel, newReuseLocLabel, 
        		newReuseLocTextField, totalNewReuseLocPanel, totalNewReuseLoc);
        
        summary.setLayout(new BoxLayout(summary, BoxLayout.Y_AXIS));
        newAndChangedLocLabel.setText("Current new and changed lines of code: " 
                                         + task.getLocNewAndChanged());
        totalLocLabel.setText("Current total lines of code: " + task.getLocTotal());
        summary.add(newAndChangedLocLabel);
        summary.add(Box.createRigidArea(new Dimension(0, 10)));
        summary.add(totalLocLabel);
        
        displayPanel.setLayout(new CardLayout());
        displayPanel.add(basePanel, "Base");
        displayPanel.add(addedPanel, "Added");
        displayPanel.add(modifiedPanel, "Modified");
        displayPanel.add(deletedPanel, "Deleted");
        displayPanel.add(reusedPanel, "Reused");
        displayPanel.add(newReusePanel, "New Reuse");
        displayPanel.add(summary, "Summary");
        
		
        mainPanel.add(displayPanel, BorderLayout.CENTER);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        comboBox.setEditable(false);
        comboBox.addItemListener(this);
    	mainPanel.add(comboBox, BorderLayout.CENTER);
    	mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        getContentPane().add(mainPanel, BorderLayout.CENTER);
  

        add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addLocToSelectedValue(e);
			}
        });
        
        cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				LocDialog.this.dispose();
			}	
        });
        
    	buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
    	buttonPanel.add(add);
    	buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    	buttonPanel.add(cancel);
    	mainPanel.add(buttonPanel);
    }

	void initializeTextField(JTextField textBox){
    	textBox.setPreferredSize(textFieldDimension);
    	textBox.setMaximumSize(textFieldDimension);
    	textBox.setMinimumSize(textFieldDimension);
    }
 
    void initializeDisplayPanel(JPanel displayedPanel, JPanel entryPanel, JLabel headerForTextBox,
    		                        JTextField entryForAddedLines, JPanel displayCurrentLocPanel,
    		                            JLabel displayCurrentLocLabel){
    	GridBagLayout layout = new GridBagLayout();
    	displayedPanel.setLayout(layout);
        
    	entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));
    	entryPanel.add(headerForTextBox);
    	entryPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    	initializeTextField(entryForAddedLines);
    	entryPanel.add(entryForAddedLines);
    	
    	displayCurrentLocPanel.add(displayCurrentLocLabel);
    	
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	displayedPanel.add(entryPanel, constraints);
    	constraints.gridy = 1;
    	displayedPanel.add(displayCurrentLocPanel, constraints);
    }

    void addLocToSelectedValue(ActionEvent e) {
    	try{
    		String selectedValue = (String) comboBox.getSelectedItem();
    		if(selectedValue.equals("Base")){
    			task.addLocBase(positiveValue(addedBaseLocTextField));
    		}else if(selectedValue.equals("Added")){
    			task.addLocAdded(positiveValue(addedLocTextField));
    		}else if(selectedValue.equals("Modified")){
    			task.addLocModified(positiveValue(modifiedLocTextField));
    		}else if(selectedValue.equals("Deleted")){
    			deleteLoc();
    		}else if(selectedValue.equals("Reused")){
    			task.addLocReused(positiveValue(reusedLocTextField));
    		}else if(selectedValue.equals("New Reuse")){
    			task.addLocNewReuse(positiveValue(newReuseLocTextField));
    		}
    		update();
    	}
		catch(Exception exept){
			JOptionPane.showMessageDialog(null, "Please enter numeric value.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
    
    void update() {
    	task.setLocNewAndChanged(task.getLocAdded() + task.getLocModified());
    	task.setLocTotal(task.getLocBase() - task.getLocDeleted() + task.getLocAdded() + task.getLocReused());
    	totalBaseLoc.setText("Current base lines of code: " + task.getLocBase());
        totalAddedLoc.setText("Current added lines of code: " + task.getLocAdded());
        totalModifiedLoc.setText("Current modified lines of code: " + task.getLocModified());
        totalDeletedLoc.setText("Current deleted lines of code: " + task.getLocDeleted());
        totalReusedLoc.setText("Current reused lines of code: " + task.getLocReused());
        totalNewReuseLoc.setText("Current new reuse lines of code: " + task.getLocNewReuse());
    	newAndChangedLocLabel.setText("Current new and changed lines of code: " 
                 + task.getLocNewAndChanged());
        totalLocLabel.setText("Current total lines of code: " + task.getLocTotal()); 
	}
    
    int positiveValue(JTextField textField){
    	int addedLoc = 0;
    	int textBoxValue = Integer.parseInt(textField.getText());
		if(textBoxValue > 0){
			addedLoc = textBoxValue;
		}else{
			JOptionPane.showMessageDialog(null, "Please enter non-negative number.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return addedLoc;
    }
    
    void deleteLoc(){
    	int deletedLoc = Integer.parseInt(deletedLocTextField.getText());
    	if((task.getLocTotal() - deletedLoc) >= 0){
    		task.addLocDeleted(positiveValue(deletedLocTextField));
    	}
    	else{
    		JOptionPane.showMessageDialog(null, "Unable to delete more lines of code than total lines of code.", "Error",
					JOptionPane.ERROR_MESSAGE);
    	}
    }

	public void itemStateChanged(ItemEvent evt) {
		CardLayout c1 = (CardLayout)(displayPanel.getLayout());
		c1.show(displayPanel, (String)evt.getItem());
		update();
    }
}
