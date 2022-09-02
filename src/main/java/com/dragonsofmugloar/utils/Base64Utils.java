package com.dragonsofmugloar.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Base64;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility for Base64 decoding
 *
 * @author lex.korovin@gmail.com
 */
@UtilityClass
public class Base64Utils {

    private final Pattern base64Pattern = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$");

    public boolean isBase64(String s) {
        Matcher m = base64Pattern.matcher(s);
        return m.find();
    }

    public String decodeBase64(String s) {
        return new String(Base64.decodeBase64(s));
    }
}
