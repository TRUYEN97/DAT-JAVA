/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.yardConfigMode;



/**
 *
 * @author Admin
 */
public class YardRankConfig {
    private final ContestConfig dungXeChoNg = new ContestConfig(5, 2, 0, 0);
    private final ContestConfig dungXeNgangDoc = new ContestConfig(5, 2, 0, 0);
    private final ContestConfig vetBanhXe = new ContestConfig(5, 2, 10, 12);
    private final ContestConfig ngaTu1 = new ContestConfig(5, 2, 0, 0);
    private final ContestConfig ngaTu2 = new ContestConfig(5, 2, 0, 0);
    private final ContestConfig ngaTu3 = new ContestConfig(5, 2, 0, 0);
    private final ContestConfig ngaTu4 = new ContestConfig(5, 2, 0, 0);
    private final ContestConfig duongS = new ContestConfig(0, 0, 10, 12);
    private final ContestConfig doXeDoc = new ContestConfig(5, 0, 10, 12);
    private final ContestConfig doXeNgang = new ContestConfig(2, 0, 10, 12);
    private final ContestConfig duongTau = new ContestConfig(5, 2, 0, 0);

    public YardRankConfig() {
    }

    public ContestConfig getDungXeChoNg() {
        return dungXeChoNg;
    }

    public ContestConfig getDungXeNgangDoc() {
        return dungXeNgangDoc;
    }

    public ContestConfig getVetBanhXe() {
        return vetBanhXe;
    }

    public ContestConfig getNgaTu1() {
        return ngaTu1;
    }

    public ContestConfig getNgaTu2() {
        return ngaTu2;
    }

    public ContestConfig getNgaTu3() {
        return ngaTu3;
    }

    public ContestConfig getNgaTu4() {
        return ngaTu4;
    }

    public ContestConfig getDuongS() {
        return duongS;
    }

    public ContestConfig getDoXeDoc() {
        return doXeDoc;
    }

    public ContestConfig getDoXeNgang() {
        return doXeNgang;
    }

    public ContestConfig getDuongTau() {
        return duongTau;
    }
    
}
