/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package CitySliders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.Clip;

/**
 *  Subclass of Scene. Creates an interactive environment that with a map and a player to move around the scene.
 *  Enforces game logic like collision detection etc. This is the main part of the Application.
 */
public class GameScene extends Scene{
    
    //
    private final String CARDS_PATH = "aNewFile.txt";

    // Sprites
    private BackgroundEffect bg;
    private Clapper clapper;
    private Chrono clock;
    private TileBoard tileBoard;
    private Legend legend;
    private GoAwayButton GAButton;
    private Caroussel caroussel;
    
    //
    ArrayList<Card> cards;
    private int currentCardIndex;
    
    // Fonts
    private Font titleFont = new Font("Times New Roman", Font.BOLD, 80);
    
    // Colours
    private Color baseColor;
    private Color legendFill = new Color(255, 255, 255, 100);;
    
    private int boardHeight;
    private int boardWidth;
    
    // Scores
    private final int TIME_BONUS = 200;
    private final int TIME_PENALTY = 30; // Deducted from base for every 30 seconds overtime
    private final int TIME_BASE_SCORE = 200;
    private final int PER_TILE_SCORE = 30;
    private final int PER_RANDOMIZATION_STEP_SCORE = 10;
    
    
    // State
    private enum State {SETUP, SHUFFLE, PLAY, PAUSE, RESET, WOOHOO, EXIT}
    private State gameState = State.SETUP;
    private boolean isEnteringState = true;
    private boolean isGongable = false; // Has clock gong not been played yet?
    
    /***
     * Constructor.
     * 
     * @param size Dimensions of the window
     * @param backgroundImagePath path to the background image file
     * @param keys keys object, used to capture user keystrokes
     */
    public GameScene(Dimension size, String backgroundImagePath, Keys keys, SoundClipper sounds){
        super(size, backgroundImagePath, keys, sounds);
        
        setSceneWidth(1500);
        setSceneHeight(1000);
        sounds.play("Background", Clip.LOOP_CONTINUOUSLY);
        initializeSprites();
    }
    
