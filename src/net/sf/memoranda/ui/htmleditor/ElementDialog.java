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

class ElementDialog extends JDialog {
    public final JTextField classField = new JTextField();
    public final JTextField idField = new JTextField();
    public final JTextField styleField = new JTextField();
    private final JLabel header = new JLabel();
    private final JPanel areaPanel = new JPanel(new GridBagLayout());
    private final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    private final JButton cancelB = new JButton();
    private final JButton okB = new JButton();
    private final JLabel lblClass = new JLabel();
    private final JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JLabel lblID = new JLabel();
    private final JLabel lblStyle = new JLabel();
    public boolean CANCELLED = false;

    public ElementDialog() {
        super((Frame) null, Local.getString("Object properties"), true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void jbInit() {
        this.setResizable(false);
        headerPanel.setBackground(Color.WHITE);
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Object properties"));
        header.setIcon(new ImageIcon(
                net.sf.memoranda.ui.htmleditor.ElementDialog.class.getResource(
                        "resources/icons/textbig.png")));
        headerPanel.add(header);
        this.getContentPane().add(headerPanel, BorderLayout.NORTH);

        areaPanel.setBorder(BorderFactory.createEtchedBorder(Color.white,
                new Color(142, 142, 142)));
        lblID.setText(Local.getString("ID"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 5, 5);
        areaPanel.add(lblID, gbc);
        idField.setPreferredSize(new Dimension(300, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 5, 10);
        areaPanel.add(idField, gbc);
        lblClass.setText(Local.getString("Class"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 5);
        areaPanel.add(lblClass, gbc);
        classField.setPreferredSize(new Dimension(300, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 10);
        areaPanel.add(classField, gbc);
        lblStyle.setText(Local.getString("Style"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 10, 5);
        areaPanel.add(lblStyle, gbc);
        styleField.setPreferredSize(new Dimension(300, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 10, 10);
        areaPanel.add(styleField, gbc);
        this.getContentPane().add(areaPanel, BorderLayout.CENTER);

        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(e -> okB_actionPerformed());
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