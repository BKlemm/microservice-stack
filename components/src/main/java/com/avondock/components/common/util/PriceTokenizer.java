package com.avondock.components.common.util;

import org.springframework.stereotype.Component;

@Component
public class PriceTokenizer {

    static String seperator = "$";

    public static String generate(String ...args) {
        StringBuilder str = new StringBuilder();
        for (String arg: args) {
            str.append(arg).append(seperator);
        }
        return str.toString();
    }
}
