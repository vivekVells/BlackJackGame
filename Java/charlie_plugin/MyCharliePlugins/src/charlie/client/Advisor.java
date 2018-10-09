
package charlie.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.IAdvisor;
import charlie.util.Play;

/**
 * This class gives play advice.
 * @author maristuser
 */
public class Advisor implements IAdvisor {
    protected BasicStrategy bs = new BasicStrategy();

    @Override
    public Play advise(Hand myHand, Card upCard) {
        return bs.getPlay(myHand, upCard);
//        return Play.NONE;
    }
    
   
}
