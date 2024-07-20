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

    public static final int API_INVALID = -3;
    public static final int URL_INVALID = -2;
    public static final int ID_INVALID = -1;
    public static final int START = 1;
    public static final int WAIT = 0;
    private static final String CHECK_CAR_ID = "checkCarId";
    private static final String CHECK_USER_ID = "checkUserId";
    private static final String SEND_DATA = "sendData";
    private static final String RUNNABLE = "runnable";
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
        String url = this.properties.getProperty(CHECK_CAR_ID);
        if (url == null) {
            ErrorLog.addError(this, "không tìm thấy: checkCarId url");
            return false;
        }
        RestAPI restAPI = new RestAPI();
        Response response = restAPI.sendPost(url, JsonBodyAPI.builder().put("id", id));
        return response != null && response.isSuccess();
    }

    public UserModel checkUserId(String id) {
        if (id == null || id.isBlank() || "0".equals(id)) {
            UserModel userModel = new ProcessModel();
            userModel.setId("0");
            userModel.setName("");
            userModel.setModeName(ModeManagement.DEFAULT_MODE);
            return userModel;
        }
        String url = this.properties.getProperty(CHECK_USER_ID);
        if (url == null) {
            ErrorLog.addError(this, "không tìm thấy: checkUserId url");
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
        if (id == null || id.isBlank()) {
            return false;
        }
        if (id.equals("0")) {
            return true;
        }
        String url = this.properties.getProperty(SEND_DATA);
        if (url == null) {
            return false;
        }
        File jsonFile = this.fileTestService.getFileJsonPath(id);
        File imgFile = this.fileTestService.getFileImagePath(id);
        if (jsonFile == null || imgFile == null) {
            ErrorLog.addError(this, "Không tìm thấy file json, png của id: " + id);
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

    public int checkRunnable(String id, String carId) {
        if (id == null || id.isBlank() || carId == null || carId.isBlank()) {
            return WAIT;
        }
        if (id.equals("0")) {
            return START;
        }
        String url = this.properties.getProperty(RUNNABLE);
        if (url == null) {
            ErrorLog.addError(this, "không tìm thấy: checkUserId url");
            return URL_INVALID;
        }
        RestAPI restAPI = new RestAPI();
        Response response = restAPI.sendPost(url, JsonBodyAPI.builder()
                .put("id", id).put("carId", carId));
        if (!response.isSuccess()) {
            ErrorLog.addError(this, response.getResponse());
            return API_INVALID;
        }
        return response.getData();
    }
}
