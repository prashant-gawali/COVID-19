package com.example.covid_19.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class data {
    @SerializedName("country")
    private String country;

    @SerializedName("countryInfo")
    private ModelCountryInfo countryInfo;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "data{" +
                "country='" + country + '\'' +
                ", countryInfo=" + countryInfo +
                '}';
    }

    public ModelCountryInfo getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(ModelCountryInfo countryInfo) {
        this.countryInfo = countryInfo;
    }

    public class ModelCountryInfo {

        @SerializedName("flag")
        private String flag;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }

}
