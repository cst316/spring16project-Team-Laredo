package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;

/**
 * Stores defects
 *
 * @author Benjamin Paothatat
 * @since 2/10/2016
 */
public class DefectImpl implements Defect {

    private Element m_element = null;
    private DefectList m_d1 = null;

    public DefectImpl(Element element, DefectList defectList) {
        m_element = element;
        m_d1 = defectList;
    }

    @Override
    public int getDefectNumber() {
        String defectNumber = m_element.getAttributeValue("defectNumber");
        return Integer.parseInt(defectNumber);
    }

    public void setDefectNumber(int defectNumber) {
        setAttr("defectNumber", String.valueOf(defectNumber));
    }

    @Override
    public CalendarDate getDateFound() {
        return new CalendarDate(m_element.getAttributeValue("dateFound"));
    }

    @Override
    public void setDateFound(CalendarDate dateFound) {
        setAttr("dateFound", dateFound.toString());
    }

    @Override
    public CalendarDate getDateRemoved() {
        CalendarDate dateRemoved;
        String dr = m_element.getAttributeValue("dateRemoved");
        Project project = this.m_d1.getProject();
        if (!(dr.equals(""))) {
            dateRemoved = new CalendarDate(dr);
        } else if (project.getEndDate() != null) {
            dateRemoved = project.getEndDate();
        } else {
            dateRemoved = this.getDateFound();
        }
        return dateRemoved;
    }

    @Override
    public void setDateRemoved(CalendarDate dateRemoved) {
        setAttr("dateRemoved", dateRemoved.toString());
    }

    @Override
    public int getPhaseOfInjection() {
        int phase = Defect.NO_PHASE;
        Attribute attr = m_element.getAttribute("phaseOfInjection");
        if (attr != null) {
            phase = Integer.parseInt(attr.getValue());
        }
        return phase;
    }

    @Override
    public void setPhaseOfInjection(int phaseOfInjection) {
        if (phaseOfInjection >= 0 && phaseOfInjection <= 6) {
            setAttr("phaseOfInjection", String.valueOf(phaseOfInjection));
        }
    }

    @Override
    public int getPhaseOfRemoval() {
        int phase = Defect.NO_PHASE;
        Attribute attr = m_element.getAttribute("removalPhase");
        if (attr != null) {
            phase = Integer.parseInt(attr.getValue());
        }
        return phase;
    }

    @Override
    public void setPhaseOfRemoval(int phaseOfRemoval) {
        if (phaseOfRemoval >= 0 && phaseOfRemoval <= 6) {
            setAttr("removalPhase", String.valueOf(phaseOfRemoval));
        }
    }

    @Override
    public int getTypeOfDefect() {
        int typeOfDefect = Defect.NON_TYPE;
        Attribute attr = m_element.getAttribute("typeOfDefect");
        if (attr != null) {
            typeOfDefect = Integer.parseInt(attr.getValue());
        }
        return typeOfDefect;
    }

    @Override
    public void setTypeOfDefect(int typeOfDefect) {
        if (typeOfDefect >= 0 && typeOfDefect <= 10) {
            setAttr("typeOfDefect", String.valueOf(typeOfDefect));
        }
    }

    @Override
    public String getDescription() {
        return m_element.getAttributeValue("description");
    }

    @Override
    public void setDescription(String description) {
        setAttr("description", description);
    }

    @Override
    public String toString() {
        String result = "Defect number: " + getDefectNumber() + "\n";
        result = result + "Defect type: " + getTypeOfDefect() + "\n";
        result = result + "Defect description: " + getDescription() + "\n";
        return result;
    }

    @Override
    public Element getElement() {
        return m_element;
    }

    private void setAttr(String a, String value) {
        Attribute attr = m_element.getAttribute(a);
        if (attr == null)
            m_element.addAttribute(new Attribute(a, value));
        else
            attr.setValue(value);
    }
}
