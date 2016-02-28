package net.sf.memoranda;

import java.util.Vector;

public class CurrentNote {

    private static final Vector<NoteListener> noteListeners = new Vector<>();
    private static Note currentNote = null;

    public static Note get() {
        return currentNote;
    }

    public static void set(Note note, boolean toSaveCurrentNote) {
        noteChanged(note, toSaveCurrentNote);
        currentNote = note;
    }

    public static void reset() {
//    	 set toSave to true to mimic status quo behaviour only. the appropriate setting could be false
        set(null, true);
    }

    public static void addNoteListener(NoteListener nl) {
        noteListeners.add(nl);
    }

    private static void noteChanged(Note note, boolean toSaveCurrentNote) {
        for (NoteListener noteListener : noteListeners) {
            noteListener.noteChange(note, toSaveCurrentNote);
        }
    }
}
