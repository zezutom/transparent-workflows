package org.zezutom.crashtracker.model;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 22/04/2012
 * Time: 19:33
 *
 * Severe incidents get extra attention.
 */
public enum IncidentSeverity {
    MAJOR("Major"),
    NORMAL("Normal"),
    MINOR("Minor");

    private String value;

    private IncidentSeverity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
