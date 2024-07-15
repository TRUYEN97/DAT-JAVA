
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

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    private SoundRunner soundRunner = new SoundRunner();

    public void sayResultTest(int score, boolean isPass) {
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
    }

    public void contestName(String name) {
        this.play(new SoundModel("contest/contest.wav"));
        this.play(new SoundModel(String.format("contest/%s.wav", name)));
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

    public void welcomeId(int num) {
        play(new SoundModel("user/welcomeId.wav"));
        sayNumber(num);
    }

    public void welcomeCarId(int num) {
        play(new SoundModel("car/carid.wav"));
        sayNumber(num);
    }

    public void inputCarId() {
        play(new SoundModel("car/inputCar.wav"));
    }

    public void begin() {
        play(new SoundModel("contest/begin.wav"));
    }

    public void sayErrorCode(String name) {
        play(new SoundModel(String.format("errorcode/%s.wav", name)));
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
            }
            arr[2] = new SoundModel(String.format("number/%d.wav", num), 17000);
            if (ch >= 20) {
                if (num == 4) {
                    arr[2] = new SoundModel("number/tu.wav", 17000);
                } else if (num == 1) {
                    arr[2] = new SoundModel("number/mot.wav", 17000);
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
                    Util.delay(50);
                }
                soundRunner = new SoundRunner();
            }
            soundRunner.setPath(soundModel.path);
            threadPool.submit(soundRunner);
            while (!soundRunner.running) {
                Util.delay(10);
            }
        }
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
                var soundFile = getClass().getResource(String.format("/sound/%s", path));
                if (soundFile == null) {
                    return;
                }
                audioClip.open(AudioSystem.getAudioInputStream(soundFile));
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
