package com.example.pumgrana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TagShow extends Activity {

    ArrayAdapter<String> adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_show);
        String [] contents = getResources().getStringArray(R.array.tags);
        ListView l= (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contents);
        l.setAdapter(adapter);
//		String id = getIntent().getExtras().getString("ids");
//        new HttpAsyncTask().execute("http://163.5.84.222/api/tag/list_from_content/".concat(id));
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tag_show, menu);
		return true;
	}
/*
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return connect(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            try {
				JSONObject jObject = new JSONObject(result);
				JSONArray jObj = jObject.getJSONArray("tags");
				List<String> jsonData = new ArrayList<String>();
				for (int i=0; i < jObj.length(); i++) {
					jsonData.add((jObj.getJSONObject(i)).getString("subject"));
				}
		        ListView l= (ListView) findViewById(R.id.listView1);
		        adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, jsonData);
		        l.setAdapter(adapter);
			} catch (JSONException e) {
				e.printStackTrace();
			}
       }
    }
    
    public static String connect(String url)
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url); 
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                instream.close();
                return result;
            }
            return null;
        } catch (Exception e) {Log.e("error", e.toString());}
		return null;
    }
    
    private static String convertStreamToString(InputStream is) {
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
*/	
}
