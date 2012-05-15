package cc.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import cc.rep.Collection;
import cc.rep.ImageAdapter;
import cc.rep.Item;
import cc.rep.Permission;
import cc.rep.Sharer;
import cc.rep.SharerListAdapter;
import cc.rep.Tag;

public class SharingManagerActivity extends Activity{
	Collection c;
	Spinner permSpinner;
	ListView sharerList;
	EditText nameEdit;
	int position = -1;
	
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
        this.registerForContextMenu(sharerList);
        
        nameEdit.clearFocus();
        
    }
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_sharing, menu);
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	    Sharer seleSharer = this.c.getSharer(info.position);
	    menu.setHeaderTitle("Sharer: "+seleSharer.getName());
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    Sharer selectedSharer = c.getSharer(info.position);
	    switch (item.getItemId()) {
	        case R.id.notifyEmail:
	            //editNote(info.id);
	        	CCActivity.notify(this, "Sharing notification sent to "+selectedSharer.getName());
	            return true;
	        case R.id.editSharer:
	        	position = info.position;
	        	//
	        	nameEdit.setText(selectedSharer.getName());
	        	permSpinner.setSelection(selectedSharer.getPermission().ordinal());
	        	CCActivity.notify(this, "Editing Sharer "+selectedSharer.getName());
	        	return true;
	        case R.id.deleteSharer:
	        	c.removeSharer(selectedSharer);
	        	((SharerListAdapter) sharerList.getAdapter()).setC(c.getSharers());
	        	((BaseAdapter) sharerList.getAdapter()).notifyDataSetChanged();
	        	CCActivity.notify(this, "Removed Sharer "+selectedSharer.getName());
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
    
    public void onAddButtonClick(View view) {
    	Permission p = Permission.values()[permSpinner.getSelectedItemPosition()];
    	Sharer share = new Sharer(nameEdit.getText().toString(),p);
    	if (position==-1) {
    	c.addSharer(share);
    	} else {
    		c.setSharer(share,position);
    		position=-1;
    	}
    	((SharerListAdapter) sharerList.getAdapter()).setC(c.getSharers());
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