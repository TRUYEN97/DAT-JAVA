/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest.process;

import com.qt.model.input.UserModel;
import com.qt.model.modelTest.ErrorCode;
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
public class ProcessModel extends UserModel{

    public static final int RUNNING = 0;
    public static final int PASS = 1;
    public static final int FAIL = -1;
    private String carId = "0";
    private String startTime;
    private String endTime;
    private String modeName;
    private int score;
    private long cycleTime;
    private double distance;
    private final List<ErrorCode> errorCodes;
    private final List<ContestDataModel> contests;
    private final LocationModel location;
    private int contestsResult = RUNNING;

    public ProcessModel() {
        this.contests = new ArrayList<>();
        this.errorCodes = new ArrayList<>();
        this.location = new LocationModel();
    }

    public void addErrorcode(ErrorCode errorcode) {
        if (errorcode == null || errorcode.getErrKey() == null) {
            return;
        }
        this.errorCodes.add(errorcode);
    }

    public void addContestModel(ContestDataModel contestModel) {
        if (contestModel == null) {
            return;
        }
        this.contests.add(contestModel);
    }

    public void clear() {
        this.contestsResult = RUNNING;
        this.cycleTime = 0;
        this.startTime = null;
        this.endTime = null;
        this.errorCodes.clear();
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
