package net.sf.memoranda.ui.htmleditor;

import net.sf.memoranda.ui.htmleditor.util.Local;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 *
 * @author unascribed
 * @version 1.0
 */

class TdDialog extends JDialog {
    final JTextField tdHeightField = new JTextField();
    final JTextField tdWidthField = new JTextField();
    final JTextField tdBgcolorField = new JTextField();
    final JCheckBox tdNowrapChB = new JCheckBox();
    final JTextField trBgcolorField = new JTextField();
    final JSpinner tdColspan = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
    final JSpinner tdRowspan = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
    final JSpinner cellpadding = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
    final JSpinner border = new JSpinner(new SpinnerNumberModel(1, 0, 999, 1));
    final JTextField bgcolorField = new JTextField();
    final JTextField heightField = new JTextField();
    final JSpinner cellspacing = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
    final JTextField widthField = new JTextField();
    private final JPanel panel1 = new JPanel();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JPanel buttonsPanel = new JPanel();
    private final JButton cancelB = new JButton();
    private final JButton okB = new JButton();
    private final FlowLayout flowLayout1 = new FlowLayout();
    private final String[] aligns = {"", "left", "center", "right"};
    final JComboBox<? extends String> tdAlignCB = new JComboBox<>(aligns);
    final JComboBox<? extends String> trAlignCB = new JComboBox<>(aligns);
    final JComboBox<? extends String> alignCB = new JComboBox<>(aligns);
    private final String[] valigns = {"", "top", "center", "bottom"};
    final JComboBox<? extends String> vAlignCB = new JComboBox<>(valigns);
    private final String[] tdvaligns = {"", "top", "middle", "bottom", "baseline"};
    final JComboBox<? extends String> tdValignCB = new JComboBox<>(tdvaligns);
    final JComboBox<? extends String> trValignCB = new JComboBox<>(tdvaligns);
    private final JPanel headerPanel = new JPanel();
    private final JLabel header = new JLabel();
    private final FlowLayout flowLayout7 = new FlowLayout();
    private final JTabbedPane jTabbedPane1 = new JTabbedPane();
    private final JLabel jLabel4 = new JLabel();
    private final FlowLayout flowLayout5 = new FlowLayout();
    private final FlowLayout flowLayout2 = new FlowLayout();
    private final JLabel jLabel7 = new JLabel();
    private final FlowLayout flowLayout6 = new FlowLayout();
    private final JPanel jPanel7 = new JPanel();
    private final JPanel tdPanel = new JPanel();
    private final JPanel jPanel6 = new JPanel();
    private final JLabel jLabel6 = new JLabel();
    private final GridLayout gridLayout2 = new GridLayout();
    private final JPanel jPanel5 = new JPanel();
    private final JLabel jLabel9 = new JLabel();
    private final JLabel jLabel5 = new JLabel();
    private final JPanel trPanel = new JPanel();
    private final GridLayout gridLayout4 = new GridLayout();
    private final JLabel jLabel20 = new JLabel();
    private final FlowLayout flowLayout12 = new FlowLayout();
    private final JPanel jPanel14 = new JPanel();
    private final JLabel jLabel21 = new JLabel();
    private final JButton tdBgcolorB = new JButton();
    private final JLabel jLabel22 = new JLabel();
    private final FlowLayout flowLayout10 = new FlowLayout();
    private final JLabel jLabel16 = new JLabel();
    private final JPanel jPanel11 = new JPanel();
    private final JLabel jLabel23 = new JLabel();
    private final JPanel jPanel15 = new JPanel();
    private final FlowLayout flowLayout13 = new FlowLayout();
    private final JButton trBgcolorB = new JButton();
    private final JLabel jLabel8 = new JLabel();
    private final JLabel jLabel12 = new JLabel();
    private final FlowLayout flowLayout8 = new FlowLayout();
    private final FlowLayout flowLayout4 = new FlowLayout();
    private final JLabel jLabel24 = new JLabel();
    private final JPanel tablePanel = new JPanel();
    private final GridLayout gridLayout3 = new GridLayout();
    private final JPanel jPanel10 = new JPanel();
    private final JPanel jPanel12 = new JPanel();
    private final JLabel jLabel13 = new JLabel();
    private final JLabel jLabel17 = new JLabel();
    private final JLabel jLabel25 = new JLabel();
    private final JButton bgColorB = new JButton();
    private final FlowLayout flowLayout11 = new FlowLayout();
    private final JPanel jPanel13 = new JPanel();
    private final JLabel jLabel14 = new JLabel();
    private final JPanel jPanel9 = new JPanel();
    private final FlowLayout flowLayout9 = new FlowLayout();
    private final JLabel jLabel15 = new JLabel();
    public boolean CANCELLED = false;

