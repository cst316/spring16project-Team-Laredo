package net.sf.memoranda.ui.htmleditor;

import net.sf.memoranda.ui.htmleditor.util.Local;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 *
 * @author unascribed
 * @version 1.0
 */

class FindDialog extends JDialog {
    public final JTextField txtSearch = new JTextField();
    public final JCheckBox chkReplace = new JCheckBox();
    public final JCheckBox chkCaseSens = new JCheckBox();
    public final JCheckBox chkWholeWord = new JCheckBox();
    public final JCheckBox chkRegExp = new JCheckBox();
    public final JTextField txtReplace = new JTextField();
    private final JLabel header = new JLabel();
    private final JPanel areaPanel = new JPanel(new GridBagLayout());
    private final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    private final JButton cancelB = new JButton();
    private final JButton okB = new JButton();
    private final JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JLabel lblSearch = new JLabel();
    public boolean CANCELLED = false;

    public FindDialog() {
        super((Frame) null, Local.getString("Find & replace"), true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() {
        this.setResizable(false);
        // Build Header and its layout

        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Find & replace"));
        header.setIcon(
                new ImageIcon(
                        net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource(
                                "resources/icons/findbig.png")));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(header);
        this.getContentPane().add(headerPanel, BorderLayout.NORTH);

        // build areaPanel
        lblSearch.setText(Local.getString("Search for") + ":");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(lblSearch, gbc);
        txtSearch.setPreferredSize(new Dimension(300, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(txtSearch, gbc);
        chkWholeWord.setText(Local.getString("Whole words only"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 5, 25);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(chkWholeWord, gbc);
        chkRegExp.setText(Local.getString("Regular expressions"));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 25, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(chkRegExp, gbc);
        chkCaseSens.setText(Local.getString("Case sensitive"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(chkCaseSens, gbc);
        chkReplace.setText(Local.getString("Replace with") + ":");
        chkReplace.addActionListener(e -> replaceChB_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(chkReplace, gbc);
        txtReplace.setPreferredSize(new Dimension(300, 25));
        txtReplace.setEnabled(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(txtReplace, gbc);
        areaPanel.setBorder(BorderFactory.createEtchedBorder(
                Color.white, new Color(142, 142, 142)));
        this.getContentPane().add(areaPanel, BorderLayout.CENTER);

        // Initialize buttons
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Find"));
        okB.addActionListener(e -> okB_actionPerformed());
        this.getRootPane().setDefaultButton(okB);
        // build button-panel
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void okB_actionPerformed() {
        this.dispose();
    }

    private void cancelB_actionPerformed() {
        CANCELLED = true;
        this.dispose();
    }

    private void replaceChB_actionPerformed() {
        txtReplace.setEnabled(chkReplace.isSelected());
    }

}