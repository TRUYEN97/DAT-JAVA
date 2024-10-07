/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.input.yard;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class YardRankModel {


    public YardRankModel() {
        this.roadZs = new ArrayList<>();
        this.roadSs = new ArrayList<>();
        this.packings = new ArrayList<>();
        this.packing1s = new ArrayList<>();
    }

    private final List<Boolean> roadZs;
    private final List<Boolean> roadSs;
    private final List<Boolean> packings;
    private final List<Boolean> packing1s;

    public List<Boolean> getRoadZs() {
        return roadZs;
    }

    public List<Boolean> getRoadSs() {
        return roadSs;
    }

    public List<Boolean> getPackings() {
        return packings;
    }

    public List<Boolean> getPacking1s() {
        return packing1s;
    }
    
    
    

}
