/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

import java.awt.EventQueue;

/**
 *  Entry point for the Game. Creates an object of the game and passes it to the event queue.
 */
public class Source {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Runnable runner = new Runnable(){
            @Override
            public void run(){
                new SceneSelector();
            }
        };
        EventQueue.invokeLater(runner);
    }
    
}
