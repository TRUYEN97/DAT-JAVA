/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.API.FileInfo;
import com.qt.common.API.JsonBodyAPI;
import com.qt.common.API.Response;
import com.qt.common.API.RestAPI;
import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.controller.modeController.ModeManagement;
import com.qt.model.input.UserModel;
import com.qt.output.SoundPlayer;
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
    private static final String CAN_START = "canStart";
    private static final String ID = "id";
    private static final String SERVER_PING_ADDR = "serverPing";
    private static final String CHECK_CAR_ID = "checkCarId";
    private static final String CHECK_USER_ID = "checkUserId";
    private static final String SEND_DATA = "sendData";
    private static final String RUNNABLE = "runnable";
    private static volatile ApiService insatnce;
    private final Properties properties;
    private final RestAPI restAPI;
    private final SoundPlayer soundPlayer;

    private ApiService() {
        this.properties = new Properties();
        this.restAPI = new RestAPI();
        this.soundPlayer = SoundPlayer.getInstance();
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
        if (checkPingToServer()) {
            return false;
        }
        if (url == null) {
            ErrorLog.addError(this, "không tìm thấy: checkCarId url");
            return false;
        }
        Response response = restAPI.sendPost(url, JsonBodyAPI.builder().put(ID, id));
        return response != null && response.isSuccess();
    }

    public UserModel checkUserId(String id) {
        if (id == null || id.isBlank() || "0".equals(id)) {
            UserModel userModel = new UserModel();
            userModel.setId("0");
            userModel.setName("");
            userModel.setModeName(ModeManagement.DEFAULT_MODE);
            return userModel;
        }
        if (checkPingToServer()) {
            return null;
        }
        String url = this.properties.getProperty(CHECK_USER_ID);
        if (url == null) {
            ErrorLog.addError(this, "không tìm thấy: checkUserId url");
            return null;
        }
        Response response = restAPI.sendPost(url, JsonBodyAPI.builder().put(ID, id));
        if (!response.isSuccess()) {

            return null;
        }
        return response.getData(UserModel.class);
    }
    public boolean sendData(byte[] jsonFile, File imgFile) {
        if (checkPingToServer()) {
            return false;
        }
        String url = this.properties.getProperty(SEND_DATA);
        if (url == null) {
            return false;
        }
        if (jsonFile == null || imgFile == null) {
            return false;
        }
        FileInfo jsonF = new FileInfo(FileInfo.type.BYTE);
        jsonF.setFile(jsonFile);
        jsonF.setPartName("data");
        jsonF.setName("data.json");
        //////////////////
        FileInfo imgF = new FileInfo(FileInfo.type.FILE);
        imgF.setFile(imgFile);
        imgF.setPartName("image");
        imgF.setName("image.png");
        Response response = restAPI.uploadFile(url, null, jsonF, imgF);
        return response != null && response.isSuccess();
    }

    public boolean sendData(File jsonFile, File imgFile) {
        if (checkPingToServer()) {
            return false;
        }
        String url = this.properties.getProperty(SEND_DATA);
        if (url == null) {
            return false;
        }
        if (jsonFile == null || imgFile == null) {
            return false;
        }
        FileInfo jsonF = new FileInfo(FileInfo.type.FILE);
        jsonF.setFile(jsonFile);
        jsonF.setPartName("data");
        jsonF.setName(jsonFile.getName());
        //////////////////
        FileInfo imgF = new FileInfo(FileInfo.type.FILE);
        imgF.setFile(jsonFile);
        imgF.setPartName("image");
        imgF.setName(jsonFile.getName());
        Response response = restAPI.uploadFile(url, null, jsonF, imgF);
        return response != null && response.isSuccess();
    }

    private boolean checkPingToServer() {
        String addr = this.properties.getProperty(SERVER_PING_ADDR);
        if (!Util.ping(addr, 2)) {
            ErrorLog.addError(this, "không thể ping đến server: " + addr);
            this.soundPlayer.pingServerFailed();
            return true;
        }
        return false;
    }

    public int checkRunnable(String id) {
        if (id == null || id.isBlank()) {
            return WAIT;
        }
        if (id.equals("0")) {
            return START;
        }
        if (checkPingToServer()) {
            return WAIT;
        }
        String url = this.properties.getProperty(RUNNABLE);
        if (url == null) {
            ErrorLog.addError(this, "không tìm thấy: checkUserId url");
            return URL_INVALID;
        }
        Response response = restAPI.sendPost(url, JsonBodyAPI.builder()
                .put(ID, id), false);
        if (!response.isResponseAvalid()) {
            ErrorLog.addError(this, response.getResponse());
            return API_INVALID;
        }
        JSONObject data = response.getData();
        return data.getIntValue(CAN_START, -1);
    }
}
