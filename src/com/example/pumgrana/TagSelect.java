package com.example.pumgrana;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
 
public class TagSelect extends Activity implements
        OnClickListener {
    Button button;
    ListView listView;
    ArrayAdapter<String> adapter;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_select);
        Log.i("onCreate", "ok");
        String checked = this.getIntent().getExtras().getString("checked");
        Log.i("getStr", "ok");
        String [] array = checked.substring(1, checked.length() - 1).split(",");
        findViewsById();
        Log.i("array", "ok");
        for (String s : array)
        	Log.d("data", s);
    	PumgranaDB tagDB = new PumgranaDB(this);
    	tagDB.open();
    	List<Tag> listTag = tagDB.getAllTag();
    	Iterator<Tag> it = listTag.iterator();
		List<String> viewTag = new ArrayList<String>();
    	while (it.hasNext()) {
    		Tag tag = it.next();
    		viewTag.add(tag.getName());
    	}
    	tagDB.close();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, viewTag);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        int i = 0;
        for (String s : array)
        	if ((i = adapter.getPosition(s.trim())) != -1)
        			listView.setItemChecked(adapter.getPosition(s.trim()), true);
     }
 
    private void findViewsById() {
        listView = (ListView) findViewById(R.id.listView1);
    }
 
    public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }
 
        String[] outputStrArr = new String[selectedItems.size()];
        
        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }
        
        TextView t = (TextView) findViewById(R.id.textView1);
        t.setText(Arrays.toString(outputStrArr));
        Intent intent = new Intent(getApplicationContext(),
                ResultActivity.class);
 
        // Create a bundle object
        Bundle b = new Bundle();
        b.putStringArray("selectedItems", outputStrArr);
 
        // Add the bundle to the intent.
        intent.putExtras(b);
 
        // start the ResultActivity
        startActivity(intent);
    }
    public void onBackPressed() {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }
 
        String[] outputStrArr = new String[selectedItems.size()];
        
        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }
        String tags = Arrays.toString(outputStrArr);
        Bundle b = new Bundle();
        b.putString("Tags", tags);
        Intent mIntent = new Intent();
        mIntent.putExtras(b);
        TextView t = (TextView) findViewById(R.id.textView1);
        t.setText(Arrays.toString(outputStrArr));
        setResult(0, mIntent);
        super.onBackPressed();
    }
}