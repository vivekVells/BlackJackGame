package charlie.bs.section4;

import charlie.client.AdvisorPlugin;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.util.Play;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests my A,A vs dealer 6 which should be STAY
 */
public class Test00_AA_6 {
    @Test
    public void test() {
        // Generate an initially empty hand
        Hand myHand = new Hand(new Hid(Seat.YOU));
        
        // Put two cards in hand: ACE + ACE
        Card card1 = new Card(Card.ACE,Card.Suit.CLUBS);
        Card card2 = new Card(Card.ACE,Card.Suit.DIAMONDS);
        
        myHand.hit(card1);
        myHand.hit(card2);
        
        // Create dealer up card: 6
        System.out.println("Boundary Condition (Hand vs Up Card = advice): (ACE + ACE) vs 6 = STAY");
        Card upCard = new Card(6,Card.Suit.HEARTS);
        
        // Construct advisor and test it
        AdvisorPlugin advisor = new AdvisorPlugin();
  
        Play advice = advisor.advise(myHand, upCard);
        System.out.println("advise received for above condition: " + advice);

        // Validate the advise
        assertEquals(advice, Play.STAY);
    }
}