package com.web.crawler.utils;

import java.io.IOException;
import java.net.URL;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlUtil {

    public static final String wordPattern = "[^a-zA-Z -'.]";

    public static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String[] parseWebPageToWordArray(String webUrl) throws IOException {
        Connection conn = Jsoup.connect(webUrl);
        Document doc = null;
        doc = conn.get();
        String result = doc.body().text();

        //Replace special characters
        result = result.replaceAll(wordPattern, "");

        return result.toLowerCase().split(" ");
    }
}
