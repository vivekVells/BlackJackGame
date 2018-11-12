package charlie.bs.section3;

import charlie.client.AdvisorPlugin;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.util.Play;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests my ACE,3 vs dealer 4 which should be HIT.
 */
public class Test00_A3_4 {
    @Test
    public void test() {
        // Generate an initially empty hand
        Hand myHand = new Hand(new Hid(Seat.YOU));
        
        // Put two cards in hand: A+3
        Card card1 = new Card(Card.ACE,Card.Suit.CLUBS);
        Card card2 = new Card(3,Card.Suit.DIAMONDS);
        
        myHand.hit(card1);
        myHand.hit(card2);
        
        // Create dealer up card: 4
        System.out.println("Boundary Condition (Hand vs Up Card = advice): (ACE + 3) vs 4 = HIT");
        Card upCard = new Card(4,Card.Suit.HEARTS);
        
        // Construct advisor and test it
        AdvisorPlugin advisor = new AdvisorPlugin();
  
        Play advice = advisor.advise(myHand, upCard);
        System.out.println("advise received for above condition: " + advice + "\n");

        // Validate the advise
        assertEquals(advice, Play.HIT);
    }
}