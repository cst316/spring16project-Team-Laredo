package net.sf.memoranda.ui;

import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;

@SuppressWarnings("serial")
public class DefectTable extends JTable {
	String[] columnNames = {"Defect Number", "Date Found", "Date Removed",
			"Phase of Injection", "Phase of Removal", "Type of Defect", 
			"Description"};
	Collection<Defect> defectList = CurrentProject.getDefectList().getAllDefects();
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	DefaultTableModel model = new DefaultTableModel(){;
	    public boolean isCellEditable(int row, int column){
	    	return false;
	    }
	};
	
	public DefectTable(){
		init();
	}
	
	void init(){
		model.setColumnIdentifiers(columnNames);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.setModel(model);
		
		for(int i = 0; i < columnNames.length - 1; i++){
		    this.getColumnModel().getColumn(i).setPreferredWidth(125);
		    this.getColumnModel().getColumn(i).setMaxWidth(125);
		    this.getColumnModel().getColumn(i).setMinWidth(125);
		}
		
		for(int i = 0; i < columnNames.length; i++){
			this.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}	
	
		update();
	}
	
	public void update(){
		model.setRowCount(0);
	    for(Defect defect: defectList){
			model.addRow(defectInformation(defect));
		}
	}
	
	private String[] defectInformation(Defect defect){
		String[] defectInfo = new String[7];
		defectInfo[0] = String.valueOf(defect.getDefectNumber() + 1);
		defectInfo[1] = sdf.format(defect.getDateFound().getDate()).toString();
        defectInfo[2] = sdf.format(defect.getDateRemoved().getDate()).toString();
		defectInfo[3] = covertPhaseToString(defect.getPhaseOfInjection());
		defectInfo[4] = covertPhaseToString(defect.getPhaseOfRemoval());
		defectInfo[5] = convertTypeToString(defect.getTypeOfDefect());
		defectInfo[6] = defect.getDescription();
		return defectInfo;
	}
	
	private String covertPhaseToString(int phase){
		String stringPhase = "";
		if(phase == 1){
			stringPhase = "Planning";
		}else if(phase == 2){
			stringPhase = "Design";
		}else if(phase == 3){
			stringPhase = "Code";
		}else if(phase == 4){
			stringPhase = "Review";
		}else if(phase == 5){
			stringPhase = "Compile";
		}else if(phase == 6){
			stringPhase = "Testing";
		}
		return stringPhase;
	}
	
	private String convertTypeToString(int type){
		String stringType = "";
		if(type == 1){
			stringType = "Documentation";
		}else if(type == 2){
			stringType = "Syntax";
		}else if(type == 3){
			stringType = "Build";
		}else if(type == 4){
			stringType = "Assignment";
		}else if(type == 5){
			stringType = "Interface";
		}else if(type == 6){
			stringType = "Checking";
		}else if(type == 7){
			stringType = "Data";
		}else if(type == 8){
			stringType = "Function";
		}else if(type == 9){
			stringType = "System";
		}else if(type == 10){
			stringType = "Enviroment";
		}
		return stringType;
	}
}
