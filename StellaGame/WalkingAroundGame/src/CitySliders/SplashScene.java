/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package CitySliders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 *  Subclass of Scene. Creates an environment that displays an opening animation to user.
 */
public class SplashScene extends Scene{
    
    // Objects to be annimated
    
    /***
     * Constructor.
     * 
     * @param size Dimensions of the window
     * @param backgroundImagePath path to the background image file
     * @param keys keys object, used to capture user keystrokes
     */
    public SplashScene(Dimension size, String backgroundImagePath, Keys keys, SoundClipper sounds){
        super(size, backgroundImagePath, keys, sounds);
        
        // Add sprites to the scene
        
        // Set Stella walking
    }
    
    /***
     * Invoked by SceneSelector 25 times per second to update the scene state.
     * Animates a simple scene with Stella walking.
     */
    @Override
    public void update() {
        super.update();

        // Wait before moving to next scene
        if (sceneCounter == 100 && firstTimeInScene) {
            setNextScene("game");
        }
        
        // Update Stella
    }

    /***
     * Invoked by SceneSelector 25 times per second to repaint the scene.
     * 
     * @param g graphics context of the JPanel this class descends from
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Paint Sprites

        g.drawImage(imageBuffer, 0, 0, width, height, null);
    }
    
}