/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common;

/**
 *
 * @author Admin
 */
public final class ConstKey {

    public static final String CHECK_INFO = "checkInfo";
    public static final String SERVER_PING_ADDR = "serverIp";
    public static final String CHECK_CAR_ID = "checkCarId";
    public static final String CHECK_USER_ID = "checkUserId";
    public static final String SEND_DATA = "sendData";
    public static final String RUNNABLE = "runnable";
    public static final String DIR_LOG = "dirLog";
    public static final String DIR_BACKUP_LOG = "dirBackupLog";

    public static final class MODE_NAME {

        public static final String B2_DUONG_TRUONG = "DT-B2";
        public static final String B2_SA_HINH = "SH-B2";
    }

    public static final class CT_NAME {

        public static final String XUAT_PHAT = "XP";
        public static final String KET_THUC = "KT";
        public static final String TANG_TOC = "TS";
        public static final String GIAM_TOC = "GS";
    }

    public static final class ERR {

        public static final String HL = "Left";//
        public static final String TN = "Begin";//
        public static final String RG = "Right";//
        public static final String QT = "+";//
        public static final String CL = "End";
        ////////////
        public static final String AT = "AT";//
        public static final String NTP = "NTP";//
        public static final String KPT = "KPT";//
        public static final String TS = "TS";//
        public static final String GS = "GS";//
        public static final String S30 = "S30";//
        public static final String S20 = "S20";//
        public static final String CM = "CM";//
        public static final String GT = "GT";//
        public static final String TT = "TT";//
        public static final String RPM = "RPM";//
        public static final String PT = "PT";//
        public static final String SO3 = "SO3";//
        public static final String VSO = "VSO";//
        public static final String TIME_OUT = "timeout";//
    }

    public static final class KEY_BOARD {

        public static final String SHOW_ERROR = "Down";
        public static final String IN = "Insert";
        public static final String SBD = "*";
        public static final String SO_XE = "/";
        public static final String BACKSPACE = "Backspace";
        public static final String CANCEL = ".";
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

        public static final class CONTEST {

            public static final String XP = "Home";
            public static final String TS = "Up";
            public static final String GS = "Page Up";
            public static final String KT = "-";
        }

        public static final class ERROR {

            public static final String HL = ConstKey.ERR.HL;//
            public static final String TN = ConstKey.ERR.TN;//
            public static final String RG = ConstKey.ERR.RG;//
            public static final String QT = ConstKey.ERR.QT;//
            public static final String CL = ConstKey.ERR.CL;
        }

        public static final class MODE {

            public static final String B2_DT_OFF = "P";
            public static final String B2_ON = "O";
        }
    }

    public static final class CAR_MODEL_KEY {

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
