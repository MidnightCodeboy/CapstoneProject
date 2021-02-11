/**
 *
 * @author      Joseph Roy-Plommer
 * @exercise	Final Project - Intermediate Java
 * @date 	2020-06-10
 */
package walkingaroundgame;

import java.util.ArrayList;

/**
 *  Stores and returns information relating to the position of a sequence of frames on a spritesheet.
 */
public class Animation {
    
    private int frameDelay;         // Number of update cycles between frame increments
    private int currentFrame = 0;   // Frame from list to be returned
    
    // List of frame infos. Each frame is an array of 4 ints (x, y, w, h)
    private ArrayList<int[]> frames = new ArrayList<>();
    
    /***
     * 1-Arg constructor.
     * 
     * @param frameDelay number of updates between frame increments
     */
    public Animation(int frameDelay){
        setFrameDelay(frameDelay);
    }
    
    //----------------------- Functional Methods -------------------------//
    
    /***
     * Adds to this list properties for a new frame in the animation.
     * 
     * @param x x-coord of top left corner of new frame
     * @param y y-coord of top left corner of new frame
     * @param width width of new frame
     * @param height height of new frame
     */
    public void addFrame(int x, int y, int width, int height){
        int[] frame = {x, y, width, height};
        frames.add(frame);
    }
    
    /***
     * Increments current frame so that it refers to the next frame in the animation sequence.
     */
    public void advanceFrame(){
        currentFrame++;
        currentFrame = getSafeFrameIndex(currentFrame);
    }
    
    //------------------------ Utility Functions -------------------------//
    
    /***
     * Utility function to keep the given frame number in the allowed range.
     * 
     * @param frameIndex frame index to be sanitized
     * @return sanitized frame index
     */
    private int getSafeFrameIndex(int frameIndex){
        if (frames.size() > 0){
            frameIndex %= frames.size();
            
            return frameIndex;
        }
        
        return 0;
    }

    //---------------------- Accessors and Mutators ----------------------//
    
    /***
     * 
     * @param frameDelay sets number of updates between frame increments
     */
    public void setFrameDelay(int frameDelay){
        this.frameDelay = frameDelay;
    }
    
    /***
     * 
     * @return number of updates between frame increments
     */
    public int getFrameDelay(){
        return frameDelay;
    }
    
    /***
     * 
     * @param currentFrame the frame to be printed
     */
    public void setCurrentFrame(int currentFrame){
        this.currentFrame = currentFrame;
    }
    
    /***
     * 
     * @return the frame to be printed
     */
    public int getCurrentFrame(){
        return currentFrame;
    }

    /***
     * 
     * @return x-coord of top left corner of current frame
     */
    public int getX(){
        return getX(currentFrame);
    }
    
    /***
     * 
     * @return y-coord of top left corner of current frame
     */
    public int getY(){
        return getY(currentFrame);
    }
    
    /***
     * 
     * @return width of current frame
     */
    public int getWidth(){
        return getWidth(currentFrame);
    }
    
    /***
     * 
     * @return height of current frame
     */
    public int getHeight(){
        return getHeight(currentFrame);
    }
    
    //----------------- Properties for particular frame ------------------//
  
    /***
     * 
     * @param frameIndex frame of interest
     * @return x-coord of top left corner of frame of interest
     */
    public int getX(int frameIndex){
        frameIndex = getSafeFrameIndex(frameIndex);
        return frames.get(frameIndex)[0];
    }
    
    /***
     * 
     * @param frameIndex frame of interest
     * @return y-coord of top left corner of frame of interest
     */
    public int getY(int frameIndex){
        frameIndex = getSafeFrameIndex(frameIndex);
        return frames.get(frameIndex)[1];
    }
    
    /***
     * 
     * @param frameIndex frame of interest
     * @return width of frame of interest
     */
    public int getWidth(int frameIndex){
        frameIndex = getSafeFrameIndex(frameIndex);
        return frames.get(frameIndex)[2];
    }
    
    /***
     * 
     * @param frameIndex frame of interest
     * @return height of frame of interest
     */
    public int getHeight(int frameIndex){
        frameIndex = getSafeFrameIndex(frameIndex);
        return frames.get(frameIndex)[3];
    }

}
