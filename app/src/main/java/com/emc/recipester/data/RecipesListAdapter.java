package com.emc.recipester.data;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Munsif on 3/19/2018.
 */

public class RecipesListAdapter extends ArrayAdapter {

    private final Activity context;
    private @LayoutRes
    int resourceId;

    public RecipesListAdapter(@NonNull Activity context, @NonNull List<Recipe> recipeList) {
        super(context, R.layout.recipe_row, recipeList);
        this.context = context;
        this.resourceId = R.layout.recipe_row;
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
        holder.recipeName.setText(recipe.getName());
        holder.imageURL = recipe.getImage();
        new DownloadAsyncTask().execute(holder);
        return rowView;
    }

    static class ViewHolder {
        TextView recipeName;
        ImageView backgroundImage;
        String imageURL;
        Bitmap bitmap;

        ViewHolder(View view) {
            this.recipeName = view.findViewById(R.id.txtRecipeName);
            this.backgroundImage = view.findViewById(R.id.imgRecipeBackground);
        }
    }

    class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {
            ViewHolder viewHolder = params[0];
            try {
                URL imageURL = new URL(viewHolder.imageURL);
                viewHolder.bitmap = BitmapFactory.decodeStream(imageURL.openStream());
            } catch (IOException e) {
                Log.e("error", "Downloading Image Failed");
                viewHolder.bitmap = null;
            }
            return viewHolder;
        }

        @Override
        protected void onPostExecute(ViewHolder result) {
            if (result.bitmap == null) {
                result.backgroundImage.setImageResource(android.R.drawable.gallery_thumb);
            } else {
                result.backgroundImage.setImageBitmap(result.bitmap);
                result.backgroundImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }
}
