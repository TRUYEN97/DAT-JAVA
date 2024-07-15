/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest.process;

import com.qt.model.modelTest.contest.ContestDataModel;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
public class ProcessModel {

    private String id = "0";
    private String carId = "0";
    private String startTime;
    private String endTime;
    private String modeName;
    private int score;
    private long cycleTime;
    private double distance;
    private final List<String> errorcodes;
    private final List<ContestDataModel> contests;
    private boolean pass;

    public ProcessModel() {
        this.contests = new ArrayList<>();
        this.errorcodes = new ArrayList<>();
    }

    public void addErrorcode(String errorcode) {
        if (errorcode == null || errorcode.isBlank()) {
            return;
        }
        this.errorcodes.add(errorcode);
    }

    public void addContestModel(ContestDataModel contestModel) {
        if (contestModel == null) {
            return;
        }
        this.contests.add(contestModel);
    }

    public void clear() {
        this.cycleTime = 0;
        this.startTime = "";
        this.endTime = "";
        this.contests.clear();
        this.distance = 0;
        this.score = 0;
    }

    public synchronized void subtract(int score) {
        if (this.score <= 0) {
            return;
        }
        this.score -= score;
        if (this.score <= 0) {
            this.score = 0;
        }
    }

}
