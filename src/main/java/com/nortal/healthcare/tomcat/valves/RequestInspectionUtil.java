package com.nortal.healthcare.tomcat.valves;

import java.util.Arrays;
import java.util.Map;

public class RequestInspectionUtil {

    public static String formatParameters(Map<String, String[]> params) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String param = entry.getKey() + " = " + Arrays.toString(entry.getValue());
            result.append(param);
            result.append("\n");
        }
        return result.toString();
    }

}

