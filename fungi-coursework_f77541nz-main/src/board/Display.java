package board;
import cards.*;
import java.util.ArrayList;
public class Display implements Displayable{
  private ArrayList<Card> displayList;


  @Override
  public void add(Card newCard){
    if (displayList == null){
      displayList = new ArrayList<Card>();
    }
    displayList.add(newCard);
  }

  @Override
  public  int size(){
    if (displayList == null){
      return 0;
    }
    return displayList.size();
  }


  @Override
  public Card getElementAt(int index){
    if (displayList == null){
      return null;
    }
    return displayList.get(index);
  }


  @Override
  public Card removeElement(int index){
    if (displayList == null){
      return null;
    }
    Card value = displayList.get(index);
    displayList.remove(index);
    return value;
  }



}
