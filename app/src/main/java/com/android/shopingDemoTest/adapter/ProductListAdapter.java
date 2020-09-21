package com.android.shopingDemoTest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.shopingDemoTest.R;
import com.android.shopingDemoTest.model.ProductModel;

import java.util.ArrayList;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    ArrayList<ProductModel> productArrayList;
    Context context;
    private OnItemClickListener mListener;
    
    public interface OnItemClickListener {
        public void onItemClick(ProductListAdapter.MyViewHolder view, int position);

        public void onLongItemClick(View view, int position);
    }

    public ProductListAdapter(Context context, ArrayList<ProductModel> productArrayList,OnItemClickListener mListener) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductModel dataModel = productArrayList.get(position);
        holder.product_name.setText(dataModel.getProductname());
        holder.product_price.setText("Price : "+dataModel.getPrice());
        holder.txt_product_vendor_name.setText(dataModel.getVendorname());
        holder.txt_product_vendor_address.setText(dataModel.getVendoraddress());

        holder.txtAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(holder,position);
                int newPosition = position;
                productArrayList.remove(newPosition);
                notifyItemRemoved(newPosition);
                notifyItemRangeChanged(newPosition, productArrayList.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,product_price,txt_product_vendor_name,txt_product_vendor_address,txtAddToCart;
        public MyViewHolder(View itemView) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.txt_product_name);
            product_price = (TextView) itemView.findViewById(R.id.txt_product_price);
            txt_product_vendor_name = (TextView) itemView.findViewById(R.id.txt_product_vendor_name);
            txt_product_vendor_address = (TextView) itemView.findViewById(R.id.txt_product_vendor_address);
            txtAddToCart = (TextView) itemView.findViewById(R.id.txtAddToCart);

        }
    }


}

