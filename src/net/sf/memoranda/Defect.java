package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public interface Defect {

    //PSP Phases
    int PLANNING = 1;
    int DESIGN = 2;
    int CODE = 3;
    int REVIEW = 4;
    int COMPILE = 5;
    int TESTING = 6;
    int NO_PHASE = 0;

    //Defect Types
    int DOCUMENTATION = 1;
    int SYNTAX = 2;
    int BUILD = 3;
    int ASSIGNMENT = 4;
    int INTERFACE = 5;
    int CHECKING = 6;
    int DATA = 7;
    int FUNCTION = 8;
    int SYSTEM = 9;
    int ENVIROMENT = 10;
    int NON_TYPE = 0;

    int getDefectNumber();

    void setDefectNumber(int i);

    CalendarDate getDateFound();

    void setDateFound(CalendarDate dateFound);

    CalendarDate getDateRemoved();

    void setDateRemoved(CalendarDate dateRemoved);

    int getPhaseOfInjection();

    void setPhaseOfInjection(int phaseOfInjection);

    int getPhaseOfRemoval();

    void setPhaseOfRemoval(int phaseOfRemoval);

    int getTypeOfDefect();

    void setTypeOfDefect(int typeOfDefect);

    String getDescription();

    void setDescription(String description);

    String toString();

    Element getElement();


}
