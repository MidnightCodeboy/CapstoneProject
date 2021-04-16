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
public class Bubble extends Sprite{
    
    private Color fill = new Color(255, 255, 255, 100);
    private Color line = Color.BLACK;
    
    //private int size = 100;
    
    public Bubble(int x, int y, Dimension velocity){
        super(x, y, 50, 50, 0, "assets/images/blankSprite.png");
        
        setVelocity(velocity.getWidth(), velocity.getHeight());
    }
    
    @Override
    public void update(){
        super.update();
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera ){
        super.paint(g, camera);
        
        g.setColor(fill);
        g.fillOval((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h);
        
        g.setColor(line);
        g.drawOval((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h);
    }
}
