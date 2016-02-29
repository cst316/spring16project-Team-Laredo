package net.sf.memoranda.ui;

import net.sf.memoranda.Task;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Editor for task progress
 */
class TaskProgressEditor extends JPanel implements TableCellEditor {

    private final java.util.List listeners = new java.util.ArrayList();
    private final JLabel label = new JLabel();
    private JTable table;
    private Task current;
    private boolean isSelected;
    private int row;
    private int column;

    public TaskProgressEditor() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) {
                    stopEditing();
                    return;
                }

                int w = getWidth() / 2;
                if (e.getX() > w) {
                    current.setProgress(current.getProgress() + 5);
                } else {
                    current.setProgress(current.getProgress() - 5);
                }
                repaint();
            }
        });
        setLayout(new java.awt.BorderLayout());
        label.setOpaque(false);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int r, int c) {
        current = (Task) value;
        this.table = table;
        this.isSelected = isSelected;
        row = r;
        column = c;
        return this;
    }

    public void paint(Graphics g) {
        paintComponent(g);
    }

    public void paintComponent(Graphics g) {
        TableCellRenderer cr = table.getCellRenderer(row, column);
        ((TaskProgressLabel) cr.getTableCellRendererComponent(table, current, isSelected, true, row, column)).paintComponent(g);

        label.setSize(this.getSize());

        label.setText("-");
        label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label.paint(g);
        label.setText("+");
        label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label.paint(g);

    }

    private void stopEditing() {
        for (Object listener : listeners) {
            CellEditorListener cel = (CellEditorListener) listener;
            cel.editingStopped(null);
        }
    }

    public void addCellEditorListener(CellEditorListener var0) {
        listeners.add(var0);
    }

    public void removeCellEditorListener(CellEditorListener var0) {
        listeners.remove(var0);
    }

    public void cancelCellEditing() {
    }

    public java.lang.Object getCellEditorValue() {
        return null; // just return null, because model will not use this
    }

    public boolean isCellEditable(java.util.EventObject e) {
        if (e instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) e;
            if (me.getButton() == MouseEvent.BUTTON1) {
                return true;
            }
        }
        return false;
    }

    public boolean stopCellEditing() {
        return true;
    }

    public boolean shouldSelectCell(java.util.EventObject var0) {
        return true;
    }

}


