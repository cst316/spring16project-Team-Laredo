package net.sf.memoranda.ui;

import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*$Id: ProjectDialog.java,v 1.26 2004/10/18 19:09:10 ivanrise Exp $*/
class ProjectDialog extends JDialog {
    public final JTextField prTitleField = new JTextField();
    public final JSpinner startDate = new JSpinner(new SpinnerDateModel());
    public final JCheckBox endDateChB = new JCheckBox();
    public final JSpinner endDate = new JSpinner(new SpinnerDateModel());
    final JButton edButton = new JButton();
    private final CalendarFrame endCalFrame = new CalendarFrame();
    private final CalendarFrame startCalFrame = new CalendarFrame();
    private final JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JLabel header = new JLabel();
    private final JPanel centerPanel = new JPanel(new GridBagLayout());
    private final JLabel titleLabel = new JLabel();
    private final JLabel sdLabel = new JLabel();
    private final JButton sdButton = new JButton();
    //public JCheckBox freezeChB = new JCheckBox();
    private final JPanel bottomPanel = new JPanel();
    private final JButton okButton = new JButton();
    private final JButton cancelButton = new JButton();
    public boolean CANCELLED = true;
    private boolean ignoreStartChanged = false;
    private boolean ignoreEndChanged = false;

    public ProjectDialog(String title) {
        super((Frame) null, title, true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    public static void newProject() {
        ProjectDialog dlg = new ProjectDialog(Local.getString("New project"));

        Dimension dlgSize = dlg.getSize();
        //dlg.setSize(dlgSize);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        String title = dlg.prTitleField.getText();
        CalendarDate startD = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        CalendarDate endD = null;
        if (dlg.endDateChB.isSelected())
            endD = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        Project prj = ProjectManager.createProject(title, startD, endD);
        /*if (dlg.freezeChB.isSelected())
            prj.freeze();*/
        CurrentStorage.get().storeProjectManager();
    }

    private void jbInit() {
        this.setResizable(false);
        getContentPane().setLayout(new GridBagLayout());
        topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
        topPanel.setBackground(Color.WHITE);
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Project"));
        //header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.ProjectDialog.class.getResource(
                "resources/icons/project48.png")));
        topPanel.add(header);

