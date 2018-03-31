package com.emc.recipester.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.emc.recipester.R;
import com.emc.recipester.data.CategoryListAdapter;

import java.io.Serializable;

public class CategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] categories = {"Beverage", "Breakfast", "Brunch", "Dessert", "Dinner", "Lunch", "Snack", "Soup"};
    Integer[] categoryImages = {
            R.drawable.beverage,
            R.drawable.breakfast,
            R.drawable.brunch,
            R.drawable.dessert,
            R.drawable.dinner,
            R.drawable.lunch,
            R.drawable.snack,
            R.drawable.soup
    };
    ListView listView;
    static final String STATE_CATEGORY = "categoryList.state";
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    Intent intent;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        if (savedInstanceState != null) {
            listView = (ListView) savedInstanceState.getSerializable(STATE_CATEGORY);
        }

        listView = findViewById(R.id.lstCategories);
        listView.setOnItemClickListener(this);

        ListAdapter categoriesAdapter = new CategoryListAdapter(this, categories, categoryImages);
        listView.setAdapter(categoriesAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
        checkPermissionLevel();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(STATE_CATEGORY, (Serializable) listView.onSaveInstanceState());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE) {
            // Request for permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted.
                Toast.makeText(getApplicationContext(), "Write Permission Granted for Caching!", Toast.LENGTH_SHORT).show();
                listRecipes();
            } else {
                // Permission request was denied.
                Toast.makeText(getApplicationContext(), "Write Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkPermissionLevel() {
        // Check if the permission has been granted
        if (ActivityCompat.checkSelfPermission(CategoryActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            // Toast.makeText(getApplicationContext(), "Write Permission Available!", Toast.LENGTH_SHORT).show();
            listRecipes();
        } else {
            // Permission is missing and must be requested.
            requestWriteExternalStoragePermission();
        }
    }

    private void requestWriteExternalStoragePermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(CategoryActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted.
            ActivityCompat.requestPermissions(CategoryActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            Toast.makeText(getApplicationContext(), "External Storage Not Found!", Toast.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(CategoryActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    private void listRecipes() {
        if (isNetworkAvailable()) {
            intent = new Intent(CategoryActivity.this, RecipesListActivity.class);
            intent.putExtra("category", categories[position]);
            CategoryActivity.this.startActivity(intent);
        } else {
            promptToConnect();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void promptToConnect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Connect to Internet to View Recipes!")
                .setCancelable(false)
                .setPositiveButton("Connect to Internet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
