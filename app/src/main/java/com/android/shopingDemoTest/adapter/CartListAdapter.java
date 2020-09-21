package com.android.shopingDemoTest.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.shopingDemoTest.R;
import com.android.shopingDemoTest.model.ProductModel;

import java.util.ArrayList;

import static android.Manifest.permission.CALL_PHONE;


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    ArrayList<ProductModel> cartProductArrayList;
    Context context;

    private CartListAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(CartListAdapter.MyViewHolder view, int position);
        public void onLongItemClick(View view, int position);
    }

    public CartListAdapter(Context context, ArrayList<ProductModel> cartProductArrayList,
                           CartListAdapter.OnItemClickListener mListener) {
        this.context = context;
        this.cartProductArrayList = cartProductArrayList;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductModel dataModel = cartProductArrayList.get(position);
        holder.product_name.setText(dataModel.getProductname());
        holder.product_price.setText("Price : "+dataModel.getPrice());
        holder.txt_product_vendor_name.setText(dataModel.getVendorname());
        holder.txt_product_vendor_address.setText(dataModel.getVendoraddress());
        holder.txt_product_vendor_number.setText("Call Vendor : "+dataModel.getPhoneNumber());
        
        holder.txtRemoveFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(holder,position);

                int newPosition = position;
                cartProductArrayList.remove(newPosition);
                notifyItemRemoved(newPosition);
                notifyItemRangeChanged(newPosition, cartProductArrayList.size());

            }
        });

        holder.txt_product_vendor_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dataModel.getPhoneNumber()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartProductArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,product_price,txt_product_vendor_name,txt_product_vendor_address,txtRemoveFromCart,txt_product_vendor_number;
        public MyViewHolder(View itemView) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.txt_product_name);
            product_price = (TextView) itemView.findViewById(R.id.txt_product_price);
            txt_product_vendor_name = (TextView) itemView.findViewById(R.id.txt_product_vendor_name);
            txt_product_vendor_address = (TextView) itemView.findViewById(R.id.txt_product_vendor_address);
            txtRemoveFromCart = (TextView) itemView.findViewById(R.id.txtRemoveFromCart);
            txt_product_vendor_number = (TextView) itemView.findViewById(R.id.txt_product_vendor_number);

        }
    }


}

