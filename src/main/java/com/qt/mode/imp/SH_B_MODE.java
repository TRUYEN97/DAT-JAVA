/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.mode.imp;

import com.qt.contest.impContest.shB2.DoXeDoc;
import com.qt.contest.impContest.shB2.DoXeNgang;
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
import com.qt.pretreatment.IKeyEvent;
import com.qt.view.modeView.SaHinhView;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class SH_B_MODE extends AbsSaHinhMode {

    public SH_B_MODE(SaHinhView hinhView, boolean isOnline) {
        super(hinhView, 24, 1080, MODEL_RANK_NAME.RANK_B, List.of("B1", "B2"), isOnline);
    }

    @Override
    protected void creadContestList() {
        contests.clear();
        int rd = new Random().nextInt(3);
        contests.add(new XuatPhat(speedLimit));
        contests.add(new DungXe(yardRankConfig.getDungXeChoNg(), speedLimit));
        contests.add(new DungXeNgangDoc(yardRankConfig.getDungXeNgangDoc(), speedLimit));
        contests.add(new VetBanhXe(yardRankModel, yardRankConfig.getVetBanhXe(), speedLimit));
        if (rd == 0) {
            contests.add(new KhanCap(20));
        }
        contests.add(new NgaTu(1, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu1(), speedLimit));
        contests.add(new DuongS(yardRankModel, yardRankConfig.getDuongS(), speedLimit));
        contests.add(new NgaTu(2, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu2(), speedLimit));
        contests.add(new DoXeDoc(yardRankModel, yardRankConfig.getDoXeDoc(), speedLimit));
        contests.add(new NgaTu(3, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu3(), speedLimit));
        if (rd == 1) {
            contests.add(new KhanCap(30));
        }
        contests.add(new DuongTau(yardRankConfig.getDuongTau(), speedLimit));
        contests.add(new TangTocDuongThang(1, speedLimit, yardRankConfig.getTangToc()));
        if (rd == 2) {
            contests.add(new KhanCap(30));
        }
        contests.add(new DoXeNgang(yardRankModel, yardRankConfig.getDoXeNgang(), speedLimit));
        contests.add(new NgaTu(4, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu4(), speedLimit));
        contests.add(new KetThuc(speedLimit));
    }

    @Override
    protected void createPrepareKeyEvents(Map<String, IKeyEvent> events) {
    }

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> events) {
    }

}
