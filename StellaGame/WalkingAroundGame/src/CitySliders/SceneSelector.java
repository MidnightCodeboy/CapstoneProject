/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package CitySliders;

import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;

/**
 *  Sets up and manages the scenes. This includes updating and repainting the scenes,
 *  as well as managing transitions between scenes.
 */
public class SceneSelector extends JFrame implements Runnable {
    
    // Window Properties
    private final int WIDTH = 1500;
    private final int HEIGHT = 1000;
    
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
    
    // Set up Sounds
    SoundClipper sounds;
    
    /***
     * No-arg constructor.
     */
    public SceneSelector() {
        
        // Set Up Window Properties
        super("CitySliders 1.0");
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
        keys.listenForKey(KeyEvent.VK_Q);   // Listen for Q
        keys.listenForKey(KeyEvent.VK_I);   // Listen for I
        keys.listenForKey(KeyEvent.VK_U);   // Listen for U
        keys.listenForKey(KeyEvent.VK_R);   // Listen for R
        keys.listenForKey(KeyEvent.VK_UP);   // Listen for UP
        keys.listenForKey(KeyEvent.VK_DOWN);   // Listen for DOWN
        keys.listenForKey(KeyEvent.VK_LEFT);   // Listen for LEFT
        keys.listenForKey(KeyEvent.VK_RIGHT);   // Listen for RIGHT
        keys.listenForKey(KeyEvent.VK_ENTER);   // Listen for ENTER
        addKeyListener(keys);
        
        // Sounds
        sounds = new SoundClipper();
        sounds.registerSoundPath("Bach", "/CitySliders/assets/music/bach.wav");
        sounds.registerSoundPath("Slide", "/CitySliders/assets/sounds/slide.wav");
        sounds.registerSoundPath("LevelComplete", "/CitySliders/assets/sounds/levelComplete1.wav");
        sounds.registerSoundPath("Click", "/CitySliders/assets/sounds/click.wav");
        sounds.registerSoundPath("Fail", "/CitySliders/assets/sounds/fail.wav");
        sounds.registerSoundPath("Gong", "/CitySliders/assets/sounds/gong1.wav");
        sounds.registerSoundPath("GameComplete", "/CitySliders/assets/sounds/harp1.wav");
        
        sounds.createClip("Background");
        sounds.assignSoundToClip("Bach", "Background");
        sounds.createClip("Slide1");
        sounds.assignSoundToClip("Slide", "Slide1");
        sounds.createClip("LevelComplete");
        sounds.assignSoundToClip("LevelComplete", "LevelComplete");
        sounds.createClip("Click");
        sounds.assignSoundToClip("Click", "Click");
        sounds.createClip("Fail");
        sounds.assignSoundToClip("Fail", "Fail");
        sounds.createClip("Gong");
        sounds.assignSoundToClip("Gong", "Gong");
        sounds.createClip("GameComplete");
        sounds.assignSoundToClip("GameComplete", "GameComplete");
        

        // Set Up Scenes
        splash = new SplashScene(new Dimension(WIDTH, HEIGHT), "assets/images/splashBackground.png", keys, sounds);
        game = new GameScene(new Dimension(WIDTH, HEIGHT), "assets/images/background.png", keys, sounds);
        credits = new CreditsScene(new Dimension(WIDTH, HEIGHT), "assets/images/background.png", keys, sounds);
        
        // Create Scene Map
        splash.transitionsTo("game", game);
        game.transitionsTo("credits", credits);

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