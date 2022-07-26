package board;
import java.util.ArrayList;
import cards.*;
public class CardList{
  private ArrayList<Card> cList;

  CardList(){
    cList = new ArrayList<Card>();

  }

  public void add(Card newCard){
    cList.add(0,newCard);
  }

  public int size(){
    return cList.size();
  }

  public Card getElementAt(int index){
    return cList.get(index);
  }

  public Card removeCardAt(int index){
   Card value = cList.get(index);
   cList.remove(index);
   return value;
  }


}
