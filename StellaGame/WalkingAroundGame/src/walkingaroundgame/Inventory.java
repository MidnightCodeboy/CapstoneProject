/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


/**
 *  Subclass of Sprite. Creates a visual Object that can be placed in scene.
 *  This Sprite represents players storage area where they can store picked up items.
 */
public class Inventory extends Sprite{
    
    // create list of items - null for empty slot
    protected Food[] items = {null, null, null, null, null, null, null, null, null, null};
    protected int currentItem = 0;
    
    // Stops sideward increments of currentItem moving too fast
    protected int timeOut = 0;
    
    // Load and store inventory items in database
    //protected InventoryItemsDB stellaDB = new InventoryItemsDB();

    /***
     * Constructs the Sprite.
     * 
     * @param x x-coord of centre point of sprite
     * @param y y-coord of centre point of sprite
     * @param width width of sprite
     * @param height height of sprite
     * @param orientation visual angle to paint Sprite on graphics context
     */
    public Inventory(int x, int y, int width, int height, double orientation) {
        super(x, y, width, height, orientation, "assets/images/inventoryBackground.png");
        
        // Load food items into inventory from database
        //items = stellaDB.getFoodItems();
    }
    
    //------------------ Inherited Methods from Sprite -------------------//
    
    /***
     * Updates state of sprite. Returns timeout to zero.
     */
    @Override
    public void update(){
        super.update();
        
        // Returns time out to zer so that another sidways movement can be made
        if (timeOut > 0){
            timeOut--;
        }
    }
    
    /***
     * Paints current state of inventory to graphics context of scene.
     * 
     * @param g Graphics context of JPanel
     * @param camera Sprites paint themselves relative to position of camera
     */
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);
        
        if (getIsVisible()){
            // Set up co-ordinates
            int startX = (int)(x) - camera.x - (int)(w * 0.50) + 11;
            int startY = (int)(y) - camera.y - (int)(h * 0.50) + 9;
                
            g.setColor(Color.BLUE);
            g.fillRect(startX + (int)(w * 0.1 * currentItem), startY, 90, 50);
            
            // Paint Items
            for (int i = 0; i < items.length; i++){
                Sprite item = items[i];
                if (item != null){
                    g.drawImage(item.getIcon(), startX + (int)(w * 0.1 * i) + 18, startY + 9, 40, 40, null);
                }
            }
        }
    }
    
    //------------------------ Functional Methods ------------------------//
    
    /***
     * If an empty slot exists in inventory, add item and updates database.
     * 
     * @param item food item to be added to inventory
     * @return true if item was successfully added
     */
    public boolean addItem(Food item){
        for (int i = 0; i < items.length; i++){
            if (items[i] == null){
                items[i] = item;
                //stellaDB.writeFoodsToDB(items);
                return true;
            }
        }
        return false;
    }
    
    /***
     * Removes given item from inventory and returns it. Updates the database to reflect change.
     * 
     * @param itemIndex item to be removed from inventory
     * @return item removed from inventory
     */
    public Food removeItem(int itemIndex){
        Food returnItem = null;
        
        if (itemIndex < items.length && itemIndex >= 0){
            returnItem = items[itemIndex];
            items[itemIndex] = null;
            //stellaDB.writeFoodsToDB(items);
        }
        
        return returnItem;
    }
    
    /***
     * Removes current item from inventory and returns it. Updates the database to reflect change.
     * 
     * @return removed (current) item
     */
    public Food removeItem(){
        return removeItem(currentItem);
    }
    
    /***
     * Moves current item to the next-left item in inventory (wrapping at ends).
     */
    public void moveCurrentItemLeft(){
        if (timeOut == 0){
            currentItem--;
            currentItem += items.length;
            currentItem %= items.length;
            timeOut = 5;
        }
    }
    
    /***
     * Moves current item to the next-right item in inventory (wrapping at ends).
     */
    public void moveCurrentItemRight(){
        if (timeOut == 0){
            currentItem++;
            currentItem %= items.length;
            timeOut = 5;
        }
    }
   
}
