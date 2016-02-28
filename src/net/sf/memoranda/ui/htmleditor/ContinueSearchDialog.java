package net.sf.memoranda.ui.htmleditor;

import net.sf.memoranda.ui.htmleditor.util.Local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class ContinueSearchDialog extends JPanel {
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final FlowLayout flowLayout1 = new FlowLayout();
    private final JButton cancelB = new JButton();
    private final JButton continueB = new JButton();
    private final JPanel buttonsPanel = new JPanel();
    private final JLabel jLabel1 = new JLabel();
    private final JTextField textF = new JTextField();
    public boolean cont = false;
    public boolean cancel = false;
    private String text;
    private Thread thread;

    public ContinueSearchDialog(Thread t, String txt) {
        try {
            text = txt;
            thread = t;
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() {

        this.setLayout(borderLayout1);
        textF.setEditable(false);
        textF.setText(text);
        cancelB.setMaximumSize(new Dimension(120, 26));
        cancelB.setMinimumSize(new Dimension(80, 26));
        cancelB.setPreferredSize(new Dimension(120, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setFocusable(false);
        cancelB.addActionListener(this::cancelB_actionPerformed);
        continueB.addActionListener(this::continueB_actionPerformed);
        continueB.setText(Local.getString("Find next"));
        continueB.setPreferredSize(new Dimension(120, 26));
        continueB.setMinimumSize(new Dimension(80, 26));
        continueB.setMaximumSize(new Dimension(120, 26));
        continueB.setFocusable(false);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        buttonsPanel.setLayout(flowLayout1);

        jLabel1.setText(" " + Local.getString("Search for") + ":  ");
        jLabel1.setIcon(new ImageIcon(net.sf.memoranda.ui.htmleditor.HTMLEditor.class.getResource("resources/icons/findbig.png")));
        this.add(jLabel1, BorderLayout.WEST);
        this.add(textF, BorderLayout.CENTER);
        buttonsPanel.add(continueB, null);
        buttonsPanel.add(cancelB, null);
        this.add(buttonsPanel, BorderLayout.EAST);
    }

    void cancelB_actionPerformed(ActionEvent e) {
        cont = true;
        cancel = true;
        thread.resume();
    }

    void continueB_actionPerformed(ActionEvent e) {
        cont = true;
        thread.resume();
    }
}