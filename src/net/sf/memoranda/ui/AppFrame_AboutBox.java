package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: AppFrame_AboutBox.java,v 1.13 2005/11/09 22:38:07 alexeya Exp $*/
class AppFrame_AboutBox extends JDialog implements ActionListener {

    private final JButton button1 = new JButton();
    private final JLabel lblText = new JLabel();

    private final String developersHead = Local.getString("Developers") + ":";
    private final String[] developers = {
            "Alex Alishevskikh (alexeya@users.sourceforge.net)",
            "Patrick Bielen (bielen@stafa.nl)",
            "Ryan Ho (rawsushi@users.sourceforge.net)",
            "Ivan Ribas (ivanrise@users.sourceforge.net)",
            "Jyrki Velhonoja (velhonoja@kapsi.fi>)",
            "Jeremy Whitlock (jwhitlock@starprecision.com)"
    };
    private final String othersHead = Local.getString("Other contributors") + ":";
    private final String[] others = {
            "Thomas Chuffart (informatique@pierrelouiscarlier.fr)",
            "Willy Dobe (wdobe@gmx.de)",
            "Yunjie Liu (liu-610@163.com)",
            "Kenneth J. Pouncey (kjpou@pt.lu)",
            "Michael Radtke (mradtke@abigale.de)",
            "Carel-J Rischmuller (carel-j.rischmuller@epiuse.com)",
            "Milena Vitali-Charewicz (milo22370@yahoo.com)",
            "Toru Watanabe (t-wata@cablenet.ne.jp)"
    };

    public AppFrame_AboutBox(Frame parent) {
        super(parent);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(400, 500);
    }

    //Component initialization
    private void jbInit() {
        StringBuilder text = new StringBuilder("<html>");

        // append product info
        text.append("<b>");
        text.append("Version ");
        text.append(App.VERSION_INFO);
        text.append(" (Build ");
        text.append(App.BUILD_INFO);
        text.append(")</b><br><br>");

        //append copyright info
        text.append("Copyright (c) 2003, 2004 Memoranda team<br>");

        // append website info
        text.append(App.WEBSITE_URL);
        text.append("<br><br>");

        // append list of developers
        text.append("<b>");
        text.append(developersHead);
        text.append("</b><br>");

        for (String developer : developers) {
            text.append(developer);
            text.append("<br>");
        }

        // append list of other credits
        text.append("<br><b>");
        text.append(othersHead);
        text.append("</b><br>");
        for (String other : others) {
            text.append(other);
            text.append("<br>");
        }

        text.append("</html>");

        ImageIcon image = new ImageIcon(AppFrame_AboutBox.class.getResource("resources/memoranda.png"));
        this.setTitle(Local.getString("About Memoranda"));
        setResizable(false);
        // Initialize Objects
        lblText.setFont(new java.awt.Font("Dialog", 0, 11));
        lblText.setText(text.toString());
        lblText.setBounds(10, 55, 300, 400);


        button1.setText(Local.getString("Ok"));
        button1.setBounds(150, 415, 95, 30);
        button1.addActionListener(this);
        button1.setPreferredSize(new Dimension(95, 30));
        button1.setBackground(new Color(69, 125, 186));
        button1.setForeground(Color.white);
        JLayeredPane layeredPane = getLayeredPane();
        JLabel imgLabel = new JLabel(image);
        imgLabel.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
        layeredPane.add(imgLabel, 1);
        layeredPane.add(lblText, 2);
        layeredPane.add(button1, 2);
        this.getContentPane().setBackground(new Color(251, 197, 63));
    }

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            cancel();
        }
        super.processWindowEvent(e);
    }

    //Close the dialog
    private void cancel() {
        dispose();
    }

    //Close the dialog on a button event
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            cancel();
        }
    }
}
