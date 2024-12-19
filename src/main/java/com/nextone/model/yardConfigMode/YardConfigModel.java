/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.yardConfigMode;

/**
 *
 * @author Admin
 */
public class YardConfigModel {
    private YardRankConfig b;
    private YardRankConfig c;
    private YardRankConfig d;
    private YardRankConfig e;

    public YardConfigModel() {
        this.b = new YardRankConfig();
        this.c = new YardRankConfig();
        this.d = new YardRankConfig();
        this.e = new YardRankConfig();
    }

    public YardRankConfig getB() {
        return b;
    }

    public void setB(YardRankConfig b) {
        this.b = b;
    }

    public YardRankConfig getC() {
        return c;
    }

    public void setC(YardRankConfig c) {
        this.c = c;
    }

    public YardRankConfig getD() {
        return d;
    }

    public void setD(YardRankConfig d) {
        this.d = d;
    }

    public YardRankConfig getE() {
        return e;
    }

    public void setE(YardRankConfig e) {
        this.e = e;
    }
    
}
