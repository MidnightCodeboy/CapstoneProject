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
    
    private Fade fade;
    private boolean isFadable = true; // is the current state allowed to trigger a fade?
    private int waitToFade = 0;
        
    public SplashScene(Dimension size, String backgroundImagePath, Keys keys, SoundClipper sounds){
        super(size, backgroundImagePath, keys, sounds);
        fade = new Fade((int)(width / 2), (int)(height / 2), width, height, Color.BLACK);
    }
    
    /***
     * Invoked by SceneSelector 25 times per second to update the scene state.
     * Animates a simple scene with Stella walking.
     */
    @Override
    public void update() {
        super.update();

        // Wait before moving to next scene
        if (sceneCounter == 200 && firstTimeInScene) {
            if (isFadable){
                isFadable = false;
                fade.fade(Fade.Direction.OUT, 4);
                waitToFade = 100;
            }
        }
        
        if (sceneCounter > 200 && waitToFade == 0){
            setNextScene("game");
        }
        
        fade.update();
        
        if (waitToFade > 0) waitToFade--;
    }

    /***
     * Invoked by SceneSelector 25 times per second to repaint the scene.
     * 
     * @param g graphics context of the JPanel this class descends from
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        fade.paint(bufferedGraphics, camera);
        g.drawImage(imageBuffer, 0, 0, width, height, null); 
    }
    
}