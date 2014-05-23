package com.sm.gce.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebHelper {

    private Map<String, String> cache = new HashMap<String, String>();
    private UrlDownloader urlDownloader = new UrlDownloader();

    public Boolean matches(String url, Pattern pattern) throws Exception {
        Boolean match = Boolean.FALSE;
        if (url != null && pattern != null) {
            if (!cacheContains(url)) {
                populateCacheForUrl(url);
            }
            return regexMatch(url, pattern);
        }
        return match;
    }

    private Boolean regexMatch(String url, Pattern pattern) {
        Boolean match = Boolean.FALSE;
        if (url != null && pattern != null) {
            String content = cache.get(url);
            if (content != null) {
                Matcher matcher = pattern.matcher(content);
                match = matcher.find();
            }
        }
        return match;
    }

    private void populateCacheForUrl(String url) throws Exception {
        String content = urlDownloader.getUrl(url);
        cache.put(url, content);
    }

    private boolean cacheContains(String url) {
        return cache.containsKey(url);
    }

}
