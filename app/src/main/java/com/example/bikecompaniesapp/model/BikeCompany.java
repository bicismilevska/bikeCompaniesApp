package com.example.bikecompaniesapp.model;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BikeCompany {

  //  private List<String> company;
  //  private Collection<String> company;
  @SerializedName("company")
  private Object company;

    private String href;

    private String id;

    private Location location;

    private String name;
    private String source;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Object getmCompany() {
        return company;
    }
    public void setmCompany(Object company) {
        this.company=company;
    }
//
//    public Collection<String> getCompany() {
//        return company;
//    }
//
//    public void setCompany(Object company) {
//        if (value instanceof Collection) {
//            this.company = (Collection<String>) company;
//        } else {
//            this.company = Arrays.asList((String) company);
//        }
//    }

    public String getSource() {
        return source;
    }


    public void setSource(String source) {
        this.source = source;
    }
//    public List<String> getCompany() {
//        return company;
//    }
//
//
//    public void setCompany(List<String> company) {
//        this.company = company;
//    }


    public String getHref() {
        return href;
    }


    public void setHref(String href) {
        this.href = href;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }



}

