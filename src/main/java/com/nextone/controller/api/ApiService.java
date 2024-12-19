/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.api;

import com.alibaba.fastjson2.JSONObject;
import com.nextone.common.API.FileInfo;
import com.nextone.common.API.JsonBodyAPI;
import com.nextone.common.API.Response;
import com.nextone.common.API.RestAPI;
import com.nextone.common.ConstKey;
import com.nextone.common.ErrorLog;
import com.nextone.common.MyObjectMapper;
import com.nextone.common.Setting;
import com.nextone.common.Util;
import com.nextone.common.timer.TimeBase;
import com.nextone.model.input.UserModel;
import com.nextone.model.modelTest.process.ProcessModel;
import com.nextone.view.component.ShowMessagePanel;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;

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
    public static final int PASS = 1;
    public static final int FAIL = 0;
    public static final int DISCONNECT = -1;
    public static final String ID = "id";
    public static final String EXAM_ID = "examId";
//    private static volatile ApiService insatnce;
    private final Setting setting;
    private final RestAPI restAPI;

    public ApiService() {
        this.setting = Setting.getInstance();
        this.restAPI = new RestAPI();
        this.restAPI.setTextComponent(ShowMessagePanel.getInstance());
    }

//    public static ApiService getInstance() {
//        ApiService ins = insatnce;
//        if (ins == null) {
//            synchronized (ApiService.class) {
//                ins = insatnce;
//                if (ins == null) {
//                    insatnce = ins = new ApiService();
//                }
//            }
//        }
//        return ins;
//    }
    public boolean checkCarId(String id) {
        try {
            String url = this.setting.getCheckCarIdUrl();
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

    public UserModel checkUserId(String id, String carID) {
        try {
            if (id == null || id.isBlank() || "0".equals(id)) {
                UserModel userModel = new UserModel();
                userModel.setId("0");
                userModel.setExamId("0");
                return userModel;
            }
            String url = this.setting.getCheckUserIdUrl();
            if (url == null) {
                ErrorLog.addError(this, "không tìm thấy: checkUserId url");
                return null;
            }
            Response response = restAPI.sendPost(url, JsonBodyAPI.builder()
                    .put(ID, id).put(CAR_ID, carID));
            if (response == null || !response.isSuccess()) {
                return null;
            }
            return response.getData(UserModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return null;
        }
    }
    protected static final String CAR_ID = "carId";

    public int sendData(BufferedImage image, JSONObject jSONObject) {
        try {
            String url = this.setting.getSendDataUrl();
            if (url == null) {
                ErrorLog.addError(this, "không tìm thấy: sendData url");
                return FAIL;
            }
            if (jSONObject == null || jSONObject.isEmpty()) {
                return FAIL;
            }
            jSONObject.put(ConstKey.VEHICLE_TIME, new TimeBase().getSimpleDateTime());
            FileInfo jsonF = new FileInfo(FileInfo.type.BYTE);
            jsonF.setFile(jSONObject.toString().getBytes());
            jsonF.setPartName("data");
            jsonF.setName("data.json");
            //////////////////
            FileInfo imgF = new FileInfo(FileInfo.type.BYTE);
            imgF.setFile(Util.convertBufferImageToBytes(image));
            imgF.setPartName("image");
            imgF.setName("image.png");
            restAPI.getLogger().addLog("jsonData", jSONObject);
            Response response = restAPI.uploadFile(url, null, jsonF, imgF);
            if (response == null || response.getCode() == -1) {
                return DISCONNECT;
            } else if (response.isSuccess()) {
                return PASS;
            } else {
                String mess = response.getMessage();
                return mess != null && mess.contains("The contestant has completed") ? PASS : FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return FAIL;
        }
    }

    public int sendData(JSONObject jSONObject, File imgFile) {
        try {
            String url = this.setting.getSendDataUrl();
            if (url == null) {
                ErrorLog.addError(this, "không tìm thấy: sendData url");
                return FAIL;
            }
            if (jSONObject == null || jSONObject.isEmpty()) {
                return FAIL;
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
            restAPI.getLogger().addLog("jsonData", jSONObject);
            Response response = restAPI.uploadFile(url, null, jsonF, imgF);
            if (response == null || response.getCode() == -1) {
                return DISCONNECT;
            } else if (response.isSuccess()) {
                return PASS;
            } else {
                String mess = response.getMessage();
                return mess != null && mess.contains("The contestant has completed") ? PASS : FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return FAIL;
        }
    }

    public int sendData(ProcessModel processModel, File imgFile) {
        try {
            if (processModel == null) {
                return FAIL;
            }
            String id = processModel.getId();
            if (id == null || id.equals("0")) {
                return ApiService.PASS;
            }
            return sendData(MyObjectMapper.convertValue(processModel, JSONObject.class),
                    imgFile);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return FAIL;
        }
    }

    public int sendData(File jsonFile, File imgFile) {
        try {
            if (jsonFile == null || !jsonFile.exists()) {
                return FAIL;
            }
            return sendData(JSONObject.parseObject(Files.readString(jsonFile.toPath())), imgFile);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return FAIL;
        }
    }

    public int sendData(File jsonFile, BufferedImage image) {
        try {
            if (jsonFile == null || !jsonFile.exists()) {
                return FAIL;
            }
            return sendData(image, JSONObject.parseObject(Files.readString(jsonFile.toPath())));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return FAIL;
        }
    }

//    public boolean pingToServer() {
//        String addr = this.setting.getServerPingIp();
//        return Util.ping(addr, 2);
//    }
//
//    private boolean checkPingToServer() {
//        if (!pingToServer()) {
//            ErrorLog.addError(this, "không thể ping đến server");
////            this.soundPlayer.pingServerFailed();
//            return true;
//        }
//        return false;
//    }
    public int checkRunnable(String id) {
        try {
            if (id == null || id.isBlank()) {
                return WAIT;
            }
            if (id.equals("0")) {
                return START;
            }
            String url = this.setting.getCheckRunnableUrl();
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
            Integer data = response.getData();
            if (data != null) {
                return data;
            }
            return ID_INVALID;
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return WAIT;
        }
    }

    public Response checkCommad(String carId) {
        try {
            if (carId == null || carId.isBlank() || carId.equals("0")) {
                return null;
            }
            String ipServer = this.setting.getServerPingIp();
            if (ipServer != null && !ipServer.isBlank() && !Util.ping(ipServer, 1)) {
                return null;
            }
            String url = this.setting.getCheckCommandUrl();
            if (url == null) {
                ErrorLog.addError(this, "không tìm thấy: checkCommand url");
                return null;
            }
            return restAPI.sendPost(url, JsonBodyAPI.builder().put(CAR_ID, carId), true);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return null;
        }
    }

    public void setRootFrame(Component component) {
        this.restAPI.setComponent(component);
    }

    public void sendCancelRequest(String id, String examId) {
        if (id == null || id.isBlank() || examId == null || examId.isBlank()) {
            return;
        }
        String url = this.setting.getCancelRequestUrl();
        if (url == null) {
            ErrorLog.addError(this, "không tìm thấy: checkInfo url");
            return;
        }
        restAPI.sendPost(url, JsonBodyAPI.builder()
                .put(ID, id)
                .put(EXAM_ID, examId), true);
    }

    public UserModel checkCarPair(String carId) {
        try {
            String url = this.setting.getcheckCarPairUrl();
            if (url == null) {
                ErrorLog.addError(this, "không tìm thấy: checkCarPair url");
                return null;
            }
            JsonBodyAPI bodyAPI = JsonBodyAPI.builder().put(ID, carId);
            Response response = restAPI.sendPost(url, bodyAPI, false);
            if (response == null || !response.isSuccess()) {
                return null;
            }
            restAPI.getLogger().addLog("POST", bodyAPI.toJSONString());
            restAPI.getLogger().addLog("RESPONSE", "%s - %s", response.getCode(), response.getResponse());
            return response.getData(UserModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return null;
        }
    }
}
