package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.Local;
import java.awt.Font;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: WorkPanel.java,v 1.9 2004/04/05 10:05:44 alexeya Exp $*/
public class WorkPanel extends JPanel {
	public final JButton notesB = new JButton();
    public final DailyItemsPanel dailyItemsPanel = new DailyItemsPanel(this);
    public final JButton agendaB = new JButton();
    public final JButton tasksB = new JButton();
    public final JButton eventsB = new JButton();
    public final JButton filesB = new JButton();
    private final JButton btnStopwatch = new JButton();
    private final ResourcesPanel filesPanel = new ResourcesPanel();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JToolBar toolBar = new JToolBar();
    private final JPanel panel = new JPanel();
    private final CardLayout cardLayout1 = new CardLayout();
    private final JButton defectsButton = new JButton();
    private JButton currentB = null;

	public WorkPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	private final void jbInit() throws Exception {
		BorderFactory.createCompoundBorder(
	            BorderFactory.createBevelBorder(
	                    BevelBorder.LOWERED,
	                    Color.white,
	                    Color.white,
	                    new Color(124, 124, 124),
	                    new Color(178, 178, 178)),
	            BorderFactory.createEmptyBorder(0, 2, 0, 0));

		this.setLayout(borderLayout1);
		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.setBackground(Color.white);

		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);
		panel.setLayout(cardLayout1);

		agendaB.setBackground(Color.white);
		agendaB.setMaximumSize(new Dimension(60, 80));
		agendaB.setMinimumSize(new Dimension(30, 30));

