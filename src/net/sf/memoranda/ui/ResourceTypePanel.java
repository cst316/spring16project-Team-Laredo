package net.sf.memoranda.ui;

import net.sf.memoranda.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;

/*$Id: ResourceTypePanel.java,v 1.8 2004/10/18 19:09:10 ivanrise Exp $*/
class ResourceTypePanel extends JPanel {
    public final JList typesList = new JList();
    private final JPanel jPanel1 = new JPanel();
    private final JButton newTypeB = new JButton();
    private final JScrollPane jScrollPane1 = new JScrollPane();
    private final BorderLayout borderLayout2 = new BorderLayout();
    private final JPanel areaPanel = new JPanel();
    private final JPanel jPanel2 = new JPanel();
    private final JButton editB = new JButton();
    private final JButton deleteB = new JButton();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final BorderLayout borderLayout3 = new BorderLayout();
    boolean CANCELLED = true;

    public ResourceTypePanel() {
        super();
        try {
            jbInit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() {
        BorderFactory.createLineBorder(SystemColor.controlText, 2);
        TitledBorder titledBorder1 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("Registered types"));
        BorderFactory.createLineBorder(Color.gray, 1);
        new TitledBorder(BorderFactory.createLineBorder(Color.gray, 1), Local.getString("Details"));
        Border border3 = BorderFactory.createEmptyBorder(0, 10, 0, 0);

        jPanel1.setBorder(titledBorder1);
        jPanel1.setLayout(borderLayout1);
        newTypeB.setMaximumSize(new Dimension(110, 25));
        newTypeB.setPreferredSize(new Dimension(110, 25));
        newTypeB.setText(Local.getString("New"));
        newTypeB.addActionListener(e -> newTypeB_actionPerformed());
        areaPanel.setLayout(borderLayout2);
        jPanel2.setMaximumSize(new Dimension(120, 32767));
        jPanel2.setMinimumSize(new Dimension(120, 36));
        jPanel2.setPreferredSize(new Dimension(120, 36));
        jPanel2.setBorder(border3);
        editB.setText(Local.getString("Edit"));
        editB.addActionListener(e -> editB_actionPerformed());
        editB.setEnabled(false);
        editB.setMaximumSize(new Dimension(110, 25));
        editB.setPreferredSize(new Dimension(110, 25));
        deleteB.setEnabled(false);
        deleteB.setMaximumSize(new Dimension(110, 25));
        deleteB.setPreferredSize(new Dimension(110, 25));
        deleteB.setText(Local.getString("Delete"));
        deleteB.addActionListener(e -> deleteB_actionPerformed());
        typesList.setCellRenderer(new TypesListRenderer());
        typesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        typesList.addListSelectionListener(e -> typesList_valueChanged());
        initTypesList();

        //typesList.setCellRenderer(new TypesListRenderer());
        this.setLayout(borderLayout3);
        this.add(areaPanel, BorderLayout.CENTER);
        areaPanel.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(typesList, null);
        jPanel1.add(jPanel2, BorderLayout.EAST);
        jPanel2.add(newTypeB, null);
        jPanel2.add(editB, null);
        jPanel2.add(deleteB, null);
        typesList.setListData(MimeTypesList.getAllMimeTypes());
        //typesList.updateUI();

    }

    private void initTypesList() {
        /*Vector v = new Vector();
        icons = new Vector();
        Vector t = MimeTypesList.getAllMimeTypes();
        for (int i = 0; i < t.size(); i++) {
            MimeType mt = (MimeType)t.get(i);
            v.add(mt.getLabel());
            icons.add(mt.getIcon());
        }*/
        typesList.setListData(MimeTypesList.getAllMimeTypes());
        typesList.updateUI();
    }


    private void newTypeB_actionPerformed() {
        EditTypeDialog dlg = new EditTypeDialog(App.getFrame(), Local.getString("New resource type"));
        Dimension dlgSize = new Dimension(420, 420);
        dlg.setSize(dlgSize);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        String ext = "";
        dlg.extField.setText(ext);
        dlg.descField.setText(ext);
        dlg.appPanel.argumentsField.setText("$1");
        dlg.iconLabel.setIcon(
                new ImageIcon(
                        net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/mimetypes/default.png")));
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        String typeId = Util.generateId();
        MimeType mt = MimeTypesList.addMimeType(typeId);
        String[] exts = dlg.extField.getText().trim().split(" ");
        for (String ext1 : exts) mt.addExtension(ext1);
        mt.setLabel(dlg.descField.getText());
        AppList appList = MimeTypesList.getAppList();
        if (dlg.appPanel.applicationField.getText().length() > 0) {
            File f = new File(dlg.appPanel.applicationField.getText());
            String appId = Util.generateId();
            appList.addApp(
                    appId,
                    f.getParent().replace('\\', '/'),
                    f.getName().replace('\\', '/'),
                    dlg.appPanel.argumentsField.getText());
            mt.setApp(appId);
        }
        if (dlg.iconPath.length() > 0)
            mt.setIconPath(dlg.iconPath);
        CurrentStorage.get().storeMimeTypesList();
        this.initTypesList();
        typesList.setSelectedValue(mt, true);
    }

    private void deleteB_actionPerformed() {
        MimeType mt = (MimeType) typesList.getSelectedValue();
        int n =
                JOptionPane.showConfirmDialog(
                        App.getFrame(),
                        Local.getString("Delete resource type")
                                + "\n'"
                                + mt.getLabel()
                                + "'\n"
                                + Local.getString("Are you sure?"),
                        Local.getString("Delete resource type"),
                        JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;
        MimeTypesList.removeMimeType(mt.getMimeTypeId());
        CurrentStorage.get().storeMimeTypesList();
        this.initTypesList();
    }

    private void editB_actionPerformed() {
        EditTypeDialog dlg = new EditTypeDialog(App.getFrame(), Local.getString("Edit resource type"));
        Dimension dlgSize = new Dimension(420, 450);
        dlg.setSize(dlgSize);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        MimeType mt = (MimeType) typesList.getSelectedValue();
        String[] exts = mt.getExtensions();

        StringBuilder extensionBuilder = new StringBuilder();
        for (String ext1 : exts) {
            extensionBuilder.append(ext1);
            extensionBuilder.append(" ");
        }
        dlg.extField.setText(extensionBuilder.toString());

        dlg.descField.setText(mt.getLabel());
        dlg.iconLabel.setIcon(mt.getIcon());
        AppList appList = MimeTypesList.getAppList();
        dlg.appPanel.applicationField.setText(
                appList.getFindPath(mt.getAppId()) + "/" + appList.getExec(mt.getAppId()));
        dlg.appPanel.argumentsField.setText(appList.getCommandLinePattern(mt.getAppId()));
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        String typeId = mt.getMimeTypeId();
        MimeTypesList.removeMimeType(typeId);
        mt = MimeTypesList.addMimeType(typeId);
        exts = dlg.extField.getText().trim().split(" ");
        for (String ext : exts) mt.addExtension(ext);
        mt.setLabel(dlg.descField.getText());
        if (dlg.appPanel.applicationField.getText().length() > 0) {
            File f = new File(dlg.appPanel.applicationField.getText());
            String appId = Util.generateId();
            appList.addApp(
                    appId,
                    f.getParent().replace('\\', '/'),
                    f.getName().replace('\\', '/'),
                    dlg.appPanel.argumentsField.getText());
            mt.setApp(appId);
        }
        if (dlg.iconPath.length() > 0)
            mt.setIconPath(dlg.iconPath);
        CurrentStorage.get().storeMimeTypesList();
        this.initTypesList();
        typesList.setSelectedValue(mt, true);

    }

    private void typesList_valueChanged() {
        boolean en = typesList.getSelectedValue() != null;
        this.editB.setEnabled(en);
        this.deleteB.setEnabled(en);
    }

    static class TypesListRenderer extends JLabel implements ListCellRenderer {

        public TypesListRenderer() {
            super();
        }

        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            MimeType mt = (MimeType) value;
            String[] exts = mt.getExtensions();

            StringBuilder extstr = new StringBuilder(mt.getLabel());
            extstr.append(" (");
            for (int j = 0; j < exts.length; j++) {
                extstr.append("*.");
                extstr.append(exts[j]);
                if (j != exts.length - 1)
                    extstr.append(", ");
            }
            extstr.append(")");

            setOpaque(true);
            setText(extstr.toString());
            setIcon(mt.getIcon());
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }


}