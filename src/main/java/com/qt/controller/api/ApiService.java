/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.api;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.API.FileInfo;
import com.qt.common.API.JsonBodyAPI;
import com.qt.common.API.Response;
import com.qt.common.API.RestAPI;
import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.common.timer.TimeBase;
import com.qt.controller.modeController.ModeManagement;
import com.qt.model.input.UserModel;
import com.qt.output.SoundPlayer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
        try {
            String url = this.properties.getProperty(ConstKey.CHECK_CAR_ID);
            if (checkPingToServer()) {
                return false;
            }
            if (url == null) {
                ErrorLog.addError(this, "không tìm thấy: checkCarId url");
                return false;
            }
            Response response = restAPI.sendPost(url, JsonBodyAPI.builder().put(ID, id));
            return response != null && response.isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    public UserModel checkUserId(String id) {
        try {
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
            String url = this.properties.getProperty(ConstKey.CHECK_USER_ID);
            if (url == null) {
                ErrorLog.addError(this, "không tìm thấy: checkUserId url");
                return null;
            }
            Response response = restAPI.sendPost(url, JsonBodyAPI.builder().put(ID, id));
            if (!response.isSuccess()) {
                return null;
            }
            return response.getData(UserModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return null;
        }
    }

    public boolean sendData(JSONObject jSONObject, File imgFile) {
        try {
            if (checkPingToServer()) {
                return false;
            }
            String url = this.properties.getProperty(ConstKey.SEND_DATA);
            if (url == null) {
                ErrorLog.addError(this, "không tìm thấy: sendData url");
                return false;
            }
            if (jSONObject == null || jSONObject.isEmpty()) {
                return false;
            }
            jSONObject.put(ConstKey.VEHICLE_TIME, new TimeBase().getSimpleDateTime());
            FileInfo jsonF = new FileInfo(FileInfo.type.BYTE);
            jsonF.setFile(jSONObject.toString().getBytes());
            jsonF.setPartName("data");
            jsonF.setName("data.json");
            //////////////////
            FileInfo imgF = new FileInfo(FileInfo.type.FILE);
            imgF.setFile(imgFile);
            imgF.setPartName("image");
            imgF.setName("image.png");
            Response response = restAPI.uploadFile(url, null, jsonF, imgF);
            return response != null && response.isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    public boolean sendData(File jsonFile, File imgFile) {
        try {
            if (jsonFile == null || !jsonFile.exists()) {
                return false;
            }
            return sendData(JSONObject.parseObject(Files.readString(jsonFile.toPath())), imgFile);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    public boolean pingToServer() {
        String addr = this.properties.getProperty(ConstKey.SERVER_PING_ADDR);
        return Util.ping(addr, 2);
    }

    private boolean checkPingToServer() {
        if (!pingToServer()) {
            ErrorLog.addError(this, "không thể ping đến server");
            this.soundPlayer.pingServerFailed();
            return true;
        }
        return false;
    }

    public int checkRunnable(String id) {
        try {
            if (id == null || id.isBlank()) {
                return WAIT;
            }
            if (id.equals("0")) {
                return START;
            }
            if (checkPingToServer()) {
                return WAIT;
            }
            String url = this.properties.getProperty(ConstKey.RUNNABLE);
            if (url == null) {
                ErrorLog.addError(this, "không tìm thấy: runnable url");
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
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return WAIT;
        }
    }

    public Response checkInfo() {
        try {
            if (pingToServer()) {
                return null;
            }
            String url = this.properties.getProperty(ConstKey.CHECK_INFO);
            if (url == null) {
                ErrorLog.addError(this, "không tìm thấy: checkInfo url");
                return null;
            }
            return restAPI.sendPost(url, JsonBodyAPI.builder(), false);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return null;
        }
    }
}
