package com.example.pumgrana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * Requests manager.
 */
public class Requests extends AsyncTask<String, Void, String> {

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(String... data) {
		if (data[0] == "get")
			return get(data[1]);
		else if (data[0] == "post")
			return post(data);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
    protected void onPostExecute(String result) {
    }
	
	/**
	 * Post.
	 *
	 * @param data the data
	 * @return the string
	 */
	private static String post(String... data)
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost((String)data[1]);
		try {
			httppost.setEntity(new StringEntity(data[2], "UTF8"));
			httppost.setHeader("Content-type", "application/json");
			HttpResponse response = httpclient.execute(httppost);
			if (response != null) {
				Log.d("Response", response.getStatusLine().toString());
				InputStream instream = response.getEntity().getContent();
                String result= convertStreamToString(instream);
				Log.d("Response", result);
				instream.close();
				return result;
			} else {
				Log.d("Response", "Error");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*try {
			httppost.setEntity(new UrlEncodedFormEntity(data[2]));
			HttpResponse response = httpclient.execute(httppost);
		}*/
		return null;
	}
	
    /**
     * Gets the.
     *
     * @param url the url
     * @return the string
     */
    private static String get(String url)
    {
    	Log.i("test", "Enter connect");
        HttpClient httpclient = new DefaultHttpClient();

        // Prepare a request object
        HttpGet httpget = new HttpGet(url); 
    	Log.d("get", "post get");        

        // Execute the request
        HttpResponse response;
        try {
        	Log.d("get", "response");        

            response = httpclient.execute(httpget);
        	Log.d("get", "exec");        
            // Examine the response status
            Log.i("Praeda",response.getStatusLine().toString());

            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release

            if (entity != null) {

                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                // now you have the string representation of the HTML request
                instream.close();
                return result;
            }
            return null;


        } catch (Exception e) {Log.e("error", e.toString());}
		return null;
    }
    
    /**
     * Convert stream to string.
     *
     * @param is the is
     * @return the string
     */
    private static String convertStreamToString(InputStream is)
    {

    	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    	StringBuilder sb = new StringBuilder();

    	String line = null;
    	try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}

}
