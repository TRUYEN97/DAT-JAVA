/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest.contest;

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
public class ContestDataModel {

    private final String name;
    private String startTime = "";
    private String endTime = "";
    private long cycleTime = 0;
    private int penaltyPoints = 0;
    private final List<String> errorcodes;

    public ContestDataModel(String name) {
        this.name = name;
        this.errorcodes = new ArrayList<>();
    }
    
    public void clearErrorCode(){
        errorcodes.clear();
    }
    
    public void removeErrorCode(String errorCode){
        if (errorCode == null) {
            return;
        }
        errorcodes.remove(errorCode);
    }
    
    public void addErrorCode(String errorCode){
        if (errorCode == null) {
            return;
        }
        errorcodes.add(errorCode);
    }

    public void clear() {
        startTime = "";
        endTime = "";
        cycleTime = 0;
        penaltyPoints = 0;
        errorcodes.clear();
    }

}