    @Override
    public void update() {
        super.update();
        
        // GameFlow
        switch(gameState){
            case SETUP:
                if (isEnteringState){
                    // do some setup
                    initializeSprites();
                    
                    GAButton.setMessage("Welcome to Round " + (currentCardIndex + 1));
                    GAButton.setButtonText("Start Round");
                    GAButton.setIsListening(true);
                    
                    isEnteringState = false;
                } else {
                    // do stuff
                    if (keys.isKeyTyped(KeyEvent.VK_ENTER)) GAButton.setIsListening(false);
                    
                    // check for state transition criteria
                    if (!GAButton.isIsListening()){
                        sounds.play("Click", 1);
                        isEnteringState = true;
                        gameState = State.SHUFFLE;
                    }
                }
                break;
            case SHUFFLE:
                if (isEnteringState){
                    // do some setup
                    tileBoard.visualRandomize(cards.get(currentCardIndex).getRandomizationSteps());
                    
                    isEnteringState = false;
                } else {
                    // do stuff
                    
                    // check for state transition criteria
                    if (!tileBoard.isShuffling()){
                        isEnteringState = true;
                        gameState = State.PLAY;
                    }
                }
                break;
            case PLAY:
                if (isEnteringState){
                    // do some setup
                    clock.start();
                    //sounds.start("Background");
                    sounds.play("Background", Clip.LOOP_CONTINUOUSLY);
                    
                    isEnteringState = false;
                } else {
                    // do stuff
                    
                    // Listen for Tile Slides
                    boolean slide = false;
                    boolean slideSuccess = false;
                    if (keys.isKeyTyped(KeyEvent.VK_UP)){
                        slideSuccess = tileBoard.slide(TileBoard.Direction.UP);
                        slide = true;
                    } else if (keys.isKeyTyped(KeyEvent.VK_DOWN)){
                        slideSuccess = tileBoard.slide(TileBoard.Direction.DOWN);
                        slide = true;
                    } else if (keys.isKeyTyped(KeyEvent.VK_LEFT)){
                        slideSuccess = tileBoard.slide(TileBoard.Direction.LEFT);
                        slide = true;
                    } else if (keys.isKeyTyped(KeyEvent.VK_RIGHT)){
                        slideSuccess = tileBoard.slide(TileBoard.Direction.RIGHT);
                        slide = true;
                    }
                    
                    // play tile slide sound
                    if (slide){
                        if (slideSuccess){
                            sounds.play("Slide1", 1);
                        } else {
                            sounds.play("Fail", 1);
                        }
                        slide = false;
                    }
                    
                    // Play Clock Gong Sound
                    if (isGongable && clock.isOverTime()){
                        isGongable = false;
                        sounds.play("Gong", 1);
                    }
                    
                    
                    // check for state transition criteria
                    if (keys.isKeyTyped(KeyEvent.VK_R)){ // Reset Level
                        isEnteringState = true;
                        gameState = State.RESET;
                    }
                    
                    if (keys.isKeyTyped(KeyEvent.VK_P)){ // Pause screen
                        isEnteringState = true;
                        gameState = State.PAUSE;
                        
                        caroussel.setActive(true);
                    }
                    
                    if (tileBoard.isSolved()){ // Puzzle solved
                        isEnteringState = true;
                        gameState = State.WOOHOO;
                    }
                }
                break;
            case PAUSE:
                if (isEnteringState){
                    // do some setup
                    clock.stop();
                    sounds.stop("Background");
                    
                    isEnteringState = false;
                } else {
                    // do stuff
                    
                    // Listen for carousel zlide
                    if (keys.isKeyTyped(KeyEvent.VK_LEFT)) caroussel.left();
                    else if (keys.isKeyTyped(KeyEvent.VK_RIGHT)) caroussel.right();
                    
                    // check for state transition criteria
                    if (keys.isKeyTyped(KeyEvent.VK_P)){
                        isEnteringState = true;
                        gameState = State.PLAY;
                        
                        caroussel.setActive(false);
                    }
                }
                break;
            case RESET:
                if (isEnteringState){
                    // do some setup
                    clock.stop();
                    clock.reset();
                    
                    CardLoader.resetCard(cards.get(currentCardIndex));
                    CardLoader.saveCards(cards, CARDS_PATH);
                    
                    GAButton.setMessage("Reset Level");
                    GAButton.setButtonText("OK");
                    GAButton.setIsListening(true);
                    
                    isEnteringState = false;
                } else {
                    // do stuff
                    if (keys.isKeyTyped(KeyEvent.VK_ENTER)) GAButton.setIsListening(false);
                    
                    // check for state transition criteria
                    if (!GAButton.isIsListening()){
                        sounds.play("Click", 1);
                        isEnteringState = true;
                        gameState = State.SETUP;
                    }
                }
                break;
            case WOOHOO:
                if (isEnteringState){
                    // do some setup
                    clock.stop();
                    sounds.play("LevelComplete", 1);
                    
                    // Update Clapper
                    cards.get(currentCardIndex).setScore(getComplexityScore() + getBaseTimeScore() + getTimeBonus());
                    cards.get(currentCardIndex).setActualTimeToComplete(clock.getAccumulatedTimeSeconds());
                    loadClapper();
                    
                    // trigger celebration stuff
                    
                    // Save to file
                    CardLoader.saveCards(cards, CARDS_PATH);
                    
                    // Congratulation message
                    GAButton.setMessage("Level Complete!"
                            + "\nComplexity Score  " + getComplexityScore()
                            + "\nBase Time Score  " + getBaseTimeScore()
                            + "\nTime Bonus  " + getTimeBonus()
                            + "\nTotal " + clapper.getLevelScore());
                    GAButton.setButtonText("OK");
                    GAButton.setIsListening(true);
                    
                    isEnteringState = false;
                } else {
                    // do stuff
                    if (keys.isKeyTyped(KeyEvent.VK_ENTER)) GAButton.setIsListening(false);
                    
                    // check for state transition criteria
                    if (!GAButton.isIsListening()){
                        sounds.play("Click", 1);
                        if (currentCardIndex < cards.size() - 1){
                            isEnteringState = true;
                            gameState = State.SETUP;
                        } else {
                            isEnteringState = true;
                            gameState = State.EXIT;
                        }
                    }
                }
                break;
            case EXIT:
                if (isEnteringState){
                    // do some setup
                    sounds.play("GameComplete", 1);
                    
                    CardLoader.resetCards(cards);
                    CardLoader.saveCards(cards, CARDS_PATH);
                    
                    // wait to quit
                    GAButton.setMessage("Ready to quit!");
                    GAButton.setButtonText("OK");
                    GAButton.setIsListening(true);
                    
                    isEnteringState = false;
                } else {
                    // do stuff
                    if (keys.isKeyTyped(KeyEvent.VK_ENTER)) GAButton.setIsListening(false);
                    
                    // check for state transition criteria
                    if (!GAButton.isIsListening()){
                        sounds.play("Click", 1);
                        isEnteringState = true;
                        // gameState = // some new state
                        setNextScene("credits");
                    }
                }
                break;
        }
        
        keys.resetKeys();
        
        // update sprites
        bg.update();
        clapper.update();
        clock.update();
        tileBoard.update();
        caroussel.update();
        GAButton.update();
    }

