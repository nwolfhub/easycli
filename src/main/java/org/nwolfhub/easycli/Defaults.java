package org.nwolfhub.easycli;

public class Defaults {
    public static Border squareBorder = new Border("-", "-", "|");
    public static Template defaultTemplate = new Template("default", "", "\n");
    public static Template carriageReturn = new Template("carriage", "\r", "");
    public static Template boxedText = new Template("boxed", "", "").setBorder(squareBorder);

}
