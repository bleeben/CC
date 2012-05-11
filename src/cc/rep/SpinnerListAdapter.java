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
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class SpinnerListAdapter extends BaseAdapter implements SpinnerAdapter{

    Context context;
    LayoutInflater li;
    List<? extends Storable> c;
    
    public SpinnerListAdapter(Context context,LayoutInflater li,ArrayList<Collection> collections)
    {
       this.context = context;
       this.li=li;
       this.c=collections;
    }
    
    public SpinnerListAdapter(Context context,LayoutInflater li,Collection collection)
    {
       this.context = context;
       this.li=li;
       ArrayList<Collection> c=new ArrayList<Collection>();
       c.add(collection);
       this.c=c;
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
           TextView tv = new TextView(context);
           tv.setText(c.get(position).getName());
           
        }
        
        return itemView;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			return getView(position,convertView,parent);
		}
		return convertView;

	}

}
