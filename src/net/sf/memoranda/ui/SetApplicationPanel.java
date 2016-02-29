package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.SingleRootFileSystemView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

/*$Id: SetApplicationPanel.java,v 1.6 2004/04/05 10:05:44 alexeya Exp $*/
public class SetApplicationPanel extends JPanel {
    public final JTextField applicationField = new JTextField();
    public final JTextField argumentsField = new JTextField();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final BorderLayout borderLayout5 = new BorderLayout();
    private final JButton selectAppBrowseB = new JButton();
    private final JPanel jPanel3 = new JPanel();
    private final BorderLayout borderLayout6 = new BorderLayout();
    private final JPanel jPanel4 = new JPanel();
    private final JLabel argHelpLabel = new JLabel();
    public File d = null;

    public SetApplicationPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    private void jbInit() throws Exception {
        Border border1 = BorderFactory.createEmptyBorder();
        TitledBorder titledBorder1 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("Path to executable"));
        Border border2 = BorderFactory.createEmptyBorder();
        TitledBorder titledBorder2 = new TitledBorder(border2, Local.getString("Arguments"));
        jPanel3.setLayout(borderLayout5);
        selectAppBrowseB.addActionListener(e -> selectAppBrowseB_actionPerformed());
        selectAppBrowseB.setText(Local.getString("Browse"));
        applicationField.addCaretListener(e -> applicationField_caretUpdate());
        applicationField.setPreferredSize(new Dimension(300, 24));
        applicationField.setMinimumSize(new Dimension(4, 24));
        this.setLayout(borderLayout1);
        jPanel3.setBorder(titledBorder1);
        argumentsField.addCaretListener(e -> argumentsField_caretUpdate());
        argumentsField.setPreferredSize(new Dimension(300, 24));
        argumentsField.setText("$1");
        argumentsField.setMinimumSize(new Dimension(4, 24));
        jPanel4.setBorder(titledBorder2);
        jPanel4.setLayout(borderLayout6);
        argHelpLabel.setFont(new java.awt.Font("Dialog", 0, 11));
        argHelpLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        argHelpLabel.setText(Local.getString("Use $1 as an alias of the file to open"));
        jPanel3.add(applicationField, BorderLayout.CENTER);
        jPanel3.add(selectAppBrowseB, BorderLayout.EAST);
        this.add(jPanel4, BorderLayout.SOUTH);
        jPanel4.add(argumentsField, BorderLayout.CENTER);
        jPanel4.add(argHelpLabel, BorderLayout.SOUTH);
        this.add(jPanel3, BorderLayout.NORTH);
    }

    private void applicationField_caretUpdate() {

    }

    private void selectAppBrowseB_actionPerformed() {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.lookInLabelText", Local.getString("Look in:"));
        UIManager.put("FileChooser.upFolderToolTipText", Local.getString("Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", Local.getString("Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", Local.getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Local.getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", Local.getString("File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString("Files of Type:"));
        UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
        UIManager.put("FileChooser.openButtonToolTipText", Local.getString("Open selected file"));
        UIManager.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString("Cancel"));
        UIManager.put("FileChooser.acceptAllFileFilterText", Local.getString("All Files") + " (*.*)");

        File root = new File(System.getProperty("user.home"));
        FileSystemView fsv = new SingleRootFileSystemView(root);
        JFileChooser chooser = new JFileChooser(fsv);
        chooser.setDialogTitle(Local.getString("Path to executable"));
        chooser.setFileHidingEnabled(true);
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (System.getProperty("os.name").startsWith("Win")) {
            chooser.setFileFilter(new AllFilesFilter(AllFilesFilter.EXE));
            chooser.setCurrentDirectory(new File("C:\\Program Files"));
        }
        chooser.setPreferredSize(new Dimension(550, 375));
        /*
            java.io.File lastSel = (java.io.File) Context.get("LAST_SELECTED_IMPORT_FILE");
            if (lastSel != null)
                chooser.setCurrentDirectory(lastSel);
        */
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            applicationField.setText(chooser.getSelectedFile().getPath());
    }

    private void argumentsField_caretUpdate() {

    }
}
