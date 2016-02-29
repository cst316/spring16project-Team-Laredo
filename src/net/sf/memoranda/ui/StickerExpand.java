package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class StickerExpand extends JDialog {
    final JLabel stickerText = new JLabel();
    private final String txt;
    private final Color backGroundColor;
    private final Color foreGroundColor;
    private final JPanel panel1 = new JPanel();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final BorderLayout borderLayout2 = new BorderLayout();
    private final JPanel bottomPanel = new JPanel();
    private final JPanel topPanel = new JPanel();
    private final JScrollPane jScrollPane1 = new JScrollPane();
    private final JPanel jPanel1 = new JPanel();
    private final BorderLayout borderLayout3 = new BorderLayout();
    public boolean CANCELLED = true;
    JLabel header = new JLabel();
    JLabel jLabel1 = new JLabel();

    public StickerExpand(Frame frame, String txt, String backcolor, String fontcolor, String priority) {
        super(frame, Local.getString("Sticker") + " [" + priority + "]", true);
        this.txt = txt;
        this.backGroundColor = Color.decode(backcolor);
        this.foreGroundColor = Color.decode(fontcolor);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    private void jbInit() {
        Border border1 = BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(
                        Color.white,
                        new Color(156, 156, 158)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Border border2 = BorderFactory.createEmptyBorder(5, 0, 5, 0);
        panel1.setLayout(borderLayout1);
        this.getContentPane().setLayout(borderLayout2);

        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
        topPanel.setBackground(Color.WHITE);

        jPanel1.setLayout(borderLayout3);
        panel1.setBorder(border1);
        jPanel1.setBorder(border2);

        getContentPane().add(panel1, BorderLayout.CENTER);
        panel1.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(stickerText, null);
        panel1.add(jPanel1, BorderLayout.SOUTH);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);

        stickerText.setText(txt);
        stickerText.setOpaque(true);
        stickerText.setBackground(backGroundColor);
        stickerText.setForeground(foreGroundColor);
    }
}
