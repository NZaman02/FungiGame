package cards;
public abstract class EdibleItem extends Card{
  protected int flavourPoints;

  public EdibleItem(CardType newCardType, String newName){
    super(newCardType,newName);
  }

  public int getFlavourPoints(){
    return flavourPoints;
  }

}
