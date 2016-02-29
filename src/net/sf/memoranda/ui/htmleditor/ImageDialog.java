package net.sf.memoranda.ui.htmleditor;

import net.sf.memoranda.ui.htmleditor.util.Local;
import net.sf.memoranda.util.SingleRootFileSystemView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 *
 * @author unascribed
 * @version 1.0
 */
class ImageDialog extends JDialog implements WindowListener {
    public final JTextField fileField = new JTextField();
    public final JTextField altField = new JTextField();
    public final JTextField widthField = new JTextField();
    public final JTextField heightField = new JTextField();
    public final JTextField hspaceField = new JTextField();
    public final JTextField vspaceField = new JTextField();
    public final JTextField borderField = new JTextField();
    public final JTextField urlField = new JTextField();
    // Note: align values are not localized because they are HTML keywords
    public final JComboBox<? extends String> alignCB;
    private final JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JLabel header = new JLabel();
    private final JPanel areaPanel = new JPanel(new GridBagLayout());
    private final JLabel jLabel1 = new JLabel();
    private final JButton browseB = new JButton();
    private final JLabel jLabel2 = new JLabel();
    private final JLabel jLabel3 = new JLabel();
    private final JLabel jLabel4 = new JLabel();
    private final JLabel jLabel5 = new JLabel();
    private final JLabel jLabel6 = new JLabel();
    private final JLabel jLabel7 = new JLabel();
    private final JLabel jLabel8 = new JLabel();
    private final String[] aligns = {"left", "right", "top", "middle", "bottom", "absmiddle",
            "texttop", "baseline"};
    private final JLabel jLabel9 = new JLabel();
    private final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    private final JButton okB = new JButton();
    private final JButton cancelB = new JButton();
    public boolean CANCELLED = false;

