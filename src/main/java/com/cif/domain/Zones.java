package com.cif.domain;

import java.util.Arrays;

public class Zones {

    public String zoneType;
    public ZoneItem[] zoneItems =new ZoneItem[0];

    @Override
    public String toString() {
        return "Zones{" +
                "\"zoneType\":'" + zoneType + '\'' +
                ", \"zoneItems\":" + Arrays.toString(zoneItems) +
                '}';
    }
}
