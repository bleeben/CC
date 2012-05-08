package cc.main;

import cc.rep.Collection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

public class NewCollectionActivity extends Activity {
	
	Collection c;
	EditText nameEdit;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_properties);
        
        c = new Collection();
        
        nameEdit = (EditText)findViewById(R.id.editTextName);
        nameEdit.setText("Untitled");
    }
    //hi
    
    
    public void onCancelButtonClick(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	c.setName(nameEdit.getText().toString());
    	Intent intent = new Intent();
    	intent.putExtra("collection", c);
    	
    	setResult(Activity.RESULT_OK,intent);
    	finish();
    }
    public void onShareButtonClick(View view){
    	Intent intent = new Intent(this, SharingManagerActivity.class);
    	intent.putExtra("collection", c);
    	startActivity(intent);
    }
}