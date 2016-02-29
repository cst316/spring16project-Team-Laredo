package net.sf.memoranda.ui;

import net.sf.memoranda.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

/*$Id: PreferencesDialog.java,v 1.16 2006/06/28 22:58:31 alexeya Exp $*/
class PreferencesDialog extends JDialog {
    private final JPanel topPanel = new JPanel(new BorderLayout());

    private final JTabbedPane tabbedPanel = new JTabbedPane();

    private final JPanel GeneralPanel = new JPanel(new GridBagLayout());

    private final JCheckBox askConfirmChB = new JCheckBox();

    private final JLabel jLabel3 = new JLabel();

    private final ButtonGroup lfGroup = new ButtonGroup();

    private final JRadioButton lfSystemRB = new JRadioButton();

    private final JRadioButton lfJavaRB = new JRadioButton();

    private final JRadioButton lfCustomRB = new JRadioButton();

    private final JLabel classNameLabel = new JLabel();

    private final JTextField lfClassName = new JTextField();

    private final JLabel jLabel4 = new JLabel();

    private final JCheckBox startMinimizedChB = new JCheckBox();

    private final JCheckBox enSplashChB = new JCheckBox();

    private final JCheckBox enL10nChB = new JCheckBox();

    private final JCheckBox firstdow = new JCheckBox();

    private final JPanel resourcePanel = new JPanel(new BorderLayout());

    private final ResourceTypePanel resourceTypePanel = new ResourceTypePanel();

    private final JPanel rsBottomPanel = new JPanel(new GridBagLayout());

    private final JButton okB = new JButton();

    private final JButton cancelB = new JButton();

    private final JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

    private final JLabel jLabel5 = new JLabel();

    private final JTextField browserPath = new JTextField();

    private final JButton browseB = new JButton();

    private final JLabel lblExit = new JLabel();

    private final JPanel soundPanel = new JPanel();

    private final JCheckBox enableSoundCB = new JCheckBox();

    private final BorderLayout borderLayout1 = new BorderLayout();

    private final ButtonGroup soundGroup = new ButtonGroup();

    private final JPanel jPanel2 = new JPanel();

    private final JButton soundFileBrowseB = new JButton();

    private final GridLayout gridLayout1 = new GridLayout();

    private final JPanel jPanel1 = new JPanel();

    private final JRadioButton soundBeepRB = new JRadioButton();

    private final JLabel jLabel6 = new JLabel();

    private final JTextField soundFile = new JTextField();

    private final JRadioButton soundDefaultRB = new JRadioButton();

    private final BorderLayout borderLayout3 = new BorderLayout();

    private final JPanel jPanel3 = new JPanel();

    private final JRadioButton soundCustomRB = new JRadioButton();

    private final BorderLayout borderLayout2 = new BorderLayout();

    private final JPanel editorConfigPanel = new JPanel(new BorderLayout());
    private final JPanel econfPanel = new JPanel(new GridLayout(5, 2));
    private final Vector fontnames = getFontNames();
    private final JComboBox normalFontCB = new JComboBox(fontnames);
    private final JComboBox headerFontCB = new JComboBox(fontnames);
    private final JComboBox monoFontCB = new JComboBox(fontnames);
    private final JSpinner baseFontSize = new JSpinner();
    private final JCheckBox antialiasChB = new JCheckBox();
    private final JLabel normalFontLabel = new JLabel();
    private final JLabel headerFontLabel = new JLabel();
    private final JLabel monoFontLabel = new JLabel();
    private final JLabel baseFontSizeLabel = new JLabel();

