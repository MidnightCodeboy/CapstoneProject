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
public class Utility {
    
    public static int boundedInt(int value, int upper, int lower){
        // Make sure upper bound is larger
        if (upper < lower){
            int temp = upper;
            upper = lower;
            lower = temp;
        }
        
        if (value > upper) return upper;
        else if (value < lower) return lower;
        else return value;
    }
    
}
