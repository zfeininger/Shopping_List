package edu.uga.cs.shoppinglist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BasketListFragment extends Fragment {


    public BasketListFragment() {
        // Required empty public constructor
    }

    public static BasketListFragment newInstance(String param1, String param2) {
        BasketListFragment fragment = new BasketListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_basket, container, false);
        return v;
    }
}