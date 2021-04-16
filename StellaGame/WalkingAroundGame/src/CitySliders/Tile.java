/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CitySliders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;

/**
 *
 * @author Joe
 */
public class Tile extends Sprite{
    
    private Color fillColor = Color.BLACK;
    private int homeX;
    private int homeY;
    
    private int targetX;
    private int targetY;
    
    private Image tileImage;
    
    private final int SPEED = 20;
    
    public Tile(int x, int y, int width, int height, int orientation,  Image tileImage){
        super(x, y, width, height, orientation, "assets/images/blankSprite.png");
        
        homeX = x;
        homeY = y;
        
        targetX = x;
        targetY = y;
        
        this.tileImage = tileImage;
    }
    
    @Override
    public void update(){
        super.update();
        
        if (targetX - x >= SPEED){
            x += SPEED;
        } else if (x - targetX >= SPEED){
            x -= SPEED;
        } else if (Math.abs(targetX - x) > 0){
            x = targetX;
        }

        if (targetY - y > SPEED){
            y += SPEED;
        } else if (y - targetY >= SPEED){
            y -= SPEED;
        } else if (Math.abs(targetX - x) >= 0){
            y = targetY;
        }
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);

        g.setColor(fillColor);
        g.fillRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h);
        
        g.drawImage(tileImage, (int)(x - w / 2), (int)(y - h / 2), (int) w, (int)h, null);
        
        g.setColor(Color.WHITE);
        g.drawRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h);
    }
    
    public void setFillColor(Color fillColor){
        this.fillColor = fillColor;
    }
    
    public void setTarget(int x, int y){
        targetX = x;
        targetY = y;
    }
    
    public boolean isOnTarget(){
        return x == targetX && y == targetY;
    }
    
    public boolean isOnHome(){
        return x == homeX && y == homeY;
    }
    
}
