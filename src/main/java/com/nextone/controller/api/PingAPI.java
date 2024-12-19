/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.api;

import com.nextone.common.API.Response;
import com.nextone.common.CarConfig;
import com.nextone.common.ErrorLog;
import com.nextone.common.Util;
import com.nextone.main.Core;

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
                    if (!Core.getInstance().getModeManagement().isOffLineMode()) {
                        aPIReceive = this.pingAPIReceive;
                        if (aPIReceive != null) {
                            response = this.apiService.checkCommad(carConfig.getCarId());
                            if (response != null) {
                                aPIReceive.receive(response);
                            }
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
