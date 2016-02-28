package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;

import javax.swing.*;
import java.awt.*;

/*$Id: TaskCalcDialog.java,v 1.3 2005/06/10 18:36:24 velhonoja Exp $*/
class TaskCalcDialog extends JDialog {
    final JCheckBox compactDatesChB = new JCheckBox();
    final JCheckBox calcEffortChB = new JCheckBox();
    final JCheckBox calcCompletionChB = new JCheckBox();
    private final JPanel topPanel = new JPanel(new BorderLayout());
    private final JPanel generalPanel = new JPanel(new GridBagLayout());
    private final JButton okB = new JButton();
    private final JButton cancelB = new JButton();
    private final JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    private final JLabel label1 = new JLabel();
    private final JLabel label2 = new JLabel();
    public boolean CANCELLED = true;
    ButtonGroup closeGroup = new ButtonGroup();

    public TaskCalcDialog(Frame frame) {
        super(frame, Local.getString("Preferences"), true);
        try {
            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    public TaskCalcDialog() {
        this(null);
    }

    private void jbInit() {
        this.setResizable(false);
        label1.setHorizontalAlignment(SwingConstants.RIGHT);
        label1.setText(Local.getString("Calculate and update data for this task using data from sub tasks."));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(2, 10, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        generalPanel.add(label1, gbc);

        label2.setHorizontalAlignment(SwingConstants.RIGHT);
        label2.setText(Local.getString("Please select data fields to update") + ":");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(2, 10, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        generalPanel.add(label2, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(2, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        compactDatesChB.setText(Local.getString("Compact task dates based on sub task dates"));
//		compactDatesChB.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				enSystrayChB_actionPerformed(e);
//			}
//		});
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.insets = new Insets(2, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        generalPanel.add(compactDatesChB, gbc);
        calcEffortChB.setText(Local.getString("Calculate task effort based on sub task efforts"));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.insets = new Insets(2, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        generalPanel.add(calcEffortChB, gbc);
        calcCompletionChB.setText(Local.getString("Calculate task completion based on sub task completion"));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(2, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        generalPanel.add(calcCompletionChB, gbc);
//		calcCompletionChB.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				enSplashChB_actionPerformed(e);
//			}
//		});
        // Build TopPanel
        topPanel.add(generalPanel, BorderLayout.CENTER);

        // Build BottomPanel
        okB.setMaximumSize(new Dimension(100, 25));
        okB.setPreferredSize(new Dimension(100, 25));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(e -> okB_actionPerformed());
        this.getRootPane().setDefaultButton(okB);
        bottomPanel.add(okB);
        cancelB.setMaximumSize(new Dimension(100, 25));
        cancelB.setPreferredSize(new Dimension(100, 25));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        bottomPanel.add(cancelB);

        // Build Preferences-Dialog
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // set all config-values
        setValues();

    }

    private void setValues() {
        calcCompletionChB.setSelected(true);
        compactDatesChB.setSelected(true);
        calcEffortChB.setSelected(true);
    }

    private void okB_actionPerformed() {
        CANCELLED = false;
        this.dispose();
    }

    private void cancelB_actionPerformed() {
        this.dispose();
    }
}