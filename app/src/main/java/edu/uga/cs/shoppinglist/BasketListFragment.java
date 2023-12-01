package edu.uga.cs.shoppinglist;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.util.Log;
import android.widget.Toast;

public class BasketListFragment extends Fragment {

    private Spinner spinner;
    private Button button;
    private EditText editText;

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

        // Initialize Spinner


        spinner = v.findViewById(R.id.spinner);
        button = v.findViewById(R.id.button_basket);
        editText = v.findViewById(R.id.editText);

        // Load data from Firebase to Spinner
        loadDataFromFirebase();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("TEST", "clicked " + spinner.getSelectedItem().toString() + " " + editText.getText().toString());
                if (editText.getText().toString().equals("") || editText.getText().toString().equals(null)) {
                    Toast.makeText(getContext(), "ENTER A PRICE VALUE", Toast.LENGTH_SHORT).show();
                    return;
                }
                BasketItem basketItem = new BasketItem(spinner.getSelectedItem().toString(), editText.getText().toString());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("shoppingBasket");
                myRef.push().setValue(basketItem)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), spinner.getSelectedItem().toString() + " has been added to shopping Basket", Toast.LENGTH_SHORT).show();
                                DatabaseReference shoppingListRef = database.getReference("shoppingList");
                                shoppingListRef.orderByChild("itemName").equalTo(spinner.getSelectedItem().toString())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    snapshot.getRef().removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            }
                        });
            }
        });


        return v;
    }


    private void loadDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingList"); // Replace with your actual data path

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the data from Firebase
                List<String> dataList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Handle the case where each item is a HashMap
                    Map<String, Object> itemMap = (Map<String, Object>) snapshot.getValue();
                    if (itemMap != null) {
                        // Assuming your item has a field named "itemName"
                        String itemName = (String) itemMap.get("itemName");
                        if (itemName != null) {
                            dataList.add(itemName);
                        }
                    }
                }

                // Populate the Spinner with the retrieved data
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dataList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }
}