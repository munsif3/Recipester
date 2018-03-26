package com.emc.recipester.data;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emc.recipester.R;

/**
 * Created by Munsif on 3/23/2018.
 */

public class CategoryListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] categoryName;
    private final Integer[] categoryBackgroundImage;

    public CategoryListAdapter(Activity context, String[] categoryName, Integer[] categoryBackgroundImage) {
        super(context, R.layout.category_row, R.id.txtCategoryName, categoryName);
        this.context = context;
        this.categoryName = categoryName;
        this.categoryBackgroundImage = categoryBackgroundImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder = null;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.category_row, parent, false);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        viewHolder.categoryName.setText(categoryName[position]);
        viewHolder.backgroundImage.setImageResource(categoryBackgroundImage[position]);

        return rowView;
    }

    static class ViewHolder {
        TextView categoryName;
        ImageView backgroundImage;

        public ViewHolder(View view) {
            categoryName = view.findViewById(R.id.txtCategoryName);
            backgroundImage = view.findViewById(R.id.imgCategoryBackground);
        }
    }
}
