package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;


class StickerConfirmation extends JDialog {
    private final JPanel panel1 = new JPanel();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final BorderLayout borderLayout2 = new BorderLayout();
    private final JButton cancelButton = new JButton();
    private final JButton okButton = new JButton();
    private final JPanel bottomPanel = new JPanel();
    private final JPanel topPanel = new JPanel();
    private final JLabel header = new JLabel();
    private final JPanel jPanel1 = new JPanel();
    private final JLabel jLabel1 = new JLabel();
    public boolean CANCELLED = true;

    public StickerConfirmation(Frame frame) {
        super(frame, Local.getString("Sticker"), true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    public StickerConfirmation() {
        this(null);
    }

    private void jbInit() {
        Border border1 = BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(
                        Color.white,
                        new Color(156, 156, 158)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Border border2 = BorderFactory.createEmptyBorder(5, 0, 5, 0);
        panel1.setLayout(borderLayout1);
        this.getContentPane().setLayout(borderLayout2);
        cancelButton.setMaximumSize(new Dimension(100, 25));
        cancelButton.setMinimumSize(new Dimension(100, 25));
        cancelButton.setPreferredSize(new Dimension(100, 25));
        cancelButton.setText(Local.getString("Cancel"));
        cancelButton.addActionListener(e -> cancelButton_actionPerformed());
        okButton.setMaximumSize(new Dimension(100, 25));
        okButton.setMinimumSize(new Dimension(100, 25));
        okButton.setPreferredSize(new Dimension(100, 25));
        okButton.setText(Local.getString("Ok"));
        okButton.addActionListener(e -> okButton_actionPerformed());
        this.getRootPane().setDefaultButton(okButton);

        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
        topPanel.setBackground(Color.WHITE);

        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Sticker"));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.StickerDialog.class.getResource(
                "resources/icons/sticker48.png")));


        jLabel1.setText(Local.getString("DELETE THIS STICKER?"));
        panel1.setBorder(border1);
        jPanel1.setBorder(border2);
        getContentPane().add(panel1, BorderLayout.CENTER);
        panel1.add(jPanel1, BorderLayout.SOUTH);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        topPanel.add(header);
        jPanel1.add(jLabel1, BorderLayout.WEST);

    }


    private void cancelButton_actionPerformed() {
        this.dispose();
    }

    private void okButton_actionPerformed() {
        CANCELLED = false;
        this.dispose();
    }


}