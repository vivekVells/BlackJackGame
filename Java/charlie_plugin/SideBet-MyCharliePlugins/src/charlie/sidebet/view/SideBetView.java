package charlie.sidebet.view;

import charlie.audio.Effect;
import charlie.audio.SoundFactory;
import charlie.card.Hid;
import charlie.plugin.ISideBetView;
import charlie.view.AMoneyManager;
import charlie.view.sprite.Chip;

import charlie.view.sprite.ChipButton;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 * This class implements the side bet view
 * 
 * @author Vivek Vellaiyappan | vivekvellaiyappans@gmail.com
 */
public class SideBetView implements ISideBetView {
    private final Logger LOG = Logger.getLogger(SideBetView.class);
   
    public final static int X = 400;
    public final static int Y = 200;
    public final static int DIAMETER = 50;
    
    //Used for placing chips in random
    protected Random ran = new Random();
    // Used for rendering
    Graphics2D graphicsObject;

    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected Font fontBet = new Font("Arial", Font.BOLD, 12);
    protected BasicStroke stroke = new BasicStroke(3);

    // See http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);

    protected List<ChipButton> buttons;
    protected int amt = 0;
    protected AMoneyManager moneyManager;
    
    int width;
    private double PAYOFF;
    //***
    protected List<charlie.view.sprite.Chip> chips = new ArrayList<>();

    public SideBetView() {
        LOG.info("side bet view constructed");
        moneyManager = new AMoneyManager();
    }

    /**
     * Sets the money manager.
     * @param moneyManager
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
        this.buttons = moneyManager.getButtons();
    } 

    /**
     * Registers a click for the side bet.
     * @param x X coordinate
     * @param y Y coordinate
     */
    @Override
    public void click(int x, int y) {
        int oldAmt = amt;

        // Test if any chip button has been pressed.
        for(ChipButton button: buttons) {
            LOG.info("chips button: " + button);
            if (button.isReady() && button.isPressed(x, y)) {
                amt += button.getAmt();
                // For Sound while adding Chips
                SoundFactory.play(Effect.CHIPS_IN);
                
                int n = chips.size();
                //Add chips with random position
                width = button.getImage().getWidth(null);
                int placeX = X + (n) * width / 3 + ran.nextInt(10) - 10;
                int placeY = Y + ran.nextInt(15) + 15;
                charlie.view.sprite.Chip chip = 
                        new charlie.view.sprite.Chip(button.getImage(), 
                                placeX, placeY, amt);
                chips.add(chip);

                LOG.info("A. side bet amount "+button.getAmt()+
                        " updated new amt = "+amt);
            }
        }

        if(oldAmt == amt) {
            if (x < (X + DIAMETER / 2) && x > X - (DIAMETER / 2) && 
                    y > (Y - DIAMETER / 2) && y < (Y + DIAMETER / 2)) {
                amt = 0;
                chips.clear();
                // For Sound while removing Chips
                SoundFactory.play(Effect.CHIPS_OUT);
                LOG.info("B. side bet amount cleared");
            }
        }
    }

    /**
     * Informs view the game is over and it's time to update the bankroll for the hand.
     * @param hid Hand id
     */
    @Override
    public void ending(Hid hid) {
        double bet = hid.getSideAmt();

        if(bet == 0)
            return;

        LOG.info("side bet outcome = "+bet);

        // Update the bankroll
        moneyManager.increase(bet);
        PAYOFF = bet;
        LOG.info("new bankroll = "+moneyManager.getBankroll());
    }

    /**
     * Informs view the game is starting
     */
    @Override
    public void starting() {
        //Set PAYOFF to zero for every new round
        PAYOFF = 0;
    }

    /**
     * Gets the side bet amount.
     * @return Bet amount
     */
    @Override
    public Integer getAmt() {
        return amt;
    }

    /**
     * Updates the view
     */
    @Override
    public void update() {
    }

    /**
     * Renders the view
     * @param g Graphics context
     */
    @Override
    public void render(Graphics2D g) {
        graphicsObject = g;
        // Draw the at-stake place on the table
        g.setColor(Color.RED);
        g.setStroke(dashed);
        g.drawOval(X-DIAMETER/2, Y-DIAMETER/2, DIAMETER, DIAMETER);

        // Draw the at-stake amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(""+amt, X-5, Y+5);
        
        // Text for SideBets Payout
        g.setColor(Color.WHITE);
        g.setFont(fontBet);
        g.drawString("SUPER 7 pays 3:1", X + 60, Y - 25);
        g.drawString("ROYAL MATCH pays 25:1", X + 60, Y - 10);
        g.drawString("EXACTLY 13 pays 1:1", X + 60, Y + 5);
        
        //Render the chips on the table
        for (int i = 0; i < buttons.size(); i++) {
            ChipButton button = buttons.get(i);
            button.render(g);
        }

        for (int i = 0; i < chips.size(); i++) {
            Chip chip = chips.get(i);
            chip.render(g);
        }

        //Render the state of the bet
        renderState(graphicsObject);
    }
    
    /*
     * This method checks the bet amount and the PAYOFF and
     * displays whether it is a win or lose for the side bets.
     * 
     */
    protected void renderState(Graphics2D g) {
        try {
            String result;

            //Check if the PAYOFF is zero and return as no action is needed
            if (PAYOFF == 0) {
                return;
            } 
            
            //If PAYOFF is higher than or same as bet amount then it is a WIN situation
            else if (PAYOFF >= amt) {
                result = "WIN";
            } 

            //If PAYOFF is less than bet amount than it is a loss.
            else {
                result = "LOSE";
            }

            //Set background color for ovals based on Win or Lose
            if ("LOSE".equals(result)) {
                g.setColor(new Color(205, 92, 92));
            } else if ("WIN".equals(result)) {
                g.setColor(new Color(0, 255, 0));
            }

            //Set the font
            Font outcomeFont = new Font("Arial", Font.BOLD, 18);
            FontMetrics fm = g.getFontMetrics(outcomeFont);
            
            //Form the result text that needs to be displayed.
            String outcomeText = " " + result.toUpperCase() + " ! ";
            
            //Get the height and width of the text that will be displayed
            int w = fm.charsWidth(outcomeText.toCharArray(), 0, 
                    outcomeText.length());
            int h = fm.getHeight();
            
            //Paint the oval with the background color.
            g.fillRoundRect(X + 10, Y - h + 50, w, h, 5, 5);

            // Paint the outcome foreground
            if ("LOSE".equals(result)) {
                g.setColor(Color.WHITE);
            } else if ("WIN".equals(result)) {
                g.setColor(Color.BLACK);
            }

            //Draw the out come text.
            g.setFont(outcomeFont);
            g.drawString(outcomeText, X + 10, Y + 45);
        } 
        catch (Exception ex) {
            System.out.println("Error in SideBetView() renderState method:" + ex.getMessage());
        }
    }
}
