package com.sm.gce.util;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;

import com.sm.gce.util.LoggingObject;
import com.sm.gce.util.UrlDownloader;

public class UrlDownloaderTest extends LoggingObject {

    private UrlDownloader urlDownloader;

    @Before
    public void before() {
        urlDownloader = new UrlDownloader();
    }

    @Test
    public void downloadUrl() throws MalformedURLException, IOException {
        String html = urlDownloader.getUrl("http://www.google.com");
        assertNotNull(html);
        logger.info("download html of " + html);
    }
}
