package com.fr.canvas;

import com.fr.stable.AssistUtils;

import java.util.Arrays;

public class ImageData {
    private int width;

    private int height;

    private int[] data;

    public ImageData(int width, int height, int[] data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int[] getData() {
        return this.data;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("ImageData(")
                .append(width).append("|")
                .append(height).append("|");
        for (int i : data) {
            s.append(i).append(",");
        }
        s.delete(s.length() - 1, s.length());
        s.append(")");
        return s.toString();
    }

    public static ImageData valueOf(String value) {
        if (value == null) {
            throw new NullPointerException("ImageData must be specified");
        }
        String start = "ImageData(";
        String end = ")";
        if (!value.startsWith(start) || !value.endsWith(end)) {
            throw new IllegalArgumentException("Invalid ImageData specification, "
                    + "must begin with \"" + start + '"' + " and end with \"" + end + '"');
        }
        value = value.substring(start.length(), value.length() - end.length());
        String[] attributes = value.split("\\|");
        String[] dataString = attributes[2].split(",");
        int[] data = new int[dataString.length];
        for (int i = 0; i < dataString.length; i++) {
            data[i] = Integer.parseInt(dataString[i]);
        }
        return new ImageData(Integer.parseInt(attributes[0]), Integer.parseInt(attributes[1]), data);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ImageData
                && AssistUtils.equals(this.width, ((ImageData) obj).width)
                && AssistUtils.equals(this.height, ((ImageData) obj).height)
                && Arrays.equals(this.data, ((ImageData) obj).data);
    }

    @Override
    public int hashCode() {
        return AssistUtils.hashCode(width, height, data);
    }
}
