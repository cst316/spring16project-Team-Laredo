package net.sf.memoranda.ui;

import net.sf.memoranda.EventsManager;
import net.sf.memoranda.util.Local;
import nu.xom.Element;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

class ExportSticker {

    private final String name;

    public ExportSticker(String x) {
        this.name = remove1(x);
    }

    /**
     * Function to eliminate special chars from a string
     */
    //This appears to not work, so I've blocked off parts of it for testing purposes.-tjcole2
    private static String remove1(String input) {
        return input;
    }

    public void export(String src) {
        String contents = getSticker();
        File file = new File(this.name + "." + src);
        try (FileWriter fwrite = new FileWriter(file, true)) {
            fwrite.write(contents);
            JOptionPane.showMessageDialog(null, Local.getString("Sticker successfully exported to your Memoranda folder"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Local.getString("Sticker export failed."));
        }
    }

    private String getSticker() {
        Map stickers = EventsManager.getStickers();
        StringBuilder result = new StringBuilder();
        String nl = System.getProperty("line.separator");
        for (Object o : stickers.keySet()) {
            String id = (String) o;
            result.append(((Element) stickers.get(id)).getValue());
            result.append(nl);
        }
        return result.toString();
    }

}