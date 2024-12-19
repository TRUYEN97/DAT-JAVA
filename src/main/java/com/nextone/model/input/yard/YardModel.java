/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.input.yard;

/**
 *
 * @author Admin
 */
public class YardModel {

    private final YardRankModel rankB;
    private final YardRankModel rankC;
    private final YardRankModel rankD;
    private final YardRankModel rankE;
    private final TrafficLightModel trafficLightModel;
    private final TrafficLightModel trafficLightModel1;

    public YardModel() {
        this.rankB = new YardRankModel();
        this.rankC = new YardRankModel();
        this.rankD = new YardRankModel();
        this.rankE = new YardRankModel();
        this.trafficLightModel = new TrafficLightModel();
        this.trafficLightModel1 = new TrafficLightModel();
    }

    public TrafficLightModel getTrafficLightModel1() {
        return trafficLightModel1;
    }
    

    public TrafficLightModel getTrafficLightModel() {
        return trafficLightModel;
    }
    

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
