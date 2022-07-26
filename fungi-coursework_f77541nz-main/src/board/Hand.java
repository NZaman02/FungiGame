package board;
import cards.*;
import java.util.ArrayList;

public class Hand implements Displayable{
  private ArrayList<Card> handList;

  @Override
  public void add(Card newCard){
    if (handList == null){
      handList = new ArrayList<Card>();
    }
    handList.add(newCard);
  }

  @Override
  public  int size(){
    if (handList == null){
      return 0;
    }
    return handList.size();
  }


  @Override
  public Card getElementAt(int index){
    if (handList == null){
      return null;
    }
    return handList.get(index);
  }


  @Override
  public Card removeElement(int index){
    if (handList == null){
      return null;
    }
    Card value = handList.get(index);
    handList.remove(index);
    return value;
  }



}
