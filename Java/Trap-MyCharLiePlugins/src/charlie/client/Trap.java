/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.client;

import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.message.Message;
import charlie.message.view.to.Outcome;
import charlie.message.view.to.Win;
import org.apache.log4j.Logger;

/**
 *
 * @author maristuser
 */
public class Trap implements ITrap {
  protected static Logger LOG = Logger.getLogger(Trap.class);
  protected int youWin = 0; 
  @Override 
  public void onReceive(Message message) { 
    if(message instanceof Win) { 
      Hid hid = ((Outcome) message).getHid(); 
      if(hid.getSeat() == Seat.YOU) 
        youWin++; 
    }
  }   
  @Override 
  public void onSend(Message message) { 
  }       
}
