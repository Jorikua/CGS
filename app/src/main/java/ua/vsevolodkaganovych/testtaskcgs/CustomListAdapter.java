package ua.vsevolodkaganovych.testtaskcgs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.vsevolodkaganovych.testtaskcgs.provider.image.ImageColumns;


public class CustomListAdapter extends ArrayAdapter<Item> {
    public CustomListAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    private static class ViewHolder {
        private TextView mTitle;
        private ImageView mImageView;
        private CheckBox mCheckBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Item item = getItem(position);

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search, parent, false);
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.titleView);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTitle.setText(item.title);
        Picasso.with(getContext()).load(item.image).resize(200, 200).centerInside().into(viewHolder.mImageView);
        viewHolder.mCheckBox.setChecked(false);
        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContentValues cv = new ContentValues();
                cv.put(ImageColumns.IMAGE, item.image);
                getContext().getContentResolver().insert(ImageColumns.CONTENT_URI, cv);
            }
        });

        viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FullScreenImage.class);
                intent.setData(Uri.parse(item.image));
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
