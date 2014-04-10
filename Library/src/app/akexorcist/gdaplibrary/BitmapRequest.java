package app.akexorcist.gdaplibrary;

import app.akexorcist.gdaplibrary.GooglePlaceSearch.OnBitmapResponseListener;

public class BitmapRequest {

	OnBitmapResponseListener listener = null;
	String url = null;
	String tag = null;
	
	public BitmapRequest(OnBitmapResponseListener listener, String url, String tag) {
		this.listener = listener;
		this.url = url;
		this.tag = tag;
	}
	
	public String getURL() {
		return this.url;
	}
	
	public OnBitmapResponseListener getListener() {
		return this.listener;
	}

	public String getTag() {
		return this.tag;
	}
}
