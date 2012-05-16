package cc.main;

import java.util.ArrayList;
import java.util.List;

import contentprovider.MainContentProvider;

import cc.rep.Collection;
import cc.rep.CollectionOpenHelper;
import cc.rep.Item;
import cc.rep.Sharer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

public class CCActivity extends Activity {
	
	 static final int NEW_COLLECTION_REQUEST = 0;
	 static final int NEW_ITEM_REQUEST = 1;
	 static final int BROWSE_COLLECTIONS = 2;
	 static final int BROWSE_COLLECTION = 3;
	 static final boolean PERSISTENT_ON = false;

	private ArrayList<Collection> collections = new ArrayList<Collection>();
	private Collection recentItems = new Collection("Recent Items");
	 
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
		 //alert(context,"Debugging",msg);
		 //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	 }
	 
	 public static void notify(Context context,String msg) {
		 //alert(context,"Debugging",msg);
		 Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	 }
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        if (CCActivity.PERSISTENT_ON) {
        //populate collections:
        String [] columns = CollectionOpenHelper.ALL_COLUMNS;
        Cursor cCursor = getContentResolver().query(MainContentProvider.CONTENT_URI_C, columns, null, new String[0], null);
        if (cCursor == null){
        	android.util.Log.e(ACTIVITY_SERVICE, "CCActivity cursor is null");
        	throw new RuntimeException("Cursor failed in CCActivity");
        }
        
        while (cCursor.moveToNext()){
        	Collection newCol = Collection.cursorToCollection(cCursor);
        	collections.add(newCol);
        }
        }
        
