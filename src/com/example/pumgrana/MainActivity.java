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
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.app.Activity;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    ArrayAdapter<String> adapter;
    List<String> contentIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new HttpAsyncTask().execute("http://163.5.84.222/api/content/list_content");
        String [] contents = getResources().getStringArray(R.array.contents);
        ListView l= (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contents);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        		Intent intent = new Intent(parent.getContext(), Content.class);
//        		String name = parent.getAdapter().getItem(position).toString();
//        		String ids = contentIds.get(position);
//        		Bundle b = new Bundle();
//        		b.putString("name", name);
//        		b.putString("ids", ids);
//        		intent.putExtras(b);
        		startActivity(intent);
        	}
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void openTags(View view)
    {
    	Intent intent = new Intent(this, TagSelect.class);
    	Bundle b = new Bundle();
    	String s = ((TextView) findViewById(R.id.textView1)).getText().toString();
    	b.putString("checked", s);
    	intent.putExtras(b);
    	startActivityForResult(intent, 0);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	String tags = data.getExtras().getString("Tags");
        TextView t = (TextView) findViewById(R.id.textView1);
        t.setText(tags);
    }
    
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
 
            return connect(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("result", "OK");
            try {
				JSONObject jObject = new JSONObject(result);
				JSONArray jObj = jObject.getJSONArray("contents");
				List<String> jsonData = new ArrayList<String>();
				contentIds = new ArrayList<String>();
				for (int i=0; i < jObj.length(); i++) {
					jsonData.add((jObj.getJSONObject(i)).getString("title"));
					contentIds.add((jObj.getJSONObject(i)).getString("_id"));
//					jsonData.add(jObj.getString(jNames.getString(i)));
//					Log.v("json", jObj.getString(jNames.getString(i)));
				}
		        ListView l= (ListView) findViewById(R.id.listView1);
		        adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, jsonData);
		        l.setAdapter(adapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
       }
    }
    
    public static String connect(String url)
    {
    	Log.i("test", "Enter connect");
        HttpClient httpclient = new DefaultHttpClient();

        // Prepare a request object
        HttpGet httpget = new HttpGet(url); 

        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
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