    //------------------------------ Paint -----------------------------------//
    /***
     * Invoked by SceneSelector 25 times per second to repaint the scene.
     * 
     * @param g graphics context of the JPanel this class descends from
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        bg.paint(bufferedGraphics, camera);
        clapper.paint(bufferedGraphics, camera);
        clock.paint(bufferedGraphics, camera);
        
        //bufferedGraphics.setColor(Color.BLACK);
        tileBoard.paint(bufferedGraphics, camera);
        legend.paint(bufferedGraphics, camera);
        displayLevelName(bufferedGraphics);
        caroussel.paint(bufferedGraphics, camera);
        GAButton.paint(bufferedGraphics, camera);
        
        g.drawImage(imageBuffer, 0, 0, width, height, null);
    }
    
    private void displayLevelName(Graphics2D g){
        bufferedGraphics.setColor(shiftColor(baseColor, -50));
        bufferedGraphics.setFont(titleFont);
        FontMetrics metrics = g.getFontMetrics(titleFont);
        int textWidth = metrics.stringWidth(cards.get(currentCardIndex).getLevelName());
        bufferedGraphics.drawString(cards.get(currentCardIndex).getLevelName(), (int)((width * 0.60) - (textWidth * 0.5)), (int)(height * 0.1));
    }
    
    private void initializeSprites(){
        
        cards = CardLoader.loadCards(CARDS_PATH);
        currentCardIndex = getCurrentCardIndex();
        
        //
        baseColor = getRandomColor();
        // set up tileboard
        double aspectRatio = width * 1.0 / height;
        boardHeight = (int)(0.7 * height);
        boardWidth = (int)(boardHeight * aspectRatio);

        bg = new BackgroundEffect((int)(width / 2), (int)(height / 2), width, height, baseColor);

        clapper = new Clapper((int)(width *0.13), (int)(height * 0.34));
        loadClapper();

        clock = new Chrono((int)(width * 0.13), (int)(height * 0.7), 300, 300, Color.WHITE, Color.RED, cards.get(currentCardIndex).getRecomendedTimeToComplete());

        tileBoard = getParameterizedBoard(cards.get(currentCardIndex));
        addMouseListener(tileBoard);

        legend = new Legend((int)(width / 2), (int)(height * 0.9), (int)(width * 0.9), 70);

        GAButton = new GoAwayButton((int)(width / 2), (int)(height / 2), 400, 300, baseColor, "This is the button\nwith multiple lines", "Go Away");
        GAButton.setIsListening(false);
        addMouseListener(GAButton);

        caroussel = new Caroussel((int)(width * 0.5), (int)(height * 0.5), (int)(width), (int)(height), cards);
        
        isGongable = true;
    }
    
    private TileBoard getParameterizedBoard(Card card){
        TileBoard board = new TileBoard((int)(width *0.6), (int)(height / 2), boardWidth, boardHeight, 0, 
                card.getRows(), card.getColumns(), card.getImagePath(), sounds);
        return board;
    }
    
    private Color getRandomColor(){
        Random rng = new Random();
        
        int r = rng.nextInt(100) + 155;
        int g = rng.nextInt(100) + 155;
        int b = rng.nextInt(100) + 155;
        
        return new Color(r, g, b);
    }
    
    private Color shiftColor(Color color, int shift){
        
        int r = Utility.boundedInt(color.getRed() + shift, 255, 0);
        int g = Utility.boundedInt(color.getGreen() + shift, 255, 0);
        int b = Utility.boundedInt(color.getBlue() + shift, 255, 0);
        
        return new Color(r, g, b);
    }
    
    private int getCurrentCardIndex(){
        int i = 0;
        for (; i < cards.size(); i++){
            if (cards.get(i).getActualTimeToComplete() == Integer.MAX_VALUE){
                return i;
            }
        }
        return --i;
    }
    
    //------------------------------ Scores ----------------------------------//
    
    private int getComplexityScore(){
        int score = 0;
        
        score += cards.get(currentCardIndex).getColumns() *
                cards.get(currentCardIndex).getRows() *
                PER_TILE_SCORE;
        
        score += cards.get(currentCardIndex).getRandomizationSteps() *
                PER_RANDOMIZATION_STEP_SCORE;
        
        return score;
    }
    
    private int getBaseTimeScore(){
        int overTime = clock.getAccumulatedTimeSeconds() - cards.get(currentCardIndex).getRecomendedTimeToComplete();
        int numberOfDeductions = 0;
        
        if (overTime > 0){
            numberOfDeductions = overTime / 30; // 30 second increments
        }
        
        int score = TIME_BASE_SCORE - numberOfDeductions * TIME_PENALTY;
        
        return score < 0 ? 0 : score;
    }
    
    private int getTimeBonus(){
        if (clock.getAccumulatedTimeSeconds() < cards.get(currentCardIndex).getRecomendedTimeToComplete()){
            return TIME_BONUS;
        }
        
        return 0;
    }
    
    //-------------------------- Loading Clapper -----------------------------//
    
    public void loadClapper(){
        clapper.setStage(currentCardIndex + 1);
        clapper.setDifficulty(getComplexityScore());
        clapper.setTimeToBeat(cards.get(currentCardIndex).getRecomendedTimeToComplete());
        clapper.setLevelScore(cards.get(currentCardIndex).getScore());
        clapper.setAccumulatedScore(calculateAccumulatedScore());
    }
    
    private int calculateAccumulatedScore(){
        int score = 0;
        
        for (int i = 0; i <= currentCardIndex; i++){
            score += cards.get(i).getScore();
        }
        
        return score;
    }
    
    //-------------------------- Loading Clapper -----------------------------//
    //------------------------------------------------------------------------//
    //------------------------------------------------------------------------//
    //------------------------------------------------------------------------//
    //------------------------------------------------------------------------//
    //------------------------------------------------------------------------//
}