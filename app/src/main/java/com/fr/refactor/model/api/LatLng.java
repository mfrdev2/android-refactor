package com.fr.refactor.model.api;

import java.io.Serializable;

import lombok.Data;


@Data
public class LatLng implements Serializable {
    private double latitude;
    private double longitude;
    private float bearing = 10.0f;

    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
