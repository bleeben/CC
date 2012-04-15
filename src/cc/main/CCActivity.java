package cc.main;

import cc.rep.Collection;
import cc.rep.Item;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CCActivity extends Activity {
	
	 static final int NEW_COLLECTION_REQUEST = 0;
	 static final int NEW_ITEM_REQUEST = 1;
	 static AlertDialog ALERT;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        ALERT = new AlertDialog.Builder(this).create();
        ALERT.setTitle("Debugging");
        ALERT.setMessage("Home Screen");
        ALERT.setButton("OK", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
              // here you can add functions
           }
        });
        ALERT.setIcon(R.drawable.ic_launcher);
        ALERT.show();
    }
    //hi
    
    public void onBrowseCollectionsButtonClick(View view) {
    	Intent intent = new Intent(this, CollectionsActivity.class);
    	CCActivity.ALERT.setMessage("Entering Collections From Home");
        CCActivity.ALERT.show();
    	startActivity(intent);
    }
    
    public void onNewItemButtonClick(View view) {
    	Intent intent = new Intent(this, NewItemActivity.class);
    	startActivityForResult(intent, NEW_ITEM_REQUEST);
    }
    
    public void onNewCollectionButtonClick(View view) {
    	Intent intent = new Intent(this, NewCollectionActivity.class);
    	startActivityForResult(intent, NEW_COLLECTION_REQUEST);
    }
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
    	switch (requestCode) {
    	case NEW_COLLECTION_REQUEST:
    		switch (resultCode) {
    		case RESULT_OK:
    	        Collection newColl = (Collection) data.getParcelableExtra("collection");
    			break;
    		case RESULT_CANCELED:
    			break;
    		}
    		break;
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
}