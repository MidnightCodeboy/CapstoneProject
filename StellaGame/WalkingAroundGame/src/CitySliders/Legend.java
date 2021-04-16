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
public class Legend extends Sprite{
    
    // Colors
    private Color fill = new Color(255, 255, 255, 100);
    private Color line = Color.BLACK;
    
    // Fonts
    private Font font = new Font("Times New Roman", Font.BOLD, 25);
    
    // Legend String
    private String legendString = "| R : Reset Level || P : Pause || Q : Quit || < : Slide Left || > : Slide Right || ^ : Slide Up || v : Slide Down |";
    
    public Legend(int x, int y, int width, int height){
        super(x, y, width, height, 0, "assets/images/blankSprite.png");
    }
    
    @Override
    public void update(){
        super.update();
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);
        
        g.setColor(fill);
        g.fillRoundRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h, 30, 30);
        
        g.setColor(line);
        g.drawRoundRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h, 30, 30);
        
        g.setColor(line);
        g.drawRoundRect((int)(x - w * 0.49), (int)(y - h * 0.45), (int)(w * 0.98), (int)(h * 0.90), 30, 30);
        
        // Display Level
        displayString(legendString, (int)(x), (int)(y - h * 0.5 + 40), g, font, line);
    }
    
    private void displayString(String string, int x, int y, Graphics2D g, Font font, Color color){
        g.setColor(color);
        g.setFont(font);
        
        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(string);
        
        g.drawString(string, (int)(x - (textWidth * 0.50)), (int)(y));
    }
    
}
