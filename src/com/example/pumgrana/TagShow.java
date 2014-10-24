package com.example.pumgrana;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * Show the current content tags.
 */
public class TagShow extends Activity {

    /** The adapter. */
    ArrayAdapter<String> adapter;
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_show);
        getTagsNoDB(getIntent().getExtras().getString("id"));
//        String [] contents = getResources().getStringArray(R.array.tags);
//		String id = getIntent().getExtras().getString("ids");
//        new HttpAsyncTask().execute("http://163.5.84.222/api/tag/list_from_content/".concat(id));
    }

    public void getTagsNoDB(String uri) {
    	Misc m = new Misc(this);
    	Requests req = new Requests();
    	Log.d("fetchData", "req");
		try {
			req.execute("get", "http://" + m.getIp() + "/api/tag/list_from_content/" + URLEncoder.encode(uri, "utf-8"));
			String result = req.get(3, TimeUnit.SECONDS);
			JSONObject jObject = new JSONObject(result);
			JSONArray jObj = jObject.getJSONArray("tags");
			List<String> tagNameList = new ArrayList<String>();
			for (int i=0; i < jObj.length(); i++) {
				tagNameList.add(jObj.getJSONObject(i).getString("subject"));
			}
	        ListView l= (ListView) findViewById(R.id.listView1);
	        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tagNameList);
	        l.setAdapter(adapter);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
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
