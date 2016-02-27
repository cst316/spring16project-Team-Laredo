package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.SingleRootFileSystemView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

/*$Id: EditTypeDialog.java,v 1.9 2005/07/05 08:17:24 alexeya Exp $*/
class EditTypeDialog extends JDialog {
    public final JTextField extField = new JTextField();
    public final JTextField descField = new JTextField();
    public final SetApplicationPanel appPanel = new SetApplicationPanel();
    final JLabel iconLabel = new JLabel();
    private final JLabel header = new JLabel();
    private final JButton cancelB = new JButton();
    private final JPanel buttonsPanel = new JPanel();
    private final FlowLayout flowLayout7 = new FlowLayout();
    private final JPanel jPanel1 = new JPanel();
    private final JPanel dialogTitlePanel = new JPanel();
    private final JButton okB = new JButton();
    private final JPanel areaPanel = new JPanel();
    private final JPanel mPanel = new JPanel();
    private final FlowLayout flowLayout1 = new FlowLayout();
    private final BorderLayout borderLayout2 = new BorderLayout();
    private final BorderLayout borderLayout3 = new BorderLayout();
    private final JPanel jPanel2 = new JPanel();
    private final BorderLayout borderLayout4 = new BorderLayout();
    private final JLabel jLabel1 = new JLabel();
    private final JPanel jPanel4 = new JPanel();
    private final BorderLayout borderLayout6 = new BorderLayout();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JPanel jPanel5 = new JPanel();
    private final BorderLayout borderLayout7 = new BorderLayout();
    private final BorderLayout borderLayout5 = new BorderLayout();
    private final JButton setIconB = new JButton();
    private final JPanel jPanel3 = new JPanel();
    public boolean CANCELLED = true;
    public String iconPath = "";
    String[] mimes = {"application", "audio", "image", "text", "video"};

