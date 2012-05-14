package cc.main;

import java.util.ArrayList;

import contentprovider.MainContentProvider;

import cc.rep.Collection;
import cc.rep.Item;
import cc.rep.ItemOpenHelper;
import cc.rep.ResultCode;
import cc.rep.SpinnerListAdapter;
import cc.rep.Tag;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends Activity {
	
	Item item;
	int position;
	TextView itemName;
    Spinner collectionSpinner;
    Gallery tagGallery;
    ArrayAdapter<Tag> tagArr;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);
        
        Intent intent = getIntent();
        item = (Item) intent.getParcelableExtra("item");
        position = intent.getIntExtra("position", 0);

        itemName = (TextView)findViewById(R.id.textViewItemName);
        itemName.setText(item.getName());

        collectionSpinner = (Spinner) findViewById(R.id.collectionSpinner);
        
        Collection c = (Collection) intent.getParcelableExtra("collection");
        ArrayList<Collection> cs = intent.getParcelableArrayListExtra("collections");
        if (cs==null) {
        	collectionSpinner.setAdapter(new SpinnerListAdapter(this,getLayoutInflater(),c));
        } else {
        	collectionSpinner.setAdapter(new SpinnerListAdapter(this,getLayoutInflater(),cs));
        }
        
        tagGallery = (Gallery) findViewById(R.id.galleryTags);
        tagArr=new ArrayAdapter<Tag>(this, android.R.layout.simple_gallery_item, item.getTags());
        tagGallery.setAdapter(tagArr);
        
        
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        if (item.getPicUri()!=null)
        	image.setImageURI(item.getPicUri());
		
    }
        
    public void onCancelButtonClick(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	item.setName(itemName.getText().toString());
    	Intent intent = new Intent();
    	//save the item
    	int updatedNum;
    	ContentValues v = item.makeContentValues();
    	updatedNum = getContentResolver().update(MainContentProvider.CONTENT_URI_I, v, ItemOpenHelper.COLUMN_ID + " = " + item.getID(), new String[0]);
    	Log.i(ACTIVITY_SERVICE, "Updated items " + updatedNum);
    	
    	intent.putExtra("item", item);
    	intent.putExtra("position", position);
    	setResult(Activity.RESULT_OK, intent);
    	finish();
    }
    
    public void onAddTag(View view){
        LayoutInflater factory = LayoutInflater.from(this);
        final View add_tag = factory.inflate(R.layout.add_tag, null);
        final EditText editTag = (EditText) add_tag.findViewById(R.id.editTextTag);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
//           .setIconAttribute(android.R.attr.alertDialogIcon)
            .setTitle(R.string.addTagTitle)
            .setView(add_tag)
            .setPositiveButton(R.string.addTagOK, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	item.addTag(editTag.getText().toString());
                	updateTagAdapter();
                }
            })
            .setNegativeButton(R.string.addTagCancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            })
            .create();
        alertDialog.show();
    }
    
    public void updateTagAdapter(){
    	tagArr=new ArrayAdapter<Tag>(this, android.R.layout.simple_gallery_item, item.getTags());
        tagGallery.setAdapter(tagArr);
    }

	@Override
	protected void onPause() {
		super.onPause();

    	CCActivity.alert(this, "Leaving Item");
    	Intent intent = new Intent();
    	intent.putExtra("item", item);
    	intent.putExtra("position", position);
    	setResult(Activity.RESULT_OK, intent);

	}
    
	public void takePicture(View view) {
		Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, ResultCode.CAMERA_PIC_REQUEST);
	}

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if (requestCode == ResultCode.CAMERA_PIC_REQUEST){
    		CCActivity.notify(this, "Image saved to:\n" + data.getData());
    		Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
    		ImageView image = (ImageView) findViewById(R.id.imageView1);
    		//image.setImageURI(data.getData());
    		item.setPicUri(data.getData());
			image.setImageBitmap(thumbnail);
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.description:
            	
                return true;
            case R.id.summary:
            	
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}