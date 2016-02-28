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

class LinkDialog extends JDialog {
    public final JLabel header = new JLabel();
    public final JTextField txtURL = new JTextField();
    public final JTextField txtName = new JTextField();
    public final JTextField txtTitle = new JTextField();
    final JTextField txtDesc = new JTextField();
    final JCheckBox chkNewWin = new JCheckBox();
    private final JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel areaPanel = new JPanel(new GridBagLayout());
    private final JLabel lblURL = new JLabel();
    private final JLabel lblName = new JLabel();
    private final JLabel lblTitle = new JLabel();
    private final JLabel lblDesc = new JLabel();
    private final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    private final JButton okB = new JButton();
    private final JButton cancelB = new JButton();
    public boolean CANCELLED = false;

    public LinkDialog() {
        super((Frame) null, Local.getString("Insert hyperlink"), true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void jbInit() {
        this.setResizable(false);
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Insert hyperlink"));
        header.setIcon(new ImageIcon(
                net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource(
                        "resources/icons/linkbig.png")));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(header);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);

        lblURL.setText(Local.getString("URL"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(lblURL, gbc);
        txtURL.setPreferredSize(new Dimension(300, 25));
        txtURL.setText("http://");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(txtURL, gbc);
        lblName.setText(Local.getString("Name"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(lblName, gbc);
        txtName.setPreferredSize(new Dimension(300, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(txtName, gbc);
        lblTitle.setText(Local.getString("Title"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(lblTitle, gbc);
        txtTitle.setPreferredSize(new Dimension(300, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(txtTitle, gbc);
        lblDesc.setText(Local.getString("Description"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(lblDesc, gbc);
        txtDesc.setPreferredSize(new Dimension(300, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(txtDesc, gbc);
        chkNewWin.setText(Local.getString("Open in a new window"));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 5, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(chkNewWin, gbc);
        areaPanel.setBorder(BorderFactory.createEtchedBorder(Color.white,
                new Color(142, 142, 142)));
        this.getContentPane().add(areaPanel, BorderLayout.CENTER);

        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText("Ok");
        okB.addActionListener(e -> okB_actionPerformed());
        this.getRootPane().setDefaultButton(okB);
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
        this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

    }

    private void okB_actionPerformed() {
        this.dispose();
    }

    private void cancelB_actionPerformed() {
        CANCELLED = true;
        this.dispose();
    }
}