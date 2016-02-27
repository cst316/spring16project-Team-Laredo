package net.sf.memoranda.ui;

import net.sf.memoranda.Stopwatch;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.concurrent.TimeUnit;

//import javax.swing.border.BevelBorder;

class StopwatchDialog extends JDialog {
    private final JTextField txtTime = new JTextField();
    private final JButton btnStartStop = new JButton("");
    private final JPanel panDialogTitle = new JPanel();
    private Stopwatch stopwatch = new Stopwatch();
    private final Timer updater = new Timer(500, e -> txtTime.setText(stopwatch.getTimeString()));
    private boolean isRunning = false;

    public StopwatchDialog() {
        getContentPane().setMaximumSize(new Dimension(315, 155));
        getContentPane().setMinimumSize(new Dimension(315, 155));
        setMaximumSize(new Dimension(315, 155));
        setMinimumSize(new Dimension(315, 155));
        setSize(new Dimension(315, 155));
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(mainPanel, BorderLayout.NORTH);
        mainPanel.setLayout(new BorderLayout(0, 0));

        Panel txtPanel = new Panel();
        mainPanel.add(txtPanel, BorderLayout.NORTH);
        txtTime.setEditable(false);

        txtPanel.add(txtTime);
        txtTime.setMinimumSize(new Dimension(400, 40));
        txtTime.setMaximumSize(new Dimension(400, 40));
        txtTime.setPreferredSize(new Dimension(400, 40));
        txtTime.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtTime.setHorizontalAlignment(SwingConstants.CENTER);
        txtTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
        txtTime.setText(stopwatch.getTimeString());
        txtTime.setColumns(10);

        Panel comboPanel = new Panel();
        mainPanel.add(comboPanel, BorderLayout.CENTER);
        comboPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"Event1 - Task1", "Event1 - Task2", "Event2 - Task1", "Event3 - Task1", "Event3 - Task2", "Event3 - Task3", "Event3 - Task4"}));
        comboBox.setPreferredSize(new Dimension(200, 20));
        comboBox.setMaximumSize(new Dimension(200, 20));
        comboBox.setMinimumSize(new Dimension(200, 20));
        comboPanel.add(comboBox);
        comboBox.setBorder(null);

        Panel buttonPanel = new Panel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton btnRestart = new JButton("");
        btnRestart.setToolTipText("Restart");
        btnRestart.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/restart.png")));
        btnRestart.setMargin(new Insets(2, 4, 2, 4));
        btnRestart.addActionListener(e -> btnRestart_actionPerformed());
        buttonPanel.add(btnRestart);

        btnStartStop.setToolTipText("Start");
        btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/play.png")));
        btnStartStop.setMargin(new Insets(2, 4, 2, 4));
        btnStartStop.addActionListener(e -> btnStartStop_actionPerformed());
        buttonPanel.add(btnStartStop);

        JButton btnAdd = new JButton("");
        btnAdd.setToolTipText("Add");
        btnAdd.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/add.png")));
        btnAdd.setMargin(new Insets(2, 4, 2, 4));
        buttonPanel.add(btnAdd);
        btnAdd.addActionListener(e -> btnAdd_actionPerformed());

        JButton btnRemove = new JButton("");
        btnRemove.setToolTipText("Remove");
        btnRemove.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/remove.png")));
        btnRemove.setMargin(new Insets(2, 4, 2, 4));
        buttonPanel.add(btnRemove);
        btnRemove.addActionListener(e -> btnRemove_actionPerformed());
    }

    private StopwatchDialog(Frame frame, String title) {
        super(frame, title, true);
        setTitle("");
        try {
            jbInit();

            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    private void jbInit() {
        setPreferredSize(new Dimension(450, 475));
        setResizable(false);
        getContentPane().setLayout(new BorderLayout(0, 0));
        panDialogTitle.setBorder(new EmptyBorder(0, 5, 0, 5));
        panDialogTitle.setBackground(Color.WHITE);
        getContentPane().add(panDialogTitle, BorderLayout.NORTH);
        panDialogTitle.setLayout(new BorderLayout(0, 0));
    }

    private void btnStartStop_actionPerformed() {
        if (isRunning) {
            stopwatch.stopStopwatch();
            btnStartStop.setToolTipText("Start");
            btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/play.png")));
            isRunning = false;
            updater.restart();
        } else {
            stopwatch.startStopwatch();
            btnStartStop.setToolTipText("Stop");
            btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/pause.png")));
            isRunning = true;
            updater.start();
        }
    }

    private void btnRestart_actionPerformed() {
        if (isRunning) {
            btnStartStop.setToolTipText("Start");
            btnStartStop.setIcon(new ImageIcon(StopwatchDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/play.png")));
            isRunning = false;
            updater.restart();
        }
        stopwatch = new Stopwatch();
        txtTime.setText(stopwatch.getTimeString());
    }

    private void btnAdd_actionPerformed() {
        AddRemoveDialog dlg = new AddRemoveDialog();
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        dlg.btnConfirm.addActionListener(e -> {
            if (!dlg.txtValue.getText().equals("")) {
                int time = Integer.parseInt(dlg.txtValue.getText());
                TimeUnit u = TimeUnit.SECONDS;
                switch (dlg.comboBox.getSelectedIndex()) {
                    case 0:
                        u = TimeUnit.SECONDS;
                        break;
                    case 1:
                        u = TimeUnit.MINUTES;
                        break;
                    case 2:
                        u = TimeUnit.HOURS;
                        break;
                }
                stopwatch.addTime(time, u);
                dlg.dispose();
                txtTime.setText(stopwatch.getTimeString());
            }
        });
    }

    private void btnRemove_actionPerformed() {
        AddRemoveDialog dlg = new AddRemoveDialog();
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        dlg.btnConfirm.addActionListener(e -> {
            if (!dlg.txtValue.getText().equals("")) {
                int time = Integer.parseInt(dlg.txtValue.getText());
                TimeUnit u = TimeUnit.SECONDS;
                switch (dlg.comboBox.getSelectedIndex()) {
                    case 0:
                        u = TimeUnit.SECONDS;
                        break;
                    case 1:
                        u = TimeUnit.MINUTES;
                        break;
                    case 2:
                        u = TimeUnit.HOURS;
                        break;
                }
                stopwatch.removeTime(time, u);
                dlg.dispose();
                txtTime.setText(stopwatch.getTimeString());
            }
        });
    }
}