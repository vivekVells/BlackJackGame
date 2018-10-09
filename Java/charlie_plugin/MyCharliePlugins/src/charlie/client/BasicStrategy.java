package charlie.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;

/**
 * This class is an implementation of the Basic Strategy rules.
 * @author Vivek Vellaiyappan Surulimuthu | vivekvellaiyappans@gmail.com
 */
public class BasicStrategy {
    
    // to read the Play types in readable format in the table below
    public final static Play P = Play.SPLIT;
    public final static Play H = Play.HIT;
    public final static Play S = Play.STAY;
    public final static Play D = Play.DOUBLE_DOWN;
    
    // section 1 rules table from 12 to 21
    Play[][] section1Rules = {
                // 2  3  4  5  6  7  8  9  T  A
        /* 21 */ { S, S, S, S, S, S, S, S, S, S  },
        /* 20 */ { S, S, S, S, S, S, S, S, S, S  },
        /* 19 */ { S, S, S, S, S, S, S, S, S, S  },
        /* 18 */ { S, S, S, S, S, S, S, S, S, S  },
        /* 17 */ { S, S, S, S, S, S, S, S, S, S  },
        /* 16 */ { S, S, S, S, S, H, H, H, H, H  },
        /* 15 */ { S, S, S, S, S, H, H, H, H, H  },
        /* 14 */ { S, S, S, S, S, H, H, H, H, H  },
        /* 13 */ { S, S, S, S, S, H, H, H, H, H  },
        /* 12 */ { H, H, S, S, S, H, H, H, H, H  }
    };

    // section 2 rules table from 5 to 11
    Play[][] section2Rules = {
                // 2  3  4  5  6  7  8  9  T  A
        /* 11 */ { D, D, D, D, D, D, D, D, D, H  },
        /* 10 */ { D, D, D, D, D, D, D, D, H, H  },
        /*  9 */ { H, D, D, D, D, H, H, H, H, H  },
        /*  8 */ { H, H, H, H, H, H, H, H, H, H  },
        /*  7 */ { H, H, H, H, H, H, H, H, H, H  },
        /*  6 */ { H, H, H, H, H, H, H, H, H, H  },
        /*  5 */ { H, H, H, H, H, H, H, H, H, H  },
    };
    
    // section 3 rules table from A,2 to A,10
    Play[][] section3Rules = {
                  // 2  3  4  5  6  7  8  9  T  A
        /* A,10 */ { S, S, S, S, S, S, S, S, S, S  },
        /* A, 9 */ { S, S, S, S, S, S, S, S, S, S  },
        /* A, 8 */ { S, S, S, S, S, S, S, S, S, S  },
        /* A, 7 */ { S, D, D, D, D, S, S, H, H, H  },
        /* A, 6 */ { H, D, D, D, D, H, H, H, H, H  },
        /* A, 5 */ { H, H, D, D, D, H, H, H, H, H  },
        /* A, 4 */ { H, H, D, D, D, H, H, H, H, H  },
        /* A, 3 */ { H, H, H, D, D, H, H, H, H, H  },
        /* A, 2 */ { H, H, H, D, D, H, H, H, H, H  },        
    };

    // section 4 rules table from 2,2 to A,A pairs
    Play[][] section4Rules = {
                    // 2  3  4  5  6  7  8  9  T  A
        /*  A,  A */ { P, P, P, P, P, P, P, P, P, P  },
        /* 10, 10 */ { S, S, S, S, S, S, S, S, S, S  },
        /*  9,  9 */ { P, P, P, P, P, S, P, P, S, S  },
        /*  8,  8 */ { P, P, P, P, P, P, P, P, P, P  },        
        /*  7,  7 */ { P, P, P, P, P, P, H, H, H, H  },
        /*  6,  6 */ { P, P, P, P, P, H, H, H, H, H  },
        /*  5,  5 */ { D, D, D, D, D, D, D, D, H, H  },
        /*  4,  4 */ { H, H, H, P, P, H, H, H, H, H  },
        /*  3,  3 */ { P, P, P, P, P, P, H, H, H, H  },
        /*  2,  2 */ { P, P, P, P, P, P, H, H, H, H  }
    };    
   
