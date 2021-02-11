/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

/**
 *  Subclass of Sprite. Creates a visual Object that can be placed in scene.
 *  This Sprite represents a food item that the player can eat. Gives +ve or -ve health to player.
 */
public class Food extends Sprite{
    
    // Health points to be applied to players health when consumed. (-ve or +ve)
    private int healthValue = 0;

    /***
     * Constructs the Sprite.
     * 
     * @param x x-coord of centre point of sprite
     * @param y y-coord of centre point of sprite
     * @param width width of sprite
     * @param height height of sprite
     * @param orientation visual angle to paint Sprite on graphics context
     * @param pathToSpriteSheet file path to image file
     */
    public Food(int x, int y, int width, int height, double orientation, String pathToSpriteSheet) {
        super(x, y, width, height, orientation, pathToSpriteSheet);
    }
    
    //---------------------- Accessors and Mutators ----------------------//
    
    /***
     * 
     * @return healthValue of this item
     */
    public int getHealthValue() {
        return healthValue;
    }
    
    /***
     * 
     * @param healthValue health value of this item
     */
    public void setHealthValue(int healthValue) {
        this.healthValue = healthValue;
    }
    
}