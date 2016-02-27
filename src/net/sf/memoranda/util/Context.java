package net.sf.memoranda.util;

import net.sf.memoranda.ui.AppFrame;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 *
 * @author unascribed
 * @version 1.0
 */

/*$Id: Context.java,v 1.3 2004/01/30 12:17:42 alexeya Exp $*/
public class Context {

    public static final LoadableProperties context = new LoadableProperties();

    static {
        CurrentStorage.get().restoreContext();
        AppFrame.addExitListener(e -> CurrentStorage.get().storeContext());
    }

    public static Object get(String key) {
        return context.get(key);
    }

    public static void put(String key, Object value) {
        context.put(key, value);
    }

}