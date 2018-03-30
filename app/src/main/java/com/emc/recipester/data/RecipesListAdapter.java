package com.emc.recipester.data;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emc.recipester.R;
import com.emc.recipester.core.ImageManager;

import java.util.List;

/**
 * Created by Munsif on 3/19/2018.
 */

public class RecipesListAdapter extends ArrayAdapter {

    private final Activity context;
    private @LayoutRes
    int resourceId;
    public ImageManager imageManager;

    public RecipesListAdapter(@NonNull Activity context, @NonNull List<Recipe> recipeList) {
        super(context, R.layout.recipe_row, recipeList);
        this.context = context;
        this.resourceId = R.layout.recipe_row;
        imageManager = new ImageManager(context.getApplicationContext());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder;
        Recipe recipe = (Recipe) getItem(position);

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.recipe_row, parent, false);
            holder = new ViewHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        if (recipe != null) {
            holder.recipeName.setText(recipe.getName());
            holder.backgroundImage.setTag(recipe.getImage());
            imageManager.displayImage(recipe.getImage(), holder.backgroundImage);
        }
        return rowView;
    }

    public static class ViewHolder {
        TextView recipeName;
        ImageView backgroundImage;

        ViewHolder(View view) {
            this.recipeName = view.findViewById(R.id.txtRecipeName);
            this.backgroundImage = view.findViewById(R.id.imgRecipeBackground);
        }
    }
}
