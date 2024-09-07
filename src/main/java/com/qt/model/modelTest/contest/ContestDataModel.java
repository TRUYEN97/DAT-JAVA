/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest.contest;

import com.qt.model.modelTest.ErrorCode;
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

    private String contestName;
    private String startTime = "";
    private String endTime = "";
    private long cycleTime = 0;
    private final List<ErrorCode> errorcodes;

    public ContestDataModel() {
        this("");
    }

    public ContestDataModel(String name) {
        this.contestName = name;
        this.errorcodes = new ArrayList<>();
    }
    
    public void clearErrorCode(){
        errorcodes.clear();
    }
    
    public void removeErrorCode(ErrorCode errorCode){
        if (errorCode == null) {
            return;
        }
        errorcodes.remove(errorCode);
    }
    
    public void addErrorCode(ErrorCode errorcode){
        if (errorcode == null || errorcode.getName() == null) {
            return;
        }
        errorcodes.add(errorcode);
    }

    public void clear() {
        startTime = "";
        endTime = "";
        cycleTime = 0;
        errorcodes.clear();
    }

}
