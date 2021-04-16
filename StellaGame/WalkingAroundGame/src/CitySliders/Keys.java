/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package CitySliders;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 *  Acts as a key listener. Stores information about which keys have been pressed or typed.
 */
public class Keys implements KeyListener{
    
    // Map keys to listen to to boolean values representing thier state
    HashMap<Integer, Boolean> keyPressed = new HashMap<Integer, Boolean>();
    HashMap<Integer, Boolean> keyTyped = new HashMap<Integer, Boolean>();
    
    /***
     * No-arg constructor.
     */
    public Keys(){
    }
    
    //------------------------ Functional Methods ------------------------//

    /***
     * Tells the keys class to listen for events for this key.
     * 
     * @param keyCode corresponding to a keyboard key
     */
    public void listenForKey(int keyCode){
        keyPressed.put(keyCode, Boolean.FALSE);
        keyTyped.put(keyCode, Boolean.FALSE);
    }
    
    /***
     * Checks if given key is being pressed.
     * 
     * @param keyCode corresponding to a keyboard key
     * @return true if key is being pressed
     */
    public boolean isKeyPressed(int keyCode){
        return keyPressed.get(keyCode);
    }
    
    /***
     * Checks if given key was previously typed. Resets value.
     * 
     * @param keyCode corresponding to a keyboard key
     * @return true if key was previously typed
     */
    public boolean isKeyTyped(int keyCode){
        boolean tmp = keyTyped.get(keyCode);
        keyTyped.put(keyCode, Boolean.FALSE);
        
        return tmp;
    }
    
    public void resetKeys(){
        for (HashMap.Entry<Integer, Boolean> entry : keyTyped.entrySet()){
            entry.setValue(false);
        }

        for (HashMap.Entry<Integer, Boolean> entry : keyPressed.entrySet()){
            entry.setValue(false);
        }
    }
  
    //---------------- Overriden Methods from KeyListener ----------------//
    
    /***
     * 
     * @param ke event that triggered this handler
     */
    @Override
    public void keyTyped(KeyEvent ke){
    }
    
    /***
     * Sets corresponding keyPressed boolean to true if pressed.
     * 
     * @param ke event that triggered this handler
     */
    @Override
    public void keyPressed(KeyEvent ke){
        if (keyPressed.containsKey(ke.getKeyCode())){
            keyPressed.put(ke.getKeyCode(), Boolean.TRUE);
        }
    }
    
    /***
     * Sets corresponding keyTyped boolean to true if released.
     * 
     * @param ke event that triggered this handler
     */
    @Override
    public void keyReleased(KeyEvent ke){
        if (keyPressed.containsKey(ke.getKeyCode())){
            keyPressed.put(ke.getKeyCode(), Boolean.FALSE);
            keyTyped.put(ke.getKeyCode(), Boolean.TRUE);
        }
    }
    
}