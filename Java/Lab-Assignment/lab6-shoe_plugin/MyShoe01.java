package mycharlie.plugin;

import charlie.card.Shoe;
import java.util.Random;


/**
 * This class extends the Shoe base class that implements a six deck shoe, the standard in many houses.
 * @author Vivek Vellaiyappan Surulimuthu | vivekvellaiyappans@gmail.com | Vivek.Surulimuthu1@marist.edu
 */
public class MyShoe01 extends Shoe {
    
    public MyShoe01(){
        super(2);
    }

//  @Override
//  public void init() {
//      super.ran = new Random(/*1*///);
//
//        // to define the number of decks
//        super.numDecks = 1;
//        
//        // Loads all cards in the deck containing 52 cards
//        super.load();
//        
//        // Shuffles the cards
//        super.shuffle();
//    }

}