    /**
     * Helps to choose the correct section to execute the test cases
     * @param hand
     * @param upCard
     * @return play ENUM values 
     */
    public Play getPlay(Hand hand, Card upCard) {
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
        
        if(hand.isPair()) {
            System.out.println("Unit Testing for Section 4 to be executed...");            
            return doSection4(hand,upCard);
        }
        else if(hand.size() == 2 && 
                (card1.getRank() == Card.ACE || card2.getRank() == Card.ACE)) {
            System.out.println("Unit Testing for Section 3 to be executed...");            
            return doSection3(hand,upCard);
        }
        else if(hand.getValue() >=5 && hand.getValue() < 12) {
            System.out.println("Unit Testing for Section 2 to be executed...");            
            return doSection2(hand,upCard);
        }
        else if(hand.getValue() >= 12) {
            System.out.println("Unit Testing for Section 1 to be executed...");            
            return doSection1(hand,upCard);
        }
        
        return Play.NONE;
    }
    
    /**
     * Does section 1 processing of the basic strategy, 12-21 (player) vs. 2-A (dealer)
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     * @return play ENUM values 
     */
    protected Play doSection1(Hand hand, Card upCard) {
        int value = hand.getValue();
                System.out.println("hand: " + value);
                
        // Subtract 21 since the player's hand starts at 21 and we're working
        // our way down through section 1
        int rowIndex = 21 - value;
        
        Play[] row = section1Rules[rowIndex];
        
        // Subtract 2 since the dealer's up-card start at 2
        int colIndex = upCard.getRank() - 2;
         
        if(upCard.isFace())
            colIndex = 10 - 2;

        // Ace is the 10th card (index 9)
        else if(upCard.isAce())
            colIndex = 9;
        
        Play play = row[colIndex];
        
        return play;
    }

    /**
     * Does section 2 processing of the basic strategy, 5-11 (player) vs. 2-A (dealer)
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     * @return play ENUM values 
     */
    protected Play doSection2(Hand hand, Card upCard) {
        int value = hand.getValue();
                System.out.println("hand: " + value);

        // Section one table built only for hand values >= 12 (see above).
        if(value >= 12)
            return Play.NONE;
        
        // Subtract 11 since the player's hand starts at 11 and we're working
        // our way down through section 2
        int rowIndex = 11 - value;
        
        Play[] row = section2Rules[rowIndex];
        
        // Subtract 2 since the dealer's up-card start at 2
        int colIndex = upCard.getRank() - 2;
         
        if(upCard.isFace())
            colIndex = 10 - 2;

        // Ace is the 10th card (index 9)
        else if(upCard.isAce())
            colIndex = 9;
        
        Play play = row[colIndex];
        
        return play;
    } 

    /**
     * Does section 3 processing of the basic strategy, A,2 - A,10 (player) vs. 2-A (dealer)
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     * @return play ENUM values 
     */
    protected Play doSection3(Hand hand, Card upCard) {
        int value = hand.getValue();
                System.out.println("hand: " + value);
        
        // Subtract 21 since the player's hand starts at 21 and we're working
        // our way down through section 3
        int rowIndex = 21 - value;
        
        Play[] row = section3Rules[rowIndex];
        
        // Subtract 2 since the dealer's up-card start at 2
        int colIndex = upCard.getRank() - 2;
         
        if(upCard.isFace())
            colIndex = 10 - 2;

        // Ace is the 10th card (index 9)
        else if(upCard.isAce())
            colIndex = 9;
        
        Play play = row[colIndex];
        
        return play;
    }  

    /**
     * Does section 4 processing of the basic strategy, 2,2-A,A (player) vs. 2-A (dealer)
     * @param hand Player's hand
     * @param upCard Dealer's up-card
     * @return play ENUM values 
     */
    protected Play doSection4(Hand hand, Card upCard) {
        int value = hand.getValue();
        System.out.println("hand: " + value);
                
        int rowIndex = 0;
        
        if (value <= 13) {
            rowIndex = 13 - value;
        } else {
            rowIndex = 21 - value;
        }
        System.out.println("rowIndex: " + rowIndex);
        
        Play[] row = section4Rules[rowIndex];
        
        // Subtract 2 since the dealer's up-card start at 2
        int colIndex = upCard.getRank() - 2;
         
        if(upCard.isFace())
            colIndex = 10 - 2;

        // Ace is the 10th card (index 9)
        else if(upCard.isAce())
            colIndex = 9;
        
        Play play = row[colIndex];
        
        return play;
    }    
}
