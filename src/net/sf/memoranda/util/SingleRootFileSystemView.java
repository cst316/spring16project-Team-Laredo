package net.sf.memoranda.util;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * A FileSystemView class that limits the file selections to a single root.
 * <p>
 * When used with the JFileChooser component the user will only be able to
 * traverse the directories contained within the specified root fill.
 * <p>
 * The "Look In" combo box will only display the specified root.
 * <p>
 * The "Up One Level" button will be disable when at the root.
 * <p>
 * Obtained from https://tips4java.wordpress.com/2009/01/28/single-root-file-chooser/
 */
public class SingleRootFileSystemView extends FileSystemView {
    private final File root;
    private final File[] roots = new File[1];

    public SingleRootFileSystemView(File root) {
        super();
        this.root = root;
        roots[0] = root;
    }

    @Override
    public File createNewFolder(File containingDir) {
        File folder = new File(containingDir, "New Folder");
        folder.mkdir();
        return folder;
    }

    @Override
    public File getDefaultDirectory() {
        return root;
    }

    @Override
    public File getHomeDirectory() {
        return root;
    }

    @Override
    public File[] getRoots() {
        return roots;
    }
}