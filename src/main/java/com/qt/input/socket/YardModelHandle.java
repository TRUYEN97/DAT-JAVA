/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.input.socket;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.qt.common.CarConfig;
import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.common.YardConfig;
import com.qt.common.communication.socket.Unicast.Client.SocketClient;
import com.qt.common.communication.socket.Unicast.commons.Interface.IReceiver;
import com.qt.model.input.yard.YardModel;
import com.qt.model.input.yard.YardRankModel;
import com.qt.model.yardConfigMode.ContestConfig;
import com.qt.model.yardConfigMode.YardConfigModel;
import com.qt.model.yardConfigMode.YardRankConfig;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class YardModelHandle {

    private static volatile YardModelHandle instance;
    private static final String INPUTS = "inputs";
    private static final String TRAFFIC_LIGHT_MODEL = "trafficLightModel";
    private static final String TRAFFIC_LIGHT_MODEL1 = "trafficLightModel1";
    private static final String TRAFFIC_LIGHT = "trafficLight";
    private static final String TIME = "time";
    private final SocketClient socketClient;
    private final CarConfig carConfig;
    private final YardConfigModel yardConfig;
    private final YardModel yardModel;
    private Thread thread;

    private YardModelHandle() {
        this.carConfig = CarConfig.getInstance();
        this.yardConfig = YardConfig.getInstance().getYardConfigModel();
        IReceiver<SocketClient> receiver = (SocketClient commnunication, String data) -> {
            analysisReciver(data);
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

    private void analysisReciver(String data) {
        if (data == null || data.isBlank()) {
            return;
        }
        try {
            System.out.println(data);
            JSONObject ob = JSONObject.parseObject(data);
            if (ob.containsKey(INPUTS)) {
                JSONArray inputs = ob.getJSONArray(INPUTS);
                updateRank(this.yardConfig.getB(), inputs, this.yardModel.getRankB());
                updateRank(this.yardConfig.getC(), inputs, this.yardModel.getRankC());
                updateRank(this.yardConfig.getD(), inputs, this.yardModel.getRankD());
                updateRank(this.yardConfig.getE(), inputs, this.yardModel.getRankE());
            }
            if (ob.containsKey(TRAFFIC_LIGHT_MODEL)) {
                JSONObject tl = ob.getJSONObject(TRAFFIC_LIGHT_MODEL);
                if (tl != null && this.yardModel.getTrafficLightModel() != null) {
                    this.yardModel.getTrafficLightModel()
                            .setTrafficLight(tl.getIntValue(TRAFFIC_LIGHT, 0));
                    this.yardModel.getTrafficLightModel()
                            .setTime(tl.getIntValue(TIME, 0));
                }
            }
            if (ob.containsKey(TRAFFIC_LIGHT_MODEL1)) {
                JSONObject tl = ob.getJSONObject(TRAFFIC_LIGHT_MODEL1);
                if (tl != null && this.yardModel.getTrafficLightModel1() != null) {
                    this.yardModel.getTrafficLightModel1()
                            .setTrafficLight(tl.getIntValue(TRAFFIC_LIGHT, 0));
                    this.yardModel.getTrafficLightModel1()
                            .setTime(tl.getIntValue(TIME, 0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    private void updateRank(YardRankConfig rankConfig, JSONArray inputs, YardRankModel rankModel) {
        updateContest(rankConfig.getDoXeDoc(), inputs, rankModel.getPackings());

        updateContest(rankConfig.getDoXeNgang(), inputs, rankModel.getPackings1());

        updateContest(rankConfig.getDuongS(), inputs, rankModel.getRoadSs());

        updateContest(rankConfig.getVetBanhXe(), inputs, rankModel.getRoadZs());

        updateContest(rankConfig.getDuongVuongGoc(), inputs, rankModel.getRoadZs1());
    }

    private void updateContest(List<ContestConfig> contestConfigs, JSONArray inputs, List<Boolean> contestInputs) {
        try {
            if (contestConfigs == null || inputs == null || contestInputs == null) {
                return;
            }
            Integer indexOfYardInput;
            ContestConfig contestConfig;
            boolean val;
            for (int i = 0; i < contestConfigs.size(); i++) {
                val = false;
                if ((contestConfig = contestConfigs.get(i)) != null
                        && (indexOfYardInput = contestConfig.getIndexOfYardInput()) != null) {
                    if (indexOfYardInput < inputs.size()) {
                        Boolean t = inputs.getBoolean(indexOfYardInput);
                        val = t == null ? false : t;
                    }
                }
                if (contestInputs.size() <= i) {
                    contestInputs.add(val);
                } else {
                    contestInputs.set(i, val);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
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
                    System.out.println("YardStart");
                    Util.delay(1000);
                    while (!this.socketClient.connect() && !stop) {
                        Util.delay(2000);
                    }
                    if (!stop) {
                        while (!sendApplyConnect()) {
                            Util.delay(1000);
                        }
                        System.out.println("Yard connected");
                        this.socketClient.run();
                        System.out.println("Yard disconnected");
                    }
                }
            });
            this.thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    private boolean sendApplyConnect() {
        return this.socketClient.send(
                new JSONObject(
                        Map.of("username", this.carConfig.getYardUser(),
                                "password", this.carConfig.getYardPassword()))
                        .toString());
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

    public boolean isConnect() {
        return this.socketClient.isConnect();
    }

}
