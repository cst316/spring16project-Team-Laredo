/**
 * CurrentProject.java
 * Created on 13.02.2003, 13:16:52 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *
 */
package net.sf.memoranda;

import net.sf.memoranda.ui.AppFrame;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Storage;

import java.util.Vector;

/**
 *
 */
/*$Id: CurrentProject.java,v 1.6 2005/12/01 08:12:26 alexeya Exp $*/
public class CurrentProject {

    private static Project _project = null;
    private static TaskList _tasklist = null;
    private static NoteList _notelist = null;
    private static DefectList m_defectList = null;
    private static ResourcesList _resources = null;
    private static DefectList _defectlist = null;
    private static Vector<ProjectListener> projectListeners = new Vector<>();

        
    static {
        String prjId = (String)Context.get("LAST_OPENED_PROJECT_ID");
        if (prjId == null) {
            prjId = "__default";
            Context.put("LAST_OPENED_PROJECT_ID", prjId);
        }
        //ProjectManager.init();
        _project = ProjectManager.getProject(prjId);
		
		if (_project == null) {
			// alexeya: Fixed bug with NullPointer when LAST_OPENED_PROJECT_ID
			// references to missing project
			_project = ProjectManager.getProject("__default");
			if (_project == null) 
				_project = (Project)ProjectManager.getActiveProjects().get(0);						
            Context.put("LAST_OPENED_PROJECT_ID", _project.getID());
			
		}		
		
        _tasklist = CurrentStorage.get().openTaskList(_project);
        _notelist = CurrentStorage.get().openNoteList(_project);
        _resources = CurrentStorage.get().openResourcesList(_project);
        _defectlist = CurrentStorage.get().openDefectList(_project);
        AppFrame.addExitListener(e -> save());
    }
        

    public static Project get() {
        return _project;
    }

    public static TaskList getTaskList() {
        return _tasklist;
    }

    public static NoteList getNoteList() {
        return _notelist;
    }
    
    public static ResourcesList getResourcesList() {
        return _resources;
    }

    public static DefectList getDefectList() { return _defectlist; }

    public static void set(Project project) {
        if (project.getID().equals(_project.getID())) return;
        TaskList newtasklist = CurrentStorage.get().openTaskList(project);
        NoteList newnotelist = CurrentStorage.get().openNoteList(project);
        DefectList newDefectList = CurrentStorage.get().openDefectList(project);
        ResourcesList newresources = CurrentStorage.get().openResourcesList(project);
        notifyListenersBefore(project, newnotelist,  newtasklist, newDefectList, newresources);
        _project = project;
        _tasklist = newtasklist;
        _notelist = newnotelist;
        m_defectList = newDefectList;
        _resources = newresources;
        notifyListenersAfter();
        Context.put("LAST_OPENED_PROJECT_ID", project.getID());
    }

    public static void addProjectListener(ProjectListener pl) {
        projectListeners.add(pl);
    }


    private static void notifyListenersBefore(Project project, NoteList nl, TaskList tl, DefectList dl, ResourcesList rl) {
        for (Object projectListener : projectListeners) {
            ((ProjectListener) projectListener).projectChange(project, nl, tl, dl, rl);
        }
    }
    
    private static void notifyListenersAfter() {
        for (Object projectListener : projectListeners) {
            ((ProjectListener) projectListener).projectWasChanged();
        }
    }

    public static void save() {
        Storage storage = CurrentStorage.get();

        storage.storeNoteList(_notelist, _project);
        storage.storeTaskList(_tasklist, _project); 
        storage.storeDefectList(m_defectList, _project);
        storage.storeResourcesList(_resources, _project);
        storage.storeProjectManager();
        storage.storeDefectList(_defectlist, _project);
    }

}
