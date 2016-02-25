package net.sf.memoranda.ui;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.ProjectPackager;
import net.sf.memoranda.util.SingleRootFileSystemView;

import java.awt.*;
import java.io.*;

class FileDialog {

	public static File getFile(String frameTitle, AllFilesFilter filters,
			String lastFile, Component compRef, boolean save, int fileSelectionMode)

	{

		// Fix until Sun's JVM supports more locales...
		UIManager.put("FileChooser.saveInLabelText",
				Local.getString("Save in:"));
		UIManager.put("FileChooser.upFolderToolTipText",
				Local.getString("Up One Level"));
		UIManager.put("FileChooser.newFolderToolTipText",
				Local.getString("Create New Folder"));
		UIManager.put("FileChooser.listViewButtonToolTipText",
				Local.getString("List"));
		UIManager.put("FileChooser.detailsViewButtonToolTipText",
				Local.getString("Details"));
		UIManager.put("FileChooser.fileNameLabelText",
				Local.getString("File Name:"));
		UIManager.put("FileChooser.filesOfTypeLabelText",
				Local.getString("Files of Type:"));
		UIManager.put("FileChooser.saveButtonText", Local.getString("Save"));
		UIManager.put("FileChooser.saveButtonToolTipText",
				Local.getString("Save selected file"));
		UIManager
				.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
		UIManager.put("FileChooser.cancelButtonToolTipText",
				Local.getString("Cancel"));

		File root = new File(System.getProperty("user.home"));
		FileSystemView fsv = new SingleRootFileSystemView(root);
		JFileChooser chooser = new JFileChooser(fsv);
		chooser.setFileHidingEnabled(true);
		chooser.setDialogTitle(Local.getString(frameTitle));
		chooser.setAcceptAllFileFilterUsed(false);

		chooser.setFileSelectionMode(fileSelectionMode);

		chooser.addChoosableFileFilter(filters);

		chooser.setPreferredSize(new Dimension(550, 375));

		File lastSel = null;

		try {
			lastSel = (java.io.File) Context.get(lastFile);
		} catch (ClassCastException cce) {
			lastSel = new File(System.getProperty("user.dir") + File.separator);
		}

		if (lastSel != null) {
			chooser.setCurrentDirectory(lastSel);
		}

		int diagResult;
		if(save){
			diagResult = chooser.showSaveDialog(compRef);
		}else{			
			diagResult = chooser.showOpenDialog(compRef);
		}
		
		if ( diagResult == JFileChooser.APPROVE_OPTION) {
			Context.put(lastFile, chooser.getSelectedFile());
			return chooser.getSelectedFile();
		} else {
			return null;
		}
	}

}
