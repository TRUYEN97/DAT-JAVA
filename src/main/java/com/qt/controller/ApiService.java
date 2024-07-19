/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller;

import com.qt.common.API.FileInfo;
import com.qt.common.API.JsonBodyAPI;
import com.qt.common.API.Response;
import com.qt.common.API.RestAPI;
import com.qt.common.ErrorLog;
import com.qt.controller.modeController.ModeManagement;
import com.qt.model.input.UserModel;
import com.qt.model.modelTest.process.ProcessModel;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Admin
 */
public class ApiService {
    
    private static volatile ApiService insatnce;
    private final Properties properties;
    private final FileTestService fileTestService;
    
    private ApiService() {
        this.properties = new Properties();
        this.fileTestService = FileTestService.getInstance();
        try {
            this.properties.load(getClass().getResourceAsStream("/config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
            ErrorLog.addError("ApiService-constructor", ex);
        }
    }
    
    public static ApiService getInstance() {
        ApiService ins = insatnce;
        if (ins == null) {
            synchronized (ApiService.class) {
                ins = insatnce;
                if (ins == null) {
                    insatnce = ins = new ApiService();
                }
            }
        }
        return ins;
    }
    
    public boolean checkCarId(String id) {
        String url = this.properties.getProperty("checkCarId");
        if (url == null) {
            return false;
        }
        RestAPI restAPI = new RestAPI();
        Response response = restAPI.sendPost(url, JsonBodyAPI.builder().put("id", id));
        return response != null && response.isSuccess();
    }
    
    public UserModel checkUserId(String id) {
        if (id == null || id.isBlank() || id == "0") {
            UserModel userModel = new ProcessModel();
            userModel.setId("0");
            userModel.setName("");
            userModel.setModeName(ModeManagement.DEFAULT_MODE);
            return userModel;
        }
        String url = this.properties.getProperty("checkUserId");
        if (url == null) {
            return null;
        }
        RestAPI restAPI = new RestAPI();
        Response response = restAPI.sendPost(url, JsonBodyAPI.builder().put("id", id));
        if (!response.isSuccess()) {
            return null;
        }
        return response.getData();
    }
    
    public boolean sendData(String id) {
        String url = this.properties.getProperty("sendData");
        if (url == null) {
            return false;
        }
        File jsonFile = this.fileTestService.getFileJsonPath(id);
        File imgFile = this.fileTestService.getFileImagePath(id);
        if (jsonFile == null || imgFile == null) {
            return false;
        }
        RestAPI restAPI = new RestAPI();
        FileInfo jsonF = new FileInfo(FileInfo.type.FILE);
        jsonF.setFile(jsonFile);
        jsonF.setName(String.format("%s.json", id));
        FileInfo imgF = new FileInfo(FileInfo.type.FILE);
        imgF.setFile(jsonFile);
        imgF.setName(String.format("%s.png", id));
        Response response = restAPI.uploadFile(url, null, jsonF, imgF);
        return response != null && response.isSuccess();
    }
}
