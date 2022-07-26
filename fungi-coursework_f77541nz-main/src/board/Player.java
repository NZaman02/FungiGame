package board;
import cards.*;
import java.util.ArrayList;
import java.util.Stack;

public class Player{
  private Hand h;
  private Display d;
  private int score;
  private int handlimit;
  private int sticks;

  public Player(){
    handlimit = 8;
    score = 0;
    sticks = 0;
    h = new Hand();
    d = new Display();
    d.add(new Pan());
  }

  public int getScore(){
    return this.score;
  }

  public int getHandLimit(){
    handlimit = 8;
    int adder = 0;
    for(int i =0; i<d.size();i++){
      if(d.getElementAt(i).getName().equals("Basket")){
        adder += 2;
      }
    }
    handlimit += adder;
    return this.handlimit;
  }

  public int getStickNumber(){
    return this.sticks;
  }

  public void addSticks(int num){
    this.sticks = sticks + num;
    for(int i=0;i<num;i++){
      d.add(new Stick());
    }

  }

  public void removeSticks(int num){
    this.sticks = sticks - num;

   Stack<Integer> index = new Stack<Integer>();
   for(int i = 0; i<d.size();i++){
     if(d.getElementAt(i).getType() == CardType.STICK){
       if(index.size() < num){
         index.push(i);
       }
     }
   }
   for(int i = 0; i<num;i++){

      int value = index.pop().intValue();
     d.removeElement(value);
   }

  }

  public Hand getHand(){
    return this.h;
  }

  public Display getDisplay(){
    return this.d;
  }

  public void addCardtoHand(Card newCard){
    if(newCard.getName().equals("Basket")){
      addCardtoDisplay(new Basket());
    }
    else{
      h.add(newCard);
    }
  }

  public void addCardtoDisplay(Card newCard){
    d.add(newCard);
  }

  public boolean takeCardFromTheForest(int value){
    CardList theForest = Board.getForest();

    //check right range
    if((value > 8) || (value<0) ){
      return false;
    }

    // check handlimit
    if((getHand().size() + 1)> getHandLimit()){
      return false;
    }

    //check sticks
    int sticksNeeded = value - 2;
    if(sticksNeeded > getStickNumber()){
      return false;
    }
    else{
      if(value != (1) && (value != 2)){
        this.removeSticks(sticksNeeded);
        }
      }

    //check if basket
    if(theForest.size()>8){
      if(theForest.getElementAt(8-value).getName().equals("Basket") ){
        d.add(new Basket());
        theForest.removeCardAt(8-value);
      }
      else{
        addCardtoHand(theForest.removeCardAt(8-value));
      }

    }
    else{
      if(theForest.getElementAt(theForest.size()-value).getName().equals("Basket") ){
        d.add(new Basket());
        theForest.removeCardAt(theForest.size()-value);
      }
      else{
        addCardtoHand(theForest.removeCardAt(theForest.size()-value));
    }
  }
  return true;
}

  public boolean takeFromDecay(){
    ArrayList<Card> theDecay = Board.getDecayPile();
    int decaySize = theDecay.size();
    //the effect the basket would have
    int adder = 0;
    int BasketsIn = 0;

    for(int i = 0; i < Board.getDecayPile().size();i++){
      if (theDecay.get(i).getName().equals("Basket")){
        adder += 2;
        BasketsIn += 1;
        decaySize -= 1;
      }
    }

    int amount = decaySize + getHand().size() ;
    if (amount > (getHandLimit()+adder)){
      return false;
    }
    else{
      for(int i=0; i<theDecay.size();i++){
        addCardtoHand(theDecay.get(i));
      }
      Board.clearDecayPile();

      return true;
    }

  }

