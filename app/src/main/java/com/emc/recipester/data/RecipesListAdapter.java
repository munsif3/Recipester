package com.emc.recipester.data;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Munsif on 3/19/2018.
 */

public class RecipesListAdapter extends ArrayAdapter<Recipe> {

    private @LayoutRes
    int resourceId;

    public RecipesListAdapter(@NonNull Context context, @NonNull List<Recipe> recipeList){
        super(context, android.R.layout.simple_list_item_activated_2, recipeList);
        this.resourceId = android.R.layout.simple_list_item_activated_2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Recipe recipe= (Recipe) getItem(position);

        if (convertView == null){
            convertView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(resourceId,null);
        }
        return convertView;
    }
}
