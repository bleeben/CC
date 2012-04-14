package cc.main;

import cc.rep.Collection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CollectionActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);
    }
    //hi
    
    public void onNewItemButtonClick(View view) {
    	Intent intent = new Intent(this, NewItemActivity.class);
    	startActivity(intent);
    }
    
    public void editProperties(View view){
    	Intent intent = new Intent(this, CollectionPropertiesActivity.class);
    	intent.putExtra("collection", new Collection());
    	startActivity(intent);
    }
    
}