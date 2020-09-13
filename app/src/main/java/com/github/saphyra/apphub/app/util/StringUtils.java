package com.github.saphyra.apphub.app.util;

import static java.util.Objects.isNull;

public class StringUtils {
    public static boolean isNullOrEmpty(String in) {
        if (isNull(in)) {
            return true;
        }

        return in.isEmpty();
    }
}
