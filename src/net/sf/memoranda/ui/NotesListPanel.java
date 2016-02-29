package net.sf.memoranda.ui;

import javax.swing.*;
import java.awt.*;

/*$Id: NotesListPanel.java,v 1.5 2005/01/29 13:55:26 rawsushi Exp $*/
class NotesListPanel extends JPanel {
    public final NotesList notesList = new NotesList();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JScrollPane scrollPane = new JScrollPane();

    public NotesListPanel() {
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