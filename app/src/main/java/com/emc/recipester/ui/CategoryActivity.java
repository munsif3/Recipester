package com.emc.recipester.ui;

import android.Manifest;
import android.animation.LayoutTransition;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.emc.recipester.R;
import com.emc.recipester.data.CategoryListAdapter;

import java.io.Serializable;
import java.util.Locale;

public class CategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String FILE_NAME = "file_lang"; // preference file name
    private static final String KEY_LANG = "key_lang"; // preference key
    static String ENGLISH_LOCALE = "en";
    String[] categories;
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
        loadLanguage();
        setContentView(R.layout.activity_category);

        categories = getResources().getStringArray(R.array.categories_array);

        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);

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

    //-------Begin Locale setting
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipes, menu);
        return true;
    }

    private void saveLanguage(String lang) {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_LANG, lang);
        editor.apply();
        recreate();
    }

    private void loadLanguage() {
        Locale locale = new Locale(getLangCode());
        Locale.setDefault(Locale.ENGLISH);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

    }

    private String getLangCode() {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        return preferences.getString(KEY_LANG, ENGLISH_LOCALE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.language_tamil:
                saveLanguage("ta");
                break;
            case R.id.language_spanish:
                saveLanguage("es");
                break;
            default:
                saveLanguage(ENGLISH_LOCALE);
                break;
        }
        return true;
    }
    //-------End Locale setting

    //-------Begin Permission Request
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
            // Permission is already available
            listRecipes();
        } else {
            // Permission is missing and must be requested.
            requestWriteExternalStoragePermission();
        }
    }

    private void requestWriteExternalStoragePermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(CategoryActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(CategoryActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            Toast.makeText(getApplicationContext(), "External Storage Not Found!", Toast.LENGTH_SHORT).show();
            // Request the permission
            ActivityCompat.requestPermissions(CategoryActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }
    //-------End Permission Request

    private void listRecipes() {
        if (isNetworkAvailable()) {
            intent = new Intent(CategoryActivity.this, RecipesListActivity.class);
            intent.putExtra("category", categories[position]);
            CategoryActivity.this.startActivity(intent);
        } else {
            promptToConnect();
        }
    }

    //-------Begin Check network
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
    //-------End Check network
}
