/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *  Creates a visual object that can be placed in the scene.
 */
public class Sprite {
    
    // Spacial characteristics
    protected double x, y, w, h;
    protected double vx, vy, ax, ay;
    protected double orientation = 0;
    protected double angularVelocity = 0;
    protected double angularAcceleration = 0;
    
    // Temporal Characteristics
    protected int animationCounter;
    protected HashMap<String, Animation> animations = new HashMap<String, Animation>();
    protected String currentAnimation;

    // Physical Attributes 
    protected boolean isVisible = true;
    protected boolean isEnabled = true;
    protected boolean isSolid = false;
    protected boolean showingBoundingBox = false;

    // bounding box for detecting contact
    protected Rectangle boundingBox = new Rectangle(0, 0, 10, 10);
    
    //toolkit for loading images
    protected Toolkit toolkit;
    protected String pathToSpriteSheet;
    protected Image spriteSheet;
    
    /***
     * Constructs the sprite
     * 
     * @param x x-coord of centre point of sprite
     * @param y y-coord of centre point of sprite
     * @param width width of sprite
     * @param height height of sprite
     * @param orientation visual angle to paint Sprite on graphics context
     * @param pathToSpriteSheet file path to image file
     */
    public Sprite(int x, int y, int width, int height, double orientation, String pathToSpriteSheet) {
        setSize(width, height);
        setPosition(x, y);
        setVelocity(0, 0);
        setAcceleration(0, 0);
        setOrientation(orientation);
        setAngularVelocity(0);
        
        toolkit = Toolkit.getDefaultToolkit();
        setSpriteSheet(pathToSpriteSheet);

    }

    //--------------------------- Update and Paint ---------------------------//
    
    /***
     * Updates the state of the sprite.
     */
    public void update(){       
        
        // Only update if sprite is enabled
        if (isEnabled){
            
            // Update Kinetic Properties
            setPosition((int)(x + vx), (int)(y + vy));
            vx += ax;
            vy += ay;
            setOrientation(orientation + angularVelocity);
            setAngularVelocity(angularVelocity + angularAcceleration);
            
            // Safely increment the animation counter
            animationCounter++;
            animationCounter %= Integer.MAX_VALUE;
            
            // progress current Animation if animation counter % animation.delay == 0
            if (!animations.isEmpty()){
                if (animationCounter % animations.get(currentAnimation).getFrameDelay() == 0){
                    animations.get(currentAnimation).advanceFrame();
                }
            }
        }
    }
    
    /***
     * Paints current frame of animation to the graphics context of Scene.
     * 
     * @param g Graphics context of JPanel
     * @param camera Sprites paint themselves relative to position of camera
     */
    public void paint(Graphics2D g, Rectangle camera){
        
        // Only paint if sprite is enabled
        if(isVisible){
            
            // Handle Rotation
            AffineTransform oldAT = g.getTransform();
            g.rotate(Math.toRadians(orientation),x, y);
            
            // Get camera x and y
            int cx = camera.x;
            int cy = camera.y;

            // If there is an animation, print frame. Otherwise whole sprite sheet
            if (!animations.isEmpty()){
                int sx1 = animations.get(currentAnimation).getX();
                int sy1 = animations.get(currentAnimation).getY();
                int sx2 = animations.get(currentAnimation).getX() + animations.get(currentAnimation).getWidth(); // 
                int sy2 = animations.get(currentAnimation).getY() + animations.get(currentAnimation).getHeight(); // 
                
                g.drawImage(spriteSheet, (int) (x - 0.5 * w) - cx, (int) (y - 0.5 * h) - cy, (int) (x + 0.5 * w) - cx, (int) (y + 0.5 * h) - cy,
                        sx1, sy1, sx2, sy2, null);
                
            } else {
                // Draw the whole sprite sheet
                g.drawImage(spriteSheet, (int) (x - 0.5 * w) - cx, (int) (y - 0.5 * h) - cy, (int) w, (int)h, null);
            }
            
            // Return rotation of graphics context to normal
            g.setTransform(oldAT);
            
            // Displays the bounding box if enabled
            if(showingBoundingBox){
                Rectangle relativeBoundingBox = new Rectangle((int)(boundingBox.x - cx), (int)(boundingBox.y - cy), (int)(0.7 * w), (int)(0.7 * h));
                g.draw(relativeBoundingBox);
            }        
        }        
    }
    
    //------------------------ Accessors and Mutators ------------------------//
    
    /***
     * Sets the dimensions of the sprite.
     * 
     * @param width width of sprite
     * @param height height of sprite
     */
    public void setSize(int width, int height) {
        w = width;
        h = height;
        
        setBoundingBox(new Rectangle((int)(x-0.35*w), (int)(y-0.35*h), (int)w, (int)h));
    }

    /***
     * 
     * @return width of sprite
     */
    public int getWidth() {
        return (int)w;
    }
    
    /***
     * 
     * @return height of sprite
     */
    public int getHeight() {
        return (int)h;
    }

    /***
     * Sets location of sprite
     * 
     * @param x x-coord of centre of sprite
     * @param y y-coord of centre of sprite
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        
        setBoundingBox(new Rectangle((int)(x-0.35*w), (int)(y-0.35*h), (int)(0.7 * w), (int)(0.7 * h)));
    }
    
    /***
     * 
     * @return x-coord of centre of sprite
     */
    public int getX() {
        return (int)x;
    }
    
    /***
     * 
     * @return y-coord of centre of sprite
     */
    public int getY() {
        return (int)y;
    }
    
