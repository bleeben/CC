package cc.rep;

import java.util.ArrayList;
import java.util.List;

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
    List<? extends Storable> c;
    
    public ImageAdapter(Context context,LayoutInflater li,ArrayList<Collection> collections)
    {
       this.context = context;
       this.li=li;
       this.c=collections;
    }
    
    public ImageAdapter(Context context,LayoutInflater li,Collection collection)
    {
       this.context = context;
       this.li=li;
       this.c=collection.getItems();
    }

	
	//@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return c.size();
	}

	//@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return c.get(position);
	}

	//@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	//@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		View itemView = view;
        
        if ( view == null )
        {
                               /*we define the view that will display on the grid*/
           
           //Inflate the layout
           itemView = li.inflate(R.layout.grid_item, null);
           
           // Add The Text!!!
           TextView tv = (TextView)itemView.findViewById(R.id.grid_item_text);
           tv.setText(position +": "+c.get(position).getName());
           
           // Add The Image!!!           
           ImageView iv = (ImageView)itemView.findViewById(R.id.grid_item_image);
           iv.setImageResource(R.drawable.ic_launcher);
        } else {
        	// Add The Text!!!
            TextView tv = (TextView)itemView.findViewById(R.id.grid_item_text);
            tv.setText(position +": "+c.get(position).getName());
            
            // Add The Image!!!           
            ImageView iv = (ImageView)itemView.findViewById(R.id.grid_item_image);
            iv.setImageResource(R.drawable.ic_launcher);
        }
        
        return itemView;
	}

}
