/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.security.SecureRandom;
import org.apache.jasper.tagplugins.jstl.core.ForEach;

/**
 *
 * @author Tung Nguyen
 */
public class TicketCodeGenerator {
    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWSXYZ0123456789";
    public static final int CHAR_LENGH = 6;
    private static final SecureRandom secureRand = new SecureRandom();
    
    public static String generateCode (){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < CHAR_LENGH; i++){
            int randIndex = secureRand.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randIndex));
        }
        return sb.toString();
    }
}
