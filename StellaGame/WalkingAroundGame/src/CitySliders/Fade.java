/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CitySliders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Joe
 */
public class Fade extends Sprite{
    
    private enum Direction {IN, OUT}
    
    private Color fadeColor;
    private int frames;
    private Direction fadeDirection;
    private int framesRemaining;
    private double fadeIncrement;
    private int framesTillCorrection;
    
    
    public Fade(int x, int y, int width, int height, Color fadeColor, Direction fadeDirection, int frames){
        super(x, y, width, height, 0, "assets/images/blankSprite.png");
        
        this.fadeDirection = fadeDirection;
        this.fadeColor = getAlphaColor(fadeColor);
        this.frames = frames;
        this.framesRemaining = 0;
        this.fadeIncrement = 255.0 / Math.abs(frames);
    }
    
    @Override
    public void update(){
        super.update();
        
        if (framesRemaining > 0){
            framesRemaining--;
        }
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);
        
        g.setColor(fadeColor);
        g.fillRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h);
    }
    
    public void fade(){
        
    }
    
    private Color getAlphaColor(Color color){
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = (this.fadeDirection == Direction.IN) ? 255 : 0;

        return new Color(r, g, b, alpha);
    }
    
}
