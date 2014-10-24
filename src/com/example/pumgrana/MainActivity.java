package com.example.pumgrana;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.pumgrana.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * MainActivity and home view.
 */
public class MainActivity extends Activity {

    /** The adapter. */
    ArrayAdapter<String> adapter;
    
    /** The data uri. */
    List<String> dataUri;
    
    /** The m. */
    Misc m;

    private String[] mTags;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout mDrawerLinearLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mTitle = mDrawerTitle = getTitle();

        m = new Misc(this);
        if (m.isOnline()) {
/*        Log.i("Main activity", "fetchData");
        fetchData();
        Log.i("Main activity", "fetchTag");
        fetchTag();
        Log.i("Main activity", "fetchDT");
        fetchDT(); */
        //updateViewNoDB();
        } else {
        	Log.i("internet", "offline");
        }
        Log.i("Main activity", "updateView");
        mTags = getResources().getStringArray(R.array.tags);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLinearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        mDrawerList = (ListView) findViewById(R.id.listView1);
        getTagNoDB(mDrawerList);
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);        

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerToggle.syncState();
//        updateView();
/*        String [] contents = getResources().getStringArray(R.array.contents);
        ListView l= (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contents);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        		Intent intent = new Intent(parent.getContext(), Content.class);
        		String name = parent.getAdapter().getItem(position).toString();
        		String uri = dataUri.get(position);
        		Bundle b = new Bundle();
        		b.putString("name", name);
        		b.putString("uri", uri);
        		intent.putExtras(b);
        		startActivity(intent);
        	}
        });*/
    }

    public static class MainFragment extends Fragment {
        ArrayAdapter<String> adapter;
        List<String> dataUri;
        
        public MainFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.main_view, container, false);
            updateViewNoDB(rootView);
            return rootView;
        }
        
        public void updateViewNoDB(View rootView)
        {
        	Requests req = new Requests();
            req.execute("get", "http://" + "163.5.84.222" + "/api/content/list_content");
            try {
    			String result = req.get(10, TimeUnit.SECONDS);
    			if (result != null) {
    				JSONObject jObject = new JSONObject(result);
    				JSONArray jObj = jObject.getJSONArray("contents");
    				List<String> viewData = new ArrayList<String>();
    				dataUri = new ArrayList<String>();
    				for (int i=0; i < jObj.length(); i++) {				
    					viewData.add((jObj.getJSONObject(i)).getString("title"));
    					dataUri.add((jObj.getJSONObject(i)).getString("uri"));
    				}
    		    ListView l= (ListView) rootView.findViewById(R.id.listView1);
    	        adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, viewData);
    	        l.setAdapter(adapter);				
    	        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    	        	@Override
    	            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
    	        		Intent intent = new Intent(parent.getContext(), Content.class);
    	        		String name = parent.getAdapter().getItem(position).toString();
    	        		String uri = dataUri.get(position);
    	        		Bundle b = new Bundle();
    	        		b.putString("name", name);
    	        		b.putString("uri", uri);
    	        		intent.putExtras(b);
    	        		startActivity(intent);
    	        	}
    	        });
    			}
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (ExecutionException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (TimeoutException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        
    }    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinearLayout);
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    
    
    private void getTagNoDB(ListView listView) {
    	Misc m = new Misc(this);
    	Requests req = new Requests();
        req.execute("get", "http://" + "163.5.84.222" + "/api/tag/list_by_type/CONTENT");
        try {
			String result = req.get(10, TimeUnit.SECONDS);
			Log.d("RESULT", result);
			JSONObject jObject = new JSONObject(result);
			JSONArray jObj = jObject.getJSONArray("tags");
			List<String> viewTag = new ArrayList<String>();
			ArrayList<String> idTag = new ArrayList<String>();
			for (int i=0; i < jObj.length(); i++) {
				idTag.add(jObj.getJSONObject(i).getString("uri"));
				viewTag.add(jObj.getJSONObject(i).getString("subject"));
			}
	        adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, viewTag);
	        listView.setAdapter(adapter);
	        int i = 0;
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

    
    /**
     * Update view no db.
     */
    public void updateViewNoDB()
    {
    	Requests req = new Requests();
        req.execute("get", "http://" + m.getIp() + "/api/content/list_content");
        try {
			String result = req.get(10, TimeUnit.SECONDS);
			if (result != null) {
				JSONObject jObject = new JSONObject(result);
				JSONArray jObj = jObject.getJSONArray("contents");
				List<String> viewData = new ArrayList<String>();
				dataUri = new ArrayList<String>();
				for (int i=0; i < jObj.length(); i++) {				
					viewData.add((jObj.getJSONObject(i)).getString("title"));
					dataUri.add((jObj.getJSONObject(i)).getString("uri"));
				}
		    ListView l= (ListView) findViewById(R.id.listView1);
	        adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, viewData);
	        l.setAdapter(adapter);				
	        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        	@Override
	            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
	        		Intent intent = new Intent(parent.getContext(), Content.class);
	        		String name = parent.getAdapter().getItem(position).toString();
	        		String uri = dataUri.get(position);
	        		Bundle b = new Bundle();
	        		b.putString("name", name);
	        		b.putString("uri", uri);
	        		intent.putExtras(b);
	        		startActivity(intent);
	        	}
	        });
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Update view filter no db.
     *
     * @param arr the arr
     */
    private void updateViewFilterNoDB(ArrayList<String> arr)
    {
    	String reqStr = "http://" + m.getIp() + "/api/content/list_content//";
    	for (int i = 0; i < arr.size(); i++) {
    		try {
				reqStr += URLEncoder.encode(arr.get(i), "utf-8") + "/";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	Log.d("reqStr", reqStr);
    	Requests req = new Requests();
        req.execute("get", reqStr);
        try {
        	Boolean status;
			String result = req.get(10, TimeUnit.SECONDS);
			Log.d("result", result);
			if (result != null) {
				Log.d("notnull", result);
				JSONObject jObject = new JSONObject(result);
				List<String> viewData = new ArrayList<String>();
				if (jObject.getInt("status") != 204) {
					status = true;
					JSONArray jObj = jObject.getJSONArray("contents");
					dataUri = new ArrayList<String>();
					for (int i=0; i < jObj.length(); i++) {				
						viewData.add((jObj.getJSONObject(i)).getString("title"));
						dataUri.add((jObj.getJSONObject(i)).getString("uri"));
					}
				} else {
					status = false;
				}
				ListView l= (ListView) findViewById(R.id.listView1);
				if (status = false) {
					Log.d("viewData", "empty");
					l.setAdapter(null);
				} else {
					Log.d("viewData", "not empty");
					adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, viewData);
					l.setAdapter(adapter);
				}
	        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        	@Override
	            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
	        		Intent intent = new Intent(parent.getContext(), Content.class);
	        		String name = parent.getAdapter().getItem(position).toString();
	        		String uri = dataUri.get(position);
	        		Bundle b = new Bundle();
	        		b.putString("name", name);
	        		b.putString("uri", uri);
	        		intent.putExtras(b);
	        		startActivity(intent);
	        	}
	        });
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * On add.
     *
     * @param view the view
     */
    public void onAdd(View view) {
    	Intent intent = new Intent(this, AddContent.class);
    	startActivity(intent);
    }
    
    public void onDelete(View view) {
    	Intent intent = new Intent(this, DeleteContents.class);
    	startActivity(intent);
    }
    /**
     * Fetch data.
     */
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
			String result = req.get(10, TimeUnit.SECONDS);
			if (result == null) {
				dataDB.close();
				return;
			}
			JSONObject jObject = new JSONObject(result);
			JSONArray jObj = jObject.getJSONArray("contents");
			for (int i=0; i < jObj.length(); i++) {
				Data data = new Data();
				data.setDataId((jObj.getJSONObject(i)).getString("uri"));
				data.setTitle((jObj.getJSONObject(i)).getString("title"));
//				data.setId((jObj.getJSONObject(i)).getString("_id"));
				data.setText((jObj.getJSONObject(i)).getString("summary"));
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

    /**
     * Checks if is online.
     *
     * @return true, if is online
     */
    public boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    
    /**
     * Fetch tag.
     */
    private void fetchTag()
    {
    	PumgranaDB tagDB = new PumgranaDB(this);
		tagDB.open();
    	Requests req = new Requests();
        req.execute("get", "http://" + m.getIp() + "/api/tag/list_by_type/CONTENT");
        try {
			String result = req.get(10, TimeUnit.SECONDS);
			Log.d("RESULT", result);
			JSONObject jObject = new JSONObject(result);
			JSONArray jObj = jObject.getJSONArray("tags");
			for (int i=0; i < jObj.length(); i++) {
				Tag tag = new Tag();
				tag.setTagId(jObj.getJSONObject(i).getString("uri"));
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
    
    /**
     * Fetch dt.
     */
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
    			String result = req.get(10, TimeUnit.SECONDS);
    			JSONObject jObject = new JSONObject(result);
    			JSONArray jObj = jObject.getJSONArray("tags");
    			for (int i=0; i < jObj.length(); i++) {
    				DataTag dt = new DataTag();
    				dt.setDataId(data.getDataId());
    				dt.setTagId(jObj.getJSONObject(i).getString("uri"));
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
    
    /**
     * Update view.
     */
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
		dataUri = new ArrayList<String>();
		Log.i("updateView", "loop");
    	while (it.hasNext()) {
    		Data data = it.next();
    		viewData.add(data.getTitle());
    		dataUri.add(data.getDataId());
    	}
    	dataDB.close();
    	Log.i("updateView", "adapter");
        ListView l= (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, viewData);
        l.setAdapter(adapter);	
    }
    
    /**
     * Open tags.
     *
     * @param view the view
     */
    public void openTags(View view)
    {
    	Intent intent = new Intent(this, TagSelect.class);
    	Bundle b = new Bundle();
    	String s = ((TextView) findViewById(R.id.textView1)).getText().toString();
    	b.putString("checked", s);
    	intent.putExtras(b);
    	startActivityForResult(intent, 0);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	String tags = data.getExtras().getString("Tags");
    	ArrayList<String> ids = data.getExtras().getStringArrayList("ids");
    	if (tags.equals("[]")) {
    		Log.d("onActivityResult3", "empty");
    		updateViewNoDB();
	        TextView t = (TextView) findViewById(R.id.textView1);
	        t.setText("Aucun");
    	} else {
	    	String st = tags.substring(1, tags.length() - 1);
	    	Log.i("onActivityResult2", st);
	    	String[] tagList = st.split(", ");
/*	    	PumgranaDB pDB = new PumgranaDB(this);
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
			pDB.close();*/
	    	updateViewFilterNoDB(ids);
	        TextView t = (TextView) findViewById(R.id.textView1);
	        t.setText(tags);
    	}
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
