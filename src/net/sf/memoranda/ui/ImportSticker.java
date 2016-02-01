package net.sf.memoranda.ui;

import javax.swing.JOptionPane;

import net.sf.memoranda.util.Local;

public class ImportSticker {

String name;        
        
        public ImportSticker(String x) {
                name = x;
        }

        public boolean import_file(){
                /* We are working on this =)*/
        		/* Translation: Error[404] Feature not found... -tjcole2*/
                
                JOptionPane.showMessageDialog(null,Local.getString("Document import failed."));
                return true;
        }
        
        
}