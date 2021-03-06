package net.sf.memoranda.ui;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*$Id: EventDialog.java,v 1.28 2005/02/19 10:06:25 rawsushi Exp $*/
class EventDialog extends JDialog implements WindowListener {
    public final JSpinner timeSpin = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
    public final JSpinner durationSpin = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
    public final JTextField textField = new JTextField();
    public final JRadioButton noRepeatRB = new JRadioButton();
    public final JRadioButton dailyRepeatRB = new JRadioButton();
    public final JSpinner daySpin = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
    public final JSpinner startDate = new JSpinner(new SpinnerDateModel());
    public final JRadioButton weeklyRepeatRB = new JRadioButton();
    public final JComboBox<String> weekdaysCB = new JComboBox<>(Local.getWeekdayNames());
    public final JCheckBox enableEndDateCB = new JCheckBox();
    public final JCheckBox workingDaysOnlyCB = new JCheckBox();
    public final JSpinner endDate = new JSpinner(new SpinnerDateModel());
    public final JRadioButton monthlyRepeatRB = new JRadioButton();
    public final JSpinner dayOfMonthSpin = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
    public final JRadioButton yearlyRepeatRB = new JRadioButton();
    private final JLabel header = new JLabel();
    private final boolean ignoreStartChanged = false;
    private final JPanel topPanel = new JPanel(new BorderLayout());
    private final JPanel bottomPanel = new JPanel(new BorderLayout());
    private final JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel eventPanel = new JPanel(new GridBagLayout());
    private final JLabel lblTime = new JLabel();
    private final JLabel lblDuration = new JLabel();
    private final JLabel lblText = new JLabel();
    private final JPanel repeatPanel = new JPanel(new GridBagLayout());
    private final JLabel lblDays = new JLabel();
    private final JLabel lblSince = new JLabel();
    private final JButton setStartDateB = new JButton();
    private final JButton setEndDateB = new JButton();
    private final JLabel lblDoM = new JLabel();
    private final ButtonGroup repeatRBGroup = new ButtonGroup();
    private final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    private final JButton okB = new JButton();
    private final JButton cancelB = new JButton();
    private final CalendarFrame endCalFrame = new CalendarFrame();
    private final CalendarFrame startCalFrame = new CalendarFrame();
    private final ChangeListener dateListener;
    public boolean CANCELLED = false;
    private boolean ignoreEndChanged = false;
    private Date eventDate;

