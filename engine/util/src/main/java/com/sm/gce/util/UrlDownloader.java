package com.sm.gce.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class UrlDownloader extends LoggingObject {

    public String getUrl(String url) throws MalformedURLException, IOException {
        logger.debug("Downloading url " + url);
        InputStream inputStream = new URL(url).openStream();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String html = IOUtils.toString(bufferedReader);
        return html;
    }
}
