package charlie.sidebet.test;

import charlie.card.Card;

/**
 *
 * @author Vivek Vellaiyappan | vivekvellaiyappans@gmail.com
 */
public class Super7Shoe extends charlie.card.Shoe {

    @Override
    public void init() {
        super.init(); 
        cards.clear();
       
        // Scenario 1 - player wins => player vs dealer => 19 vs 18
        cards.add(new Card(7,Card.Suit.HEARTS));        // YOU: 7
        cards.add(new Card(Card.KING,Card.Suit.CLUBS)); // DEALER: 10
        
        cards.add(new Card(9,Card.Suit.SPADES));        // YOU: 16
        cards.add(new Card(8,Card.Suit.DIAMONDS));      // DEALER: 18
        
        cards.add(new Card(3,Card.Suit.CLUBS));         // Hit YOU: 19            

        // Scenario 2 - player wins => player vs dealer => 18 vs 17
        cards.add(new Card(6,Card.Suit.HEARTS));        // YOU: 6
        cards.add(new Card(Card.KING,Card.Suit.CLUBS)); // DEALER: 10
        
        cards.add(new Card(9,Card.Suit.SPADES));        // YOU: 15
        cards.add(new Card(7,Card.Suit.DIAMONDS));      // DEALER: 17
        
        cards.add(new Card(3,Card.Suit.CLUBS));         // Hit YOU: 18
        
        // Scenario 3 - player loses => player vs dealer => 15 vs 17
        cards.add(new Card(7,Card.Suit.HEARTS));        // YOU: 7
        cards.add(new Card(Card.KING,Card.Suit.CLUBS)); // DEALER: 10
        
        cards.add(new Card(5,Card.Suit.SPADES));        // YOU: 12
        cards.add(new Card(7,Card.Suit.DIAMONDS));      // DEALER: 17
        
        cards.add(new Card(3,Card.Suit.CLUBS));         // Hit YOU: 15

        // Scenario 4 - player loses => player vs dealer => 15 vs 17
        cards.add(new Card(6,Card.Suit.HEARTS));        // YOU: 6
        cards.add(new Card(Card.KING,Card.Suit.CLUBS)); // DEALER: 10
        
        cards.add(new Card(6,Card.Suit.SPADES));        // YOU: 12
        cards.add(new Card(7,Card.Suit.DIAMONDS));      // DEALER: 17
        
        cards.add(new Card(3,Card.Suit.CLUBS));         // Hit YOU: 15        
        
    }
    
    @Override
    public boolean shuffleNeeded() {
        return false; 
    }    
}
