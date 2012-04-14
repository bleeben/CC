package cc.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class NewCollectionActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_properties);
    }
    //hi
    
    
    public void onCancelButtonClick(View view) {
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	finish();
    }
}