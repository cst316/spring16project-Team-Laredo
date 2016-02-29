package net.sf.memoranda.ui;

/**
 * AllFilesFilter.java
 * Created on 25.02.2003, 17:30:12 Alex
 * Package:
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

import net.sf.memoranda.util.Local;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 *
 */
/*$Id: AllFilesFilter.java,v 1.5 2004/01/30 12:17:41 alexeya Exp $*/
class AllFilesFilter extends FileFilter {

    public static final String HTML = "HTML";
    public static final String XHTML = "XHTML";
    public static final String ZIP = "ZIP";
    public static final String EXE = "EXE";
    public static final String WAV = "WAV";
    private static final String RTF = "RTF";
    private static final String JAR = "JAR";
    private static final String ICO = "ICO";
    private String _type = "";

    /**
     * Constructor for AllFilesFilter.
     */
    public AllFilesFilter(String type) {
        super();
        _type = type;
    }

    private static String getExtension(File f) {
        String ext = "";
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    /**
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        String ext = getExtension(f);
        switch (_type) {
            case RTF:
                return ext.equals("rtf");
            case ZIP:
                return ext.equals("zip");
            case EXE:
                return (ext.equals("exe") || ext.equals("com") || ext.equals("bat"));
            case JAR:
                return ext.equals("jar");
            case WAV:
                return (ext.equals("wav") || ext.equals("au"));
            case XHTML:
                return (ext.equals("xhtml") || ext.equals("xml"));
            case ICO:
                return (ext.equals("ico") || ext.equals("png"));
        }
        return ext.startsWith("htm");
    }

    /**
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    public String getDescription() {
        switch (_type) {
            case RTF:
                return "Rich Text Format (*.rtf)";
            case ZIP:
                return "ZIP archives (*.zip)";
            case EXE:
                return Local.getString("Executable Files") + " (*.exe, *.com, *.bat)";
            case JAR:
                return "JAR " + Local.getString("Files") + " (*.jar)";
            case WAV:
                return Local.getString("Sound files") + " (*.wav, *.au)";
            case XHTML:
                return "XHTML files (*.xhtml, *.xml)";
            case ICO:
                return Local.getString("Icon") + " " + Local.getString("Files") + " (*.ico, *.png)";
        }
        return "HTML files (*.html, *.htm)";
    }

}
