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
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_properties);
        
        Intent i = getIntent();
        c = (Collection) i.getParcelableExtra("collection");
        
        nameEdit = (EditText)findViewById(R.id.editTextName);
        nameEdit.setText(c.getName());
    }
    //hi
    
    public void onCancelButtonClick(View view) {
    	c.setName(nameEdit.getText().toString());
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	// save the collections state
    	finish();
    }
    
    public void onShareButtonClick(View view){
    	Intent intent = new Intent(this, SharingManagerActivity.class);
    	intent.putExtra("collection", c);
    	System.out.println("tesT");
    	startActivity(intent);
    }
}