        centerPanel.setBorder(new EtchedBorder());
        titleLabel.setText(Local.getString("Title"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(5, 10, 5, 10);
        //gbc.anchor = GridBagConstraints.WEST;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        centerPanel.add(titleLabel, gbc);

        //prTitleField.setPreferredSize(new Dimension(270, 20));
        gbc = new GridBagConstraints();
        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 5, 0);
        //gbc.anchor = GridBagConstraints.EAST;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(prTitleField, gbc);

        sdLabel.setText(Local.getString("Start date"));
        sdLabel.setPreferredSize(new Dimension(70, 20));
        sdLabel.setMinimumSize(new Dimension(70, 20));
        sdLabel.setMaximumSize(new Dimension(70, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 10, 10);
        centerPanel.add(sdLabel, gbc);

        startDate.setPreferredSize(new Dimension(80, 20));
        startDate.setLocale(Local.getCurrentLocale());
        //Added by (jcscoobyrs) on 17-Nov-2003 at 14:24:43 PM
        //---------------------------------------------------
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT);
        startDate.setEditor(new JSpinner.DateEditor(startDate,
                sdf.toPattern()));
        //---------------------------------------------------
        startDate.addChangeListener(e -> {
            if (ignoreStartChanged) return;
            ignoreStartChanged = true;
            Date sd = (Date) startDate.getModel().getValue();
            if (endDate.isEnabled()) {
                Date ed = (Date) endDate.getModel().getValue();
                if (sd.after(ed)) {
                    startDate.getModel().setValue(ed);
                    sd = ed;
                }
            }
            startCalFrame.cal.set(new CalendarDate(sd));
            ignoreStartChanged = false;
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 5);
        centerPanel.add(startDate, gbc);

        sdButton.setMinimumSize(new Dimension(20, 20));
        sdButton.setPreferredSize(new Dimension(20, 20));
        sdButton.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/calendar.png")));
        sdButton.addActionListener(e -> sdButton_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 25);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(sdButton, gbc);

        endDateChB.setForeground(Color.gray);
        endDateChB.setText(Local.getString("End date"));
        endDateChB.addActionListener(e -> endDateChB_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(endDateChB, gbc);

        endDate.setEnabled(false);
        endDate.setPreferredSize(new Dimension(80, 20));
        endDate.setLocale(Local.getCurrentLocale());
        //Added by (jcscoobyrs) on 17-Nov-2003 at 14:24:43 PM
        //---------------------------------------------------
        endDate.setEditor(new JSpinner.DateEditor(endDate,
                sdf.toPattern()));
        //---------------------------------------------------
        endDate.addChangeListener(e -> {
            if (ignoreEndChanged) return;
            ignoreEndChanged = true;
            Date sd = (Date) startDate.getModel().getValue();
            Date ed = (Date) endDate.getModel().getValue();
            if (sd.after(ed)) {
                endDate.getModel().setValue(sd);
                ed = sd;
            }
            endCalFrame.cal.set(new CalendarDate(ed));
            ignoreEndChanged = false;
        });
        //((JSpinner.DateEditor) endDate.getEditor()).setLocale(Local.getCurrentLocale());
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(endDate, gbc);

        edButton.setEnabled(false);
        edButton.setMinimumSize(new Dimension(20, 20));
        edButton.setMaximumSize(new Dimension(20, 20));
        edButton.setPreferredSize(new Dimension(20, 20));
        edButton.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/calendar.png")));
        edButton.addActionListener(e -> edButton_actionPerformed());
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(edButton, gbc);

        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        okButton.setMaximumSize(new Dimension(100, 25));
        okButton.setMinimumSize(new Dimension(100, 25));
        okButton.setPreferredSize(new Dimension(100, 25));
        okButton.setText(Local.getString("Ok"));
        okButton.addActionListener(e -> okButton_actionPerformed());
        this.getRootPane().setDefaultButton(okButton);
        cancelButton.setMaximumSize(new Dimension(100, 25));
        cancelButton.setMinimumSize(new Dimension(100, 25));
        cancelButton.setPreferredSize(new Dimension(100, 25));
        cancelButton.setText(Local.getString("Cancel"));
        cancelButton.addActionListener(e -> cancelButton_actionPerformed());
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(topPanel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        getContentPane().add(centerPanel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(bottomPanel, gbc);

        startCalFrame.cal.addSelectionListener(e -> {
            if (ignoreStartChanged)
                return;
            startDate.getModel().setValue(startCalFrame.cal.get().getCalendar().getTime());
        });
        endCalFrame.cal.addSelectionListener(e -> {
            if (ignoreEndChanged)
                return;
            endDate.getModel().setValue(endCalFrame.cal.get().getCalendar().getTime());
        });
    }

    private void okButton_actionPerformed() {
        CANCELLED = false;
        this.dispose();
    }

    private void cancelButton_actionPerformed() {
        this.dispose();
    }

    private void endDateChB_actionPerformed() {
        endDate.setEnabled(endDateChB.isSelected());
        edButton.setEnabled(endDateChB.isSelected());
        if (endDateChB.isSelected()) {
            endDateChB.setForeground(Color.BLACK);
            endDate.getModel().setValue(startDate.getModel().getValue());
        } else endDateChB.setForeground(Color.GRAY);
    }

    private void sdButton_actionPerformed() {
        //startCalFrame.setLocation(sdButton.getLocation());
        startCalFrame.setLocation(0, 0);
        startCalFrame.setSize((this.getContentPane().getWidth() / 2),
                this.getContentPane().getHeight());
        this.getLayeredPane().add(startCalFrame);
        startCalFrame.setTitle(Local.getString("Start date"));
        startCalFrame.show();
    }

    private void edButton_actionPerformed() {
        endCalFrame.setLocation((this.getContentPane().getWidth() / 2), 0);
        endCalFrame.setSize((this.getContentPane().getWidth() / 2),
                this.getContentPane().getHeight());
        this.getLayeredPane().add(endCalFrame);
        endCalFrame.setTitle(Local.getString("End date"));
        endCalFrame.show();
    }
}
