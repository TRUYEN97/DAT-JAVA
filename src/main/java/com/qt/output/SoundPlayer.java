
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.output;

import com.qt.common.ErrorLog;
import com.qt.common.Util;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Admin
 */
public class SoundPlayer {

    private static volatile SoundPlayer instance;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private SoundRunner soundRunner;

    private SoundPlayer() {
        this.soundRunner = new SoundRunner();
    }

    public static SoundPlayer getInstance() {
        SoundPlayer ins = instance;
        if (ins == null) {
            synchronized (SoundPlayer.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new SoundPlayer();
                }
            }
        }
        return ins;
    }

    public void sayResultTest(int score, boolean isPass) {
        new Thread(() -> {
            if (isPass) {
                this.play(new SoundModel("congratulations.wav"));
            } else {
                this.play(new SoundModel("congratulations1.wav"));
            }
            this.play(new SoundModel("theScore.wav"));
            sayNumber(score);
            this.play(new SoundModel("diem.wav", 8000));
            if (isPass) {
                this.play(new SoundModel("success.wav"));
            } else {
                this.play(new SoundModel("failure.wav"));
            }
        }).start();
    }

    public void contestName(String name) {
        new Thread(() -> {
            this.play(new SoundModel("contest/contest.wav"));
            this.play(new SoundModel(String.format("contest/%s.wav", name)));
        }).start();
    }

    public void startContest() {
        this.play(new SoundModel("warning/contestStart.wav", true));
    }

    public void endContest() {
        this.play(new SoundModel("warning/contestFinish.wav", true));
    }

    public void sayWelcome() {
        this.play(new SoundModel("welcome.wav"));
    }

    public void inputId() {
        play(new SoundModel("user/inputId.wav"));
    }

    public void welcomeId(String numString) {
        if (numString == null || numString.isBlank()) {
            return;
        }
//        new Thread(() -> {
        try {
            int num = Integer.parseInt(numString.trim());
            play(new SoundModel("user/welcomeId.wav"));
            sayNumber(num);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
//        }).start();
    }

    public void welcomeCarId(String numString) {
        if (numString == null || numString.isBlank()) {
            return;
        }
//        new Thread(() -> {
        try {
            int num = Integer.parseInt(numString.trim());
            play(new SoundModel("car/carid.wav"));
            sayNumber(num);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
//        }).start();
    }

    public void inputCarId() {
        play(new SoundModel("car/inputCar.wav"));
    }

    public void begin() {
        play(new SoundModel("contest/begin.wav"));
    }

    public void sayErrorCode(String name) {
        new Thread(() -> {
            play(new SoundModel(String.format("errorcode/%s.wav", name)));
        }).start();
    }

    private void sayNumber(int num) {
        if (num < 10) {
            play(new SoundModel(String.format("number/%d.wav", num)));
            return;
        }
        SoundModel[] arr = new SoundModel[3];
        if (num >= 100) {
            int le = num - num % 100;
            num -= le;
            arr[0] = new SoundModel(String.format("number/%d.wav", le));
        }
        int ch = num;
        if (num >= 10) {
            int le = num - num % 10;
            num -= le;
            arr[1] = new SoundModel(String.format("number/%d.wav", le), 15000);
        }
        if (num > 0) {
            if (ch < 10) {
                arr[1] = new SoundModel("number/linh.wav", 15000);
            } else {
                arr[2] = new SoundModel(String.format("number/%d.wav", num), 17000);
                if (num == 5) {
                    arr[2] = new SoundModel(String.format("number/lam.wav"), 17000);
                } else if (ch >= 20) {
                    if (num == 4) {
                        arr[2] = new SoundModel("number/tu.wav", 17000);
                    } else if (num == 1) {
                        arr[2] = new SoundModel("number/mot.wav", 17000);
                    }
                }
            }
        }
        play(arr);
    }

    private synchronized void play(SoundModel... soundModels) {
        for (SoundModel soundModel : soundModels) {
            if (soundModel == null) {
                continue;
            }
            if (soundModel.playNow) {
                soundRunner = new SoundRunner();
            } else {
                while (soundRunner.frameLength - soundRunner.framePosition > soundModel.num) {
                    Util.delay(100);
                }
                soundRunner = new SoundRunner();
            }
            soundRunner.setPath(soundModel.path);
            threadPool.execute(soundRunner);
            while (!soundRunner.running) {
                Util.delay(100);
            }
        }
    }

    public void carIdInvalid() {
        play(new SoundModel("car/CarIdInvalid.wav"));
    }

    public void userIdInvalid() {
        play(new SoundModel("user/IdInvalid.wav"));
    }

    public void userIdHasTest() {
        play(new SoundModel("user/idHasTest.wav"));
    }

    public void pingServerFailed() {
        play(new SoundModel("lostPing.wav"));
    }

    public void sendResultFailed() {
        play(new SoundModel("sendDataFailed.wav"));
    }

    public void sendlostConnect() {
        play(new SoundModel("lostConnect.wav"));
    }

    public void modeInvalid() {
        play(new SoundModel("userModeInvalid.wav"));
    }

    public void practice() {
        play(new SoundModel("cheDoOnTap.wav"));
    }

    public void sayChecking() {
        play(new SoundModel("checking.wav"));
    }

    public void pleasePrepare() {
        play(new SoundModel("pleasePrepare.wav"));
    }

    public void canNotChange() {
        play(new SoundModel("warning/canNotChange.wav"));
    }

    public void inputPassword() {
        play(new SoundModel("warning/inputPassword.wav"));
    }

    public void wrongPassword() {
        play(new SoundModel("warning/wrongPassword.wav"));
    }

    public void inputNewPassword() {
        play(new SoundModel("warning/inputNewPassword.wav"));
    }

    public void changeSucess() {
        play(new SoundModel("warning/changeSucess.wav"));
    }

    public void successSound() {
        play(new SoundModel("warning/dingdong.wav"));
    }

    public void alarm() {
        play(new SoundModel("warning/warning.wav"));
    }

    class SoundRunner implements Runnable {

        private String path;
        private int framePosition;
        private int frameLength;
        private boolean running;

        @Override
        public void run() {
            try (Clip audioClip = AudioSystem.getClip()) {
                if (path == null || path.isBlank()) {
                    return;
                }
                String pathString = String.format("/sound/%s", path);
                var soundFile = getClass().getResource(pathString);
                if (soundFile == null) {
                    ErrorLog.addError(this, String.format("sound: %s - not found!", pathString));
                    return;
                }
                var audioStream = AudioSystem.getAudioInputStream(soundFile);
                if (audioStream == null) {
                    ErrorLog.addError(this, String.format("sound: %s - error!", pathString));
                    return;
                }
                audioClip.open(audioStream);
                audioClip.start();
                frameLength = audioClip.getFrameLength() - 1;
                this.running = true;
                while ((framePosition = audioClip.getFramePosition()) < frameLength) {
                    Util.delay(100);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ErrorLog.addError(this, ex);
            }
            this.running = false;
        }

        private void setPath(String path) {
            this.path = path;
        }

    }

    class SoundModel {

        private boolean playNow = false;

        SoundModel(String path) {
            this(path, 0, false);
        }

        SoundModel(String path, int num) {
            this(path, num, false);
        }

        SoundModel(String path, boolean playNow) {
            this(path, 0, playNow);
        }

        SoundModel(String path, int num, boolean playNow) {
            setNum(num);
            setPath(path);
            this.playNow = playNow;
        }

        private String path;
        private int num;

        private void setPath(String path) {
            this.path = path;
        }

        private void setNum(int num) {
            if (num < 0) {
                num = 0;
            }
            this.num = num;
        }

    }
}
