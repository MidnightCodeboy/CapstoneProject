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
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *  Subclass of Scene. Creates an environment to display rolling game credits to user. Then closes the application.
 */
public class CreditsScene extends Scene{
    
    //                // List of credits
    private int vOffset = (int)(height * 0.2);       // Vertical position of first credit at start
    private int lineSpacing = 50;
    
    // Colours
    private Color textColor = Color.WHITE;
    private Color backgroundColor = new Color(0, 0, 0, 150);
    private Color baseColor = new Color(230, 255, 255);
    
    // Fonts
    private Font creditFont = new Font("Arial", Font.BOLD, 45);
    
    // Sprites
    private BackgroundEffect bg;
    private Rectangle tablet;
    private ArrayList<String> credits;
    private Fade fade;
    
    // Fade Parameters
    private boolean isFadable = true; // is the current state allowed to trigger a fade?
    private int waitToFade = 0;
    
    
    /***
     * Constructor.
     * 
     * @param size Dimensions of the window
     * @param backgroundImagePath path to the background image file
     * @param keys keys object, used to capture user keystrokes
     */
    public CreditsScene(Dimension size, String backgroundImagePath, Keys keys, SoundClipper sounds){
        super(size, backgroundImagePath, keys, sounds);
        
        bg = new BackgroundEffect((int)(width / 2), (int)(height / 2), width, height, baseColor);
        tablet = new Rectangle((int)(width * 0.1), (int)(height * 0.05),(int)(width * 0.8), (int)(height * 0.8));
        fade = new Fade((int)(width / 2), (int)(height / 2), width, height, Color.BLACK);
        fade.fade(Fade.Direction.IN, 4);
        
        // List of credits
        credits = new ArrayList<>();
        
        // Credits to be displayed
        credits.add("Coding: Joe Roy-Plommer");
        credits.add("");
        credits.add("City Images: WallpaperAccess.com");
        credits.add("");
        credits.add("Music");
        credits.add("Goldberg Variations BWV 998");
        credits.add("By J.S. Bach");
        credits.add("Performed by Kimiko Ishizaka");
        credits.add("");
        credits.add("Sound effects obtained from");
        credits.add("https://www.zapsplat.com");
  
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
            //vOffset--;
        }

        // Wait before moving to next scene
        if (sceneCounter == 400 && firstTimeInScene) {
            if (isFadable){
                isFadable = false;
                fade.fade(Fade.Direction.OUT, 4);
                waitToFade = 100;
            }
        }
        
        if (sceneCounter > 400 && waitToFade == 0){
            System.exit(0);
        }
        if (waitToFade > 0) waitToFade--;
        
        bg.update();
        fade.update();
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
        
        bg.paint(bufferedGraphics, camera);
        
        
        // Paint Tablet
        bufferedGraphics.setColor(backgroundColor);
        bufferedGraphics.fillRect(tablet.x, tablet.y, tablet.width, tablet.height);
        
        // Set font parameters
        bufferedGraphics.setColor(textColor);
        bufferedGraphics.setFont(creditFont);
        
        // Paint each credit one below the other with regular spacing
        for (int i = 0; i < credits.size(); i++){
            bufferedGraphics.drawString(credits.get(i), (int)(width * 0.2), vOffset + lineSpacing * i);
        }
        
        fade.paint(bufferedGraphics, camera);
        
        // draw back-buffer onto front
        g.drawImage(imageBuffer, 0, 0, width, height, null);
    }
    
}