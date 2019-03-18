package com.appzone.validate.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "products")
public class ProductModel implements Serializable {
    @PrimaryKey()
    @NonNull
    private String id;
    @NonNull
    private String qr_code_num;
    @NonNull
    private String ar_name;
    @NonNull
    private String en_name;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private  byte [] image;
    @NonNull
    private String imagePathEndPoint;
    @NonNull
    private String type;
    @NonNull
    private String price;
    @NonNull
    private long production_date;
    @NonNull
    private long created_at;
    @Ignore
    private int status;

    @Ignore
    public ProductModel() {
    }

    public ProductModel(@NonNull String id, @NonNull String qr_code_num, @NonNull String ar_name, @NonNull String en_name, @NonNull String imagePathEndPoint, @NonNull String type, @NonNull String price, long production_date, long created_at) {
        this.id = id;
        this.qr_code_num = qr_code_num;
        this.ar_name = ar_name;
        this.en_name = en_name;
        this.imagePathEndPoint = imagePathEndPoint;
        this.type = type;
        this.price = price;
        this.production_date = production_date;
        this.created_at = created_at;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getQr_code_num() {
        return qr_code_num;
    }

    public void setQr_code_num(@NonNull String qr_code_num) {
        this.qr_code_num = qr_code_num;
    }

    @NonNull
    public String getAr_name() {
        return ar_name;
    }

    public void setAr_name(@NonNull String ar_name) {
        this.ar_name = ar_name;
    }

    @NonNull
    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(@NonNull String en_name) {
        this.en_name = en_name;
    }

    @NonNull
    public byte[] getImage() {
        return image;
    }

    public void setImage(@NonNull byte[] image) {
        this.image = image;
    }

    @NonNull
    public String getImagePathEndPoint() {
        return imagePathEndPoint;
    }

    public void setImagePathEndPoint(@NonNull String imagePathEndPoint) {
        this.imagePathEndPoint = imagePathEndPoint;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public String getPrice() {
        return price;
    }

    public void setPrice(@NonNull String price) {
        this.price = price;
    }

    public long getProduction_date() {
        return production_date;
    }

    public void setProduction_date(long production_date) {
        this.production_date = production_date;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
