/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package CitySliders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *  Subclass of Scene. Creates an environment to display rolling game credits to user. Then closes the application.
 */
public class CreditsScene extends Scene{
    
    private ArrayList<String> credits;      // List of credits
    private int vOffset = height / 2;       // Vertical position of first credit at start
    
    /***
     * Constructor.
     * 
     * @param size Dimensions of the window
     * @param backgroundImagePath path to the background image file
     * @param keys keys object, used to capture user keystrokes
     */
    public CreditsScene(Dimension size, String backgroundImagePath, Keys keys, SoundClipper sounds){
        super(size, backgroundImagePath, keys, sounds);
        
        // List of credits
        credits = new ArrayList<>();
        
        // Credits to be displayed
        credits.add("Coding: Joe Roy-Plommer");
        credits.add("Stella Images: a dude on the web");
        credits.add("Rock Images: another dude");
        credits.add("Tree Images: this third guy");
        credits.add("Music: take a guess");
        credits.add("Sounds: wwiwiwiwiw");
        
    }
    
    /***
     * Invoked by SceneSelector 25 times per second to update the scene state.
     * Advances vertical position of credits to create a rolling effect, after a delay.
     */
    @Override
    public void update() {
        super.update();
        
        // Wait before rolling credits
        if(sceneCounter > 100){
            vOffset--;
        }

        // Close Application
        if (sceneCounter == 1000) {
            System.exit(0);
        }
    }

    /***
     * Invoked by SceneSelector 25 times per second to repaint the scene.
     * Paints credits to scene.
     * 
     * @param g graphics context of the JPanel this class descends from
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Set font parameters
        bufferedGraphics.setColor(Color.YELLOW);
        bufferedGraphics.setFont(new Font("Arial", Font.BOLD, 45));
        
        // Paint each credit one below the other with regular spacing
        for (int i = 0; i < credits.size(); i++){
            bufferedGraphics.drawString(credits.get(i), 100, vOffset + (height / 4) * i);
        }
        
        // draw back-buffer onto front
        g.drawImage(imageBuffer, 0, 0, width, height, null);
    }
    
}