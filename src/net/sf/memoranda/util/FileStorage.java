/**
 * Storage.java
 * Created on 12.02.2003, 0:21:40 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.sf.memoranda.*;
import net.sf.memoranda.ui.ExceptionDialog;
import net.sf.memoranda.ui.htmleditor.AltHTMLWriter;
import nu.xom.Builder;
import nu.xom.Document;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;
import java.net.URL;


/**
 *
 */
/*$Id: FileStorage.java,v 1.15 2006/10/09 23:31:58 alexeya Exp $*/
public class FileStorage implements Storage {

    private static String JN_DOCPATH = Util.getEnvDir();

    static {
        /*The 'MEMORANDA_HOME' key is an undocumented feature for
          hacking the default location (Util.getEnvDir()) of the memoranda
          storage dir. Note that memoranda.config file is always placed at fixed
          location (Util.getEnvDir()) anyway */
        String mHome = (String) Configuration.get("MEMORANDA_HOME");
        if (mHome.length() > 0) {
            JN_DOCPATH = mHome;
            /*DEBUG*/
            System.out.println("[DEBUG]***Memoranda storage path has set to: " +
                    JN_DOCPATH);
        }
    }

    private final HTMLEditorKit editorKit = new HTMLEditorKit();

    public FileStorage() {
    }

    public static void saveDocument(Document doc, String filePath) {
        /**
         * @todo: Configurable parameters
         */
        try {
            OutputStreamWriter fw =
                    new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
            fw.write(doc.toXML());
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            new ExceptionDialog(
                    ex,
                    "Failed to write a document to " + filePath,
                    "");
        }
    }

    private static Document openDocument(InputStream in) throws Exception {
        Builder builder = new Builder();
        return builder.build(new InputStreamReader(in, "UTF-8"));
    }

    public static Document openDocument(String filePath) {
        try {
            return openDocument(new FileInputStream(filePath));
        } catch (Exception ex) {
            new ExceptionDialog(
                    ex,
                    "Failed to read a document from " + filePath,
                    "");
        }
        return null;
    }

    private static boolean documentExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     */
    public void storeNote(Note note, javax.swing.text.Document doc) {
        String filename =
                JN_DOCPATH + note.getProject().getID() + File.separator;
        doc.putProperty(
                javax.swing.text.Document.TitleProperty,
                note.getTitle());

        filename += note.getId();
        System.out.println("[DEBUG] Save note: " + filename);

        try {
            OutputStreamWriter fw =
                    new OutputStreamWriter(new FileOutputStream(filename), "UTF-8");
            AltHTMLWriter writer = new AltHTMLWriter(fw, (HTMLDocument) doc);
            writer.write();
            fw.flush();
            fw.close();
        } catch (Exception ex) {
            new ExceptionDialog(
                    ex,
                    "Failed to write a document to " + filename,
                    "");
        }
    }

    /**
     * @see net.sf.memoranda.util.Storage#openNote(net.sf.memoranda.Note)
     */
    // it's OK to ignore the exception, it's used for control flow. UGH.
    @SuppressFBWarnings("DE_MIGHT_IGNORE")
    public javax.swing.text.Document openNote(Note note) {

        HTMLDocument doc = (HTMLDocument) editorKit.createDefaultDocument();
        if (note == null)
            return doc;
        String filename = getNotePath(note);
        try (InputStreamReader rdr = new InputStreamReader(new FileInputStream(filename), "UTF-8")) {
            doc.setBase(new URL(getNoteURL(note)));
            editorKit.read(rdr, doc, 0);
        } catch (Exception ex) {
            // Do nothing - we've got a new empty document!
        }

        return doc;
    }

    public String getNoteURL(Note note) {
        return "file:" + JN_DOCPATH + note.getProject().getID() + "/" + note.getId();
    }

    private String getNotePath(Note note) {
        String filename = JN_DOCPATH + note.getProject().getID() + File.separator;
        filename += note.getId();
        return filename;
    }


