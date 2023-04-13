package com.example.logreg;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ProfileActivityPassenger extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ProfileActivityPassenger() {
        // Required empty public constructor
    }
    public static ProfileActivityPassenger newInstance(String param1, String param2) {
        ProfileActivityPassenger fragment = new ProfileActivityPassenger();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/duck-e0e86.appspot.com/o/Users_Profile_Cover_image%2Fimage_NBSR6urldCZu5WGC2ZpJo83QD6F3?alt=media&token=42f5f989-df8f-4cac-bc1e-60bab9674b86";
        Glide.with(this).load(imageUrl).into(imageView);
        return view;
    }
}