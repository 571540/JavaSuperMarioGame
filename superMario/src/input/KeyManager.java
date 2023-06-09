package input;

import game.Handler;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys;
    //public boolean enter = false;
    public boolean jump, run, right, left, crouch, throwFireBall;
    public boolean direction = true;
    public boolean pause = false;
    public boolean pauseSound = false;
    public Handler handler;

    public KeyManager(Handler handler){
        keys = new boolean[256];
        handler = this.handler;
    }

    public void tick(){
        jump = keys[KeyEvent.VK_SPACE];
        run = keys[KeyEvent.VK_SHIFT];
        throwFireBall = keys[KeyEvent.VK_SHIFT];
        right = keys[KeyEvent.VK_RIGHT];
        left = keys[KeyEvent.VK_LEFT];
        crouch = keys[KeyEvent.VK_DOWN];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direction = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            direction = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            pause = !pause;
            pauseSound = !pauseSound;
            System.out.println("pause: " + pause);
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e){
        throwFireBall = true;
    }

}
