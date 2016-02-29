/**
 * ResourcesList.java
 * Created on 24.03.2003, 18:25:59 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import nu.xom.Document;

import java.util.Vector;

/**
 *
 */
/*$Id: ResourcesList.java,v 1.4 2007/03/20 06:21:46 alexeya Exp $*/
public interface ResourcesList {

    Vector getAllResources();

    //Vector getResourcesForTask(String taskId);

    Resource getResource(String path);

    void addResource(String path, boolean isInternetShortcut, boolean isProjectFile);

    void addResource(String path);

    boolean removeResource(String path);

    int getAllResourcesCount();

    Document getXMLContent();

}
