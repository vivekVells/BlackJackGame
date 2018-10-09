package charlie.bs.section4;

import charlie.client.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.util.Play;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests my 2,2 vs dealer 7 which should be SPLIT;
 *   Since SPLIT unimplemented, for 4 value we can change
 *      basic strategy table to advice HIT
 */
public class Test00_22_7 {
    @Test
    public void test() {
        // Generate an initially empty hand
        Hand myHand = new Hand(new Hid(Seat.YOU));
        
        // Put two cards in hand: 2+2
        Card card1 = new Card(2,Card.Suit.CLUBS);
        Card card2 = new Card(2,Card.Suit.DIAMONDS);
        
        myHand.hit(card1);
        myHand.hit(card2);
        
        // Create dealer up card: 7
        System.out.println("Boundary Condition (Hand vs Up Card = advice): (2 + 2) vs 7 = SPLIT");
        Card upCard = new Card(7,Card.Suit.HEARTS);
        
        // Construct advisor and test it
        Advisor advisor = new Advisor();
  
        Play advice = advisor.advise(myHand, upCard);
        System.out.println("advise received for above condition: " + advice);
        System.out.println("Unit test is success. but as SPLIT unimplemented; "
                + "\n\tfor 4 value we can change the basic strategy table to advice HIT\n");

        // Validate the advise
        assertEquals(advice, Play.SPLIT);
    }
}