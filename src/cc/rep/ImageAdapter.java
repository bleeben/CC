package cc.rep;

import cc.main.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

    Context context;
    LayoutInflater li;
    
    public ImageAdapter(Context context,LayoutInflater li)
    {
       this.context = context;
       this.li=li;
    }

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		View itemView = view;
        
        if ( view == null )
        {
                               /*we define the view that will display on the grid*/
           
           //Inflate the layout
           itemView = li.inflate(R.layout.grid_item, null);
           
           // Add The Text!!!
           TextView tv = (TextView)itemView.findViewById(R.id.grid_item_text);
           tv.setText("Item "+ position );
           
           // Add The Image!!!           
           ImageView iv = (ImageView)itemView.findViewById(R.id.grid_item_image);
           iv.setImageResource(R.drawable.ic_launcher);
        }
        
        return itemView;
	}

}
