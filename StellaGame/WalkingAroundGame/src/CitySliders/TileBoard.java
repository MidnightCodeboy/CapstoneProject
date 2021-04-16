/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CitySliders;

import java.util.Random;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Joe
 */
public class TileBoard extends Sprite implements MouseListener{
    
    private int rows;
    private int cols;
    private Tile[] tiles;
    private Tile gapTile;
    private int left;
    private int top;
    private int tileWidth;
    private int tileHeight;
    private BufferedImage puzzleBuffer;
    private int visualRandomizationSteps;
    private Direction lastSlideDirection;
    private SoundClipper sounds;
    
    public enum Direction {UP, DOWN, LEFT, RIGHT, NONE}
    
    public TileBoard(int x, int y, int width, int height, int orientation, int rows, int cols, String pathToPuzzleImage, SoundClipper sounds){
        super(x, y, width, height, orientation, "assets/images/blankSprite.png");
        
        // Tile Properties
        this.rows = rows;
        this.cols = cols;
        lastSlideDirection = Direction.NONE;
        visualRandomizationSteps = 0;
        tileWidth = (int)(w / cols);
        tileHeight = (int)(h / rows);
        left = (int)(x - w / 2 + tileWidth / 2);
        top = (int)(y - h / 2 + tileHeight / 2);
        
        this.sounds = sounds;
        
        this.tiles = new Tile[rows * cols];
        
        // Get Base Puzzle Image
        puzzleBuffer = getBufferedImageFromFilePath(pathToPuzzleImage);
        
        // Sub Image Clip Properties
        int clipWidth = (int)(puzzleBuffer.getWidth() / cols);
        int clipHeight = (int)(puzzleBuffer.getHeight() / rows);
        
        // Build List of Tiles
        Image tempImage;
        for (int i = 0; i < tiles.length; i++){
            tempImage = puzzleBuffer.getSubimage( (int)(i % cols) * clipWidth, (int)(i / cols) * clipHeight, clipWidth, clipHeight );
            
            tiles[i] = new Tile(left + (int)(i % cols) * tileWidth, top + (int)(i / cols) * tileHeight, 
                    tileWidth, tileHeight, orientation,
                    tempImage
            );
        }
        
        // Get a useful refference to the gap tile
        gapTile = tiles[tiles.length - 1];

    }
    
    @Override
    public void update(){
        super.update();
        
        for (Tile tile : tiles){
            tile.update();
        }
        
        if (visualRandomizationSteps > 0 && !isSliding()){
            // set up a slide
            Direction dir = getRandomDirection(getOppositeDirection(lastSlideDirection));
            lastSlideDirection = dir;
            boolean isSuccessful = slide(dir);
            
            if (isSuccessful){
                visualRandomizationSteps--;
                sounds.play("Slide1", 1);
            } else {
                //sounds.play("Fail", 1);
            }
        }
        
    }
    
    @Override
    public void paint(Graphics2D g, Rectangle camera){
        super.paint(g, camera);
        
        g.drawRect((int)(x - w / 2), (int)(y - h / 2), (int)w, (int)h);
        
        if (isSolved())
        {
            g.drawImage(puzzleBuffer, (int)(x - w / 2), (int)(y - h / 2), (int) w, (int)h, null);
        } 
        else 
        {
            for (int i = 0; i < tiles.length - 1; i++){
                tiles[i].paint(g, camera);
            }
        }

    }

