package cc.main;

import cc.rep.Collection;
import cc.rep.Item;
import cc.rep.ResultCode;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ToggleButton;

public class NewCollectionActivity extends Activity {
	
	Collection c;
	EditText nameEdit;
	EditText descEdit;
	ToggleButton shareToggle;
	static final int SHARE_COLLECTION = 6;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_properties);
        
        c = new Collection();
        Intent i = getIntent();
        int size = i.getIntExtra("totalNum",0);
        
        nameEdit = (EditText)findViewById(R.id.editTextName);
        nameEdit.setText("Untitled "+size);
        descEdit = (EditText)findViewById(R.id.editTextDesc);
        shareToggle = (ToggleButton) findViewById(R.id.toggleButtonShare);
        shareToggle.setChecked(true);
        
        nameEdit.clearFocus();
    }
    //hi
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
    	switch (requestCode) {
    	case SHARE_COLLECTION:
    		switch (resultCode) {
    		case Activity.RESULT_OK:
    			Collection collection = data.getParcelableExtra("collection");
    			c = collection;
    			break;
    		}
    		break;
    	}
    }
    
    public void onCancelButtonClick(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	c.setName(nameEdit.getText().toString());
    	c.setDesc(descEdit.getText().toString());
       	c.setPrivate(shareToggle.isChecked());
    	Intent intent = new Intent();
    	intent.putExtra("collection", c);
    	
    	setResult(Activity.RESULT_OK,intent);
    	finish();
    }
    public void onShareButtonClick(View view){
    	Intent intent = new Intent(this, SharingManagerActivity.class);
    	intent.putExtra("collection", c);
    	startActivityForResult(intent,SHARE_COLLECTION);
    }
}