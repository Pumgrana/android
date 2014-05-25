package com.example.pumgrana;

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
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    ArrayAdapter<String> adapter;
    List<String> contentIds;
    Misc m;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m = new Misc(this);
        if (m.isOnline()) {
        Log.i("Main activity", "fetchData");
        fetchData();
        Log.i("Main activity", "fetchTag");
        fetchTag();
        Log.i("Main activity", "fetchDT");
        fetchDT();
        } else {
        	Log.i("internet", "offline");
        }
        Log.i("Main activity", "updateView");        
        updateView();
/*        try {
			String result = req.get();
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//        new HttpAsyncTask().execute("http://163.5.84.222/api/content/list_content");
        String [] contents = getResources().getStringArray(R.array.contents);
        ListView l= (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contents);
//        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        		Intent intent = new Intent(parent.getContext(), Content.class);
        		String name = parent.getAdapter().getItem(position).toString();
//        		String ids = contentIds.get(position);
        		Bundle b = new Bundle();
        		b.putString("name", name);
//        		b.putString("ids", ids);
        		intent.putExtras(b);
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
    
    private void fetchData()
    {
    	Log.d("fetchData", "start");
    	PumgranaDB dataDB = new PumgranaDB(this);
		dataDB.open();
    	Requests req = new Requests();
    	Log.d("fetchData", "req");
        req.execute("get", "http://" + m.getIp() + "/api/content/list_content");
        try {
        	Log.d("fetchData", "try");        	
			String result = req.get(3, TimeUnit.SECONDS);
			if (result == null) {
				dataDB.close();
				return;
			}
			JSONObject jObject = new JSONObject(result);
			JSONArray jObj = jObject.getJSONArray("contents");
			for (int i=0; i < jObj.length(); i++) {
				Data data = new Data();
				data.setDataId((jObj.getJSONObject(i)).getString("_id"));
				data.setTitle((jObj.getJSONObject(i)).getString("title"));
//				data.setId((jObj.getJSONObject(i)).getString("_id"));
				data.setText((jObj.getJSONObject(i)).getString("text"));
				dataDB.insertData(data);
				Log.i("insert", "insert finished");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			Log.i("timeout", "timed out");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        dataDB.close();
    	Log.d("fetchData", "end");
    }

    public boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    
    private void fetchTag()
    {
    	PumgranaDB tagDB = new PumgranaDB(this);
		tagDB.open();
    	Requests req = new Requests();
        req.execute("get", "http://" + m.getIp() + "/api/tag/list_by_type/CONTENT");
        try {
			String result = req.get(1, TimeUnit.SECONDS);
			JSONObject jObject = new JSONObject(result);
			JSONArray jObj = jObject.getJSONArray("tags");
			for (int i=0; i < jObj.length(); i++) {
				Tag tag = new Tag();
				tag.setTagId(jObj.getJSONObject(i).getString("_id"));
				tag.setName(jObj.getJSONObject(i).getString("subject"));
				tagDB.insertTag(tag);
				Log.i("insert", "insert finished");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			Log.i("timeout", "timed out");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tagDB.close();
    }
    
    private void fetchDT()
    {
        Log.i("fetchDT", "start");
    	PumgranaDB dtDB = new PumgranaDB(this);
    	dtDB.open();
    	List<Data> listData = dtDB.getAllData();
    	Log.i("fetchDT", "iterator");
    	if (listData == null) {
            Log.i("fetchDT", "exit");
    		return;
    	}
        Log.i("fetchDT", "notEmpty");
    	Iterator<Data> it = listData.iterator();
        Log.i("fetchDT", "loop");
    	while (it.hasNext()) {
    		Data data = it.next();
        	Requests req = new Requests();
            req.execute("get", "http://" + m.getIp() + "/api/tag/list_from_content/" + data.getDataId());
            try {
    			String result = req.get(1, TimeUnit.SECONDS);
    			JSONObject jObject = new JSONObject(result);
    			JSONArray jObj = jObject.getJSONArray("tags");
    			for (int i=0; i < jObj.length(); i++) {
    				DataTag dt = new DataTag();
    				dt.setDataId(data.getDataId());
    				dt.setTagId(jObj.getJSONObject(i).getString("_id"));
    				dtDB.insertDataTag(dt);
    				Log.i("insert", "insert finished");
    			}
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (ExecutionException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (TimeoutException e) {
    			Log.i("timeout", "timed out");
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}   	
    	}
        Log.i("fetchDT", "end");
    }
    
    private void updateView()
    {
    	Log.i("updateView", "start");
    	PumgranaDB dataDB = new PumgranaDB(this);
    	dataDB.open();
    	List<Data> listData = dataDB.getAllData();
    	if (listData == null)
    		return;
    	Iterator<Data> it = listData.iterator();
		List<String> viewData = new ArrayList<String>();
    	Log.i("updateView", "loop");
    	while (it.hasNext()) {
    		Data data = it.next();
    		viewData.add(data.getTitle());
    	}
    	dataDB.close();
    	Log.i("updateView", "adapter");
        ListView l= (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, viewData);
        l.setAdapter(adapter);	
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
    	String st = tags.substring(1, tags.length() - 1);
    	Log.i("st", st);
    	String[] tagList = st.split(", ");
    	PumgranaDB pDB = new PumgranaDB(this);
    	pDB.open();
    	List<String> tIL = new ArrayList<String>();
    	for (int i=0;i< tagList.length; i++)
    	{
    		Tag t = pDB.getTag(tagList[i]);
    		tIL.add(t.getTagId());
    	}
    	List<Data> dList = pDB.getDbyT(tIL);
		Iterator<Data> it = dList.iterator();
		List<String> viewData = new ArrayList<String>();
		while(it.hasNext()) {
			Data d = it.next();
			viewData.add(d.getTitle());
		}
		pDB.close();
        ListView l= (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, viewData);
        l.setAdapter(adapter);	
        TextView t = (TextView) findViewById(R.id.textView1);
        t.setText(tags);
    }
    
/*    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
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
	*/
}
