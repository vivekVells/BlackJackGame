/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.plugin;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;

/**
 *
 * @author Vivek Vellaiyappan Surulimuthu | vivekvellaiyappans@gmail.com
 */
public class SimpleAdvisor implements IAdvisor {
    
    @Override
    public Play advise(Hand myHand, Card upCard) {
        // throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
        
        while (true) {
            //System.out.print("Advice [S, H, D, or P]? ");
            Play play;
            
            if(myHand == null) {
                System.out.println("myHand value is null!");
                play = Play.NONE;
            }else if(myHand.getValue() >= 21){
                System.out.println("myHand value should be less than 21");
                play = Play.NONE;
            }else if(upCard == null) {
                System.out.println("upcard value is null!");
                play = Play.NONE;
            }else if(upCard.value() >= 21){
                System.out.println("upcard value should be less than 21");
                play = Play.NONE;
            }else if(myHand.isPair() 
                    && (myHand.getCard(0).getRank()==8
                    || myHand.getCard(0).getRank()==Card.ACE)){
                play = Play.SPLIT;
            }
            else if(myHand.getValue()>=17){
                play = Play.STAY;
            }else if(myHand.getValue()<11){
                play = Play.HIT;
            }else if(myHand.getValue()==11){
                play = Play.DOUBLE_DOWN;
            }else if (myHand.getValue() >=12 
                    && myHand.getValue() <=16 
                    && ((upCard.value() + 10) <=10) ) {
                play = Play.STAY;
            }else if (myHand.getValue() >=12 
                    && myHand.getValue() <=16 
                    && ((upCard.value() + 10) > 16) ) {
                play = Play.HIT;
            }else{
                play = Play.NONE;
            }
            
            System.out.println("play value: " + play);           
            return play;
            
        }        
    }
    
}
