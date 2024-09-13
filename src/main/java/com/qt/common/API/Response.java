/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common.API;

import com.alibaba.fastjson2.JSONObject;
import com.qt.view.component.ShowMessagePanel;
import java.util.List;

/**
 *
 * @author Administrator
 */
public final class Response {

    private final int code;
    private final String response;
    private JSONObject wareHouse;
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    public static final String STATUS = "status";

    public Response(int code, String response, ShowMessagePanel textComponent) {
        this.code = code;
        this.response = response;
        if (response == null || response.isBlank()) {
            this.wareHouse = new JSONObject();
            return;
        }
        try {
            this.wareHouse = JSONObject.parseObject(response);
            if (textComponent != null) {
                textComponent.showMess(getMessage());
            }
        } catch (Exception e) {
        }
    }

    public String getMessage() {
        if (code == 403) {
            return String.format("Access permissions insufficient to access");
        }
        if (code == 404 || code == -1 || this.wareHouse == null) {
            return this.response;
        }
        if (!isResponseAvalid()) {
            return null;
        }
        String val = this.wareHouse.getString(MESSAGE);
        return val == null ? response : val;
    }

    public boolean isResponseAvalid() {
        return this.wareHouse != null;
    }

    public <T> T getData(Class<T> clazz) {
        if (!isResponseAvalid()) {
            return null;
        }
        return this.wareHouse.getObject(DATA, clazz);
    }

    public <T> T getData() {
        if (!isResponseAvalid()) {
            return null;
        }
        Object value = this.wareHouse.get(DATA);
        if (value == null) {
            return null;
        }
        return (T) value;
    }

    public <T> List<T> getDatas(Class<T> clazz) {
        if (!isResponseAvalid()) {
            return null;
        }
        return this.wareHouse.getList(DATA, clazz);
    }

    public String getResponse() {
        return response;
    }

    public boolean isSuccess() {
        return code == 200
                && isResponseAvalid()
                && this.wareHouse.getBooleanValue(STATUS, false);
    }

    public boolean getStringEquals(String key, String target) {
        try {
            String value = this.wareHouse.getString(key);
            return value != null && value.equals(target);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean getStringEqualsIgnoreCase(String key, String target) {
        try {
            String value = this.wareHouse.getString(key);
            return value != null && value.equalsIgnoreCase(target);
        } catch (Exception e) {
            return false;
        }
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return getResponse();
    }

}
