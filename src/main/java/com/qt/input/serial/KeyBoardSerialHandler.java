
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.input.serial;

import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.model.input.CarModel;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class KeyBoardSerialHandler {

    private static volatile KeyBoardSerialHandler instance;
    private final SerialHandler serialHandler;
    private CarModel model;
    private Thread threadRunner;
    private final Map<String, String> keyMap;

    private KeyBoardSerialHandler() {
        this.model = new CarModel();
        this.keyMap = new HashMap<>();
        this.serialHandler = new SerialHandler("ttyACM0", 115200); //ttyACM0
        this.serialHandler.setReceiver((serial, data) -> {
            try {
                if (data == null || !this.keyMap.containsKey(data)) {
                    return;
                }
                this.model.setRemoteValue(this.keyMap.get(data));
            } catch (Exception e) {
                e.printStackTrace();
                ErrorLog.addError(this, e);
            }
        });
        this.keyMap.put("A1", ConstKey.KEY_BOARD.CONTEST.XP);
        this.keyMap.put("B1", ConstKey.KEY_BOARD.CONTEST.TS);
        this.keyMap.put("C1", ConstKey.KEY_BOARD.CONTEST.GS);
        this.keyMap.put("D1", ConstKey.KEY_BOARD.CONTEST.KT);
        this.keyMap.put("E1", ConstKey.KEY_BOARD.IN);
        
        this.keyMap.put("A2", ConstKey.KEY_BOARD.SO_XE);
        this.keyMap.put("B2", ConstKey.KEY_BOARD.SBD);
        this.keyMap.put("C2", ConstKey.KEY_BOARD.CANCEL);
        this.keyMap.put("D2", ConstKey.KEY_BOARD.BACKSPACE);
        this.keyMap.put("E2", ConstKey.KEY_BOARD.OK);
        
        this.keyMap.put("A3", ConstKey.KEY_BOARD.VK_0);
        this.keyMap.put("B3", ConstKey.KEY_BOARD.VK_1);
        this.keyMap.put("C3", ConstKey.KEY_BOARD.VK_2);
        this.keyMap.put("D3", ConstKey.KEY_BOARD.VK_3);
        this.keyMap.put("E3", ConstKey.KEY_BOARD.VK_4);
        
        this.keyMap.put("A4", ConstKey.KEY_BOARD.VK_5);
        this.keyMap.put("B4", ConstKey.KEY_BOARD.VK_6);
        this.keyMap.put("C4", ConstKey.KEY_BOARD.VK_7);
        this.keyMap.put("D4", ConstKey.KEY_BOARD.VK_8);
        this.keyMap.put("E4", ConstKey.KEY_BOARD.VK_9);
        
        this.keyMap.put("A5", ConstKey.KEY_BOARD.ERROR.CL);
        this.keyMap.put("B5", ConstKey.KEY_BOARD.ERROR.HL);
        this.keyMap.put("C5", ConstKey.KEY_BOARD.ERROR.QT);
        this.keyMap.put("D5", ConstKey.KEY_BOARD.ERROR.RG);
        this.keyMap.put("E5", ConstKey.KEY_BOARD.ERROR.TN);
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

    public static KeyBoardSerialHandler getInstance() {
        KeyBoardSerialHandler ins = KeyBoardSerialHandler.instance;
        if (ins == null) {
            synchronized (KeyBoardSerialHandler.class) {
                ins = KeyBoardSerialHandler.instance;
                if (ins == null) {
                    KeyBoardSerialHandler.instance = ins = new KeyBoardSerialHandler();
                }
            }
        }
        return ins;
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

}
