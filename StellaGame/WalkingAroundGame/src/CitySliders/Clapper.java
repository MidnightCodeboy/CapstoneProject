/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CitySliders;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Joe
 */
public class Clapper extends Sprite{
    
    // Display Variables
    private int stage;
    private int difficulty;
    private int timeToBeat;
    private int levelScore;
    private int accumulatedScore;
    
    // Colors
    private Color bg = new Color(255, 255, 255, 100);
    private Color line = Color.BLACK;
    private Color key = Color.GRAY;
    private Color value = Color.RED;
    
    // Fonts
    private Font keyFont = new Font("Times New Roman", Font.BOLD, 18);
    private Font valueFont = new Font("Times New Roman", Font.BOLD, 35);
    
    public Clapper(int x, int y){
        super(x, y, 300, 380, 0, "assets/images/blankSprite.png");
        
        this.stage = 0;
        this.difficulty = 0;
        this.timeToBeat = 0;
        this.levelScore = 0;
        this.accumulatedScore = 0;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getTimeToBeat() {
        return timeToBeat;
    }

    public void setTimeToBeat(int timeToBeat) {
        this.timeToBeat = timeToBeat;
    }

    public int getLevelScore() {
        return levelScore;
    }

    public void setLevelScore(int levelScore) {
        this.levelScore = levelScore;
    }
    
    public int getAccumulatedScore() {
        return accumulatedScore;
    }

    public void setAccumulatedScore(int accumulatedScore) {
        this.accumulatedScore = accumulatedScore;
    }

    @Override
    public void update(){
        super.update();
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);
        
        g.setColor(bg);
        g.fillRoundRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h, 30, 30);
        
        g.setColor(line);
        g.drawRoundRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h, 30, 30);
        
        g.setColor(line);
        g.drawRoundRect((int)(x - w * 0.48), (int)(y - h * 0.48), (int)(w * 0.96), (int)(h * 0.96), 30, 30);
        
        // Display Level
        displayString("Level", (int)(x), (int)(y - h * 0.5 + 30), g, keyFont, key);
        displayString(this.stage + "", (int)(x), (int)(y - h * 0.5 + 60), g, valueFont, value);
        
        // Display Difficulty
        displayString("Difficulty", (int)(x), (int)(y - h * 0.5 + 100), g, keyFont, key);
        displayString(difficulty + "", (int)(x), (int)(y - h * 0.5 + 130), g, valueFont, value);
        
        // Display Time To Beat
        displayString("Time To Beat", (int)(x), (int)(y - h * 0.5 + 170), g, keyFont, key);
        displayString(timeToBeat + "", (int)(x), (int)(y - h * 0.5 + 200), g, valueFont, value);
        
        // Display Level Score
        displayString("Level Score", (int)(x), (int)(y - h * 0.5 + 240), g, keyFont, key);
        displayString(levelScore + "", (int)(x), (int)(y - h * 0.5 + 270), g, valueFont, value);
        
        // Display Accumulated Score
        displayString("Accumulated Score", (int)(x), (int)(y - h * 0.5 + 310), g, keyFont, key);
        displayString(accumulatedScore + "", (int)(x), (int)(y - h * 0.5 + 340), g, valueFont, value);
    }
    
    private void displayString(String string, int x, int y, Graphics2D g, Font font, Color color){
        g.setColor(color);
        g.setFont(font);
        
        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(string);
        
        g.drawString(string, (int)(x - (textWidth * 0.50)), (int)(y));
    }
    
}
