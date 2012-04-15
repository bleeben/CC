package cc.main;

import cc.rep.Collection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CollectionActivity extends Activity {
    /** Called when the activity is first created. */
	static final int NEW_ITEM = 100;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);
    }
    //hi
    
    public void onNewItemButtonClick(View view) {
    	Intent intent = new Intent(this, NewItemActivity.class);
    	startActivityForResult(intent, NEW_ITEM);
    }
    
    public void editProperties(View view){
    	Intent intent = new Intent(this, CollectionPropertiesActivity.class);
    	intent.putExtra("collection", new Collection());
    	startActivity(intent);
    }
    

    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == NEW_ITEM) {
            if (resultCode == RESULT_OK) {
                // A new item was made
            }
        }
    }
}