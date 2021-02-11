/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;

/**
 *  Sets up and manages the scenes. This includes updating and repainting the scenes,
 *  as well as managing transitions between scenes.
 */
public class SceneSelector extends JFrame implements Runnable {
    
    // Window Properties
    private final int WIDTH = 1000;
    private final int HEIGHT = 600;
    
    // Thread Properties
    private Thread gameLoop;
    private final int REFRESH_TIME = 1000 / 50;
    
    // Scenes
    private Scene splash;
    private Scene menu;
    private Scene game;
    private Scene controls;
    private Scene credits;
    
    private Scene currentScene;

    // Set Up A Key Listener
    Keys keys;
    
    /***
     * No-arg constructor.
     */
    public SceneSelector() {
        
        // Set Up Window Properties
        super("Adventures of Stella 1.0");
        setSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);  
        
        // Set Up Key Listener
        keys = new Keys();
        keys.listenForKey(KeyEvent.VK_W);   // Listen for W
        keys.listenForKey(KeyEvent.VK_A);   // Listen for A
        keys.listenForKey(KeyEvent.VK_S);   // Listen for S
        keys.listenForKey(KeyEvent.VK_D);   // Listen for D
        keys.listenForKey(KeyEvent.VK_P);   // Listen for P
        keys.listenForKey(KeyEvent.VK_O);   // Listen for O
        keys.listenForKey(KeyEvent.VK_I);   // Listen for I
        keys.listenForKey(KeyEvent.VK_U);   // Listen for U
        addKeyListener(keys);

        // Set Up Scenes
        splash = new SplashScene(new Dimension(WIDTH, HEIGHT), "assets/images/grass2.jpg", keys);       
        menu = new MenuScene(new Dimension(WIDTH, HEIGHT), "assets/images/menuBackground1.png", keys);
        controls = new ControlsScene(new Dimension(WIDTH, HEIGHT), "assets/images/controlsBackground1.png", keys);
        game = new GameScene(new Dimension(WIDTH, HEIGHT), "assets/images/grass3.jpg", keys);
        credits = new CreditsScene(new Dimension(WIDTH, HEIGHT), "assets/images/backgroundCredits.jpg", keys);
        
        // Create Scene Map
        splash.transitionsTo("menu", menu);
        menu.transitionsTo("game", game);
        menu.transitionsTo("credits", credits);
        menu.transitionsTo("controls", controls);
        game.transitionsTo("menu", menu);
        controls.transitionsTo("menu", menu);

        // Set current scene to splash
        currentScene = splash;
        add(currentScene);

        revalidate();
        repaint();

        // Start the gameloop
        gameLoop = new Thread(this);
        gameLoop.start();
    }
    
    /***
     * Implements the game loop by repeatedly updating and repainting the current scene.
     */
    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        while (currentThread == gameLoop) {
            try {
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace(System.out);
            }
            update();
            repaint();
        }
    }
    
    /***
     * Updates scene and then switches current scene to next if necessary.
     */
    private void update() {
        currentScene.update();
        
        // Check if scene is transitioning at make the switch
        if (currentScene != currentScene.getNextScene()){
            Scene nextScene = currentScene.getNextScene();
            nextScene.setEntryDelay(25);
            remove(currentScene);
            currentScene = nextScene;
            add(currentScene);
        }
        revalidate();
    }
    
}