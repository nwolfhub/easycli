package org.nwolfhub;

public class Defaults {
    public Border squareBorder = new Border("-", "-", "|");
    public Template defaultTemplate = new Template("default", "", "\n");
    public Template carriageReturn = new Template("carriage", "\r", "");
    public Template boxedText = new Template("boxed", "", "").setBorder(squareBorder);

}
