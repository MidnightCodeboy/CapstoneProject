/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *  Subclass of Sprite. Creates a visual Object that can be placed in scene.
 *  This Sprite represents the controllable player.
 */
public class Player extends Sprite{
    
    // Number of updates between frame increments
    final int frameDelay = 3;
    
    // Health points
    private int lives = 5;
    
    // Direction player is facing, whether standing or running
    private enum DirectionFaced {NORTH , SOUTH, WEST, EAST, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST};
    private DirectionFaced currentlyFacing = DirectionFaced.SOUTH;
    
    /***
     * Constructs the Sprite.
     * 
     * @param x x-coord of centre point of sprite
     * @param y y-coord of centre point of sprite
     * @param width width of sprite
     * @param height height of sprite
     * @param orientation visual angle to paint Sprite on graphics context
     */
    public Player(int x, int y, int width, int height, double orientation){
        super(x, y, width, height, orientation, "assets/images/stella_walk.png");
        
        // Set Up Animations
        
        // Standing Animations
        Animation standingSouth = new Animation(frameDelay);
        standingSouth.addFrame(0, 0, 64, 64);
        
        Animation standingWest = new Animation(frameDelay);
        standingWest.addFrame(0, 64, 64, 64);
        
        Animation standingEast = new Animation(frameDelay);
        standingEast.addFrame(0, 128, 64, 64);
        
        Animation standingNorth = new Animation(frameDelay);
        standingNorth.addFrame(0, 192, 64, 64);
        
        Animation standingSouthEast = new Animation(frameDelay);
        standingSouthEast.addFrame(0, 256, 64, 64);
        
        Animation standingSouthWest = new Animation(frameDelay);
        standingSouthWest.addFrame(0, 320, 64, 64);
        
        Animation standingNorthWest = new Animation(frameDelay);
        standingNorthWest.addFrame(0, 384, 64, 64);
        
        Animation standingNorthEast = new Animation(frameDelay);
        standingNorthEast.addFrame(0, 448, 64, 64);
        
        // Walking South
        Animation walkingSouth = new Animation(frameDelay);
        walkingSouth.addFrame(0, 0, 64, 64);
        walkingSouth.addFrame(64, 0, 64, 64);
        walkingSouth.addFrame(128, 0, 64, 64);
        walkingSouth.addFrame(192, 0, 64, 64);
        
        // Walking West
        Animation walkingWest = new Animation(frameDelay);
        walkingWest.addFrame(0, 64, 64, 64);
        walkingWest.addFrame(64, 64, 64, 64);
        walkingWest.addFrame(128, 64, 64, 64);
        walkingWest.addFrame(192, 64, 64, 64);
        
        // Walking East
        Animation walkingEast = new Animation(frameDelay);
        walkingEast.addFrame(0, 128, 64, 64);
        walkingEast.addFrame(64, 128, 64, 64);
        walkingEast.addFrame(128, 128, 64, 64);
        walkingEast.addFrame(192, 128, 64, 64);
        
        // Walking North
        Animation walkingNorth = new Animation(frameDelay);
        walkingNorth.addFrame(0, 192, 64, 64);
        walkingNorth.addFrame(64, 192, 64, 64);
        walkingNorth.addFrame(128, 192, 64, 64);
        walkingNorth.addFrame(192, 192, 64, 64);
        
        // Walking South East
        Animation walkingSouthEast = new Animation(frameDelay);
        walkingSouthEast.addFrame(0, 256, 64, 64);
        walkingSouthEast.addFrame(64, 256, 64, 64);
        walkingSouthEast.addFrame(128, 256, 64, 64);
        walkingSouthEast.addFrame(192, 256, 64, 64);
        
        // Walking South West
        Animation walkingSouthWest = new Animation(frameDelay);
        walkingSouthWest.addFrame(0, 320, 64, 64);
        walkingSouthWest.addFrame(64, 320, 64, 64);
        walkingSouthWest.addFrame(128, 320, 64, 64);
        walkingSouthWest.addFrame(192, 320, 64, 64);
        
        // Walking North West
        Animation walkingNorthWest = new Animation(frameDelay);
        walkingNorthWest.addFrame(0, 384, 64, 64);
        walkingNorthWest.addFrame(64, 384, 64, 64);
        walkingNorthWest.addFrame(128, 384, 64, 64);
        walkingNorthWest.addFrame(192, 384, 64, 64);
        
        // Walking North East
        Animation walkingNorthEast = new Animation(frameDelay);
        walkingNorthEast.addFrame(0, 448, 64, 64);
        walkingNorthEast.addFrame(64, 448, 64, 64);
        walkingNorthEast.addFrame(128, 448, 64, 64);
        walkingNorthEast.addFrame(192, 448, 64, 64);
        
        // Add Animations to Sprite
        addAnimation("standingSouth", standingSouth);
        addAnimation("standingWest", standingWest);
        addAnimation("standingEast", standingEast);
        addAnimation("standingNorth", standingNorth);
        addAnimation("standingSouthEast", standingSouthEast);
        addAnimation("standingSouthWest", standingSouthWest);
        addAnimation("standingNorthWest", standingNorthWest);
        addAnimation("standingNorthEast", standingNorthEast);
        
        addAnimation("walkingSouth", walkingSouth);
        addAnimation("walkingWest", walkingWest);
        addAnimation("walkingEast", walkingEast);
        addAnimation("walkingNorth", walkingNorth);
        addAnimation("walkingSouthEast", walkingSouthEast);
        addAnimation("walkingSouthWest", walkingSouthWest);
        addAnimation("walkingNorthWest", walkingNorthWest);
        addAnimation("walkingNorthEast", walkingNorthEast);
        
        // Set Current Animation
        setCurrentAnimationAuto();
        
    }
    
