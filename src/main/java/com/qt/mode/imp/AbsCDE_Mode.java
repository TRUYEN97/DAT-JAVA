/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.imp;

import com.qt.contest.impContest.shB2.DoXeDoc;
import com.qt.contest.impContest.shB2.DungXe;
import com.qt.contest.impContest.shB2.DungXeNgangDoc;
import com.qt.contest.impContest.shB2.DuongS;
import com.qt.contest.impContest.shB2.DuongTau;
import com.qt.contest.impContest.shB2.KetThuc;
import com.qt.contest.impContest.shB2.KhanCap;
import com.qt.contest.impContest.shB2.NgaTu;
import com.qt.contest.impContest.shB2.TangTocDuongThang;
import com.qt.contest.impContest.shB2.VetBanhXe;
import com.qt.contest.impContest.shB2.XuatPhat;
import com.qt.mode.AbsSaHinhMode;
import com.qt.view.modeView.SaHinhView;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Admin
 */
public abstract class AbsCDE_Mode extends AbsSaHinhMode {

    public AbsCDE_Mode(SaHinhView hinhView, int speedLimit, int timeOut, MODEL_RANK_NAME modelRank, List<String> ranks) {
        super(hinhView, speedLimit, timeOut, modelRank, ranks);
    }

   

    @Override
    protected void creadContestList() {
        contests.clear();
        int rd = new Random().nextInt(4);
        System.out.println(rd);
        contests.add(new XuatPhat(speedLimit));
        contests.add(new DungXe(yardRankConfig.getDungXeChoNg(), speedLimit));
        contests.add(new DungXeNgangDoc(yardRankConfig.getDungXeNgangDoc(), speedLimit));
        if (rd == 0) {
            contests.add(new KhanCap(3));
        }
        contests.add(new VetBanhXe(yardRankModel, yardRankConfig.getVetBanhXe(), speedLimit));
        if (rd == 1) {
            contests.add(new KhanCap(2));
        }
        contests.add(new NgaTu(1, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu1(), speedLimit));
        contests.add(new DuongS(yardRankModel, yardRankConfig.getDuongS(), speedLimit));
        contests.add(new NgaTu(2, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu2(), speedLimit));
        contests.add(new DoXeDoc(yardRankModel, yardRankConfig.getDoXeDoc(), speedLimit));
        contests.add(new NgaTu(3, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu3(), speedLimit));
        if (rd == 2) {
            contests.add(new KhanCap(6));
        }
        contests.add(new DuongTau(yardRankConfig.getDuongTau(), speedLimit));
        contests.add(new TangTocDuongThang(1, speedLimit));
        if (rd == 3) {
            contests.add(new KhanCap(6));
        }
        contests.add(new NgaTu(4, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu4(), speedLimit));
        contests.add(new KetThuc(speedLimit));
    }

}
