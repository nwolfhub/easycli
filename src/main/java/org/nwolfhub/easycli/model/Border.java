package org.nwolfhub.easycli.model;

public class Border {
    public String upChar;
    public String downChar;
    public String midChar;

    public Border() {}

    public Border(String upChar, String downChar, String midChar) {
        this.upChar = upChar;
        this.downChar = downChar;
        this.midChar = midChar;
    }

    public String getUpChar() {
        return upChar;
    }

    public Border setUpChar(String upChar) {
        this.upChar = upChar;
        return this;
    }

    public String getDownChar() {
        return downChar;
    }

    public Border setDownChar(String downChar) {
        this.downChar = downChar;
        return this;
    }

    public String getMidChar() {
        return midChar;
    }

    public Border setMidChar(String midChar) {
        this.midChar = midChar;
        return this;
    }

    private int findMax(String[] text) {
        int max = 0;
        for (String line:text) {
            max = Math.max(line.length(), max);
        }
        return max;
    }

    public String applyBorder(String[] text) {
        int maxTextLength = findMax(text);
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        builder.append(upChar.repeat(maxTextLength));
        builder.append("\n");
        for (String line:text) {
            builder.append(midChar);
            builder.append(line);
            builder.append(" ".repeat(maxTextLength-line.length()));
            builder.append(midChar);
            builder.append("\n");
        }
        builder.append(" ").append(downChar.repeat(maxTextLength)).append("\n");
        return builder.toString();
    }
    public String applyBorder(String text) {
        return applyBorder(text.split("\n"));
    }
}
