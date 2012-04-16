package cc.main;

import cc.rep.Item;
import cc.rep.ResultCode;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

public class NewItemActivity extends Activity {
    Item item;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);
        
        item = new Item();
    }
    //hi
    
    
    public void onCancelButtonClick(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	Intent intent = new Intent();
    	intent.putExtra("item", item);
    	setResult(Activity.RESULT_OK, intent);
    	finish();
    }
    
    
    public void takePicture(View view) {
    	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	startActivityForResult(cameraIntent, ResultCode.CAMERA_PIC_REQUEST);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if (requestCode == ResultCode.CAMERA_PIC_REQUEST){
    		Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
    		ImageView image = (ImageView) findViewById(R.id.imageView1);
    		image.setImageBitmap(thumbnail);
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item, menu);
        return true;
    }
}