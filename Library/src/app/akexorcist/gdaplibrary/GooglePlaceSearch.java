package app.akexorcist.gdaplibrary;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

@SuppressLint("NewApi")
public class GooglePlaceSearch {

	private String API_KEY;
		
	public final static String STATUS_OK = "OK";
	public final static String STATUS_ZERO_RESULTS = "ZERO_RESULTS";
	public final static String STATUS_OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
	public final static String STATUS_REQUEST_DENIED = "REQUEST_DENIED";
	public final static String STATUS_INVALID_REQUEST = "INVALID_REQUEST ";

	public final static String PLACE_NAME = "Name";
	public final static String PLACE_ADDRESS = "Address";
	public final static String PLACE_LATITUDE = "Latitude";
	public final static String PLACE_LONGITUDE = "Longitude";
	public final static String PLACE_ICON = "Icon";
	public final static String PLACE_OPENNOW = "OpenNow";
	public final static String PLACE_PHOTO = "Photo";
	public final static String PLACE_PHONENUMBER = "PhoneNumber";
	
	private boolean isLogging = false;
	
	private OnPlaceResponseListener mPlaceResponseListener = null; 
	
	public GooglePlaceSearch(String api_key) { 
		API_KEY = api_key;
	}
	
	public String getApiKey() {
		return API_KEY;
	}
    
    public void setLogging(boolean state) {
    	isLogging = state;
    }

	/****************************************************************************/
    
	public void getNearby(double latitude, double longitude
			, int radius, String type, String language, String keyword) {
		getNearbyDocument(latitude, longitude, radius, type, language, keyword);
	}
	
	public void getNearby(double latitude, double longitude
			, int radius, String type, String language) {
		getNearbyDocument(latitude, longitude, radius, type, language, "");
	}
	
	public void getNearby(double latitude, double longitude
			, int radius, String type) {
		getNearbyDocument(latitude, longitude, radius, type, "", "");
	}
	
	public void getNearby(double latitude, double longitude
			, int radius) {
		getNearbyDocument(latitude, longitude, radius, "", "", "");
	}
	
	private void getNearbyDocument(double latitude, double longitude, int radius
			, String type, String language, String keyword) {
		String url = "https://maps.googleapis.com/maps/api/place/search/xml?"
				+ "location=" + latitude + "," + longitude + "&radius=" + radius
				+ "&key=" + API_KEY + "&sensor=false";
		if(!type.equals(""))
			url += "&types=" + type.toLowerCase(Locale.getDefault());
		if(!keyword.equals(""))
			url += "&keyword=" + keyword.replace(" ", "+");
		if(!language.equals(""))
			url += "&language=" + language.toLowerCase(Locale.getDefault());
   		if(isLogging)
   			Log.i("GooglePlace", "URL : " + url);
        new RequestTask().execute(new String[]{ url });
	}
		
	/****************************************************************************/
		
	public void getTextSearch(String keyword, String type, boolean opennow
			, String language, double latitude, double longitude, int radius) {
		getTextSearchDocument(keyword, type, opennow, language, latitude, longitude, radius);
	}
	
	public void getTextSearch(String keyword, String type, boolean opennow
			, String language) {
		getTextSearchDocument(keyword, type, opennow, language, -1, -1, -1);
	}
	
	public void getTextSearch(String keyword, String type, boolean opennow) {
		getTextSearchDocument(keyword, type, opennow, "", -1, -1, -1);
	}
	
	public void getTextSearch(String keyword, String type) {
		getTextSearchDocument(keyword, type, false, "", -1, -1, -1);
	}
	
	public void getTextSearch(String keyword) {
		getTextSearchDocument(keyword, "", false, "", -1, -1, -1);
	}	
	
	private void getTextSearchDocument(String keyword, String type, boolean opennow
			, String language, double latitude, double longitude, int radius) {
		String url = "https://maps.googleapis.com/maps/api/place/textsearch/xml?"
				+ "query=" + keyword.replace(" ", "+") + "&key=" + API_KEY + "&sensor=false";
		if(latitude != -1 && longitude != -1 && radius != -1)
			url += "&location=" + latitude + "," + longitude + "&radius=" + radius;
		if(opennow)
			url += "&opennow";
		if(!language.equals(""))
			url += "&language=" + language.toLowerCase(Locale.getDefault());
		if(isLogging)
			Log.i("GooglePlace", "URL : " + url);
        new RequestTask().execute(new String[]{ url });
	}
		
	/****************************************************************************/
	
	public void getRadarSearch(double latitude, double longitude
			, int radius, String type, String language, boolean opennow, String keyword) {
		getRadarSearchDocument(latitude, longitude, radius, type, language, opennow, keyword);
	}
	
