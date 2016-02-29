package net.sf.memoranda.ui;

/**
 * ExportFilter.java
 * Created on 25.02.2003, 17:30:12 Alex
 * Package:
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 *
 */
/*$Id: ExportFilter.java,v 1.2 2004/01/30 12:17:41 alexeya Exp $*/
class ExportFilter extends FileFilter {

    public static final String HTML = "HTML";
    private static final String RTF = "RTF";
    private static final String XHTML = "XHTML";
    private static final String ZIP = "ZIP";

    private String _type = "";

    /**
     * Constructor for ExportFilter.
     */
    public ExportFilter(String type) {
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
            case XHTML:
                return (ext.equals("xhtml") || ext.equals("xml"));
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
            case XHTML:
                return "XHTML files (*.xhtml, *.xml)";
        }
        return "HTML files (*.html, *.htm)";
    }

}
