package edu.uga.cs.shoppinglist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PurchasedListFragment extends Fragment {

    public PurchasedListFragment() {
        // Required empty public constructor
    }

    public static PurchasedListFragment newInstance(String param1, String param2) {
        PurchasedListFragment fragment = new PurchasedListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_purchased_list, container, false);
        return v;
    }
}