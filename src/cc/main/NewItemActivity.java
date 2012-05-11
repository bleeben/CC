package cc.main;

import java.util.ArrayList;

import cc.rep.Collection;
import cc.rep.ImageAdapter;
import cc.rep.Item;
import cc.rep.ResultCode;
import cc.rep.SpinnerListAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class NewItemActivity extends Activity {
    Item item;
    EditText nameEdit;
    Spinner collectionSpinner;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);
        
        item = new Item();
        Intent i = getIntent();
        int size = i.getIntExtra("totalNum",0);
        
        nameEdit = (EditText)findViewById(R.id.editTextName);
        nameEdit.setText("Untitled "+size);
        
        collectionSpinner = (Spinner) findViewById(R.id.collectionSpinner);
        
        Collection c = (Collection) i.getParcelableExtra("collection");
        ArrayList<Collection> cs = i.getParcelableArrayListExtra("collections");
        if (cs==null) {
        	collectionSpinner.setAdapter(new SpinnerListAdapter(this,getLayoutInflater(),c));
        } else {
        	collectionSpinner.setAdapter(new SpinnerListAdapter(this,getLayoutInflater(),cs));
        }
        
    }
    //hi
    
    
    public void onCancelButtonClick(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	item.setName(nameEdit.getText().toString());
    	Intent intent = new Intent();
    	intent.putExtra("item", item);
    	setResult(Activity.RESULT_OK, intent);
    	finish();
    }
    
    public void onAddTag(View view){
        LayoutInflater factory = LayoutInflater.from(this);
        final View add_tag = factory.inflate(R.layout.add_tag, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
//           .setIconAttribute(android.R.attr.alertDialogIcon)
            .setTitle(R.string.addTagTitle)
            .setView(add_tag)
            .setPositiveButton(R.string.addTagOK, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	Toast.makeText(NewItemActivity.this, "NewItemActivity: + OK : " + ((EditText) add_tag.findViewById(R.id.editText1)).getText(), Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton(R.string.addTagCancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	Toast.makeText(NewItemActivity.this, "NewItemActivity: + cancel", Toast.LENGTH_SHORT).show();
                }
            })
            .create();
        alertDialog.show();
        
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