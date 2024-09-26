/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.input.yard;


/**
 *
 * @author Admin
 */
public class YardModel {

    public YardModel() {
        this.rankB = new YardRankModel();
        this.rankC = new YardRankModel();
        this.rankD = new YardRankModel();
        this.rankE = new YardRankModel();
    }
    
    private final YardRankModel rankB; 
    private final YardRankModel rankC; 
    private final YardRankModel rankD; 
    private final YardRankModel rankE; 

    public YardRankModel getRankB() {
        return rankB;
    }

    public YardRankModel getRankC() {
        return rankC;
    }

    public YardRankModel getRankD() {
        return rankD;
    }

    public YardRankModel getRankE() {
        return rankE;
    }

    
    
}
