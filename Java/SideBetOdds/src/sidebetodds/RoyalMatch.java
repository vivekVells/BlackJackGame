package sidebetodds;

import charlie.card.Card;
import charlie.card.Shoe;

/**
 * This program estimates the odds of Super 7 using Monte Carlo simulation.
 * @author Vivek Vellaiyappan | vivekvellaiyappans@gmail.com
 */
public class RoyalMatch {
    public final static int NUM_SAMPLES = 109644;

    public static void main(String[] args) { 
        // Only need a one deck, standard shoe
        Shoe shoe = new Shoe(1);

               
        // Frequency we draw a 7 as our first card
        int count = 0;
        
        // Main loop of the simulation
        for(int sim=0; sim < NUM_SAMPLES; sim++) {
            
            // Initialize the shoe--reloads the shoe and shuffles
            shoe.init();
        
            // Get our card
            Card card1 = shoe.next();
            
            // Get our card
            Card card2 = shoe.next();
            
            
            // This point forward starts Blackjack but it does not affect Super 7
            
            // Dealer's hole card
            shoe.next();
            
            // My next card
            shoe.next();
            
            // Dealer's up-card
            shoe.next();
            
            if(
                (card1.getRank() == card1.QUEEN &&  card2.getRank() == card2.KING)
                    || (card1.getRank() == card1.KING &&  card2.getRank() == card2.QUEEN)
                    && (card1.getSuit() == card2.getSuit())
                    ) {
                count++;
            }
        }
        
        // Compute the estimate probablity and odds
        double p = (1 / Math.sqrt((double)NUM_SAMPLES));
        
        int odds = (int)((1 - p) / p);
        
        System.out.printf("Royal Match prob = %8.6f odds = %d:1\n", p, odds);
    }            
}
/**
 * Output:
 * run:
    Royal Match prob = 0.003020 odds = 330:1
    BUILD SUCCESSFUL (total time: 12 seconds)
 */