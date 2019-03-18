package com.appzone.validate.models;

import java.io.Serializable;

public class ProductDataModel implements Serializable {

    private Products data;

    public Products getData() {
        return data;
    }

    public class Products implements Serializable
    {
        private String id;
        private String ar_name;
        private String en_name;
        private String image;
        private String type;
        private String price;
        private String qr_code_num;
        private String production_date;
        private String trader_id_fk;

        public String getId() {
            return id;
        }

        public String getAr_name() {
            return ar_name;
        }

        public String getEn_name() {
            return en_name;
        }

        public String getImage() {
            return image;
        }

        public String getType() {
            return type;
        }

        public String getPrice() {
            return price;
        }

        public String getQr_code_num() {
            return qr_code_num;
        }

        public String getProduction_date() {
            return production_date;
        }

        public String getTrader_id_fk() {
            return trader_id_fk;
        }
    }
}
