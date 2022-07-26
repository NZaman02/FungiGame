package board;
import cards.*;
public interface Displayable{
  void add(Card newCard);

  int size();

  Card getElementAt(int index);

  Card removeElement(int index);


}
