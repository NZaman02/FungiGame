package cards;
public abstract class Mushroom extends EdibleItem{
  protected int sticksPerMushroom;
  public Mushroom(CardType newType, String newName){
    super(newType,newName);
  }

  public int getSticksPerMushroom(){
    return sticksPerMushroom;
  }

}