    public boolean removeNote(Note note) {
        File f = new File(getNotePath(note));
        /*DEBUG*/
        System.out.println("[DEBUG] Remove note:" + getNotePath(note));
        return f.delete();
    }

    /**
     * @see net.sf.memoranda.util.Storage#openProjectManager()
     */
    public void openProjectManager() {
        if (!new File(JN_DOCPATH + ".projects").exists()) {
            ProjectManager.setDoc(null);
            return;
        }
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Open project manager: " + JN_DOCPATH + ".projects");
        ProjectManager.setDoc(openDocument(JN_DOCPATH + ".projects"));
    }

    /**
     */
    public void storeProjectManager() {
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Save project manager: " + JN_DOCPATH + ".projects");
        saveDocument(ProjectManager.getDoc(), JN_DOCPATH + ".projects");
    }

    public boolean removeProjectStorage(Project prj) {
        String id = prj.getID();
        File f = new File(JN_DOCPATH + id);
        File[] files = f.listFiles();
        if (files == null) return false;
        boolean result = true;
        for (File file : files) {
            if (!file.delete()) {
                result = false;
            }
        }
        if (!f.delete()) {
            result = false;
        }
        return result;
    }

    public TaskList openTaskList(Project prj) {
        String fn = JN_DOCPATH + prj.getID() + File.separator + ".tasklist";

        if (documentExists(fn)) {
            /*DEBUG*/
            System.out.println(
                    "[DEBUG] Open task list: "
                            + JN_DOCPATH
                            + prj.getID()
                            + File.separator
                            + ".tasklist");

            Document tasklistDoc = openDocument(fn);
            return new TaskListImpl(tasklistDoc, prj);
        } else {
            /*DEBUG*/
            System.out.println("[DEBUG] New task list created");
            return new TaskListImpl(prj);
        }
    }

