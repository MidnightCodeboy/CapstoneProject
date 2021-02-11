/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *  Subclass of Scene. Creates an interactive environment that with a map and a player to move around the scene.
 *  Enforces game logic like collision detection etc. This is the main part of the Application.
 */
public class GameScene extends Scene{
    
    Player stella;      // The user-controllable player character

    ArrayList<Sprite> mobs = new ArrayList();   // List of trees and rocks
    ArrayList<Food> foods = new ArrayList();    // list of pizzas and snakes
    
    // Inventory used by player to store picked up items
    Inventory inventory;
    int inventoryButtonTimeOut = 0; // Delay so that left-right movement in inventory is not too fast
    
    /***
     * Constructor.
     * 
     * @param size Dimensions of the window
     * @param backgroundImagePath path to the background image file
     * @param keys keys object, used to capture user keystrokes
     */
    public GameScene(Dimension size, String backgroundImagePath, Keys keys){
        super(size, backgroundImagePath, keys);
        
        setSceneWidth(1500);
        setSceneHeight(1000);

        stella = new Player(sceneWidth / 2 , sceneHeight / 2 , 100, 100, 0);
        //stella.showBoundingBox();
        
        // Create trees
        mobs.add(new Sprite((int)(sceneWidth * 0.1), (int)(sceneHeight * 0.1), 100, 150, 0, "assets/images/tree1.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.1), (int)(sceneHeight * 0.4), 100, 150, 0, "assets/images/tree2.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.3), (int)(sceneHeight * 0.2), 100, 150, 0, "assets/images/tree1.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.2), (int)(sceneHeight * 0.7), 100, 150, 0, "assets/images/tree2.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.7), (int)(sceneHeight * 0.8), 100, 150, 0, "assets/images/tree1.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.8), (int)(sceneHeight * 0.9), 100, 150, 0, "assets/images/tree2.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.9), (int)(sceneHeight * 0.7), 100, 150, 0, "assets/images/tree1.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.8), (int)(sceneHeight * 0.3), 100, 150, 0, "assets/images/tree2.png"));
        
