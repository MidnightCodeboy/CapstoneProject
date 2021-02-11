/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.JPanel;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 * Creates an environment for painting sprites.
 */
public class Scene extends JPanel {
    
    // JPanel Properties
    protected int width;
    protected int height;
    
    // Scene Dimesions
    protected int sceneWidth;
    protected int sceneHeight;
    protected int backgroundScaleX;
    protected int backgroundScaleY;
    protected Rectangle camera;

    // Scenes this can transition to
    HashMap<String, Scene> transitionToScene = new HashMap<String, Scene>();
    Scene nextScene = this; // this means stay on this sceen. Only change value when we want to transition.

    // Temporal Properties
    protected int sceneCounter = 0;
    protected int entryDelay = 0;
    
    // Keys object to listen for keyboard input
    Keys keys;
    
    // Scene Transition Variables
    protected boolean firstTimeInScene = true;
    protected boolean isTransitioning = false;

    // Set up Back-Buffer as a stutter-free drawing surface
    protected BufferedImage imageBuffer;
    protected Graphics2D bufferedGraphics;
    
    // Set up Affine Transform to handle rotating the canvas
    private final AffineTransform at = new AffineTransform();

    // Set up image importing for background image
    private final Toolkit tk = Toolkit.getDefaultToolkit();
    private Image background;

    /***
     * Constructor.
     * 
     * @param size Dimensions of the window
     * @param backgroundImagePath path to the background image file
     * @param keys keys object, used to capture user keystrokes
     */
    public Scene(Dimension size, String backgroundImagePath, Keys keys) {
        super();
        repaint();
        
        // Set Panel Size
        this.width = (int) size.getWidth();
        this.height = (int) size.getHeight();
        
        // Set Scene Scale
        sceneWidth = width;
        sceneHeight = height;
        camera = new Rectangle(0, 0, width, height);

        // Set Up Key Listener
        this.keys = keys;
        
        // Set up properties for back-buffer
        imageBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedGraphics = (Graphics2D) imageBuffer.getGraphics();
        bufferedGraphics.setTransform(at);
        bufferedGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Set up background image
        setBackground(backgroundImagePath);
    }

    /***
     * Invoked by SceneSelector 25 times per second to update the scene state.
     * Animates a simple scene with Stella walking.
     */
    public void update() {
        // Safely increment the scene counter
        sceneCounter++;
        sceneCounter %= Integer.MAX_VALUE;
        
        // Returns entry delay to zero so that we can listen for keyboard input again
        if (entryDelay > 0){ entryDelay--; }
        
        if (!isTransitioning){
            nextScene = this;
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
        
        // get camera x and y
        int cx = camera.x;
        int cy = camera.y;
        
        // Get co-ordinates for pulling part of the background image for painting on screen
        int sx1 = (int)(background.getWidth(this) *  (camera.x *1.0 / sceneWidth));
        int sy1 = (int)(background.getHeight(this) *  (camera.y *1.0 / sceneHeight));
        int sx2 = (int)(background.getWidth(this) *  ((camera.x + camera.width) *1.0 / sceneWidth));
        int sy2 = (int)(background.getHeight(this) *  ((camera.y + camera.height) *1.0 / sceneHeight));

        // Clear last rendering
        bufferedGraphics.setColor(new Color(0, 0, 0));
        bufferedGraphics.fillRect(0, 0, width, height);
  
        // Paint section of background image onto entire surface of back-buffer
        bufferedGraphics.drawImage(background, 0, 0, width, height,
                        sx1, sy1, sx2, sy2, null);

        // Paint backbuffer onto graphics context of JPanel
        bufferedGraphics.drawImage(imageBuffer, 0, 0, width, height, null);
    }
    
    /***
     * 
     * @return scene to transition to
     */
    public Scene getNextScene() {
        return nextScene;
    }
    
    /***
     * Adds a named scene to the transitionToScene map, so that this scene knows what other scenes it can connect to.
     * 
     * @param sceneName name of other scene this can transition to
     * @param nextScene other scene this can transition to
     */
    public void transitionsTo(String sceneName, Scene nextScene) {
        transitionToScene.put(sceneName, nextScene);
    }
    
    /***
     * Sets the background image from the provided image path.
     * 
     * @param backgroundImagePath path to background image
     */
    public void setBackground(String backgroundImagePath){
        try{
            URL url = getClass().getResource(backgroundImagePath);
            background = tk.getImage(url);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Failed to load background Images. Program will close.");
            System.exit(-1);
        }
    }
    
    /***
     * 
     * @param nextScene scene to transition to
     */
    protected void setNextScene(String nextScene){
        this.nextScene = transitionToScene.get(nextScene);
        firstTimeInScene = false;
        isTransitioning = false;
    }
    
    /***
     * Used to stop reading keys until a little time in scene has passed.
     * This avoids rapid cycling between scenes.
     * 
     * @param entryDelay number of frames to wait
     */
    public void setEntryDelay(int entryDelay){
        this.entryDelay = entryDelay;
    }

    //------------------------ Accessors and Mutators ------------------------//
    
    /***
     * 
     * @return width of scene (width of map not of display)
     */
    public int getSceneWidth() {
        return sceneWidth;
    }

    /***
     * 
     * @param sceneWidth width of scene (width of map not of display)
     */
    public void setSceneWidth(int sceneWidth) {
        this.sceneWidth = sceneWidth;
    }

    /***
     * 
     * @return height of scene (height of map not of display)
     */
    public int getSceneHeight() {
        return sceneHeight;
    }

    /***
     * 
     * @param sceneHeight height of scene (height of map not of display)
     */
    public void setSceneHeight(int sceneHeight) {
        this.sceneHeight = sceneHeight;
    }

}