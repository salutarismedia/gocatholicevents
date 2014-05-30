package com.sm.gce.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class UrlDownloader extends LoggingObject {

    public String getUrl(String urlString) throws MalformedURLException,
            IOException {
        logger.debug("Downloading url " + urlString);
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Referer",
                "http://www.gocatholicevents.com/");
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String html = IOUtils.toString(bufferedReader);
        return html;
    }
}
