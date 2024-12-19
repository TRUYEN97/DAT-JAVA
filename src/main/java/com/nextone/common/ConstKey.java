/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common;

/**
 *
 * @author Admin
 */
public final class ConstKey {

    public static final class URL {

        public static final String CHECK_COMMAND_URL = "checkCommandUrl";
        public static final String CHECK_CAR_ID_URL = "checkCarIdUrl";
        public static final String CHECK_USER_ID_URL = "checkUserIdUrl";
        public static final String SEND_DATA_URL = "sendDataUrl";
        public static final String RUNNABLE_URL = "runnableUrl";
        public static final String CANCEL_REQUEST_URL = "cancelUrl";
        public static final String SERVER_PING_ADDR = "serverIp";
        public static final String CAR_PAIR_URL = "checkCarPairUrl";
    }

    public static final class PATH {

        public static final String DIR_LOG = "dirLog";
        public static final String CONFIG_PATH = "configPath";
        public static final String YARD_CONFIG_PATH = "yardConfigPath";
        public static final String DIR_BACKUP_LOG = "dirBackupLog";
    }

    public static final String VEHICLE_TIME = "vehicleTime";

     public static final class DATA_TRANSFER {
         public static final String OLD_DISTANCE = "oldDistance";
     }
    public static final class CAR_CONFIG {

        public static final String MCU_CONFIG = "mcuConfig";
    }

    public static final class MODE_NAME {

        public static final String DUONG_TRUONG = "DT";
        public static final String SA_HINH = "SH";
    }

    public static final class CONTEST_NAME {

        public static final String XUAT_PHAT = "XP";
        public static final String KET_THUC = "KT";
        public static final String TANG_TOC = "TS";
        public static final String GIAM_TOC = "GS";
        public static final String DUNG_XE_CNDB = "DXCNDB";
        public static final String DUNG_XE_ND = "DXND";
        public static final String VET_BANH_XE = "QVBX";
        public static final String KHAN_CAP = "KC";
        public static final String NGAT_TU = "QNT";
        public static final String CHU_S = "DVQC";
        public static final String GHEP_XE_DOC = "GXD";
        public static final String GHEP_XE_NGANG = "GXN";
        public static final String DUONG_TAU = "QDT";
        public static final String THAY_DOI_SO = "TDSTDB";
    }

    public static final class ERR {

        public static final String DISQUALIFIED = "DISQUALIFIED";
        public static final String FAILED_COMPLETE_PARKING = "FAILED_COMPLETE_PARKING";
        public static final String IGNORED_PARKING = "IGNORED_PARKING";
        public static final String WRONG_WAY = "WRONG_WAY";
        public static final String WRONG_LANE = "WRONG_LANE";
        public static final String INCORRECTLY_FINISHED = "INCORRECTLY_FINISHED";
        public static final String WHEEL_OUT_OF_PATH = "WHEEL_OUT_OF_PATH";
        public static final String ROLLED_BACK_OVER_50M = "ROLLED_BACK_OVER_50M";
        public static final String DONT_STOP_AS_REQUIRED_2 = "DONT_STOP_AS_REQUIRED_2";
        public static final String STOP_AFTER_DES_2 = "STOP_AFTER_DES_2";
        public static final String OVER_30S_TO_START = "OVER_30S_TO_START";
        public static final String FAILED_PASS_INTERSECTION_OVER_30S = "FAILED_PASS_INTERSECTION_OVER_30S";
        public static final String NO_EMERGENCY_SIGNAL = "NO_EMERGENCY_SIGNAL";
        public static final String RAN_A_RED_LIGHT = "RAN_A_RED_LIGHT";
        public static final String ENGINE_SPEED_EXCEEDED_4000_RPM = "ENGINE_SPEED_EXCEEDED_4000_RPM";
        public static final String INCORRECT_PARCKING = "INCORRECT_PARCKING";
        public static final String ENGINE_STALLED = "ENGINE_STALLED";
        public static final String NO_SIGNAL_TURN_RIGHT = "NO_SIGNAL_TURN_RIGHT";
        public static final String NO_SIGNAL_TURN_LEFT = "NO_SIGNAL_TURN_LEFT";
        public static final String NO_SIGNAL_RIGHT_END = "NO_SIGNAL_RIGHT_END";
        public static final String INCORRECT_GEAR_DOWNSHIFT = "INCORRECT_GEAR_DOWNSHIFT";
        public static final String FAILED_TO_SHIFT_HIGH_GEAR = "FAILED_TO_SHIFT_HIGH_GEAR";
        public static final String FAILED_TO_SHIFT_LOW_GEAR = "FAILED_TO_SHIFT_LOW_GEAR";
        public static final String FAILED_TO_REACH_REQUIRED_SPEED = "FAILED_TO_REACH_REQUIRED_SPEED";
        public static final String INCORRECT_GEAR_SHIFT = "INCORRECT_GEAR_SHIFT";
        public static final String PARCKED_WRONG_POS = "PARCKED_WRONG_POS";
        public static final String FAILED_PASS_INTERSECTION_OVER_20S = "FAILED_PASS_INTERSECTION_OVER_20S";
        public static final String DONT_STOP_AS_REQUIRED = "DONT_STOP_AS_REQUIRED";
        public static final String STOP_AFTER_DES = "STOP_AFTER_DES";
        public static final String STOP_BEFORE_DES = "STOP_BEFORE_DES";
        public static final String SIGNAL_KEPT_ON_OVER_5M = "SIGNAL_KEPT_ON_OVER_5M";
        public static final String NO_START_SIGNAL_LEFT = "NO_START_SIGNAL_LEFT";
        public static final String OVER_20S_TO_START = "OVER_20S_TO_START";
        public static final String ERR_PAUSE_MOVE2 = "errPauseMove2";
        public static final String IGNORED_STOP_SIGNAL = "IGNORED_STOP_SIGNAL";
        public static final String WHEEL_CROSSED_LINE = "WHEEL_CROSSED_LINE";
        public static final String TIME_LIMIT_EXCEEDED = "TIME_LIMIT_EXCEEDED";
        public static final String OVERALL_TIME_EXCEEDED = "OVERALL_TIME_EXCEEDED";
        public static final String SPEED_LIMIT_EXCEEDED = "SPEED_LIMIT_EXCEEDED";
        public static final String IGNORED_INSTRUCTIONS = "IGNORED_INSTRUCTIONS";//
        public static final String CAUSED_AN_ACCIDENT = "CAUSED_AN_ACCIDENT";//
        public static final String HEAVY_SHAKING = "HEAVY_SHAKING";//
        public static final String VIOLATION_TRAFFIC_RULES = "VIOLATION_TRAFFIC_RULES";//
        public static final String SWERVED_OUT_OF_LANE = "SWERVED_OUT_OF_LANE";
        public static final String SEATBELT_NOT_FASTENED = "SEATBELT_NOT_FASTENED";//
        public static final String FAILED_APPLY_PARKING_BRAKE = "FAILED_APPLY_PARKING_BRAKE";//
        public static final String GT = "GT";//
        public static final String TT = "TT";//
        public static final String PARKING_BRAKE_NOT_RELEASED = "PARKING_BRAKE_NOT_RELEASED";//
        public static final String USED_GEAR_3_UNDER_20KMH = "USED_GEAR_3_UNDER_20KMH";//
        public static final String FAILED_SHIFTTO_NEUTRAL = "FAILED_SHIFTTO_NEUTRAL";//
        public static final String FAILED_SHIFTUP_GEAR_IN_15M = "FAILED_SHIFTUP_GEAR_IN_15M";//
        public static final String FAILED_SHIFTUP_GEAR_IN_100M = "FAILED_SHIFTUP_GEAR_IN_100M";//
        public static final String FAILED_SHIFTDOWN_GEAR_IN_100M = "FAILED_SHIFTDOWN_GEAR_IN_100M";//
    }

