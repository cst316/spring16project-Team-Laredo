package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.SingleRootFileSystemView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * @author Alex
 */
class FileExportDialog extends javax.swing.JDialog {

    private final javax.swing.JFileChooser fileChooser;
    public boolean CANCELLED = true;
    public javax.swing.JCheckBox numentChB;
    public javax.swing.JTextField templF;
    public javax.swing.JCheckBox usetemplChB;
    public javax.swing.JCheckBox xhtmlChB;
    public JComboBox<? extends String> encCB;
    private javax.swing.JButton okB;
    private javax.swing.JButton templBrowseB;

    /**
     * Creates new form ExportDialog
     */
    public FileExportDialog(java.awt.Frame parent, String title, JFileChooser chooser) {
        super(parent, title, true);
        fileChooser = chooser;
        initComponents();
    }

    private void initComponents() {//GEN-BEGIN:initComponents
        JPanel jPanel2 = new JPanel();
        okB = new javax.swing.JButton();
        JButton cancelB = new JButton();
        JPanel filePanel = new JPanel();
        //fileChooser = new javax.swing.JFileChooser();
        JPanel optionsPanel = new JPanel();
        JPanel encPanel = new JPanel();
        JLabel jLabel2 = new JLabel();
        encCB = new JComboBox<>(new String[]{Local.getString("System default"), "UTF-8", "ANSI"});
        usetemplChB = new javax.swing.JCheckBox();
        xhtmlChB = new javax.swing.JCheckBox();
        JPanel templPanel = new JPanel();
        templF = new javax.swing.JTextField();
        templF.setEditable(false);
        templBrowseB = new javax.swing.JButton();
        numentChB = new javax.swing.JCheckBox();
        JPanel jPanel6 = new JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        okB.setText(Local.getString("Save"));
        okB.setPreferredSize(new java.awt.Dimension(90, 25));
        okB.addActionListener(e -> {
            CANCELLED = false;
            dispose();
        });
        okB.setEnabled(false);
        jPanel2.add(okB);

        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new java.awt.Dimension(90, 25));
        cancelB.addActionListener(e -> dispose());
        jPanel2.add(cancelB);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        filePanel.setLayout(new java.awt.BorderLayout());

        filePanel.setBorder(new javax.swing.border.EtchedBorder());
        fileChooser.setControlButtonsAreShown(false);
        fileChooser.addPropertyChangeListener(evt -> chooserActionPerformed());
        /*fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed();
            }
        });*/


        filePanel.add(fileChooser, java.awt.BorderLayout.CENTER);

        optionsPanel.setLayout(new java.awt.GridLayout(3, 2, 5, 0));

        optionsPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        encPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel2.setText(Local.getString("Encoding") + ":");
        encPanel.add(jLabel2);

        encCB.setMaximumSize(new java.awt.Dimension(32767, 19));
        encPanel.add(encCB);

        optionsPanel.add(encPanel);

        usetemplChB.setText(Local.getString("Use template") + ":");
        usetemplChB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        usetemplChB.addActionListener(e -> {
            if (usetemplChB.isSelected()) {
                templF.setEnabled(true);
                templBrowseB.setEnabled(true);
            } else {
                templF.setEnabled(false);
                templBrowseB.setEnabled(false);
            }
        });
        optionsPanel.add(usetemplChB);

        xhtmlChB.setText(Local.getString("Save as XHTML"));
        xhtmlChB.addActionListener(evt -> xhtmlChBActionPerformed());

        optionsPanel.add(xhtmlChB);

        templPanel.setLayout(new java.awt.BorderLayout());
        templF.setEnabled(false);
        templPanel.add(templF, java.awt.BorderLayout.CENTER);

        templBrowseB.setText("Browse");
        templBrowseB.setEnabled(false);
        templBrowseB.addActionListener(e -> browseTemplate());
        templPanel.add(templBrowseB, java.awt.BorderLayout.EAST);

        optionsPanel.add(templPanel);

        numentChB.setText("Use numeric entities");
        optionsPanel.add(numentChB);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        optionsPanel.add(jPanel6);

        filePanel.add(optionsPanel, java.awt.BorderLayout.SOUTH);

        getContentPane().add(filePanel, java.awt.BorderLayout.CENTER);
        getRootPane().setDefaultButton(okB);
        pack();
    }//GEN-END:initComponents

    private void xhtmlChBActionPerformed() {//GEN-FIRST:event_xhtmlChBActionPerformed
        // TODO add your handling code here:
    }

    private void chooserActionPerformed() {//GEN-FIRST:event_chooserActionPerformed
        okB.setEnabled(fileChooser.getSelectedFile() != null);
    }

    private void browseTemplate() {
        File root = new File(System.getProperty("user.home"));
        FileSystemView fsv = new SingleRootFileSystemView(root);
        JFileChooser chooser = new JFileChooser(fsv);
        chooser.setFileHidingEnabled(true);
        chooser.setDialogTitle(Local.getString("Select file"));
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (templF.getText().length() > 0)
            chooser.setCurrentDirectory(new java.io.File(templF.getText()));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            templF.setText(chooser.getSelectedFile().getPath());
    }
    // End of variables declaration//GEN-END:variables

}
