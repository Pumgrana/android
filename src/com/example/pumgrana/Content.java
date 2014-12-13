package com.example.pumgrana;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.pumgrana.MainActivity.MainFragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * Show the details of a content.
 */
public class Content extends Activity {
	
	/** The name. */
	public String name;
	
	/** The uri. */
	public String uri;
	
	/** The id. */
	public String id;
	
	/** The str. */
	public String str = "[a]";
	
	/** The m. */
	public Misc m;
	
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
		setContentView(R.layout.activity_content);
		
        mTitle = mDrawerTitle = getTitle();
		m = new Misc(this);
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLinearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        mDrawerList = (ListView) findViewById(R.id.listView1);

		//setupActionBar();
		Log.i("attention", "crash");
		name = getIntent().getExtras().getString("name");
		uri = getIntent().getExtras().getString("uri");
		
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
        
        Fragment fragment = new MainFragment(uri);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerToggle.syncState();
/*		updateContent();
		Log.i("attention", "crashed");
//		id = getIntent().getExtras().getString("ids");
		getDetails(name);*/
//        new HttpAsyncTask().execute("http://163.5.84.222/api/content/detail/".concat(id));
	}
	
	public static class MainFragment extends Fragment {
        ArrayAdapter<String> adapter;
        List<String> dataUri;
        String uri;
        public MainFragment(String main_uri) {
        	uri = main_uri;
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.content_view, container, false);
            updateViewNoDB(rootView);
            return rootView;
        }
        
        public void updateViewNoDB(View rootView) {
        	Requests req = new Requests();
        	Log.d("fetchData", "req");
    		try {
    			req.execute("get", "http://" + "163.5.84.222" + "/api/content/detail/" + URLEncoder.encode(uri, "utf-8"));
    			String result = req.get(3, TimeUnit.SECONDS);
    			JSONObject jObject = new JSONObject(result);
    			JSONArray jObj = jObject.getJSONArray("contents");
    			for (int i=0; i < jObj.length(); i++) {
    		        TextView t = (TextView) rootView.findViewById(R.id.textView1);
    				t.setText(jObj.getJSONObject(i).getString("title"));
    				TextView t2 = (TextView) rootView.findViewById(R.id.textView2);
    				t2.setText(Html.fromHtml((jObj.getJSONObject(i)).getString("body")));
    		        t2.setMovementMethod(new ScrollingMovementMethod());
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
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
        
    }
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.content, menu);
		return true;
	}
	
	/**
	 * Gets the details.
	 *
	 * @param name the name
	 * @return the details
	 */
	private void getDetails(String name) {
		TextView t = (TextView) findViewById(R.id.textView1);
		t.setText(name);
    	PumgranaDB dataDB = new PumgranaDB(this);
		dataDB.open();
		Data data = dataDB.getData(name);
		id = data.getDataId();
		dataDB.close();
		TextView t2 = (TextView) findViewById(R.id.textView2);
		t2.setText(data.getText());
	}
	
	/**
	 * Gets the details no db.
	 *
	 * @param uri the uri
	 * @return the details no db
	 */
	private void getDetailsNoDB(String uri) {
    	Requests req = new Requests();
    	Log.d("fetchData", "req");
		try {
			req.execute("get", "http://" + m.getIp() + "/api/content/detail/" + URLEncoder.encode(uri, "utf-8"));
			String result = req.get(3, TimeUnit.SECONDS);
			JSONObject jObject = new JSONObject(result);
			JSONArray jObj = jObject.getJSONArray("contents");
			for (int i=0; i < jObj.length(); i++) {
		        TextView t = (TextView) findViewById(R.id.textView1);
				t.setText(jObj.getJSONObject(i).getString("title"));
				TextView t2 = (TextView) findViewById(R.id.textView2);
				t2.setText(Html.fromHtml((jObj.getJSONObject(i)).getString("body")));
		        t2.setMovementMethod(new ScrollingMovementMethod());
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
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Update content.
	 */
	public void updateContent() {
    	PumgranaDB dataDB = new PumgranaDB(this);
		dataDB.open();
    	Requests req = new Requests();
    	Log.d("fetchData", "req");
        try {
			req.execute("get", "http://" + m.getIp() + "/api/content/detail/" + URLEncoder.encode(uri, "utf-8"));
        	Log.d("fetchData", "try");        	
			String result = req.get(3, TimeUnit.SECONDS);
			Log.d("Result", result);
			if (result == null) {
				dataDB.close();
				return;
			}
			JSONObject jObject = new JSONObject(result);
			JSONArray jObj = jObject.getJSONArray("contents");
			for (int i=0; i < jObj.length(); i++) {
				Data data = new Data();
				data.setDataId((jObj.getJSONObject(i)).getString("uri"));
				data.setText((jObj.getJSONObject(i)).getString("body"));
				data.setTitle((jObj.getJSONObject(i)).getString("title"));
				dataDB.updateData(uri, data);
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
			

	}
	
	/**
	 * Open edit.
	 *
	 * @param view the view
	 */
	public void openEdit(View view)
	{
		Intent intent = new Intent(this, Content_edit.class);
    	intent.putExtras(getIntent().getExtras());
    	startActivityForResult(intent, 0);
	}

    /* (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	uri = data.getExtras().getString("uri");
    	getDetailsNoDB(uri);
    }
	
    /**
     * Open tags.
     *
     * @param view the view
     */
    public void openTags(View view)
    {
    	Intent intent = new Intent(this, TagShow.class);
		Bundle b = new Bundle();
		b.putString("name", name);
		b.putString("id", uri);
		intent.putExtras(b);
    	startActivity(intent);
    }
    
    /**
     * Open links.
     *
     * @param view the view
     */
    public void openLinks(View view)
    {
    	Intent intent = new Intent(this, Links.class);
    	Bundle b = new Bundle();
    	b.putString("id", uri);
    	intent.putExtras(b);
    	startActivity(intent);
    }
    
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			//finish();
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		NavUtils.navigateUpFromSameTask(this);
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
				JSONArray jObj = jObject.getJSONArray("contents");
				String text = jObj.getJSONObject(0).getString("text");
				TextView t = (TextView) findViewById(R.id.textView2);
				t.setText(text);
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
