package net.sf.memoranda.ui;

import net.sf.memoranda.util.Configuration;
import net.sf.memoranda.util.Local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class ExitConfirmationDialog extends JDialog implements WindowListener {

    private final JLabel header = new JLabel();
    private final JCheckBox donotaskCB = new JCheckBox();
    private final JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel bottomPanel = new JPanel(new BorderLayout());
    private final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    private final JButton okB = new JButton();
    private final JButton cancelB = new JButton();
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    public boolean CANCELLED = false;

    public ExitConfirmationDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
        super.addWindowListener(this);
    }

    private void jbInit() {
        this.setResizable(false);

        // Build headerPanel
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Exit"));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.EventDialog.class.getResource(
                "resources/icons/exit.png")));
        headerPanel.add(header);

        // Build mainPanel
        JLabel confirm = new JLabel();
        confirm.setText("<HTML>" + Local.getString("This action will cause Memoranda to exit") +
                "<p>" + Local.getString("Do you want to continue?"));

        donotaskCB.setText(Local.getString("do not ask again"));
        donotaskCB.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(donotaskCB, BorderLayout.SOUTH);
        mainPanel.add(confirm, BorderLayout.CENTER);

        // Build ButtonsPanel
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(e -> okB_actionPerformed());
        this.getRootPane().setDefaultButton(okB);
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        this.getRootPane().setDefaultButton(okB);

        // Build dialog
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.getContentPane().add(headerPanel, BorderLayout.NORTH);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    // if donotaskCB is checked update Configuration.
    private void checkDoNotAsk() {
        if (this.donotaskCB.isSelected()) {
            Configuration.put("ASK_ON_EXIT", "no");
            Configuration.saveConfig();
        }
    }

    // ok button action
    private void okB_actionPerformed() {
        checkDoNotAsk();
        this.dispose();
    }

    //cancel button action
    private void cancelB_actionPerformed() {
        CANCELLED = true;
        checkDoNotAsk();
        this.dispose();
    }

    public void windowClosing(WindowEvent e) {
        CANCELLED = true;
        this.dispose();
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
}
