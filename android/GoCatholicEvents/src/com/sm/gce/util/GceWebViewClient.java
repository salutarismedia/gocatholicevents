package com.sm.gce.util;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GceWebViewClient extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
	}
}
