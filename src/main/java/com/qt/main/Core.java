/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.main;

import com.qt.common.ConstKey;
import com.qt.common.TestStatusLogger;
import com.qt.controller.ErrorcodeHandle;
import com.qt.controller.api.ApiService;
import com.qt.controller.modeController.ModeManagement;
import com.qt.input.camera.CameraRunner;
import com.qt.mode.imp.DT_B2_MODE;
import com.qt.mode.imp.SH_B2_MODE;
import com.qt.model.modelTest.ErrorCode;
import com.qt.view.ViewMain;
import com.qt.view.frame.ChooseModeFrame;
import lombok.Getter;

/**
 *
 * @author Admin
 */
@Getter
public class Core {

    private static volatile Core instance;
    private final ModeManagement modeManagement;
    private final ViewMain viewMain;
    private final CameraRunner cameraRunner;
    private final ChooseModeFrame chooseModeFrame;
    private final ErrorcodeHandle errorcodeHandle;
    private final TestStatusLogger statusLogger;

    private Core() {
        this.viewMain = ViewMain.getInstance();
        ApiService.getInstance().setRootFrame(this.viewMain);
        this.cameraRunner = CameraRunner.getInstance();
        this.cameraRunner.setCamera(0);
        this.modeManagement = new ModeManagement(viewMain);
        this.chooseModeFrame = new ChooseModeFrame(this.modeManagement);
        this.errorcodeHandle = ErrorcodeHandle.getInstance();
        this.statusLogger = TestStatusLogger.getInstance();
        addMode();
        initErrorcode();
    }

    private void initErrorcode() {
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.SO3, new ErrorCode("SO3", 2, "TAY SO KHONG PHU HOP"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TIME_OUT, new ErrorCode("timeout", 5, "QUA THOI GIAN"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.AT, new ErrorCode("AT", 5, "DAY AN TOAN"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.NT, new ErrorCode("NT", 5, "KHONG XI NHAN TRAI"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.NP, new ErrorCode("NP", 5, "KHONG XI NHAN PHAI"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.KPT, new ErrorCode("KPT", 5, "KHONG KEO PHANH TAY"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.NPT, new ErrorCode("NPT", 5, "KHONG NHA PHANH TAY"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TS, new ErrorCode("TS", 5, "KO TANG DUOC SO"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.GS, new ErrorCode("GS", 5, "KO GIAM DUOC SO"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TT, new ErrorCode("TT", 5, "KO TANG TOC DO"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.GT, new ErrorCode("GT", 5, "KO GIAM TOC DO"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.S20, new ErrorCode("S20", 5, "QUA 20 GIAY"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.RG, new ErrorCode("RG", 5, "RUNG GIAT"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.CM, new ErrorCode("CM", 5, "CHET MAY"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.RPM, new ErrorCode("RPM", 5, "QUA VONG TUA"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.QT, new ErrorCode("QT", 10, "QUY TAC GIAO THONG"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.S30, new ErrorCode("S30", 25, "QUA 30 GIAY"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.HL, new ErrorCode("HL", 25, "HIEU LENH"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TN, new ErrorCode("TN", 25, "GAY TAI NAN"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.CL, new ErrorCode("CL", 25, "CHOANG LAI"));
    }

    public static Core getInstance() {
        Core ins = instance;
        if (ins == null) {
            synchronized (Core.class) {
                ins = instance;
                if (ins == null) {
                    instance = ins = new Core();
                }
            }
        }
        return ins;
    }

    private void addMode() {
        this.modeManagement.addMode(new DT_B2_MODE());
        this.modeManagement.addMode(new SH_B2_MODE());
    }

    public void start() {
        this.chooseModeFrame.display();
        this.cameraRunner.start();
        this.viewMain.display();
        this.modeManagement.start();
        this.statusLogger.check();
    }

}