        if (collections.size()==0)
        	addSampleData();
        
    }
    //hi
    
    public void onRecentButtonClick(View view) {
    	if(recentItems.size() == 0){
    		CCActivity.notify(this, getString(R.string.noRecentItems));
    	} else {
        	Intent i = new Intent(getApplicationContext(), CollectionActivity.class);
            i.putExtra("collection", recentItems);
            startActivityForResult(i,BROWSE_COLLECTION);
    	}
    }
    
    public void onBrowseCollectionsButtonClick(View view) {
    	Intent intent = new Intent(this, CollectionsActivity.class);
    	CCActivity.alert(this,"Entering Collections From Home");
    	intent.putParcelableArrayListExtra("collections", collections);
    	intent.putExtra("recentItems", recentItems);
    	startActivityForResult(intent,BROWSE_COLLECTIONS);
    	//startActivity(intent);
    }
    
    public void onBrowseCollectionsButtonClick(View view,int position) {
    	Intent intent = new Intent(this, CollectionsActivity.class);
    	CCActivity.alert(this,"Entering Collections From Home");
    	intent.putParcelableArrayListExtra("collections", collections);
    	intent.putExtra("recentItems", recentItems);
    	intent.putExtra("position", position);
        intent.putExtra("toBrowseInner",true);
    	startActivityForResult(intent,BROWSE_COLLECTIONS);
    	//startActivity(intent);
    }
    
    public void onNewItemButtonClick(View view) {
    	Intent intent = new Intent(this, NewItemActivity.class);
    	intent.putExtra("totalNum", collections.size());
    	intent.putParcelableArrayListExtra("collections", collections);
    	startActivityForResult(intent, NEW_ITEM_REQUEST);
    }
    
    public void onNewCollectionButtonClick(View view) {
    	Intent intent = new Intent(this, NewCollectionActivity.class);
    	intent.putExtra("totalNum", collections.size());
    	intent.putExtra("recentItems", recentItems); // If new collections goes straight to that screen.
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
    	        CCActivity.alert(this, "Num Collections: "+collections.size());
    	        onBrowseCollectionsButtonClick(null,collections.indexOf(newColl));
    			break;
    		}
    		break;
    	case NEW_ITEM_REQUEST:
    		switch (resultCode) {
    		case Activity.RESULT_OK:
    			Item newItem = (Item) data.getParcelableExtra("item");
    			int position = data.getIntExtra("position", 0);
    			recentItems.recentAdd(newItem);
    			if (collections.size()>0) {
    				collections.get(position).addItem(newItem);
    				onBrowseCollectionsButtonClick(null,position);
    			}
    			break;
    		}
    		break;
    	case BROWSE_COLLECTIONS:
    		switch (resultCode) {
    		case Activity.RESULT_OK:
    			ArrayList<Collection> c = data.getParcelableArrayListExtra("collections");
    			collections = c;
    			recentItems = data.getParcelableExtra("recentItems");
    			break;
    		}
    		break;
    	}
    }

	public static void alert(NewCollectionActivity newCollectionActivity,
			String string) {
		// TODO Auto-generated method stub
		
	}

	public static void alert(OnClickListener onClickListener, String msg) {
		// TODO Auto-generated method stub
		
	}
	
	public void addSampleData(){
		Item item;
		Collection collection;
		
		
		collection = new Collection("Animals");
		
		item = new Item("Dog");
		item.addTag("Dogs");
		item.addTag("Mammal");
		item.addTag("Pet");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Cat");
		item.addTag("Cats");
		item.addTag("Mammal");
		item.addTag("Pet");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Elephant");
		item.addTag("Elephants");
		item.addTag("Mammal");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Giraffe");
		item.addTag("Giraffes");
		item.addTag("Mammal");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Loch Ness");
		item.addTag("Monster");
		item.addTag("Imaginary");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Penguin");
		item.addTag("Penguins");
		item.addTag("Bird");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Flamingo");
		item.addTag("Flamingos");
		item.addTag("Bird");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Hamster");
		item.addTag("Hamsters");
		item.addTag("Mammal");
		item.addTag("Pet");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		
		item = new Item("Whale");
		item.addTag("Whales");
		item.addTag("Ocean");
		item.addTag("Fish");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Snake");
		item.addTag("Snakes");
		item.addTag("Reptile");
		item.addTag("Scary");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		collections.add(collection);
		
		
		collection = new Collection("Games");
		
		item = new Item("Settlers of Catan");
		item.addTag("Board Game");
		item.addTag("Random");
		item.addTag("Anger");
		item.addTag("Exploration");
		item.addTag("Resource");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Dominion");
		item.addTag("Card Game");
		item.addTag("too good");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Munchkin");
		item.addTag("Card Game");
		item.addTag("Fun");
		item.addTag("RPG");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Deus Ex");
		item.addTag("Video Game");
		item.addTag("RPG");
		item.addTag("Action");
		item.addTag("Adventure");
		item.addTag("Eidos");
		item.addTag("Square Enix");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		
		item = new Item("D&D");
		item.addTag("Role-Playing");
		item.addTag("RPG");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Mario Kart");
		item.addTag("Video Game");
		item.addTag("Console");
		item.addTag("Nintendo");
		item.addTag("Wii");
		collection.addItem(item);
		recentItems.recentAdd(item);

		item = new Item("Brawl");
		item.addTag("Video Game");
		item.addTag("Console");
		item.addTag("Nintendo");
		item.addTag("Wii");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		
		item = new Item("Sonic");
		item.addTag("Video Game");
		item.addTag("Console");
		item.addTag("Sega");
		item.addTag("Wii");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		
		item = new Item("Monopoly");
		item.addTag("Bored Game");
		item.addTag("Board Game");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		collections.add(collection);
		
		
		collection = new Collection("Shoes");
		
		item = new Item("York");
		item.addTag("Mens");
		item.addTag("Black");
		item.addTag("Boots");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Court");
		item.addTag("Mens");
		item.addTag("Sneaker");
		item.addTag("Red");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Stroll");
		item.addTag("Mens");
		item.addTag("Dress");
		item.addTag("Black");
		item.addTag("Leather");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Kicks");
		item.addTag("White");
		item.addTag("Sneakers");
		item.addTag("Boys");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Couch");
		item.addTag("Sandals");
		item.addTag("Brown");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Party");
		item.addTag("Womens");
		item.addTag("Heels");
		item.addTag("Taupe");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Street");
		item.addTag("Womens");
		item.addTag("Heels");
		item.addTag("Red");
		item.addTag("Patent");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("High");
		item.addTag("Womens");
		item.addTag("Sandals");
		item.addTag("White");
		item.addTag("Platform");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Lounge");
		item.addTag("Womens");
		item.addTag("Sandals");
		item.addTag("Lime");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("Rider");
		item.addTag("Womens");
		item.addTag("Boots");
		item.addTag("Brown");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		collections.add(collection);
		
		
		collection = new Collection("Emoticons");
		
		item = new Item(":]");
		item.addTag("Smiley");
		item.addTag("Happy");
		item.addTag("Face");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item(":)");
		item.addTag("Smiley");
		item.addTag("Happy");
		item.addTag("Face");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item(":D");
		item.addTag("Smiley");
		item.addTag("Excited");
		item.addTag("Happy");
		item.addTag("Face");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item(":O");
		item.addTag("Surprised");
		item.addTag("Face");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item(":[");
		item.addTag("Frowny");
		item.addTag("Sad");
		item.addTag("Face");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item(":(");
		item.addTag("Frowny");
		item.addTag("Sad");
		item.addTag("Face");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("[:|]");
		item.addTag("Robot");
		item.addTag("Face");
		item.addTag("Neutral");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("<3");
		item.addTag("Love");
		item.addTag("Symbol");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("C>");
		item.addTag("Ice Cream");
		item.addTag("Symbol");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item(":|");
		item.addTag("Face");
		item.addTag("Neutral");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("v.V.v");
		item.addTag("Crab");
		item.addTag("Crabby");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		item = new Item("D:");
		item.addTag("Oh noooo");
		item.addTag("Face");
		collection.addItem(item);
		recentItems.recentAdd(item);
		
		collections.add(collection);
	}
}