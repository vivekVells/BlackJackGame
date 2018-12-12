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
   
    protected Random ran = new Random();
    Graphics2D graphicsObject;

    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected Font fontBet = new Font("Arial", Font.BOLD, 12);
    protected BasicStroke stroke = new BasicStroke(3);
    private double PAYOFF;
    int width;
    protected List<charlie.view.sprite.Chip> chips = new ArrayList<>();

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
            if(button.isPressed(x, y)) {
                amt += button.getAmt();
                LOG.info("A. side bet amount "+button.getAmt()+" updated new amt = "+amt);
            } 
        }
        
        if(oldAmt == amt) {
            amt = 0;
            LOG.info("B. side bet amount cleared");
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

        moneyManager.increase(bet);
        PAYOFF = bet;
        LOG.info("Updated new bankroll = "+ moneyManager.getBankroll());
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
        renderState(graphicsObject);
    }
    
    /**
     * 
     * @param g 
     */
    protected void renderState(Graphics2D g) {
        try {
            String result;

            if (PAYOFF == 0) {
                return;
            } else if (PAYOFF >= amt) {
                result = "WIN";
            } else {
                result = "LOSE";
            }

            if ("LOSE".equals(result)) {
                g.setColor(new Color(205, 92, 92));
            } else if ("WIN".equals(result)) {
                g.setColor(new Color(0, 255, 0));
            }

            Font outcomeFont = new Font("Arial", Font.BOLD, 18);
            FontMetrics fm = g.getFontMetrics(outcomeFont);
            
            String outcomeText = " " + result.toUpperCase() + " ! ";
           
            int w = fm.charsWidth(outcomeText.toCharArray(), 0, 
                    outcomeText.length());
            int h = fm.getHeight();
            g.fillRoundRect(X + 10, Y - h + 50, w, h, 5, 5);

            if ("LOSE".equals(result)) {
                g.setColor(Color.WHITE);
            } else if ("WIN".equals(result)) {
                g.setColor(Color.BLACK);
            }

            g.setFont(outcomeFont);
            g.drawString(outcomeText, X + 10, Y + 45);
        } 
        catch (Exception ex) {
            System.out.println("Error in SideBetView() renderState method:" + ex.getMessage());
        }
    }
}
