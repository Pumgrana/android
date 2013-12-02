package com.example.pumgrana;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String [] contents = getResources().getStringArray(R.array.contents);
        ListView l= (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contents);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        		Intent intent = new Intent(parent.getContext(), Content.class);
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
    
}
