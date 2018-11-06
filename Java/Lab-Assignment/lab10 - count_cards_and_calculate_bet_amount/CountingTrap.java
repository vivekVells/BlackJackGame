package charlie.client;

import charlie.card.Card;
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
    private int shoesize = 52;
    private int runningCount = 0;
    private double numDecks = 0.0;
    private int trueCount = 0;
    private int betAmt = 0;    
    
    @Override
    public void onSend(Message msg) {
        LOG.info("onSend overriding function from CountingTrap.java file");
    }

    @Override
    public void onReceive(Message msg) {        
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
                runningCount -= 1;
                LOG.info("decrement runningCount: " + runningCount);                
            } else if(card.getRank() <=6) {
                runningCount += 1;
                LOG.info("increment runningCount: " + runningCount);                                
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
    }
    
}
