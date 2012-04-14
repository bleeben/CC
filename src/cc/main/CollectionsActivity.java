package cc.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CollectionsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collections);
    }
    //hi
    
    public void onNewCollectionButtonClick(View view) {
    	Intent intent = new Intent(this, NewCollectionActivity.class);
    	startActivity(intent);
    }
}