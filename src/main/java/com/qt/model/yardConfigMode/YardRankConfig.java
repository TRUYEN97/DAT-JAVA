/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.yardConfigMode;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Admin
 */
public class YardRankConfig {
    private final ContestConfig dungXeChoNg;
    private final ContestConfig dungXeNgangDoc;
    private final List<ContestConfig> vetBanhXe;
    private final ContestConfig ngaTu1;
    private final ContestConfig ngaTu2;
    private final ContestConfig ngaTu3;
    private final ContestConfig ngaTu4;
    private final List<ContestConfig> duongS;
    private final List<ContestConfig> doXeDoc;
    private final List<ContestConfig> doXeNgang;
    private final ContestConfig duongTau;

    public YardRankConfig() {
        this.dungXeChoNg  = new ContestConfig(3, 2, 0, 0);
        this.dungXeNgangDoc  = new ContestConfig(20, 2, 0, 0);
        this.ngaTu1 = new ContestConfig(30, 2, 0, 0);
        this.ngaTu2 = new ContestConfig(25, 2, 0, 0);
        this.ngaTu3 = new ContestConfig(30, 2, 0, 0);
        this.ngaTu4 = new ContestConfig(25, 2, 0, 0);
        this.duongTau = new ContestConfig(3, 2, 0, 0);
        this.vetBanhXe =  new ArrayList<>();
        this.doXeDoc =  new ArrayList<>();
        this.duongS  =  new ArrayList<>();
        this.doXeNgang =  new ArrayList<>();
        this.vetBanhXe.add(new ContestConfig(30, 10, 60, 65));
        this.vetBanhXe.add(new ContestConfig(30, 10, 65, 70));
        this.doXeDoc.add(new ContestConfig(5, 0, 10, 17));
        this.doXeDoc.add(new ContestConfig(5, 0, 17, 25));
        this.doXeDoc.add(new ContestConfig(5, 0, 25, 35));
        this.duongS.add(new ContestConfig(5, 0, 50, 62));
        this.duongS.add(new ContestConfig(5, 0, 62, 74));
        this.doXeNgang.add(new ContestConfig(5, 0, 60, 68));
        this.doXeNgang.add(new ContestConfig(5, 0, 68, 76));
    }

    public ContestConfig getDungXeChoNg() {
        return dungXeChoNg;
    }

    public ContestConfig getDungXeNgangDoc() {
        return dungXeNgangDoc;
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

    public ContestConfig getDuongTau() {
        return duongTau;
    }

    public List<ContestConfig> getVetBanhXe() {
        return vetBanhXe;
    }

    public List<ContestConfig> getDuongS() {
        return duongS;
    }

    public List<ContestConfig> getDoXeDoc() {
        return doXeDoc;
    }

    public List<ContestConfig> getDoXeNgang() {
        return doXeNgang;
    }
    
}
