/**
 * HTMLFileImport.java
 * Created on 16.03.2003, 14:34:48 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.util;

import net.sf.memoranda.ui.ExceptionDialog;
import net.sf.memoranda.ui.htmleditor.HTMLEditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 *
 */
/*$Id: HTMLFileImport.java,v 1.5 2005/07/05 08:17:28 alexeya Exp $*/
public class HTMLFileImport {

    /**
     * Constructor for HTMLFileImport.
     */
    public HTMLFileImport(File f, HTMLEditor editor) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
            String line = in.readLine();
            while (line != null) {
                builder.append(line);
                builder.append('\n');
                line = in.readLine();
            }
        } catch (Exception e) {
            new ExceptionDialog(e, "Failed to import " + f.getPath(), "");
            return;
        }
        String text = builder.toString();
        text = Pattern.compile("<body(.*?)>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
                .split(text)[1];
        text = Pattern.compile("</body>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
                .split(text)[0];

        editor.insertHTML(text, editor.editor.getCaretPosition());

    }

}
