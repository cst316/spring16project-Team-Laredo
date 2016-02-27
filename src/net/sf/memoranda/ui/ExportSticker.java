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
        
        /*public static Document _doc = null;
        static Element _root = null;

        static {
                CurrentStorage.get().openEventsManager();
                if (_doc == null) {
                        _root = new Element("eventslist");
/*                        _root.addNamespaceDeclaration("jnevents", NS_JNEVENTS);
                        _root.appendChild(
                                new Comment("This is JNotes 2 data file. Do not modify.")); */
/*                        _doc = new Document(_root);
                } else
                        _root = _doc.getRootElement();

        }*/

    public ExportSticker(String x) {
        this.name = remove1(x);
    }

    /**
     * Function to eliminate special chars from a string
     */
    //This appears to not work, so I've blocked off parts of it for testing purposes.-tjcole2
    private static String remove1(String input) {
        //String original = "Ã¡Ã Ã¤Ã©Ã¨Ã«Ã­Ã¬Ã¯Ã³Ã²Ã¶ÃºÃ¹uÃ±Ã�Ã€Ã„Ã‰ÃˆÃ‹Ã�ÃŒÃ�Ã“Ã’Ã–ÃšÃ™ÃœÃ‘Ã§Ã‡";
        //String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        //String output = input;
        //for (int i=0; i<original.length(); i++) {
        //    output = output.replace(original.charAt(i), ascii.charAt(i));
        //}
        //return output;
        return input;
    }

    public void export(String src) {
        String contents = getSticker();
        try {
            File file = new File(this.name + "." + src);
            FileWriter fwrite = new FileWriter(file, true);
            fwrite.write(contents);
            fwrite.close();
            JOptionPane.showMessageDialog(null, Local.getString("Sticker successfully exported to your Memoranda folder"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Local.getString("Sticker export failed."));
        }
    }

    private String getSticker() {
        Map stickers = EventsManager.getStickers();
        String result = "";
        String nl = System.getProperty("line.separator");
        for (Object o : stickers.keySet()) {
            String id = (String) o;
            result += ((Element) stickers.get(id)).getValue() + nl;
        }
        return result;
    }
        
        /*public static String getStickers() {
                String result ="";
                Elements els = _root.getChildElements("sticker");
                for (int i = 0; i < els.size(); i++) {
                        Element se = els.get(i);
                        m.put(se.getAttribute("id").getValue(), se.getValue());
                }
                return m;
        }*/


}