    public EventDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }

        this.dateListener = e -> {
            if (ignoreEndChanged)
                return;
            ignoreEndChanged = true;
            Date sd = (Date) startDate.getModel().getValue();
            Date ed = (Date) endDate.getModel().getValue();
            if (sd.after(ed)) {
                endDate.getModel().setValue(sd);
                ed = sd;
            }
            endCalFrame.cal.set(new CalendarDate(ed));
            ignoreEndChanged = false;
        };

        super.addWindowListener(this);
    }

    private void jbInit() {
        this.setResizable(false);
        // Build headerPanel
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Event"));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.EventDialog.class.getResource(
                "resources/icons/event48.png")));
        headerPanel.add(header);

        // Build eventPanel
        lblTime.setText(Local.getString("Start Time"));
        lblTime.setMinimumSize(new Dimension(60, 24));

        lblDuration.setText(Local.getString("End Time"));
        lblDuration.setMinimumSize(new Dimension(60, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblTime, gbc);

        timeSpin.setPreferredSize(new Dimension(60, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(timeSpin, "HH:mm");
        timeSpin.setEditor(startEditor);
        eventPanel.add(timeSpin, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblDuration, gbc);

        durationSpin.setPreferredSize(new Dimension(60, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        JSpinner.DateEditor durationEditor = new JSpinner.DateEditor(durationSpin, "HH:mm");
        durationSpin.setEditor(durationEditor);
        eventPanel.add(durationSpin, gbc);

        lblText.setText(Local.getString("Text"));
        lblText.setMinimumSize(new Dimension(120, 24));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblText, gbc);
        textField.setMinimumSize(new Dimension(375, 24));
        textField.setPreferredSize(new Dimension(375, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eventPanel.add(textField, gbc);

        // Build RepeatPanel
        TitledBorder repeatBorder = new TitledBorder(BorderFactory.createLineBorder(
                Color.gray, 1), Local.getString("Repeat"));
        repeatPanel.setBorder(repeatBorder);
        noRepeatRB.setMaximumSize(new Dimension(80, 35));
        noRepeatRB.setSelected(true);
        noRepeatRB.setText(Local.getString("No repeat"));
        noRepeatRB.addActionListener(this::noRepeatRB_actionPerformed);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        repeatPanel.add(noRepeatRB, gbc);
        dailyRepeatRB.setActionCommand("daily");
        dailyRepeatRB.setText(Local.getString("Every"));
        dailyRepeatRB.addActionListener(this::dailyRepeatRB_actionPerformed);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(dailyRepeatRB, gbc);
        daySpin.setPreferredSize(new Dimension(50, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(daySpin, gbc);
        lblDays.setText(Local.getString("day(s)"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 40);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(lblDays, gbc);
        lblSince.setText(Local.getString("Since"));
        lblSince.setMinimumSize(new Dimension(70, 16));
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        repeatPanel.add(lblSince, gbc);
        startDate.addChangeListener(this.dateListener);
        startDate.setPreferredSize(new Dimension(80, 24));

        //Added by (jcscoobyrs) on 12-Nov-2003 at 15:34:27 PM
        //---------------------------------------------------
        SimpleDateFormat sdf;
        sdf = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT);
        startDate.setEditor(new JSpinner.DateEditor(startDate,
                sdf.toPattern()));
        //---------------------------------------------------
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(startDate, gbc);
        setStartDateB.addActionListener(this::setStartDateB_actionPerformed);
        setStartDateB.setIcon(
                new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/calendar.png")));
        setStartDateB.setText("");
        setStartDateB.setPreferredSize(new Dimension(24, 24));

        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(setStartDateB, gbc);
        weeklyRepeatRB.setActionCommand("weekly");
        weeklyRepeatRB.setText(Local.getString("Every"));
        weeklyRepeatRB.addActionListener(this::weeklyRepeatRB_actionPerformed);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(weeklyRepeatRB, gbc);
        weekdaysCB.setPreferredSize(new Dimension(100, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 0, 5, 40);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(weekdaysCB, gbc);
        enableEndDateCB.setHorizontalAlignment(SwingConstants.RIGHT);
        enableEndDateCB.setText(Local.getString("Till"));
        enableEndDateCB.addActionListener(this::enableEndDateCB_actionPerformed);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        repeatPanel.add(enableEndDateCB, gbc);
        endDate.setPreferredSize(new Dimension(80, 24));
        //Added by (jcscoobyrs) on 12-Nov-2003 at 15:34:27 PM
        //---------------------------------------------------
        endDate.setEditor(new JSpinner.DateEditor(endDate, sdf.toPattern()));
        //---------------------------------------------------
        endDate.addChangeListener(this.dateListener);
        // working days
        workingDaysOnlyCB.setText(Local.getString("Working days only"));
        workingDaysOnlyCB.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        repeatPanel.add(workingDaysOnlyCB, gbc);
        // -------------------------------------
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(endDate, gbc);
        setEndDateB.setMinimumSize(new Dimension(24, 24));
        setEndDateB.setPreferredSize(new Dimension(24, 24));
        setEndDateB.setText("");
        setEndDateB.setIcon(
                new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/calendar.png")));
        setEndDateB.addActionListener(this::setEndDateB_actionPerformed);
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(setEndDateB, gbc);
        monthlyRepeatRB.setActionCommand("daily");
        monthlyRepeatRB.setText(Local.getString("Every"));
        monthlyRepeatRB.addActionListener(this::monthlyRepeatRB_actionPerformed);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(monthlyRepeatRB, gbc);
        dayOfMonthSpin.setPreferredSize(new Dimension(50, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(dayOfMonthSpin, gbc);
        lblDoM.setText(Local.getString("day of month"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(lblDoM, gbc);
        yearlyRepeatRB.setActionCommand("yearly");
        yearlyRepeatRB.setText(Local.getString("Yearly"));
        yearlyRepeatRB.addActionListener(this::yearlyRepeatRB_actionPerformed);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(yearlyRepeatRB, gbc);

        repeatRBGroup.add(noRepeatRB);
        repeatRBGroup.add(dailyRepeatRB);
        repeatRBGroup.add(weeklyRepeatRB);
        repeatRBGroup.add(monthlyRepeatRB);
        repeatRBGroup.add(yearlyRepeatRB);

        // Build ButtonsPanel
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(this::okB_actionPerformed);
        this.getRootPane().setDefaultButton(okB);
        cancelB.addActionListener(this::cancelB_actionPerformed);
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);

        // Finally build the Dialog
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(eventPanel, BorderLayout.SOUTH);
        bottomPanel.add(repeatPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Do final things...
        startCalFrame.cal.addSelectionListener(e -> {
            if (ignoreStartChanged) return;
            startDate.getModel().setValue(startCalFrame.cal.get().getCalendar().getTime());
        });
        endCalFrame.cal.addSelectionListener(e -> {
            if (ignoreEndChanged)
                return;
            endDate.getModel().setValue(endCalFrame.cal.get().getCalendar().getTime());
        });

        disableElements();
        enableEndDateCB_actionPerformed(null);
    }

    private void disableElements() {
        dayOfMonthSpin.setEnabled(false);
        daySpin.setEnabled(false);
        weekdaysCB.setEnabled(false);
        startDate.setEnabled(false);
        setStartDateB.setEnabled(false);
        lblSince.setEnabled(false);
        endDate.setEnabled(false);
        setEndDateB.setEnabled(false);
        enableEndDateCB.setEnabled(false);
        enableEndDateCB.setSelected(false);
        workingDaysOnlyCB.setEnabled(false);
        workingDaysOnlyCB.setSelected(false);
    }

    public void yearlyRepeatRB_actionPerformed(ActionEvent e) {
        anyActionPerformed();
    }

    public void monthlyRepeatRB_actionPerformed(ActionEvent e) {
        anyActionPerformed();
        dayOfMonthSpin.setEnabled(true);
    }

    public void dailyRepeatRB_actionPerformed(ActionEvent e) {
        anyActionPerformed();
        daySpin.setEnabled(true);
    }

    public void weeklyRepeatRB_actionPerformed(ActionEvent e) {
        anyActionPerformed();
        weekdaysCB.setEnabled(true);
        workingDaysOnlyCB.setEnabled(false);
    }

    public void noRepeatRB_actionPerformed(ActionEvent e) {
        disableElements();
    }

    void setStartDateB_actionPerformed(ActionEvent e) {
        startCalFrame.setSize(200, 190);
        startCalFrame.setTitle(Local.getString("Start date"));
        this.getLayeredPane().add(startCalFrame);
        startCalFrame.show();
    }

    void okB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        CANCELLED = true;
        this.dispose();
    }

    private void anyActionPerformed() {
        disableElements();
        startDate.setEnabled(true);
        setStartDateB.setEnabled(true);
        lblSince.setEnabled(true);
        enableEndDateCB.setEnabled(true);
        workingDaysOnlyCB.setEnabled(true);
        startDate.getModel().setValue(
                startCalFrame.cal.get().getCalendar().getTime());
    }

    public void yearlyRepeatRB_actionPerformed() {
        anyActionPerformed();
    }

    public void monthlyRepeatRB_actionPerformed() {
        anyActionPerformed();
        dayOfMonthSpin.setEnabled(true);
    }

    public void dailyRepeatRB_actionPerformed() {
        anyActionPerformed();
        daySpin.setEnabled(true);
    }

    public void weeklyRepeatRB_actionPerformed() {
        anyActionPerformed();
        weekdaysCB.setEnabled(true);
        workingDaysOnlyCB.setEnabled(false);
    }

    private void setEndDateB_actionPerformed(ActionEvent e) {
        endCalFrame.setSize(200, 190);
        endCalFrame.setTitle(Local.getString("End date"));
        this.getLayeredPane().add(endCalFrame);
        endCalFrame.show();
    }

    public void enableEndDateCB_actionPerformed(ActionEvent e) {
        endDate.setEnabled(enableEndDateCB.isSelected());
        setEndDateB.setEnabled(enableEndDateCB.isSelected());
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        CANCELLED = true;
        this.dispose();
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date d) {
        eventDate = d;
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

}