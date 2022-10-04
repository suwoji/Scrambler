package ru.startandroid.develop.scrambler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.startandroid.develop.scrambler.UI.FullImageActivity;

public class GalleryAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Bitmap> mThumbIds = new ArrayList<Bitmap>();
    public GalleryAdapter(Context c, ArrayList<Bitmap> img) {
        mContext = c;
        mThumbIds = img;
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return mThumbIds.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
//        for( int i = 0; i < mThumbIds.size(); i++){
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Intent intent = new Intent(mContext, FullImageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        imageView.setImageBitmap(mThumbIds.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.imageView: {
                        mContext.startActivity(intent);
                    }
                }
            }
        });
        return imageView;
    }

    // references to our images

}