package mains;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener{
    private boolean isPressedW;
    private boolean isPressedA;
    private boolean isPressedS;
    private boolean isPressedD;
    private boolean isPressedDebug;
    private volatile boolean isPaused;
    private boolean isKeyPressed;
    private boolean isPressedSpace;

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W){
            isPressedW = true;
        }
        if(key == KeyEvent.VK_A){
            isPressedA = true;
        }
        if(key == KeyEvent.VK_S){
            isPressedS = true;
        }
        if(key == KeyEvent.VK_D){
            isPressedD = true;
        }
        if(key == KeyEvent.VK_F1) {
            isPressedDebug = !isPressedDebug;
        }
        if(key == KeyEvent.VK_SPACE) {
            isPressedSpace = true;
        }
        if(key == KeyEvent.VK_P) {
            isPaused = !isPaused;
        }
    }

    public KeyboardListener() {
        isPressedW = false;
        isPressedA = false;
        isPressedS = false;
        isPressedD = false;
        isPressedDebug = false;
        isKeyPressed = false;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();


        if(key == KeyEvent.VK_W){
            isPressedW = false;
        }
        if(key == KeyEvent.VK_A){
            isPressedA = false;
        }
        if(key == KeyEvent.VK_S){
            isPressedS = false;
        }
        if(key == KeyEvent.VK_D){
            isPressedD = false;
        }
        if(key == KeyEvent.VK_SPACE){
            isPressedSpace = false;
        }
    }


    public boolean isWPressed() {
        return isPressedW;
    }
    public boolean isAPressed() {
        return isPressedA;
    }
    public boolean isSPressed() {
        return isPressedS;
    }
    public boolean isDPressed() {
        return isPressedD;
    }
    public boolean isDebugPressed() {
        return isPressedDebug;
    }
    public boolean isPausedPressed() {
        return isPaused;
    }
    public boolean isSpacerPressed() {
        return isPressedSpace;
    }


    // used bitwise XOR for simplicity's sake
    public boolean checkIfMoreThanOne() {
        return isPressedW ^ isPressedA ^ isPressedS ^ isPressedD;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this should not be used, this is only here because the interface
        // requires it to.
    }

    public boolean isKeyboardPressed() {
        return isPressedA || isPressedW || isPressedS || isPressedD || isPressedSpace;
    }


}

