/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CitySliders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Joe
 */
public class Caroussel extends Sprite{
    
    // Colors
    private Color ribbonColor = new Color(0, 0, 0, 200);
    private Color line = Color.BLACK;
    private Color cardColor = Color.WHITE;
    private Color statsColor = Color.RED;
    private Color shadedCardColor = new Color(200, 200, 200);
    private Color buttonCoolColor = new Color(200, 200, 200);
    private Color buttonHotColor = new Color(100, 150, 150);
    private Color maskColor = new Color(255, 255, 255, 200);
    
    // Fonts
    private Font messageFont = new Font("Times New Roman", Font.BOLD, 25);
    private Font buttonFont = new Font("Arial", Font.BOLD, 35);
    
    // Image
    private Rectangle imageBounds;
    
    // Cards
    private ArrayList<Card> cards;
    private int currentCardIndex;

    public int getCurrentCardIndex() {
        return currentCardIndex;
    }

    public void setCurrentCardIndex(int currentCardIndex) {
        this.currentCardIndex = currentCardIndex;
    }
    
    // State
    private boolean isActive;
    private int leftShiftCoolDown;
    private int rightShiftCoolDown;
    private int shiftCoolDown = 5;
    
    public Caroussel(int x, int y, int width, int height, ArrayList<Card> cards){
        super(x, y, width, height, 0, "assets/images/blankSprite.png");

        imageBounds = new Rectangle((int)(x - w * 0.18), (int)(y - h * 0.25), (int)(w * 0.5), (int)(h * 0.5));
        this.cards = cards;
        isActive = false;
        isVisible = false;
        currentCardIndex = 0;
        leftShiftCoolDown = 0;
        rightShiftCoolDown = 0;
    }
    
    @Override
    public void update(){
        super.update();
        
        if (leftShiftCoolDown >0) leftShiftCoolDown--;
        if (rightShiftCoolDown >0) rightShiftCoolDown--;
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);
        
        if (isVisible){
            g.setColor(ribbonColor);
            g.fillRect((int)(x - w / 2), (int)(y - h * 0.35), (int)w, (int)(h * 0.7));

            // Paint left and Right Cards
            if (currentCardIndex > 0){
                g.setColor(shadedCardColor);
                g.fillRoundRect((int)(x - w * 0.6), (int)(y - h * 0.28), (int)(w * 0.15), (int)(h * 0.56), 30, 30);
                
                g.setColor(line);
                g.drawRoundRect((int)(x - w * 0.59), (int)(y - h * 0.27), (int)(w * 0.135), (int)(h * 0.54), 30, 30);
                
                drawButton((int)(x - w * 0.4), (int)(y), leftShiftCoolDown, "<", g);
            }
            
            if (currentCardIndex < cards.size() - 1){
                g.setColor(shadedCardColor);
                g.fillRoundRect((int)(x + w * 0.44), (int)(y - h * 0.28), (int)(w * 0.15), (int)(h * 0.56), 30, 30);
                
                g.setColor(line);
                g.drawRoundRect((int)(x + w * 0.445), (int)(y - h * 0.27), (int)(w * 0.135), (int)(h * 0.54), 30, 30);

                // Paint right shifters
                drawButton((int)(x + w * 0.4), (int)(y), rightShiftCoolDown, ">", g);
            }

            // Paint centre Card
            g.setColor(cardColor);
            g.fillRoundRect((int)(x - w * 0.35), (int)(y - h * 0.30), (int)(w * 0.7), (int)(h * 0.6), 30, 30);
            g.setColor(line);
            g.drawRoundRect((int)(x - w * 0.345), (int)(y - h * 0.29), (int)(w * 0.69), (int)(h * 0.58), 30, 30);
            
            // Paint Card Stats
            Card c = cards.get(currentCardIndex);
            String levelName = "Not Completed";
            String levelGrid = "---------";
            String levelTime = "---------";
            String levelScore = "---------";
            
            g.drawString("Level " + (currentCardIndex + 1) + "/" + cards.size(), (int)(x - w * 0.32), (int)(y - h * 0.25));
            
            if (c.getActualTimeToComplete() < Integer.MAX_VALUE){ // Puzzle was solved
                levelName = c.getLevelName();
                levelGrid = c.getRows() + "x" + c.getColumns();
                levelTime = c.getActualTimeToComplete() + "s";
                levelScore = "" + c.getScore() + " points";
            } else {
                
            }
       
            g.setFont(messageFont);
            g.setColor(statsColor);
            g.drawString(levelName, (int)(x - w * 0.32), (int)(y - h * 0.15));
            g.drawString(levelGrid, (int)(x - w * 0.32), (int)(y - h * 0.05));
            g.drawString(levelTime, (int)(x - w * 0.32), (int)(y - h * -0.05));
            g.drawString(levelScore, (int)(x - w * 0.32), (int)(y - h * -0.15));
            
            // Paint Image
            g.setColor(line);
            g.draw(imageBounds);
            
            Image cardImage = getImage(c.getImagePath());
            g.drawImage(cardImage, imageBounds.x, imageBounds.y, imageBounds.width, imageBounds.height, null);
            
            if (c.getActualTimeToComplete() == Integer.MAX_VALUE){
                g.setColor(maskColor);
                g.fillRect(imageBounds.x, imageBounds.y, imageBounds.width, imageBounds.height);
            }
            
        }        
    }
    
    private void drawButton(int x, int y, int coolDownCounter, String s, Graphics2D g){
        g.setColor((coolDownCounter > 0) ? buttonHotColor : buttonCoolColor);
        g.fillRoundRect((int)(x - 40), (int)(y - 40), (int)(80), (int)(80), 30, 30);
        g.setColor(line);
        g.drawRoundRect((int)(x - 38), (int)(y - 38), (int)(76), (int)(76), 30, 30);
        g.setFont(buttonFont);
        g.drawString(s,(int)(x), (int)(y));
    }
    
    public void left(){
        if (currentCardIndex > 0 && isActive && leftShiftCoolDown == 0){
            currentCardIndex--;
            leftShiftCoolDown = shiftCoolDown;
            rightShiftCoolDown = 0;
        }
    }
        
    public void right(){
        if (currentCardIndex < cards.size() - 1 && isActive && rightShiftCoolDown == 0){
            currentCardIndex++;
            rightShiftCoolDown = shiftCoolDown;
            leftShiftCoolDown = 0;
        }
    }
        
    public void setActive(boolean isActive){
        this.isActive = isActive;
        this.isVisible = this.isActive;
    }
    
    private Image getImage(String imagePath){
        URL url = getClass().getResource(imagePath);
        return toolkit.getImage(url);
    }
    
}