        // Create Rocks
        mobs.add(new Sprite((int)(sceneWidth * 0.1), (int)(sceneHeight * 0.8), 100, 100, 0, "assets/images/rock1.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.2), (int)(sceneHeight * 0.9), 100, 100, 0, "assets/images/rock2.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.4), (int)(sceneHeight * 0.5), 100, 100, 0, "assets/images/rock3.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.5), (int)(sceneHeight * 0.3), 100, 100, 0, "assets/images/rock1.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.5), (int)(sceneHeight * 0.7), 100, 100, 0, "assets/images/rock2.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.6), (int)(sceneHeight * 0.5), 100, 100, 0, "assets/images/rock3.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.8), (int)(sceneHeight * 0.1), 100, 100, 0, "assets/images/rock1.png"));
        mobs.add(new Sprite((int)(sceneWidth * 0.9), (int)(sceneHeight * 0.2), 100, 100, 0, "assets/images/rock2.png"));

        // Creat Food Items
        foods.add(new Food((int)(sceneWidth * 0.1), (int)(sceneHeight * 0.2), 50, 50, 0, "assets/images/pizza.png"));
        foods.add(new Food((int)(sceneWidth * 0.2), (int)(sceneHeight * 0.4), 50, 50, 0, "assets/images/pizza.png"));
        foods.add(new Food((int)(sceneWidth * 0.1), (int)(sceneHeight * 0.9), 50, 50, 0, "assets/images/pizza.png"));
        foods.add(new Food((int)(sceneWidth * 0.5), (int)(sceneHeight * 0.1), 50, 50, 0, "assets/images/pizza.png"));
        foods.add(new Food((int)(sceneWidth * 0.5), (int)(sceneHeight * 0.9), 50, 50, 0, "assets/images/pizza.png"));
        foods.add(new Food((int)(sceneWidth * 0.8), (int)(sceneHeight * 0.6), 50, 50, 0, "assets/images/pizza.png"));
        foods.add(new Food((int)(sceneWidth * 0.9), (int)(sceneHeight * 0.9), 50, 50, 0, "assets/images/pizza.png"));
        foods.add(new Food((int)(sceneWidth * 0.9), (int)(sceneHeight * 0.1), 50, 50, 0, "assets/images/pizza.png"));
        
        // Assign random health values to food items in the range 1 - 3
        for (Food f : foods){
            f.setHealthValue((int)(Math.random() * 3) + 1);
            //m.showBoundingBox();
        }
        
        // Set trees and rocks solid
        for (Sprite m : mobs){
            m.setIsSolid(true);
            //m.showBoundingBox();
        }
        
        // Create poison items
        for (int i = 0; i < 4; i++){
            Food snake = new Food(0, 0, 50, 50, 0, "assets/images/snake.png");
            snake.setHealthValue(-4);
            foods.add(snake);
        }
        
        // Place Food items on the map
        foods.get(foods.size() - 1).setPosition((int)(sceneWidth * 0.15), (int)(sceneHeight * 0.3));
        foods.get(foods.size() - 2).setPosition((int)(sceneWidth * 0.05), (int)(sceneHeight * 0.8));
        foods.get(foods.size() - 3).setPosition((int)(sceneWidth * 0.85), (int)(sceneHeight * 0.3));
        foods.get(foods.size() - 4).setPosition((int)(sceneWidth * 0.85), (int)(sceneHeight * 0.8));
        
        // Set up inventory - Initially invisible
        inventory = new Inventory(width / 2, height /2, width, height / 9, 0);
        inventory.setIsVisible(false);

    }
    
    @Override
    public void update() {
        super.update();
        
        // Allows left-right delay to go to zero so that currentItem can shift again
        if (inventoryButtonTimeOut > 0){
            inventoryButtonTimeOut--;
        }
        
        // update Stella
        stella.update();
        camera.setLocation(stella.getX() - (int)(0.5 * width), stella.getY() - (int)(0.5 * height));
        
        // Update food (including poison)
        for (Food f : foods){
            f.update();
        }
        
        // Update Mobs
        for (Sprite m : mobs){
            m.update();
        }
        
        // Update inventory
        inventory.setPosition(camera.x + (int)(camera.width * 0.5), camera.y + (int)(camera.height * 0.88));
        inventory.update();
        
        // Respond to keyboard input
        if (entryDelay == 0) { // wait before listening for keys.. stops rapid cycling between scenes.
            
            // GOTO Menu Scene
            if (keys.isKeyPressed(KeyEvent.VK_P)) {
            setNextScene("menu");
            }
            
            // Toggle Inventory Visible
            if (keys.isKeyPressed(KeyEvent.VK_I) && inventoryButtonTimeOut == 0){
                inventoryButtonTimeOut = 10; // wait before shifting again - stops rapid cycling
                
                if (inventory.getIsVisible()){ // true
                    inventory.setIsVisible(false);
                } else{ // false
                    inventory.setIsVisible(true);
                }
            }

            // If In Inventory State
            if (inventory.getIsVisible()){
                if (keys.isKeyPressed(KeyEvent.VK_A)){ // Move selection left
                    inventory.moveCurrentItemLeft();
                } else if (keys.isKeyPressed(KeyEvent.VK_D)){ // Move selection right
                    inventory.moveCurrentItemRight();
                } else if (keys.isKeyPressed(KeyEvent.VK_U)){ // Eat item
                    stella.eatItem(inventory.removeItem());
                } else if (keys.isKeyPressed(KeyEvent.VK_O)){ // Drop item
                    Food item = inventory.removeItem();
                    if (item != null){
                        item.setPosition(stella.getX() + 50, stella.getY() + 50);
                        item.setIsVisible(true);
                        item.setIsEnabled(true);
                        foods.add(item);
                    }
                }
                
            // If in Non-Inventory State
            } else {
                
                // Set Stellas Velocity
                if (keys.isKeyPressed(KeyEvent.VK_W) && keys.isKeyPressed(KeyEvent.VK_D)){
                    stella.setVelocity(2, -2);
                } else if (keys.isKeyPressed(KeyEvent.VK_D) && keys.isKeyPressed(KeyEvent.VK_S)){
                    stella.setVelocity(2, 2);
                } else if (keys.isKeyPressed(KeyEvent.VK_A) && keys.isKeyPressed(KeyEvent.VK_S)){
                    stella.setVelocity(-2, 2);
                } else if (keys.isKeyPressed(KeyEvent.VK_A) && keys.isKeyPressed(KeyEvent.VK_W)){
                    stella.setVelocity(-2, -2);
                } else if (keys.isKeyPressed(KeyEvent.VK_W)){
                    stella.setVelocity(0, -2);
                } else if (keys.isKeyPressed(KeyEvent.VK_D)){
                    stella.setVelocity(2, 0);
                } else if (keys.isKeyPressed(KeyEvent.VK_S)){
                    stella.setVelocity(0, 2);
                } else if (keys.isKeyPressed(KeyEvent.VK_A)){
                    stella.setVelocity(-2, 0);
                } else { // not moving
                    stella.setVelocity(0, 0);
                }
                
                // Collision Detection for food items
                ArrayList<Food> itemsToRemove = new ArrayList<>();
                for (Food f : foods){
                    if (stella.collidesWith(f) && f.getIsEnabled()){

                        // Stella decides whether to eat item or store in inventory
                        if (keys.isKeyPressed(KeyEvent.VK_O)){ // Storing Item in Inventory
                            boolean itemStored = inventory.addItem(f);
                            if (itemStored){
                                itemsToRemove.add(f);
                            }
                        } else if (keys.isKeyPressed(KeyEvent.VK_U)) { // Eating item
                            stella.eatItem(f);
                            itemsToRemove.add(f);
                        }
                        // Else ignoring item
                    }
                }
                
                // Remove consumed items
                for (Food f : itemsToRemove){
                    foods.remove(f);
                }
                itemsToRemove.clear();
            }
        }

        // Collision Detection for mobs
        for (Sprite m : mobs){
            if (stella.collidesWith(m)){
                // Get Info to decide which side of m the collision occurred on (top, bottom, left, right)
                int xDif = stella.getX() - m.getX();
                int yDif = stella.getY() - m.getY();
                
                // Get information to decide bounce-back distance
                int sw = stella.getBoundingBox().width;
                int sh = stella.getBoundingBox().height;
                int mw = m.getBoundingBox().width;
                int mh = m.getBoundingBox().height;
                
                // Decide which side the collision occurred on and bounce stella back
                if (yDif > 0 && Math.abs(yDif) > Math.abs(xDif)){ // Top Side Collision
                    stella.setPosition(stella.getX(), m.getY() + (int)(0.5 * (sh + mh)) + 3);
                    stella.setVelocity(stella.getVX(), 0);
                } else if (yDif < 0 && Math.abs(yDif) > Math.abs(xDif)){ // Bottom Side Collision
                    stella.setPosition(stella.getX(), m.getY() - (int)(0.5 * (sh + mh)) -3);
                    stella.setVelocity(stella.getVX(), 0);
                }else if (xDif > 0 && Math.abs(xDif) > Math.abs(yDif)){ // Right Side Collision
                    stella.setPosition(m.getX() + (int)(0.5 * (sw + mw)) + 3, stella.getY());
                    stella.setVelocity(0, stella.getVY());
                }else if (xDif < 0 && Math.abs(xDif) > Math.abs(yDif)){ // Left Side Collision
                    stella.setPosition(m.getX() - (int)(0.5 * (sw + mw)) - 3, stella.getY());
                    stella.setVelocity(0, stella.getVY());
                }
            }
        }

        // Collision detection for scene boundaries
        if (stella.getX() >= sceneWidth){ // West side collision
            stella.setPosition(sceneWidth - 5, stella.getY());
            stella.setVelocity(0, stella.getVY());
        } else if (stella.getX() <= 0){ // East side collision
            stella.setPosition(5, stella.getY());
            stella.setVelocity(0, stella.getVY());
        }
        
        if (stella.getY() >= sceneHeight){ // South side collision
            stella.setPosition(stella.getX(), sceneHeight - 5);
            stella.setVelocity(stella.getVX(), 0);
        } else if (stella.getY() <= 0){ // North side collision 
            stella.setPosition(stella.getX(), 5);
            stella.setVelocity(stella.getVX(), 0);
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
        
        // Paint mobs
        for (Sprite m : mobs){
            m.paint(bufferedGraphics, camera);
        }
        
        // Paint food items
        for (Food f : foods){
            f.paint(bufferedGraphics, camera);
        }
        
        // paint Stella
        stella.paint(bufferedGraphics, camera);

        // Paint Lives
        bufferedGraphics.setColor(Color.RED);
        bufferedGraphics.setFont(new Font("Arial", Font.BOLD, 40));
        for (int i = 0; i < stella.getLives(); i++){
            bufferedGraphics.drawString("♥", 25 + i * 25, 30);
        }
        
        // Paint Inventory
        inventory.paint(bufferedGraphics, camera);

        g.drawImage(imageBuffer, 0, 0, width, height, null);
    }
    
}