	public void getRadarSearch(double latitude, double longitude
			, int radius, String type, String language, boolean opennow) {
		getRadarSearchDocument(latitude, longitude, radius, type, language, opennow, "");
	}
	
	public void getRadarSearch(double latitude, double longitude
			, int radius, String type, String language) {
		getRadarSearchDocument(latitude, longitude, radius, type, language, false, "");
	}
	
	public void getRadarSearch(double latitude, double longitude
			, int radius, String type) {
		getRadarSearchDocument(latitude, longitude, radius, type, "", false, "");
	}
	
	public void getRadarSearch(double latitude, double longitude
			, int radius) {
		getRadarSearchDocument(latitude, longitude, radius, "", "", false, "");
	}
	
	private void getRadarSearchDocument(double latitude, double longitude, int radius
			, String type, String language, boolean opennow, String keyword) {
		String url = "https://maps.googleapis.com/maps/api/place/search/xml?"
				+ "location=" + latitude + "," + longitude + "&radius=" + radius
				+ "&key=" + API_KEY + "&sensor=false";
		if(!type.equals(""))
			url += "&types=" + type.toLowerCase(Locale.getDefault());
		if(opennow)
			url += "&opennow";
		if(!keyword.equals(""))
			url += "&keyword=" + keyword.replace(" ", "+");
		if(!language.equals(""))
			url += "&language=" + language.toLowerCase(Locale.getDefault());
		if(isLogging)
			Log.i("GooglePlace", "URL : " + url);
        new RequestTask().execute(new String[]{ url });
	}

	/****************************************************************************/
	
	private class RequestTask extends AsyncTask<String, Void, ArrayList<ContentValues>> {
		String status = "";
		Document doc = null;
		
		protected ArrayList<ContentValues> doInBackground(String... url) {
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpContext localContext = new BasicHttpContext();
				HttpPost httpPost = new HttpPost(url[0]);
				HttpResponse response = httpClient.execute(httpPost, localContext);
				InputStream in = response.getEntity().getContent();
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				doc = builder.parse(in);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} 
			
			if(doc != null) {
				status = getStatus(doc);
				
		        if(status.equals(STATUS_OK)) {
		    		ArrayList<ContentValues> arr_cv = new ArrayList<ContentValues>();
		        	NodeList nl1 = doc.getElementsByTagName("result");
			        for (int i = 0; i < nl1.getLength(); i++) {
						ContentValues cv = new ContentValues();
						Node node = nl1.item(i);
			            NodeList nl2 = node.getChildNodes();
			            node = nl2.item(getNodeIndex(nl2, "reference"));
			            cv.put("reference", node.getTextContent());
			            
			            String reference = node.getTextContent();
			            String ref_url = "https://maps.googleapis.com/maps/api/place/details/xml?"
			            		+ "reference=" + reference + "&key=" + API_KEY + "&sensor=false";

			            try {
			                HttpClient httpClient = new DefaultHttpClient();
			                HttpContext localContext = new BasicHttpContext();
			                HttpPost httpPost = new HttpPost(ref_url);
			                HttpResponse response = httpClient.execute(httpPost, localContext);
			                InputStream in = response.getEntity().getContent();
			                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			                cv = getReferenceData(cv, builder.parse(in));
			            } catch (Exception e) {
			                e.printStackTrace();
			            }

			            try {
				    	    node = nl2.item(getNodeIndex(nl2, "opening_hours"));
				    	    NodeList nl3 = node.getChildNodes();
				            node = nl3.item(getNodeIndex(nl3, "open_now"));;
				            cv.put(PLACE_OPENNOW, node.getTextContent());
			            } catch (ArrayIndexOutOfBoundsException e) {
				            cv.put(PLACE_OPENNOW, "");
			            }
			            
			            try {
				    	    node = nl2.item(getNodeIndex(nl2, "photo"));
				    	    NodeList nl3 = node.getChildNodes();
				            node = nl3.item(getNodeIndex(nl3, "photo_reference"));;
				            cv.put(PLACE_PHOTO, node.getTextContent());
			            } catch (ArrayIndexOutOfBoundsException e) {
				            cv.put(PLACE_PHOTO, "");
			            }
			            arr_cv.add(cv);
			        }
			        return arr_cv;
				}
				return null;
			}
			return null;
		}	

		protected void onPostExecute(ArrayList<ContentValues> arr_cv) {
			super.onPostExecute(arr_cv);
			
			if(mPlaceResponseListener != null)
				mPlaceResponseListener.onResponse(status, arr_cv, doc);
		}

