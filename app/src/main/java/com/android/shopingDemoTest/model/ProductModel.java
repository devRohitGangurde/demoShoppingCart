package com.android.shopingDemoTest.model;

public class ProductModel
{
    private int id;

    private String phoneNumber;

    private String vendoraddress;

    private String price;

    private String productname;

    private String vendorname;

    private String isAdded;

    public int getId(){
        return id;
    }
    public void  setId(int id){
        this.id=id;
    }

    public String getPhoneNumber ()
    {
        return phoneNumber;
    }

    public void setPhoneNumber (String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getVendoraddress ()
    {
        return vendoraddress;
    }

    public void setVendoraddress (String vendoraddress)
    {
        this.vendoraddress = vendoraddress;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getProductname ()
    {
        return productname;
    }

    public void setProductname (String productname)
    {
        this.productname = productname;
    }

    public String getVendorname ()
    {
        return vendorname;
    }

    public void setVendorname (String vendorname)
    {
        this.vendorname = vendorname;
    }

    public String getIsAdded(){
        return  isAdded;
    }

    public void setIsAdded(String isAdded){
        this.isAdded=isAdded;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [phoneNumber = "+phoneNumber+", vendoraddress = "+vendoraddress+", price = "+price+", productname = "+productname+", vendorname = "+vendorname+"]";
    }
}