package com.cif.utils.util;

import java.awt.*;

public class CifColorFormat {

    public static Color getColor(String cifColorString) {
        try {
            String[] rgb = cifColorString.split("-");
            int r = Integer.parseInt(rgb[0]);
            int g = Integer.parseInt(rgb[1]);
            int b = Integer.parseInt(rgb[2]);
            return new Color(r, g, b);
        } catch (Exception e) {
            return Color.WHITE;
        }
    }

    public static String getCifFormatColorString(Color color) {
        return color.getRed() + "-" + color.getGreen() + "-" + color.getBlue();
    }

}