		agendaB.setFont(new Font("Dialog", 1, 10));
		agendaB.setPreferredSize(new Dimension(50, 50));
		agendaB.setBorderPainted(false);
		agendaB.setContentAreaFilled(false);
		agendaB.setFocusPainted(false);
		agendaB.setHorizontalTextPosition(SwingConstants.CENTER);
		agendaB.setText(Local.getString("Agenda"));
		agendaB.setVerticalAlignment(SwingConstants.TOP);
		agendaB.setVerticalTextPosition(SwingConstants.BOTTOM);
		agendaB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agendaBActionPerformed(e);
			}
		});
		agendaB.setIcon(
			new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
					"resources/icons/agenda.png")));
		agendaB.setOpaque(false);
		agendaB.setMargin(new Insets(0, 0, 0, 0));
		agendaB.setSelected(true);

		eventsB.setBackground(Color.white);
		eventsB.setMaximumSize(new Dimension(60, 80));
		eventsB.setMinimumSize(new Dimension(30, 30));

		eventsB.setFont(new Font("Dialog", 1, 10));
		eventsB.setPreferredSize(new Dimension(50, 50));
		eventsB.setBorderPainted(false);
		eventsB.setContentAreaFilled(false);
		eventsB.setFocusPainted(false);
		eventsB.setHorizontalTextPosition(SwingConstants.CENTER);
		eventsB.setText(Local.getString("Events"));
		eventsB.setVerticalAlignment(SwingConstants.TOP);
		eventsB.setVerticalTextPosition(SwingConstants.BOTTOM);
		eventsB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventsBActionPerformed(e);
			}
		});
		eventsB.setIcon(
			new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
					"resources/icons/events.png")));
		eventsB.setOpaque(false);
		eventsB.setMargin(new Insets(0, 0, 0, 0));
		//eventsB.setSelected(true);

		tasksB.setSelected(true);
		tasksB.setFont(new Font("Dialog", 1, 10));
		tasksB.setMargin(new Insets(0, 0, 0, 0));
		tasksB.setIcon(
			new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
					"resources/icons/tasks.png")));
		tasksB.setVerticalTextPosition(SwingConstants.BOTTOM);
		tasksB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tasksBActionPerformed(e);
			}
		});
		tasksB.setVerticalAlignment(SwingConstants.TOP);
		tasksB.setText(Local.getString("Tasks"));
		tasksB.setHorizontalTextPosition(SwingConstants.CENTER);
		tasksB.setFocusPainted(false);
		tasksB.setBorderPainted(false);
		tasksB.setContentAreaFilled(false);
		tasksB.setPreferredSize(new Dimension(50, 50));
		tasksB.setMinimumSize(new Dimension(30, 30));
		tasksB.setOpaque(false);
		tasksB.setMaximumSize(new Dimension(60, 80));
		tasksB.setBackground(Color.white);

		notesB.setFont(new Font("Dialog", 1, 10));
		notesB.setBackground(Color.white);
		notesB.setBorder(null);
		notesB.setMaximumSize(new Dimension(60, 80));
		notesB.setMinimumSize(new Dimension(30, 30));
		notesB.setOpaque(false);
		notesB.setPreferredSize(new Dimension(60, 50));
		notesB.setBorderPainted(false);
		notesB.setContentAreaFilled(false);
		notesB.setFocusPainted(false);
		notesB.setHorizontalTextPosition(SwingConstants.CENTER);
		notesB.setText(Local.getString("Notes"));
		notesB.setVerticalAlignment(SwingConstants.TOP);
		notesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		notesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notesBActionPerformed(e);
			}
		});
		notesB.setIcon(
			new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
					"resources/icons/notes.png")));
		notesB.setMargin(new Insets(0, 0, 0, 0));
		notesB.setSelected(true);
		this.setPreferredSize(new Dimension(1073, 300));

		filesB.setSelected(true);
		filesB.setMargin(new Insets(0, 0, 0, 0));
		filesB.setIcon(
			new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
					"resources/icons/files.png")));
		filesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		filesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filesBActionPerformed(e);
			}
		});
		filesB.setFont(new Font("Dialog", 1, 10));
		filesB.setVerticalAlignment(SwingConstants.TOP);
		filesB.setText(Local.getString("Resources"));
		filesB.setHorizontalTextPosition(SwingConstants.CENTER);
		filesB.setFocusPainted(false);
		filesB.setBorderPainted(false);
		filesB.setContentAreaFilled(false);
		filesB.setPreferredSize(new Dimension(50, 50));
		filesB.setMinimumSize(new Dimension(30, 30));
		filesB.setOpaque(false);
		filesB.setMaximumSize(new Dimension(60, 80));
		filesB.setBackground(Color.white);
		this.add(toolBar, BorderLayout.WEST);
		this.add(panel, BorderLayout.CENTER);
		panel.add(dailyItemsPanel, "DAILYITEMS");
		panel.add(filesPanel, "FILES");
		toolBar.add(agendaB, null);
		toolBar.add(eventsB, null);
		toolBar.add(tasksB, null);
		toolBar.add(notesB, null);
		toolBar.add(filesB, null);
		currentB = agendaB;
		// Default blue color
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);

		toolBar.setBorder(null);
		btnStopwatch.setIcon(new ImageIcon(WorkPanel.class.getResource("/net/sf/memoranda/ui/resources/icons/timeclock.png")));
		btnStopwatch.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnStopwatch.setVerticalAlignment(SwingConstants.TOP);
		btnStopwatch.setText("Stopwatch");
		btnStopwatch.setSelected(true);
		btnStopwatch.setPreferredSize(new Dimension(50, 50));
		btnStopwatch.setOpaque(false);
		btnStopwatch.setMinimumSize(new Dimension(30, 30));
		btnStopwatch.setMaximumSize(new Dimension(60, 80));
		btnStopwatch.setMargin(new Insets(0, 0, 0, 0));
		btnStopwatch.setHorizontalTextPosition(SwingConstants.CENTER);
		btnStopwatch.setFont(new Font("Dialog", Font.BOLD, 10));
		btnStopwatch.setFocusPainted(false);
		btnStopwatch.setContentAreaFilled(false);
		btnStopwatch.setBorderPainted(false);
		btnStopwatch.setBackground(Color.WHITE);
		btnStopwatch.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStopwatchActionPerformed(e);
			}
		});
		toolBar.add(btnStopwatch);
		
		//Image created by Tyler Cole
        //Instantiates defect button and defect button layout
        defectsButton.setIcon(new ImageIcon(WorkPanel.class.getResource("/net/sf/memoranda/ui/resources/icons/defects.png")));
        defectsButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        defectsButton.setVerticalAlignment(SwingConstants.TOP);
        defectsButton.setText("Defects");
        defectsButton.setSelected(true);
        defectsButton.setPreferredSize(new Dimension(50, 50));
        defectsButton.setMaximumSize(new Dimension(60, 80));
        defectsButton.setMinimumSize(new Dimension(30, 30));
        defectsButton.setMargin(new Insets(0, 0, 0, 0));
        defectsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        defectsButton.setFont(new Font("Dialog", Font.BOLD, 10));
        defectsButton.setFocusPainted(false);
        defectsButton.setContentAreaFilled(false);
        defectsButton.setBorderPainted(false);
        defectsButton.setBackground(Color.WHITE);
        defectsButton.addActionListener(e -> btnDefectActionPerform());
        toolBar.add(defectsButton, null);
		
		panel.setBorder(null);
		dailyItemsPanel.setBorder(null);
		filesPanel.setBorder(null);

	}

	public void selectPanel(String pan) {
		if (pan != null) {
			if (pan.equals("NOTES"))
				notesBActionPerformed(null);
			else if (pan.equals("TASKS"))
				tasksBActionPerformed(null);
			else if (pan.equals("EVENTS"))
				eventsBActionPerformed(null);
			else if (pan.equals("FILES"))
				filesBActionPerformed(null);
		}
	}

    private void btnDefectActionPerform() {
        cardLayout1.show(panel, "DAILYITEMS");
        setCurrentButton(defectsButton);
        dailyItemsPanel.selectPanel("DEFECTS");
        Context.put("CURRENT_PANEL", "DEFECTS");
    }

	public void agendaBActionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("AGENDA");
		setCurrentButton(agendaB);
		Context.put("CURRENT_PANEL", "AGENDA");
	}

	public void notesBActionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("NOTES");
		setCurrentButton(notesB);
		Context.put("CURRENT_PANEL", "NOTES");
	}

	public void tasksBActionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TASKS");
		setCurrentButton(tasksB);
		Context.put("CURRENT_PANEL", "TASKS");
	}

	public void eventsBActionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("EVENTS");
		setCurrentButton(eventsB);
		Context.put("CURRENT_PANEL", "EVENTS");
	}

	public void filesBActionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "FILES");
		setCurrentButton(filesB);
		Context.put("CURRENT_PANEL", "FILES");
	}
	public void btnStopwatchActionPerformed(ActionEvent e) {
		if(CurrentProject.getTaskList().getAllTasks().isEmpty()) {
			JOptionPane.showMessageDialog(null, "No tasks that need timing exist in project.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		StopwatchDialogSingleton.getStopwatch(App.getFrame());
	}

	void setCurrentButton(JButton cb) {
		currentB.setBackground(Color.white);
		currentB.setOpaque(false);
		currentB = cb;
		// Default color blue
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);
	}
}