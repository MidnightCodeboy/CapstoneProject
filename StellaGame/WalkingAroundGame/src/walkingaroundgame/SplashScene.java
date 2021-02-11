/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

import java.awt.Dimension;
import java.awt.Graphics;

/**
 *  Subclass of Scene. Creates an environment that displays an opening animation to user.
 */
public class SplashScene extends Scene{
    
    // Objects to be annimated
    private Sprite banner;
    private Sprite rock;
    private Player stella;
    
    /***
     * Constructor.
     * 
     * @param size Dimensions of the window
     * @param backgroundImagePath path to the background image file
     * @param keys keys object, used to capture user keystrokes
     */
    public SplashScene(Dimension size, String backgroundImagePath, Keys keys){
        super(size, backgroundImagePath, keys);
        
        // Add sprites to the scene
        banner = new Sprite(width / 2, 200, 500, 150, 0, "assets/images/banner.png");
        rock = new Sprite(width / 8, height / 2, 200, 200, 0, "assets/images/rock1.png");
        stella = new Player(width, height / 2, 100, 100, 0);
        
        // Set Stella walking
        stella.setVelocity(-2, 0);
    }
    
    /***
     * Invoked by SceneSelector 25 times per second to update the scene state.
     * Animates a simple scene with Stella walking.
     */
    @Override
    public void update() {
        super.update();

        // Wait before moving to next scene
        if (sceneCounter == 400 && firstTimeInScene) {
            setNextScene("menu");
        }
        
        // Stop Stella when she gets to the center of the screen and face her forward
        if (stella.getX() < width / 2){
            if (stella.getVX() == -2){
                stella.setVelocity(0, 1);
            } else {
                stella.setVelocity(0, 0);
            }
        }
        
        // Update Stella
        stella.update();
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
        banner.paint(bufferedGraphics, camera);
        rock.paint(bufferedGraphics, camera);
        stella.paint(bufferedGraphics, camera);

        g.drawImage(imageBuffer, 0, 0, width, height, null);
    }
    
}