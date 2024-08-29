/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.api;

import com.qt.common.API.Response;
import com.qt.common.Util;

/**
 *
 * @author Admin
 */
public class PingAPI {

    private final ApiService apiService;
    private IPingAPIReceive<Response> pingAPIReceive;
    private Thread thread;
    private boolean stop;

    public PingAPI() {
        this.apiService = ApiService.getInstance();
        this.stop = false;
    }

    public void setPingAPIReceive(IPingAPIReceive<Response> pingAPIReceive) {
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
            this.stop = false;
            Response response;
            IPingAPIReceive<Response> aPIReceive;
            while (!stop) {
                aPIReceive = this.pingAPIReceive;
                if (aPIReceive != null) {
                    response = this.apiService.checkInfo();
                    if (response != null) {
                        aPIReceive.receive(response);
                    }
                }
                Util.delay(5000);
            }
        });
        this.thread.start();
    }

}
