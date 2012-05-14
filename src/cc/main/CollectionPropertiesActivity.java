package cc.main;

import contentprovider.MainContentProvider;
import cc.rep.Collection;
import cc.rep.CollectionOpenHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CollectionPropertiesActivity extends Activity {
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
        
        Intent i = getIntent();
        c = (Collection) i.getParcelableExtra("collection");
        
        
        nameEdit = (EditText)findViewById(R.id.editTextName);
        nameEdit.setText(c.getName());

        descEdit = (EditText)findViewById(R.id.editTextDesc);
        descEdit.setText(c.getDesc());
        
        TextView headText = (TextView) findViewById(R.id.collectionText);
        headText.setText("Edit Collection");
        
        shareToggle = (ToggleButton) findViewById(R.id.toggleButtonShare);
        shareToggle.setChecked(c.isPrivate());
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
    	
    	// update contentresolver
    	ContentValues updateValues = c.makeContentValues();
    	String selectionClause = CollectionOpenHelper.COLUMN_ID + " = " + c.getID();
    	int rowsUpdated = getContentResolver().update(MainContentProvider.CONTENT_URI_C, updateValues, selectionClause, new String[0]);
    	System.out.println("updated rows id; " + c.getID() + " and num: " + rowsUpdated);
    	setResult(Activity.RESULT_OK,intent);
    	finish();
    }
    public void onShareButtonClick(View view){
    	Intent intent = new Intent(this, SharingManagerActivity.class);
    	intent.putExtra("collection", c);
    	startActivityForResult(intent,SHARE_COLLECTION);
    }
}