    public ImageDialog() {
        super((Frame) null, Local.getString("Image"), true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.addWindowListener(this);
        alignCB = new JComboBox<>(aligns);
    }

    private void jbInit() {
        this.setResizable(false);
        // three Panels, so used BorderLayout for this dialog.
        headerPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
        headerPanel.setBackground(Color.WHITE);
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Image"));
        header.setIcon(new ImageIcon(
                net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource(
                        "resources/icons/imgbig.png")));
        headerPanel.add(header);
        this.getContentPane().add(headerPanel, BorderLayout.NORTH);

        areaPanel.setBorder(new EtchedBorder(Color.white, new Color(142, 142,
                142)));
        jLabel1.setText(Local.getString("Image file"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel1, gbc);
        fileField.setMinimumSize(new Dimension(200, 25));
        fileField.setPreferredSize(new Dimension(285, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(10, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(fileField, gbc);
        browseB.setMinimumSize(new Dimension(25, 25));
        browseB.setPreferredSize(new Dimension(25, 25));
        browseB.setIcon(new ImageIcon(
                net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource(
                        "resources/icons/fileopen16.png")));
        browseB.addActionListener(e -> browseB_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(browseB, gbc);
        jLabel2.setText(Local.getString("ALT text"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel2, gbc);
        altField.setPreferredSize(new Dimension(315, 25));
        altField.setMinimumSize(new Dimension(200, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(altField, gbc);
        jLabel3.setText(Local.getString("Width"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel3, gbc);
        widthField.setPreferredSize(new Dimension(30, 25));
        widthField.setMinimumSize(new Dimension(30, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(widthField, gbc);
        jLabel4.setText(Local.getString("Height"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 50, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel4, gbc);
        heightField.setMinimumSize(new Dimension(30, 25));
        heightField.setPreferredSize(new Dimension(30, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(heightField, gbc);
        jLabel5.setText(Local.getString("H. space"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel5, gbc);
        hspaceField.setMinimumSize(new Dimension(30, 25));
        hspaceField.setPreferredSize(new Dimension(30, 25));
        hspaceField.setText("0");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(hspaceField, gbc);
        jLabel6.setText(Local.getString("V. space"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 50, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel6, gbc);
        vspaceField.setMinimumSize(new Dimension(30, 25));
        vspaceField.setPreferredSize(new Dimension(30, 25));
        vspaceField.setText("0");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(vspaceField, gbc);
        jLabel7.setText(Local.getString("Border"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel7, gbc);
        borderField.setMinimumSize(new Dimension(30, 25));
        borderField.setPreferredSize(new Dimension(30, 25));
        borderField.setText("0");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(borderField, gbc);
        jLabel8.setText(Local.getString("Align"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 50, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel8, gbc);
        alignCB.setBackground(new Color(230, 230, 230));
        alignCB.setFont(new java.awt.Font("Dialog", 1, 10));
        alignCB.setPreferredSize(new Dimension(100, 25));
        alignCB.setSelectedIndex(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(alignCB, gbc);
        jLabel9.setText(Local.getString("Hyperlink"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 10, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel9, gbc);
        urlField.setPreferredSize(new Dimension(315, 25));
        urlField.setMinimumSize(new Dimension(200, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 5, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(urlField, gbc);
        this.getContentPane().add(areaPanel, BorderLayout.CENTER);

        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(e -> okB_actionPerformed());
        this.getRootPane().setDefaultButton(okB);
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);
        this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void okB_actionPerformed() {
        this.dispose();
    }

    private void cancelB_actionPerformed() {
        CANCELLED = true;
        this.dispose();
    }

    private ImageIcon getPreviewIcon(java.io.File file) {
        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        ImageIcon thmb = null;
        if (tmpIcon.getIconHeight() > 48) {
            thmb = new ImageIcon(tmpIcon.getImage()
                    .getScaledInstance(-1, 48, Image.SCALE_DEFAULT));
        } else {
            thmb = tmpIcon;
        }
        if (thmb.getIconWidth() > 350) {
            return new ImageIcon(thmb.getImage()
                    .getScaledInstance(350, -1, Image.SCALE_DEFAULT));
        } else {
            return thmb;
        }
    }

    //java.io.File selectedFile = null;
    public void updatePreview() {
        try {
            if (new java.net.URL(fileField.getText()).getPath().equals(""))
                header.setIcon(getPreviewIcon(new java.io.File(
                        new java.net.URL(fileField.getText()).getPath())));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        CANCELLED = true;
        this.dispose();
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    private void browseB_actionPerformed() {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.lookInLabelText", Local
                .getString("Look in:"));
        UIManager.put("FileChooser.upFolderToolTipText", Local.getString(
                "Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", Local.getString(
                "Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", Local
                .getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                .getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", Local.getString(
                "File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString(
                "Files of Type:"));
        UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
        UIManager.put("FileChooser.openButtonToolTipText", Local.getString(
                "Open selected file"));
        UIManager
                .put("FileChooser.cancelButtonText", Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString(
                "Cancel"));

        File root = new File(System.getProperty("user.home"));
        FileSystemView fsv = new SingleRootFileSystemView(root);
        JFileChooser chooser = new JFileChooser(fsv);
        chooser.setFileHidingEnabled(true);
        chooser.setDialogTitle(Local.getString("Choose an image file"));
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(
                new net.sf.memoranda.ui.htmleditor.filechooser.ImageFilter());
        chooser.setAccessory(
                new net.sf.memoranda.ui.htmleditor.filechooser.ImagePreview(
                        chooser));
        chooser.setPreferredSize(new Dimension(550, 375));
        java.io.File lastSel = (java.io.File) Context.get(
                "LAST_SELECTED_IMG_FILE");
        if (lastSel != null)
            chooser.setCurrentDirectory(lastSel);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                fileField.setText(chooser.getSelectedFile().toURI().toURL().toString());
                header.setIcon(getPreviewIcon(chooser.getSelectedFile()));
                Context
                        .put("LAST_SELECTED_IMG_FILE", chooser
                                .getSelectedFile());
            } catch (Exception ex) {
                fileField.setText(chooser.getSelectedFile().getPath());
            }
            try {
                ImageIcon img = new ImageIcon(chooser.getSelectedFile()
                        .getPath());
                widthField.setText(Integer.toString(img.getIconWidth()));
                heightField
                        .setText(Integer.toString(img.getIconHeight()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}