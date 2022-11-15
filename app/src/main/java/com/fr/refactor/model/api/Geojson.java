package com.fr.refactor.model.api;

import com.google.gson.JsonArray;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

@Data
public class Geojson implements Serializable {
    public String type;
    public ArrayList<ArrayList<JsonArray>> coordinates;
}
