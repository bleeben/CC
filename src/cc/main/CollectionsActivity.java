package cc.main;

import java.util.ArrayList;

import java.util.List;

import cc.rep.Collection;
import cc.rep.ImageAdapter;
import cc.rep.Item;
import cc.rep.Tag;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CollectionsActivity extends Activity{
	
	static final int NEW_COLLECTION_REQUEST = 0;
	static final int BROWSE_COLLECTION = 3;
	
	private ArrayList<Collection> collections = new ArrayList<Collection>();;
	GridView gridColls;
	Collection recentItems;
	
	EditText filterEdit;
	ImageAdapter adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collections);
        
        Intent i = getIntent();
        collections = i.getParcelableArrayListExtra("collections");
        recentItems = i.getParcelableExtra("recentItems");
        
        adapter = new ImageAdapter(this,getLayoutInflater(),collections);
        gridColls = (GridView) findViewById(R.id.gridView1);
        gridColls.setAdapter(adapter);

        filterEdit = (EditText) findViewById(R.id.filterText);
        filterEdit.clearFocus();
        
        gridColls.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
 
                openCollectionActivity(position);
                
            }
        });
        
        this.registerForContextMenu(gridColls);
        
        filterEdit = (EditText) findViewById(R.id.filterText);
        filterEdit.clearFocus();
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
        
        boolean toBrowseInner = i.getBooleanExtra("toBrowseInner", false);
        if (toBrowseInner) {
        	int position = i.getIntExtra("position", 0);
        	openCollectionActivity(position);
        }
    }
    
    public void openCollectionActivity(int position) {
    	Intent i = new Intent(getApplicationContext(), CollectionActivity.class);
        // passing array index
        i.putExtra("position", position);
        i.putParcelableArrayListExtra("collections", collections);
        i.putExtra("collection", collections.get(position));
        i.putExtra("filter", filterEdit.getText().toString());
        i.putExtra("recentItems", recentItems);
        startActivityForResult(i,BROWSE_COLLECTION);
    }
    
    public void editCollectionActivity(int position) {
    	Intent i = new Intent(getApplicationContext(), CollectionActivity.class);
        // passing array index
        i.putExtra("position", position);
        i.putParcelableArrayListExtra("collections", collections);
        i.putExtra("collection", collections.get(position));
        i.putExtra("filter", filterEdit.getText().toString());
        i.putExtra("toEdit",true);
        startActivityForResult(i,BROWSE_COLLECTION);
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_collections, menu);
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	    Collection seleColl = this.collections.get(info.position);
	    menu.setHeaderTitle("Collection: "+seleColl.getName());
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    Collection selectedColl = collections.get(info.position);
	    switch (item.getItemId()) {
	    	case R.id.removeThumb:
	    		selectedColl.setPicUri(null);
	    		((BaseAdapter) gridColls.getAdapter()).notifyDataSetChanged();
	        	CCActivity.notify(this, "Removed Thumbnail");
	        	return true;
	        case R.id.editColl:
	        	editCollectionActivity(info.position);
	        	return true;
	        case R.id.deleteColl:
	        	collections.remove(selectedColl);
	        	((ImageAdapter) gridColls.getAdapter()).setC(collections);
	        	((BaseAdapter) gridColls.getAdapter()).notifyDataSetChanged();
	        	CCActivity.notify(this, "Removed Collection "+selectedColl.getName());
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
    
    public void onNewCollectionButtonClick(View view) {
    	Intent intent = new Intent(this, NewCollectionActivity.class);
    	intent.putExtra("totalNum", collections.size());
    	startActivityForResult(intent, NEW_COLLECTION_REQUEST);
    }
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
    	switch (requestCode) {
    	case NEW_COLLECTION_REQUEST:
    		switch (resultCode) {
    		case RESULT_OK:
    	        Collection newColl = (Collection) data.getParcelableExtra("collection");
    	        collections.add(newColl);
    	        ((ImageAdapter) gridColls.getAdapter()).setC(collections);
    	        ((BaseAdapter) gridColls.getAdapter()).notifyDataSetChanged();
    	        CCActivity.alert(this, "Num Collections: "+collections.size());
    	        openCollectionActivity(collections.indexOf(newColl));
    			break;
    		case RESULT_CANCELED:
    			break;
    		}
    		break;
    	case BROWSE_COLLECTION:
    		switch (resultCode) {
    		case Activity.RESULT_OK:
    			Collection c = data.getParcelableExtra("collection");
    			recentItems = data.getParcelableExtra("recentItems");
    			int pos = data.getIntExtra("position", 0);
    			collections.set(pos, c);
    			((ImageAdapter) gridColls.getAdapter()).setC(collections);
    			((BaseAdapter) gridColls.getAdapter()).notifyDataSetChanged();
    			break;
    		}
    		break;
    	}
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	CCActivity.alert(this, "Leaving Collections");
    	
    	Intent intent = new Intent();
    	intent.putExtra("collections", collections);
    	intent.putExtra("recentItems", recentItems);
    	setResult(Activity.RESULT_OK, intent);
    }
    	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.collections, menu);
        return true;
    }


	public void filter() {
		filter(null); //TODO - IS THIS OKAY?!?!?!?
	}
	
    public void filter(View view){
    	Tag filter = new Tag(filterEdit.getText().toString());

//        adapter = new ImageAdapter(this,getLayoutInflater(),c.getMatches(filter));        
    	adapter.setC(Collection.getMatches(collections,filter));
//        gridColls.setAdapter(adapter);
    	adapter.notifyDataSetChanged();
    	gridColls.invalidateViews();
    }
    
    public void clearFilter(View view){
    	filterEdit.setText("");
    	filter();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.share:
            	
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}