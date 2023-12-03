package edu.uga.cs.shoppinglist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PurchasedItemsAdapter extends RecyclerView.Adapter<PurchasedItemsAdapter.ViewHolder>{
    private List<BasketItem> basketItemList;
    public PurchasedItemsAdapter(List<BasketItem> basketItemList) {
        this.basketItemList = basketItemList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_items, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BasketItem basketItem = basketItemList.get(position);
        holder.itemName.setText(basketItem.getItemName());
        holder.itemPrice.setText("Price: $" + basketItem.getItemPrice());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("previousList/basketItems");
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("shoppingList");
                Query query = databaseReference.orderByChild("itemName").equalTo(basketItem.getItemName());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            BasketItem basketItem = snapshot.getValue(BasketItem.class);
                            snapshot.getRef().removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d("DELETE", "Item deleted: " + basketItem.getItemName());
                                            Toast.makeText(view.getContext(), "Item deleted: " + basketItem.getItemName(), Toast.LENGTH_SHORT).show();
                                            Item item = new Item(basketItem.getItemName().toString());
                                            databaseReference1.push().setValue(item);
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FirebaseDatabase", "Database write cancelled. Details: " + error.getMessage());
                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {return basketItemList.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        Button deleteButton;
        Button updateButton;
        EditText editText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tvfirstNameBasket);
            itemPrice = itemView.findViewById(R.id.PriceText);
            deleteButton = itemView.findViewById(R.id.buttonBasket4);
            updateButton = itemView.findViewById(R.id.button7);
            editText = itemView.findViewById(R.id.editText);
        }
    }
}
