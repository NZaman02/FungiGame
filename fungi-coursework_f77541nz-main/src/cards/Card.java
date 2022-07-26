package cards;
public abstract class Card{
    protected String cardName;
    protected CardType type;

    public Card(CardType newType, String newCardName ){
      cardName = newCardName;
      type = newType;
    }

    public CardType getType(){
      return type;
    }

    public String getName(){
      return cardName;
    }

}
