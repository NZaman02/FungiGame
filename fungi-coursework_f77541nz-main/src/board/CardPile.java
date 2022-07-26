package board;
import java.util.Stack;
import java.util.Collections;
import cards.*;
public class CardPile{
  private Stack<Card> cPile;

  CardPile(){
     cPile = new Stack<Card>();
  }

  public void addCard(Card newCard){
    cPile.push(newCard);
  }

  public Card drawCard(){
    Card value = cPile.pop();
    return value;
  }

  public void shufflePile(){
    Collections.shuffle(cPile);
  }

  public int pileSize(){
    int value = cPile.size();
    return value;
  }

  public boolean isEmpty(){
   boolean value = cPile.isEmpty();
   return value;
  }


}
