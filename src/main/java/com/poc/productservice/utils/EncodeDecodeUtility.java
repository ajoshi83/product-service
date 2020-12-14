package com.poc.productservice.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class EncodeDecodeUtility {
    private EncodeDecodeUtility() {}
    public static String encode(String serialNumber) {
        return URLEncoder.encode(serialNumber, StandardCharsets.UTF_8);
    }

    public static String decode(String serialNumber) {
        return URLDecoder.decode(serialNumber, StandardCharsets.UTF_8);
    }
}