    /***
     * Sets velocity of sprite.
     * 
     * @param vx x component of the velocity of sprite
     * @param vy y component of the velocity of sprite
     */
    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }
    
    /***
     * 
     * @return x component of velocity of sprite
     */
    public double getVX() {
        return vx;
    }
    
    /***
     * 
     * @return y component of velocity of sprite
     */
    public double getVY() {
        return vy;
    }
    
    /***
     * Sets acceleration of sprite
     * 
     * @param ax x component of acceleration of sprite
     * @param ay y component of acceleration of sprite
     */
    public void setAcceleration(double ax, double ay) {
        this.ax = ax;
        this.ay = ay;
    }
    
    /***
     * 
     * @return x component of acceleration of sprite
     */
    public double getAX() {
        return ax;
    }
    
    /***
     * 
     * @return y component of acceleration of sprite
     */
    public double getAY() {
        return ay;
    }
    
    /***
     * Sets angular velocity of sprite
     * 
     * @param angularVelocity 
     */
    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }
    
    /***
     * 
     * @return angular velocity of sprite
     */
    public double getAngularVelocity() {
        return angularVelocity;
    }
    
    /***
     * Sets angular acceleration of sprite
     * 
     * @param angularAcceleration 
     */
    public void setAngularAcceleration(double angularAcceleration) {
        this.angularAcceleration = angularAcceleration;
    }
    
    /***
     * 
     * @return angular acceleration of sprite
     */
    public double getAngularAcceleration() {
        return angularAcceleration;
    }
    
    /***
     * Sets orientation of sprite.
     * 
     * @param orientation 
     */
    public void setOrientation(double orientation) {
        this.orientation = orientation % 360;
    }
    
    /***
     * 
     * @return orientation of sprite
     */
    public double getOrientation() {
        return orientation;
    }
    
    //--------------------- Physical Properties ---------------------//

    /***
     * 
     * @param isVisible whether sprite is visible
     */
    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /***
     * 
     * @return whether sprite is visible
     */
    public boolean getIsVisible() {
        return isVisible;
    }

    /***
     * 
     * @param enabled whether sprite is enabled
     */
    public void setIsEnabled(boolean enabled){
        isEnabled = enabled;
    }

    /***
     * 
     * @return whether sprite is enabled
     */
    public boolean getIsEnabled() {
        return isEnabled;
    }
    
    /***
     * 
     * @param solid whether sprite is solid
     */
    public void setIsSolid(boolean solid){
        isSolid = solid;
    }

    /***
     * 
     * @return whether sprite is solid
     */
    public boolean getIsSolid() {
        return isSolid;
    }

    //--------------------- Collision Detection ---------------------//
    
    /***
     * 
     * @return bounding box for this sprite
     */
    public Rectangle getBoundingBox() {
        return boundingBox;
    }
    
    /***
     * 
     * @param boundingBox bounding box for this sprite
     */
    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    /***
     * Toggles whether bounding box is shown
     */
    public void showBoundingBox() {
        if(showingBoundingBox)
            showingBoundingBox = false;
        else
            showingBoundingBox = true;           
    }

    /***
     * Checks if the bounding boxes of the two sprites overlap.
     * 
     * @param otherSprite sprite to check against this
     * @return whether bounding boxes overlap
     */
    public boolean collidesWith(Sprite otherSprite){
        // Get Other Sprite Corners
        int ox = otherSprite.getBoundingBox().x;
        int oy = otherSprite.getBoundingBox().y;
        int ow = otherSprite.getBoundingBox().width;
        int oh = otherSprite.getBoundingBox().height;

        // Get corner of other sprite
        Point[] otherSpriteCorners = {
            new Point(ox, oy),
            new Point(ox + ow, oy),
            new Point(ox + ow, oy + oh),
            new Point(ox, oy + oh)        
        };
        
        // Get corners of this sprite
        Point[] thisSpriteCorners = {
            new Point(boundingBox.x, boundingBox.y),
            new Point(boundingBox.x, boundingBox.y + boundingBox.height),
            new Point(boundingBox.x + boundingBox.width, boundingBox.y),
            new Point(boundingBox.x + boundingBox.width, boundingBox.y + boundingBox.height)
        };
        
        // Check other sprite corners inside this sprite bounding box
        for (Point p : otherSpriteCorners){
            if(boundingBox.contains(p)){
                return true;
            }
        }
        
        // Check this sprite corners inside other sprite bounding box
        for (Point p : thisSpriteCorners){
            if(otherSprite.getBoundingBox().contains(p)){
                return true;
            }
        }
        
        return false;
    }
 
    //-------------------------- Animation --------------------------//
    
    /***
     * 
     * @param pathToSpriteSheet path to set for the sprite sheet file
     */
    public void setSpriteSheet(String pathToSpriteSheet){
        try{
           URL url = getClass().getResource(pathToSpriteSheet);
            spriteSheet = toolkit.getImage(url);
            this.pathToSpriteSheet = pathToSpriteSheet;
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Failed to load Sprite Images. Program will close.");
            System.exit(-1);
        }
        
    }
    
    /***
     * Adds a named animation object to the animations map.
     * 
     * @param name name of animation to add
     * @param animation animation to add
     */
    public void addAnimation(String name, Animation animation){
        animations.put(name, animation);
    }
    
    /***
     * 
     * @param name name of animation in the animations map
     */
    public void setCurrentAnimation(String name){
        if( animations.containsKey(name)){
            currentAnimation = name;
        }
    }
    
    /***
     * 
     * @return an image representation of this sprite
     */
    public Image getIcon(){
        return spriteSheet;
    }
    
    /***
     * 
     * @return file path for sprite sheet
     */
    public String getPathToSpriteSheet(){
        return pathToSpriteSheet;
    }

}