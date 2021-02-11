/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *  This class mediates between the game and the database to facilitate persisting the inventory.
 */
public class InventoryItemsDB {

    /***
     * No-arg Constructor
     */
    public InventoryItemsDB() {
    }

    /***
     * Pulls records from the database and uses the to build a list of Food items.
     * 
     * @return an array of Food objects built using information retrieved from the database
     */
    public Food[] getFoodItems(){
        
        // List of Food objects from database. null for empty slots
        Food[] foods = {null, null, null, null, null, null, null, null, null, null};
        
        // Database Login Properties
        String url = "jdbc:mysql://localhost/adventuresofstella";
        String userName = "root";
        String password = "1234";//"9705940pncc1701A";
        
        // Connect to database
        try (Connection conn = DriverManager.getConnection(url, userName, password)){
            // Create statement
            try (Statement stmt = conn.createStatement()){
                String sql = "SELECT * FROM InventoryItem";
                // Get result set
                try (ResultSet rs = stmt.executeQuery(sql)){
                    if (!rs.isBeforeFirst()){
                    } else {
                        Food f; // Temporary handle for Food item to be stored in list
                        int i = 0;
                        while (rs.next()){
                            // Set properties for food item
                            int x = rs.getInt("X");
                            int y = rs.getInt("Y");
                            int width = rs.getInt("Width");
                            int height = rs.getInt("Height");
                            double orientation = rs.getDouble("Orientation");
                            String pathToSpriteSheet = rs.getString("PathToSpriteSheet");
                            int healthValue = rs.getInt("HealthValue");
                            
                            // Create the food item
                            f = new Food(x, y, width, height, orientation, pathToSpriteSheet);
                            f.setHealthValue(healthValue);
                      
                            // Add item to list
                            if (i < foods.length){
                                foods[i++] = f;
                            }
                        }
                    }
                } catch (SQLException sqle){
                    JOptionPane.showMessageDialog(null, "Couldn't retrieve inventory items from database");
                }
            } catch (SQLException sqle){
                JOptionPane.showMessageDialog(null, "Couldn't retrieve inventory items from database");
            }
        } catch (SQLException sqle){
            JOptionPane.showMessageDialog(null, "Couldn't retrieve inventory items from database");
        }

        // If bad connection to DB it will just be empty
        return foods;
    }
    
    /***
     * Updates database with records based on the properties of the food items in the given list.
     * 
     * @param listOfFoodItems items to be written to database
     */
    public void writeFoodsToDB(Food[] listOfFoodItems){
        
        // Database Login Properties
        String url = "jdbc:mysql://localhost/adventuresofstella";
        String userName = "root";
        String password = "9705940pncc1701A";
        
        // Connect to database
        try (Connection conn = DriverManager.getConnection(url, userName, password)){
            // Create statement
            try(Statement stmnt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE)){
                
                // Flatten Table
                String sqlTruncate = "TRUNCATE TABLE InventoryItem;";
                int recordCount = stmnt.executeUpdate(sqlTruncate);
                
                // Check if foods list is empty
                int nonNullItems = 0;
                for (Food f : listOfFoodItems){
                    if (f != null){
                        nonNullItems++;
                    }
                }

                // Add items to DB
                // If inventory is not empty then add items to database
                if (nonNullItems > 0){
                    String sqlAddItems = "INSERT INTO InventoryItem ("
                        + "ID, X, Y, Width, Height, Orientation, PathToSpriteSheet, HealthValue) VALUES ";
                
                    // Build list of items for insertion
                    int i = 1;
                    for (Food f : listOfFoodItems){
                        if (f != null){
                            
                            // get elements of the record from the food item properties
                            int ID = i;
                            int x = f.getX();
                            int y = f.getY();
                            int width = f.getWidth();
                            int height = f.getHeight();
                            double orientation = f.getOrientation();
                            String pathToSpriteSheet = f.getPathToSpriteSheet();
                            int healthValue = f.getHealthValue();

                            // Add record info to quiery
                            sqlAddItems += "(" + ID + ", " +
                                    x + ", " +
                                    y + ", " +
                                    width + ", " +
                                    height + ", " +
                                    orientation + ", \"" +
                                    pathToSpriteSheet + "\", " +
                                    healthValue + ")";

                            // add commas
                            if (i++ < nonNullItems){
                                sqlAddItems += ", ";
                            }
                        }                      
                    }
                
                    sqlAddItems += "; ";
                    
                    // Send update to database
                    recordCount = stmnt.executeUpdate(sqlAddItems);
                }
            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(null, "Couldn't add inventory items yo database");
            }
        } catch (SQLException sqle){
            JOptionPane.showMessageDialog(null, "Couldn't add inventory items to database");
        }
    }
}