    //------------------- Methods Inherited from Sprite ------------------//
    
    /***
     * Updates the information needed to decide which frame to display.
     */
    @Override
    public void update(){
        super.update();
        
        setCurrentlyFacing();
        
        setCurrentAnimationAuto();
    }
    
    /***
     * Paints current frame of animation to the graphics context of Scene.
     * 
     * @param g Graphics context of JPanel
     * @param camera Sprites paint themselves relative to position of camera
     */
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);
    }
    
    //------------------------ Functional Methods ------------------------//
    
    /***
     * Sets the direction the player is facing based on x and y components of velocity.
     */
    private void setCurrentlyFacing(){
        if (vx > 0 && vy == 0){
            currentlyFacing = DirectionFaced.EAST;
        } else if (vx < 0 && vy == 0){
            currentlyFacing = DirectionFaced.WEST;
        } else if (vx == 0 && vy > 0){
            currentlyFacing = DirectionFaced.SOUTH;
        } else if (vx == 0 && vy < 0){
            currentlyFacing = DirectionFaced.NORTH;
        } else if (vx < 0 && vy < 0){
            currentlyFacing = DirectionFaced.NORTHWEST;
        } else if (vx > 0 && vy < 0){
            currentlyFacing = DirectionFaced.NORTHEAST;
        } else if (vx < 0 && vy > 0){
            currentlyFacing = DirectionFaced.SOUTHWEST;
        } else if (vx > 0 && vy > 0){
            currentlyFacing = DirectionFaced.SOUTHEAST;
        } else {
            //currentlyFacing = DirectionFaced.SOUTH;
        }
    }
    
    /***
     * Decides which animation (ie south north etc) to use based on direction faced and speed.
     */
    private void setCurrentAnimationAuto(){
        if (isSpeedNonZero() && currentlyFacing == DirectionFaced.NORTH){
            currentAnimation = "walkingNorth";
        } else if(isSpeedNonZero() && currentlyFacing == DirectionFaced.SOUTH){
            currentAnimation = "walkingSouth";
        } else if(isSpeedNonZero() && currentlyFacing == DirectionFaced.EAST){
            currentAnimation = "walkingEast";
        } else if(isSpeedNonZero() && currentlyFacing == DirectionFaced.WEST){
            currentAnimation = "walkingWest";
        } else if(isSpeedNonZero() && currentlyFacing == DirectionFaced.NORTHEAST){
            currentAnimation = "walkingNorthEast";
        } else if(isSpeedNonZero() && currentlyFacing == DirectionFaced.NORTHWEST){
            currentAnimation = "walkingNorthWest";
        } else if(isSpeedNonZero() && currentlyFacing == DirectionFaced.SOUTHEAST){
            currentAnimation = "walkingSouthEast";
        } else if(isSpeedNonZero() && currentlyFacing == DirectionFaced.SOUTHWEST){
            currentAnimation = "walkingSouthWest";
        } else if(currentlyFacing == DirectionFaced.NORTH){
            currentAnimation = "standingNorth";
        } else if(currentlyFacing == DirectionFaced.SOUTH){
            currentAnimation = "standingSouth";
        } else if(currentlyFacing == DirectionFaced.EAST){
            currentAnimation = "standingEast";
        } else if(currentlyFacing == DirectionFaced.WEST){
            currentAnimation = "standingWest";
        } else if(currentlyFacing == DirectionFaced.NORTHEAST){
            currentAnimation = "standingNorthEast";
        } else if(currentlyFacing == DirectionFaced.NORTHWEST){
            currentAnimation = "standingNorthWest";
        } else if(currentlyFacing == DirectionFaced.SOUTHEAST){
            currentAnimation = "standingSouthEast";
        } else { // Southwest
            currentAnimation = "standingSouthWest";
        }
    }
    
    /***
     * Adds health points of given item of food to players health points.
     * 
     * @param itemOfFood item to be consumed
     */
    public void eatItem(Food itemOfFood){
        if (itemOfFood != null){
            this.lives += itemOfFood.getHealthValue();
        }
    }
    
    //------------------------- Utility Functions ------------------------//
    
    /***
     * 
     * @return true if vx or vy are non zero
     */
    private boolean isSpeedNonZero(){
        return vx != 0 || vy != 0;
    }
    
    //---------------------- Accessors and Mutators ----------------------//
    
    /***
     * 
     * @return health points of player
     */
    public int getLives() {
        return lives;
    }
    
    /***
     * 
     * @param lives health points of player
     */
    public void setLives(int lives) {
        this.lives = lives;
    }
    
}