package net.sf.memoranda.ui;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.Resource;
import net.sf.memoranda.ui.table.TableSorter;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.MimeType;
import net.sf.memoranda.util.MimeTypesList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.util.Date;
import java.util.Vector;

/*$Id: ResourcesTable.java,v 1.4 2004/04/05 10:05:44 alexeya Exp $*/
class ResourcesTable extends JTable {

    public static final int _RESOURCE = 100;
    private final ImageIcon inetIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/mimetypes/inetshortcut.png"));
    private Vector<Resource> files = null;
    private TableSorter sorter = null;

    public ResourcesTable() {
        super();
        initTable();
        sorter = new TableSorter(new ResourcesTableModel());
        sorter.addMouseListenerToHeaderInTable(this);
        setModel(sorter);
        this.setShowGrid(false);
        this.setFont(new Font("Dialog", 0, 11));
        initColumsWidth();
        //this.setModel(new ResourcesTableModel());
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange() {

            }

            public void projectWasChanged() {
                tableChanged();
            }
        });
    }

    private void initColumsWidth() {
        for (int i = 0; i < 4; i++) {
            TableColumn column = getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(32767);
            } else {
                column.setMinWidth(100);
                column.setPreferredWidth(100);
            }
        }
    }

    public void tableChanged() {
        initTable();
        sorter.tableChanged(null);
        initColumsWidth();
        updateUI();
    }

    private void initTable() {
        Vector v = CurrentProject.getResourcesList().getAllResources();
        files = new Vector<>();
        for (Object aV : v) {
            Resource r = (Resource) aV;
            if (!r.isInetShortcut()) {
                File f = new File(r.getPath());
                if (f.isFile())
                    files.add(r);
            } else
                files.add(r);
        }

    }

    public TableCellRenderer getCellRenderer(int row, int column) {
        return new javax.swing.table.DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                JLabel comp;

                comp = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 0) {
                    Resource r = (Resource) getModel().getValueAt(row, _RESOURCE);
                    if (!r.isInetShortcut())
                        comp.setIcon(MimeTypesList.getMimeTypeForFile((String) value).getIcon());
                    else
                        comp.setIcon(inetIcon);
                }
                return comp;
            }
        };

    }

    class ResourcesTableModel extends AbstractTableModel {

        final String[] columnNames = {
                Local.getString("Name"),
                Local.getString("Type"),
                Local.getString("Date modified"),
                Local.getString("Path")};

        public String getColumnName(int i) {
            return columnNames[i];
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return files.size();
        }


        public Object getValueAt(int row, int col) {
            Resource r = files.get(row);
            if (col == _RESOURCE)
                return r;
            if (!r.isInetShortcut()) {
                File f = new File(r.getPath());
                switch (col) {
                    case 0:
                        return f.getName();
                    case 1:
                        MimeType mt = MimeTypesList.getMimeTypeForFile(f.getName());
                        return mt.getLabel();
                    case 2:
                        Date d = new Date(f.lastModified());
                        return d;
                    case 3:
                        return f.getPath();
                }
            } else {
                if (col == 0)
                    return r.getPath();
                else if (col == 1)
                    return Local.getString("Internet shortcut");
                else
                    return "";
            }
            return null;
        }


        public Class getColumnClass(int col) {
            try {
                switch (col) {
                    case 0:
                    case 1:
                    case 3:
                        return Class.forName("java.lang.String");
                    case 2:
                        return Class.forName("java.util.Date");
                }
            } catch (Exception ex) {
                new ExceptionDialog(ex);
            }
            return null;
        }
    }

}