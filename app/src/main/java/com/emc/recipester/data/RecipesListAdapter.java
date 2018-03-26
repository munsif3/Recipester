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
 * Created by Munsif on 3/19/2018.
 */

public class RecipesListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] recipeName;
    private final Integer[] recipeBackgroundImages;

    public RecipesListAdapter(Activity context, String[] recipeName, Integer[] recipeBackgroundImages) {
        super(context, R.layout.recipe_row, recipeName);
        this.context = context;
        this.recipeName = recipeName;
        this.recipeBackgroundImages = recipeBackgroundImages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        RecipesListAdapter.ViewHolder holder;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.recipe_row, parent, false);
            holder = new RecipesListAdapter.ViewHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (RecipesListAdapter.ViewHolder) rowView.getTag();
        }
        holder.recipeName.setText(recipeName[position]);
        holder.backgroundImage.setImageResource(recipeBackgroundImages[position]);

        return rowView;
    }

    static class ViewHolder {
        TextView recipeName;
        ImageView backgroundImage;

        ViewHolder(View view) {
            this.recipeName = view.findViewById(R.id.txtRecipeName);
            this.backgroundImage = view.findViewById(R.id.imgRecipeBackground);
        }
    }
}
