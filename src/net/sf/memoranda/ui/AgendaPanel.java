package net.sf.memoranda.ui;

import net.sf.memoranda.*;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.AgendaGenerator;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;
import nu.xom.Element;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/*$Id: AgendaPanel.java,v 1.11 2005/02/15 16:58:02 rawsushi Exp $*/
class AgendaPanel extends JPanel {
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JButton historyBackB = new JButton();
    private final JToolBar toolBar = new JToolBar();
    private final JButton historyForwardB = new JButton();
    private final JEditorPane viewer = new JEditorPane("text/html", "");
    private final JScrollPane scrollPane = new JScrollPane();
    private final String[] priorities = {"Very High", "High", "Medium", "Low", "Very Low"};


    private DailyItemsPanel parentPanel = null;

    private Collection<String> expandedTasks;
    private String gotoTask = null;

    private boolean isActive = true;

    public AgendaPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
            ex.printStackTrace();
        }
    }

    private void jbInit() {
        expandedTasks = new ArrayList<>();
        toolBar.setFloatable(false);
        viewer.setEditable(false);
        viewer.setOpaque(false);
        viewer.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                String d = e.getDescription();
                if (d.equalsIgnoreCase("memoranda:events"))
                    parentPanel.alarmB_actionPerformed(null);
                else if (d.startsWith("memoranda:tasks")) {
                    String id = d.split("#")[1];
                    CurrentProject.set(ProjectManager.getProject(id));
                    parentPanel.taskB_actionPerformed(null);
                } else if (d.startsWith("memoranda:project")) {
                    String id = d.split("#")[1];
                    CurrentProject.set(ProjectManager.getProject(id));
                } else if (d.startsWith("memoranda:removesticker")) {
                    String id = d.split("#")[1];
                    StickerConfirmation stc = new StickerConfirmation(App.getFrame());
                    Dimension frmSize = App.getFrame().getSize();
                    stc.setSize(new Dimension(300, 180));
                    Point loc = App.getFrame().getLocation();
                    stc.setLocation(
                            (frmSize.width - stc.getSize().width) / 2 + loc.x,
                            (frmSize.height - stc.getSize().height) / 2
                                    + loc.y);
                    stc.setVisible(true);
                    if (!stc.CANCELLED) {
                        EventsManager.removeSticker(id);
                        CurrentStorage.get().storeEventsManager();
                    }
                    refresh(CurrentDate.get());
                } else if (d.startsWith("memoranda:addsticker")) {
                    StickerDialog dlg = new StickerDialog(App.getFrame());
                    Dimension frmSize = App.getFrame().getSize();
                    dlg.setSize(new Dimension(300, 380));
                    Point loc = App.getFrame().getLocation();
                    dlg.setLocation(
                            (frmSize.width - dlg.getSize().width) / 2 + loc.x,
                            (frmSize.height - dlg.getSize().height) / 2
                                    + loc.y);
                    dlg.setVisible(true);
                    if (!dlg.CANCELLED) {
                        String txt = dlg.getStickerText();
                        int sP = dlg.getPriority();
                        txt = txt.replaceAll("\\n", "<br>");
                        txt = "<div style=\"background-color:" + dlg.getStickerColor() + ";font-size:" + dlg.getStickerTextSize() + ";color:" + dlg.getStickerTextColor() + "; \">" + txt + "</div>";
                        EventsManager.createSticker(txt, sP);
                        CurrentStorage.get().storeEventsManager();
                    }
                    refresh(CurrentDate.get());
                    System.out.println("agregué un sticker");
                } else if (d.startsWith("memoranda:expandsubtasks")) {
                    String id = d.split("#")[1];
                    gotoTask = id;
                    expandedTasks.add(id);
                    refresh(CurrentDate.get());
                } else if (d.startsWith("memoranda:closesubtasks")) {
                    String id = d.split("#")[1];
                    gotoTask = id;
                    expandedTasks.remove(id);
                    refresh(CurrentDate.get());
                } else if (d.startsWith("memoranda:expandsticker")) {
                    String id = d.split("#")[1];
                    Element pre_sticker = EventsManager.getStickers().get(id);
                    String sticker = pre_sticker.getValue();
                    int first = sticker.indexOf(">");
                    int last = sticker.lastIndexOf("<");
                    int backcolor = sticker.indexOf("#");
                    int fontcolor = sticker.indexOf("#", backcolor + 1);
                    int sP = Integer.parseInt(pre_sticker.getAttributeValue("priority"));
                    String backGroundColor = sticker.substring(backcolor, sticker.indexOf(';', backcolor));
                    String foreGroundColor = sticker.substring(fontcolor, sticker.indexOf(';', fontcolor));
                    sticker = "<html>" + sticker.substring(first + 1, last) + "</html>";
                    StickerExpand dlg = new StickerExpand(App.getFrame(), sticker, backGroundColor, foreGroundColor, Local.getString("priority") + ": " + Local.getString(priorities[sP]));
                    Dimension frmSize = App.getFrame().getSize();
                    dlg.setSize(new Dimension(300, 200));
                    Point loc = App.getFrame().getLocation();
                    dlg.setLocation(
                            (frmSize.width - dlg.getSize().width) / 2 + loc.x,
                            (frmSize.height - dlg.getSize().height) / 2
                                    + loc.y);
                    dlg.stickerText.setText(sticker);
                    dlg.setVisible(true);
                } else if (d.startsWith("memoranda:editsticker")) {
                    String id = d.split("#")[1];
                    Element pre_sticker = EventsManager.getStickers().get(id);
                    String sticker = pre_sticker.getValue();
                    sticker = sticker.replaceAll("<br>", "\n");
                    int first = sticker.indexOf(">");
                    int last = sticker.lastIndexOf("<");
                    int backcolor = sticker.indexOf("#");
                    int fontcolor = sticker.indexOf("#", backcolor + 1);
                    int sizeposition = sticker.indexOf("font-size") + 10;
                    int size = Integer.parseInt(sticker.substring(sizeposition, sizeposition + 2));
                    System.out.println(size + " " + sizeposition);
                    int sP = Integer.parseInt(pre_sticker.getAttributeValue("priority"));
                    String backGroundColor = sticker.substring(backcolor, sticker.indexOf(';', backcolor));
                    String foreGroundColor = sticker.substring(fontcolor, sticker.indexOf(';', fontcolor));
                    StickerDialog dlg = new StickerDialog(App.getFrame(), sticker.substring(first + 1, last), backGroundColor, foreGroundColor, sP, size);
                    Dimension frmSize = App.getFrame().getSize();
                    dlg.setSize(new Dimension(300, 380));
                    Point loc = App.getFrame().getLocation();
                    dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                            (frmSize.height - dlg.getSize().height) / 2 + loc.y);
                    dlg.setVisible(true);
                    if (!dlg.CANCELLED) {
                        String txt = dlg.getStickerText();
                        sP = dlg.getPriority();
                        txt = txt.replaceAll("\\n", "<br>");
                        txt = "<div style=\"background-color:" + dlg.getStickerColor() + ";font-size:" + dlg.getStickerTextSize() + ";color:" + dlg.getStickerTextColor() + ";\">" + txt + "</div>";
                        EventsManager.removeSticker(id);
                        EventsManager.createSticker(txt, sP);
                        CurrentStorage.get().storeEventsManager();
                    }
                    refresh(CurrentDate.get());
                } else if (d.startsWith("memoranda:exportstickerst")) {
                    //  You need to add the export sticker meanwhile ..
                    final JFrame parent = new JFrame();
                    String name = JOptionPane.showInputDialog(parent, Local.getString("Enter filename to export"), null);
                    if (name == null) {
                        JOptionPane.showMessageDialog(null, Local.getString("Sticker export cancelled"));
                    } else {
                        new ExportSticker(name).export("txt");
                        //JOptionPane.showMessageDialog(null,name);
                    }
                    //JOptionPane.showMessageDialog(null,name);
                } else if (d.startsWith("memoranda:exportstickersh")) {
                    //  You need to add the export sticker meanwhile ..
                    final JFrame parent = new JFrame();
                    String name = JOptionPane.showInputDialog(parent, Local.getString("Enter file name to export"), null);
                    if (name == null) {
                        JOptionPane.showMessageDialog(null, Local.getString("Sticker export cancelled"));
                    } else {
                        new ExportSticker(name).export("html");
                        //JOptionPane.showMessageDialog(null,name);
                    }
                } else if (d.startsWith("memoranda:importstickers")) {
                    final JFrame parent = new JFrame();
                    String name = JOptionPane.showInputDialog(parent, Local.getString("Enter name of file to import"), null);
                    new ImportSticker().import_file();
                }
            }
        });
        historyBackB.setAction(History.historyBackAction);
        historyBackB.setFocusable(false);
        historyBackB.setBorderPainted(false);
        historyBackB.setToolTipText(Local.getString("History back"));
        historyBackB.setRequestFocusEnabled(false);
        historyBackB.setPreferredSize(new Dimension(24, 24));
        historyBackB.setMinimumSize(new Dimension(24, 24));
        historyBackB.setMaximumSize(new Dimension(24, 24));
        historyBackB.setText("");
        historyForwardB.setAction(History.historyForwardAction);
        historyForwardB.setBorderPainted(false);
        historyForwardB.setFocusable(false);
        historyForwardB.setPreferredSize(new Dimension(24, 24));
        historyForwardB.setRequestFocusEnabled(false);
        historyForwardB.setToolTipText(Local.getString("History forward"));
        historyForwardB.setMinimumSize(new Dimension(24, 24));
        historyForwardB.setMaximumSize(new Dimension(24, 24));
        historyForwardB.setText("");

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);

        scrollPane.getViewport().add(viewer, null);
        this.add(scrollPane, BorderLayout.CENTER);
        toolBar.add(historyBackB, null);
        toolBar.add(historyForwardB, null);
        toolBar.addSeparator(new Dimension(8, 24));

        this.add(toolBar, BorderLayout.NORTH);

        CurrentDate.addDateListener(d -> {
            if (isActive)
                refresh(d);
        });
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange() {
            }

            public void projectWasChanged() {
                if (isActive)
                    refresh(CurrentDate.get());
            }
        });
        EventsScheduler.addListener(new EventNotificationListener() {
            public void eventIsOccured(net.sf.memoranda.Event ev) {
                if (isActive)
                    refresh(CurrentDate.get());
            }

            public void eventsChanged() {
                if (isActive)
                    refresh(CurrentDate.get());
            }
        });
        refresh(CurrentDate.get());

        //        agendaPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        //        agendaPPMenu.add(ppShowActiveOnlyChB);
        //        PopupListener ppListener = new PopupListener();
        //        viewer.addMouseListener(ppListener);
        //		ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
        //		ppShowActiveOnlyChB.setText(
        //			Local.getString("Show Active only"));
        //		ppShowActiveOnlyChB.addActionListener(new java.awt.event.ActionListener() {
        //			public void actionPerformed(ActionEvent e) {
        //				toggleShowActiveOnly_actionPerformed(e);
        //			}
        //		});
        //		boolean isShao =
        //			(Context.get("SHOW_ACTIVE_TASKS_ONLY") != null)
        //				&& (Context.get("SHOW_ACTIVE_TASKS_ONLY").equals("true"));
        //		ppShowActiveOnlyChB.setSelected(isShao);
        //		toggleShowActiveOnly_actionPerformed(null);
    }

    public void refresh(CalendarDate date) {
        viewer.setText(AgendaGenerator.getAgenda(date, expandedTasks));
        SwingUtilities.invokeLater(() -> {
            if (gotoTask != null) {
                viewer.scrollToReference(gotoTask);
                scrollPane.setViewportView(viewer);
                Util.debug("Set view port to " + gotoTask);
            }
        });

        Util.debug("Summary updated.");
    }

    public void setActive(boolean isa) {
        isActive = isa;
    }

    //	void toggleShowActiveOnly_actionPerformed(ActionEvent e) {
    //		Context.put(
    //			"SHOW_ACTIVE_TASKS_ONLY",
    //			new Boolean(ppShowActiveOnlyChB.isSelected()));
    //		/*if (taskTable.isShowActiveOnly()) {
    //			// is true, toggle to false
    //			taskTable.setShowActiveOnly(false);
    //			//showActiveOnly.setToolTipText(Local.getString("Show Active Only"));
    //		}
    //		else {
    //			// is false, toggle to true
    //			taskTable.setShowActiveOnly(true);
    //			showActiveOnly.setToolTipText(Local.getString("Show All"));
    //		}*/
    //		refresh(CurrentDate.get());
    ////		parentPanel.updateIndicators();
    //		//taskTable.updateUI();
    //	}

    //    class PopupListener extends MouseAdapter {
    //
    //        public void mouseClicked(MouseEvent e) {
    //        	System.out.println("mouse clicked!");
    ////			if ((e.getClickCount() == 2) && (taskTable.getSelectedRow() > -1))
    ////				editTaskB_actionPerformed(null);
    //		}
    //
    //		public void mousePressed(MouseEvent e) {
    //        	System.out.println("mouse pressed!");
    //			maybeShowPopup(e);
    //		}
    //
    //		public void mouseReleased(MouseEvent e) {
    //        	System.out.println("mouse released!");
    //			maybeShowPopup(e);
    //		}
    //
    //		private void maybeShowPopup(MouseEvent e) {
    //			if (e.isPopupTrigger()) {
    //				agendaPPMenu.show(e.getComponent(), e.getX(), e.getY());
    //			}
    //		}
    //
    //    }
}
