package android.example.morsecodeflashlight;

import android.content.Context;
import android.hardware.camera2.CameraManager;

public class SosRunnable extends CameraFlashManager implements Runnable {

    SosRunnable(CameraManager cameraManager) {
        super(cameraManager);
    }

    @Override
    public void run() {
        // Flashlight sends out SOS signal (... --- ...)
        try {
            Thread.sleep(1000);
            // S
            FlashDit();
            FlashDit();
            FlashDit();

            // wait 3 seconds between letters
            FlashPauseLetter();

            // O
            FlashDah();
            FlashDah();
            FlashDah();

            // wait 3 seconds between letters
            FlashPauseLetter();

            FlashDit();
            FlashDit();
            FlashDit();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void FlashPauseLetter() throws InterruptedException {
        Thread.sleep(3000);
    }

    private void FlashDah() throws InterruptedException {
        TurnOnAllFlashlights();
        FlashPauseLetter();
        TurnOffAllFlashlights();
        Thread.sleep(1000);
    }

    private void FlashDit() throws InterruptedException {
        TurnOnAllFlashlights();
        Thread.sleep(1000);
        TurnOffAllFlashlights();
        Thread.sleep(1000);
    }


}