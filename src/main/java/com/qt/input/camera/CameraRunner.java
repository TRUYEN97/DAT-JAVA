/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.input.camera;

import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.interfaces.IStarter;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

/**
 *
 * @author Admin
 */
public class CameraRunner implements IStarter {

    private final ExecutorService threadPool;
    private final Camera camera;
    private Future future;

    public CameraRunner() {
        this.threadPool = Executors.newSingleThreadExecutor();
        this.camera = new Camera();
    }

    public boolean openCamera(int index) {
        return camera.open(index);
    }

    public void setImageLabel(JLabel ImgLb) {
        this.camera.imageLabel = ImgLb;
    }

    public boolean saveImg(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            BufferedImage image = this.camera.image;
            if (image == null) {
                return false;
            }
            return ImageIO.write(image, ".png", file);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    @Override
    public void start() {
        if (isStarted()) {
            return;
        }
        this.future = this.threadPool.submit(camera);
    }

    @Override
    public void stop() {
        if (!isStarted()) {
            return;
        }
        this.camera.stop();
        while (isStarted()) {
            this.future.cancel(true);
            Util.delay(500);
        }
    }

    @Override
    public boolean isStarted() {
        return this.future != null && !this.future.isDone();
    }

    class Camera implements Runnable {

        static {
            // Tải thư viện gốc của OpenCV
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
        private final VideoCapture camera = new VideoCapture();
        private boolean stop;
        private JLabel imageLabel;
        private BufferedImage image;

        private boolean open(int cameraID) {
            if (this.camera.open(cameraID) && this.camera.isOpened()) {
                camera.set(Videoio.CAP_PROP_FRAME_WIDTH, 320);
                camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 240);
                return true;
            }
            return false;
        }

        private void stop() {
            this.stop = true;
            this.camera.release();
        }

        @Override
        public void run() {
            try {
                this.stop = false;
                Mat frameMat = new Mat();
                while (!stop) {
                    if (camera.isOpened() && camera.read(frameMat)) {
                        image = matToBufferedImage(frameMat);
                        Core.flip(frameMat, frameMat, 1);
                        updateView(matToBufferedImage(frameMat));
                    }
                    Util.delay(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorLog.addError(this, e);
            }
        }

        private void updateView(Image img) {
            if (img == null || imageLabel == null) {
                return;
            }
            try {
                img.getScaledInstance(320, 240, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                e.printStackTrace();
                ErrorLog.addError(this, e);
            }
        }

        private BufferedImage matToBufferedImage(Mat mat) {
            int type = BufferedImage.TYPE_BYTE_GRAY;
            if (mat.channels() > 1) {
                type = BufferedImage.TYPE_3BYTE_BGR;
            }
            int bufferSize = mat.channels() * mat.cols() * mat.rows();
            byte[] buffer = new byte[bufferSize];
            mat.get(0, 0, buffer);
            BufferedImage img = new BufferedImage(mat.cols(), mat.rows(), type);
            final byte[] targetPixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
            System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
            return img;
        }
    }
}
