/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CitySliders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Joe
 */
public class Chrono extends Sprite{

    private Color baseColor;
    private Color overTimeColor;
    private Font font;
    private long lastSystemTime;
    private long accumulatedTime;
    private int recomendedTime;
    private int tenthSeconds;
    private int seconds;
    private int minutes;
    private boolean running;
    
    public Chrono(int x, int y, int width, int height, Color baseColor, Color overTimeColor, int recomendedTime){
        super(x, y, width, height, 0, "assets/images/clockface.png");
        
        this.baseColor = baseColor;
        this.overTimeColor = overTimeColor;
        this.font = new Font("Comic Sans", Font.BOLD, 50);
        
        // Time variables
        this.lastSystemTime = System.currentTimeMillis();
        this.accumulatedTime = 0;
        this.recomendedTime = recomendedTime;
        this.tenthSeconds = 0;
        this.seconds = 0;
        this.minutes = 0;
        
        running = false;
    }
    
    @Override
    public void update(){
        super.update();
        
        if (running){
            updateTime();
        }
        
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);
        
        if (isOverTime()){
            g.setColor(overTimeColor);
        } else{
            g.setColor(baseColor);
        }
        
        g.setFont(font);
        g.drawString(getTimeString(), (int)(x - w * 0.35), (int)(y * 1.04));
    }
    
    private void updateTime(){
        long currentTime = System.currentTimeMillis();
        
        long dt = currentTime - this.lastSystemTime;
        this.lastSystemTime = currentTime;
        
        this.accumulatedTime += dt;
        
        setHoursMinutesSeconds();
    }
    
    private void setHoursMinutesSeconds(){
        long accumulatedTime = this.accumulatedTime;
        
        this.minutes = (int)(accumulatedTime / (60 * 1000));
        accumulatedTime %= (60 * 1000);
        this.seconds = (int)(accumulatedTime / (1000));
        accumulatedTime %= (1000);
        this.tenthSeconds =  (int)(accumulatedTime / (100));
    }
    
    private String getTimeString(){
        String timeString = "";
        
        if (this.minutes / 100 == 0) timeString += "0";
        if (this.minutes / 10 == 0) timeString += "0";
        timeString += minutes + ":";
        
        if (this.seconds / 10 == 0) timeString += "0";
        timeString += seconds + ":" + tenthSeconds;
        
        return timeString;
    }
    
    public Color getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    public Color getOverTimeColor() {
        return overTimeColor;
    }

    public void setOverTimeColor(Color overTimeColor) {
        this.overTimeColor = overTimeColor;
    }
    
    public int getAccumulatedTimeSeconds(){
        return (int)(accumulatedTime / 1000);
    }
    
    public boolean isOverTime(){
        return accumulatedTime > recomendedTime * 1000;
    }
    
    public void stop(){
        this.running = false;
    }
    
    public void start(){
        this.running = true;
        this.lastSystemTime = System.currentTimeMillis();
    }
    
    public void reset(){
        this.accumulatedTime = 0;
        this.tenthSeconds = 0;
        this.seconds = 0;
        this.minutes = 0;
        this.lastSystemTime = System.currentTimeMillis();
    }
    
}
