/**
 * Storage.java
 * Created on 12.02.2003, 0:58:42 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.util;

import net.sf.memoranda.*;

/**
 *
 */
/*$Id: Storage.java,v 1.4 2004/01/30 12:17:42 alexeya Exp $*/
public interface Storage {

    TaskList openTaskList(Project prj);

    void storeTaskList(TaskList tl, Project prj);

    NoteList openNoteList(Project prj);

    void storeNoteList(NoteList nl, Project prj);

    /**
     * Retrieves a defect list from a file.
     * @param project Project that defect list will be pulled from.
     * @return DefectList Returns a defect list from project or creates a new defect list for project.
     */
    DefectList openDefectList(Project project);

    /**
     * Stores a defect list for a project.
     * @param defectList The defect list that will be sorted.
     * @param project The project that the defect list is associated with.
     */
    void storeDefectList(DefectList defectList, Project project);

    void storeNote(Note note, javax.swing.text.Document doc);

    javax.swing.text.Document openNote(Note note);

    void removeNote(Note note);

    String getNoteURL(Note note);

    void openProjectManager();

    void storeProjectManager();

    void openEventsManager();

    void storeEventsManager();

    void openMimeTypesList();

    void storeMimeTypesList();

    void createProjectStorage(Project prj);

    void removeProjectStorage(Project prj);

    ResourcesList openResourcesList(Project prj);

    void storeResourcesList(ResourcesList rl, Project prj);

    void restoreContext();

    void storeContext();

}
