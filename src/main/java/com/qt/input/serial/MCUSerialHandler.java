
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.input.serial;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.CarConfig;
import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.MyObjectMapper;
import com.qt.common.Util;
import com.qt.controller.ProcessModelHandle;
import com.qt.model.config.MCU_CONFIG_MODEL;
import com.qt.model.input.CarModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.output.SoundPlayer;

/**
 *
 * @author Admin
 */
public class MCUSerialHandler {

    private static volatile MCUSerialHandler instance;
    private final SerialHandler serialHandler;
    private final GearBoxer gearBoxer;
    private CarModel model;
    private Thread threadRunner;
    private boolean sendorStartEnable = false;
    private boolean sendorEndEnable = false;
    private Thread startSoundThread;
    private Thread endSoundThread;

    private MCUSerialHandler() {
        this.model = new CarModel();
        this.gearBoxer = new GearBoxer();
        this.serialHandler = new SerialHandler("ttyS0", 115200); //ttyS0
        this.serialHandler.setFirstConnectAction(() -> {
            System.out.println("send MCU config");
            while (!sendConfig(CarConfig.getInstance().getMcuConfig())) {
                Util.delay(500);
            }
            System.out.println("send MCU config ok");

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
                ProcessModelHandle modelHandle = ProcessModelHandle.getInstance();
                boolean t1 = json.getBooleanValue(ConstKey.CAR_MODEL_KEY.T1, false);
                this.model.setT1(t1);
                boolean t2 = json.getBooleanValue(ConstKey.CAR_MODEL_KEY.T2, false);
                this.model.setT2(t2);
                boolean t3 = json.getBooleanValue(ConstKey.CAR_MODEL_KEY.T3, false);
                this.model.setT3(t3);
                if (!modelHandle.isTesting()) {
                    SoundPlayer soundPlayer = SoundPlayer.getInstance();
                    if (t1 || t2) {
                        if (!this.sendorStartEnable) {
                            this.model.setDistance(0);
                            this.sendorStartEnable = true;
                            if (this.startSoundThread == null || !this.startSoundThread.isAlive()) {
                                this.startSoundThread = new Thread(() -> {
                                    soundPlayer.startContest();
                                });
                                this.startSoundThread.start();
                            }
                        }
                    } else {
                        this.sendorStartEnable = false;
                    }
                    if (t3) {
                        if (!this.sendorEndEnable) {
                            this.sendorEndEnable = true;
                            if (this.endSoundThread == null || !this.endSoundThread.isAlive()) {
                                this.endSoundThread = new Thread(() -> {
                                    soundPlayer.endContest();
                                });
                                this.endSoundThread.start();
                            }
                        }
                    } else {
                        this.sendorEndEnable = false;
                    }
                }
                this.model.setStatus(json.getIntValue(ConstKey.CAR_MODEL_KEY.STATUS, 0));
                this.model.setDistance(this.model.getDistance()
                        + json.getDoubleValue(ConstKey.CAR_MODEL_KEY.DISTANCE));
                this.model.setSpeed(json.getFloatValue(ConstKey.CAR_MODEL_KEY.SPEED));
                this.model.setRpm(json.getIntValue(ConstKey.CAR_MODEL_KEY.RPM, 0));
                double lat = json.getDoubleValue(ConstKey.CAR_MODEL_KEY.LATITUDE);
                double lng = json.getDoubleValue(ConstKey.CAR_MODEL_KEY.LONGITUDE);
                if (lng != 0 && lat != 0) {
                    ProcessModel processModel = modelHandle.getProcessModel();
                    processModel.getLocation().setLat(lat);
                    processModel.getLocation().setLng(lng);
                }
                this.gearBoxer.mathGearBoxValue();
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

    public void sendLedRedOff() {
        this.serialHandler.send("r2off");
    }

    public void sendLedGreenOff() {
        this.serialHandler.send("r1off");
    }

    public void sendLedYellowOff() {
        this.serialHandler.send("r3off");
    }

    public void sendLedGreenOn() {
        this.serialHandler.send("r1");
    }

    public boolean sendLedRedOn() {
        return this.serialHandler.send("r2");
    }

    public boolean sendLedYellowOn() {
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

    public boolean sendConfig(String config) {
        try {
            if (config == null || config.isBlank()) {
                return false;
            }
            return this.serialHandler.send(config);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorLog.addError(this, ex);
            return false;
        }
    }

    public boolean sendConfig(MCU_CONFIG_MODEL config) {
        try {
            if (config == null) {
                return false;
            }
            String configString = MyObjectMapper.writeValueAsString(config);
            return this.serialHandler.send(configString);
        } catch (Exception ex) {
            ErrorLog.addError(this, ex);
            return false;
        }
    }

    class GearBoxer {

        public void mathGearBoxValue() {
            model.setGearBoxValue(Util.getGearBoxVal(model.isS1(), model.isS2(),
                    model.isS3(), model.isS4()));
        }
    }
}
