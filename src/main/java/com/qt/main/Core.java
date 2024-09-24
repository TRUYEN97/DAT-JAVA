/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.main;

import com.qt.common.ConstKey;
import com.qt.common.TestStatusLogger;
import com.qt.controller.ErrorcodeHandle;
import com.qt.controller.PrintWithKeyBoard;
import com.qt.controller.api.ApiService;
import com.qt.controller.modeController.ModeManagement;
import com.qt.controller.settingElement.imp.ChangeCarId;
import com.qt.controller.settingElement.imp.ChangePassword;
import com.qt.controller.settingElement.imp.ChangeUserId;
import com.qt.controller.settingElement.imp.SettingEncoder;
import com.qt.controller.settingElement.imp.SettingSignalLinghtDelayTime;
import com.qt.input.camera.CameraRunner;
import com.qt.mode.imp.DT_B2_MODE;
import com.qt.mode.imp.SH_B2_MODE;
import com.qt.model.modelTest.ErrorCode;
import com.qt.output.SoundPlayer;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.pretreatment.KeyEventsPackage;
import com.qt.view.ViewMain;
import com.qt.view.frame.ChooseModeFrame;
import com.qt.view.frame.SettingFrame;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.Timer;
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
    private final KeyEventManagement eventManagement;
    private final KeyEventsPackage eventsPackage;
    private final ExecutorService threadPool;
    private Timer timer;

    private Core() {
        this.viewMain = ViewMain.getInstance();
        ApiService.getInstance().setRootFrame(this.viewMain);
        this.cameraRunner = CameraRunner.getInstance();
        this.cameraRunner.setCamera(0);
        this.modeManagement = new ModeManagement(viewMain);
        this.chooseModeFrame = new ChooseModeFrame(this.modeManagement);
        this.errorcodeHandle = ErrorcodeHandle.getInstance();
        this.eventManagement = KeyEventManagement.getInstance();
        this.eventsPackage = new KeyEventsPackage(getClass().getSimpleName());
        this.threadPool = Executors.newSingleThreadExecutor();
        PrintWithKeyBoard printWithKeyBoard = new PrintWithKeyBoard();
        ChangeUserId changeUserId = new ChangeUserId();
        SettingFrame settingFrame = new SettingFrame(3, 3);
        initElementSetting(settingFrame);
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.SETTING, (key) -> {
            this.threadPool.submit(settingFrame);
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.SBD, (key) -> {
            this.threadPool.submit(changeUserId);
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.IN, (key) -> {
            this.threadPool.submit(printWithKeyBoard);
        });
        this.timer = new Timer(20000, (e) -> {
//            moveSettingEvent();
        });
        addMode();
        initErrorcode();
    }

    private void initElementSetting(SettingFrame settingFrame) {
        settingFrame.addElementSetting(new ChangeCarId());
        settingFrame.addElementSetting(new SettingEncoder());
        settingFrame.addElementSetting(new SettingSignalLinghtDelayTime());
        settingFrame.addElementSetting(new ChangePassword());
    }
    
    private void moveSettingEvent() {
        this.eventsPackage.remove(ConstKey.KEY_BOARD.SETTING);
        this.timer.stop();
        this.timer = null;
    }

    private void initErrorcode() {
        putErrorCode(ConstKey.ERR.SPEED_LIMIT_EXCEEDED, "Chạy quá tốc độ", 1);
        putErrorCode(ConstKey.ERR.OVERALL_TIME_EXCEEDED, "Quá tổng thời gian", 1);
        putErrorCode(ConstKey.ERR.TIME_LIMIT_EXCEEDED, "Quá thời gian 1 bài", 5);
        putErrorCode(ConstKey.ERR.WHEEL_CROSSED_LINE, "Bánh xe đè vạch", 5);
        putErrorCode(ConstKey.ERR.IGNORED_STOP_SIGNAL, "Không C.H tín hiệu dừng", 5);
        putErrorCode(ConstKey.ERR.ERR_PAUSE_MOVE2, "errPauseMove2", 5);
        putErrorCode(ConstKey.ERR.OVER_20S_TO_START, "Không xp sau 20s", 5);
        putErrorCode(ConstKey.ERR.NO_START_SIGNAL_LEFT, "không xi nhan trái", 5);
        putErrorCode(ConstKey.ERR.SIGNAL_KEPT_ON_OVER_5M, "xi nhan quá 5m", 5);
        putErrorCode(ConstKey.ERR.STOP_BEFORE_DES, "Dừng chưa đến vị trí", 5);
        putErrorCode(ConstKey.ERR.STOP_AFTER_DES, "Dừng xe quá vị trí", 5);
        putErrorCode(ConstKey.ERR.DONT_STOP_AS_REQUIRED, "Không dừng xe", 5);
        putErrorCode(ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_20S, "Ko qua ngã tư sau 20s", 5);
        putErrorCode(ConstKey.ERR.PARCKED_WRONG_POS, "Ghép xe sai vị trí", 5);
        putErrorCode(ConstKey.ERR.INCORRECT_GEAR_SHIFT, "Tăng số sai", 5);
        putErrorCode(ConstKey.ERR.FAILED_TO_REACH_REQUIRED_SPEED, "Không đạt tốc độ", 5);
        putErrorCode(ConstKey.ERR.FAILED_SHIFTUP_GEAR_IN_100M, "Không tăng số", 5);
        putErrorCode(ConstKey.ERR.FAILED_TO_SHIFT_HIGH_GEAR, "Không tăng số", 5);
        putErrorCode(ConstKey.ERR.FAILED_SHIFTDOWN_GEAR_IN_100M, "Không giảm số", 5);
        putErrorCode(ConstKey.ERR.FAILED_TO_SHIFT_LOW_GEAR, "Không giảm số", 5);
        putErrorCode(ConstKey.ERR.INCORRECT_GEAR_DOWNSHIFT, "Giảm số sai", 5);
        putErrorCode(ConstKey.ERR.NO_SIGNAL_RIGHT_END, "Không xi nhan phải", 5);
        putErrorCode(ConstKey.ERR.NO_SIGNAL_TURN_LEFT, "Không xi nhan trái", 5);
        putErrorCode(ConstKey.ERR.NO_SIGNAL_TURN_RIGHT, "Không xi nhan phải", 5);
        putErrorCode(ConstKey.ERR.ENGINE_STALLED, "Chết máy", 5);
        putErrorCode(ConstKey.ERR.INCORRECT_PARCKING, "Ghép xe sai", 5);
        putErrorCode(ConstKey.ERR.ENGINE_SPEED_EXCEEDED_4000_RPM, "ĐC quá 4000 vòng/phút", 5);
        putErrorCode(ConstKey.ERR.RAN_A_RED_LIGHT, "Vượt đèn đỏ", 10);
        putErrorCode(ConstKey.ERR.NO_EMERGENCY_SIGNAL, "Ko tuân thủ T.H khẩn cấp", 10);
        putErrorCode(ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_30S, "Ko qua ngã tư sau 30s", 25);
        putErrorCode(ConstKey.ERR.OVER_30S_TO_START, "Không xp sau 30s", 25);
        putErrorCode(ConstKey.ERR.STOP_AFTER_DES_2, "Dừng xe quá vị trí", 25);
        putErrorCode(ConstKey.ERR.DONT_STOP_AS_REQUIRED_2, "Không dừng xe", 25);
        putErrorCode(ConstKey.ERR.ROLLED_BACK_OVER_50M, "Trôi dốc quá 50cm", 25);
        putErrorCode(ConstKey.ERR.WHEEL_OUT_OF_PATH, "không qua vệt bánh xe", 25);
        putErrorCode(ConstKey.ERR.INCORRECTLY_FINISHED, "Kết thúc sai", 25);
        putErrorCode(ConstKey.ERR.WRONG_LANE, "Sai làn đường", 25);
        putErrorCode(ConstKey.ERR.WRONG_WAY, "Đi sai đường", 25);
        putErrorCode(ConstKey.ERR.IGNORED_PARKING, "Không ghép xe", 25);
        putErrorCode(ConstKey.ERR.FAILED_COMPLETE_PARKING, "Không hoàn thành ghép xe", 25);
        putErrorCode(ConstKey.ERR.DISQUALIFIED, "Bị truất quyền thi", 25);
        putErrorCode(ConstKey.ERR.SEATBELT_NOT_FASTENED, "Không thắt dây an toàn", 5);
        putErrorCode(ConstKey.ERR.USED_GEAR_3_UNDER_20KMH, "Tay số không phù hợp", 2);
        putErrorCode(ConstKey.ERR.FAILED_APPLY_PARKING_BRAKE, "Không kéo phanh tay", 5);
        putErrorCode(ConstKey.ERR.PARKING_BRAKE_NOT_RELEASED, "Không nhả phanh tay", 5);
        putErrorCode(ConstKey.ERR.FAILED_SHIFTTO_NEUTRAL, "Không nhả phanh tay", 5);
        putErrorCode(ConstKey.ERR.HEAVY_SHAKING, "Rung xe", 5);
        putErrorCode(ConstKey.ERR.FAILED_SHIFTUP_GEAR_IN_15M, "15m Không tăng số", 5);
        putErrorCode(ConstKey.ERR.VIOLATION_TRAFFIC_RULES, "Quy tắc giao thông", 10);
        putErrorCode(ConstKey.ERR.IGNORED_INSTRUCTIONS, "Không tuân thu hiệu lệnh", 25);
        putErrorCode(ConstKey.ERR.CAUSED_AN_ACCIDENT, "Gây tai nạn", 25);
        putErrorCode(ConstKey.ERR.SWERVED_OUT_OF_LANE, "Gây tai nạn", 25);
        /////////////////////
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.TT, new ErrorCode("TT", 5, "KO TANG TOC DO"));
        this.errorcodeHandle.putErrorCode(ConstKey.ERR.GT, new ErrorCode("GT", 5, "KO GIAM TOC DO"));
    }

    private void putErrorCode(String errName, String description, int point) {
        putErrorCode(errName, errName, description, point);
    }

    private void putErrorCode(String errCode, String errName, String description, int point) {
        if (errCode == null || errName == null) {
            return;
        }
        this.errorcodeHandle.putErrorCode(errCode, new ErrorCode(errName, point, description));
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
        SoundPlayer.getInstance().sayWelcome();
        this.eventManagement.start();
        this.chooseModeFrame.display();
        this.cameraRunner.start();
        this.viewMain.display();
        TestStatusLogger.getInstance().check();
        this.eventManagement.addKeyEventBackAge(eventsPackage);
        this.timer.start();
    }

}
