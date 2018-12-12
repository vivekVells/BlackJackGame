package charlie.plugin;

import charlie.card.Card;
import java.awt.Graphics2D;

/**
 * 
 * @author Vivek Vellaiyappan | vivekvellaiyappans@gmail.com
 */
public interface ICardCounter {
	/**
	 * Invoked whenever a new game begins.
	 * @param shoeSize Size of the shoe in number of cards.
	*/
	public void startGame(int shoeSize);

	/**
	 * Invoked when a new card is dealt or dealer reveals hole card.
	 * @param card New card from shoe, might be null if dealer's hole card.
	*/
	public void update(Card card);

    /**
     * Renders card count info.
     * @param g Graphics context referencing the casino table.
    */
	public void render(Graphics2D g);

    /**
    * This method gets invoked before the end of the current game to signal the
    * shoe will be reshuffled before the start of the next game. 
    */
	public void shufflePending();
}