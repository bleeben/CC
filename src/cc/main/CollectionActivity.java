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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class CollectionActivity extends Activity {
	Collection c;
	Collection recentItems;
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
        recentItems = i.getParcelableExtra("recentItems");
        
        position = i.getIntExtra("position", 0);
        if ("Recent Items".equals(c.getName()))
        	adapter = new ImageAdapter(this,getLayoutInflater(),c.getItems(),true);
        else
        	adapter = new ImageAdapter(this,getLayoutInflater(),c.getItems());
        gridColls = (GridView) findViewById(R.id.gridView1);
        gridColls.setAdapter(adapter);
        
        gridColls.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
 
                openItemActivity(position);
            }
        });
        this.registerForContextMenu(gridColls);
        
        collectionName = (TextView) findViewById(R.id.collectionName);
        collectionName.setText(c.getName());
        
        filterEdit = (EditText) findViewById(R.id.filterText);
        
        String filter = i.getStringExtra("filter");
        filterEdit.setText(filter != null ? filter : "");
        filterEdit.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				filter();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
        });
//        filterEdit.setOnKeyListener(new OnKeyListener() {
//        	@Override
//        	public boolean onKey(View v, int keyCode, KeyEvent event) {
//        		filter(v);
//				Toast.makeText(CollectionActivity.this,
//						"NewItemActivity: + cancel",
//						Toast.LENGTH_SHORT).show();
//        		return false;
//        	}
//        });
        filter();
        filterEdit.clearFocus();
        
        boolean toEdit = i.getBooleanExtra("toEdit", false);
        if (toEdit)
        	editProperties();
        
        collectionName.requestFocus();
        filterEdit.clearFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    //hi
	
	public int positionToNewPos(int position) {
    	int newPos = c.getItems().indexOf(adapter.getC().get(position));
        return newPos;
    }
	
	public void openItemActivity(int pos){
		pos = positionToNewPos(pos);
		Intent i = new Intent(getApplicationContext(), ItemActivity.class);
        // passing array index
        
        i.putExtra("collection", c);
        //int newPos = c.getItems().indexOf(adapter.getC().get(position));
        i.putExtra("position", pos);
        //i.putExtra("position", newPos);
        i.putExtra("item", c.getItem(pos));
        //i.putExtra("item", c.getItem(newPos));
        startActivityForResult(i,BROWSE_ITEM);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_collection, menu);
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	    Item seleItem = this.c.getItem(info.position);
	    menu.setHeaderTitle("Item: "+seleItem.getName());
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    Item selectedItem = c.getItem(info.position);
	    switch (item.getItemId()) {
	        case R.id.collectionThumb:
	            //editNote(info.id);
	        	c.setPicUri(selectedItem.getPicUri());
	        	CCActivity.notify(this, "Picture Set To Collection Preview");
	            return true;
	        case R.id.editItem:
	        	openItemActivity(info.position);
	        	return true;
	    	case R.id.removeThumb:
	    		selectedItem.setPicUri(null);
	    		((BaseAdapter) gridColls.getAdapter()).notifyDataSetChanged();
	        	CCActivity.notify(this, "Removed Thumbnail");
	        	return true;
	        case R.id.deleteItem:
	        	c.removeItem(selectedItem);
	        	((ImageAdapter) gridColls.getAdapter()).setC(c.getItems());
	        	((BaseAdapter) gridColls.getAdapter()).notifyDataSetChanged();
	        	CCActivity.notify(this, "Removed Item "+selectedItem.getName());
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
    	        recentItems.recentAdd(newItem);
    	        
    			((ImageAdapter) gridColls.getAdapter()).setC(c.getItems());
    			((BaseAdapter) gridColls.getAdapter()).notifyDataSetChanged();
    			CCActivity.alert(this, "Num Items: "+c.size());
    			filter();
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
    			((ImageAdapter) gridColls.getAdapter()).setC(c.getItems());
    			((BaseAdapter) gridColls.getAdapter()).notifyDataSetChanged();
    			filter();
    			break;
    		}
    		break;
    	case EDIT_COLLECTION_PROPERTIES:
    		switch (resultCode) {
    		case Activity.RESULT_OK:
    			Collection collection = data.getParcelableExtra("collection");
    			c.setName(collection.getName());
    			c.setDesc(collection.getDesc());
    			c.setPrivate(collection.isPrivate());
    			c.setSharers(collection.getSharers());
    			break;
    		}
    		break;
    	case SHARE_COLLECTION:
    		switch (resultCode) {
    		case Activity.RESULT_OK:
    			Collection collection = data.getParcelableExtra("collection");
    			c.setSharers(collection.getSharers());
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
    	
    	CCActivity.alert(this, "Leaving Collections");
    	
    	Intent intent = new Intent();
    	intent.putExtra("collection", c);
    	intent.putExtra("recentItems", recentItems);
    	//CCActivity.notify(this, filterEdit.getText().toString());
    	intent.putExtra("filterBack", filterEdit.getText().toString());
    	intent.putExtra("position", position);
    	setResult(Activity.RESULT_OK, intent);
    	super.onPause();
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
    
    public void filter(){
    	filter(null); //TODO - IS THIS OKAY?!?!?
    }
    public void filter(View view){
    	String filterStr = filterEdit.getText().toString();
    	if (filterStr==null || filterStr.length()==0) {
    		adapter.setC(this.c.getItems());
//          gridColls.setAdapter(adapter);
    		adapter.notifyDataSetChanged();
    		gridColls.invalidateViews();
    		return;
    	}
    	CCActivity.notify(this, "Filtering: "+filterStr);
    	Tag filter = new Tag(filterStr);

//        adapter = new ImageAdapter(this,getLayoutInflater(),c.getMatches(filter));        
    	adapter.setC(this.c.getMatches(filter));
//        gridColls.setAdapter(adapter);
    	adapter.notifyDataSetChanged();
    	gridColls.invalidateViews();
    }
    
    public void clearFilter(View view){
    	filterEdit.setText("");
    	filter();
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