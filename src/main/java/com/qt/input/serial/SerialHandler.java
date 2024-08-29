/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.input.serial;

import com.qt.common.communication.Communicate.Impl.Comport.ComPort;
import com.qt.common.communication.socket.Unicast.commons.Interface.IReceiver;


/**
 *
 * @author Admin
 */
public class SerialHandler implements Runnable {

    private final String serialName;
    private final int baudrate;
    private boolean connect = false;
    private IReceiver<ComPort> receiver;
    private ComPort startSerial;
    private Runnable action;

    public SerialHandler(String serialName, int baudrate) {
        this.baudrate = baudrate;
        this.serialName = serialName;
    }

    public void setFirstConnectAction(Runnable action) {
        this.action = action;
    }
    
    public String getSerialName() {
        return serialName;
    }

    public boolean isConnect() {
        return connect;
    }

    public void setReceiver(IReceiver<ComPort> receiver) {
        this.receiver = receiver;
    }
    
    public boolean send(String command, Object... params){
        if (!connect) {
            return false;
        }
        return this.startSerial.sendCommand(command, params);
    }

    @Override
    public void run() {
        while (true) {
            connect = false;
            startSerial = null;
            try (ComPort serial = new ComPort()){
                while (!serial.connect(serialName, baudrate)) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
                startSerial = serial;
                connect = true;
                if (action != null) {
                    action.run();
                }
                String line;
                while (serial.isConnect()) { 
                    line = serial.readLine();
                    if(line == null){
                        continue;
                    }
                    if (receiver != null) {
                        receiver.receiver(serial, line.trim());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
