package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.SingleRootFileSystemView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

/*$Id: AddResourceDialog.java,v 1.12 2007/03/20 06:21:46 alexeya Exp $*/
class AddResourceDialog extends JDialog {
    public final JRadioButton localFileRB = new JRadioButton();
    public final JCheckBox projectFileCB = new JCheckBox("Copy file to memoranda", false);
    public final JTextField pathField = new JTextField();
    public final JTextField urlField = new JTextField();
    private final JRadioButton inetShortcutRB = new JRadioButton();
    private final JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JLabel header = new JLabel();
    private final ButtonGroup buttonGroup1 = new ButtonGroup();
    private final JPanel areaPanel = new JPanel(new GridBagLayout());
    private final JLabel jLabel1 = new JLabel();
    private final JButton browseB = new JButton();
    private final JLabel jLabel2 = new JLabel();
    private final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
    private final JButton okB = new JButton();
    private final JButton cancelB = new JButton();
    public boolean CANCELLED = true;

    public AddResourceDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
            ex.printStackTrace();
        }
    }

    /**
     * setup user interface and init dialog
     */

    private void jbInit() {
        this.setResizable(false);
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("New resource"));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.AddResourceDialog.class.getResource(
                "resources/icons/resource48.png")));
        dialogTitlePanel.add(header);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);

        buttonGroup1.add(localFileRB);
        buttonGroup1.add(inetShortcutRB);
        localFileRB.setSelected(true);
        localFileRB.setText(Local.getString("Local file"));
        localFileRB.addActionListener(e -> localFileRB_actionPerformed());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(localFileRB, gbc);

        gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(projectFileCB, gbc);

        jLabel1.setText(Local.getString("Path") + ": ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 20, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(jLabel1, gbc);
        pathField.setMinimumSize(new Dimension(4, 24));
        pathField.setPreferredSize(new Dimension(250, 24));
        pathField.addCaretListener(e -> pathField_caretUpdate());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(pathField, gbc);
        browseB.setText(Local.getString("Browse"));
        browseB.addActionListener(e -> browseB_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(browseB, gbc);
        inetShortcutRB.setText(Local.getString("Internet shortcut"));
        inetShortcutRB.addActionListener(e -> inetShortcutRB_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(inetShortcutRB, gbc);
        jLabel2.setText(Local.getString("URL") + ":  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 20, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel2, gbc);
        urlField.setMinimumSize(new Dimension(4, 24));
        urlField.setPreferredSize(new Dimension(335, 24));
        urlField.addCaretListener(e -> urlField_caretUpdate());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 0, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(urlField, gbc);
        this.getContentPane().add(areaPanel, BorderLayout.CENTER);

        okB.setEnabled(false);
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(e -> okB_actionPerformed());
        this.getRootPane().setDefaultButton(okB);
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
        enableFields();
        this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * set CANCELLED variable to false so we can know the user
     * pressed the ok button and close this dialog.
     */

    private void okB_actionPerformed() {
        CANCELLED = false;
        this.dispose();
    }

    /**
     * close the dialog window
     */

    private void cancelB_actionPerformed() {
        this.dispose();
    }

    /**
     * enable localRB fields. Request focus for the text field
     * so the user can start typing and set the pathField text selected
     */

    private void localFileRB_actionPerformed() {
        enableFields();
        checkOkEnabled();
        urlField.select(0, 0);
        pathField.select(0, pathField.getText().length());
        pathField.requestFocus();
    }

    /**
     * enable inetShorcutRB fields. Request focus for the text field
     * so the user can start typing and set the urlField text selected
     */

    private void inetShortcutRB_actionPerformed() {
        enableFields();
        checkOkEnabled();
        pathField.select(0, 0);
        urlField.select(0, urlField.getText().length());
        urlField.requestFocus();
    }

    /**
     * setup the JFileChooser so the user can select the resource file
     */

    private void browseB_actionPerformed() {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.lookInLabelText",
                Local.getString("Look in:"));
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
        UIManager.put("FileChooser.openButtonText",
                Local.getString("Open"));
        UIManager.put("FileChooser.openButtonToolTipText",
                Local.getString("Open selected file"));
        UIManager.put("FileChooser.cancelButtonText",
                Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText",
                Local.getString("Cancel"));

        File root = new File(System.getProperty("user.home"));
        FileSystemView fsv = new SingleRootFileSystemView(root);
        JFileChooser chooser = new JFileChooser(fsv);
        chooser.setFileHidingEnabled(true);
        chooser.setDialogTitle(Local.getString("Add resource"));
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setPreferredSize(new Dimension(550, 375));
        /*java.io.File lastSel = (java.io.File) Context.get("LAST_SELECTED_RESOURCE_FILE");
        if (lastSel != null)
            chooser.setCurrentDirectory(lastSel);*/
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        /*try {
            Context.put("LAST_SELECTED_RESOURCE_FILE", chooser.getSelectedFile());
        }
        catch (Exception ex) {}*/
        pathField.setText(chooser.getSelectedFile().getPath());
        checkOkEnabled();
    }

    /**
     * disable the ok button if pathField is empty
     */

    private void pathField_caretUpdate() {
        checkOkEnabled();
    }

    /**
     * disable the ok button if urlField is empty
     */

    private void urlField_caretUpdate() {
        checkOkEnabled();
    }

    /**
     * do not enable the ok button until the text field is not empty.
     */

    private void checkOkEnabled() {
        okB.setEnabled(
                (localFileRB.isSelected() && pathField.getText().length() > 0) ||
                        (inetShortcutRB.isSelected() && urlField.getText().length() > 0)
        );
    }

    /**
     * enable and disable fields when user selects the radio buttons options
     */

    private void enableFields() {
        pathField.setEnabled(localFileRB.isSelected());
        jLabel1.setEnabled(localFileRB.isSelected());
        browseB.setEnabled(localFileRB.isSelected());
        projectFileCB.setEnabled(localFileRB.isSelected());

        urlField.setEnabled(inetShortcutRB.isSelected());
        jLabel2.setEnabled(inetShortcutRB.isSelected());
    }
}