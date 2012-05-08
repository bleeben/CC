package cc.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cc.rep.Collection;

public class SharingManagerActivity extends Activity{
	Collection c;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_manager);
        
        c = new Collection();
        
    }
    public void onCancelButtonClick(View view) {
    	//c.setName(nameEdit.getText().toString());
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	// save the collections state
    	finish();
    }
}