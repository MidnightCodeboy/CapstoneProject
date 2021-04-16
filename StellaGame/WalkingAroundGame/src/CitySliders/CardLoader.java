/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CitySliders;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joe
 */
public class CardLoader {
    
    public static void saveCards(ArrayList<Card> cards, String filePath){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            
            for (Card card : cards){
                String cardString = "";
                cardString += card.getImagePath() + " ";
                cardString += card.getLevelName() + " ";
                cardString += card.getRows() + " ";
                cardString += card.getColumns() + " ";
                cardString += card.getRecomendedTimeToComplete()+ " ";
                cardString += card.getActualTimeToComplete()+ " ";
                cardString += card.getScore() + " ";
                cardString += card.getRandomizationSteps() + "\n";
                
                bw.write(cardString);
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(CardLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Card> loadCards(String filePath){
        ArrayList<Card> cards = new ArrayList();
        
        try {
            // Get file
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            
            String line;
            while ((line = br.readLine()) != null){
                Scanner sc = new Scanner(line);
                
                String imagePath = sc.next();
                String levelName = sc.next();
                int rows = sc.nextInt();
                int columns = sc.nextInt();
                int recomendedTimeToComplete = sc.nextInt();
                int actualTimeToComplete = sc.nextInt();
                int score = sc.nextInt();
                int randomizationSteps = sc.nextInt();
                
                Card card = new Card(imagePath, levelName, rows, columns, recomendedTimeToComplete,
                            actualTimeToComplete, score, randomizationSteps);
                
                cards.add(card);
                
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CardLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CardLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cards;
    }
    
    public static void resetCards(ArrayList<Card> cards){
        for (Card card : cards){
                resetCard(card);
            }
    }
    
    public static void resetCard(Card card){
            card.setActualTimeToComplete(Integer.MAX_VALUE);
            card.setScore(0);
    }
}
