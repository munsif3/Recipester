package com.emc.recipester.data;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Munsif on 3/19/2018.
 */

public class RecipesListAdapter extends ArrayAdapter {

    private @LayoutRes
    int resourceId;

    public RecipesListAdapter(@NonNull Context context, @NonNull List<Recipe> recipeList){
        super(context, android.R.layout.simple_list_item_activated_2, recipeList);
        this.resourceId = android.R.layout.simple_list_item_activated_2;
    }
}
