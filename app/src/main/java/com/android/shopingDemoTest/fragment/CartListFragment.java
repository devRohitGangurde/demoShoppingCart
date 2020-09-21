package com.android.shopingDemoTest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.shopingDemoTest.R;
import com.android.shopingDemoTest.adapter.CartListAdapter;
import com.android.shopingDemoTest.model.ProductModel;
import com.android.shopingDemoTest.sqliteDb.DatabaseHelper;

import java.util.ArrayList;


public class CartListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<ProductModel> productModels;
    RecyclerView rvCart;
    CartListAdapter cartListAdapter;
    DatabaseHelper databaseHelper;
    private String mParam1;
    private String mParam2;
    TextView empty_view,txtTotalPrice;

    public CartListFragment() {
    }

    public static CartListFragment newInstance(String param1, String param2) {
        CartListFragment fragment = new CartListFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        productModels = new ArrayList<>();
        rvCart = (RecyclerView) view.findViewById(R.id.recycler_view_cart);
        empty_view=(TextView)view.findViewById(R.id.empty_view);
        txtTotalPrice=(TextView)view.findViewById(R.id.txtTotalPrice);
        getActivity().setTitle("Cart");

        databaseHelper = new DatabaseHelper(getActivity());
        productModels=databaseHelper.getCartProducts();

        if (productModels.isEmpty()) {
            rvCart.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
        }
        else {
            rvCart.setVisibility(View.VISIBLE);
            empty_view.setVisibility(View.GONE);
        }
       getTotalPrice();
        cartListAdapter = new CartListAdapter(getActivity(),productModels , new CartListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CartListAdapter.MyViewHolder view, int position) {
                databaseHelper.updateData(String.valueOf(productModels.get(position).getId()),"0");
                Toast.makeText(getContext(), "Product removed from Cart", Toast.LENGTH_SHORT).show();
                productModels=databaseHelper.getCartProducts();

                if (productModels.isEmpty()) {
                    rvCart.setVisibility(View.GONE);
                    empty_view.setVisibility(View.VISIBLE);
                }
                else {
                    rvCart.setVisibility(View.VISIBLE);
                    empty_view.setVisibility(View.GONE);
                }
                getTotalPrice();

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        });

        rvCart.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvCart.setItemAnimator(new DefaultItemAnimator());
        rvCart.setAdapter(cartListAdapter);

        return view;
    }
    public void getTotalPrice(){
        int total = 0;
        for (int i=0;i<productModels.size();i++){
            int price= Integer.parseInt(productModels.get(i).getPrice());
            total += price;
        }
        txtTotalPrice.setText("Total Price : "+total+"");
    }
}