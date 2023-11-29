package edu.uga.cs.shoppinglist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ShoppingListFragment extends Fragment {
    private EditText editTextItem;
    private Button button;


    public ShoppingListFragment() {
        // Required empty public constructor
    }

    public static ShoppingListFragment newInstance(String param1, String param2) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        editTextItem = v.findViewById(R.id.editText);
        button = v.findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextItem.getText().toString().equals("") || editTextItem.getText().toString().equals(null)) {
                    return;
                } else {
                    Toast.makeText(getContext(), editTextItem.getText().toString() + " has been added to the Shopping List!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return v;
    }
}