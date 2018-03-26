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

public class RecipesListAdapter extends ArrayAdapter<String> {

//    private @LayoutRes
//    int resourceId;
//
//
//    public RecipesListAdapter(@NonNull Context context, @NonNull List<Recipe> recipeList){
//        super(context, android.R.layout.simple_list_item_activated_2, recipeList);
//        this.resourceId = android.R.layout.simple_list_item_activated_2;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
////        Recipe recipe= (Recipe) getItem(position);
//
//        if (convertView == null){
//            convertView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
//                    .inflate(resourceId,null);
//        }
//        return convertView;
//    }


//    private final Activity context;
//    private final String[] recipeName;
//    private final Integer[] recipeBackgroundImage;
//
//
//    public RecipesListAdapter(Activity context, String[] recipeName, Integer[] recipeBackgroundImage) {
//        super( context, R.layout.recipe_row, recipeName);
//        this.context = context;
//        this.recipeName = recipeName;
//        this.recipeBackgroundImage = recipeBackgroundImage;
//    }

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
        View row = convertView;
        RecipesListAdapter.ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.recipe_row, parent, false);
            holder = new RecipesListAdapter.ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (RecipesListAdapter.ViewHolder) row.getTag();
        }
        holder.recipeName.setText(recipeName[position]);
        holder.backgroundImage.setImageResource(recipeBackgroundImages[position]);

        return row;
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
