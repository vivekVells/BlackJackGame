package charlie.bs.section1;

import charlie.client.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.util.Play;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests my 12 vs dealer 2 which should be HIT.
 */
public class Test00_19_7 {
    @Test
    public void test() {
        // Generate an initially empty hand
        Hand myHand = new Hand(new Hid(Seat.YOU));
        
        // Put two cards in hand: 10+9
        Card card1 = new Card(10,Card.Suit.CLUBS);
        Card card2 = new Card(9,Card.Suit.DIAMONDS);
        
        myHand.hit(card1);
        myHand.hit(card2);
        
        // Create dealer up card: 7
        System.out.println("Boundary Condition (Hand vs Up Card = advice): (10 + 9) vs 7 = STAY");
        Card upCard = new Card(7,Card.Suit.HEARTS);
        
        // Construct advisor and test it
        Advisor advisor = new Advisor();
  
        Play advice = advisor.advise(myHand, upCard);
        System.out.println("advise received for above condition: " + advice + "\n");

        // Validate the advise
        assertEquals(advice, Play.STAY);
    }
}