package charlie.client;

import charlie.card.Card;
import charlie.card.Hid;
import charlie.message.Message;
import charlie.message.view.to.Deal;
import charlie.plugin.ITrap;
import org.apache.log4j.Logger;
import charlie.message.view.to.GameStart;

/**
 *
 * @author Vivek Vellaiyappan | vivekvellaiyappans@gmail.com
 */
public class CountingTrap implements ITrap {
    private final Logger LOG = Logger.getLogger(CountingTrap.class);  
    
    @Override
    public void onSend(Message msg) {
        LOG.info("onSend overriding function from CountingTrap.java file");
    }

    @Override
    public void onReceive(Message msg) {
        int shoesize = 0;
        int runningCount = 0;
        double numDecks = 0.0;
        int trueCount = 0;
        int betAmt = 0;
        
        LOG.info("onReceive overriding function from CountingTrap.java file");
        
        if(msg instanceof GameStart) {
            GameStart gs = (GameStart)msg;
            shoesize = gs.shoeSize();
        }
        
        if(msg instanceof Deal) {
            --shoesize;
            Deal deal = (Deal)msg;
            Card card = deal.getCard();
            
            if(card == null) return;
            
            if(card.getRank() == 10 || card.isFace() || card.isAce()) {
                --runningCount;
            } else if(card.getRank() <=6) {
                ++runningCount;
            }           
            
            numDecks = shoesize / 52.0;
            trueCount = (int) Math.round((runningCount / numDecks));
            betAmt = Math.max(1,(trueCount + 1)); 
            
            LOG.info("shoeSize: " + shoesize 
                    + "| runningCount: " + runningCount
                    + "| numDecks: " + numDecks 
                    + "| trueCount: " + trueCount 
                    + "| bet amount: " + betAmt);
        }
        
        // bet_amt = max((1, true_count + 1));
        // (int)(0.5 + bet_amt)
    }
    
}
