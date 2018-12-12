package charlie.sidebet.rule;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.ISideBetRule;
import org.apache.log4j.Logger;

/**
 * This class implements the side bet rule for Super 7.
 * @author Vivek Vellaiyappan | vivekvellaiyappans@gmail.com
 */
public class SideBetRule implements ISideBetRule {
    private final Logger LOG = Logger.getLogger(SideBetRule.class);
    
    private final Double PAYOFF_BY_SUPER7 = 3.0;
    private final Double PAYOFF_BY_ROYALMATCH = 25.0;
    private final Double PAYOFF_BY_EXACT13 = 1.0;
    
    /**
     * Apply rule to the hand and return the payout if the rule matches
     * and the negative bet if the rule does not match.
     * @param hand Hand to analyze.
     * @return Payout amount: <0 lose, >0 win, =0 no bet
     */
    @Override
    public double apply(Hand hand) {
        Double bet = hand.getHid().getSideAmt();
        double PAYOFF_CONDITION = 0.0;
        double EXACT13_PAYOFF_CONDITION = 0.0;
        
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
        
        LOG.info("side bet amount = "+bet);
        
        if(bet == 0)
            return 0.0;
        
        LOG.info("side bet rule applying hand = "+hand);
         
        // for Super 7
        if(card1.getRank() == 7) {
            LOG.info("side bet SUPER 7 matches");
            return bet * PAYOFF_BY_SUPER7;
        }
        
        // for Royal Match
        if(
            (card1.getRank() == card1.QUEEN &&  card2.getRank() == card2.KING)
                || (card1.getRank() == card1.KING &&  card2.getRank() == card2.QUEEN)
                && (card1.getSuit() == card2.getSuit())
                ) {
                LOG.info("side bet Royal Match matches...");
                PAYOFF_CONDITION = bet * PAYOFF_BY_ROYALMATCH;
        }        
//        if(card1.getSuit() == card2.getSuit()){
//            if((card1.getName().equals("K")) && (card2.getName().equals("Q")) || 
//                    (card1.getName().equals("Q")) && (card2.getName().equals("K"))){
//                LOG.info("side bet Royal Match matches...");
//                PAYOFF_CONDITION = bet * PAYOFF_BY_ROYALMATCH;
//            }
//        }
            
        // for Exact 13
        if(card1.getRank() + card2.getRank() == 13){
            LOG.info("side bet Exact 13 matches...");
            EXACT13_PAYOFF_CONDITION = bet * PAYOFF_BY_EXACT13;
            
            if(EXACT13_PAYOFF_CONDITION > PAYOFF_CONDITION){
                PAYOFF_CONDITION = EXACT13_PAYOFF_CONDITION;
            }
            
        }
        
        if(PAYOFF_CONDITION > 0){
            return PAYOFF_CONDITION;
        }
        else{
            LOG.info("side bet rule no match");
            return -bet;
        }
    }
}
