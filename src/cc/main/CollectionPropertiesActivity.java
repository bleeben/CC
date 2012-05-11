package cc.main;

import cc.rep.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CollectionPropertiesActivity extends Activity {
	Collection c;
	EditText nameEdit;
	EditText descEdit;
	
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
    }
    //hi
    
    public void onCancelButtonClick(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	c.setName(nameEdit.getText().toString());
    	c.setDesc(descEdit.getText().toString());
    	Intent intent = new Intent();
    	intent.putExtra("collection", c);
    	
    	setResult(Activity.RESULT_OK,intent);
    	finish();
    }
    public void onShareButtonClick(View view){
    	Intent intent = new Intent(this, SharingManagerActivity.class);
    	intent.putExtra("collection", c);
    	System.out.println("tesT");
    	startActivity(intent);
    }
}