/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *  Subclass of Scene. Creates an interactive environment that display game control information to user,
 *  and listens for input.
 */
public class ControlsScene extends Scene{
    
    /***
     * Constructor.
     * 
     * @param size Dimensions of the window
     * @param backgroundImagePath path to the background image file
     * @param keys keys object, used to capture user keystrokes
     */
    public ControlsScene(Dimension size, String backgroundImagePath, Keys keys){
        super(size, backgroundImagePath, keys);
    }
    
    /***
     * Invoked by SceneSelector 25 times per second to update the scene state.
     * Checks for user keystrokes.
     */
     @Override
    public void update() {
        super.update();

        // Check for keyboard input after entry delay
        if (entryDelay == 0) {
            if (keys.isKeyPressed(KeyEvent.VK_P)) {
                setNextScene("menu");
            }
        }
    }

    /***
     * Invoked by SceneSelector 25 times per second to repaint the scene.
     * 
     * @param g graphics context of the JPanel this class descends from
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imageBuffer, 0, 0, width, height, null);
    }
    
}
