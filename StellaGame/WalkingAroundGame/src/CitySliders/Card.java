/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CitySliders;

/**
 *
 * @author Joe
 */
public class Card {

    private String imagePath;
    
    private String levelName;

    private int rows;
    
    private int columns;
    
    private int recomendedTimeToComplete;
    
    private int actualTimeToComplete;
    
    private int score;
    
    private int randomizationSteps;
    
    public Card(){
        this(null, "", 3, 3, 180, 10);
    }
    
    public Card(String imagePath){
        this(imagePath, "", 3, 3, 180, 10);
    }
    
    public Card(String imagePath, String levelName, int rows, int columns, int recomendedTimeToComplete, int randomizationSteps){
        this(imagePath, levelName, rows, columns, recomendedTimeToComplete, Integer.MAX_VALUE, 0, randomizationSteps);
    }
    
    public Card(String imagePath, String levelName, int rows, int columns, int recomendedTimeToComplete, int actualTimeToComplete, int score, int randomizationSteps){
        this.imagePath = imagePath;
        this.levelName = levelName;
        this.rows = rows;
        this.columns = columns;
        this.recomendedTimeToComplete = recomendedTimeToComplete;
        this.actualTimeToComplete = actualTimeToComplete;
        this.score = score;
        this.randomizationSteps = randomizationSteps;
    }
    
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
    
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRecomendedTimeToComplete() {
        return recomendedTimeToComplete;
    }

    public void setRecomendedTimeToComplete(int recomendedTimeToComplete) {
        this.recomendedTimeToComplete = recomendedTimeToComplete;
    }

    public int getActualTimeToComplete() {
        return actualTimeToComplete;
    }

    public void setActualTimeToComplete(int actualTimeToComplete) {
        this.actualTimeToComplete = actualTimeToComplete;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRandomizationSteps() {
        return randomizationSteps;
    }

    public void setRandomizationSteps(int randomizationSteps) {
        this.randomizationSteps = randomizationSteps;
    }
    
    public String toString(){
        String output = "";
        
        output += getImagePath() + " ";
        output += getLevelName() + " ";
        output += getRows() + " ";
        output += getColumns() + " ";
        output += getRecomendedTimeToComplete() + " ";
        output += getActualTimeToComplete() + " ";
        output += getScore() + " ";
        output += getRandomizationSteps();
        
        return output;
    }
}
