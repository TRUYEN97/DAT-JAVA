/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.input.serial;

import com.nextone.common.ErrorLog;
import com.nextone.common.communication.Communicate.Impl.Comport.ComPort;
import com.nextone.common.communication.socket.Unicast.commons.Interface.IReceiver;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class SerialHandler implements Runnable {

    private final String serialName;
    private final int baudrate;
    private final CheckConntect checkConntect;
    private boolean connect = false;
    private IReceiver<ComPort> receiver;
    private ComPort startSerial;
    private Runnable action;

    public SerialHandler(String serialName, int baudrate) {
        this.baudrate = baudrate;
        this.serialName = serialName;
        this.checkConntect = new CheckConntect(2000);
    }

    public void setFirstConnectAction(Runnable action) {
        this.action = action;
    }

    public String getSerialName() {
        return serialName;
    }

    private boolean checkConnecting() {
        if (!isConnect()) {
            return false;
        }
        return send("isConnect");
    }

    public boolean isConnect() {
        return connect && startSerial != null && startSerial.isConnect();
    }

    public void setReceiver(IReceiver<ComPort> receiver) {
        this.receiver = receiver;
    }

    public synchronized boolean send(String command, Object... params) {
        if (!isConnect()) {
            return false;
        }
        if (this.startSerial.sendCommand(command, params)) {
            this.checkConntect.update();
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            connect = false;
            startSerial = null;
            try (ComPort serial = new ComPort()) {
                while (!serial.connect(serialName, baudrate)) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
                System.out.println("reConnect " + serialName);
                startSerial = serial;
                connect = true;
                if (action != null) {
                    action.run();
                }
                String line;
                this.checkConntect.start();
                while (isConnect()) {
                    line = serial.readLine();
                    if (line == null) {
                        if (this.checkConntect.isNoResponse()) {
                            connect = false;
                        }
                        continue;
                    }
                    this.checkConntect.update();
                    if (line.equalsIgnoreCase("isConnect")) {
                        continue;
                    }
                    if (receiver != null) {
                        receiver.receiver(serial, line.trim());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorLog.addError(this, e);
            }
            this.checkConntect.stop();
        }
    }

    private class CheckConntect {

        private final Timer timer;
        private boolean hasCheck = false;
        private boolean noResponse = false;

        private CheckConntect(int time) {
            this.timer = new Timer(time <= 1000 ? 1000 : time, (e) -> {
                if (!hasCheck) {
                    checkConnecting();
                    hasCheck = true;
                } else {
                    if (isConnect()) {
                        startSerial.stopRead();
                    }
                    noResponse = true;
                }
            });
        }

        private void update() {
            noResponse = false;
            hasCheck = false;
            timer.restart();
        }

        private boolean isNoResponse() {
            return noResponse;
        }

        private void start() {
            noResponse = false;
            hasCheck = false;
            if (this.timer.isRunning()) {
                return;
            }
            this.timer.start();
        }

        private void stop() {
            if (this.timer.isRunning()) {
                this.timer.stop();
            }
        }

    }
}
