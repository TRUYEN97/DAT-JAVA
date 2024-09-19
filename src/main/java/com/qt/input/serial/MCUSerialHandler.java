
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.input.serial;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qt.common.CarConfig;
import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.MyObjectMapper;
import com.qt.common.Util;
import com.qt.model.config.MCU_CONFIG_MODEL;
import com.qt.model.input.CarModel;

/**
 *
 * @author Admin
 */
public class MCUSerialHandler {

    private static volatile MCUSerialHandler instance;
    private final SerialHandler serialHandler;
    private CarModel model;
    private Thread threadRunner;

    private MCUSerialHandler() {
        this.model = new CarModel();
        this.serialHandler = new SerialHandler("ttyS0", 115200); //ttyS0
        this.serialHandler.setFirstConnectAction(() -> {
            System.out.println("send MCU config");
            while (!sendConfig(CarConfig.getInstance().getMcuConfig())) {
                Util.delay(500);
            }
            System.out.println("send MCU reset ok");
            
        });
        this.serialHandler.setReceiver((serial, data) -> {
            try {
                System.out.println(data);
                JSONObject json = JSONObject.parseObject(data);
                this.model.setAt(!json.getBooleanValue(ConstKey.CAR_MODEL_KEY.AT, false));
                this.model.setCm(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.CM, false));
                this.model.setNp(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.NP, false));
                this.model.setNt(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.NT, false));
                this.model.setPt(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.PT, false));
                this.model.setS1(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.S1, false));
                this.model.setS2(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.S2, false));
                this.model.setS3(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.S3, false));
                this.model.setS4(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.S4, false));
                this.model.setS5(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.S5, false));
                this.model.setT1(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.T1, false));
                this.model.setT2(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.T2, false));
                this.model.setT3(json.getBooleanValue(ConstKey.CAR_MODEL_KEY.T3, false));
                this.model.setStatus(json.getIntValue(ConstKey.CAR_MODEL_KEY.STATUS, 0));
                this.model.setDistance(json.getDoubleValue(ConstKey.CAR_MODEL_KEY.DISTANCE));
                this.model.setSpeed(json.getFloatValue(ConstKey.CAR_MODEL_KEY.SPEED));
                this.model.setRpm(json.getIntValue(ConstKey.CAR_MODEL_KEY.RPM, 0));
                this.model.mathGearBoxValue();
            } catch (Exception e) {
                e.printStackTrace();
                ErrorLog.addError(this, e);
            }
        });
    }

    public boolean isConnect() {
        return this.serialHandler.isConnect();
    }

    public void setModel(CarModel model) {
        if (model == null) {
            return;
        }
        this.model = model;
    }

    public CarModel getModel() {
        return model;
    }

    public static MCUSerialHandler getInstance() {
        MCUSerialHandler ins = MCUSerialHandler.instance;
        if (ins == null) {
            synchronized (MCUSerialHandler.class) {
                ins = MCUSerialHandler.instance;
                if (ins == null) {
                    MCUSerialHandler.instance = ins = new MCUSerialHandler();
                }
            }
        }
        return ins;
    }

    public void sendLedOff() {
        this.serialHandler.send("roff");
    }

    public void sendLedGreenOn() {
        this.serialHandler.send("roff");
        this.serialHandler.send("r1");
    }

    public boolean sendLedRedOn() {
        this.serialHandler.send("roff");
       return this.serialHandler.send("r2");
    }

    public boolean sendLedYellowOn() {
        this.serialHandler.send("roff");
        return this.serialHandler.send("r3");
    }

    public boolean sendReset() {
        return this.serialHandler.send("reset");
    }

    public void sendUpdate() {
        this.serialHandler.send("get");
    }

    public boolean sendCommand(String command, Object... params) {
        return this.serialHandler.send(command, params);
    }

    public void start() {
        if (threadRunner != null && threadRunner.isAlive()) {
            return;
        }
        threadRunner = new Thread(this.serialHandler);
        threadRunner.start();
    }

    public boolean sendConfig(MCU_CONFIG_MODEL config) {
        try {
            String configString = MyObjectMapper.writeValueAsString(config);
            return this.serialHandler.send(configString);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
