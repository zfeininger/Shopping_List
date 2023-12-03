package edu.uga.cs.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PurchasedItemsAdapter extends RecyclerView.Adapter<PurchasedItemsAdapter.ViewHolder>{
    private List<BasketItem> basketItemList;
    public PurchasedItemsAdapter(List<BasketItem> basketItemList) {
        this.basketItemList = basketItemList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BasketItem basketItem = basketItemList.get(position);
        holder.itemName.setText(basketItem.getItemName());
        holder.itemPrice.setText(basketItem.getItemPrice());
    }
    @Override
    public int getItemCount() {return basketItemList.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tvfirstNameBasket);
            itemPrice = itemView.findViewById(R.id.PriceText);
        }
    }
}