    public PreferencesDialog(Frame frame) {
        super(frame, Local.getString("Preferences"), true);
        try {
            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    public PreferencesDialog() {
        this(null);
    }

    private void jbInit() {
        TitledBorder titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(
                Color.white, new Color(156, 156, 158)), Local
                .getString("Sound"));
        this.setResizable(false);
        // Build Tab1
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 15);
        gbc.anchor = GridBagConstraints.EAST;
        enableSoundCB.setText(Local.getString("Enable sound notifications"));
        enableSoundCB.addActionListener(e -> enableSoundCB_actionPerformed());
        soundPanel.setLayout(borderLayout1);
        soundFileBrowseB.setText(Local.getString("Browse"));
        soundFileBrowseB.addActionListener(e -> soundFileBrowseB_actionPerformed());
        gridLayout1.setRows(4);
        jPanel1.setBorder(titledBorder1);
        jPanel1.setLayout(gridLayout1);
        soundBeepRB.setText(Local.getString("System beep"));
        soundBeepRB.addActionListener(e -> soundBeepRB_actionPerformed());
        jLabel6.setText(Local.getString("Sound file") + ":");
        soundDefaultRB.setText(Local.getString("Default"));
        soundDefaultRB.addActionListener(e -> soundDefaultRB_actionPerformed());
        jPanel3.setLayout(borderLayout3);
        soundCustomRB.setText(Local.getString("Custom"));
        soundCustomRB.addActionListener(e -> soundCustomRB_actionPerformed());
        jPanel2.setLayout(borderLayout2);
        soundPanel.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(soundDefaultRB, null);
        jPanel1.add(soundBeepRB, null);
        jPanel1.add(soundCustomRB, null);
        this.soundGroup.add(soundDefaultRB);
        this.soundGroup.add(soundBeepRB);
        this.soundGroup.add(soundCustomRB);
        jPanel1.add(jPanel3, null);
        jPanel3.add(soundFile, BorderLayout.CENTER);
        jPanel3.add(soundFileBrowseB, BorderLayout.EAST);
        jPanel3.add(jLabel6, BorderLayout.WEST);

        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText(Local.getString("Look and feel:"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(2, 10, 0, 15);
        gbc.anchor = GridBagConstraints.EAST;
        GeneralPanel.add(jLabel3, gbc);


        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(2, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        lfSystemRB.setText("System");
        GeneralPanel.add(lfSystemRB, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(2, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        lfJavaRB.setText("Java");
        GeneralPanel.add(lfJavaRB, gbc);
        lfGroup.add(lfCustomRB);
        lfCustomRB.setText(Local.getString("Custom"));
        lfCustomRB.addActionListener(e -> lfCustomRB_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets = new Insets(2, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        GeneralPanel.add(lfCustomRB, gbc);
        classNameLabel.setEnabled(false);
        classNameLabel.setText(Local.getString("L&F class name:"));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.insets = new Insets(2, 20, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        GeneralPanel.add(classNameLabel, gbc);
        lfClassName.setEnabled(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.insets = new Insets(7, 20, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        GeneralPanel.add(lfClassName, gbc);
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setText(Local.getString("Startup:"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.insets = new Insets(2, 10, 0, 15);
        gbc.anchor = GridBagConstraints.EAST;
        GeneralPanel.add(jLabel4, gbc);


        startMinimizedChB.setText(Local.getString("Start minimized"));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.insets = new Insets(2, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        GeneralPanel.add(startMinimizedChB, gbc);
        enSplashChB.setText(Local.getString("Show splash screen"));
        enSplashChB.addActionListener(e -> enSplashChB_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.insets = new Insets(2, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        GeneralPanel.add(enSplashChB, gbc);
        enL10nChB.setText(Local.getString("Enable localization"));
        enL10nChB.addActionListener(e -> enL10nChB_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(2, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        GeneralPanel.add(enL10nChB, gbc);
        firstdow.setText(Local.getString("First day of week - Monday"));
        firstdow.addActionListener(e -> {
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.insets = new Insets(2, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        GeneralPanel.add(firstdow, gbc);
        lblExit.setHorizontalAlignment(SwingConstants.RIGHT);
        lblExit.setText(Local.getString("Exit") + ":");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.insets = new Insets(2, 10, 10, 15);
        gbc.anchor = GridBagConstraints.EAST;
        GeneralPanel.add(lblExit, gbc);
        askConfirmChB.setSelected(true);
        askConfirmChB.setText(Local.getString("Ask confirmation"));
        askConfirmChB.addActionListener(e -> askConfirmChB_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 15;
        gbc.insets = new Insets(2, 0, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        GeneralPanel.add(askConfirmChB, gbc);

        // Build Tab2
        Border rstPanelBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        resourceTypePanel.setBorder(rstPanelBorder);
        resourcePanel.add(resourceTypePanel, BorderLayout.CENTER);
        TitledBorder rsbpBorder = new TitledBorder(BorderFactory.createEmptyBorder(5, 5, 5,
                5), Local.getString("Web browser executable"));
        rsBottomPanel.setBorder(rsbpBorder);
        jLabel5.setText(Local.getString("Path") + ":");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.anchor = GridBagConstraints.WEST;
        rsBottomPanel.add(jLabel5, gbc);
        browserPath.setPreferredSize(new Dimension(250, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rsBottomPanel.add(browserPath, gbc);
        browseB.setText(Local.getString("Browse"));
        browseB.setPreferredSize(new Dimension(110, 25));
        browseB.addActionListener(e -> browseB_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        // gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        rsBottomPanel.add(browseB, gbc);

        resourcePanel.add(rsBottomPanel, BorderLayout.SOUTH);

        // Build editorConfigPanel
        normalFontLabel.setText(Local.getString("Normal text font"));
        normalFontLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        headerFontLabel.setText(Local.getString("Header font"));
        headerFontLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        monoFontLabel.setText(Local.getString("Monospaced font"));
        monoFontLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        baseFontSizeLabel.setText(Local.getString("Base font size"));
        baseFontSizeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        antialiasChB.setText(Local.getString("Antialias text"));
        JPanel bfsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bfsPanel.add(baseFontSize);
        econfPanel.add(normalFontLabel);
        econfPanel.add(normalFontCB);
        econfPanel.add(headerFontLabel);
        econfPanel.add(headerFontCB);
        econfPanel.add(monoFontLabel);
        econfPanel.add(monoFontCB);
        econfPanel.add(baseFontSizeLabel);
        econfPanel.add(bfsPanel);
        econfPanel.add(antialiasChB);
        econfPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        ((GridLayout) econfPanel.getLayout()).setHgap(10);
        ((GridLayout) econfPanel.getLayout()).setVgap(5);
        editorConfigPanel.add(econfPanel, BorderLayout.NORTH);
        // Build TabbedPanel
        tabbedPanel.add(GeneralPanel, Local.getString("General"));
        tabbedPanel.add(resourcePanel, Local.getString("Resource types"));
        tabbedPanel.add(soundPanel, Local.getString("Sound"));
        tabbedPanel.add(editorConfigPanel, Local.getString("Editor"));

        // Build TopPanel
        topPanel.add(tabbedPanel, BorderLayout.CENTER);

        // Build BottomPanel
        okB.setMaximumSize(new Dimension(100, 25));
        okB.setPreferredSize(new Dimension(100, 25));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(e -> okB_actionPerformed());
        this.getRootPane().setDefaultButton(okB);
        bottomPanel.add(okB);
        cancelB.setMaximumSize(new Dimension(100, 25));
        cancelB.setPreferredSize(new Dimension(100, 25));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        bottomPanel.add(cancelB);

        // Build Preferences-Dialog
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        soundPanel.add(enableSoundCB, BorderLayout.NORTH);

        // set all config-values
        setValues();

    }

    private void setValues() {
        enL10nChB.setSelected(!Configuration.get("DISABLE_L10N").toString()
                .equalsIgnoreCase("yes"));
        enSplashChB.setSelected(!Configuration.get("SHOW_SPLASH").toString()
                .equalsIgnoreCase("no"));
        startMinimizedChB.setSelected(Configuration.get("START_MINIMIZED")
                .toString().equalsIgnoreCase("yes"));
        firstdow.setSelected(Configuration.get("FIRST_DAY_OF_WEEK").toString()
                .equalsIgnoreCase("mon"));

        enableCustomLF(false);
        String lf = Configuration.get("LOOK_AND_FEEL").toString();
        if (lf.equalsIgnoreCase("system"))
            lfSystemRB.setSelected(true);
        else if (lf.equalsIgnoreCase("default"))
            lfJavaRB.setSelected(true);
        else if (lf.length() > 0) {
            lfCustomRB.setSelected(true);
            enableCustomLF(true);
            lfClassName.setText(lf);
        } else
            lfJavaRB.setSelected(true);

        askConfirmChB.setSelected(!Configuration.get("ASK_ON_EXIT").toString()
                .equalsIgnoreCase("no"));


        if (!System.getProperty("os.name").startsWith("Win"))
            this.browserPath.setText(MimeTypesList.getAppList()
                    .getBrowserExec());
        if (Configuration.get("NOTIFY_SOUND").equals("")) {
            Configuration.put("NOTIFY_SOUND", "DEFAULT");
        }

        boolean enableSnd = !Configuration.get("NOTIFY_SOUND").toString()
                .equalsIgnoreCase("DISABLED");
        enableSoundCB.setSelected(enableSnd);
        if (Configuration.get("NOTIFY_SOUND").toString().equalsIgnoreCase(
                "DEFAULT")
                || Configuration.get("NOTIFY_SOUND").toString()
                .equalsIgnoreCase("DISABLED")) {
            this.soundDefaultRB.setSelected(true);
            this.enableCustomSound(false);
        } else if (Configuration.get("NOTIFY_SOUND").toString()
                .equalsIgnoreCase("BEEP")) {
            this.soundBeepRB.setSelected(true);
            this.enableCustomSound(false);
        } else {
            System.out.println(Configuration.get("NOTIFY_SOUND").toString());
            this.soundCustomRB.setSelected(true);
            this.soundFile
                    .setText(Configuration.get("NOTIFY_SOUND").toString());
            this.enableCustomSound(true);
        }
        this.enableSound(enableSnd);

        antialiasChB.setSelected(Configuration.get("ANTIALIAS_TEXT")
                .toString().equalsIgnoreCase("yes"));
        if (Configuration.get("NORMAL_FONT").toString().length() > 0)
            normalFontCB.setSelectedItem(Configuration.get("NORMAL_FONT").toString());
        else
            normalFontCB.setSelectedItem("serif");
        if (Configuration.get("HEADER_FONT").toString().length() > 0)
            headerFontCB.setSelectedItem(Configuration.get("HEADER_FONT").toString());
        else
            headerFontCB.setSelectedItem("sans-serif");
        if (Configuration.get("MONO_FONT").toString().length() > 0)
            monoFontCB.setSelectedItem(Configuration.get("MONO_FONT").toString());
        else
            monoFontCB.setSelectedItem("monospaced");
        if (Configuration.get("BASE_FONT_SIZE").toString().length() > 0)
            baseFontSize.setValue(Integer.decode(Configuration.get("BASE_FONT_SIZE").toString()));
        else
            baseFontSize.setValue(16);
    }

    private void apply() {
        if (this.firstdow.isSelected())
            Configuration.put("FIRST_DAY_OF_WEEK", "mon");
        else
            Configuration.put("FIRST_DAY_OF_WEEK", "sun");

        if (this.enL10nChB.isSelected())
            Configuration.put("DISABLE_L10N", "no");
        else
            Configuration.put("DISABLE_L10N", "yes");

        if (this.enSplashChB.isSelected())
            Configuration.put("SHOW_SPLASH", "yes");
        else
            Configuration.put("SHOW_SPLASH", "no");

        if (this.startMinimizedChB.isSelected())
            Configuration.put("START_MINIMIZED", "yes");
        else
            Configuration.put("START_MINIMIZED", "no");

        if (this.askConfirmChB.isSelected())
            Configuration.put("ASK_ON_EXIT", "yes");
        else
            Configuration.put("ASK_ON_EXIT", "no");


        Configuration.put("ON_MINIMIZE", "normal");

        String lf = Configuration.get("LOOK_AND_FEEL").toString();
        String newlf = "";

        if (this.lfSystemRB.isSelected())
            newlf = "system";
        else if (this.lfJavaRB.isSelected())
            newlf = "default";
        else if (this.lfCustomRB.isSelected())
            newlf = this.lfClassName.getText();

        if (!lf.equalsIgnoreCase(newlf)) {
            Configuration.put("LOOK_AND_FEEL", newlf);
            try {
                if (Configuration.get("LOOK_AND_FEEL").equals("system"))
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                else if (Configuration.get("LOOK_AND_FEEL").equals("default"))
                    UIManager.setLookAndFeel(UIManager
                            .getCrossPlatformLookAndFeelClassName());
                else if (Configuration.get("LOOK_AND_FEEL").toString().length() > 0)
                    UIManager.setLookAndFeel(Configuration.get("LOOK_AND_FEEL")
                            .toString());

                SwingUtilities.updateComponentTreeUI(App.getFrame());

            } catch (Exception e) {
                Configuration.put("LOOK_AND_FEEL", lf);
                new ExceptionDialog(
                        e,
                        "Error when initializing a pluggable look-and-feel. Default LF will be used.",
                        "Make sure that specified look-and-feel library classes are on the CLASSPATH.");
            }
        }
        String brPath = this.browserPath.getText();
        if (new java.io.File(brPath).isFile()) {
            MimeTypesList.getAppList().setBrowserExec(brPath);
            CurrentStorage.get().storeMimeTypesList();
        }

        if (!this.enableSoundCB.isSelected())
            Configuration.put("NOTIFY_SOUND", "DISABLED");
        else if (this.soundDefaultRB.isSelected())
            Configuration.put("NOTIFY_SOUND", "DEFAULT");
        else if (this.soundBeepRB.isSelected())
            Configuration.put("NOTIFY_SOUND", "BEEP");
        else if ((this.soundCustomRB.isSelected())
                && (this.soundFile.getText().trim().length() > 0))
            Configuration.put("NOTIFY_SOUND", this.soundFile.getText().trim());

        if (antialiasChB.isSelected())
            Configuration.put("ANTIALIAS_TEXT", "yes");
        else
            Configuration.put("ANTIALIAS_TEXT", "no");

        Configuration.put("NORMAL_FONT", normalFontCB.getSelectedItem());
        Configuration.put("HEADER_FONT", headerFontCB.getSelectedItem());
        Configuration.put("MONO_FONT", monoFontCB.getSelectedItem());
        Configuration.put("BASE_FONT_SIZE", baseFontSize.getValue());
        App.getFrame().workPanel.dailyItemsPanel.editorPanel.editor.editor.setAntiAlias(antialiasChB.isSelected());
        App.getFrame().workPanel.dailyItemsPanel.editorPanel.initCSS();
        App.getFrame().workPanel.dailyItemsPanel.editorPanel.editor.repaint();

        Configuration.saveConfig();

    }

    private void enableCustomLF(boolean is) {
        this.classNameLabel.setEnabled(is);
        this.lfClassName.setEnabled(is);
    }

    private void enableCustomSound(boolean is) {
        this.soundFile.setEnabled(is);
        this.soundFileBrowseB.setEnabled(is);
        this.jLabel6.setEnabled(is);
    }

    private void enableSound(boolean is) {
        this.soundDefaultRB.setEnabled(is);
        this.soundBeepRB.setEnabled(is);
        this.soundCustomRB.setEnabled(is);
        enableCustomSound(is);

        this.soundFileBrowseB.setEnabled(is && soundCustomRB.isSelected());
        this.soundFile.setEnabled(is && soundCustomRB.isSelected());
        this.jLabel6.setEnabled(is && soundCustomRB.isSelected());

    }

    private void okB_actionPerformed() {
        apply();
        this.dispose();
    }

    private void cancelB_actionPerformed() {
        this.dispose();
    }

    private void askConfirmChB_actionPerformed() {

    }

    void lfSystemRB_actionPerformed() {
        this.enableCustomLF(false);
    }

    void lfJavaRB_actionPerformed() {
        this.enableCustomLF(false);
    }

    private void lfCustomRB_actionPerformed() {
        this.enableCustomLF(true);
    }

    private void enSplashChB_actionPerformed() {

    }

    private void enL10nChB_actionPerformed() {

    }

    private void browseB_actionPerformed() {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.lookInLabelText", Local
                .getString("Look in:"));
        UIManager.put("FileChooser.upFolderToolTipText", Local
                .getString("Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", Local
                .getString("Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", Local
                .getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                .getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", Local
                .getString("File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", Local
                .getString("Files of Type:"));
        UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
        UIManager.put("FileChooser.openButtonToolTipText", Local
                .getString("Open selected file"));
        UIManager
                .put("FileChooser.cancelButtonText", Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", Local
                .getString("Cancel"));

        File root = new File(System.getProperty("user.home"));
        FileSystemView fsv = new SingleRootFileSystemView(root);
        JFileChooser chooser = new JFileChooser(fsv);
        chooser.setFileHidingEnabled(true);
        chooser.setDialogTitle(Local
                .getString("Select the web-browser executable"));
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setPreferredSize(new Dimension(550, 375));
        if (System.getProperty("os.name").startsWith("Win")) {
            chooser.setFileFilter(new AllFilesFilter(AllFilesFilter.EXE));
            chooser.setCurrentDirectory(new File("C:\\Program Files"));
        }
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            this.browserPath.setText(chooser.getSelectedFile().getPath());
    }

    private void enableSoundCB_actionPerformed() {
        enableSound(enableSoundCB.isSelected());
    }

    private void soundFileBrowseB_actionPerformed() {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.lookInLabelText", Local
                .getString("Look in:"));
        UIManager.put("FileChooser.upFolderToolTipText", Local
                .getString("Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", Local
                .getString("Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", Local
                .getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                .getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", Local
                .getString("File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", Local
                .getString("Files of Type:"));
        UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
        UIManager.put("FileChooser.openButtonToolTipText", Local
                .getString("Open selected file"));
        UIManager
                .put("FileChooser.cancelButtonText", Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", Local
                .getString("Cancel"));

        File root = new File(System.getProperty("user.home"));
        FileSystemView fsv = new SingleRootFileSystemView(root);
        JFileChooser chooser = new JFileChooser(fsv);
        chooser.setFileHidingEnabled(true);
        chooser.setDialogTitle(Local.getString("Select the sound file"));
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setPreferredSize(new Dimension(550, 375));
        chooser.setFileFilter(new AllFilesFilter(AllFilesFilter.WAV));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            this.soundFile.setText(chooser.getSelectedFile().getPath());
    }

    private void soundDefaultRB_actionPerformed() {
        this.enableCustomSound(false);
    }

    private void soundBeepRB_actionPerformed() {
        this.enableCustomSound(false);
    }

    private void soundCustomRB_actionPerformed() {
        this.enableCustomSound(true);
    }

    private Vector getFontNames() {
        GraphicsEnvironment gEnv =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        String envfonts[] = gEnv.getAvailableFontFamilyNames();
        Vector fonts = new Vector();
        fonts.add("serif");
        fonts.add("sans-serif");
        fonts.add("monospaced");
        Collections.addAll(fonts, envfonts);
        return fonts;
    }
}