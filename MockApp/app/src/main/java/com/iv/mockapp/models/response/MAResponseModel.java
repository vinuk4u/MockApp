package com.iv.mockapp.models.response;

import com.iv.mockapp.models.MALocationModel;
import com.iv.mockapp.models.MATransportInfoModel;

/**
 * Created by vineeth on 10/09/16
 */
public class MAResponseModel {
    private int id;
    private String name;

    private MATransportInfoModel fromcentral;

    private MALocationModel location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MATransportInfoModel getTransportInfo() {
        return fromcentral;
    }

    public void setTransportInfo(MATransportInfoModel fromcentral) {
        this.fromcentral = fromcentral;
    }

    public MALocationModel getLocation() {
        return location;
    }

    public void setLocation(MALocationModel location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
