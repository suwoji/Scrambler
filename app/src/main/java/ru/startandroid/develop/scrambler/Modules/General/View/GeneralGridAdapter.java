package ru.startandroid.develop.scrambler.Modules.General.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import ru.startandroid.develop.scrambler.Model.ImageDBService;
import ru.startandroid.develop.scrambler.Model.ImageInfo;
import ru.startandroid.develop.scrambler.R;
import ru.startandroid.develop.scrambler.UI.FullImageActivity;

public class GeneralGridAdapter extends BaseAdapter {
    private Context mContext;

    ArrayList<Bitmap> mThumbIds = new ArrayList<Bitmap>();
    ArrayList<ImageInfo> previewUri = new ArrayList<ImageInfo>();
    public GeneralGridAdapter(Context c, ArrayList<ImageInfo> img) {
        mContext = c;
        previewUri = ImageDBService.INSTANCE.queryAllImages();
//        mThumbIds = img;
    }

    public int getCount() {
        return previewUri.size();
    }

    public Object getItem(int position) {
        return previewUri.get(position);
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

        imageView.setImageURI(Uri.parse(previewUri.get(position).getPreviewUri()));
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