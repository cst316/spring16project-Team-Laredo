package net.sf.memoranda.ui.htmleditor;

import net.sf.memoranda.ui.htmleditor.util.Local;

import javax.swing.*;
import javax.swing.border.Border;
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

class ReplaceOptionsDialog extends JDialog {

    public static final int YES_OPTION = 0;
    public static final int YES_TO_ALL_OPTION = 1;
    public static final int CANCEL_OPTION = 3;
    private static final int NO_OPTION = 2;
    private final JPanel panel1 = new JPanel();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JPanel areaPanel = new JPanel();
    private final JPanel buttonsPanel = new JPanel();
    private final JButton cancelB = new JButton();
    private final JButton yesAllB = new JButton();
    private final FlowLayout flowLayout1 = new FlowLayout(FlowLayout.LEFT);
    private final BorderLayout borderLayout3 = new BorderLayout();
    private final JLabel textLabel = new JLabel();
    private final JButton yesB = new JButton();
    private final JButton noB = new JButton();
    public int option = 0;

    public ReplaceOptionsDialog(String text) {
        super((Frame) null, Local.getString("Replace"), true);
        try {
            textLabel.setText(text);
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() {
        this.setResizable(false);
        textLabel.setIcon(new ImageIcon(net.sf.memoranda.ui.htmleditor.HTMLEditor.class.getResource("resources/icons/findbig.png")));
        textLabel.setIconTextGap(10);
        Border border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border border2 = BorderFactory.createEmptyBorder();

        panel1.setLayout(borderLayout1);

        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        // cancelB.setFocusable(false);

        yesAllB.setText(Local.getString("Yes to all"));
        yesAllB.addActionListener(e -> yesAllB_actionPerformed());
        //yesAllB.setFocusable(false);
        buttonsPanel.setLayout(flowLayout1);
        panel1.setBorder(border1);
        areaPanel.setLayout(borderLayout3);
        areaPanel.setBorder(border2);
        borderLayout3.setHgap(5);
        borderLayout3.setVgap(5);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        yesB.addActionListener(e -> yesB_actionPerformed());
        yesB.setText(Local.getString("Yes"));

        //yesB.setFocusable(false);
        this.getRootPane().setDefaultButton(yesB);


        noB.setText(Local.getString("No"));
        noB.addActionListener(e -> noB_actionPerformed());
        // noB.setFocusable(false);
        buttonsPanel.add(yesB, null);
        getContentPane().add(panel1);
        panel1.add(areaPanel, BorderLayout.CENTER);
        areaPanel.add(textLabel, BorderLayout.WEST);
        panel1.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.add(yesAllB, null);
        buttonsPanel.add(noB, null);
        buttonsPanel.add(cancelB, null);


    }

    private void yesAllB_actionPerformed() {
        option = YES_TO_ALL_OPTION;
        this.dispose();
    }

    private void cancelB_actionPerformed() {
        option = CANCEL_OPTION;
        this.dispose();
    }

    private void yesB_actionPerformed() {
        option = YES_OPTION;
        this.dispose();
    }

    private void noB_actionPerformed() {
        option = NO_OPTION;
        this.dispose();
    }

}