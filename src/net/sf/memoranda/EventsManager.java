/**
 * EventsManager.java Created on 08.03.2003, 12:35:19 Alex Package:
 * net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net Copyright (c) 2003
 *         Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.*;


import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParentNode;

/*$Id: EventsManager.java,v 1.11 2004/10/06 16:00:11 ivanrise Exp $*/
public class EventsManager {
	public static final int REPEAT_DAILY = 1;
	public static final int REPEAT_WEEKLY = 2;
	public static final int REPEAT_MONTHLY = 3;
	public static final int REPEAT_YEARLY = 4;

	public static Document _doc = null;
	static Element _root = null;

	static {
		CurrentStorage.get().openEventsManager();
		if (_doc == null) {
			_root = new Element("eventslist");
			_doc = new Document(_root);
		} else
			_root = _doc.getRootElement();

	}

	public static void createSticker(String text, int prior) {
		Element el = new Element("sticker");
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("priority", prior+""));
		el.appendChild(text);
		_root.appendChild(el);
	}

	public static Map<String, Element> getStickers() {
		Map<String, Element> m = new HashMap<>();
		Elements els = _root.getChildElements("sticker");
		for (int i = 0; i < els.size(); i++) {
			Element se = els.get(i);
			m.put(se.getAttribute("id").getValue(), se);
		}
		return m;
	}

	public static void removeSticker(String stickerId) {
		Elements els = _root.getChildElements("sticker");
		for (int i = 0; i < els.size(); i++) {
			Element se = els.get(i);
			if (se.getAttribute("id").getValue().equals(stickerId)) {
				_root.removeChild(se);
				break;
			}
		}
	}

	public static boolean isNREventsForDate(CalendarDate date) {
		Day d = getDay(date);
		return d != null && d.getElement().getChildElements("event").size() > 0;
	}

	public static Collection<Event> getEventsForDate(CalendarDate date) {
		Vector<Event> v = new Vector<>();
		Day d = getDay(date);
		if (d != null) {
			Elements els = d.getElement().getChildElements("event");
			for (int i = 0; i < els.size(); i++)
				v.add(new EventImpl(els.get(i)));
		}
		Collection<Event> r = getRepeatableEventsForDate(date);
		if (r.size() > 0) {
			v.addAll(r);
		}
		Collections.sort(v);
		return v;
	}

	public static Event createEvent(
		CalendarDate date,
		int start_hh,
		int start_mm,
		int end_hh,
		int end_mm,
		String text) {
		Element el = new Element("event");
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("start_hour", String.valueOf(start_hh)));
		el.addAttribute(new Attribute("start_min", String.valueOf(start_mm)));
		el.addAttribute(new Attribute("end_hour", String.valueOf(end_hh)));
		el.addAttribute(new Attribute("end_min", String.valueOf(end_mm)));
		el.appendChild(text);
		Day d = getDay(date);
		if (d == null)
			d = createDay(date);
		d.getElement().appendChild(el);
		return new EventImpl(el);
	}

	public static Event createRepeatableEvent(
		int type,
		CalendarDate startDate,
		CalendarDate endDate,
		int period,
		int hh,
		int mm,
		String text,
		boolean workDays) {
		Element el = new Element("event");
		Element rep = _root.getFirstChildElement("repeatable");
		if (rep == null) {
			rep = new Element("repeatable");
			_root.appendChild(rep);
		}
		el.addAttribute(new Attribute("repeat-type", String.valueOf(type)));
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("hour", String.valueOf(hh)));
		el.addAttribute(new Attribute("min", String.valueOf(mm)));
		el.addAttribute(new Attribute("startDate", startDate.toString()));
		if (endDate != null)
			el.addAttribute(new Attribute("endDate", endDate.toString()));
		el.addAttribute(new Attribute("period", String.valueOf(period)));
		// new attribute for wrkin days - ivanrise
		el.addAttribute(new Attribute("workingDays",String.valueOf(workDays)));
		el.appendChild(text);
		rep.appendChild(el);
		return new EventImpl(el);
	}

	public static Collection getRepeatableEvents() {
		Vector<Event> v = new Vector<>();
		Element rep = _root.getFirstChildElement("repeatable");
		if (rep == null)
			return v;
		Elements els = rep.getChildElements("event");
		for (int i = 0; i < els.size(); i++)
			v.add(new EventImpl(els.get(i)));
		return v;
	}

	public static Collection<Event> getRepeatableEventsForDate(CalendarDate date) {
		Vector reps = (Vector) getRepeatableEvents();
		Vector<Event> v = new Vector<>();
		for (Object rep : reps) {
			Event ev = (Event) rep;

			// --- ivanrise
			// ignore this event if it's a 'only working days' event and today is weekend.
			if (ev.getWorkingDays() && (date.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
					date.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) continue;

			if (date.inPeriod(ev.getStartDate(), ev.getEndDate())) {
				if (ev.getRepeat() == REPEAT_DAILY) {
					int n = date.getCalendar().get(Calendar.DAY_OF_YEAR);
					int ns =
							ev.getStartDate().getCalendar().get(
									Calendar.DAY_OF_YEAR);

					if ((n - ns) % ev.getPeriod() == 0)
						v.add(ev);
				} else if (ev.getRepeat() == REPEAT_WEEKLY) {
					//noinspection MagicConstant
					if (date.getCalendar().get(Calendar.DAY_OF_WEEK)
							== ev.getPeriod())
						v.add(ev);
				} else if (ev.getRepeat() == REPEAT_MONTHLY) {
					//noinspection MagicConstant
					if (date.getCalendar().get(Calendar.DAY_OF_MONTH)
							== ev.getPeriod())
						v.add(ev);
				} else if (ev.getRepeat() == REPEAT_YEARLY) {
					int period = ev.getPeriod();

					if ((date.getYear() % 4) == 0
							&& date.getCalendar().get(Calendar.DAY_OF_YEAR) > 60)
						period++;

					//noinspection MagicConstant
					if (date.getCalendar().get(Calendar.DAY_OF_YEAR) == period)
						v.add(ev);
				}
			}
		}
		return v;
	}

	public static Collection<Event> getActiveEvents() {
		return getEventsForDate(CalendarDate.today());
	}

	public static void removeEvent(Event ev) {
		ParentNode parent = ev.getContent().getParent();
		parent.removeChild(ev.getContent());
	}

	private static Day createDay(CalendarDate date) {
		Year y = getYear(date.getYear());
		if (y == null)
			y = createYear(date.getYear());
		Month m = y.getMonth(date.getMonth());
		if (m == null)
			m = y.createMonth(date.getMonth());
		Day d = m.getDay(date.getDay());
		if (d == null)
			d = m.createDay(date.getDay());
		return d;
	}

	private static Year createYear(int y) {
		Element el = new Element("year");
		el.addAttribute(new Attribute("year", Integer.toString(y)));
		_root.appendChild(el);
		return new Year(el);
	}

	private static Year getYear(int y) {
		Elements yrs = _root.getChildElements("year");
		String yy = Integer.toString(y);
		for (int i = 0; i < yrs.size(); i++)
			if (yrs.get(i).getAttribute("year").getValue().equals(yy))
				return new Year(yrs.get(i));
		return null;
	}

	private static Day getDay(CalendarDate date) {
		Year y = getYear(date.getYear());
		if (y == null)
			return null;
		Month m = y.getMonth(date.getMonth());
		if (m == null)
			return null;
		return m.getDay(date.getDay());
	}

	static class Year {
		Element yearElement = null;

		public Year(Element el) {
			yearElement = el;
		}

		public int getValue() {
			return new Integer(yearElement.getAttribute("year").getValue());
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

		public Element getElement() {
			return yearElement;
		}

	}

	static class Month {
		Element mElement = null;

		public Month(Element el) {
			mElement = el;
		}

		public int getValue() {
			return new Integer(mElement.getAttribute("month").getValue());
		}

		public Day getDay(int d) {
			if (mElement == null)
				return null;
			Elements ds = mElement.getChildElements("day");
			String dd = Integer.toString(d);
			for (int i = 0; i < ds.size(); i++)
				if (ds.get(i).getAttribute("day").getValue().equals(dd))
					return new Day(ds.get(i));
			return null;
		}

		private Day createDay(int d) {
			Element el = new Element("day");
			el.addAttribute(new Attribute("day", Integer.toString(d)));
			el.addAttribute(
				new Attribute(
					"date",
					new CalendarDate(
						d,
						getValue(),
							new Integer(
									((Element) mElement.getParent())
											.getAttribute("year")
											.getValue()))
						.toString()));

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

	static class Day {
		Element dEl = null;

		public Day(Element el) {
			dEl = el;
		}

		public int getValue() {
			return new Integer(dEl.getAttribute("day").getValue());
		}

		public Element getElement() {
			return dEl;
		}
	}
}
