package listeners;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Interface of KeyListener that contains default methods.
 * @see KeyListener
 */
public interface CustomKeyListener extends KeyListener{
    default void keyTyped(KeyEvent e)
    {
        char c = e.getKeyChar();
        if ((c == KeyEvent.VK_SPACE)) {

            Toolkit.getDefaultToolkit().beep();
            e.consume();
        }
    }

    @Override
    default void keyPressed(KeyEvent e){}

    @Override
    default void keyReleased(KeyEvent e){}
}
