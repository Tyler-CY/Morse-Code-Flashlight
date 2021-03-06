package android.example.morsecodeflashlight;

import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

public class ParserRunnable extends CameraFlashManager implements Runnable {
    String message;

    // The ratio for morse code signal
    int durationDit = 1, durationDah = 3, durationLetter = 3, durationWord = 7;
    float scale;

    // ToneGenerator if the user wants to play the sound as well
    ToneGenerator mToneGenerator;
    int mSoundEffect = ToneGenerator.TONE_DTMF_0;

    ParserRunnable(CameraManager cameraManager, String message, int speed, boolean soundOn, int volume) {
        super(cameraManager);
        this.message = message;
        this.scale = 100L * speed;
        Log.d("SCALE", Float.toString(this.scale));
        if (soundOn) {
            mToneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, volume);
        }
    }

    @Override
    public void run() {
        // Handshake
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            handleInterruptedException(e);
            e.printStackTrace();
        }

        for (int i = 0; i < message.length(); i++) {
            // Extract the current character
            char c = message.charAt(i);

            // Sends out dits or dahs or turn of flashlight depending on the morse code.
            if (c == '.') {
                try {
                    flashDit();
                } catch (InterruptedException e) {
                    handleInterruptedException(e);
                    return;
                }
            } else if (c == '-') {
                try {
                    flashDah();
                } catch (InterruptedException e) {
                    handleInterruptedException(e);
                    return;
                }
            } else if (c == '_') {
                try {
                    flashPauseWord();
                } catch (InterruptedException e) {
                    handleInterruptedException(e);
                    return;
                }
            } else {
                try {
                    flashPauseLetter();
                } catch (InterruptedException e) {
                    handleInterruptedException(e);
                    return;
                }
            }

        }
    }

    /*
    Clean up procedures if the thread is interrupted (probably by UI stop button)
     */
    private void handleInterruptedException(InterruptedException e) {
        TurnOffAllFlashlights();
        try {
            mToneGenerator.stopTone();
        }
        catch (NullPointerException ignored){}
        Thread.currentThread().interrupt();
        e.printStackTrace();
    }

    /*
    Flash sequence between words
     */
    private void flashPauseWord() throws InterruptedException {
        Thread.sleep((long) (durationWord * scale));
    }

    /*
    Flash sequence between letters of the same word
     */
    private void flashPauseLetter() throws InterruptedException {
        Thread.sleep((long) (durationLetter * scale));
    }

    /*
    Flash sequence for a '-'
     */
    private void flashDah() throws InterruptedException {
        TurnOnAllFlashlights();
        try {
            mToneGenerator.startTone(mSoundEffect, (int) (durationDah * scale));
        } catch (NullPointerException ignored){}
        Thread.sleep((long) (durationDah * scale));
        TurnOffAllFlashlights();
        Thread.sleep((long) (durationDit * scale));
    }

    /*
    Flash sequence for a '.'
     */
    private void flashDit() throws InterruptedException {
        TurnOnAllFlashlights();
        try {
            mToneGenerator.startTone(mSoundEffect, (int) (durationDit * scale));
        } catch (NullPointerException ignored){}
        Thread.sleep((long) (durationDit * scale));
        TurnOffAllFlashlights();
        Thread.sleep((long) (durationDit * scale));
    }


    /*
    Currently not in used. Set as default method.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

