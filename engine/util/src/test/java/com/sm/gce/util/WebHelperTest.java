package com.sm.gce.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sm.gce.util.LoggingObject;
import com.sm.gce.util.UrlDownloader;
import com.sm.gce.util.WebHelper;

@RunWith(MockitoJUnitRunner.class)
public class WebHelperTest extends LoggingObject {

    @Mock
    private UrlDownloader urlDownloader;

    @InjectMocks
    private WebHelper webHelper = new WebHelper();

    private String url;
    private String content;
    private Pattern regex;

    @Before
    public void before() throws Exception {
        url = "url";
        content = "content";
        when(urlDownloader.getUrl(Matchers.eq(url))).thenReturn(content);
        regex = Pattern.compile(content);
    }

    @Test
    public void regexMatches() throws Exception {
        assertTrue(webHelper.matches(url, regex));

    }

    @Test
    public void regexFails() throws Exception {
        assertFalse(webHelper.matches(url, Pattern.compile("test")));
    }

    @Test
    public void downloadsWebPageOnlyOnce() throws Exception {
        webHelper.matches(url, regex);
        webHelper.matches(url, regex);
        verify(urlDownloader, times(1)).getUrl(Matchers.eq(url));
    }
}
