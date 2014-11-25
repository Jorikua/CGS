package ua.vsevolodkaganovych.testtaskcgs;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ua.vsevolodkaganovych.testtaskcgs.provider.image.ImageColumns;

public class CustomCursorAdapter extends CursorAdapter {

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    private static class ViewHolder {
        private ImageView mImageView;
        private int imageIndex;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorites, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.mImageView = (ImageView)view.findViewById(R.id.imageView2);
        viewHolder.imageIndex = cursor.getColumnIndex(ImageColumns.IMAGE);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        Picasso.with(context).load(cursor.getString(viewHolder.imageIndex)).into(viewHolder.mImageView);
    }

}
