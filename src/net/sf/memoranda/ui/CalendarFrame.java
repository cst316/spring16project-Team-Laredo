package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/*$Id: CalendarFrame.java,v 1.5 2004/04/05 10:05:44 alexeya Exp $*/
class CalendarFrame extends JInternalFrame {

    public final JNCalendarPanel cal = new JNCalendarPanel();

    public CalendarFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            new ExceptionDialog(e);
        }

    }

    private void jbInit() throws Exception {
        Border border1 = BorderFactory.createLineBorder(Color.gray, 1);
        this.setClosable(true);
        this.setTitle(Local.getString("Select date"));
        this.setBorder(border1);
        //this.setPreferredSize(new Dimension(200, 200));
        this.setToolTipText("");
        cal.setPreferredSize(new Dimension(this.getContentPane().getWidth(),
                this.getContentPane().getHeight()));
        this.getContentPane().add(cal, BorderLayout.CENTER);
    }
}