/**
 * CurrentStorage.java
 * Created on 13.02.2003, 18:30:59 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 *
 */
/*$Id: CurrentStorage.java,v 1.3 2004/01/30 12:17:42 alexeya Exp $*/
public class CurrentStorage {

    private static final Vector actionListeners = new Vector();
    private static Storage _storage = new FileStorage();

    public static Storage get() {
        return _storage;
    }

    public static void set(Storage storage) {
        _storage = storage;
        storageChanged();
    }

    private static void storageChanged() {
        for (Object actionListener : actionListeners)
            ((ActionListener) actionListener).actionPerformed(new ActionEvent(null, 0, "Current storage changed"));
    }

}
