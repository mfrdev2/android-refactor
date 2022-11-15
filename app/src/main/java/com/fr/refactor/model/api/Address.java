package com.fr.refactor.model.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

@Data
public class Address implements Serializable {
    @SerializedName("place_id")
    private Integer placeId;
    private String licence;
    @SerializedName("osm_type")
    private String osmType;
    @SerializedName("osm_id")
    private Long osmId;
    @SerializedName("boundingbox")
    private ArrayList<String> boundingBox;
    private String lat;
    private String lon;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("class")
    private String myclass;
    private String type;
    private Double importance;
    private String icon;
    private Geojson geojson;
}
