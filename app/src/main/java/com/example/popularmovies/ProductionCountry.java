package com.example.popularmovies;


import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCountry implements Serializable
{

    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("name")
    @Expose
    private String name;
    private final static long serialVersionUID = 2779399510725929973L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProductionCountry() {
    }

    /**
     *
     * @param name
     * @param iso31661
     */
    public ProductionCountry(String iso31661, String name) {
        super();
        this.iso31661 = iso31661;
        this.name = name;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
