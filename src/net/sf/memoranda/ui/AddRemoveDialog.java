package net.sf.memoranda.ui;

import javax.swing.*;
import java.awt.*;

class AddRemoveDialog extends JDialog {
    public final JTextField txtValue;
    public final JButton btnConfirm = new JButton("Confirm");
    public final JComboBox<String> comboBox = new JComboBox<>();

    public AddRemoveDialog() {
        getContentPane().setPreferredSize(new Dimension(285, 65));
        getContentPane().setMinimumSize(new Dimension(285, 65));
        getContentPane().setMaximumSize(new Dimension(285, 65));
        setPreferredSize(new Dimension(350, 65));
        setMinimumSize(new Dimension(350, 65));
        setMaximumSize(new Dimension(350, 65));
        setTitle("Modify Timer");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        txtValue = new JTextField();
        txtValue.setMaximumSize(new Dimension(20, 6));
        getContentPane().add(txtValue);
        txtValue.setColumns(10);
        comboBox.setMaximumSize(new Dimension(28, 20));

        comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"SECONDS", "MINUTES", "HOURS"}));
        getContentPane().add(comboBox);

        getContentPane().add(btnConfirm);
    }
}