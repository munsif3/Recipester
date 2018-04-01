package com.emc.recipester.data;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Munsif on 3/29/2018.
 */

public class RecipeDataFragment extends Fragment {
    // data object we want to retain
    private Recipe data;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(Recipe data) {
        this.data = data;
    }

    public Recipe getData() {
        return data;
    }
}
