package com.example.lifecareapplication.helper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuestionsResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("error")
    private String error;


    @Expose
    @SerializedName("products")
    ArrayList<Product> products;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public  class Product
    {

        @SerializedName("id")
        private String id;

        @SerializedName("productName")
        private String productName;

        @SerializedName("productImg")
        private String productImg;

        @SerializedName("isdeleted")
        private String isdeleted;


        @SerializedName("addedon")
        private String addedon;

        @SerializedName("updatedon")
        private String updatedon;


        public Product(String id, String productName, String productImg, String isdeleted, String addedon, String updatedon) {
            this.id = id;
            this.productName = productName;
            this.productImg = productImg;
            this.isdeleted = isdeleted;
            this.addedon = addedon;
            this.updatedon = updatedon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public String getIsdeleted() {
            return isdeleted;
        }

        public void setIsdeleted(String isdeleted) {
            this.isdeleted = isdeleted;
        }

        public String getAddedon() {
            return addedon;
        }

        public void setAddedon(String addedon) {
            this.addedon = addedon;
        }

        public String getUpdatedon() {
            return updatedon;
        }

        public void setUpdatedon(String updatedon) {
            this.updatedon = updatedon;
        }
    }


}