    // -----------------------------------------------------------------------//
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (!isSliding()){ // Dont look for clicks when tiles are sliding
            
            Tile candidateTile = getTileAtCoordinate(e.getX(), e.getY());
                
            if (candidateTile != null && (isGapAdjacentHorizontal(candidateTile) || isGapAdjacentVertical(candidateTile))){
                slideSwapTiles(gapTile, candidateTile);
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
    
    private int distanceBetweenPoints(int x1, int y1, int x2, int y2){
        return (int)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    private void slideSwapTiles(Tile tile1, Tile tile2){
        
        int tempX = tile1.getX();
        int tempY = tile1.getY();

        tile1.setTarget(tile2.getX(), tile2.getY());
        tile2.setTarget(tempX, tempY);
    }
    
    private void swapTiles(Tile tile1, Tile tile2){
        int tempX = tile1.getX();
        int tempY = tile1.getY();
                
        tile1.setPosition(tile2.getX(), tile2.getY());
        tile1.setTarget(tile2.getX(), tile2.getY());
        
        tile2.setPosition(tempX, tempY);
        tile2.setTarget(tempX, tempY);
    }
    
    // True if any tile is not currently at its target location
    public boolean isSliding(){
        for (Tile tile : tiles){
            if (!tile.isOnTarget()){
                return true;
            }
        }
        return false;
    }
    
    // True of all puzzle tiles in their home positions
    public boolean isSolved(){
        for (Tile tile : tiles){
            if (!tile.isOnHome()){
                return false;
            }
        }
        return true;
    }
    
    // Gets BufferedImage from path - Buffered Images have methods for getting sub-images
    private BufferedImage getBufferedImageFromFilePath(String imagePath){
        
        URL url = getClass().getResource(imagePath);
        ImageIcon puzzleImageIcon = new ImageIcon(url);
        Image puzzleImage = toolkit.getImage(url);
        
        BufferedImage puzzleBuffer = new BufferedImage(puzzleImageIcon.getIconWidth(), puzzleImageIcon.getIconHeight(), 
                BufferedImage.TYPE_INT_RGB);
        Graphics2D bufferedGraphics = (Graphics2D) puzzleBuffer.getGraphics();
        
        bufferedGraphics.drawImage(puzzleImage, 0, 0, puzzleImageIcon.getIconWidth(), puzzleImageIcon.getIconHeight(), null);
        
        return puzzleBuffer;
    }
    
    // swaps positions of two given tiles by initiating slides
    public boolean slide(Direction dir){
        if (!isSliding()){
            
            Tile candidateTile = getCandidateTile(dir);

            if (candidateTile != null){
                slideSwapTiles(gapTile, candidateTile);
                return true;
            }
            return false;
        }
        
        return false;
    }
    
    // Swap the positions of the two given tiles - useful for randomization
    public boolean swap(Direction dir){
            
        Tile candidateTile = getCandidateTile(dir);

        if (candidateTile != null){
            swapTiles(gapTile, candidateTile);
            return true;
        }
        
        return false;
    }
    
    // Returns the adjacent tile in the given direction or null if no tile
    private Tile getCandidateTile(Direction dir){
        
        int candidateX = gapTile.getX();
        int candidateY = gapTile.getY();

        switch(dir){
            case UP:
                candidateY += tileHeight;
                break;
            case DOWN:
                candidateY -= tileHeight;
                break;
            case LEFT:
                candidateX += tileWidth;
                break;
            case RIGHT:
                candidateX -= tileWidth;
                break;
            default:
        }

        return getTileAtCoordinate(candidateX, candidateY);
    }
    
    // returns tile at coord or null if no match
    private Tile getTileAtCoordinate(int x, int y){
        for (int i = 0; i < tiles.length - 1; i++){
            if (Math.abs(tiles[i].x - x) <= tileWidth / 2.0 && Math.abs(tiles[i].y - y) <= tileHeight / 2.0){
                return tiles[i];
            }
        }

        return null;
    }
    
    // is given tile horizontally adjacent to the gap tile
    private boolean isGapAdjacentHorizontal(Tile tile){
        boolean isAdjacentHor = Math.abs(tile.getX() - gapTile.getX()) <= tileWidth;
        
        int centreDisp = distanceBetweenPoints(gapTile.getX(), gapTile.getY(), tile.getX(), tile.getY());
        
        if (isAdjacentHor && !(centreDisp > tileHeight && centreDisp > tileWidth)){
            return true;
        }

        return false;
    }
    
    // is given tile vertically adjacent to the gap tile
    private boolean isGapAdjacentVertical(Tile tile){
        boolean isAdjacentVert = Math.abs(tile.getY() - gapTile.getY()) <= tileHeight;
        
        int centreDisp = distanceBetweenPoints(gapTile.getX(), gapTile.getY(), tile.getX(), tile.getY());
        
        if (isAdjacentVert && !(centreDisp > tileHeight && centreDisp > tileWidth)){
            return true;
        }

        return false;
    }
    
    // Randomize tile configuration by randomly walking the gaptile through the puzzle
    public void randomize(int steps){
        for (int i = 0; i < steps; ){
            boolean isSuccessful = swap(getRandomDirection(Direction.NONE));
            
            if (isSuccessful){
                i++;
            }
        }
    }
    
    public void visualRandomize(int steps){
        this.visualRandomizationSteps = Math.abs(steps);
    }
    
    // Randomly generate a direction up/down/left/right
    private Direction getRandomDirection(Direction excludedDirection){
        Direction dir;
        
        Random rng = new Random();
        
        do{
            switch(rng.nextInt(4)){
                case 0:
                    dir = Direction.UP;
                    break;
                case 1:
                    dir = Direction.DOWN;
                    break;
                case 2:
                    dir = Direction.LEFT;
                    break;
                case 3:
                    dir = Direction.RIGHT;
                    break;
                default:
                    dir = Direction.UP;
            }
        } while (excludedDirection != Direction.NONE && dir == excludedDirection);

        return dir;
    }
    
    private Direction getOppositeDirection(Direction dir){
        switch(dir){
            case UP: return Direction.DOWN;
            case DOWN: return Direction.UP;
            case LEFT: return Direction.RIGHT;
            case RIGHT: return Direction.LEFT;
            case NONE: return Direction.NONE;
            default: return Direction.NONE;
        }
    }
    
    public boolean isShuffling(){
        return visualRandomizationSteps > 0;
    }
    
}
