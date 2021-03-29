package com.example.gallerylib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Noah Tobias Lackenberger
 * @version 1.1
 * @since  1.0
 * @see <a href="https://abhiandroid.com/ui/gridview">https://abhiandroid.com/ui/gridview</a>
 */
public class GalleryObject {

    // *************************************************************** Variable Section ***************************************************************

    Context context;
    ArrayList imageItems;

    // *************************************************************** Constructor Section ***************************************************************

    /***
     *      The constructor for a Gallery Object
     * <p>
     *      @version 1.0
     * </p>
     * <p>
     *      @param context the main class must be passed here as context
     * </p>
     */
    public GalleryObject(Context context) {
        if(context == null)
            throw new IllegalArgumentException("The context can not be null!");

        this.context = context;
    }

    // *************************************************************** Method Section ***************************************************************


    /***
     *     This method loads all images from the SickCameraApplication-Directory
     * <p>
     *      @version 1.0
     * </p>
     * <p>
     *      @return an ArrayList of the images in the SickCameraApplication-Directory
     * </p>
     */
    public ArrayList getData() {
        imageItems  = new ArrayList();

        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/SickCameraApplication");

        File[] imageFiles = path.listFiles();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        assert imageFiles != null;
        for(int i = 0; i < imageFiles.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFiles[i].getAbsolutePath(), options);
            imageItems.add(new ImageItem(bitmap, imageFiles[i].getName()));
        }

        return imageItems;
    }

    /***
     *      The constructor for a Camera Object
     * <p>
     *      @param position position of the image in the ArrayList of pictures
     * </p>
     * <p>
     *      @return the image (as Bitmap) from the given position
     * </p>
     */
    public Bitmap getBitmapFromPosition(int position) {
        return (Bitmap) imageItems.get(position);
    }

    // *************************************************************** Class Section ***************************************************************


    /**
     *      This class reprecents a custom Grid View adapter for the gallery
     * <p>
     *      @version 1.0
     *      @since 1.0
     * </p>
     */
    public static class gridViewAdapter extends ArrayAdapter {
        Context context;
        private int resourceId;
        private ArrayList data = new ArrayList();
        private ImageView imageView;
        private TextView textView;

        public gridViewAdapter(Context context, int resourceId, ArrayList data, @NonNull TextView textView, @NonNull ImageView imageView) {
            super(context, resourceId, data);
            this.context = context;
            this.resourceId = resourceId;
            this.data = data;
            this.imageView = imageView;
            this.textView = textView;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;

            if(row == null) {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();

                row = inflater.inflate(resourceId, parent, false);
                holder = new ViewHolder();

                holder.imageTitle = this.textView;
                holder.image = this.imageView;

                row.setTag(holder);
            } else {
                holder = (ViewHolder)row.getTag();
            }

            ImageItem item = (ImageItem) data.get(position);
            holder.imageTitle.setText(item.getTitle());
            holder.image.setImageBitmap(item.getImage());

            return row;
        }


        class ViewHolder {
            TextView imageTitle;
            ImageView image;
        };
    };

    /**
     *      This class represents one image in the gallery
     * <p>
     *      @version 1.0
     *      @since 1.0
     * </p>
     */
    public static class ImageItem {
        private Bitmap image;
        private String title;


        public ImageItem(Bitmap image, String title) {
            super();
            this.image = image;
            this.title = title;
        }



        public Bitmap getImage() {
            return image;
        }

        public void setImage(Bitmap image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    };


    // *************************************************************** Enum Section ***************************************************************
}

