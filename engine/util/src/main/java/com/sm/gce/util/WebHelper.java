package com.sm.gce.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebHelper extends LoggingObject {

    private Map<String, String> cache = new HashMap<String, String>();
    private UrlDownloader urlDownloader = new UrlDownloader();

    public String getUrl(String url) throws Exception {
        if (!cacheContains(url)) {
            populateCacheForUrl(url);
        }
        return cache.get(url);
    }

    public Boolean matches(String url, Pattern pattern) throws Exception {
        Boolean match = Boolean.FALSE;
        if (url != null && pattern != null) {
            return regexMatch(url, pattern);
        }
        return match;
    }

    private Boolean regexMatch(String url, Pattern pattern) throws Exception {
        Boolean match = Boolean.FALSE;
        if (url != null && pattern != null) {
            String content = getUrl(url);
            if (content != null) {
                Matcher matcher = pattern.matcher(content);
                match = matcher.find();
            }
        }
        return match;
    }

    private void populateCacheForUrl(String url) throws Exception {
        String content = urlDownloader.getUrl(url);
        logger.info("downloaded \"" + content + "\" from: " + url);
        cache.put(url, content);
    }

    private boolean cacheContains(String url) {
        return cache.containsKey(url);
    }

}
