package org.nwolfhub.easycli;

import org.nwolfhub.easycli.model.Border;
import org.nwolfhub.easycli.model.FlexableValue;
import org.nwolfhub.easycli.model.Template;
import org.nwolfhub.easycli.model.Variable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Defaults {
    public static Border squareBorder = new Border("-", "-", "|");
    public static Template defaultTemplate = new Template("default", "", "\n");
    public static Template carriageReturn = new Template("carriage", "\r", "");
    public static Template boxedText = new Template("boxed", "", "").setBorder(squareBorder);

    public static Template loggingTemplate = new Template("logging", "{logLevel} [{printDate}] ", "\n").addVariable(new Variable("{printDate}", new FlexableValue(new SimpleDateFormat("dd.MM HH:mm:ss")) {
        @Override
        public String call() {
            Date date = new Date();
            return ((SimpleDateFormat) meta).format(date.getTime());
        }
    }));

}
