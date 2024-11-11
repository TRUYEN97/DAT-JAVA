/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.qt.common.API.Response;
import com.qt.common.CarConfig;
import com.qt.common.ErrorLog;
import com.qt.controller.ErrorcodeHandle;
import com.qt.controller.ProcessModelHandle;
import com.qt.controller.logTest.FileTestService;
import com.qt.input.camera.CameraRunner;
import com.qt.main.Core;
import com.qt.model.modelTest.process.ProcessModel;
import java.io.File;
import java.nio.file.Files;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class AnalysisApiCommand implements ICommandAPIReceive<Response> {

    private static final String UPDATED_ERROR_KEY = "updatedErrorKey";
    private static final String ERROR_KEY = "errorKey";
    private static final String UPDATE = "update";
    private static final String USER_ID = "userId";
    private final CarConfig carConfig;
    private final FileTestService fileTestService;
    private final ProcessModelHandle modelHandle;
    private final ProcessModel processModel;
    private final ErrorcodeHandle errorcodeHandle;
    private final ApiService apiService;

    public AnalysisApiCommand() {
        this.carConfig = CarConfig.getInstance();
        this.fileTestService = FileTestService.getInstance();
        this.modelHandle = ProcessModelHandle.getInstance();
        this.processModel = this.modelHandle.getProcessModel();
        this.errorcodeHandle = ErrorcodeHandle.getInstance();
        this.apiService = new ApiService();
    }

    @Override
    public void receive(Response responce) {
        try {
            if (responce == null) {
                return;
            }
            if (!responce.isSuccess() || responce.getData() == null) {
                return;
            }
            Object data = responce.getData();
            if (data instanceof JSONObject jsono) {
                checkCommand(jsono);
            } else if (data instanceof JSONArray array) {
                for (Object object : array) {
                    if (object instanceof JSONObject jsono) {
                        checkCommand(jsono);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    private void checkCommand(JSONObject jsono) {
        String exId = jsono.getString(ApiService.EXAM_ID);
        JSONArray arrayNeedUpdate = jsono.getJSONArray(UPDATE);
        if (exId == null || arrayNeedUpdate == null) {
            return;
        }
        for (Object object : arrayNeedUpdate) {
            if (object instanceof JSONObject json) {
                checkCommandWithCurrentExamId(exId, json);
            }
        }
    }

    private void checkCommandWithCurrentExamId(String examId, JSONObject json) {
        String id = json.getString(USER_ID);
        if (id == null || examId == null || Core.getInstance().getModeManagement().isOffLineMode()) {
            return;
        }
        String errKey = json.getString(ERROR_KEY);
        if (this.carConfig.getExamId() != null
                && examId.equals(this.carConfig.getExamId())
                && this.processModel.getId() != null
                && id.equals(this.processModel.getId())) {
            if (errKey != null && !errKey.isBlank()) {
                if (this.modelHandle.isTesting()) {
                    this.errorcodeHandle.addBaseErrorCode(errKey, new JSONObject(Map.of(UPDATED_ERROR_KEY, errKey)));
                }
            } else {
                JSONObject testData = this.modelHandle.toProcessModelJson();
                testData.put(UPDATED_ERROR_KEY, "");
                this.apiService.sendData(CameraRunner.getInstance().getImage(), testData);
            }
        } else {
            try {
                File jsonFile = this.fileTestService.getFileJsonPath(examId, id);
                if (jsonFile == null || !jsonFile.exists()) {
                    return;
                }
                JSONObject testData = JSONObject.parseObject(Files.readString(jsonFile.toPath()));
                testData.put(UPDATED_ERROR_KEY, "");
                this.apiService.sendData(testData, null);
            } catch (Exception e) {
                e.printStackTrace();
                ErrorLog.addError(this, e);
            }
        }
    }

}
