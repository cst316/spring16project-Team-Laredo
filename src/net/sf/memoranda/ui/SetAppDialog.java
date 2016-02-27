package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/*$Id: SetAppDialog.java,v 1.6 2004/04/05 10:05:44 alexeya Exp $*/
class SetAppDialog extends JDialog {
    public final SetApplicationPanel appPanel = new SetApplicationPanel();
    private final FlowLayout flowLayout1 = new FlowLayout();
    private final JButton cancelB = new JButton();
    private final BorderLayout borderLayout3 = new BorderLayout();
    private final JPanel buttonsPanel = new JPanel();
    private final JPanel mPanel = new JPanel();
    private final JButton okB = new JButton();
    public boolean CANCELLED = true;
    BorderLayout borderLayout2 = new BorderLayout();

    public SetAppDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }


    private void jbInit() {
        this.setResizable(false);
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        borderLayout3.setHgap(5);
        buttonsPanel.setLayout(flowLayout1);
        appPanel.applicationField.addCaretListener(e -> applicationField_caretUpdate());

        mPanel.setLayout(borderLayout3);
        okB.setEnabled(false);
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(e -> okB_actionPerformed());
        this.getRootPane().setDefaultButton(okB);
        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);
        mPanel.add(appPanel, BorderLayout.NORTH);
        mPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.getContentPane().add(mPanel, BorderLayout.CENTER);
    }

    public void setDirectory(File dir) {
        appPanel.d = dir;
    }

    private void cancelB_actionPerformed() {
        this.dispose();
    }

    private void okB_actionPerformed() {
        File f = new File(appPanel.applicationField.getText());
        if (f.isFile()) {
            CANCELLED = false;
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(App.getFrame(), Local.getString("File not found!"), "", JOptionPane.ERROR_MESSAGE);
            this.appPanel.applicationField.setText("");
            checkOkEnabled();
        }
    }

    private void applicationField_caretUpdate() {
        checkOkEnabled();
    }

    private void checkOkEnabled() {
        okB.setEnabled(appPanel.applicationField.getText().length() > 0);
    }
}