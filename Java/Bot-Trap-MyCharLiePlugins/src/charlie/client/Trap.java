package charlie.client;

import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.message.Message;
import charlie.message.view.to.Blackjack;
import charlie.message.view.to.Bust;
import charlie.message.view.to.Charlie;
import charlie.message.view.to.Loose;
import charlie.message.view.to.Outcome;
import charlie.message.view.to.Push;
import charlie.message.view.to.SplitResponse;
import charlie.message.view.to.Win;
import charlie.plugin.ITrap;
import org.apache.log4j.Logger;

/**
 * This class implements the ITrap that “traps” 
 * inbound and outbound messages which it can use for various purposes, one being to accumulate 
 * outcome statistics of number of wins, losses, Blackjacks, Charlies, etc. for as long as the login session persists.
 * @author Vivek Vellaiyappan Surulimuthu
 */
public class Trap implements ITrap {
  protected static Logger LOG = Logger.getLogger(Trap.class);
  protected int youWin = 0; 
  protected int youLose = 0;
  protected int youPush = 0;
  protected int youBreaks = 0;
  protected int youBlackjacks = 0;  
  protected int youCharlies = 0;  
  protected int youSplits = 0;
  protected int youTotalHands = 0;
  protected Double totalBankRoll = charlie.util.Constant.PLAYER_BANKROLL;
          
  @Override 
  public void onReceive(Message message) { 
    if(message instanceof Win) { 
      Hid hid = ((Outcome) message).getHid(); 
      if(hid.getSeat() == Seat.YOU) 
        youWin++; 
    }
    
    if(message instanceof Loose) { 
         Hid hid = ((Outcome) message).getHid(); 
         if(hid.getSeat() == Seat.YOU) {
           youLose++; 
           totalBankRoll -= hid.getAmt();
         }
    }
    if(message instanceof Push) { 
         Hid hid = ((Outcome) message).getHid(); 
         if(hid.getSeat() == Seat.YOU) 
           youPush++; 
    }
    if(message instanceof Bust) { 
         Hid hid = ((Outcome) message).getHid(); 
         if(hid.getSeat() == Seat.YOU) {
           youBreaks++; 
           totalBankRoll -= hid.getAmt();
         }
    }    
    if(message instanceof Blackjack) { 
         Hid hid = ((Outcome) message).getHid(); 
         if(hid.getSeat() == Seat.YOU) 
           youBlackjacks++; 
    }    
    if(message instanceof Charlie) { 
         Hid hid = ((Outcome) message).getHid(); 
         if(hid.getSeat() == Seat.YOU) 
           youCharlies++; 
    }    
    if(message instanceof SplitResponse) { 
         Hid hid = ((Outcome) message).getHid(); 
         if(hid.getSeat() == Seat.YOU) 
           youSplits++; 
    }
    
    // calculating total hands value
    int handsPlayer = youWin + youLose + youPush;
    int handsDealer = handsPlayer - youSplits;
    
    LOG.info("Wins: " + youWin 
                + " | Lose: " + youLose 
                + " | Push: " + youPush 
                + " | Breaks: " + youBreaks 
                + " | Blackjacks: " + youBlackjacks
                + " | Splits: " + youSplits
                + " | Total Hands: " + handsDealer 
                + " | Bankroll: " + totalBankRoll
    );
  }   
  @Override 
  public void onSend(Message message) { 
  }       
}
