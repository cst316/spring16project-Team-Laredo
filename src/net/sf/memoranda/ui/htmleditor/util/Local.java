/**
 * Local.java
 * Created on 05.09.2003, 16:43:39 Alex
 * Package: org.openmechanics.htmleditor
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 OpenMechanics.org
 */
package net.sf.memoranda.ui.htmleditor.util;

import java.util.Hashtable;

/**
 *
 */
public class Local {

    private static Hashtable<String, String> messages = null;

    static {
        messages = new Hashtable<>();
    }

    public static void setMessages(Hashtable<String, String> msgs) {
        if (msgs != null) {
            messages.putAll(msgs);
        }
    }

    public static String getString(String key) {
        if (messages == null) {
            return key;
        }
        String msg = messages.get(key.trim().toUpperCase());
        if ((msg != null) && (msg.length() > 0)) {
            return msg;
        } else {
            return key;
        }
    }

}