    public TdDialog() {
        super((Frame) null, Local.getString("Table properties"), true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void jbInit() {
        Border border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border border2 = BorderFactory.createEtchedBorder(Color.white, new Color(142, 142, 142));
        Border border4 = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        Component component1 = Box.createHorizontalStrut(8);
        Component component2 = Box.createHorizontalStrut(8);
        Component component11 = Box.createHorizontalStrut(8);
        Component component12 = Box.createHorizontalStrut(8);
        Component component7 = Box.createHorizontalStrut(8);
        Component component3 = Box.createHorizontalStrut(8);
        Component component5 = Box.createHorizontalStrut(8);
        Component component6 = Box.createHorizontalStrut(8);
        Border border5 = BorderFactory.createEmptyBorder();
        panel1.setLayout(borderLayout1);
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(e -> cancelB_actionPerformed());
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText("Ok");
        okB.addActionListener(e -> okB_actionPerformed());
        this.getRootPane().setDefaultButton(okB);
        buttonsPanel.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        panel1.setBorder(border1);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(border4);
        headerPanel.setMinimumSize(new Dimension(159, 52));
        headerPanel.setPreferredSize(new Dimension(159, 52));
        headerPanel.setLayout(flowLayout7);
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Table properties"));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource("resources/icons/tablebig.png")));
        flowLayout7.setAlignment(FlowLayout.LEFT);
        flowLayout7.setHgap(2);
        flowLayout7.setVgap(2);
        jLabel4.setMaximumSize(new Dimension(70, 16));
        jLabel4.setMinimumSize(new Dimension(50, 16));
        jLabel4.setPreferredSize(new Dimension(70, 16));
        jLabel4.setText(Local.getString("Width"));
        flowLayout5.setAlignment(FlowLayout.LEFT);
        tdHeightField.setMinimumSize(new Dimension(30, 25));
        tdHeightField.setPreferredSize(new Dimension(50, 25));
        flowLayout2.setAlignment(FlowLayout.LEFT);
        jLabel7.setMaximumSize(new Dimension(70, 16));
        jLabel7.setMinimumSize(new Dimension(40, 16));
        jLabel7.setPreferredSize(new Dimension(70, 16));
        jLabel7.setText(Local.getString("Row span"));
        flowLayout6.setAlignment(FlowLayout.LEFT);
        jPanel7.setLayout(flowLayout6);
        tdPanel.setLayout(gridLayout2);
        tdPanel.setBorder(border2);
        tdWidthField.setPreferredSize(new Dimension(50, 25));
        tdWidthField.setMinimumSize(new Dimension(30, 25));
        jPanel6.setLayout(flowLayout5);
        tdAlignCB.setBackground(new Color(230, 230, 230));
        tdAlignCB.setFont(new java.awt.Font("Dialog", 1, 10));
        tdAlignCB.setPreferredSize(new Dimension(63, 25));
        jLabel6.setText(Local.getString("Col span"));
        jLabel6.setPreferredSize(new Dimension(70, 16));
        jLabel6.setMinimumSize(new Dimension(50, 16));
        jLabel6.setMaximumSize(new Dimension(70, 16));
        gridLayout2.setColumns(1);
        gridLayout2.setRows(4);
        jPanel5.setLayout(flowLayout2);
        jLabel9.setMaximumSize(new Dimension(70, 16));
        jLabel9.setMinimumSize(new Dimension(40, 16));
        jLabel9.setPreferredSize(new Dimension(70, 16));
        jLabel9.setText(Local.getString("Vert. align"));
        jLabel5.setMaximumSize(new Dimension(70, 16));
        jLabel5.setMinimumSize(new Dimension(40, 16));
        jLabel5.setPreferredSize(new Dimension(70, 16));
        jLabel5.setText(Local.getString("Height"));
        trPanel.setLayout(gridLayout4);
        trPanel.setBorder(border2);
        gridLayout4.setColumns(1);
        gridLayout4.setRows(2);
        jLabel20.setText(Local.getString("Align"));
        jLabel20.setPreferredSize(new Dimension(70, 16));
        jLabel20.setMinimumSize(new Dimension(50, 16));
        jLabel20.setMaximumSize(new Dimension(70, 16));
        tdValignCB.setPreferredSize(new Dimension(63, 25));
        tdValignCB.setFont(new java.awt.Font("Dialog", 1, 10));
        tdValignCB.setBackground(new Color(230, 230, 230));
        flowLayout12.setAlignment(FlowLayout.LEFT);
        tdBgcolorField.setPreferredSize(new Dimension(70, 25));
        tdBgcolorField.setMinimumSize(new Dimension(60, 25));
        jPanel14.setLayout(flowLayout12);
        jLabel21.setText(Local.getString("Fill color"));
        jLabel21.setPreferredSize(new Dimension(70, 16));
        jLabel21.setMinimumSize(new Dimension(50, 16));
        jLabel21.setMaximumSize(new Dimension(70, 16));
        tdBgcolorB.setMinimumSize(new Dimension(25, 25));
        tdBgcolorB.setPreferredSize(new Dimension(25, 25));
        tdBgcolorB.setIcon(new ImageIcon(net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource("resources/icons/color.png")));
        tdBgcolorB.addActionListener(e -> tdBgcolorB_actionPerformed());
        tdNowrapChB.setText(Local.getString("No text wrapping"));
        trAlignCB.setPreferredSize(new Dimension(100, 25));
        trAlignCB.setFont(new java.awt.Font("Dialog", 1, 10));
        trAlignCB.setBackground(new Color(230, 230, 230));
        trValignCB.setBackground(new Color(230, 230, 230));
        trValignCB.setFont(new java.awt.Font("Dialog", 1, 10));
        trValignCB.setPreferredSize(new Dimension(100, 25));
        jLabel22.setMaximumSize(new Dimension(50, 16));
        jLabel22.setMinimumSize(new Dimension(50, 16));
        jLabel22.setPreferredSize(new Dimension(50, 16));
        jLabel22.setText(Local.getString("Align"));
        flowLayout10.setAlignment(FlowLayout.LEFT);
        jLabel16.setText(Local.getString("Vert. align"));
        jLabel16.setPreferredSize(new Dimension(80, 16));
        jLabel16.setMinimumSize(new Dimension(40, 16));
        jLabel16.setMaximumSize(new Dimension(80, 16));
        jPanel11.setLayout(flowLayout10);
        trBgcolorField.setMinimumSize(new Dimension(60, 25));
        trBgcolorField.setPreferredSize(new Dimension(70, 25));
        jLabel23.setMaximumSize(new Dimension(70, 16));
        jLabel23.setMinimumSize(new Dimension(50, 16));
        jLabel23.setPreferredSize(new Dimension(50, 16));
        jLabel23.setText(Local.getString("Fill color"));
        jPanel15.setLayout(flowLayout13);
        flowLayout13.setAlignment(FlowLayout.LEFT);
        trBgcolorB.setMinimumSize(new Dimension(25, 25));
        trBgcolorB.setPreferredSize(new Dimension(25, 25));
        trBgcolorB.setIcon(new ImageIcon(net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource("resources/icons/color.png")));
        trBgcolorB.addActionListener(e -> trBgcolorB_actionPerformed());
        tdColspan.setBorder(border5);
        tdColspan.setPreferredSize(new Dimension(50, 24));
        tdRowspan.setBorder(border5);
        tdRowspan.setPreferredSize(new Dimension(50, 24));
        jLabel8.setText(Local.getString("Width"));
        jLabel8.setPreferredSize(new Dimension(70, 16));
        jLabel8.setMinimumSize(new Dimension(60, 16));
        jLabel8.setMaximumSize(new Dimension(70, 16));
        jLabel12.setText(Local.getString("Border"));
        jLabel12.setPreferredSize(new Dimension(70, 16));
        jLabel12.setMinimumSize(new Dimension(60, 16));
        jLabel12.setMaximumSize(new Dimension(70, 16));
        cellpadding.setBorder(border5);
        cellpadding.setPreferredSize(new Dimension(50, 24));
        flowLayout8.setAlignment(FlowLayout.LEFT);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        jLabel24.setMaximumSize(new Dimension(70, 16));
        jLabel24.setMinimumSize(new Dimension(60, 16));
        jLabel24.setPreferredSize(new Dimension(70, 16));
        jLabel24.setText(Local.getString("Align"));
        tablePanel.setBorder(border2);
        tablePanel.setLayout(gridLayout3);
        gridLayout3.setColumns(1);
        gridLayout3.setRows(4);
        border.setBorder(border5);
        border.setPreferredSize(new Dimension(50, 24));
        jPanel10.setLayout(flowLayout4);
        jPanel12.setLayout(flowLayout11);
        vAlignCB.setPreferredSize(new Dimension(63, 25));
        vAlignCB.setFont(new java.awt.Font("Dialog", 1, 10));
        vAlignCB.setBackground(new Color(230, 230, 230));
        bgcolorField.setPreferredSize(new Dimension(70, 25));
        bgcolorField.setMinimumSize(new Dimension(60, 25));
        heightField.setMinimumSize(new Dimension(30, 25));
        heightField.setPreferredSize(new Dimension(50, 25));
        jLabel13.setMaximumSize(new Dimension(70, 16));
        jLabel13.setMinimumSize(new Dimension(40, 16));
        jLabel13.setPreferredSize(new Dimension(70, 16));
        jLabel13.setText(Local.getString("Cell spacing"));
        cellspacing.setBorder(border5);
        cellspacing.setPreferredSize(new Dimension(50, 24));
        jLabel17.setMaximumSize(new Dimension(70, 16));
        jLabel17.setMinimumSize(new Dimension(40, 16));
        jLabel17.setPreferredSize(new Dimension(70, 16));
        jLabel17.setText(Local.getString("Vert. align"));
        jLabel25.setMaximumSize(new Dimension(70, 16));
        jLabel25.setMinimumSize(new Dimension(70, 16));
        jLabel25.setPreferredSize(new Dimension(70, 16));
        jLabel25.setText(Local.getString("Fill color"));
        bgColorB.addActionListener(e -> bgColorB_actionPerformed());
        bgColorB.setIcon(new ImageIcon(net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource("resources/icons/color.png")));
        bgColorB.setPreferredSize(new Dimension(25, 25));
        bgColorB.setMinimumSize(new Dimension(25, 25));
        widthField.setPreferredSize(new Dimension(50, 25));
        widthField.setMinimumSize(new Dimension(30, 25));
        flowLayout11.setAlignment(FlowLayout.LEFT);
        jPanel13.setLayout(flowLayout8);
        alignCB.setBackground(new Color(230, 230, 230));
        alignCB.setFont(new java.awt.Font("Dialog", 1, 10));
        alignCB.setPreferredSize(new Dimension(63, 25));
        jLabel14.setText(Local.getString("Cell padding"));
        jLabel14.setPreferredSize(new Dimension(70, 16));
        jLabel14.setMinimumSize(new Dimension(60, 16));
        jLabel14.setMaximumSize(new Dimension(70, 16));
        jPanel9.setLayout(flowLayout9);
        flowLayout9.setAlignment(FlowLayout.LEFT);
        jLabel15.setMaximumSize(new Dimension(70, 16));
        jLabel15.setMinimumSize(new Dimension(40, 16));
        jLabel15.setPreferredSize(new Dimension(70, 16));
        jLabel15.setText(Local.getString("Height"));
        jPanel14.add(jLabel21, null);
        jPanel14.add(tdBgcolorField, null);
        jPanel14.add(tdBgcolorB, null);
        jPanel14.add(component11, null);
        jPanel14.add(tdNowrapChB, null);
        getContentPane().add(panel1);
        panel1.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);
        panel1.add(jTabbedPane1, BorderLayout.NORTH);
        this.getContentPane().add(headerPanel, BorderLayout.NORTH);
        headerPanel.add(header, null);

        jPanel5.add(jLabel4, null);
        jPanel5.add(tdWidthField, null);
        jPanel5.add(component1, null);
        jPanel5.add(jLabel5, null);
        jPanel5.add(tdHeightField, null);
        tdPanel.add(jPanel5, null);
        tdPanel.add(jPanel6, null);
        tdPanel.add(jPanel7, null);
        jPanel6.add(jLabel6, null);
        jPanel6.add(tdColspan, null);
        jPanel6.add(component2, null);
        jPanel6.add(jLabel7, null);
        jPanel7.add(jLabel20, null);
        jPanel7.add(tdAlignCB, null);
        jPanel7.add(jLabel9, null);
        jPanel7.add(tdValignCB, null);
        jTabbedPane1.add(tdPanel, Local.getString("Table cell"));
        jTabbedPane1.add(trPanel, Local.getString("Table row"));
        tdPanel.add(jPanel14, null);
        jPanel11.add(jLabel22, null);
        jPanel11.add(trAlignCB, null);
        jPanel11.add(component12, null);
        jPanel11.add(jLabel16, null);
        jPanel11.add(trValignCB, null);
        jPanel11.add(component7, null);
        jPanel15.add(jLabel23, null);
        jPanel15.add(trBgcolorField, null);
        jPanel15.add(trBgcolorB, null);
        trPanel.add(jPanel11, null);
        trPanel.add(jPanel15, null);
        jPanel6.add(tdRowspan, null);
        Util.setBgcolorField(tdBgcolorField);
        tablePanel.add(jPanel10, null);
        jPanel10.add(jLabel8, null);
        jPanel10.add(widthField, null);
        jPanel10.add(component5, null);
        jPanel10.add(jLabel15, null);
        jPanel10.add(heightField, null);
        tablePanel.add(jPanel13, null);
        jPanel13.add(jLabel14, null);
        jPanel13.add(cellpadding, null);
        jPanel13.add(component3, null);
        jPanel13.add(jLabel13, null);
        jPanel13.add(cellspacing, null);
        tablePanel.add(jPanel9, null);
        jPanel9.add(jLabel12, null);
        jPanel9.add(border, null);
        jPanel9.add(component6, null);
        jPanel9.add(jLabel25, null);
        jPanel9.add(bgcolorField, null);
        jPanel9.add(bgColorB, null);
        tablePanel.add(jPanel12, null);
        jPanel12.add(jLabel24, null);
        jPanel12.add(alignCB, null);
        jPanel12.add(jLabel17, null);
        jPanel12.add(vAlignCB, null);
        jTabbedPane1.add(tablePanel, Local.getString("Table"));
    }

    private void okB_actionPerformed() {
        this.dispose();
    }

    private void cancelB_actionPerformed() {
        CANCELLED = true;
        this.dispose();
    }


    private void tdBgcolorB_actionPerformed() {
        Color c = JColorChooser.showDialog(this, Local.getString("Table cell background color"), Util.decodeColor(tdBgcolorField.getText()));
        if (c == null) return;
        tdBgcolorField.setText(Util.encodeColor(c));
        Util.setBgcolorField(tdBgcolorField);
    }

    private void trBgcolorB_actionPerformed() {
        Color c = JColorChooser.showDialog(this, Local.getString("Table row background color"), Util.decodeColor(trBgcolorField.getText()));
        if (c == null) return;
        trBgcolorField.setText(Util.encodeColor(c));
        Util.setBgcolorField(trBgcolorField);
    }

    private void bgColorB_actionPerformed() {
        Color c = JColorChooser.showDialog(this, Local.getString("Table background color"), Util.decodeColor(bgcolorField.getText()));
        if (c == null) return;
        bgcolorField.setText(Util.encodeColor(c));
        Util.setBgcolorField(bgcolorField);
    }

}