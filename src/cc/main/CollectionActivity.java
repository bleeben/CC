package cc.main;

import cc.rep.Collection;
import cc.rep.Item;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class CollectionActivity extends Activity {
	
	 static final int NEW_ITEM_REQUEST = 1;
	
	Collection c;
	
    /** Called when the activity is first created. */
	static final int NEW_ITEM = 100;
	
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
<<<<<<< HEAD
    	startActivityForResult(intent, NEW_ITEM);
=======
    	startActivityForResult(intent, NEW_ITEM_REQUEST);
>>>>>>> 26b4faa94b913486614f808bff119872998c2be6
    }
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
    	switch (requestCode) {
    	case NEW_ITEM_REQUEST:
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
    
<<<<<<< HEAD

    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == NEW_ITEM) {
            if (resultCode == RESULT_OK) {
                // A new item was made
            }
=======
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
>>>>>>> 26b4faa94b913486614f808bff119872998c2be6
        }
    }
}