/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.input.yard;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class YardRankModel {


    public YardRankModel() {
        this.roadZs = new ArrayList<>();
        this.roadZs1 = new ArrayList<>();
        this.roadSs = new ArrayList<>();
        this.packings = new ArrayList<>();
        this.packings1 = new ArrayList<>();
    }

    private final List<Boolean> roadZs;
    private final List<Boolean> roadZs1;
    private final List<Boolean> roadSs;
    private final List<Boolean> packings;
    private final List<Boolean> packings1;

    public List<Boolean> getRoadZs1() {
        return roadZs1;
    }
    
    public List<Boolean> getRoadZs() {
        return roadZs;
    }

    public List<Boolean> getRoadSs() {
        return roadSs;
    }

    public List<Boolean> getPackings() {
        return packings;
    }

    public List<Boolean> getPackings1() {
        return packings1;
    }
    
    
    

}
