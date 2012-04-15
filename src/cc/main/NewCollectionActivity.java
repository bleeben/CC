package cc.main;

import cc.rep.Collection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
        
        
    	setResult(RESULT_OK);
//    	CCActivity.alert(this,"Set Result");
    	c.setName(nameEdit.getText().toString());
    	CCActivity.alert(this,"Name Set: "+c.getName());
    	Intent intent = new Intent(this, NewCollectionActivity.class);
    	intent.putExtra("collection", c);
    	setIntent(intent);
    	CCActivity.alert(this,"Intent Set");
    	
    	finish();
    }
}