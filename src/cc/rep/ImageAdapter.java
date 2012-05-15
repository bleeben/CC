package cc.rep;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cc.main.CCActivity;
import cc.main.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
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
    boolean isRecent=false;
    
    public ImageAdapter(Context context,LayoutInflater li, List<? extends Storable> list)
    {
       this.context = context;
       this.li=li;
       this.c = list;
       this.isRecent=false;
    }
    
    public ImageAdapter(Context context,LayoutInflater li, List<? extends Storable> list, boolean isRecent)
    {
       this.context = context;
       this.li=li;
       this.c = list;
       this.isRecent=isRecent;
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
           Storable s = c.get(position);
           
           // Add The Text!!!
           TextView tv = (TextView)itemView.findViewById(R.id.grid_item_text);
           if (isRecent && ((Item) s).getCollection()!=null)
        	   tv.setText(position +": "+((Item) s).getCollection().getName()+":"+s.getName());
           else
        	   tv.setText(position +": "+s.getName());
           
           // Add The Image!!!           
           ImageView iv = (ImageView)itemView.findViewById(R.id.grid_item_image);
			try {
				if (s.getPicUri() != null) {
					Uri imageUri = s.getPicUri();
					ContentResolver cr = context.getContentResolver();
					//Bitmap thumbnail = android.provider.MediaStore.Images.Media
					//		.getBitmap(cr, imageUri);
					//Bitmap nail = Bitmap.createScaledBitmap(thumbnail, iv.getWidth(), iv.getHeight(), true);
					//Bitmap thumbnail = readBitmap(imageUri);
					Bitmap thumbnail = ImageAdapter.getThumbnail(context, imageUri, 100);
					iv.setImageBitmap(thumbnail);
					//thumbnail.recycle();
				} else {
					iv.setImageResource(R.drawable.ic_launcher);					
				}
			} catch (Exception e) {
				iv.setImageResource(R.drawable.ic_launcher);
			}
			
        } else {
        	
        	Storable s = c.get(position);
        	// Add The Text!!!
            TextView tv = (TextView)itemView.findViewById(R.id.grid_item_text);
            if (isRecent && ((Item) s).getCollection()!=null)
         	   tv.setText(position +": "+((Item) s).getCollection().getName()+":"+s.getName());
            else
            	tv.setText(position +": "+s.getName());
            
            // Add The Image!!!           
            ImageView iv = (ImageView)itemView.findViewById(R.id.grid_item_image);
            try {
				if (s.getPicUri() != null) {
					Uri imageUri = s.getPicUri();
					ContentResolver cr = context.getContentResolver();
					//Bitmap thumbnail = android.provider.MediaStore.Images.Media
					//		.getBitmap(cr, imageUri);
					Bitmap thumbnail = ImageAdapter.getThumbnail(context, imageUri, 100);
					iv.setImageBitmap(thumbnail);
				} else {
					iv.setImageResource(R.drawable.ic_launcher);					
				}
			} catch (Exception e) {
				iv.setImageResource(R.drawable.ic_launcher);
			}
        }
        
        return itemView;
	}
	
	public static Bitmap getThumbnail(Context context,Uri uri,int thumb) throws FileNotFoundException, IOException{
		int THUMBNAIL_SIZE = thumb;
        InputStream input = context.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }
	
	public Bitmap readBitmap(Uri selectedImage) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2; //reduce quality 
        AssetFileDescriptor fileDescriptor =null;
        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(selectedImage,"r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally{
            try {
                bm = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
                fileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bm;
    }

	public void setC(List<? extends Storable> c){
		this.c = c;
	}
	
	public List<? extends Storable> getC() {
		return this.c;
	}
}