  public boolean cookMushrooms(ArrayList<Card> theList){
    boolean PanFound = false;
    int panAt = 0;
    //check for Pan
    //check Display
    for(int i = 0; i<d.size();i++){
      if(d.getElementAt(i).getName().equals("Pan")){
        PanFound = true;
        panAt = 1;
      }
    }
    //check hand
    for(int i = 0; i<getHand().size();i++){
      if(getHand().getElementAt(i).getName().equals("Pan")){
        PanFound = true;
        panAt = 2;
      }
    }
    //check input
    for(int i = 0; i<theList.size();i++){
      if(theList.get(i).getName().equals("Pan")){
        PanFound = true;
      }
    }

    if(PanFound != true){
      return false;
    }

    int numOfmush = 0;
    int totFlavour = 0;

    boolean ciderF = false;
    boolean butterF = false;

    int cFound = 0;
    int bFound = 0;
    ArrayList<String> mNames = new ArrayList<String>();


    //looks through list
      for(int i=0;i<theList.size();i++){
        if( (theList.get(i).getType() == (CardType.NIGHTMUSHROOM)) || (theList.get(i).getType() == (CardType.DAYMUSHROOM)) ){
          if(theList.get(i).getType() == (CardType.DAYMUSHROOM)){
            numOfmush += 1;
            EdibleItem theCard = (EdibleItem) theList.get(i);
            totFlavour += theCard.getFlavourPoints();
          }
          else{
            numOfmush +=2;
            EdibleItem theCard = (EdibleItem) theList.get(i);
            totFlavour += 2*(theCard.getFlavourPoints());
          }
          mNames.add(theList.get(i).getName());

          }
          if(theList.get(i).getType() == CardType.BUTTER){
            butterF = true;
            bFound += 1;
          }

          if(theList.get(i).getType() == CardType.CIDER){
            ciderF = true;
            cFound += 1;
        }
            if(theList.get(i).getType() == CardType.BASKET){
              return false;
            }
      }

    //checks all the same
    for(int i=0;i<mNames.size();i++){
      if(mNames.get(i) != mNames.get(0)){
        return false;
      }
    }

      //need to add score + remove from hand
    int mushRequired = 3;
    if((butterF == true) || (ciderF == true)){
       mushRequired = (cFound*5) + (bFound*4);
    }


    if(numOfmush>=mushRequired){
      int mushRemoved = 0;
      int cRemoved = 0;
      int bRemoved = 0;
      //remove from hand
      for(int i = 0;i<getHand().size();i++){
        //remove mush
        if(mushRemoved < mushRequired){
          if(getHand().getElementAt(i).getName().equals(mNames.get(0))){
            if(getHand().getElementAt(i).getType() == CardType.NIGHTMUSHROOM){
              getHand().removeElement(i);
              mushRemoved+=2;
            }
            else{
              getHand().removeElement(i);
              mushRemoved+=1;
            }
          }
        if(cRemoved<cFound){
          if(getHand().getElementAt(i).getName().equals("Cider")){
            getHand().removeElement(i);
            cRemoved+=1;

          }
        }
        if(bRemoved<bFound){
          if(getHand().getElementAt(i).getName().equals("Butter")){
            getHand().removeElement(i);
            bRemoved+=1;
          }
        }
      }
    }
      //get pan
      if(panAt == 1){
        //hand
        for(int i = 0; i<getHand().size();i++){
          if(getHand().getElementAt(i).getName().equals("Pan")){
            getHand().removeElement(i);
            break;
          }
        }
      }
        else{
          for(int x = 0; x<d.size();x++){
            if(d.getElementAt(x).getName().equals("Pan")){
              d.removeElement(x);
              break;
            }
          }
        }



      this.score += totFlavour;
      this.score += 3*bFound;
      this.score += 5*cFound;
      return true;
    }
    else{
      return false;
    }
}

  public boolean sellMushrooms(String name, int value){
    //formats it
    name = name.toLowerCase();
    name = name.replaceAll("\\s+","");
    //check is valid mush
    String[] allowedNames = {"honeyfungus","treeear","lawyerswig","shiitake","henofwoods","birchbolete","porcini","chanterelle","morel"};
    boolean valid = false;
    for(int i =0;i<allowedNames.length;i++){
      if(name.equals(allowedNames[i])){
        valid = true;
      }
    }

    if(valid == false){
      return false;
    }

    int numOfmushFound = 0;
    int rightCards = 0;
    Stack<Integer> indexForHand = new Stack<Integer>();
    //checks has same type of mush in hand and right amount
    for(int i =0; i<getHand().size(); i++){
      String nameInHand = getHand().getElementAt(i).getName();
      nameInHand = nameInHand.toLowerCase();
      nameInHand = nameInHand.replaceAll("\\s+","");

      //if card actually in hand
      //take in account if night
      if(nameInHand.equals(name)){
        CardType typeInHand = getHand().getElementAt(i).getType();
        if(typeInHand == CardType.NIGHTMUSHROOM){
          numOfmushFound +=2;
          rightCards += 1;
        }
        else if(typeInHand == CardType.DAYMUSHROOM){
          numOfmushFound += 1;
          rightCards += 1;
        }
        //so we know where to remove from

        indexForHand.push(i);

        if(numOfmushFound==value){
          break;
        }
      }
    }

    //do you have the cards you say
    if(numOfmushFound < value){
      return false;
    }

    if(value <2){
      return false;
    }
    //do they have enough
    if(numOfmushFound < 2){
      return false;
    }
    else{
      int stackSize = indexForHand.size();
      //calc num of sticks + removing + add to dis
      int sticksToadd = 0;
      for(int i=0;i<stackSize;i++){
        int theIndex = indexForHand.pop();
        Mushroom theMush = (Mushroom) getHand().getElementAt(theIndex);
        if(theMush.getType() == CardType.DAYMUSHROOM){
          sticksToadd += theMush.getSticksPerMushroom();
        }
        else if(theMush.getType() == CardType.NIGHTMUSHROOM){
          sticksToadd += 2*(theMush.getSticksPerMushroom());
        }
        getHand().removeElement(theIndex);
      }
      this.addSticks(sticksToadd);
      return true;
    }
  }

  public boolean putPanDown(){
    for(int i = 0; i<h.size();i++){
      if(h.getElementAt(i).getName().equals("Pan")){
        d.add(h.getElementAt(i));
        h.removeElement(i);
        return true;
      }
    }


    return false;
  }

}
