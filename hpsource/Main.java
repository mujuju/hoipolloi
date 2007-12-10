/*
 * Main.java
 *
 * Created on November 10, 2006, 3:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package hoipolloi;

import java.util.*;

/**
 *
 * @author Brandon Buck
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Debug.turnOff();  
        MainMenu foo = new MainMenu();
        try {
            Person dude = new Person(249);
            //foo.editProfile(dude);
            foo.showProfile(dude);
        } catch (Exception e) {}
        /*
        try {
            Person peep = new Person(134);
            peep.setEyeColor("Blue");
            peep.setHairColor("Red");
            peep.setGender("Female");
            peep.setMaidenName("Smith");
            peep.setNationality("American");
            peep.setOccupation("Social Worker");
            peep.setBirthday("1973-03-22");
            peep.saveToDatabase();
        }
        catch (Exception e) {}
        */
    }
    
}
