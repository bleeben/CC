package cc.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CCActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }
    //hi
    
    public void onBrowseCollectionsButtonClick(View view) {
    	Intent intent = new Intent(this, CollectionsActivity.class);
    	startActivity(intent);
    }
    
    public void onNewItemButtonClick(View view) {
    	Intent intent = new Intent(this, NewItemActivity.class);
    	startActivity(intent);
    }
    
    public void onNewCollectionsButtonClick(View view) {
    	Intent intent = new Intent(this, CollectionPropertiesActivity.class);
    	startActivity(intent);
    }
}