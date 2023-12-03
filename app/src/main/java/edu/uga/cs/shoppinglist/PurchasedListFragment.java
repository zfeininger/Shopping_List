package edu.uga.cs.shoppinglist;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PurchasedListFragment extends Fragment {

    RecyclerView recyclerView;
    PurhcasedListAdapter purhcasedListAdapter;
    ArrayList<PreviousListItem> list;
    DatabaseReference database;

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
        recyclerView = v.findViewById(R.id.shoppingListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        database = FirebaseDatabase.getInstance().getReference("previousList");
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        purhcasedListAdapter = new PurhcasedListAdapter(getContext(), list);
        recyclerView.setAdapter(purhcasedListAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PreviousListItem item = dataSnapshot.getValue(PreviousListItem.class);
                    list.add(item);
                }
                purhcasedListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }
}