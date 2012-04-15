package cc.main;

import cc.rep.Collection;
import cc.rep.Item;
import cc.rep.ResultCode;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class CollectionActivity extends Activity {
	Collection c;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);
        
        Intent i = getIntent();
        c = (Collection) i.getParcelableExtra("collection");
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
}