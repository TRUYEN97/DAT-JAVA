/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.api;

import com.qt.common.API.Response;
import com.qt.common.CarConfig;
import com.qt.common.ErrorLog;
import com.qt.common.Util;

/**
 *
 * @author Admin
 */
public class PingAPI {

    private final ApiService apiService;
    private ICommandAPIReceive<Response> pingAPIReceive;
    private Thread thread;
    private boolean stop;

    public PingAPI() {
        this.apiService = new ApiService();
        this.stop = false;
    }

    public void setPingAPIReceive(ICommandAPIReceive<Response> pingAPIReceive) {
        this.pingAPIReceive = pingAPIReceive;
    }

    public void stop() {
        if (this.thread != null) {
            while (this.thread.isAlive()) {
                this.thread.stop();
                Util.delay(500);
            }
        }
    }

    public void start() {
        if (this.thread != null && this.thread.isAlive()) {
            return;
        }
        this.thread = new Thread(() -> {
            try {
                this.stop = false;
                Response response;
                ICommandAPIReceive<Response> aPIReceive;
                CarConfig carConfig = CarConfig.getInstance();
                while (!stop) {
                    aPIReceive = this.pingAPIReceive;
                    if (aPIReceive != null) {
                        response = this.apiService.checkCommad(carConfig.getCarId());
                        if (response != null) {
                            aPIReceive.receive(response);
                        }
                    }
                    Util.delay(5000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorLog.addError(PingAPI.this, e);
            }
        });
        this.thread.start();
    }

}