    public static final class KEY_BOARD {

        public static final String POWER = "Power";
        public static final String DOWN = "Down";
        public static final String LEFT = "Left";
        public static final String RIGHT = "Right";
        public static final String UP = "Up";
        public static final String SHOW_ERROR = "Down";
        public static final String IN = "Print";
        public static final String SBD = "*";
        public static final String SETTING = "setting";
        public static final String SO_XE = "/";
        public static final String BACKSPACE = "Backspace";
        public static final String CANCEL = "Cancel";
        public static final String OK = "Enter";
        public static final String CTRL = "Ctrl";
        public static final String ALT = "Alt";
        public static final String VK_0 = "0";
        public static final String VK_1 = "1";
        public static final String VK_2 = "2";
        public static final String VK_3 = "3";
        public static final String VK_4 = "4";
        public static final String VK_5 = "5";
        public static final String VK_6 = "6";
        public static final String VK_7 = "7";
        public static final String VK_8 = "8";
        public static final String VK_9 = "9";
        public static final String VK_POINT = ".";

        public static final class CONTEST {

            public static final String XP = "Xp";
            public static final String TS = "Ts";
            public static final String GS = "Gs";
            public static final String KT = "Kt";
        }

        public static final class ERROR {

            public static final String HL = ERR.IGNORED_INSTRUCTIONS;//
            public static final String TN = ERR.CAUSED_AN_ACCIDENT;//
            public static final String RG = ERR.HEAVY_SHAKING;//
            public static final String QT = ERR.VIOLATION_TRAFFIC_RULES;//
            public static final String CL = ERR.SWERVED_OUT_OF_LANE;
        }

        public static final class MODE {

            public static final String B2_DT_OFF = "P";
            public static final String B2_ON = "O";
        }
    }

    public static final class CAR_MODEL_KEY {

        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
        public static final String STATUS = "status";
        public static final String DISTANCE = "distance";
        public static final String SPEED = "speed";
        public static final String SPEED_KM = "speed1";
        public static final String RPM = "rpm";
        public static final String NT = "nt";
        public static final String NP = "np";
        public static final String AT = "at";
        public static final String PT = "pt";
        public static final String CM = "cm";
        public static final String T1 = "t1";
        public static final String T2 = "t2";
        public static final String T3 = "t3";
        public static final String S1 = "s1";
        public static final String S2 = "s2";
        public static final String S3 = "s3";
        public static final String S4 = "s4";
        public static final String S5 = "s5";
        public static final String REMOTE = "remote";
    }

    public static final class CAR_ST {

        public static final int STOP = 0;
        public static final int FORWARD = 1;
        public static final int BACKWARD = -1;
    }
}
