package charlie.bs.section2;

import charlie.client.AdvisorPlugin;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.util.Play;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests my 10 vs dealer 10 which should be HIT.
 */
public class Test00_10_10 {
    @Test
    public void test() {
        // Generate an initially empty hand
        Hand myHand = new Hand(new Hid(Seat.YOU));
        
        // Put two cards in hand: 7+3
        Card card1 = new Card(7,Card.Suit.CLUBS);
        Card card2 = new Card(3,Card.Suit.DIAMONDS);
        
        myHand.hit(card1);
        myHand.hit(card2);
        
        // Create dealer up card: 10
        System.out.println("Boundary Condition (Hand vs Up Card = advice): (7+3) vs 10 = HIT");
        Card upCard = new Card(10,Card.Suit.HEARTS);
        
        // Construct advisor and test it
        AdvisorPlugin advisor = new AdvisorPlugin();
  
        Play advice = advisor.advise(myHand, upCard);
        System.out.println("advise received for above condition: " + advice + "\n");

        // Validate the advise
        assertEquals(advice, Play.HIT);
    }
}