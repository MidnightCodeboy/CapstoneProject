/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CitySliders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Joe
 */
public class BackgroundEffect extends Sprite{
    
    private Color baseColor;
    
    private ArrayList<Square> mainSquares;
    private ArrayList<Bubble> bubbles;
    
    public BackgroundEffect(int x, int y, int width, int height, Color baseColor){
        super(x, y, width, height, 0, "assets/images/blankSprite.png");
        
        this.baseColor = baseColor;
        
        this.mainSquares = new ArrayList<>();
        this.bubbles = new ArrayList<>();
        
        for (int i = 0; i < 10; i++){
            addSquare();
        }
        
        for (int i = 0; i < 15; i++){
            addBubble();
        }
    }
    
    @Override
    public void update(){
        
        for (Square sq : mainSquares){
            sq.update();
            
            // Wrap squares to keep them on the screen
            if (sq.getX() < 0 - (int)(sq.getWidth() / 2)) sq.setPosition((int)w + (int)(sq.getWidth() / 2), sq.getY());
            else if (sq.getX() > w + (int)(sq.getWidth() / 2)) sq.setPosition(0 - (int)(sq.getWidth() / 2), sq.getY());
            
            if (sq.getY() < 0 - (int)(sq.getHeight() / 2)) sq.setPosition(sq.getX(), (int)h + (int)(sq.getWidth() / 2));
            else if (sq.getY() > h + (int)(sq.getHeight() / 2)) sq.setPosition(sq.getX(), 0 - (int)(sq.getWidth() / 2));
        }
        
        for (Bubble bl : bubbles){
            bl.update();
            
            // Wrap bubbles to keep them on the screen
            if (bl.getX() < 0 - (int)(bl.getWidth() / 2)) bl.setPosition((int)w + (int)(bl.getWidth() / 2), bl.getY());
            else if (bl.getX() > w + (int)(bl.getWidth() / 2)) bl.setPosition(0 - (int)(bl.getWidth() / 2), bl.getY());
            
            if (bl.getY() < 0 - (int)(bl.getHeight() / 2)) bl.setPosition(bl.getX(), (int)h + (int)(bl.getWidth() / 2));
            else if (bl.getY() > h + (int)(bl.getHeight() / 2)) bl.setPosition(bl.getX(), 0 - (int)(bl.getWidth() / 2));
        }
        
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);
        
        g.setColor(baseColor);
        g.fillRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h);
        
        for (Square sq : mainSquares){
            sq.paint(g, camera);
        }
        
        for (Bubble bl : bubbles){
            bl.paint(g, camera);
        }
    }
    
    private void addSquare(){
        Random rng = new Random();
        
        // Random Position
        int x = rng.nextInt(super.getWidth());
        int y = rng.nextInt(super.getHeight());
        
        // Random Size
        int w = rng.nextInt((int)(super.getWidth() * 0.2));
        int h = w;
        
        // Random Velocity
        int vx = rng.nextInt(3)  * getRandomPolarity();
        int vy = rng.nextInt(3) * getRandomPolarity();
        
        // Random deviation in colour from base
        int r = Utility.boundedInt(rng.nextInt(50) * getRandomPolarity() + baseColor.getRed(), 255, 0);
        int g = Utility.boundedInt(rng.nextInt(50) * getRandomPolarity() + baseColor.getGreen(), 255, 0);
        int b = Utility.boundedInt(rng.nextInt(50) * getRandomPolarity() + baseColor.getBlue(), 255, 0);
        
        Square sq = new Square(x, y, w, h, 0, new Dimension(vx, vy));
        sq.setColor(new Color(r, g, b));
        
        mainSquares.add(sq);
        
    }
    
    private void addBubble(){
        Random rng = new Random();
        
        // Random Position
        int x = rng.nextInt(super.getWidth());
        int y = rng.nextInt(super.getHeight());
        
        // Random Velocity
        int vx = rng.nextInt(3)  * getRandomPolarity();
        int vy = rng.nextInt(3) * getRandomPolarity();
        
        bubbles.add(new Bubble(x, y, new Dimension(vx, vy)));
    }
    
    private int getRandomPolarity(){
        Random rng = new Random();
        
        if (rng.nextInt(100) % 2 == 0){
            return 1;
        }
        
        return -1;
    }

    
}
