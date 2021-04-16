/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CitySliders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Joe
 */
public class Square extends Sprite{

    private Color color;
    
    public Square(int x, int y, int width, int height, int orientation, Dimension velocity){
        super(x, y, width, height, 0, "assets/images/blankSprite.png");
        
        setVelocity(velocity.getWidth(), velocity.getHeight());
    }
    
    @Override
    public void update(){
        super.update();
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera ){
        super.paint(g, camera);
        
        g.setColor(color);
        g.fillRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h);
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
}
