/**
 * NoteListImpl.java
 * Created on 21.02.2003, 15:43:26 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import java.util.Collection;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 *
 */
/*$Id: NoteListImpl.java,v 1.14 2004/10/28 11:30:15 alexeya Exp $*/
public class NoteListImpl implements NoteList {

    private Project _project = null;
    private Document _doc = null;
    private Element _root = null;

    /**
     * Constructor for NoteListImpl.
     */
    public NoteListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
    }

    public NoteListImpl(Project prj) {
        _root = new Element("noteslist");
        _doc = new Document(_root);
        _project = prj;
    }

    public Collection getAllNotes() {
        Vector<NoteImpl> v = new Vector<>();
        Elements yrs = _root.getChildElements("year");
        for (int yi = 0; yi < yrs.size(); yi++) {
            Year y = new Year(yrs.get(yi));
            Vector<Month> ms = y.getMonths();
            for (Month m : ms) {
                Vector<Day> ds = m.getDays();
                for (Day d : ds) {
                    Vector<NoteElement> ns = d.getNotes();
                    v.addAll(ns.stream().map(n -> new NoteImpl(n.getElement(), _project)).collect(Collectors.toList()));
                }
            }
        }
        return v;
    }

    /**
     * @see net.sf.memoranda.NoteList#getMarkedNotes()
     */
    public Collection getMarkedNotes() {
        Vector<Note> v = new Vector<>();
        Elements yrs = _root.getChildElements("year");
        for (int yi = 0; yi < yrs.size(); yi++) {
            Year y = new Year(yrs.get(yi));
            Vector<Month> ms = y.getMonths();
            for (Month m : ms) {
                Vector<Day> ds = m.getDays();
                for (Day d : ds) {
                    Vector<NoteElement> ns = d.getNotes();
                    for (NoteElement ne : ns) {
                        Note n = new NoteImpl(ne.getElement(), _project);
                        if (n.isMarked()) v.add(n);
                    }
                }
            }
        }
        return v;
    }

    public Collection getNotesForPeriod(CalendarDate startDate, CalendarDate endDate) {
        Vector<NoteImpl> v = new Vector<>();
        Elements yrs = _root.getChildElements("year");
        for (int yi = 0; yi < yrs.size(); yi++) {
            Year y = new Year(yrs.get(yi));
            if ((y.getValue() >= startDate.getYear()) && (y.getValue() <= endDate.getYear())) {
                Vector<Month> months = y.getMonths();
                for (Month m : months) {
                    if (!((y.getValue() == startDate.getYear()) && (m.getValue() < startDate.getMonth()))
                            || !((y.getValue() == endDate.getYear()) && (m.getValue() > endDate.getMonth()))) {
                        Vector<Day> days = m.getDays();
                        for (Day d : days) {
                            if (!((m.getValue() == startDate.getMonth()) && (d.getValue() < startDate.getDay()))
                                    || !((m.getValue() == endDate.getMonth()) && (d.getValue() > endDate.getDay()))) {
                                Vector<NoteElement> ns = d.getNotes();
                                v.addAll(ns.stream().map(n -> new NoteImpl(n.getElement(), _project)).collect(Collectors.toList()));
                            }
                        }
                    }
                }
            }
        }
        return v;
    }

    /**
     * returns the first note for a date.
     *
     * @return Note
     */

    public Note getNoteForDate(CalendarDate date) {
        Day d = getDay(date);
        if (d == null)
            return null;
        Vector<NoteElement> ns = d.getNotes();
        if (ns.size() > 0) {
            NoteElement n = ns.get(0);
            return new NoteImpl(n.getElement(), _project);
        }
        return null;
    }

    public Note createNoteForDate(CalendarDate date) {
        Year y = getYear(date.getYear());
        if (y == null)
            y = createYear(date.getYear());
        Month m = y.getMonth(date.getMonth());
        if (m == null)
            m = y.createMonth(date.getMonth());
        Day d = m.getDay(date.getDay());
        if (d == null)
            d = m.createDay(date.getDay());
        NoteElement ne = d.createNote();
        return new NoteImpl(ne.getElement(), _project);
    }

    public void removeNote(CalendarDate date, String id) {
        Day d = getDay(date);
        if (d == null) return;
        Vector<NoteElement> ns = d.getNotes();
        for (NoteElement n : ns) {
            Element ne = n.getElement();
            if (ne.getAttribute("refid").getValue().equals(id)) d.getElement().removeChild(n.getElement());
        }
    }

    public Note getActiveNote() {
        return getNoteForDate(CurrentDate.get());
        // FIXED: Must return the first note for today [alexeya]
    }

    private Year getYear(int y) {
        Elements yrs = _root.getChildElements("year");
        String yy = Integer.toString(y);
        for (int i = 0; i < yrs.size(); i++)
            if (yrs.get(i).getAttribute("year").getValue().equals(yy))
                return new Year(yrs.get(i));
        //return createYear(y);
        return null;
    }

    private Year createYear(int y) {
        Element el = new Element("year");
        el.addAttribute(new Attribute("year", Integer.toString(y)));
        _root.appendChild(el);
        return new Year(el);
    }

    private Day getDay(CalendarDate date) {
        Year y = getYear(date.getYear());
        if (y == null)
            return null;
        Month m = y.getMonth(date.getMonth());
        if (m == null)
            return null;
        return m.getDay(date.getDay());
    }

    /**
     * @see net.sf.memoranda.NoteList#getXMLContent()
     */
    public Document getXMLContent() {
        return _doc;
    }

    private static class Month {
        Element mElement = null;

        public Month(Element el) {
            mElement = el;
        }

        public int getValue() {
            return Integer.parseInt(mElement.getAttribute("month").getValue());
        }

        public Day getDay(int d) {
            if (mElement == null)
                return null;
            Elements ds = mElement.getChildElements("day");
            String dd = Integer.toString(d);
            for (int i = 0; i < ds.size(); i++)
                if (ds.get(i).getAttribute("day").getValue().equals(dd))
                    return new Day(ds.get(i));
            //return createDay(d);
            return null;
        }

        private Day createDay(int d) {
            Element el = new Element("day");
            el.addAttribute(new Attribute("day", Integer.toString(d)));
            mElement.appendChild(el);
            return new Day(el);
        }

        public Vector<Day> getDays() {
            if (mElement == null)
                return null;
            Vector<Day> v = new Vector<>();
            Elements ds = mElement.getChildElements("day");
            for (int i = 0; i < ds.size(); i++)
                v.add(new Day(ds.get(i)));
            return v;
        }

        public Element getElement() {
            return mElement;
        }

    }

	
	/*
     * private class Day
	 */

    private static class Day {
        Element dEl = null;

        public Day(Element el) {
            dEl = el;
            // Added to fix old '.notes' XML format
            // Old-style XML is converted on the fly [alexeya]
            if (dEl.getAttribute("date") != null) {
                Attribute dAttr = dEl.getAttribute("date");
                Attribute tAttr = dEl.getAttribute("title");
                Element nEl = new Element("note");
                String date = dAttr.getValue().replace('/', '-');
                nEl.addAttribute(new Attribute("refid", date));
                nEl.addAttribute(new Attribute("title", tAttr.getValue()));
                dEl.appendChild(nEl);
                dEl.removeAttribute(dAttr);
                dEl.removeAttribute(tAttr);
            }
        }

        public int getValue() {
            return Integer.parseInt(dEl.getAttribute("day").getValue());
        }

        public NoteElement createNote() {
            Element el = new Element("note");
            dEl.appendChild(el);
            return new NoteElement(el);
        }

        public Vector<NoteElement> getNotes() {
            if (dEl == null)
                return null;
            Vector<NoteElement> v = new Vector<>();
            Elements ds = dEl.getChildElements("note");
            for (int i = 0; i < ds.size(); i++)
                v.add(new NoteElement(ds.get(i)));
            return v;
        }

        public Element getElement() {
            return dEl;
        }
    }

	
	/*
     * private class Day
	 */

    private static class NoteElement {
        final Element nEl;

        public NoteElement(Element el) {
            nEl = el;
        }

        public Element getElement() {
            return nEl;
        }
    }

    private class Year {
        Element yearElement = null;

        public Year(Element el) {
            yearElement = el;
        }

        public int getValue() {
            return Integer.parseInt(yearElement.getAttribute("year").getValue());
        }

        public Month getMonth(int m) {
            Elements ms = yearElement.getChildElements("month");
            String mm = Integer.toString(m);
            for (int i = 0; i < ms.size(); i++)
                if (ms.get(i).getAttribute("month").getValue().equals(mm))
                    return new Month(ms.get(i));
            return null;
        }

        private Month createMonth(int m) {
            Element el = new Element("month");
            el.addAttribute(new Attribute("month", Integer.toString(m)));
            yearElement.appendChild(el);
            return new Month(el);
        }

        public Vector<Month> getMonths() {
            Vector<Month> v = new Vector<>();
            Elements ms = yearElement.getChildElements("month");
            for (int i = 0; i < ms.size(); i++)
                v.add(new Month(ms.get(i)));
            return v;
        }

        public Element getElement() {
            return yearElement;
        }

    }


}