    public void storeTaskList(TaskList tasklist, Project prj) {
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Save task list: "
                        + JN_DOCPATH
                        + prj.getID()
                        + File.separator
                        + ".tasklist");
        Document tasklistDoc = tasklist.getXMLContent();
        saveDocument(tasklistDoc, JN_DOCPATH + prj.getID() + File.separator + ".tasklist");
    }

    /**
     * @see net.sf.memoranda.util.Storage#createProjectStorage(net.sf.memoranda.Project)
     */
    public boolean createProjectStorage(Project prj) {
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Create project dir: " + JN_DOCPATH + prj.getID());
        File dir = new File(JN_DOCPATH + prj.getID());
        return dir.mkdirs();
    }

    /**
     * @see net.sf.memoranda.util.Storage#openNoteList(net.sf.memoranda.Project)
     */
    public NoteList openNoteList(Project prj) {
        String fn = JN_DOCPATH + prj.getID() + File.separator + ".notes";
        if (documentExists(fn)) {
            /*DEBUG*/
            System.out.println(
                    "[DEBUG] Open note list: "
                            + JN_DOCPATH
                            + prj.getID()
                            + File.separator
                            + ".notes");
            return new NoteListImpl(openDocument(fn), prj);
        } else {
            /*DEBUG*/
            System.out.println("[DEBUG] New note list created");
            return new NoteListImpl(prj);
        }
    }

    /**
     * @see net.sf.memoranda.util.Storage#storeNoteList(net.sf.memoranda.NoteList, net.sf.memoranda.Project)
     */
    public void storeNoteList(NoteList nl, Project prj) {
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Save note list: "
                        + JN_DOCPATH
                        + prj.getID()
                        + File.separator
                        + ".notes");
        saveDocument(
                nl.getXMLContent(),
                JN_DOCPATH + prj.getID() + File.separator + ".notes");
    }

    /**
     */
    public void openEventsManager() {
        if (!new File(JN_DOCPATH + ".events").exists()) {
            EventsManager.setDoc(null);
            return;
        }
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Open events manager: " + JN_DOCPATH + ".events");
        EventsManager.setDoc(openDocument(JN_DOCPATH + ".events"));
    }

    /**
     */
    public void storeEventsManager() {
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Save events manager: " + JN_DOCPATH + ".events");
        saveDocument(EventsManager.getDoc(), JN_DOCPATH + ".events");
    }

    /**
     * @see net.sf.memoranda.util.Storage#openMimeTypesList()
     */
    public void openMimeTypesList() {
        if (!new File(JN_DOCPATH + ".mimetypes").exists()) {
            try {
                MimeTypesList.setDoc(openDocument(
                                FileStorage.class.getResourceAsStream(
                                        "resources/default.mimetypes")));
            } catch (Exception e) {
                new ExceptionDialog(
                        e,
                        "Failed to read default mimetypes config from resources",
                        "");
            }
            return;
        }
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Open mimetypes list: " + JN_DOCPATH + ".mimetypes");
        MimeTypesList.setDoc(openDocument(JN_DOCPATH + ".mimetypes"));
    }

    /**
     * @see net.sf.memoranda.util.Storage#storeMimeTypesList()
     */
    public void storeMimeTypesList() {
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Save mimetypes list: " + JN_DOCPATH + ".mimetypes");
        saveDocument(MimeTypesList.getDoc(), JN_DOCPATH + ".mimetypes");
    }

    /**
     * @see net.sf.memoranda.util.Storage#openResourcesList(net.sf.memoranda.Project)
     */
    public ResourcesList openResourcesList(Project prj) {
        String fn = JN_DOCPATH + prj.getID() + File.separator + ".resources";
        if (documentExists(fn)) {
            /*DEBUG*/
            System.out.println("[DEBUG] Open resources list: " + fn);
            return new ResourcesListImpl(openDocument(fn), prj);
        } else {
            /*DEBUG*/
            System.out.println("[DEBUG] New note list created");
            return new ResourcesListImpl(prj);
        }
    }

    /**
     * @see net.sf.memoranda.util.Storage#storeResourcesList(net.sf.memoranda.ResourcesList, net.sf.memoranda.Project)
     */
    public void storeResourcesList(ResourcesList rl, Project prj) {
        /*DEBUG*/
        System.out.println(
                "[DEBUG] Save resources list: "
                        + JN_DOCPATH
                        + prj.getID()
                        + File.separator
                        + ".resources");
        saveDocument(
                rl.getXMLContent(),
                JN_DOCPATH + prj.getID() + File.separator + ".resources");
    }

    /**
     * @see net.sf.memoranda.util.Storage#restoreContext()
     */
    // it's fine for it to catch runtime exceptions. this is used here for control flow, as awful as that is.
    @SuppressFBWarnings("REC_CATCH_EXCEPTION")
    public void restoreContext() {
        try (FileInputStream is = new FileInputStream(JN_DOCPATH + ".context")) {
            /*DEBUG*/
            System.out.println(
                    "[DEBUG] Open context: " + JN_DOCPATH + ".context");
            Context.context.load(is);
        } catch (Exception ex) {
            /*DEBUG*/
            System.out.println("Context created.");
        }
    }

    /**
     * @see net.sf.memoranda.util.Storage#storeContext()
     */
    public void storeContext() {
        try (OutputStream os = new FileOutputStream(JN_DOCPATH + ".context")) {
            System.out.println(
                    "[DEBUG] Save context: " + JN_DOCPATH + ".context");
            Context.context.save(os);
        } catch (Exception ex) {
            new ExceptionDialog(
                    ex,
                    "Failed to store context to " + JN_DOCPATH + ".context",
                    "");
        }
    }

    /**
     * See storage interface.
     */
    public DefectList openDefectList(Project project) {
        String files = JN_DOCPATH + project.getID() + File.separator + ".defectlist";
        DefectList defectList;
        if (documentExists(files)) {
            Document defectDocument = openDocument(files);
            defectList = new DefectListImpl(defectDocument, project);
        } else {
            defectList = new DefectListImpl(project);
        }
        return defectList;
    }

    /**
     * See storage interface.
     */
    public void storeDefectList(DefectList defectList, Project project) {
        saveDocument(defectList.getXMLContent(), JN_DOCPATH + project.getID() + File.separator + ".defectlist");
    }
}
