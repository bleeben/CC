package cc.main;

import java.util.ArrayList;

import cc.rep.Collection;
import cc.rep.ImageAdapter;
import cc.rep.Item;
import cc.rep.ResultCode;
import cc.rep.Tag;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class CollectionActivity extends Activity {
	Collection c;
	GridView gridColls;
	int position;
	static final int BROWSE_ITEM = 4;
	TextView collectionName;
	static final int EDIT_COLLECTION_PROPERTIES = 5;
	static final int SHARE_COLLECTION = 6;
	EditText filterEdit;
	ImageAdapter adapter;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);
        
        Intent i = getIntent();
        c = (Collection) i.getParcelableExtra("collection");
        position = i.getIntExtra("position", 0);
        
        adapter = new ImageAdapter(this,getLayoutInflater(),c.getItems());
        gridColls = (GridView) findViewById(R.id.gridView1);
        gridColls.setAdapter(adapter);
        
        gridColls.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
 
                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), ItemActivity.class);
                // passing array index
                i.putExtra("position", position);
                i.putExtra("collection", c);
                i.putExtra("item", c.getItem(position));
                startActivityForResult(i,BROWSE_ITEM);
            }
        });
        this.registerForContextMenu(gridColls);
        
        collectionName = (TextView) findViewById(R.id.collectionName);
        collectionName.setText(c.getName());
        
        filterEdit = (EditText) findViewById(R.id.filterText);
        filterEdit.clearFocus();
    }
    //hi
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_collection, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.collectionThumb:
	            //editNote(info.id);
	            return true;
	        case R.id.addDesc:
	        	return true;
	        case R.id.renameItem:
	        	return true;
	        case R.id.deleteItem:
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
    
    public void onNewItemButtonClick(View view) {
    	Intent intent = new Intent(this, NewItemActivity.class);
    	intent.putExtra("totalNum", c.size());
    	intent.putExtra("collection", c);
    	startActivityForResult(intent, ResultCode.NEW_ITEM_REQUEST);

    }
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
    	switch (requestCode) {
    	case ResultCode.NEW_ITEM_REQUEST:
    		switch (resultCode) {
    		case RESULT_OK:
    			Item newItem = (Item) data.getParcelableExtra("item");
    			c.addItem(newItem);
    			((BaseAdapter) gridColls.getAdapter()).notifyDataSetChanged();
    			CCActivity.alert(this, "Num Items: "+c.size());
    			break;
    		case RESULT_CANCELED:
    			break;
    		}
    		break;
    	case BROWSE_ITEM:
    		switch (resultCode) {
    		case Activity.RESULT_OK:
    			Item item = data.getParcelableExtra("item");
    			int pos = data.getIntExtra("position", 0);
    			c.setItem(pos, item);
    			//collections = c;
    			break;
    		}
    		break;
    	case EDIT_COLLECTION_PROPERTIES:
    		switch (resultCode) {
    		case Activity.RESULT_OK:
    			Collection collection = data.getParcelableExtra("collection");
    			c = collection;
    			break;
    		}
    		break;
    	case SHARE_COLLECTION:
    		switch (resultCode) {
    		case Activity.RESULT_OK:
    			Collection collection = data.getParcelableExtra("collection");
    			c = collection;
    			break;
    		}
    		break;
    	}
    }
    public void editProperties(View view){
    	editProperties();
    }
    
    public void editProperties(){
    	Intent intent = new Intent(this, CollectionPropertiesActivity.class);
    	intent.putExtra("collection", c);
    	startActivityForResult(intent,EDIT_COLLECTION_PROPERTIES );
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	CCActivity.alert(this, "Leaving Collections");
    	
    	Intent intent = new Intent();
    	intent.putExtra("collection", c);
    	intent.putExtra("position", position);
    	setResult(Activity.RESULT_OK, intent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.collection, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.properties:
            	editProperties();
                return true;
            case R.id.share:
            	onShareButtonClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void filter(View view){
    	Tag filter = new Tag(filterEdit.getText().toString());

//        adapter = new ImageAdapter(this,getLayoutInflater(),c.getMatches(filter));        
    	adapter.setC(this.c.getMatches(filter));
//        gridColls.setAdapter(adapter);
    	adapter.notifyDataSetChanged();
    	gridColls.invalidateViews();
    }
    
    public void onShareButtonClick(View view){
    	onShareButtonClick();
    }
    public void onShareButtonClick(){
    	Intent intent = new Intent(this, SharingManagerActivity.class);
    	intent.putExtra("collection", c);
    	startActivityForResult(intent,SHARE_COLLECTION);
    }
}