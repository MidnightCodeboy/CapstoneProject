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
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Joe
 */
public class GoAwayButton extends Sprite implements MouseListener{
    
    private String message;
    private String buttonText;
    
    // Colors
    private Color bg = new Color(255, 255, 255, 200);
    private Color line = Color.BLACK;
    private Color buttonColor;
    
    // Fonts
    private Font messageFont = new Font("Times New Roman", Font.BOLD, 25);
    private Font buttonFont = new Font("Times New Roman", Font.BOLD, 35);
    
    // State
    private boolean isListening;

    // Bounding Box
    private Rectangle boundingBox;
    
    public GoAwayButton(int x, int y, int width, int height, Color buttonColor, String message, String buttonText){
        super(x, y, width, height, 0, "assets/images/blankSprite.png");
        
        this.message = message;
        this.buttonText = buttonText;
        this.buttonColor = buttonColor;
        this.isListening = false;
        
        boundingBox = new Rectangle((int)(x - width / 2), (int)(y - height / 2), width, height);
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
    
    public boolean isIsListening() {
        return isListening;
    }

    public void setIsListening(boolean isListening) {
        this.isListening = isListening;
        isVisible = isListening;
    }
    
    @Override
    public void update(){
        super.update();
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);
        
        if (isVisible){
            g.setColor(bg);
            g.fillRoundRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h, 30, 30);

            g.setColor(line);
            g.drawRoundRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h, 30, 30);

            g.setColor(line);
            g.drawRoundRect((int)(x - w * 0.48), (int)(y - h * 0.48), (int)(w * 0.96), (int)(h * 0.96), 30, 30);
            
            // Display Level Score
            
            displayString(message, (int)(x), (int)(y - h * 0.5 + 40), g, messageFont, Color.BLACK);
            
            g.setColor(buttonColor);
            g.fillRect((int)(x - w * 0.45), (int)(y + h * 0.28), (int)(w * 0.90), (int)(h * 0.15));
            g.setColor(line);
            g.drawRect((int)(x - w * 0.45), (int)(y + h * 0.28), (int)(w * 0.90), (int)(h * 0.15));
            displayString(buttonText + "", (int)(x), (int)(y + h * 0.25), g, buttonFont, Color.BLACK);
        } 
    }
    
    private void displayString(String string, int x, int y, Graphics2D g, Font font, Color color){
        g.setColor(color);
        g.setFont(font);
        
        FontMetrics metrics = g.getFontMetrics(font);
        int lineHeight = metrics.getHeight();
        int yy = y;
        
        for (String line : string.split("\n")){
            int textWidth = metrics.stringWidth(line);
            g.drawString(line, (int)(x - (textWidth * 0.50)), (int)(yy += lineHeight));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isListening){
            if (boundingBox.contains(new Point(e.getX(), e.getY()))){
                System.out.println("Something was clicked");
                setIsListening(false);
                setIsVisible(false);
            }
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {;}

    @Override
    public void mouseReleased(MouseEvent e) {;}

    @Override
    public void mouseEntered(MouseEvent e) {;}

    @Override
    public void mouseExited(MouseEvent e) {;}
    
}
