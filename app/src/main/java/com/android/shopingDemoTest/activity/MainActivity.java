package com.android.shopingDemoTest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.shopingDemoTest.R;
import com.android.shopingDemoTest.fragment.CartListFragment;
import com.android.shopingDemoTest.fragment.ProductListFragment;
import com.android.shopingDemoTest.model.ProductModel;
import com.android.shopingDemoTest.sqliteDb.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    ArrayList<ProductModel> productModels;
    DatabaseHelper databaseHelper;
    boolean doubleBackToExitPressedOnce = false;

    public  String jsonProductArray=" [\n" +
            "    {\n" +
            "      \"productname\": \"Product 1\",\n" +
            "      \"price\": \"2000\",\n" +
            "      \"vendorname\": \"Vendor 1\",\n" +
            "      \"vendoraddress\": \"Pune\",\n" +
            "      \"phoneNumber\": \"+919999999997\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"productname\": \"Product 2\",\n" +
            "      \"price\": \"1000\",\n" +
            "      \"vendorname\": \"Vendor 2\",\n" +
            "      \"vendoraddress\": \"Pune\",\n" +
            "      \"phoneNumber\": \"+919999999997\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"productname\": \"Product 3\",\n" +
            "      \"price\": \"4000\",\n" +
            "      \"vendorname\": \"Vendor 3\",\n" +
            "      \"vendoraddress\": \"Mumbai\",\n" +
            "      \"phoneNumber\": \"+919999999997\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"productname\": \"Product 4\",\n" +
            "      \"price\": \"2000\",\n" +
            "      \"vendorname\": \"Vendor 4\",\n" +
            "      \"vendoraddress\": \"Delhi\",\n" +
            "      \"phoneNumber\": \"+919999999997\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"productname\": \"Product 5\",\n" +
            "      \"price\": \"2000\",\n" +
            "      \"vendorname\": \"Vendor 5\",\n" +
            "      \"vendoraddress\": \"Nasik\",\n" +
            "      \"phoneNumber\": \"+919999999997\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"productname\": \"Product 6\",\n" +
            "      \"price\": \"5000\",\n" +
            "      \"vendorname\": \"Vendor 6\",\n" +
            "      \"vendoraddress\": \"Pune\",\n" +
            "      \"phoneNumber\": \"+919999999997\"\n" +
            "    } ]\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.deleteData();
        try {
            JSONArray jsonProductArrayList= new JSONArray(jsonProductArray);
            for(int i=0;i<jsonProductArrayList.length();i++) {
                JSONObject jsonObject=jsonProductArrayList.getJSONObject(i);
                ProductModel productModel = new ProductModel();
                productModel.setProductname(jsonObject.getString("productname"));
                productModel.setPrice(jsonObject.getString("price"));
                productModel.setVendorname(jsonObject.getString("vendorname"));
                productModel.setVendoraddress(jsonObject.getString("vendoraddress"));
                productModel.setPhoneNumber(jsonObject.getString("phoneNumber"));
                productModel.setIsAdded("0");

                databaseHelper.insertData(productModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        openFragment(ProductListFragment.newInstance("", ""));
    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_product:
                            openFragment(ProductListFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_cart:
                            openFragment(CartListFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}