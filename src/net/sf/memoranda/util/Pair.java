package net.sf.memoranda.util;

import nu.xom.Element;

class Pair {
    private Element element;
    private int priority;

    public Pair(Element value, int priority) {
        setElement(value);
        setPriority(priority);
    }

    public Element getElement() {
        return element;
    }

    private void setElement(Element value) {
        this.element = value;
    }

    public int getPriority() {
        return priority;
    }

    private void setPriority(int priority) {
        this.priority = priority;
    }

}