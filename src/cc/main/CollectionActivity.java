package cc.main;

import cc.rep.Collection;
import cc.rep.ImageAdapter;
import cc.rep.Item;
import cc.rep.ResultCode;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class CollectionActivity extends Activity {
	Collection c;
	GridView gridColls;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);
        
        Intent i = getIntent();
        c = (Collection) i.getParcelableExtra("collection");
        
        gridColls = (GridView) findViewById(R.id.gridView1);
        gridColls.setAdapter(new ImageAdapter(this,getLayoutInflater(),c));
        
        gridColls.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
 
                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), ItemActivity.class);
                // passing array index
                i.putExtra("id", position);
                i.putExtra("collection", c);
                startActivity(i);
            }
        });
    }
    //hi
    
    public void onNewItemButtonClick(View view) {
    	Intent intent = new Intent(this, NewItemActivity.class);
    	startActivityForResult(intent, ResultCode.NEW_ITEM_REQUEST);

    }
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
    	switch (requestCode) {
    	case ResultCode.NEW_ITEM_REQUEST:
    		switch (resultCode) {
    		case RESULT_OK:
    			Item newItem = (Item) data.getParcelableExtra("item");
    			break;
    		case RESULT_CANCELED:
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
    	intent.putExtra("collection", new Collection());
    	startActivity(intent);
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
            	
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void onShareButtonClick(View view){
    	Intent intent = new Intent(this, SharingManagerActivity.class);
    	intent.putExtra("collection", c);
    	startActivity(intent);
    }
}