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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CollectionsActivity extends Activity {
	
	static final int NEW_COLLECTION_REQUEST = 0;
	static final int BROWSE_COLLECTION = 3;
	
	private ArrayList<Collection> collections = new ArrayList<Collection>();;
	GridView gridColls;
	
	EditText filterEdit;
	ImageAdapter adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collections);
        
        Intent i = getIntent();
        collections = i.getParcelableArrayListExtra("collections");
        
        adapter = new ImageAdapter(this,getLayoutInflater(),collections);
        gridColls = (GridView) findViewById(R.id.gridView1);
        gridColls.setAdapter(adapter);
        
        gridColls.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
 
                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), CollectionActivity.class);
                // passing array index
                i.putExtra("position", position);
                i.putParcelableArrayListExtra("collections", collections);
                i.putExtra("collection", collections.get(position));
                startActivityForResult(i,BROWSE_COLLECTION);
            }
        });
        
        filterEdit = (EditText) findViewById(R.id.filterText);
        filterEdit.clearFocus();
    }
    //hi
    
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
    	        ((BaseAdapter) gridColls.getAdapter()).notifyDataSetChanged();
    	        CCActivity.alert(this, "Num Collections: "+collections.size());
    			break;
    		case RESULT_CANCELED:
    			break;
    		}
    		break;
    	case BROWSE_COLLECTION:
    		switch (resultCode) {
    		case Activity.RESULT_OK:
    			Collection c = data.getParcelableExtra("collection");
    			int pos = data.getIntExtra("position", 0);
    			collections.set(pos, c);
    			//collections = c;
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
    	setResult(Activity.RESULT_OK, intent);
    }
    	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.collections, menu);
        return true;
    }
    
    public void filter(View view){
    	Tag filter = new Tag(filterEdit.getText().toString());

//        adapter = new ImageAdapter(this,getLayoutInflater(),c.getMatches(filter));        
    	adapter.setC(Collection.getMatches(collections,filter));
//        gridColls.setAdapter(adapter);
    	adapter.notifyDataSetChanged();
    	gridColls.invalidateViews();
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