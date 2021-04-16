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
    
    public enum Direction {IN, OUT} // out means toward fadeColor, in means toward transparent
    
    private Color fadeColor;
    private Color maskColor;
    private Direction fadeDirection;
    private int transitionSpeed;
    
    public Fade(int x, int y, int width, int height, Color fadeColor){
        super(x, y, width, height, 0, "assets/images/blankSprite.png");

        this.fadeColor = fadeColor;
        this.maskColor = getAlphaColor(fadeColor);
        fadeDirection = Direction.OUT;
        this.transitionSpeed = 0;
    }
    
    @Override
    public void update(){
        super.update();
        
        if (fadeDirection == Direction.IN){
            if (maskColor.getAlpha() > 0){
                System.out.println("Fade in");
                setAlpha(maskColor.getAlpha() - transitionSpeed);
            }
        } else { // fade Direction is OUT
            if (maskColor.getAlpha() < 255){
                System.out.println("Fade out");
                setAlpha(maskColor.getAlpha() + transitionSpeed);
            }
        }
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);
        
        g.setColor(maskColor);
        g.fillRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h);
    }
    
    public void fade(Direction dir, int speed){ // speed is degrees per frame
        this.transitionSpeed = Math.abs(speed);
        this.fadeDirection = dir;
        maskColor = getAlphaColor(fadeColor);
    }
    
    private Color getAlphaColor(Color color){
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = (this.fadeDirection == Direction.IN) ? 255 : 0;

        return new Color(r, g, b, alpha);
    }
    
    private void setAlpha(int alpha){
        // Correct out of bounds input
        if (alpha < 0) alpha = 0;
        else if (alpha > 255) alpha = 255;
        
        int r = maskColor.getRed();
        int g = maskColor.getGreen();
        int b = maskColor.getBlue();
        maskColor = new Color(r, g, b, alpha);
    }

}
