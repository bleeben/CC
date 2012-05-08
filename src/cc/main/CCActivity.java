package cc.main;

import java.util.ArrayList;
import java.util.List;

import cc.rep.Collection;
import cc.rep.Item;
import cc.rep.Sharer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

public class CCActivity extends Activity {
	
	 static final int NEW_COLLECTION_REQUEST = 0;
	 static final int NEW_ITEM_REQUEST = 1;

	private ArrayList<Collection> collections = new ArrayList<Collection>();;
	 
	 public static void alert(Context context,String title, String msg) {
		 AlertDialog aDial = new AlertDialog.Builder(context).create();
		 aDial.setTitle(title);
		 aDial.setMessage(msg);
		 aDial.setButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int which) {
	              // here you can add functions
	           }
	        });
		 aDial.setIcon(R.drawable.ic_launcher);
		 aDial.show(); 
	 }
	 
	 public static void alert(Context context,String msg) {
		 alert(context,"Debugging",msg);
	 }
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        CCActivity.alert(this,"Debugging","Home Screen");
    }
    //hi
    
    public void onBrowseCollectionsButtonClick(View view) {
    	Intent intent = new Intent(this, CollectionsActivity.class);
    	CCActivity.alert(this,"Entering Collections From Home");
    	intent.putParcelableArrayListExtra("collections", collections);
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
    		case Activity.RESULT_OK:
    	        Collection newColl = (Collection) data.getParcelableExtra("collection");
    	        collections.add(newColl);
    			break;
    		}
    		break;
    	case NEW_ITEM_REQUEST:
    		switch (resultCode) {
    		case Activity.RESULT_OK:
    			Item newItem = (Item) data.getParcelableExtra("item");
    			break;
    		}
    		break;
    	}
    }

	public static void alert(NewCollectionActivity newCollectionActivity,
			String string) {
		// TODO Auto-generated method stub
		
	}
}