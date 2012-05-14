package cc.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cc.rep.Collection;
import cc.rep.ImageAdapter;
import cc.rep.Item;
import cc.rep.ResultCode;
import cc.rep.SpinnerListAdapter;
import cc.rep.Tag;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class CopyOfNewItemActivity extends Activity {
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private Uri fileUri;

	Item item;
	EditText nameEdit;
	Spinner collectionSpinner;

	private Preview mPreview;
	Camera mCamera;
	int numberOfCameras = 0;
	boolean hasCam;
	int cameraCurrentlyLocked;
	// The first rear facing camera
	int defaultCameraId;
	boolean cameraWorking = false;
	
	Gallery tagGallery;
	ArrayAdapter<Tag> tagArr;

	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_item);

		item = new Item();
		Intent intent = getIntent();
		int size = intent.getIntExtra("totalNum", 0);

		nameEdit = (EditText) findViewById(R.id.editTextName);
		nameEdit.setText("Untitled " + size);

		collectionSpinner = (Spinner) findViewById(R.id.collectionSpinner);

		Collection c = (Collection) intent.getParcelableExtra("collection");
		ArrayList<Collection> cs = intent
				.getParcelableArrayListExtra("collections");
		if (cs == null) {
			collectionSpinner.setAdapter(new SpinnerListAdapter(this,
					getLayoutInflater(), c));
		} else {
			collectionSpinner.setAdapter(new SpinnerListAdapter(this,
					getLayoutInflater(), cs));
		}
		
		hasCam = checkCameraHardware(getApplicationContext());
		getCameraInstance();
		/*if (getCameraInstance() != null) {
			

			if (hasCam) {
				numberOfCameras = Camera.getNumberOfCameras();
				// Find the ID of the default camera
				CameraInfo cameraInfo = new CameraInfo();
				for (int i = 0; i < numberOfCameras; i++) {
					Camera.getCameraInfo(i, cameraInfo);
					if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
						defaultCameraId = i;
					}
				}
				// CCActivity.alert(this, "Cameras: "+numberOfCameras);

				
				// CCActivity.alert(this, "Frame");

				if (numberOfCameras > 0 && hasCam) {
					FrameLayout frame = (FrameLayout) findViewById(R.id.frameLayout);
					ImageView image = (ImageView) findViewById(R.id.imageView1);
					frame.removeView(image);
					mPreview = new Preview(this);
					// CCActivity.alert(this, "Preview");
					frame.addView(mPreview);
				}
			}
		}*/
		
		tagGallery = (Gallery) findViewById(R.id.galleryTags);
		tagArr = new ArrayAdapter<Tag>(this,
				android.R.layout.simple_gallery_item, item.getTags());
		tagGallery.setAdapter(tagArr);

		nameEdit.clearFocus();
	}

	// hi

	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
		// CCActivity.alert(this, "Checking for Cam");
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	/** A safe way to get an instance of the Camera object. */
	public Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
			cameraWorking = true;
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
			CCActivity
					.alert(getApplicationContext(), "Unable to access camera");
			cameraWorking = false;
		}
		return c; // returns null if camera is unavailable
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Open the default i.e. the first rear facing camera.
		if (cameraWorking && hasCam && mPreview != null) {
			mCamera = getCameraInstance();
			cameraCurrentlyLocked = defaultCameraId;
			mPreview.setCamera(mCamera);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Because the Camera object is a shared resource, it's very
		// important to release it when the activity is paused.
		if (cameraWorking && hasCam && numberOfCameras > 0 && mPreview != null) {
			if (mCamera != null) {
				mPreview.setCamera(null);
				mCamera.release();
				mCamera = null;
			}
		}
	}

	public void onCancelButtonClick(View view) {
		setResult(RESULT_CANCELED);
		finish();
	}

	public void onDoneButtonClick(View view) {
		item.setName(nameEdit.getText().toString());
		Intent intent = new Intent();
		intent.putExtra("item", item);

		Intent i = getIntent();
		Collection c = (Collection) i.getParcelableExtra("collection");
		ArrayList<Collection> cs = i.getParcelableArrayListExtra("collections");
		if (cs == null) {
		} else {
			intent.putExtra("position",
					collectionSpinner.getSelectedItemPosition());
		}

		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	public void onAddTag(View view) {
		LayoutInflater factory = LayoutInflater.from(this);
		final View add_tag = factory.inflate(R.layout.add_tag, null);
		final EditText editTag = (EditText) add_tag
				.findViewById(R.id.editTextTag);
		AlertDialog alertDialog = new AlertDialog.Builder(this)
				// .setIconAttribute(android.R.attr.alertDialogIcon)
				.setTitle(R.string.addTagTitle)
				.setView(add_tag)
				.setPositiveButton(R.string.addTagOK,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								item.addTag(editTag.getText().toString());
								updateTagAdapter();
							}
						})
				.setNegativeButton(R.string.addTagCancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Toast.makeText(CopyOfNewItemActivity.this,
										"NewItemActivity: + cancel",
										Toast.LENGTH_SHORT).show();
							}
						}).create();
		alertDialog.show();
	}

	public void updateTagAdapter() {
		tagArr = new ArrayAdapter<Tag>(this,
				android.R.layout.simple_gallery_item, item.getTags());
		tagGallery.setAdapter(tagArr);
	}
	
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}

	private PictureCallback mPicture = new PictureCallback() {

	    @Override
	    public void onPictureTaken(byte[] data, Camera camera) {

	        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
	        if (pictureFile == null){
	            CCActivity.alert(getApplicationContext(), "Error creating media file, check storage permissions");
	            return;
	        }

	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(data);
	            fos.close();
	        } catch (FileNotFoundException e) {
	        	CCActivity.alert(getApplicationContext(), "File not found");
	        } catch (IOException e) {
	        	CCActivity.alert(getApplicationContext(), "Error accessing file");
	        }
	    }
	};
	
	
	public void takePicture(View view) {
		if (cameraWorking==true && hasCam) {
			PictureCallback postview = null;
			PictureCallback raw = null;
			PictureCallback jpeg = null;
			ShutterCallback shutter = null;
			mPreview.takePicture(shutter, raw, postview, mPicture);
		} else if (cameraWorking == false) {
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			//fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
			//CCActivity.alert(this, "Made URI");
		    //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
		    //CCActivity.alert(this, "Opening up Camera");
			startActivityForResult(cameraIntent, ResultCode.CAMERA_PIC_REQUEST);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ResultCode.CAMERA_PIC_REQUEST) {
			if (resultCode == RESULT_OK) {
				CCActivity.alert(this, "Image saved to:\n" + data.getData());
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				ImageView image = (ImageView) findViewById(R.id.imageView1);
				//image.setImageURI(data.getData());
				image.setImageBitmap(thumbnail);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.item, menu);
		return true;
	}
}