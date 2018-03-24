package com.emc.recipester.data;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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

public class CategoryListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] categoryName;
    private final Integer[] categoryBackgroundImage;

    public CategoryListAdapter(Activity context, String[] categoryName, Integer[] categoryBackgroundImage) {
        super(context, R.layout.category_row, categoryName);
        this.context = context;
        this.categoryName = categoryName;
        this.categoryBackgroundImage = categoryBackgroundImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.category_row, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.categoryName.setText(categoryName[position]);
        holder.backgroundImage.setImageResource(categoryBackgroundImage[position]);

        return row;
    }

    static class ViewHolder {
        TextView categoryName;
        ImageView backgroundImage;

        ViewHolder(View view) {
            this.categoryName = view.findViewById(R.id.txtCategoryName);
            this.backgroundImage = view.findViewById(R.id.imgCategoryBackground);
        }
    }
}