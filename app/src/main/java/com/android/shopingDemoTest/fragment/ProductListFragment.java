package com.android.shopingDemoTest.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.shopingDemoTest.R;
import com.android.shopingDemoTest.adapter.ProductListAdapter;
import com.android.shopingDemoTest.model.ProductModel;
import com.android.shopingDemoTest.sqliteDb.DatabaseHelper;

import java.util.ArrayList;


public class ProductListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<ProductModel> productModels;
    RecyclerView rvProduct;
    TextView txtEmptyView;
    ProductListAdapter productListAdapter;
    DatabaseHelper databaseHelper;
    private String mParam1;
    private String mParam2;

    public ProductListFragment() {

    }

    public static ProductListFragment newInstance(String param1, String param2) {
        ProductListFragment fragment = new ProductListFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        rvProduct = (RecyclerView) view.findViewById(R.id.recycler_view_product);
        txtEmptyView = (TextView) view.findViewById(R.id.empty_view);
        productModels = databaseHelper.getProductsList();
        getActivity().setTitle("Product");

        if (productModels.isEmpty()) {
            rvProduct.setVisibility(View.GONE);
            txtEmptyView.setVisibility(View.VISIBLE);
        }
        else {
            rvProduct.setVisibility(View.VISIBLE);
            txtEmptyView.setVisibility(View.GONE);
        }
        productListAdapter = new ProductListAdapter(getActivity(), productModels, new ProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductListAdapter.MyViewHolder view, int position) {

                databaseHelper.updateData(String.valueOf(productModels.get(position).getId()),"1");
                Toast.makeText(getContext(), "Product moved to Cart", Toast.LENGTH_SHORT).show();
                productModels = databaseHelper.getProductsList();
                if (productModels.isEmpty()) {
                    rvProduct.setVisibility(View.GONE);
                    txtEmptyView.setVisibility(View.VISIBLE);
                }
                else {
                    rvProduct.setVisibility(View.VISIBLE);
                    txtEmptyView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        });

        rvProduct.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvProduct.setItemAnimator(new DefaultItemAnimator());
        rvProduct.setAdapter(productListAdapter);

        return view;
    }
}