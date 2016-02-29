package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

/*$Id: ResourceTypeDialog.java,v 1.11 2004/07/01 14:44:10 pbielen Exp $*/
class ResourceTypeDialog extends JDialog {
    private final JButton cancelB = new JButton();
    private final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private final JLabel header = new JLabel();
    private final JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JButton okB = new JButton();
    //JPanel mPanel = new JPanel(new BorderLayout());
    private final ResourceTypePanel areaPanel = new ResourceTypePanel();
    public String ext = "";
    Border border2;
    TitledBorder titledBorder2;
    boolean CANCELLED = true;

    public ResourceTypeDialog(JFrame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    private void jbInit() throws Exception {
        this.setResizable(false);
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Resource type"));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.ResourceTypeDialog.class.getResource(
                "resources/icons/resource48.png")));
        dialogTitlePanel.add(header);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);

        //mPanel.add(areaPanel, BorderLayout.CENTER);
        this.getContentPane().add(areaPanel, BorderLayout.CENTER);

        cancelB.addActionListener(e -> cancelB_actionPerformed());
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));


        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(e -> okB_actionPerformed());
        this.getRootPane().setDefaultButton(okB);
        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);

        this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    }


    private void cancelB_actionPerformed() {
        this.dispose();
    }

    private void okB_actionPerformed() {
        CANCELLED = false;
        this.dispose();
    }

    public JList getTypesList() {
        return areaPanel.typesList;
    }


}