    public EditTypeDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    private void jbInit() {
        this.setResizable(false);
        Border border1 = BorderFactory.createLineBorder(Color.gray, 1);
        TitledBorder titledBorder1 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("File type extensions"));
        Border border2 = BorderFactory.createLineBorder(Color.gray, 1);
        TitledBorder titledBorder2 = new TitledBorder(border2, Local.getString("Description"));
        Border border3 = BorderFactory.createLineBorder(Color.gray, 1);
        TitledBorder titledBorder3 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("Command line"));
        Border border4 = BorderFactory.createEmptyBorder();
        TitledBorder titledBorder4 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("Description"));
        Border border5 = BorderFactory.createLineBorder(Color.gray, 1);
        TitledBorder titledBorder5 = new TitledBorder(border5, Local.getString("Application"));
        Border border6 = BorderFactory.createEmptyBorder();
        TitledBorder titledBorder6 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("Icon") + ":");
        Border border7 = BorderFactory.createEmptyBorder();
        TitledBorder titledBorder7 = new TitledBorder(BorderFactory.createEmptyBorder(), Local.getString("MIME-type") + ":");
        Border border8 = BorderFactory.createLineBorder(Color.gray, 1);
        Border border9 = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        buttonsPanel.setLayout(flowLayout1);
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        flowLayout7.setAlignment(FlowLayout.LEFT);
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Resource type"));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.EditTypeDialog.class.getResource(
                "resources/icons/resource48.png")));
        jPanel1.setLayout(borderLayout1);
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setLayout(flowLayout7);
        dialogTitlePanel.setBorder(border9);
        okB.setEnabled(false);
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(e -> okB_actionPerformed());
        this.getRootPane().setDefaultButton(okB);
        areaPanel.setLayout(borderLayout2);
        mPanel.setLayout(borderLayout3);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        borderLayout3.setHgap(5);
        jPanel2.setBorder(titledBorder1);
        jPanel2.setLayout(borderLayout4);
        extField.setMaximumSize(new Dimension(2147483647, 24));
        extField.setMinimumSize(new Dimension(4, 24));
        extField.setPreferredSize(new Dimension(300, 24));
        extField.addCaretListener(e -> extField_caretUpdate());
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 11));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Local.getString("List of file extensions, divided by spaces"));
        descField.setPreferredSize(new Dimension(300, 24));
        descField.addCaretListener(e -> descField_caretUpdate());
        descField.setMaximumSize(new Dimension(2147483647, 24));
        descField.setMinimumSize(new Dimension(4, 24));
        jPanel4.setBorder(titledBorder4);
        jPanel4.setLayout(borderLayout6);
        jPanel4.setPreferredSize(new Dimension(300, 24));
        appPanel.setBorder(titledBorder5);
        jPanel5.setLayout(borderLayout7);
        iconLabel.setMaximumSize(new Dimension(24, 24));
        iconLabel.setMinimumSize(new Dimension(24, 24));
        iconLabel.setPreferredSize(new Dimension(24, 24));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setIconTextGap(0);
        setIconB.setMaximumSize(new Dimension(160, 24));
        setIconB.setMinimumSize(new Dimension(100, 24));
        setIconB.setText(Local.getString("Set icon"));
        setIconB.addActionListener(e -> setIconB_actionPerformed());
        jPanel3.setLayout(borderLayout5);
        jPanel3.setBorder(titledBorder6);
        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);
        mPanel.add(areaPanel, BorderLayout.CENTER);
        areaPanel.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.NORTH);
        jPanel2.add(extField, BorderLayout.CENTER);
        jPanel2.add(jLabel1, BorderLayout.SOUTH);
        mPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        dialogTitlePanel.add(header, null);
        this.getContentPane().add(mPanel, BorderLayout.CENTER);
        jPanel4.add(descField, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.SOUTH);
        areaPanel.add(appPanel, BorderLayout.SOUTH);
        areaPanel.add(jPanel5, BorderLayout.NORTH);
        jPanel3.add(setIconB, BorderLayout.EAST);
        jPanel3.add(iconLabel, BorderLayout.CENTER);
        jPanel1.add(jPanel4, BorderLayout.CENTER);
    }

    private void cancelB_actionPerformed() {
        this.dispose();
    }

    private void okB_actionPerformed() {
        CANCELLED = false;
        this.dispose();
    }

    private void extField_caretUpdate() {
        checkOkEnabled();
    }

    private void descField_caretUpdate() {
    }

    private void checkOkEnabled() {
        okB.setEnabled((extField.getText().length() > 0));
    }

    private void setIconB_actionPerformed() {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.lookInLabelText", Local.getString("Look in:"));
        UIManager.put("FileChooser.upFolderToolTipText", Local.getString("Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", Local.getString("Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", Local.getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Local.getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", Local.getString("File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString("Files of Type:"));
        UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
        UIManager.put("FileChooser.openButtonToolTipText", Local.getString("Open selected file"));
        UIManager.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString("Cancel"));
        UIManager.put("FileChooser.acceptAllFileFilterText", Local.getString("All Files") + " (*.*)");

        File root = new File(System.getProperty("user.home"));
        FileSystemView fsv = new SingleRootFileSystemView(root);
        JFileChooser chooser = new JFileChooser(fsv);
        chooser.setPreferredSize(new Dimension(550, 375));
        chooser.setFileHidingEnabled(true);
        chooser.setDialogTitle(Local.getString("Choose icon file"));
        //chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setCurrentDirectory(
                new File(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/mimetypes").getPath()));
        /*if (System.getProperty("os.name").startsWith("Win")) {
            chooser.setFileFilter(new AllFilesFilter(AllFilesFilter.ICO));
            chooser.setCurrentDirectory(new File("C:\\Program Files"));
        }
        else */
        chooser.addChoosableFileFilter(new net.sf.memoranda.ui.htmleditor.filechooser.ImageFilter());
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                iconLabel.setIcon(new ImageIcon(chooser.getSelectedFile().getPath()));
            } catch (Exception ex) {
                //ex.printStackTrace();
            } finally {
                iconPath = chooser.getSelectedFile().getPath();
            }

        }
    }

}