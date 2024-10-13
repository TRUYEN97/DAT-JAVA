/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common;

import com.qt.controller.api.ApiService;

/**
 *
 * @author Admin
 */
public class TestStatusLogger {

    private static volatile TestStatusLogger instance;
    private final ApiService apiService;
    private final CarConfig carConfig;

    private TestStatusLogger() {
        this.carConfig = CarConfig.getInstance();
        this.apiService = new ApiService();
    }

    public static TestStatusLogger getInstance() {
        TestStatusLogger ins = instance;
        if (ins == null) {
            synchronized (TestStatusLogger.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new TestStatusLogger();
                }
            }
        }
        return ins;
    }

    public void check() {
        String data = this.carConfig.getTestStatusValue();
        if (data == null || data.isBlank()) {
            return;
        }
        String[] elems = data.trim().split(",");
        if (elems.length < 2) {
            return;
        }
        this.apiService.sendCancelRequest(elems[0].trim(), elems[1].trim());
    }

    public void setTestStatus(String id, String examId) {
        if (id == null || id.isBlank() || id.equalsIgnoreCase("0") || examId == null || examId.isBlank()) {
            this.carConfig.removeTestStatusValue();
        } else {
            this.carConfig.setCurrentTestStatusValue(id, examId);
        }
    }

    public void remove() {
        this.carConfig.removeTestStatusValue();
    }

}
