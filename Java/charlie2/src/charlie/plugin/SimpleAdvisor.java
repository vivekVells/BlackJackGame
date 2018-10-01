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
            if(myHand.getValue()>=17){
                play = Play.STAY;
            }else if(myHand.getValue()<11){
                play = Play.HIT;
            }else if(myHand.getValue()==11){
                play = Play.DOUBLE_DOWN;
            }else if (myHand.getValue() >=12 && myHand.getValue() <=16 && ((upCard.value() + 10) <=10) ) {
                play = Play.STAY;
            }else if (myHand.getValue() >=12 && myHand.getValue() <=16 && ((upCard.value() + 10) > 16) ) {
                play = Play.HIT;
            }else{
                play = Play.NONE;
            }
           
            return play;
            
        }        
    }
    
}
