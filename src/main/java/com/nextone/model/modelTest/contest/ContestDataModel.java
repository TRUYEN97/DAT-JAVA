/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.modelTest.contest;

import com.nextone.model.modelTest.Errorcode;
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
    private final List<Errorcode> errorCodes;

    public ContestDataModel() {
        this("");
    }

    public ContestDataModel(String name) {
        this.contestName = name;
        this.errorCodes = new ArrayList<>();
    }
    
    public void clearErrorCode(){
        errorCodes.clear();
    }
    
    public void removeErrorCode(Errorcode errorCode){
        if (errorCode == null) {
            return;
        }
        errorCodes.remove(errorCode);
    }
    
    public void addErrorCode(Errorcode errorcode){
        if (errorcode == null || errorcode.getErrKey() == null) {
            return;
        }
        errorCodes.add(errorcode);
    }

    public void clear() {
        startTime = "";
        endTime = "";
        cycleTime = 0;
        errorCodes.clear();
    }

}