		private String getStatus(Document doc) {
			NodeList nl1 = doc.getElementsByTagName("PlaceSearchResponse");
			NodeList nl2 = nl1.item(0).getChildNodes();
			Node node = nl2.item(getNodeIndex(nl2, "status"));
			return node.getTextContent();
		}
	}
	
	private ContentValues getReferenceData(ContentValues cv, Document doc) {
		NodeList nl1 = doc.getElementsByTagName("result");
		nl1 = nl1.item(0).getChildNodes();

        Node node = nl1.item(getNodeIndex(nl1, "name"));
        cv.put(PLACE_NAME, node.getTextContent());

        try {
    	    node = nl1.item(getNodeIndex(nl1, "formatted_phone_number"));
            cv.put(PLACE_PHONENUMBER, node.getTextContent());
        } catch (ArrayIndexOutOfBoundsException e) {
            cv.put(PLACE_PHONENUMBER, "Unknown");
        }

	    node = nl1.item(getNodeIndex(nl1, "formatted_address"));
        cv.put(PLACE_ADDRESS, node.getTextContent());

	    node = nl1.item(getNodeIndex(nl1, "geometry"));
        NodeList nl2 = node.getChildNodes();
        node = nl2.item(getNodeIndex(nl2, "location"));
        nl2 = node.getChildNodes();
        node = nl2.item(getNodeIndex(nl2, "lat"));
        cv.put(PLACE_LATITUDE, node.getTextContent());
        node = nl2.item(getNodeIndex(nl2, "lng"));
        cv.put(PLACE_LONGITUDE, node.getTextContent());

	    node = nl1.item(getNodeIndex(nl1, "icon"));
        cv.put(PLACE_ICON, node.getTextContent());

		return cv;
	}
	
	public void getPhotoBitmapByWidth(String reference, int maxWidth
			, String tag, OnBitmapResponseListener listener) {
		getReferencePhoto(reference, maxWidth, 0, tag, listener);			
	}
	
	public void getPhotoBitmapByHeight(String reference, int maxHeight
			, String tag, OnBitmapResponseListener listener) {
		getReferencePhoto(reference, 0, maxHeight, tag, listener);			
	}
	
	public void getPhotoBitmap(String reference, int maxWidth, int maxHeight
			, String tag, OnBitmapResponseListener listener) {
		getReferencePhoto(reference, maxWidth, maxHeight, tag, listener);			
	}
	
	private void getReferencePhoto(String reference, int maxWidth, int maxHeight
			, String tag, OnBitmapResponseListener listener) {
		String url = "https://maps.googleapis.com/maps/api/place/photo?"
				+ "photoreference=" + reference + "&sensor=false&key=" + API_KEY;
		if(maxWidth > 0) 
			url += "&maxwidth=" + String.valueOf(maxWidth);
		if(maxHeight > 0) 
			url += "&maxheight=" + String.valueOf(maxHeight);
		if(isLogging)
			Log.i("GooglePlace", "URL : " + url);
		BitmapRequest br = new BitmapRequest(listener, url, tag);
        new BitmapTask().execute(new BitmapRequest[]{ br });
	}
	
	private class BitmapTask extends AsyncTask<BitmapRequest, Void, Bitmap> {
		BitmapRequest br = null;
		
		protected Bitmap doInBackground(BitmapRequest... arg0) {
			br = arg0[0];
			
			HttpClient httpclient = new DefaultHttpClient();   
	        HttpGet request = new HttpGet(arg0[0].getURL()); 
	        InputStream in = null;
			Bitmap bmp = null;
	        try {
	            in = httpclient.execute(request).getEntity().getContent();
	            bmp = BitmapFactory.decodeStream(in);
	            in.close();
	        } catch (IllegalStateException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return bmp;
		}
		
		protected void onPostExecute(Bitmap bm) {
			super.onPostExecute(bm);
			
			if(br.getListener() != null)
				br.getListener().onResponse(bm, br.getTag());
		}
	}
	
	private int getNodeIndex(NodeList nl, String nodename) {
		for(int i = 0 ; i < nl.getLength() ; i++) {
			if(nl.item(i).getNodeName().equals(nodename))
				return i;
		}
		return -1;
	}
	
	public interface OnPlaceResponseListener{
	    public void onResponse(String status, ArrayList<ContentValues> arr_data, Document doc);
	}
	
	public void setOnPlaceResponseListener(OnPlaceResponseListener listener) {
		mPlaceResponseListener = listener;
	}
	
	public interface OnBitmapResponseListener{
	    public void onResponse(Bitmap bm, String tag);
	}
}
