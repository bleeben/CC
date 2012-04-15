package cc.main;

import cc.rep.Collection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewCollectionActivity extends Activity {
	
	Collection c;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_properties);
        
        c = new Collection();
        
    }
    //hi
    
    
    public void onCancelButtonClick(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	setResult(RESULT_OK);
    	Intent intent = new Intent();
    	intent.putExtra("collection", c);
    	setIntent(intent);
    	finish();
    }
}