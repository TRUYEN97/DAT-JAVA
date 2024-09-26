/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.input.socket;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.CarConfig;
import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.common.communication.socket.Unicast.Client.SocketClient;
import com.qt.common.communication.socket.Unicast.commons.Interface.IReceiver;
import com.qt.model.input.yard.RoadZ;
import com.qt.model.input.yard.YardModel;
import com.qt.model.input.yard.YardRankModel;

/**
 *
 * @author Admin
 */
public class YardModelHandle {

    private static volatile YardModelHandle instance;
    private static final String RANK_B = "a";
    private static final String RANK_C = "b";
    private static final String RANK_D = "c";
    private static final String RANK_E = "e";
    private static final String ROAD_Z = "roadZ";
    private static final String WHEEL_PATH = "wheelPath";
    private static final String WHEEL_CROSSIDE_LINE = "WheelCrossideLine";
    private static final String PACKING1 = "packing1";
    private static final String PACKING = "packing";
    private static final String ROAD_S = "roadS";
    private final SocketClient socketClient;
    private final CarConfig carConfig;
    private final YardModel yardModel;
    private Thread thread;

    private YardModelHandle() {
        this.carConfig = CarConfig.getInstance();
        IReceiver<SocketClient> receiver = (SocketClient commnunication, String data) -> {
            createReciver(data);
        };
        this.socketClient = new SocketClient(this.carConfig.getYardIp(),
                this.carConfig.getYardPort(), receiver);
        this.yardModel = new YardModel();
    }

    public YardModel getYardModel() {
        return yardModel;
    }
    

    public static YardModelHandle getInstance() {
        YardModelHandle ins = instance;
        if (ins == null) {
            synchronized (YardModelHandle.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new YardModelHandle();
                }
            }
        }
        return ins;
    }

    private void createReciver(String data) {
        if (data == null || data.isBlank()) {
            return;
        }
        try {
            JSONObject ob = JSONObject.parseObject(data);
            if (ob.containsKey(RANK_B)) {
                update(ob.getJSONObject(RANK_B), this.yardModel.getRankB());
            }
            if (ob.containsKey(RANK_C)) {
                update(ob.getJSONObject(RANK_C), this.yardModel.getRankC());
            }
            if (ob.containsKey(RANK_D)) {
                update(ob.getJSONObject(RANK_D), this.yardModel.getRankD());
            }
            if (ob.containsKey(RANK_E)) {
                update(ob.getJSONObject(RANK_E), this.yardModel.getRankE());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    private void update(JSONObject jsonObject, YardRankModel yardRankModel) {
        if (jsonObject == null || yardRankModel == null) {
            return;
        }
        RoadZ roadZModel = yardRankModel.getRoadZ();
        if (roadZModel != null && jsonObject.containsKey(ROAD_Z)) {
            JSONObject roadZJson = jsonObject.getJSONObject(ROAD_Z);
            roadZModel.setWheelPath(roadZJson.getBooleanValue(WHEEL_PATH, false));
            roadZModel.setWheelCrossideLine(roadZJson.getBooleanValue(WHEEL_CROSSIDE_LINE, false));
        }
        yardRankModel.setRoadS(jsonObject.getBooleanValue(ROAD_S, false));
        yardRankModel.setPacking(jsonObject.getBooleanValue(PACKING, false));
        yardRankModel.setPacking1(jsonObject.getBooleanValue(PACKING1, false));
    }
    private boolean stop = false;

    public void start() {
        try {
            if (thread != null && thread.isAlive()) {
                return;
            }
            stop = false;
            this.thread = new Thread(() -> {
                while (!stop) {
                    System.out.println("YartStart");
                    while (!this.socketClient.connect() && !stop) {
                        Util.delay(1000);
                    }
                    if (!stop) {
                        this.socketClient.run();
                    }
                }
            });
            this.thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    public void stop() {
        try {
            stop = true;
            this.socketClient.disconnect();
            if (thread == null || !thread.isAlive()) {
                return;
            }
            while (thread.isAlive()) {
                thread.stop();
                if (!thread.isAlive()) {
                    break;
                }
                Util.delay(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

}
