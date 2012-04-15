package cc.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class ItemActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);
    }
    //hi
    
    
    public void onCancelButtonClick(View view) {
    	finish();
    }
    
    public void onDoneButtonClick(View view) {
    	finish();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.description:
            	
                return true;
            case R.id.summary:
            	
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}