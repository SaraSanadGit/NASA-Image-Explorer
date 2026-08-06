package com.sara.nasaimageexplorer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sara.nasaimageexplorer.R;

/**
 * Home Fragment for NASA Image Explorer.
 * This fragment represents the main screen of the application.
 *
 * @author Sara
 * @version 1.0
 */
public class HomeFragment extends Fragment {


    /**
     * Creates the fragment view.
     *
     * @param inflater Layout inflater
     * @param container Parent container
     * @param savedInstanceState Saved state
     * @return Created view
     */
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {


        return inflater.inflate(
                R.layout.fragment_home,
                container,
                false
        );
    }
}