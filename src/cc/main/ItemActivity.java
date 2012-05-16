package cc.main;

import java.io.File;
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
import android.content.ContentResolver;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ItemActivity extends Activity {
	
	Item item;
	int position;
	TextView itemName;
    Spinner collectionSpinner;
    Gallery tagGallery;
    ArrayAdapter<Tag> tagArr;
    
    Uri imageUri;

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
        collectionSpinner.setPrompt("Located in Collection:");
        Collection c = (Collection) intent.getParcelableExtra("collection");
        ArrayList<Collection> cs = intent.getParcelableArrayListExtra("collections");
        if (cs==null) {
        	collectionSpinner.setAdapter(new SpinnerListAdapter(this,getLayoutInflater(),c));
        } else {
        	collectionSpinner.setAdapter(new SpinnerListAdapter(this,getLayoutInflater(),cs));
        }
        
        tagGallery = (Gallery) findViewById(R.id.galleryTags);
        updateTagAdapter();
        
		try {
			ImageView image = (ImageView) findViewById(R.id.imageView1);
			if (item.getPicUri() != null) {
				CCActivity.notify(this, "Picture Present");
				imageUri = item.getPicUri();
				ContentResolver cr = getContentResolver();
				Bitmap thumbnail = android.provider.MediaStore.Images.Media
						.getBitmap(cr, imageUri);
				CCActivity.notify(this, "Thumbnail Obtained");
				// image.setImageURI(data.getData());
				// item.setPicUri(imageUri);
				image.setImageBitmap(thumbnail);
			}
		} catch (Exception e) {
			CCActivity.notify(this, "Picture Failed to Load");
		}
		
		this.registerForContextMenu(tagGallery);
		itemName.requestFocus();
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_tags, menu);
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	    Tag seleTag = this.item.getTag(info.position);
	    menu.setHeaderTitle("Tag: "+seleTag.getText());
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    Tag selectedTag = this.item.getTag(info.position);
	    switch (item.getItemId()) {
	        case R.id.renameTag:
	            //editNote(info.id);
	        	onEditTag(info.position,selectedTag);
	        	//updateTagAdapter(); already done in onEditTag
	            return true;
	        case R.id.deleteTag:
	        	this.item.removeTag(info.position);
	        	updateTagAdapter();
	        	CCActivity.notify(this, "Removed Tag "+selectedTag.getText());
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}

        
    public void onCancelButtonClick(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	item.setName(itemName.getText().toString());
    	Intent intent = new Intent();
    	
    	if (CCActivity.PERSISTENT_ON) {
    	//save the item
    	int updatedNum;
    	ContentValues v = item.makeContentValues();
    	updatedNum = getContentResolver().update(MainContentProvider.CONTENT_URI_I, v, ItemOpenHelper.COLUMN_ID + " = " + item.getID(), new String[0]);
    	Log.i(ACTIVITY_SERVICE, "Updated items " + updatedNum);
    	}
    	
    	intent.putExtra("item", item);
    	intent.putExtra("position", position);
    	setResult(Activity.RESULT_OK, intent);
    	finish();
    }
    
	public void onEditTag(final int position,Tag selectedTag) {
		LayoutInflater factory = LayoutInflater.from(this);
		final View add_tag = factory.inflate(R.layout.add_tag, null);
		final EditText editTag = (EditText) add_tag
				.findViewById(R.id.editTextTag);
		editTag.setText(selectedTag.getText());
		AlertDialog alertDialog = new AlertDialog.Builder(this)
				// .setIconAttribute(android.R.attr.alertDialogIcon)
				.setTitle(R.string.renameTag)
				.setView(add_tag)
				.setPositiveButton(R.string.addTagOK,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								item.setTag(editTag.getText().toString(),position);
								updateTagAdapter();
							}
						})
				.setNegativeButton(R.string.addTagCancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								CCActivity.alert(ItemActivity.this,
										"NewItemActivity: + cancel");
							}
						}).create();
		alertDialog.show();
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
    	tagArr=new ArrayAdapter<Tag>(this, R.layout.tag_item, item.getTags());
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
    
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    if (imageUri != null) {
	        outState.putString("cameraImageUri", imageUri.toString());
	    }
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    if (savedInstanceState.containsKey("cameraImageUri")) {
	        imageUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
	    }
	}
	
	public void takePicture(View view) {
		File photo;
		if (android.os.Environment.getExternalStorageState().equals(
	            android.os.Environment.MEDIA_MOUNTED)) {
			photo = new File(Environment.getExternalStorageDirectory(),
	                "cc_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
	    } else {
	        photo = new File(getCacheDir(),
	                "cc_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
	    }
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	        
	    if (photo != null) {
	    	imageUri = Uri.fromFile(photo);
	        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	        cameraIntent.putExtra("return-data", true);
	        startActivityForResult(cameraIntent, ResultCode.CAMERA_PIC_REQUEST);
	    }
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ResultCode.CAMERA_PIC_REQUEST) {
			if (resultCode == RESULT_OK) {
				
				try {
	                Uri selectedImage = imageUri;
	                CCActivity.alert(this, "Image attempted to:\n" + selectedImage);
	                /*
	                if (data.getData()!=null)
	                	CCActivity.notify(this, "Image saved to:\n" + data.getData());
	                	*/
	                //getContentResolver().notifyChange(selectedImage, null);
	                ContentResolver cr = getContentResolver();
	                Bitmap thumbnail = android.provider.MediaStore.Images.Media
	                        .getBitmap(cr, selectedImage);
	                ImageView image = (ImageView) findViewById(R.id.imageView1);
					//image.setImageURI(data.getData());
	                image.setImageBitmap(thumbnail);
					
	                Uri oldUri = item.getPicUri();
	                item.setPicUri(selectedImage);
					if (oldUri != null)
						cr.delete(oldUri, null, null);
	                
	                CCActivity.alert(this, "Image saved to:\n" + selectedImage);
	            } catch (Exception e) {
	                CCActivity.notify(this, "Picture Failed to Load");
	            }
			}
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}