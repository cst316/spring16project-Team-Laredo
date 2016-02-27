package net.sf.memoranda.ui;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Vector;

/*$Id: ProjectsPanel.java,v 1.14 2005/01/04 09:59:22 pbielen Exp $*/
public class ProjectsPanel extends JPanel implements ExpandablePanel {
    final ProjectsTablePanel prjTablePanel = new ProjectsTablePanel();
    public final Action newProjectAction =
            new AbstractAction(
                    Local.getString("New project") + "...",
                    new ImageIcon(
                            net.sf.memoranda.ui.AppFrame.class.getResource(
                                    "resources/icons/newproject.png"))) {

                public void actionPerformed(ActionEvent e) {
                    ppNewProject_actionPerformed();
                }
            };
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JToolBar topBar = new JToolBar();
    private final JPanel toolbarPanel = new JPanel();
    private final BorderLayout borderLayout2 = new BorderLayout();
    private final JPanel buttonsPanel = new JPanel();
    private final JButton toggleButton = new JButton();
    private final FlowLayout flowLayout1 = new FlowLayout();
    private final Vector expListeners = new Vector();
    private final ImageIcon expIcon =
            new ImageIcon(
                    net.sf.memoranda.ui.AppFrame.class.getResource(
                            "resources/icons/exp_panel.png"));
    private final ImageIcon collIcon =
            new ImageIcon(
                    net.sf.memoranda.ui.AppFrame.class.getResource(
                            "resources/icons/coll_panel.png"));
    private final JLabel curProjectTitle = new JLabel();
    private final JPopupMenu projectsPPMenu = new JPopupMenu();
    private final JMenuItem ppNewProject = new JMenuItem();
    private final JMenuItem ppProperties = new JMenuItem();
    private final JMenuItem ppDeleteProject = new JMenuItem();
    private final JMenuItem ppOpenProject = new JMenuItem();
    private final JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();
    private final JButton ppOpenB = new JButton();
    private boolean expanded = false;


    public ProjectsPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    private void jbInit() {
        Component component1 = Box.createHorizontalStrut(20);
        this.setLayout(borderLayout1);
        topBar.setBackground(new Color(215, 225, 250));
        topBar.setAlignmentX((float) 0.0);
        topBar.setFloatable(false);
        toolbarPanel.setLayout(borderLayout2);
        toggleButton.setMaximumSize(new Dimension(20, 20));
        toggleButton.setMinimumSize(new Dimension(20, 20));
        toggleButton.setOpaque(false);
        toggleButton.setPreferredSize(new Dimension(20, 20));
        toggleButton.setBorderPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setVerticalAlignment(SwingConstants.TOP);
        toggleButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        toggleButton.addActionListener(e -> toggleButton_actionPerformed());

        toggleButton.setIcon(expIcon);
        toggleButton.setMargin(new Insets(0, 0, 0, 0));
        buttonsPanel.setMinimumSize(new Dimension(70, 22));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setPreferredSize(new Dimension(80, 22));
        buttonsPanel.setRequestFocusEnabled(false);
        buttonsPanel.setLayout(flowLayout1);
        toolbarPanel.setBackground(SystemColor.textHighlight);
        toolbarPanel.setMinimumSize(new Dimension(91, 22));
        toolbarPanel.setOpaque(false);
        toolbarPanel.setPreferredSize(new Dimension(91, 22));
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);

