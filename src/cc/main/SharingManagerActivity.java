package cc.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import cc.rep.Collection;
import cc.rep.Permission;
import cc.rep.Sharer;
import cc.rep.SharerListAdapter;

public class SharingManagerActivity extends Activity{
	Collection c;
	Spinner permSpinner;
	ListView sharerList;
	EditText nameEdit;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_manager);
        
        Intent i = getIntent();
        c = (Collection) i.getParcelableExtra("collection");
        
        nameEdit = (EditText) findViewById(R.id.editText);
        permSpinner = (Spinner) findViewById(R.id.spinnerPerm);
        
        sharerList = (ListView) findViewById(R.id.listSharers);
        sharerList.setAdapter(new SharerListAdapter(this,getLayoutInflater(),c));
        
        sharerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
            	CCActivity.alert(getApplicationContext(), c.getSharer(position).getName());
            }
        });
        
        nameEdit.clearFocus();
    }
    
    public void onAddButtonClick(View view) {
    	Permission p = Permission.values()[permSpinner.getSelectedItemPosition()];
    	Sharer share = new Sharer(nameEdit.getText().toString(),p);
    	c.addSharer(share);
    	((BaseAdapter) sharerList.getAdapter()).notifyDataSetChanged();
    	nameEdit.setText(R.string.fieldName);
    	permSpinner.setSelection(0);
    	nameEdit.clearFocus();
    }
    
    public void onCancelButtonClick(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	Intent intent = new Intent();
    	intent.putExtra("collection", c);
    	
    	setResult(Activity.RESULT_OK,intent);
    	finish();
    }
}