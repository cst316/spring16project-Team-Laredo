/**
 * BookmarksPanel.java
 * Created on 17.03.2003, 22:55:39 Alex
 * Package: net.sf.memoranda.ui
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.ui;

import javax.swing.*;
import java.awt.*;

/*$Id: BookmarksPanel.java,v 1.4 2004/04/05 10:05:44 alexeya Exp $*/
class BookmarksPanel extends JPanel {
    public final NotesList notesList = new NotesList(NotesList.BOOKMARKS);
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JScrollPane scrollPane = new JScrollPane();

    public BookmarksPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        this.add(scrollPane, BorderLayout.CENTER);
        scrollPane.getViewport().add(notesList, null);


    }


}
