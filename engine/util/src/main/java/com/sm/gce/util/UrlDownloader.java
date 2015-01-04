package com.sm.gce.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class UrlDownloader extends LoggingObject {

    public String getUrl(String urlString) throws MalformedURLException,
            IOException {
        logger.debug("Downloading url " + urlString);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Referer",
                "http://www.gocatholicevents.com");
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String html = IOUtils.toString(bufferedReader);
        return html;
    }
}
