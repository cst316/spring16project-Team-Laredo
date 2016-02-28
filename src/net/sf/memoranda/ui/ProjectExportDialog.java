package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;

/*$Id: ProjectExportDialog.java,v 1.3 2004/04/05 10:05:44 alexeya Exp $*/
class ProjectExportDialog extends JDialog {

    final JComboBox encCB = new JComboBox(new String[]{Local.getString("System default"), "UTF-8", "ANSI"});
    final JCheckBox splitChB = new JCheckBox();
    final JCheckBox titlesAsHeadersChB = new JCheckBox();
    private final JPanel jPanel2 = new JPanel();
    private final BorderLayout borderLayout3 = new BorderLayout();
    private final JPanel jPanel3 = new JPanel();
    private final JButton okB = new JButton();
    private final JButton cancelB = new JButton();
    private final FlowLayout flowLayout1 = new FlowLayout();
    private final JPanel jPanel4 = new JPanel();
    private final JPanel jPanel1 = new JPanel();
    private final BorderLayout borderLayout2 = new BorderLayout();
    private final JLabel jLabel1 = new JLabel();
    private final GridLayout gridLayout1 = new GridLayout();
    public boolean CANCELLED = true;
    BorderLayout borderLayout1 = new BorderLayout();
    private JFileChooser fileChooser = null;

    public ProjectExportDialog(Frame frame, String title, JFileChooser chooser) {
        super(frame, title, true);
        try {
            fileChooser = chooser;
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }


    private void jbInit() {
        this.setResizable(false);

        Border border1 = BorderFactory.createEmptyBorder(10, 10, 0, 10);
        Border border2 = BorderFactory.createEmptyBorder(5, 10, 5, 5);
        fileChooser.setBorder(null);
        fileChooser.setControlButtonsAreShown(false);
        jPanel2.setLayout(borderLayout3);
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Save"));
        okB.addActionListener(e -> {
            okB_actionPerformed();
            if (fileChooser.getUI() instanceof BasicFileChooserUI) //Added to fix problem with export note
            //jcscoobyrs 17-Nov-2003 at 08:36:14 AM
            {//Added to fix problem with export note jcscoobyrs 17-Nov-2003 at 08:36:14 AM
                BasicFileChooserUI ui = (BasicFileChooserUI) fileChooser.getUI();//Added to fix problem with export note
                //jcscoobyrs 17-Nov-2003 at 08:36:14 AM
                ui.getApproveSelectionAction().actionPerformed(e);//Added to fix problem with export note
                //jcscoobyrs 17-Nov-2003 at 08:36:14 AM
            }//Added to fix problem with export note jcscoobyrs 17-Nov-2003 at 08:36:14 AM
        });
        this.getRootPane().setDefaultButton(okB);
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        borderLayout3.setHgap(5);
        borderLayout3.setVgap(5);
        jPanel2.setBorder(border1);
        jPanel3.setBorder(border2);
        jPanel1.setLayout(borderLayout2);
        jLabel1.setMaximumSize(new Dimension(155, 16));
        jLabel1.setPreferredSize(new Dimension(80, 16));
        jLabel1.setText(Local.getString("Encoding") + ":");
        jPanel4.setLayout(gridLayout1);
        splitChB.setText(Local.getString("Split notes into separate files"));
        gridLayout1.setColumns(1);
        gridLayout1.setRows(3);
        titlesAsHeadersChB.setText(Local.getString("Notes titles as headers"));
        this.getContentPane().add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel4, BorderLayout.SOUTH);
        jPanel4.add(jPanel1, null);
        jPanel1.add(encCB, BorderLayout.CENTER);
        jPanel1.add(jLabel1, BorderLayout.WEST);
        jPanel4.add(splitChB, null);
        jPanel2.add(fileChooser, BorderLayout.NORTH);
        this.getContentPane().add(jPanel3, BorderLayout.SOUTH);
        jPanel3.add(okB, null);
        jPanel3.add(cancelB, null);
        jPanel4.add(titlesAsHeadersChB, null);
    }

    private void cancelB_actionPerformed() {
        this.dispose();
    }

    private void okB_actionPerformed() {
        CANCELLED = false;
        this.dispose();
    }
}