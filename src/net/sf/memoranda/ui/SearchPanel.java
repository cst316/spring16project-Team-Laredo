package net.sf.memoranda.ui;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Note;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.Document;
import java.awt.*;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*$Id: SearchPanel.java,v 1.5 2004/04/05 10:05:44 alexeya Exp $*/
class SearchPanel extends JPanel {
    final NotesList notesList = new NotesList(NotesList.EMPTY);
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JScrollPane scrollPane = new JScrollPane();
    private final JPanel jPanel1 = new JPanel();
    private final BorderLayout borderLayout2 = new BorderLayout();
    private final JTextField searchField = new JTextField();
    private final JPanel jPanel2 = new JPanel();
    private final BorderLayout borderLayout3 = new BorderLayout();
    private final JPanel jPanel3 = new JPanel();
    private final JPanel jPanel4 = new JPanel();
    private final JCheckBox caseSensCB = new JCheckBox();
    private final JCheckBox regexpCB = new JCheckBox();
    private final JCheckBox wholeWCB = new JCheckBox();
    private final JButton searchB = new JButton();
    private final BorderLayout borderLayout4 = new BorderLayout();
    private final BorderLayout borderLayout5 = new BorderLayout();
    private final Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);
    JProgressBar progressBar = new JProgressBar();

    public SearchPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    private void jbInit() throws Exception {
        Border border1 = BorderFactory.createEmptyBorder(2, 2, 2, 2);

        TitledBorder titledBorder1 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("Search") + ":");

        this.setLayout(borderLayout1);

        jPanel1.setLayout(borderLayout2);
        jPanel2.setLayout(borderLayout3);
        jPanel2.setBorder(titledBorder1);
        titledBorder1.setTitleFont(new java.awt.Font("Dialog", 1, 11));
        searchField.setFont(new java.awt.Font("Dialog", 1, 10));
        searchField.addCaretListener(e -> searchField_caretUpdate());
        jPanel3.setLayout(borderLayout5);
        caseSensCB.setText(Local.getString("Case sensitive"));
        caseSensCB.setFont(new java.awt.Font("Dialog", 1, 10));
        caseSensCB.setMargin(new Insets(0, 0, 0, 0));

        regexpCB.setFont(new java.awt.Font("Dialog", 1, 10));
        regexpCB.setMargin(new Insets(0, 0, 0, 0));
        regexpCB.setText(Local.getString("Regular expressions"));
        wholeWCB.setText(Local.getString("Whole words only"));
        wholeWCB.setMargin(new Insets(0, 0, 0, 0));
        wholeWCB.setFont(new java.awt.Font("Dialog", 1, 10));
        searchB.setEnabled(false);
        searchB.setFont(new java.awt.Font("Dialog", 1, 11));
        searchB.setMaximumSize(new Dimension(72, 25));
        searchB.setMinimumSize(new Dimension(2, 25));
        searchB.setPreferredSize(new Dimension(70, 25));
        searchB.setMargin(new Insets(0, 0, 0, 0));
        searchB.setText(Local.getString("Search"));
        searchB.addActionListener(e -> searchB_actionPerformed());
        jPanel4.setLayout(borderLayout4);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(jPanel1, BorderLayout.NORTH);
        scrollPane.getViewport().add(notesList);
        jPanel1.add(jPanel2, BorderLayout.NORTH);
        jPanel2.add(searchField, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jPanel4, BorderLayout.NORTH);
        jPanel4.add(caseSensCB, BorderLayout.SOUTH);
        jPanel4.add(wholeWCB, BorderLayout.NORTH);
        jPanel4.add(regexpCB, BorderLayout.CENTER);
        jPanel3.add(searchB, BorderLayout.SOUTH);
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange() {
                notesList.update(new Vector());
            }

            public void projectWasChanged() {
            }
        });
        //notesList.update(new Vector());

    }

    private void searchB_actionPerformed() {
        Cursor cur = App.getFrame().getCursor();
        App.getFrame().setCursor(waitCursor);
        doSearch();
        App.getFrame().setCursor(cur);
    }

    private void searchField_caretUpdate() {
        searchB.setEnabled(searchField.getText().length() > 0);
    }


    private void doSearch() {
        Pattern pattern;
        //this.add(progressBar, BorderLayout.SOUTH);
        int flags = Pattern.DOTALL;
        if (!caseSensCB.isSelected())
            flags = flags + Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE;
        String _find = searchField.getText();
        if (!regexpCB.isSelected())
            _find = "\\Q" + _find + "\\E";
        if (wholeWCB.isSelected())
            _find = "[\\s\\p{Punct}]" + _find + "[\\s\\p{Punct}]";
        try {
            pattern = Pattern.compile(_find, flags);
        } catch (Exception ex) {
            new ExceptionDialog(ex, "Error in regular expression", "Check the regular expression syntax");
            return;
        }
        /*progressBar.setMinimum(0);
        progressBar.setStringPainted(true);*/
        Vector notes = (Vector) CurrentProject.getNoteList().getAllNotes();
        Vector found = new Vector();
        /*progressBar.setMaximum(notes.size()-1);
        progressBar.setIndeterminate(false);
        this.add(progressBar, BorderLayout.SOUTH);*/
        for (Object note1 : notes) {
            //progressBar.setValue(i);
            Note note = (Note) note1;
            Document doc = CurrentStorage.get().openNote(note);
            try {
                String txt = doc.getText(0, doc.getLength());
                Matcher matcher = pattern.matcher(txt);
                if (matcher.find())
                    found.add(note);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //this.remove(progressBar);
        this.notesList.update(found);
    }

}