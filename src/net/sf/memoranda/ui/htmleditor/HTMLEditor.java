package net.sf.memoranda.ui.htmleditor;

import net.sf.memoranda.ui.htmleditor.util.Local;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

/**
 *
 */
public class HTMLEditor extends JPanel {
    public final int T_P = 0;
    public final int T_H1 = 1;
    public final int T_H2 = 2;
    public final int T_H3 = 3;
    public final int T_H4 = 4;
    public final int T_H5 = 5;
    public final int T_H6 = 6;
    public final int T_PRE = 7;
    public final int T_BLOCKQ = 8;
    public final int I_NORMAL = 0;
    public final int I_EM = 1;
    public final int I_STRONG = 2;
    public final int I_CODE = 3;
    public final int I_CITE = 4;
    public final int I_SUPERSCRIPT = 5;
    public final int I_SUBSCRIPT = 6;
    public final int I_CUSTOM = 7;
    public final HTMLEditorPane editor = new HTMLEditorPane();
    public final Action selectAllAction =
            new HTMLEditorAction(Local.getString("Select all")) {
                public void actionPerformed(ActionEvent e) {
                    editor.selectAll();
                }
            };
    public final JTabbedPane toolsPanel = new JTabbedPane();
    public final InsertTableCellAction insertTableCellAction =
            new InsertTableCellAction();
    public final InsertTableRowAction insertTableRowAction =
            new InsertTableRowAction();
    public final BreakAction breakAction = new BreakAction();
    public final Action cutAction = new HTMLEditorKit.CutAction();
    public final Action stylePasteAction = new HTMLEditorKit.PasteAction();
    public final UndoAction undoAction = new UndoAction();
    public final RedoAction redoAction = new RedoAction();
    public final JToolBar editToolbar = new JToolBar();
    public final Action lAlignAction =
            new HTMLEditorAction(
                    Local.getString("Align left"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/alignleft.png"))) {
                public void actionPerformed(ActionEvent e) {
                    lAlignActionB_actionPerformed(e);
                }
            };
    public final Action cAlignAction =
            new HTMLEditorAction(
                    Local.getString("Align center"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/aligncenter.png"))) {
                public void actionPerformed(ActionEvent e) {
                    cAlignActionB_actionPerformed(e);
                }
            };
    public final Action rAlignAction =
            new HTMLEditorAction(
                    Local.getString("Align right"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/alignright.png"))) {
                public void actionPerformed(ActionEvent e) {
                    rAlignActionB_actionPerformed(e);
                }
            };
    public final Action findAction =
            new HTMLEditorAction(
                    Local.getString("Find & Replace"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/find.png"))) {
                public void actionPerformed(ActionEvent e) {
                    doFind();
                }
            };
    private final HTMLEditorKit editorKit = new HTMLEditorKit();
    private final Action styleCopyAction = new HTMLEditorKit.CopyAction();
    public final Action copyAction = styleCopyAction;
    /**
     * Listener for the edits on the current document.
     */
    private final UndoableEditListener undoHandler = new UndoHandler();
    /**
     * UndoManager that we add edits to.
     */
    private final UndoManager undo = new UndoManager();
    private final JScrollPane jScrollPane1 = new JScrollPane();
    private final CharTablePanel charTablePanel = new CharTablePanel(editor);
    private final JButton lAlignActionB = new JButton();
    private final JButton olActionB = new JButton();
    private final JButton linkActionB = new JButton();
    private final JButton italicActionB = new JButton();
    private final JButton propsActionB = new JButton();
    private final JButton imageActionB = new JButton();
    private final String[] elementTypes =
            {
                    Local.getString("Paragraph"),
                    Local.getString("Header") + " 1",
                    Local.getString("Header") + " 2",
                    Local.getString("Header") + " 3",
                    Local.getString("Header") + " 4",
                    Local.getString("Header") + " 5",
                    Local.getString("Header") + " 6",
                    Local.getString("Preformatted"),
                    //"Address",
                    Local.getString("Blockquote")};
    private final JComboBox<? extends String> blockCB = new JComboBox<>(elementTypes);
    private final String[] inlineTypes =
            {
                    Local.getString("Normal"),
                    Local.getString("Emphasis"),
                    Local.getString("Strong"),
                    Local.getString("Code"),
                    Local.getString("Cite"),
                    Local.getString("Superscript"),
                    Local.getString("Subscript"),
                    Local.getString("Custom style") + "..."};
    private final JComboBox<? extends String> inlineCB = new JComboBox<>(inlineTypes);
    private final JButton boldActionB = new JButton();
    private final JButton ulActionB = new JButton();
    private final JButton rAlignActionB = new JButton();
    private final JButton tableActionB = new JButton();
    private final JButton cAlignActionB = new JButton();
    private final JButton underActionB = new JButton();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JMenuItem jMenuItemUndo = new JMenuItem(undoAction);
    private final JMenuItem jMenuItemRedo = new JMenuItem(redoAction);
    private final JMenuItem jMenuItemCut = new JMenuItem(cutAction);
    private final JMenuItem jMenuItemCopy = new JMenuItem(copyAction);
    private final JMenuItem jMenuItemPaste;
    private final JMenuItem jMenuItemProp;
    private final JMenuItem jMenuItemInsCell;
    private final JMenuItem jMenuItemInsRow;
    //JPopupMenu tablePopupMenu = new JPopupMenu();
    private final JButton brActionB = new JButton();
    private final JButton hrActionB = new JButton();
    private final JButton insCharActionB = new JButton();
    public HTMLDocument document = null;
    public final Action pasteAction =
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    doPaste();
                }
            };
    public final Action tableAction =
            new HTMLEditorAction(
                    Local.getString("Insert table"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/table.png"))) {
                public void actionPerformed(ActionEvent e) {
                    tableActionB_actionPerformed();
                }
            };
    public final Action linkAction =
            new HTMLEditorAction(
                    Local.getString("Insert hyperlink"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/link.png"))) {
                public void actionPerformed(ActionEvent e) {
                    linkActionB_actionPerformed();
                }
            };
    public final Action insertHRAction =
            new HTMLEditorAction(
                    Local.getString("Insert horizontal rule"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/hr.png"))) {
                public void actionPerformed(ActionEvent e) {
                    try {
                        editorKit.insertHTML(
                                document,
                                editor.getCaretPosition(),
                                "<hr>",
                                0,
                                0,
                                HTML.Tag.HR);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
    public final Action propsAction =
            new HTMLEditorAction(
                    Local.getString("Object properties"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/properties.png"))) {
                public void actionPerformed(ActionEvent e) {
                    propsActionB_actionPerformed();
                }
            };
    public final Action ulAction =
            new HTMLEditorAction(
                    Local.getString("Unordered list"),
                    new ImageIcon(
                            HTMLEditor.class.getResource("resources/icons/listunordered.png"))) {
                public void actionPerformed(ActionEvent e) {
                    ulActionB_actionPerformed(e);
                }
            };
    public final Action olAction =
            new HTMLEditorAction(
                    Local.getString("Ordered list"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/listordered.png"))) {
                public void actionPerformed(ActionEvent e) {
                    olActionB_actionPerformed(e);
                }
            };
    private boolean toolsPanelShow = false;
    private boolean bold = false;
    private boolean italic = false;
    private boolean under = false;
    private Border border1;
    private Border border2;
    public final Action italicAction =
            new HTMLEditorAction(
                    Local.getString("Italic"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/italic.png"))) {
                public void actionPerformed(ActionEvent e) {
                    italicActionB_actionPerformed(e);
                }
            };
    public final Action boldAction =
            new HTMLEditorAction(
                    Local.getString("Bold"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/bold.png"))) {
                public void actionPerformed(ActionEvent e) {
                    boldActionB_actionPerformed(e);
                }
            };
    public final Action underAction =
            new HTMLEditorAction(
                    Local.getString("Underline"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/underline.png"))) {
                public void actionPerformed(ActionEvent e) {
                    underActionB_actionPerformed(e);
                }
            };
    private String imagesDir = null;

    public final Action imageAction =
            new HTMLEditorAction(
                    Local.getString("Insert image"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/image.png"))) {
                public void actionPerformed(ActionEvent e) {
                    imageActionB_actionPerformed();
                }
            };
    private boolean charTableShow = false;
    public final Action insCharAction =
            new HTMLEditorAction(
                    Local.getString("Insert character"),
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/char.png"))) {
                public void actionPerformed(ActionEvent e) {
                    if (!charTableShow) {
                        addCharTablePanel();
                        charTableShow = true;
                        insCharActionB.setBorder(border2);
                    } else {
                        removeCharTablePanel();
                        charTableShow = false;
                        insCharActionB.setBorder(border1);
                    }
                    insCharActionB.setBorderPainted(charTableShow);
                }
            };
    private boolean blockCBEventsLock = false;
    private boolean inlineCBEventsLock = false;

    public HTMLEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jMenuItemPaste = new JMenuItem(pasteAction);
        jMenuItemProp = new JMenuItem(propsAction);
        jMenuItemInsCell = new JMenuItem(insertTableCellAction);
        jMenuItemInsRow = new JMenuItem(insertTableRowAction);
    }

    public void showToolsPanel() {
        if (toolsPanelShow)
            return;
        this.add(toolsPanel, BorderLayout.SOUTH);
        toolsPanelShow = true;
    }

    public void hideToolsPanel() {
        if (!toolsPanelShow)
            return;
        this.remove(charTablePanel);
        toolsPanelShow = false;
    }

    private void addCharTablePanel() {
        showToolsPanel();
        toolsPanel.addTab(Local.getString("Characters"), charTablePanel);
    }

    private void removeCharTablePanel() {
        toolsPanel.remove(charTablePanel);
        if (toolsPanel.getTabCount() == 0)
            hideToolsPanel();
    }

    private void doPaste() {
        Clipboard clip =
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            Transferable content = clip.getContents(this);
            if (content == null)
                return;
            String txt =
                    content
                            .getTransferData(new DataFlavor(String.class, "String"))
                            .toString();
            document.replace(
                    editor.getSelectionStart(),
                    editor.getSelectionEnd() - editor.getSelectionStart(),
                    txt,
                    editorKit.getInputAttributes());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() {

        cutAction.putValue(
                Action.SMALL_ICON,
                new ImageIcon(HTMLEditor.class.getResource("resources/icons/cut.png")));
        cutAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
        cutAction.putValue(Action.NAME, Local.getString("Cut"));
        cutAction.putValue(Action.SHORT_DESCRIPTION, Local.getString("Cut"));

        copyAction.putValue(
                Action.SMALL_ICON,
                new ImageIcon(HTMLEditor.class.getResource("resources/icons/copy.png")));
        copyAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        copyAction.putValue(Action.NAME, Local.getString("Copy"));
        copyAction.putValue(Action.SHORT_DESCRIPTION, Local.getString("Copy"));

        pasteAction.putValue(
                Action.SMALL_ICON,
                new ImageIcon(HTMLEditor.class.getResource("resources/icons/paste.png")));
        pasteAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
        pasteAction.putValue(Action.NAME, Local.getString("Paste"));
        pasteAction.putValue(
                Action.SHORT_DESCRIPTION,
                Local.getString("Paste"));

        stylePasteAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_V,
                        KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
        stylePasteAction.putValue(
                Action.NAME,
                Local.getString("Paste special"));
        stylePasteAction.putValue(
                Action.SHORT_DESCRIPTION,
                Local.getString("Paste special"));

        selectAllAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));

        boldAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK));
        italicAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));
        underAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK));
        breakAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_MASK));
        breakAction.putValue(Action.NAME, Local.getString("Insert Break"));
        breakAction.putValue(
                Action.SHORT_DESCRIPTION,
                Local.getString("Insert Break"));

        findAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));

        document = (HTMLDocument) editorKit.createDefaultDocument();

        border1 =
                BorderFactory.createEtchedBorder(
                        Color.white,
                        new Color(142, 142, 142));
        border2 =
                BorderFactory.createBevelBorder(
                        BevelBorder.LOWERED,
                        Color.white,
                        Color.white,
                        new Color(142, 142, 142),
                        new Color(99, 99, 99));
        this.setLayout(borderLayout1);

        editor.addCaretListener(this::editor_caretUpdate);

        editor.setEditorKit(editorKit);
        editorKit.setDefaultCursor(new Cursor(Cursor.TEXT_CURSOR));

        editor.setDocument(document);
        document.addUndoableEditListener(undoHandler);

        this.setPreferredSize(new Dimension(520, 57));
        editToolbar.setRequestFocusEnabled(false);
        editToolbar.setToolTipText("");

        boldActionB.setAction(boldAction);
        boldActionB.setBorder(border1);
        boldActionB.setMaximumSize(new Dimension(22, 22));
        boldActionB.setMinimumSize(new Dimension(22, 22));
        boldActionB.setPreferredSize(new Dimension(22, 22));
        boldActionB.setBorderPainted(false);
        boldActionB.setFocusable(false);
        boldActionB.setText("");

        italicActionB.setAction(italicAction);
        italicActionB.setBorder(border1);
        italicActionB.setMaximumSize(new Dimension(22, 22));
        italicActionB.setMinimumSize(new Dimension(22, 22));
        italicActionB.setPreferredSize(new Dimension(22, 22));
        italicActionB.setBorderPainted(false);
        italicActionB.setFocusable(false);
        italicActionB.setText("");

        underActionB.setAction(underAction);
        underActionB.setBorder(border1);
        underActionB.setMaximumSize(new Dimension(22, 22));
        underActionB.setMinimumSize(new Dimension(22, 22));
        underActionB.setPreferredSize(new Dimension(22, 22));
        underActionB.setBorderPainted(false);
        underActionB.setFocusable(false);
        underActionB.setText("");

        lAlignActionB.setAction(lAlignAction);
        lAlignActionB.setMaximumSize(new Dimension(22, 22));
        lAlignActionB.setMinimumSize(new Dimension(22, 22));
        lAlignActionB.setPreferredSize(new Dimension(22, 22));
        lAlignActionB.setBorderPainted(false);
        lAlignActionB.setFocusable(false);
        lAlignActionB.setText("");

        rAlignActionB.setAction(rAlignAction);
        rAlignActionB.setFocusable(false);
        rAlignActionB.setPreferredSize(new Dimension(22, 22));
        rAlignActionB.setBorderPainted(false);
        rAlignActionB.setMinimumSize(new Dimension(22, 22));
        rAlignActionB.setMaximumSize(new Dimension(22, 22));
        rAlignActionB.setText("");

        cAlignActionB.setAction(cAlignAction);
        cAlignActionB.setMaximumSize(new Dimension(22, 22));
        cAlignActionB.setMinimumSize(new Dimension(22, 22));
        cAlignActionB.setPreferredSize(new Dimension(22, 22));
        cAlignActionB.setBorderPainted(false);
        cAlignActionB.setFocusable(false);
        cAlignActionB.setText("");

        ulActionB.setAction(ulAction);
        ulActionB.setMaximumSize(new Dimension(22, 22));
        ulActionB.setMinimumSize(new Dimension(22, 22));
        ulActionB.setPreferredSize(new Dimension(22, 22));
        ulActionB.setBorderPainted(false);
        ulActionB.setFocusable(false);
        ulActionB.setText("");

        olActionB.setAction(olAction);
        olActionB.setMaximumSize(new Dimension(22, 22));
        olActionB.setMinimumSize(new Dimension(22, 22));
        olActionB.setPreferredSize(new Dimension(22, 22));
        olActionB.setBorderPainted(false);
        olActionB.setFocusable(false);
        olActionB.setText("");

        linkActionB.setAction(linkAction);
        linkActionB.setMaximumSize(new Dimension(22, 22));
        linkActionB.setMinimumSize(new Dimension(22, 22));
        linkActionB.setPreferredSize(new Dimension(22, 22));
        linkActionB.setBorderPainted(false);
        linkActionB.setFocusable(false);
        linkActionB.setText("");

        propsActionB.setAction(propsAction);
        propsActionB.setFocusable(false);
        propsActionB.setPreferredSize(new Dimension(22, 22));
        propsActionB.setBorderPainted(false);
        propsActionB.setMinimumSize(new Dimension(22, 22));
        propsActionB.setMaximumSize(new Dimension(22, 22));
        propsActionB.setText("");

        imageActionB.setAction(imageAction);
        imageActionB.setMaximumSize(new Dimension(22, 22));
        imageActionB.setMinimumSize(new Dimension(22, 22));
        imageActionB.setPreferredSize(new Dimension(22, 22));
        imageActionB.setBorderPainted(false);
        imageActionB.setFocusable(false);
        imageActionB.setText("");

        tableActionB.setAction(tableAction);
        tableActionB.setFocusable(false);
        tableActionB.setPreferredSize(new Dimension(22, 22));
        tableActionB.setBorderPainted(false);
        tableActionB.setMinimumSize(new Dimension(22, 22));
        tableActionB.setMaximumSize(new Dimension(22, 22));
        tableActionB.setText("");

        brActionB.setAction(breakAction);
        brActionB.setFocusable(false);
        brActionB.setBorderPainted(false);
        brActionB.setPreferredSize(new Dimension(22, 22));
        brActionB.setMinimumSize(new Dimension(22, 22));
        brActionB.setMaximumSize(new Dimension(22, 22));
        brActionB.setText("");

        hrActionB.setAction(insertHRAction);
        hrActionB.setMaximumSize(new Dimension(22, 22));
        hrActionB.setMinimumSize(new Dimension(22, 22));
        hrActionB.setPreferredSize(new Dimension(22, 22));
        hrActionB.setBorderPainted(false);
        hrActionB.setFocusable(false);
        hrActionB.setText("");

        insCharActionB.setAction(insCharAction);
        insCharActionB.setBorder(border1);
        insCharActionB.setMaximumSize(new Dimension(22, 22));
        insCharActionB.setMinimumSize(new Dimension(22, 22));
        insCharActionB.setPreferredSize(new Dimension(22, 22));
        insCharActionB.setBorderPainted(false);
        insCharActionB.setFocusable(false);
        insCharActionB.setText("");

        blockCB.setBackground(new Color(230, 230, 230));
        blockCB.setMaximumRowCount(12);
        blockCB.setFont(new java.awt.Font("Dialog", 1, 10));
        blockCB.setMaximumSize(new Dimension(120, 22));
        blockCB.setMinimumSize(new Dimension(60, 22));
        blockCB.setPreferredSize(new Dimension(79, 22));
        blockCB.setFocusable(false);
        blockCB.addActionListener(e -> blockCB_actionPerformed());

        inlineCB.addActionListener(e -> inlineCB_actionPerformed());
        inlineCB.setFocusable(false);
        inlineCB.setPreferredSize(new Dimension(79, 22));
        inlineCB.setMinimumSize(new Dimension(60, 22));
        inlineCB.setMaximumSize(new Dimension(120, 22));
        inlineCB.setFont(new java.awt.Font("Dialog", 1, 10));
        inlineCB.setMaximumRowCount(12);
        inlineCB.setBackground(new Color(230, 230, 230));

        this.add(jScrollPane1, BorderLayout.CENTER);
        this.add(editToolbar, BorderLayout.NORTH);

        editToolbar.add(propsActionB, null);
        editToolbar.addSeparator();
        editToolbar.add(blockCB, null);

        editToolbar.addSeparator();
        editToolbar.add(inlineCB, null);
        editToolbar.addSeparator();
        editToolbar.add(boldActionB, null);
        editToolbar.add(italicActionB, null);
        editToolbar.add(underActionB, null);
        editToolbar.addSeparator();
        editToolbar.add(ulActionB, null);
        editToolbar.add(olActionB, null);
        editToolbar.addSeparator();
        editToolbar.add(lAlignActionB, null);
        editToolbar.add(cAlignActionB, null);
        editToolbar.add(rAlignActionB, null);
        editToolbar.addSeparator();
        editToolbar.add(imageActionB, null);
        editToolbar.add(tableActionB, null);
        editToolbar.add(linkActionB, null);
        editToolbar.addSeparator();
        editToolbar.add(hrActionB, null);
        editToolbar.add(brActionB, null);
        editToolbar.add(insCharActionB, null);

        jScrollPane1.getViewport().add(editor, null);

        toolsPanel.setTabPlacement(JTabbedPane.BOTTOM);
        toolsPanel.setFont(new Font("Dialog", 1, 10));

        //  editToolbar.add(jAlignActionB, null);

		/* KEY ACTIONS */

		/*
         * editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_MASK),
		 */

        editor.getKeymap().removeKeyStrokeBinding(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        editor.getKeymap().addActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                new ParaBreakAction());

		/*
         * editor.getKeymap().addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_B,
		 * KeyEvent.CTRL_MASK), boldAction);
		 * editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK),
		 * italicAction); editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK),
		 */

		/*
         * editor.getKeymap().addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
		 * KeyEvent.CTRL_MASK), undoAction);
		 * editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK +
		 * KeyEvent.SHIFT_MASK), redoAction);
		 * editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_MASK +
		 * KeyEvent.SHIFT_MASK), insertTableCellAction);
		 * editor.getKeymap().addActionForKeyStroke(
		 * KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_MASK),
		 */

        editor.getKeymap().removeKeyStrokeBinding(
                KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        editor.getKeymap().removeKeyStrokeBinding(
                KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
        editor.getKeymap().removeKeyStrokeBinding(
                KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));

        editor.getKeymap().addActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK),
                copyAction);
        editor.getKeymap().addActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK),
                pasteAction);
        editor.getKeymap().addActionForKeyStroke(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_V,
                        KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK),
                stylePasteAction);
        editor.getKeymap().addActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK),
                cutAction);

        editor.getKeymap().addActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK),
                findAction);

        editor.addMouseListener(new PopupListener());

        int currentFontSize = 4;
        document.getStyleSheet().setBaseFontSize(currentFontSize);
        this.requestFocusInWindow();
    }

    /**
     * Resets the undo manager.
     */
    private void resetUndoManager() {
        undo.discardAllEdits();
        undoAction.update();
        redoAction.update();
    }

    public String getContent() {
        try {
            return editor.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void boldActionB_actionPerformed(ActionEvent e) {
        if (!bold) {
            boldActionB.setBorder(border2);
        } else {
            boldActionB.setBorder(border1);
        }
        bold = !bold;
        boldActionB.setBorderPainted(bold);
        /*
         * SimpleAttributeSet attrs = new SimpleAttributeSet();
		 * attrs.addAttribute(StyleConstants.Bold, new Boolean(bold));
		 */
        new StyledEditorKit.BoldAction().actionPerformed(e);
    }

    private void italicActionB_actionPerformed(ActionEvent e) {
        if (!italic) {
            italicActionB.setBorder(border2);
        } else {
            italicActionB.setBorder(border1);
        }
        italic = !italic;
        italicActionB.setBorderPainted(italic);
        /*
         * SimpleAttributeSet attrs = new SimpleAttributeSet();
		 * attrs.addAttribute(StyleConstants.Italic, new Boolean(italic));
		 */
        new StyledEditorKit.ItalicAction().actionPerformed(e);
    }

    private void underActionB_actionPerformed(ActionEvent e) {
        if (!under) {
            underActionB.setBorder(border2);
        } else {
            underActionB.setBorder(border1);
        }
        under = !under;
        underActionB.setBorderPainted(under);

        new StyledEditorKit.UnderlineAction().actionPerformed(e);
    }

    private void editor_caretUpdate(CaretEvent e) {
        AttributeSet charattrs = null;
        if (editor.getCaretPosition() > 0)
            try {
                charattrs =
                        document
                                .getCharacterElement(editor.getCaretPosition() - 1)
                                .getAttributes();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        else
            charattrs =
                    document
                            .getCharacterElement(editor.getCaretPosition())
                            .getAttributes();

        if (charattrs != null) {
            if (charattrs
                    .containsAttribute(StyleConstants.Bold, Boolean.TRUE)) {
                boldActionB.setBorder(border2);
                bold = true;
            } else if (bold) {
                boldActionB.setBorder(border1);
                bold = false;
            }
        }
        boldActionB.setBorderPainted(bold);
        if (charattrs
                .containsAttribute(StyleConstants.Italic, Boolean.TRUE)) {
            italicActionB.setBorder(border2);
            italic = true;
        } else if (italic) {
            italicActionB.setBorder(border1);
            italic = false;
        }
        italicActionB.setBorderPainted(italic);
        if (charattrs
                .containsAttribute(StyleConstants.Underline, Boolean.TRUE)) {
            underActionB.setBorder(border2);
            under = true;
        } else if (under) {
            underActionB.setBorder(border1);
            under = false;
        }
        underActionB.setBorderPainted(under);
        /*
         * String iName = document
		 * .getCharacterElement(editor.getCaretPosition()) .getAttributes()
		 * .getAttribute(StyleConstants.NameAttribute) .toString()
		 */
        inlineCBEventsLock = true;
        inlineCB.setEnabled(!charattrs.isDefined(HTML.Tag.A));
        if (charattrs.isDefined(HTML.Tag.EM))
            inlineCB.setSelectedIndex(I_EM);
        else if (charattrs.isDefined(HTML.Tag.STRONG))
            inlineCB.setSelectedIndex(I_STRONG);
        else if (
                (charattrs.isDefined(HTML.Tag.CODE))
                        || (charattrs.isDefined(HTML.Tag.SAMP)))
            inlineCB.setSelectedIndex(I_CODE);
        else if (charattrs.isDefined(HTML.Tag.SUP))
            inlineCB.setSelectedIndex(I_SUPERSCRIPT);
        else if (charattrs.isDefined(HTML.Tag.SUB))
            inlineCB.setSelectedIndex(I_SUBSCRIPT);
        else if (charattrs.isDefined(HTML.Tag.CITE))
            inlineCB.setSelectedIndex(I_CITE);
        else if (charattrs.isDefined(HTML.Tag.FONT))
            inlineCB.setSelectedIndex(I_CUSTOM);
        else
            inlineCB.setSelectedIndex(I_NORMAL);
        inlineCBEventsLock = false;

        Element pEl = document.getParagraphElement(editor.getCaretPosition());
        String pName = pEl.getName().toUpperCase();
        blockCBEventsLock = true;
        if (pName.equals("P-IMPLIED"))
            pName = pEl.getParentElement().getName().toUpperCase();

        switch (pName) {
            case "P":
                blockCB.setSelectedIndex(T_P);
                break;
            case "H1":
                blockCB.setSelectedIndex(T_H1);
                break;
            case "H2":
                blockCB.setSelectedIndex(T_H2);
                break;
            case "H3":
                blockCB.setSelectedIndex(T_H3);
                break;
            case "H4":
                blockCB.setSelectedIndex(T_H4);
                break;
            case "H5":
                blockCB.setSelectedIndex(T_H5);
                break;
            case "H6":
                blockCB.setSelectedIndex(T_H6);
                break;
            case "PRE":
                blockCB.setSelectedIndex(T_PRE);
                break;
            case "BLOCKQUOTE":
                blockCB.setSelectedIndex(T_BLOCKQ);
                break;
            default:
                break;
        }
        blockCBEventsLock = false;
        this.insertTableCellAction.update();
        this.insertTableRowAction.update();
        /*
         * String ppName =
		 * document.getParagraphElement(editor.getCaretPosition()).getParentElement().getName().toUpperCase();
		 * System.out.print(ppName+"->"+pName+":");
		 *
		 * AbstractDocument.BranchElement pEl =
		 * (AbstractDocument.BranchElement)document.getParagraphElement(editor.getCaretPosition());
		 * Element el = pEl.positionToElement(editor.getCaretPosition());
		 * System.out.println(el.getAttributes().getAttribute(StyleConstants.NameAttribute)+",
		 * "+pEl.getElementCount()+"/"+el.getElementCount());
		 */

    }

    private void ulActionB_actionPerformed(ActionEvent e) {
        String parentname =
                document
                        .getParagraphElement(editor.getCaretPosition())
                        .getParentElement()
                        .getName();
        HTML.Tag parentTag = HTML.getTag(parentname);
        HTMLEditorKit.InsertHTMLTextAction ulAction =
                new HTMLEditorKit.InsertHTMLTextAction(
                        "insertUL",
                        "<ul><li></li></ul>",
                        parentTag,
                        HTML.Tag.UL);
        ulAction.actionPerformed(e);
        //removeIfEmpty(document.getParagraphElement(editor.getCaretPosition()-1));
        /*
         * Element pEl =
		 * document.getParagraphElement(editor.getCaretPosition());
		 * StringWriter sw = new StringWriter(); try { editorKit.write(sw,
		 * document, pEl.getStartOffset(),
		 * pEl.getEndOffset()-pEl.getStartOffset()); String copy =
		 * sw.toString(); String elName = pEl.getName(); copy =
		 * copy.substring(copy.indexOf(" <"+elName)); copy =
		 * copy.substring(0,copy.indexOf(" </"+elName)+elName.length()+3);
		 * document.setOuterHTML(pEl, " <ul><li> "+copy+" </li></ul> ");
		 * System.out.println(copy); } catch (Exception ex){
		 * ex.printStackTrace();
		 */

    }

    private void olActionB_actionPerformed(ActionEvent e) {
        String parentname =
                document
                        .getParagraphElement(editor.getCaretPosition())
                        .getParentElement()
                        .getName();
        HTML.Tag parentTag = HTML.getTag(parentname);
        HTMLEditorKit.InsertHTMLTextAction olAction =
                new HTMLEditorKit.InsertHTMLTextAction(
                        "insertOL",
                        "<ol><li></li></ol>",
                        parentTag,
                        HTML.Tag.OL);
        olAction.actionPerformed(e);
        //removeIfEmpty(document.getParagraphElement(editor.getCaretPosition()-1));
    }

    private void removeIfEmpty(Element elem) {
        if (elem.getEndOffset() - elem.getStartOffset() < 2) {
            try {
                document.remove(elem.getStartOffset(), elem.getEndOffset());
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }
    }

    private void lAlignActionB_actionPerformed(ActionEvent e) {
        HTMLEditorKit.AlignmentAction aa =
                new HTMLEditorKit.AlignmentAction(
                        "leftAlign",
                        StyleConstants.ALIGN_LEFT);
        aa.actionPerformed(e);
    }

    private void cAlignActionB_actionPerformed(ActionEvent e) {
        HTMLEditorKit.AlignmentAction aa =
                new HTMLEditorKit.AlignmentAction(
                        "centerAlign",
                        StyleConstants.ALIGN_CENTER);
        aa.actionPerformed(e);
    }

    private void rAlignActionB_actionPerformed(ActionEvent e) {
        HTMLEditorKit.AlignmentAction aa =
                new HTMLEditorKit.AlignmentAction(
                        "rightAlign",
                        StyleConstants.ALIGN_RIGHT);
        aa.actionPerformed(e);
    }

    public void jAlignActionB_actionPerformed(ActionEvent e) {
        HTMLEditorKit.AlignmentAction aa =
                new HTMLEditorKit.AlignmentAction(
                        "justifyAlign",
                        StyleConstants.ALIGN_JUSTIFIED);
        aa.actionPerformed(e);
    }

    public void insertHTML(String html, int location) {
        //assumes editor is already set to "text/html" type
        try {
            HTMLEditorKit kit = (HTMLEditorKit) editor.getEditorKit();
            Document doc = editor.getDocument();
            StringReader reader = new StringReader(html);
            kit.read(reader, doc, location);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void imageActionB_actionPerformed() {
        /*
         * JFileChooser chooser = new JFileChooser();
		 * chooser.setFileHidingEnabled(false); chooser.setDialogTitle("Choose
		 * image file"); chooser.setAcceptAllFileFilterUsed(false);
		 * chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		 * chooser.addChoosableFileFilter(new
		 * net.sf.memoranda.ui.htmleditor.filechooser.ImageFilter());
		 * chooser.setAccessory(new
		 * net.sf.memoranda.ui.htmleditor.filechooser.ImagePreview(chooser));
		 *
		 */
        ImageDialog dlg = new ImageDialog();
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = this.getSize();
        Point loc = this.getLocationOnScreen();
        dlg.setLocation(
                (frmSize.width - dlgSize.width) / 2 + loc.x,
                (frmSize.height - dlgSize.height) / 2 + loc.y);
        //dlg.setLocation(imageActionB.getLocationOnScreen());
        dlg.setModal(true);
        dlg.setVisible(true);

        if (!dlg.CANCELLED) {
            String urlString = dlg.fileField.getText();
            String path = urlString;
            if (imagesDir != null) {
                try {
                    URL url = new URL(urlString);
                    if (!url.getProtocol().startsWith("http"))
                        path = imagesDir + "/" + url.getFile();
                } catch (MalformedURLException e1) {
                }
            }
            try {
                String imgTag =
                        "<img src=\""
                                + path
                                + "\" alt=\""
                                + dlg.altField.getText()
                                + "\" ";
                String w = dlg.widthField.getText();
                try {
                    Integer.parseInt(w, 10);
                    imgTag += " width=\"" + w + "\" ";
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String h = dlg.heightField.getText();
                try {
                    Integer.parseInt(h, 10);
                    imgTag += " height=\"" + h + "\" ";
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String hs = dlg.hspaceField.getText();
                try {
                    Integer.parseInt(hs, 10);
                    imgTag += " hspace=\"" + hs + "\" ";
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String vs = dlg.vspaceField.getText();
                try {
                    Integer.parseInt(vs, 10);
                    imgTag += " vspace=\"" + vs + "\" ";
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String b = dlg.borderField.getText();
                try {
                    Integer.parseInt(b, 10);
                    imgTag += " border=\"" + b + "\" ";
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (dlg.alignCB.getSelectedIndex() > 0)
                    imgTag += " align=\""
                            + dlg.alignCB.getSelectedItem()
                            + "\" ";
                imgTag += ">";

                if (dlg.urlField.getText().length() > 0) {
                    imgTag =
                            "<a href=\""
                                    + dlg.urlField.getText()
                                    + "\">"
                                    + imgTag
                                    + "</a>";
                    if (editor.getCaretPosition() == document.getLength())
                        imgTag += "&nbsp;";
                    editorKit.insertHTML(
                            document,
                            editor.getCaretPosition(),
                            imgTag,
                            0,
                            0,
                            HTML.Tag.A);
                } else
                    editorKit.insertHTML(
                            document,
                            editor.getCaretPosition(),
                            imgTag,
                            0,
                            0,
                            HTML.Tag.IMG);

                //System.out.println(imgTag);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void tableActionB_actionPerformed() {
        TableDialog dlg = new TableDialog();
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = this.getSize();
        Point loc = this.getLocationOnScreen();
        dlg.setLocation(
                (frmSize.width - dlgSize.width) / 2 + loc.x,
                (frmSize.height - dlgSize.height) / 2 + loc.y);

        dlg.setModal(true);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        StringBuilder tableTag = new StringBuilder("<table ");
        String w = dlg.widthField.getText().trim();
        if (w.length() > 0) {
            tableTag.append(" width=\"");
            tableTag.append(w);
            tableTag.append("\" ");
        }
        String h = dlg.heightField.getText().trim();
        if (h.length() > 0) {
            tableTag.append(" height=\"");
            tableTag.append(h);
            tableTag.append("\" ");
        }
        String cp = dlg.cellpadding.getValue().toString();
        try {
            Integer.parseInt(cp, 10);
            tableTag.append(" cellpadding=\"");
            tableTag.append(cp);
            tableTag.append("\" ");
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        String cs = dlg.cellspacing.getValue().toString();
        try {
            Integer.parseInt(cs, 10);
            tableTag.append(" cellspacing=\"");
            tableTag.append(cs);
            tableTag.append("\" ");
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        String b = dlg.border.getValue().toString();
        try {
            Integer.parseInt(b, 10);
            tableTag.append(" border=\"");
            tableTag.append(b);
            tableTag.append("\" ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (dlg.alignCB.getSelectedIndex() > 0) {
            tableTag.append(" align=\"");
            tableTag.append(dlg.alignCB.getSelectedItem());
            tableTag.append("\" ");
        }
        if (dlg.vAlignCB.getSelectedIndex() > 0) {
            tableTag.append(" valign=\"");
            tableTag.append(dlg.vAlignCB.getSelectedItem());
            tableTag.append("\" ");
        }
        if (dlg.bgcolorField.getText().length() > 0) {
            tableTag.append(" bgcolor=\"");
            tableTag.append(dlg.bgcolorField.getText());
            tableTag.append("\" ");
        }
        tableTag.append(">");
        int cols = 1;
        int rows = 1;
        try {
            cols = (Integer) dlg.columns.getValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            rows = (Integer) dlg.rows.getValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for (int r = 0; r < rows; r++) {
            tableTag.append("<tr>");
            for (int c = 0; c < cols; c++) {
                tableTag.append("<td><p></p></td>");
            }
            tableTag.append("</tr>");
        }
        tableTag.append("</table>");
        String parentname = document
                .getParagraphElement(editor.getCaretPosition())
                .getParentElement()
                .getName();
        HTML.Tag parentTag = HTML.getTag(parentname);
        System.out.println(parentTag + ":\n" + tableTag);

        try {
            editorKit.insertHTML(
                    document,
                    editor.getCaretPosition(),
                    tableTag.toString(),
                    1,
                    0,
                    HTML.Tag.TABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void linkActionB_actionPerformed() {
        LinkDialog dlg = new LinkDialog();
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = this.getSize();
        Point loc = this.getLocationOnScreen();
        dlg.setLocation(
                (frmSize.width - dlgSize.width) / 2 + loc.x,
                (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        if (editor.getSelectedText() != null)
            dlg.txtDesc.setText(editor.getSelectedText());
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        String aTag = "<a";
        if (dlg.txtURL.getText().length() > 0)
            aTag += " href=\"" + dlg.txtURL.getText() + "\"";
        if (dlg.txtName.getText().length() > 0)
            aTag += " name=\"" + dlg.txtName.getText() + "\"";
        if (dlg.txtTitle.getText().length() > 0)
            aTag += " title=\"" + dlg.txtTitle.getText() + "\"";
        if (dlg.chkNewWin.isSelected())
            aTag += " target=\"_blank\"";
        aTag += ">" + dlg.txtDesc.getText() + "</a>";
        if (editor.getCaretPosition() == document.getLength())
            aTag += "&nbsp;";
        editor.replaceSelection("");
        try {
            editorKit.insertHTML(
                    document,
                    editor.getCaretPosition(),
                    aTag,
                    0,
                    0,
                    HTML.Tag.A);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setLinkProperties(
            Element el,
            String href,
            String target,
            String title,
            String name) {
        LinkDialog dlg = new LinkDialog();
        dlg.setLocation(linkActionB.getLocationOnScreen());
        dlg.setModal(true);
        //dlg.descPanel.setVisible(false);
        dlg.txtURL.setText(href);
        dlg.txtName.setText(name);
        dlg.txtTitle.setText(title);
        try {
            dlg.txtDesc.setText(
                    document.getText(
                            el.getStartOffset(),
                            el.getEndOffset() - el.getStartOffset()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        dlg.chkNewWin.setSelected(target.toUpperCase().equals("_BLANK"));
        dlg.header.setText(Local.getString("Hyperlink properties"));
        dlg.setTitle(Local.getString("Hyperlink properties"));
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        //String p = "";
        /*
         * SimpleAttributeSet attrs = new
		 * SimpleAttributeSet(el.getAttributes()); if
		 * (dlg.urlField.getText().length() >0) {
		 * attrs.addAttribute(HTML.Attribute.HREF, dlg.urlField.getText()); p +=
		 * "href="+dlg.urlField.getText()+" "; } if
		 * (dlg.nameField.getText().length() >0) {
		 * attrs.addAttribute(HTML.Attribute.NAME, dlg.nameField.getText()); p +=
		 * "name="+dlg.nameField.getText()+" "; } if
		 * (dlg.titleField.getText().length() >0) {
		 * attrs.addAttribute(HTML.Attribute.TITLE, dlg.titleField.getText());
		 * p += "title="+dlg.titleField.getText()+" "; } if
		 * (dlg.newWinChB.isSelected()) {
		 * attrs.addAttribute(HTML.Attribute.TARGET, "_blank"); p +=
		 * "target=_blank "; } attrs.addAttribute(StyleConstants.NameAttribute,
		 * "a"); attrs.addAttribute(HTML.Tag.A, p);
		 */
        String aTag = "<a";
        if (dlg.txtURL.getText().length() > 0)
            aTag += " href=\"" + dlg.txtURL.getText() + "\"";
        if (dlg.txtName.getText().length() > 0)
            aTag += " name=\"" + dlg.txtName.getText() + "\"";
        if (dlg.txtTitle.getText().length() > 0)
            aTag += " title=\"" + dlg.txtTitle.getText() + "\"";
        if (dlg.chkNewWin.isSelected())
            aTag += " target=\"_blank\"";
        aTag += ">" + dlg.txtDesc.getText() + "</a>";
        try {
            document.setOuterHTML(el, aTag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setImageProperties(
            Element el,
            String src,
            String alt,
            String width,
            String height,
            String hspace,
            String vspace,
            String border,
            String align) {
        ImageDialog dlg = new ImageDialog();
        dlg.setLocation(imageActionB.getLocationOnScreen());
        dlg.setModal(true);
        dlg.setTitle(Local.getString("Image properties"));
        dlg.fileField.setText(src);
        dlg.altField.setText(alt);
        dlg.widthField.setText(width);
        dlg.heightField.setText(height);
        dlg.hspaceField.setText(hspace);
        dlg.vspaceField.setText(vspace);
        dlg.borderField.setText(border);
        dlg.alignCB.setSelectedItem(align);
        dlg.updatePreview();
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        String imgTag =
                "<img src=\""
                        + dlg.fileField.getText()
                        + "\" alt=\""
                        + dlg.altField.getText()
                        + "\" ";
        String w = dlg.widthField.getText();
        try {
            Integer.parseInt(w, 10);
            imgTag += " width=\"" + w + "\" ";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String h = dlg.heightField.getText();
        try {
            Integer.parseInt(h, 10);
            imgTag += " height=\"" + h + "\" ";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String hs = dlg.hspaceField.getText();
        try {
            Integer.parseInt(hs, 10);
            imgTag += " hspace=\"" + hs + "\" ";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String vs = dlg.vspaceField.getText();
        try {
            Integer.parseInt(vs, 10);
            imgTag += " vspace=\"" + vs + "\" ";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String b = dlg.borderField.getText();
        try {
            Integer.parseInt(b, 10);
            imgTag += " border=\"" + b + "\" ";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (dlg.alignCB.getSelectedIndex() > 0)
            imgTag += " align=\"" + dlg.alignCB.getSelectedItem() + "\" ";
        imgTag += ">";
        if (dlg.urlField.getText().length() > 0) {
            imgTag =
                    "<a href=\"" + dlg.urlField.getText() + "\">" + imgTag + "</a>";
            if (editor.getCaretPosition() == document.getLength())
                imgTag += "&nbsp;";
        }
        try {
            document.setOuterHTML(el, imgTag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setElementProperties(Element el, String id, String cls, String sty) {
        ElementDialog dlg = new ElementDialog();
        //dlg.setLocation(linkActionB.getLocationOnScreen());
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = this.getSize();
        Point loc = this.getLocationOnScreen();
        dlg.setLocation(
                (frmSize.width - dlgSize.width) / 2 + loc.x,
                (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.setTitle(Local.getString("Object properties"));
        dlg.idField.setText(id);
        dlg.classField.setText(cls);
        dlg.styleField.setText(sty);
        // Uncommented, returns a simple p into the header... fix needed ?
        //dlg.header.setText(el.getName());
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        SimpleAttributeSet attrs = new SimpleAttributeSet(el.getAttributes());
        if (dlg.idField.getText().length() > 0)
            attrs.addAttribute(HTML.Attribute.ID, dlg.idField.getText());
        if (dlg.classField.getText().length() > 0)
            attrs.addAttribute(HTML.Attribute.CLASS, dlg.classField.getText());
        if (dlg.styleField.getText().length() > 0)
            attrs.addAttribute(HTML.Attribute.STYLE, dlg.styleField.getText());
        document.setParagraphAttributes(el.getStartOffset(), 0, attrs, true);
    }

    private void setTableProperties(Element td) {
        Element tr = td.getParentElement();
        Element table = tr.getParentElement();

        TdDialog dlg = new TdDialog();
        dlg.setLocation(editor.getLocationOnScreen());
        dlg.setModal(true);
        dlg.setTitle(Local.getString("Table properties"));

        /** **********PARSE ELEMENTS*********** */
        // TD***
        AttributeSet tda = td.getAttributes();
        if (tda.isDefined(HTML.Attribute.BGCOLOR)) {
            dlg.tdBgcolorField.setText(
                    tda.getAttribute(HTML.Attribute.BGCOLOR).toString());
            Util.setBgcolorField(dlg.tdBgcolorField);
        }
        if (tda.isDefined(HTML.Attribute.WIDTH))
            dlg.tdWidthField.setText(
                    tda.getAttribute(HTML.Attribute.WIDTH).toString());
        if (tda.isDefined(HTML.Attribute.HEIGHT))
            dlg.tdHeightField.setText(
                    tda.getAttribute(HTML.Attribute.HEIGHT).toString());
        if (tda.isDefined(HTML.Attribute.COLSPAN))
            try {
                Integer i = Integer.parseInt(tda.getAttribute(HTML.Attribute.COLSPAN).toString());
                dlg.tdColspan.setValue(i);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        if (tda.isDefined(HTML.Attribute.ROWSPAN))
            try {
                Integer i = Integer.parseInt(tda.getAttribute(HTML.Attribute.ROWSPAN).toString());
                dlg.tdRowspan.setValue(i);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        if (tda.isDefined(HTML.Attribute.ALIGN))
            dlg.tdAlignCB.setSelectedItem(
                    tda
                            .getAttribute(HTML.Attribute.ALIGN)
                            .toString()
                            .toLowerCase());
        if (tda.isDefined(HTML.Attribute.VALIGN))
            dlg.tdValignCB.setSelectedItem(
                    tda
                            .getAttribute(HTML.Attribute.VALIGN)
                            .toString()
                            .toLowerCase());
        dlg.tdNowrapChB.setSelected((tda.isDefined(HTML.Attribute.NOWRAP)));

        //TR ****
        AttributeSet tra = tr.getAttributes();
        if (tra.isDefined(HTML.Attribute.BGCOLOR)) {
            dlg.trBgcolorField.setText(
                    tra.getAttribute(HTML.Attribute.BGCOLOR).toString());
            Util.setBgcolorField(dlg.trBgcolorField);
        }
        if (tra.isDefined(HTML.Attribute.ALIGN))
            dlg.trAlignCB.setSelectedItem(
                    tra
                            .getAttribute(HTML.Attribute.ALIGN)
                            .toString()
                            .toLowerCase());
        if (tra.isDefined(HTML.Attribute.VALIGN))
            dlg.trValignCB.setSelectedItem(
                    tra
                            .getAttribute(HTML.Attribute.VALIGN)
                            .toString()
                            .toLowerCase());

        //TABLE ****
        AttributeSet ta = table.getAttributes();
        if (ta.isDefined(HTML.Attribute.BGCOLOR)) {
            dlg.bgcolorField.setText(
                    ta.getAttribute(HTML.Attribute.BGCOLOR).toString());
            Util.setBgcolorField(dlg.bgcolorField);
        }
        if (ta.isDefined(HTML.Attribute.WIDTH))
            dlg.widthField.setText(
                    ta.getAttribute(HTML.Attribute.WIDTH).toString());
        if (ta.isDefined(HTML.Attribute.HEIGHT))
            dlg.heightField.setText(
                    ta.getAttribute(HTML.Attribute.HEIGHT).toString());
        if (ta.isDefined(HTML.Attribute.ALIGN))
            dlg.alignCB.setSelectedItem(
                    ta.getAttribute(HTML.Attribute.ALIGN).toString().toLowerCase());
        if (ta.isDefined(HTML.Attribute.VALIGN))
            dlg.vAlignCB.setSelectedItem(
                    ta
                            .getAttribute(HTML.Attribute.VALIGN)
                            .toString()
                            .toLowerCase());
        if (ta.isDefined(HTML.Attribute.CELLPADDING))
            try {
                Integer i = Integer.parseInt(ta.getAttribute(HTML.Attribute.CELLPADDING).toString());
                dlg.cellpadding.setValue(i);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        if (ta.isDefined(HTML.Attribute.CELLSPACING))
            try {
                Integer i = Integer.parseInt(ta.getAttribute(HTML.Attribute.CELLSPACING).toString());
                dlg.cellspacing.setValue(i);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        if (ta.isDefined(HTML.Attribute.BORDER))
            try {
                Integer i = Integer.parseInt(ta.getAttribute(HTML.Attribute.BORDER).toString());
                dlg.border.setValue(i);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        /** ****************************** */

        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;

        /** ******** SET ATTRIBUTES ********* */
        // TD***
        StringBuilder tdTag = new StringBuilder("<td");
        if (dlg.tdBgcolorField.getText().length() > 0) {
            tdTag.append(" bgcolor=\"");
            tdTag.append(dlg.tdBgcolorField.getText());
            tdTag.append("\"");
        }

        if (dlg.tdWidthField.getText().length() > 0) {
            tdTag.append(" width=\"");
            tdTag.append(dlg.tdWidthField.getText());
            tdTag.append("\"");
        }

        if (dlg.tdHeightField.getText().length() > 0) {
            tdTag.append(" height=\"");
            tdTag.append(dlg.tdHeightField.getText());
            tdTag.append("\"");
        }

        if (!dlg.tdColspan.getValue().toString().equals("0")) {
            tdTag.append(" colspan=\"");
            tdTag.append(dlg.tdColspan.getValue().toString());
            tdTag.append("\"");
        }

        if (!dlg.tdRowspan.getValue().toString().equals("0")) {
            tdTag.append(" rowspan=\"");
            tdTag.append(dlg.tdRowspan.getValue().toString());
            tdTag.append("\"");
        }

        if (dlg.tdAlignCB.getSelectedItem().toString().length() > 0) {
            tdTag.append(" align=\"");
            tdTag.append(dlg.tdAlignCB.getSelectedItem().toString());
            tdTag.append("\"");
        }

        if (dlg.tdValignCB.getSelectedItem().toString().length() > 0) {
            tdTag.append(" valign=\"");
            tdTag.append(dlg.tdValignCB.getSelectedItem().toString());
            tdTag.append("\"");
        }

        if (dlg.tdNowrapChB.isSelected()) {
            tdTag.append(" nowrap");
        }

        tdTag.append(">");

        //TR***
        String trTag = "<tr";
        if (dlg.trBgcolorField.getText().length() > 0)
            trTag += " bgcolor=\"" + dlg.trBgcolorField.getText() + "\"";

        if (dlg.trAlignCB.getSelectedItem().toString().length() > 0)
            trTag += " align=\""
                    + dlg.trAlignCB.getSelectedItem().toString()
                    + "\"";

        if (dlg.trValignCB.getSelectedItem().toString().length() > 0)
            trTag += " valign=\""
                    + dlg.trValignCB.getSelectedItem().toString()
                    + "\"";

        trTag += ">";

        //TABLE ***
        String tTag = "<table";
        if (dlg.bgcolorField.getText().length() > 0)
            tTag += " bgcolor=\"" + dlg.bgcolorField.getText() + "\"";

        if (dlg.widthField.getText().length() > 0)
            tTag += " width=\"" + dlg.widthField.getText() + "\"";

        if (dlg.heightField.getText().length() > 0)
            tTag += " height=\"" + dlg.heightField.getText() + "\"";

        tTag += " cellpadding=\""
                + dlg.cellpadding.getValue().toString()
                + "\"";

        tTag += " cellspacing=\""
                + dlg.cellspacing.getValue().toString()
                + "\"";

        tTag += " border=\"" + dlg.border.getValue().toString() + "\"";

        if (dlg.alignCB.getSelectedItem().toString().length() > 0)
            tTag += " align=\""
                    + dlg.alignCB.getSelectedItem().toString()
                    + "\"";

        if (dlg.vAlignCB.getSelectedItem().toString().length() > 0)
            tTag += " valign=\""
                    + dlg.vAlignCB.getSelectedItem().toString()
                    + "\"";

        tTag += ">";

        /** ****************************** */

        /** ** UPDATE TABLE ***** */
        try {
            StringWriter sw = new StringWriter();
            String copy;

            editorKit.write(
                    sw,
                    document,
                    td.getStartOffset(),
                    td.getEndOffset() - td.getStartOffset());
            copy = sw.toString();
            copy = copy.split("<td(.*?)>")[1];
            copy = copy.split("</td>")[0];

            tdTag.append(copy);
            tdTag.append("</td>");
            document.setOuterHTML(td, tdTag.toString());

            sw = new StringWriter();
            editorKit.write(
                    sw,
                    document,
                    tr.getStartOffset(),
                    tr.getEndOffset() - tr.getStartOffset());
            copy = sw.toString();
            copy = copy.split("<tr(.*?)>")[1];
            copy = copy.split("</tr>")[0];
            document.setOuterHTML(tr, trTag + copy + "</tr>");

            sw = new StringWriter();
            editorKit.write(
                    sw,
                    document,
                    table.getStartOffset(),
                    table.getEndOffset() - table.getStartOffset());
            copy = sw.toString();
            copy = copy.split("<table(.*?)>")[1];
            copy = copy.split("</table>")[0];
            document.setOuterHTML(table, tTag + copy + "</table>");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void blockCB_actionPerformed() {
        if (blockCBEventsLock) {
            return;
        }
        int sel = blockCB.getSelectedIndex();

        HTML.Tag tag = null;

        switch (sel) {
            case T_P:
                tag = HTML.Tag.P;
                break;
            case T_H1:
                tag = HTML.Tag.H1;
                break;
            case T_H2:
                tag = HTML.Tag.H2;
                break;
            case T_H3:
                tag = HTML.Tag.H3;
                break;
            case T_H4:
                tag = HTML.Tag.H4;
                break;
            case T_H5:
                tag = HTML.Tag.H5;
                break;
            case T_H6:
                tag = HTML.Tag.H6;
                break;
            case T_PRE:
                tag = HTML.Tag.PRE;
                break;
            case T_BLOCKQ:
                tag = HTML.Tag.BLOCKQUOTE;
                break;
            default:
                break;
        }

        Element el = document.getParagraphElement(editor.getCaretPosition());
        if (el.getName().toUpperCase().equals("P-IMPLIED")) {
            Element pEl = el.getParentElement();
            String pElName = pEl.getName();
            String newName = null;
            if (tag != null) {
                newName = tag.toString();
            }
            StringWriter sw = new StringWriter();
            String copy;
            try {
                editorKit.write(
                        sw,
                        document,
                        el.getStartOffset(),
                        el.getEndOffset() - el.getStartOffset());
                copy = sw.toString();
                copy = copy.split("<" + pElName + "(.*?)>")[1];
                copy = copy.split("</" + pElName + ">")[0];
                document.setOuterHTML(
                        pEl,
                        "<" + newName + ">" + copy + "</" + newName + ">");
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        SimpleAttributeSet attrs = new SimpleAttributeSet(el.getAttributes());
        attrs.addAttribute(StyleConstants.NameAttribute, tag);
        if (editor.getSelectionEnd() - editor.getSelectionStart() > 0)
            document.setParagraphAttributes(
                    editor.getSelectionStart(),
                    editor.getSelectionEnd() - editor.getSelectionStart(),
                    attrs,
                    true);
        else
            document.setParagraphAttributes(
                    editor.getCaretPosition(),
                    0,
                    attrs,
                    true);

    }

    private void propsActionB_actionPerformed() {

        AbstractDocument.BranchElement pEl =
                (AbstractDocument.BranchElement) document.getParagraphElement(
                        editor.getCaretPosition());
        System.out.println("--------------");
        System.out.println(
                pEl.getName() + "<-" + pEl.getParentElement().getName());
        Element el = pEl.positionToElement(editor.getCaretPosition());
        System.out.println(
                ":"
                        + el.getAttributes().getAttribute(StyleConstants.NameAttribute));
        AttributeSet attrs = el.getAttributes();

        String elName =
                attrs
                        .getAttribute(StyleConstants.NameAttribute)
                        .toString()
                        .toUpperCase();
        if (elName.equals("IMG")) {
            String src = "",
                    alt = "",
                    width = "",
                    height = "",
                    hspace = "",
                    vspace = "",
                    border = "",
                    align = "";
            if (attrs.isDefined(HTML.Attribute.SRC))
                src = attrs.getAttribute(HTML.Attribute.SRC).toString();
            if (attrs.isDefined(HTML.Attribute.ALT))
                alt = attrs.getAttribute(HTML.Attribute.ALT).toString();
            if (attrs.isDefined(HTML.Attribute.WIDTH))
                width = attrs.getAttribute(HTML.Attribute.WIDTH).toString();
            if (attrs.isDefined(HTML.Attribute.HEIGHT))
                height = attrs.getAttribute(HTML.Attribute.HEIGHT).toString();
            if (attrs.isDefined(HTML.Attribute.HSPACE))
                hspace = attrs.getAttribute(HTML.Attribute.HSPACE).toString();
            if (attrs.isDefined(HTML.Attribute.VSPACE))
                vspace = attrs.getAttribute(HTML.Attribute.VSPACE).toString();
            if (attrs.isDefined(HTML.Attribute.BORDER))
                border = attrs.getAttribute(HTML.Attribute.BORDER).toString();
            if (attrs.isDefined(HTML.Attribute.ALIGN))
                align = attrs.getAttribute(HTML.Attribute.ALIGN).toString();
            setImageProperties(
                    el,
                    src,
                    alt,
                    width,
                    height,
                    hspace,
                    vspace,
                    border,
                    align);
            return;
        }

        Object k;
        for (Enumeration<?> en = attrs.getAttributeNames();
             en.hasMoreElements();
                ) {
            k = en.nextElement();
            if (k.toString().equals("a")) {
                String[] param = attrs.getAttribute(k).toString().split(" ");
                String href = "", target = "", title = "", name = "";
                for (String aParam : param)
                    if (aParam.startsWith("href="))
                        href = aParam.split("=")[1];
                    else if (aParam.startsWith("title="))
                        title = aParam.split("=")[1];
                    else if (aParam.startsWith("target="))
                        target = aParam.split("=")[1];
                    else if (aParam.startsWith("name="))
                        name = aParam.split("=")[1];
                setLinkProperties(el, href, target, title, name);
                return;
            }
            System.out.println(k + " = '" + attrs.getAttribute(k) + "'");
        }

        if (pEl.getParentElement().getName().toUpperCase().equals("TD")) {
            setTableProperties(pEl.getParentElement());
            return;
        }

        String id = "", cls = "", sty = "";
        AttributeSet pa = pEl.getAttributes();
        if (pa.getAttribute(HTML.Attribute.ID) != null)
            id = pa.getAttribute(HTML.Attribute.ID).toString();
        if (pa.getAttribute(HTML.Attribute.CLASS) != null)
            cls = pa.getAttribute(HTML.Attribute.CLASS).toString();
        if (pa.getAttribute(HTML.Attribute.STYLE) != null)
            sty = pa.getAttribute(HTML.Attribute.STYLE).toString();
        setElementProperties(pEl, id, cls, sty);

    }

    private String setFontProperties(Element el, String text) {
        FontDialog dlg = new FontDialog();
        Dimension dlgSize = dlg.getSize();
        Dimension frmSize = this.getSize();
        Point loc = this.getLocationOnScreen();
        dlg.setLocation(
                (frmSize.width - dlgSize.width) / 2 + loc.x,
                (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        AttributeSet ea = el.getAttributes();
        if (ea.isDefined(StyleConstants.FontFamily))
            dlg.fontFamilyCB.setSelectedItem(
                    ea.getAttribute(StyleConstants.FontFamily).toString());
        if (ea.isDefined(HTML.Tag.FONT)) {
            String s = ea.getAttribute(HTML.Tag.FONT).toString();
            String size =
                    s.substring(s.indexOf("size=") + 5, s.indexOf("size=") + 6);
            dlg.fontSizeCB.setSelectedItem(size);
        }
        if (ea.isDefined(StyleConstants.Foreground)) {
            dlg.colorField.setText(
                    Util.encodeColor(
                            (Color) ea.getAttribute(StyleConstants.Foreground)));
            Util.setColorField(dlg.colorField);
            dlg.sample.setForeground(
                    (Color) ea.getAttribute(StyleConstants.Foreground));
        }
        if (text != null)
            dlg.sample.setText(text);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return null;
        String attrs = "";
        if (dlg.fontSizeCB.getSelectedIndex() > 0)
            attrs += "size=\"" + dlg.fontSizeCB.getSelectedItem() + "\"";
        if (dlg.fontFamilyCB.getSelectedIndex() > 0)
            attrs += "face=\"" + dlg.fontFamilyCB.getSelectedItem() + "\"";
        if (dlg.colorField.getText().length() > 0)
            attrs += "color=\"" + dlg.colorField.getText() + "\"";
        if (attrs.length() > 0)
            return " " + attrs;
        else
            return null;
    }

    private void inlineCB_actionPerformed() {
        if (inlineCBEventsLock)
            return;
        int sel = inlineCB.getSelectedIndex();
        if (sel == I_NORMAL) {
            Element el =
                    document.getCharacterElement(editor.getCaretPosition());
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            attrs.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
            if (editor.getSelectionEnd() > editor.getSelectionStart())
                document.setCharacterAttributes(
                        editor.getSelectionStart(),
                        editor.getSelectionEnd() - editor.getSelectionStart(),
                        attrs,
                        true);
            else
                document.setCharacterAttributes(
                        el.getStartOffset(),
                        el.getEndOffset() - el.getStartOffset(),
                        attrs,
                        true);
            return;
        }
        String text = "&nbsp;";
        if (editor.getSelectedText() != null)
            text = editor.getSelectedText();
        String tag = "";
        String att = "";
        switch (sel) {
            case I_EM:
                tag = "em";
                break;
            case I_STRONG:
                tag = "strong";
                break;
            case I_CODE:
                tag = "code";
                break;
            case I_SUPERSCRIPT:
                tag = "sup";
                break;
            case I_SUBSCRIPT:
                tag = "sub";
                break;
            case I_CITE:
                tag = "cite";
                break;
            case I_CUSTOM:
                tag = "font";
                att =
                        setFontProperties(
                                document.getCharacterElement(editor.getCaretPosition()),
                                editor.getSelectedText());
                if (att == null)
                    return;
                break;
        }
        String html = "<" + tag + att + ">" + text + "</" + tag + ">";
        if (editor.getCaretPosition() == document.getLength())
            html += "&nbsp;";
        editor.replaceSelection("");
        try {
            editorKit.insertHTML(
                    document,
                    editor.getCaretPosition(),
                    html,
                    0,
                    0,
                    HTML.getTag(tag));
            if (editor.getCaretPosition() == document.getLength())
                editor.setCaretPosition(editor.getCaretPosition() - 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void doZoom() {
    }

    public void setDocument(Document doc) {
        this.document = (HTMLDocument) doc;
        initEditor();
    }

    public void initEditor() {
        editor.setDocument(document);
        resetUndoManager();
        document.addUndoableEditListener(undoHandler);
        editor.scrollRectToVisible(new Rectangle(0, 0, 0, 0));
        editor.setCaretPosition(0);
    }

    public boolean isDocumentChanged() {
        return undo.canUndo();
    }

    public void setStyleSheet(Reader r) {
        StyleSheet css = new StyleSheet();
        try {
            css.loadRules(r, null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        editorKit.setStyleSheet(css);
    }

    private void doFind() {
        FindDialog dlg = new FindDialog();
        //dlg.setLocation(linkActionB.getLocationOnScreen());
        Dimension dlgSize = dlg.getSize();
        //dlg.setSize(400, 300);
        Dimension frmSize = this.getSize();
        Point loc = this.getLocationOnScreen();
        dlg.setLocation(
                (frmSize.width - dlgSize.width) / 2 + loc.x,
                (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        if (editor.getSelectedText() != null)
            dlg.txtSearch.setText(editor.getSelectedText());
        else if (Context.get("LAST_SEARCHED_WORD") != null)
            dlg.txtSearch.setText(Context.get("LAST_SEARCHED_WORD").toString());
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        Context.put("LAST_SEARCHED_WORD", dlg.txtSearch.getText());
        String repl = null;
        if (dlg.chkReplace.isSelected())
            repl = dlg.txtReplace.getText();
        Finder finder =
                new Finder(
                        this,
                        dlg.txtSearch.getText(),
                        dlg.chkWholeWord.isSelected(),
                        dlg.chkCaseSens.isSelected(),
                        dlg.chkRegExp.isSelected(),
                        repl);
        finder.start();

    }

    abstract class HTMLEditorAction extends AbstractAction {
        HTMLEditorAction(String name, ImageIcon icon) {
            super(name, icon);
            super.putValue(Action.SHORT_DESCRIPTION, name);
        }

        HTMLEditorAction(String name) {
            super(name);
            super.putValue(Action.SHORT_DESCRIPTION, name);
        }
    }

    class PopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                JPopupMenu popupMenu = new JPopupMenu();
                popupMenu.setFocusable(false);

                popupMenu.add(jMenuItemUndo);
                popupMenu.add(jMenuItemRedo);
                popupMenu.addSeparator();
                popupMenu.add(jMenuItemCut);
                popupMenu.add(jMenuItemCopy);
                popupMenu.add(jMenuItemPaste);
                popupMenu.addSeparator();
                if (jMenuItemInsCell.getAction().isEnabled()) {
                    popupMenu.add(jMenuItemInsCell);
                    jMenuItemInsCell.setEnabled(true);
                    popupMenu.add(jMenuItemInsRow);
                    jMenuItemInsRow.setEnabled(true);
                    popupMenu.addSeparator();
                }
                popupMenu.add(jMenuItemProp);
                popupMenu.show(e.getComponent(), e.getX(), e.getY());

            }
        }
    }

    private class UndoHandler implements UndoableEditListener {

        /**
         * Messaged when the Document has created an edit, the edit is added to
         * <code>undo</code>, an instance of UndoManager.
         */
        public void undoableEditHappened(UndoableEditEvent e) {
            undo.addEdit(e.getEdit());
            undoAction.update();
            redoAction.update();
        }
    }

    class UndoAction extends AbstractAction {
        public UndoAction() {
            super(Local.getString("Undo"));
            setEnabled(false);
            putValue(
                    Action.SMALL_ICON,
                    new ImageIcon(net.sf.memoranda.ui.htmleditor.HTMLEditor.class.getResource("resources/icons/undo16.png")));
            putValue(
                    Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
            }
            update();
            redoAction.update();
        }

        void update() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(
                        Action.SHORT_DESCRIPTION,
                        undo.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.SHORT_DESCRIPTION, Local.getString("Undo"));
            }
        }
    }

    class RedoAction extends AbstractAction {
        public RedoAction() {
            super(Local.getString("Redo"));
            setEnabled(false);
            putValue(
                    Action.SMALL_ICON,
                    new ImageIcon(HTMLEditor.class.getResource("resources/icons/redo16.png")));
            putValue(
                    Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(
                            KeyEvent.VK_Z,
                            KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo.redo();
            } catch (CannotRedoException ex) {
                System.out.println("Unable to redo: " + ex);
                ex.printStackTrace();
            }
            update();
            undoAction.update();
        }

        void update() {
            if (undo.canRedo()) {
                setEnabled(true);
                putValue(
                        Action.SHORT_DESCRIPTION,
                        undo.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.SHORT_DESCRIPTION, Local.getString("Redo"));
            }
        }
    }

    public class BlockAction extends AbstractAction {
        final int _type;

        public BlockAction(int type) {
            super("");
            _type = type;
        }

        public void actionPerformed(ActionEvent e) {
            blockCB.setSelectedIndex(_type);
        }
    }

    public class InlineAction extends AbstractAction {
        final int _type;

        public InlineAction(int type) {
            super("");
            _type = type;
        }

        public void actionPerformed(ActionEvent e) {
            inlineCB.setSelectedIndex(_type);
        }
    }

    class ParaBreakAction extends AbstractAction {
        ParaBreakAction() {
            super("ParaBreakAction");
        }

        public void actionPerformed(ActionEvent e) {

            Element elem =
                    document.getParagraphElement(editor.getCaretPosition());
            String elName = elem.getName().toUpperCase();
            String parentname = elem.getParentElement().getName();
            HTML.Tag parentTag = HTML.getTag(parentname);
            if (parentname.toUpperCase().equals("P-IMPLIED"))
                parentTag = HTML.Tag.IMPLIED;
            if (parentname.toLowerCase().equals("li")) {
                if (elem.getEndOffset() - elem.getStartOffset() > 1) {
                    try {
                        document.insertAfterEnd(
                                elem.getParentElement(),
                                "<li></li>");
                        editor.setCaretPosition(
                                elem.getParentElement().getEndOffset());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        document.remove(editor.getCaretPosition(), 1);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    Element listParentElement =
                            elem
                                    .getParentElement()
                                    .getParentElement()
                                    .getParentElement();
                    HTML.Tag listParentTag =
                            HTML.getTag(listParentElement.getName());
                    String listParentTagName = listParentTag.toString();
                    if (listParentTagName.toLowerCase().equals("li")) {
                        Element listAncEl =
                                listParentElement.getParentElement();
                        try {
                            editorKit.insertHTML(
                                    document,
                                    listAncEl.getEndOffset(),
                                    "<li><p></p></li>",
                                    3,
                                    0,
                                    HTML.Tag.LI);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        HTMLEditorKit.InsertHTMLTextAction pAction =
                                new HTMLEditorKit.InsertHTMLTextAction(
                                        "insertP",
                                        "<p></p>",
                                        listParentTag,
                                        HTML.Tag.P);
                        pAction.actionPerformed(e);
                    }
                }
            } else if (
                    (elName.equals("PRE"))
                            || (elName.equals("ADDRESS"))
                            || (elName.equals("BLOCKQUOTE"))) {
                if (editor.getCaretPosition() > 0)
                    removeIfEmpty(
                            document.getParagraphElement(
                                    editor.getCaretPosition() - 1));
                HTMLEditorKit.InsertHTMLTextAction pAction =
                        new HTMLEditorKit.InsertHTMLTextAction(
                                "insertP",
                                "<p></p>",
                                parentTag,
                                HTML.Tag.P);
                System.out.println("PRE");
                pAction.actionPerformed(e);
            } else if (elName.equals("P-IMPLIED")) {
                try {
                    System.out.println("IMPLIED");
                    document.insertAfterEnd(elem.getParentElement(), "<p></p>");
                    editor.setCaretPosition(
                            elem.getParentElement().getEndOffset());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                editor.replaceSelection("\n");
                editorKit.getInputAttributes().removeAttribute(
                        HTML.Attribute.ID);
                editorKit.getInputAttributes().removeAttribute(
                        HTML.Attribute.CLASS);
            }
        }
    }

    class BreakAction extends AbstractAction {
        BreakAction() {
            super(
                    Local.getString("Insert break"),
                    new ImageIcon(net.sf.memoranda.ui.htmleditor.HTMLEditor.class.getResource("resources/icons/break.png")));
        }

        public void actionPerformed(ActionEvent e) {
            String elName =
                    document
                            .getParagraphElement(editor.getCaretPosition())
                            .getName();

            HTML.Tag tag = HTML.getTag(elName);
            if (elName.toUpperCase().equals("P-IMPLIED"))
                tag = HTML.Tag.IMPLIED;

            HTMLEditorKit.InsertHTMLTextAction hta =
                    new HTMLEditorKit.InsertHTMLTextAction(
                            "insertBR",
                            "<br>",
                            tag,
                            HTML.Tag.BR);
            hta.actionPerformed(e);

            //insertHTML("<br>",editor.getCaretPosition());

        }
    }

    class InsertTableRowAction extends AbstractAction {
        InsertTableRowAction() {
            super(Local.getString("Insert table row"));
            this.putValue(
                    Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_MASK));
        }

        public void actionPerformed(ActionEvent e) {
            StringBuilder build = new StringBuilder();
            build.append("<tr>");
            Element tr = document
                    .getParagraphElement(editor.getCaretPosition())
                    .getParentElement()
                    .getParentElement();
            for (int i = 0; i < tr.getElementCount(); i++) {
                if (tr.getElement(i).getName().toUpperCase().equals("TD")) {
                    build.append("<td><p></p></td>");
                }
            }
            build.append("</tr>");

            String trTag = build.toString();

            try {
                document.insertAfterEnd(tr, trTag);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public boolean isEnabled() {
            return document != null && document.getParagraphElement(editor.getCaretPosition())
                    .getParentElement().getName().toUpperCase().equals("TD");
        }

        public void update() {
            this.setEnabled(isEnabled());
        }
    }

    class InsertTableCellAction extends AbstractAction {
        InsertTableCellAction() {
            super(Local.getString("Insert table cell"));
            this.putValue(
                    Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(
                            KeyEvent.VK_ENTER,
                            KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
        }

        public void actionPerformed(ActionEvent e) {
            String tdTag = "<td><p></p></td>";
            Element td =
                    document
                            .getParagraphElement(editor.getCaretPosition())
                            .getParentElement();
            try {
                document.insertAfterEnd(td, tdTag);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public boolean isEnabled() {
            return document != null && document.getParagraphElement(editor.getCaretPosition())
                    .getParentElement().getName().toUpperCase().equals("TD");
        }

        public void update() {
            this.setEnabled(isEnabled());
        }
    }
}