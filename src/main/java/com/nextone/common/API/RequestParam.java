package com.nextone.common.API;

import java.util.ArrayList;
import java.util.Collection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Administrator
 */
public class RequestParam {

    private final ArrayList<String[]> params;
    private final ArrayList<String> paths;

    public static RequestParam builder() {
        return new RequestParam();
    }

    public RequestParam(ArrayList<String[]> params, ArrayList<String> paths) {
        this.params = params;
        this.paths = paths;
    }
    
    public RequestParam() {
        this.params = new ArrayList<>();
        this.paths = new ArrayList<>();
    }

    @Override
    public RequestParam clone(){
        return new RequestParam((ArrayList)params.clone(), (ArrayList) paths.clone());
    }
    
    public RequestParam addParam(String key, Object... values) {
        if (key != null && values != null && values.length > 0) {
            for (Object value : values) {
                if (value == null) {
                    continue;
                }
                this.params.add(new String[]{key, String.valueOf(value)});
            }
        }
        return this;
    }

    public RequestParam addParam(String key, Collection values) {
        return addParam(key, values.toArray());
    }

    public String toURL() {
        StringBuilder builder = new StringBuilder();
        for (String path : paths) {
            builder.append("/").append(path);
        }
        if (!params.isEmpty()) {
            builder.append("?");
            for (String[] elem : this.params) {
                if (elem[1] != null) {
                    builder.append(elem[0]);
                    builder.append("=").append(elem[1]);
                    builder.append("&");
                }
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString().trim();
    }

    @Override
    public String toString() {
        return toURL();
    }

    public RequestParam addPath(String path) {
        if (path != null) {
            this.paths.add(path);
        }
        return this;
    }

}