        curProjectTitle.setFont(new java.awt.Font("Dialog", 1, 11));
        curProjectTitle.setForeground(new Color(64, 70, 128));
        curProjectTitle.setMaximumSize(new Dimension(32767, 22));
        curProjectTitle.setPreferredSize(new Dimension(32767, 22));
        curProjectTitle.setText(CurrentProject.get().getTitle());
        curProjectTitle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                toggleButton_actionPerformed();
            }
        });

		/*
         * buttonsPanel.add(newProjectB, null); buttonsPanel.add(editProjectB,
		 * null);
		 */
        ppNewProject.setFont(new java.awt.Font("Dialog", 1, 11));
        ppNewProject.setAction(newProjectAction);

        ppProperties.setFont(new java.awt.Font("Dialog", 1, 11));
        ppProperties.setText(Local.getString("Project properties"));
        ppProperties.addActionListener(e -> ppProperties_actionPerformed());
        ppProperties.setIcon(
                new ImageIcon(
                        net.sf.memoranda.ui.AppFrame.class.getResource(
                                "resources/icons/editproject.png")));
        ppProperties.setEnabled(false);
        ppDeleteProject.setFont(new java.awt.Font("Dialog", 1, 11));
        ppDeleteProject.setText(Local.getString("Delete project"));
        ppDeleteProject.addActionListener(e -> ppDeleteProject_actionPerformed());
        ppDeleteProject.setIcon(
                new ImageIcon(
                        net.sf.memoranda.ui.AppFrame.class.getResource(
                                "resources/icons/removeproject.png")));
        ppDeleteProject.setEnabled(false);

        ppOpenProject.setFont(new java.awt.Font("Dialog", 1, 11));

        ppOpenProject.setText(" " + Local.getString("Open project"));

        ppOpenProject.addActionListener(e -> ppOpenProject_actionPerformed());
        ppOpenProject.setEnabled(false);

        ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
        ppShowActiveOnlyChB.setText(
                Local.getString("Show active projects only"));
        ppShowActiveOnlyChB
                .addActionListener(e -> ppShowActiveOnlyChB_actionPerformed());
        boolean isShao =
                (Context.get("SHOW_ACTIVE_PROJECTS_ONLY") != null)
                        && (Context.get("SHOW_ACTIVE_PROJECTS_ONLY").equals("true"));
        ppShowActiveOnlyChB.setSelected(isShao);
        ppShowActiveOnlyChB_actionPerformed();

        projectsPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        ppOpenB.setMaximumSize(new Dimension(34, 20));
        ppOpenB.setMinimumSize(new Dimension(24, 10));
        ppOpenB.setOpaque(false);
        ppOpenB.setPreferredSize(new Dimension(24, 20));
        ppOpenB.setBorderPainted(false);
        ppOpenB.setFocusPainted(false);
        ppOpenB.setMargin(new Insets(0, 0, 0, 0));
        ppOpenB.addActionListener(e -> ppOpenB_actionPerformed());
        ppOpenB.setIcon(
                new ImageIcon(
                        net.sf.memoranda.ui.AppFrame.class.getResource(
                                "resources/icons/ppopen.png")));
        buttonsPanel.add(ppOpenB, null);
        buttonsPanel.add(component1, null);
        this.add(topBar, BorderLayout.NORTH);
        this.add(prjTablePanel, BorderLayout.CENTER);
        topBar.add(toolbarPanel, null);
        toolbarPanel.add(buttonsPanel, BorderLayout.EAST);
        buttonsPanel.add(toggleButton, null);
        toolbarPanel.add(curProjectTitle, BorderLayout.CENTER);
        projectsPPMenu.add(ppOpenProject);
        projectsPPMenu.addSeparator();
        projectsPPMenu.add(ppNewProject);
        projectsPPMenu.add(ppDeleteProject);
        projectsPPMenu.addSeparator();
        projectsPPMenu.add(ppProperties);
        projectsPPMenu.addSeparator();
        projectsPPMenu.add(ppShowActiveOnlyChB);
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange() {
            }

            public void projectWasChanged() {
                curProjectTitle.setText(CurrentProject.get().getTitle());
                prjTablePanel.updateUI();
            }
        });
        CurrentDate.addDateListener(d -> prjTablePanel.updateUI());
        prjTablePanel.projectsTable.addMouseListener(new PopupListener());
        prjTablePanel
                .projectsTable
                .getSelectionModel()
                .addListSelectionListener(e -> {
                    boolean enabled1 =
                            !prjTablePanel
                                    .projectsTable
                                    .getModel()
                                    .getValueAt(
                                            prjTablePanel.projectsTable.getSelectedRow(),
                                            ProjectsTablePanel.PROJECT_ID)
                                    .toString()
                                    .equals(CurrentProject.get().getID());
                    ppDeleteProject.setEnabled(enabled1);
                    ppOpenProject.setEnabled(enabled1);
                    ppProperties.setEnabled(true);
                });
        prjTablePanel.projectsTable.setToolTipText(
                Local.getString("Double-click to set a current project"));

        // delete projects using the DEL kew
        prjTablePanel.projectsTable.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (prjTablePanel.projectsTable.getSelectedRows().length > 0
                        && e.getKeyCode() == KeyEvent.VK_DELETE)
                    ppDeleteProject_actionPerformed();
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });
    }

    private void toggleButton_actionPerformed() {
        for (Object expListener : expListeners)
            ((ActionListener) expListener).actionPerformed(
                    new ActionEvent(this, 0, "Panel expanded (collapsed)"));
        if (expanded) {
            expanded = false;
            toggleButton.setIcon(expIcon);
        } else {
            expanded = true;
            toggleButton.setIcon(collIcon);
        }
    }

    public void AddExpandListener(ActionListener al) {
        expListeners.add(al);
    }

    private void ppOpenB_actionPerformed() {
        projectsPPMenu.show(
                buttonsPanel,
                (int) (ppOpenB.getLocation().getX() + 24)
                        - projectsPPMenu.getWidth(),
                (int) ppOpenB.getLocation().getY() + 24);
    }

    private void ppOpenProject_actionPerformed() {
        CurrentProject.set(prjTablePanel.getSelectedProject());
        prjTablePanel.updateUI();
        ppDeleteProject.setEnabled(false);
        ppOpenProject.setEnabled(false);
    }

    private void ppNewProject_actionPerformed() {
        ProjectDialog.newProject();
        prjTablePanel.updateUI();
    }

    private void ppDeleteProject_actionPerformed() {
        String msg;
        Project prj;
        Vector toremove = new Vector();
        if (prjTablePanel.projectsTable.getSelectedRows().length > 1)
            msg =
                    Local.getString("Delete")
                            + " "
                            + prjTablePanel.projectsTable.getSelectedRows().length
                            + " "
                            + Local.getString("projects")
                            + "\n"
                            + Local.getString("Are you sure?");
        else {
            prj = prjTablePanel.getSelectedProject();
            msg =
                    Local.getString("Delete project")
                            + " '"
                            + prj.getTitle()
                            + "'.\n"
                            + Local.getString("Are you sure?");
        }

        int n =
                JOptionPane.showConfirmDialog(
                        App.getFrame(),
                        msg,
                        Local.getString("Delete project"),
                        JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;

        for (int i = 0;
             i < prjTablePanel.projectsTable.getSelectedRows().length;
             i++) {
            prj =
                    (net.sf.memoranda.Project) prjTablePanel
                            .projectsTable
                            .getModel()
                            .getValueAt(
                                    prjTablePanel.projectsTable.getSelectedRows()[i],
                                    ProjectsTablePanel.PROJECT);
            toremove.add(prj.getID());
        }
        for (Object aToremove : toremove) {
            ProjectManager.removeProject((String) aToremove);
        }
        CurrentStorage.get().storeProjectManager();
        prjTablePanel.projectsTable.clearSelection();
        prjTablePanel.updateUI();
        setMenuEnabled();
    }

    private void ppProperties_actionPerformed() {
        Project prj = prjTablePanel.getSelectedProject();
        ProjectDialog dlg =
                new ProjectDialog(Local.getString("Project properties"));
        Dimension dlgSize = dlg.getSize();
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation(
                (frmSize.width - dlgSize.width) / 2 + loc.x,
                (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.prTitleField.setText(prj.getTitle());
        dlg.startDate.getModel().setValue(
                prj.getStartDate().getCalendar().getTime());
        if (prj.getEndDate() != null) {
            dlg.edButton.setEnabled(true);
            dlg.endDateChB.setForeground(Color.BLACK);

            dlg.endDateChB.setSelected(true);
            dlg.endDate.setEnabled(true);
            dlg.endDate.getModel().setValue(
                    prj.getEndDate().getCalendar().getTime());
        }
        /*if (prj.getStatus() == Project.FROZEN)
			dlg.freezeChB.setSelected(true);*/
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        prj.setTitle(dlg.prTitleField.getText());
        prj.setStartDate(
                new CalendarDate((Date) dlg.startDate.getModel().getValue()));

        if (dlg.endDateChB.isSelected())
            prj.setEndDate(
                    new CalendarDate((Date) dlg.endDate.getModel().getValue()));
        else
            prj.setEndDate(null);
        prjTablePanel.updateUI();
        /*
		 * if (dlg.freezeChB.isSelected()) prj.freeze(); else
		 */
    }

    private void ppShowActiveOnlyChB_actionPerformed() {
        prjTablePanel.setShowActiveOnly(ppShowActiveOnlyChB.isSelected());
        Context.put(
                "SHOW_ACTIVE_PROJECTS_ONLY",
                Boolean.valueOf(ppShowActiveOnlyChB.isSelected()));
    }

    private void setMenuEnabled() {
        ppDeleteProject.setEnabled(false);
        ppOpenProject.setEnabled(false);
        ppProperties.setEnabled(false);
    }

    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2)
                ppOpenProject_actionPerformed();
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                projectsPPMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

}