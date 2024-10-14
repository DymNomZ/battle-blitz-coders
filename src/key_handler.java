package src;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class key_handler implements KeyListener {

    public boolean up_pressed = false;
    public boolean down_pressed = false;
    public boolean left_pressed = false;
    public boolean right_pressed = false;

    @Override
    public void keyPressed(KeyEvent e){
        //below is what is known as a running switch case, no need for breaks, pretty cool
        int code = e.getKeyCode();
        switch (code){
            case KeyEvent.VK_W -> up_pressed = true;
            case KeyEvent.VK_S -> down_pressed = true;
            case KeyEvent.VK_A -> left_pressed = true;
            case KeyEvent.VK_D -> right_pressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();
        switch (code){
            case KeyEvent.VK_W -> up_pressed = false;
            case KeyEvent.VK_S -> down_pressed = false;
            case KeyEvent.VK_A -> left_pressed = false;
            case KeyEvent.VK_D -> right_pressed = false;
        }
    }

    //Generated because Java angy I don't implement all methods of the interface heheh :3
    @Override
    public void keyTyped(KeyEvent e) {
        //this fucking exception that comes with the generation has fucked me up
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}