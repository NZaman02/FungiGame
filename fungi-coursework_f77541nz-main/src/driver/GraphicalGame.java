package driver;
import board.*;
import java.util.ArrayList;
import java.io.Console;
import board.*;
import cards.*;
import cards.CardType;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent ;
import javafx.scene.input.KeyCode ;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStroke.*;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.CheckBox;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;




public class GraphicalGame extends Application{
  	private static Player p1, p2;
    public  Player currentPlayer;
    public boolean p2plays;

    //ui elements
    public Text whoTurn;

    public Text cardLeftT;
    public HBox forestCardsBox;

    public HBox forestHBox;
    public FlowPane decayFlowPane;

    public HBox forestLayerHbox;

    public FlowPane p1Display;
    public FlowPane  p1Zone;
    public FlowPane p2Display;
    public FlowPane p2Zone;

    public FlowPane actionsChooserFlow;
    public VBox theActionChosenBox;

    public Text badMoveT;

    public VBox p1AreaVBox;
    public VBox actionZone;
    public VBox p2AreaVBox;

    public HBox playerlevelHbox;
    public VBox topLayerVbox;

    public String mushChose;
    public String panCheck;

    @Override
    public void start (Stage stage) throws FileNotFoundException{
      //says which turn
      whoTurn = new Text();
      whoTurn.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

      //contents for forest layer
      cardLeftT = new Text();
      forestCardsBox = new HBox();

      //create contents for forest level
      forestHBox = new HBox(cardLeftT,forestCardsBox);
      forestHBox.setSpacing(10);
      decayFlowPane = new FlowPane();
      decayFlowPane.setPrefWidth(800);


      //create the forest layers HBox
      //HOLDING cards left - forest - decaypile
      forestLayerHbox = new HBox(forestHBox,decayFlowPane);
      forestLayerHbox.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: brown;");
      forestLayerHbox.setMinHeight(200);
      forestLayerHbox.setPrefWidth(1600);
      forestLayerHbox.setSpacing(20);
      forestLayerHbox.setAlignment(Pos.CENTER_LEFT);

      //contents for each player area
      p1Display = new FlowPane();
      p1Zone = new FlowPane ();
      p2Display = new FlowPane();
      p2Zone = new FlowPane();

      //content of actions zone
      actionsChooserFlow = new FlowPane();
      theActionChosenBox = new VBox();

      badMoveT = new Text();
      String badMoveValue  = "Last Move Not Bad";
      badMoveT.setText(badMoveValue);
      badMoveT.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));


      //creates contents for playerlevel
      p1AreaVBox = new VBox(p1Display,p1Zone);
      actionZone = new VBox(badMoveT,theActionChosenBox,actionsChooserFlow);
      actionZone.setSpacing(10);
      p2AreaVBox = new VBox(p2Display,p2Zone);

      p1AreaVBox.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: brown;");
      actionZone.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: brown;");
      p2AreaVBox.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: brown;");

      p1AreaVBox.setPrefWidth(600);
      p2AreaVBox.setPrefWidth(600);

      actionZone.setPrefWidth(600);

      p1AreaVBox.setPadding(new Insets(10, 10, 10, 10));
      p2AreaVBox.setPadding(new Insets(10, 10, 10, 10));
      actionZone.setPadding(new Insets(10, 10, 10, 10));

      theActionChosenBox.setPadding(new Insets(10, 10, 10, 10));
      theActionChosenBox.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: brown;");
      actionsChooserFlow.setPadding(new Insets(10, 10, 10, 10));
      actionsChooserFlow.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: brown;");

      //create the Player level  HBox
      //HOLDING p1 hand + display - actions zone - p2 hand+display
      playerlevelHbox = new HBox(p1AreaVBox,actionZone,p2AreaVBox);

      playerlevelHbox.setMinHeight(250);
      playerlevelHbox.setAlignment(Pos.CENTER);
      whoTurn.setTextAlignment(TextAlignment.CENTER);

      Label header = new Label("Welcome To Fungi");
      header.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));

      // create a group that HOLDING - Turn, Forest, Player Area
      topLayerVbox = new VBox(header,whoTurn,forestLayerHbox,playerlevelHbox);

      // create and configure a new scene
      Scene scene = new Scene(topLayerVbox, 1920 , 1080 ,Color.WHITE);

//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------


      Board.initialisePiles();
      Board.setUpCards();
      Board.getForestCardsPile().shufflePile();
      //Populate forest
      for (int i=0; i<8;i++) {
        Board.getForest().add(Board.getForestCardsPile().drawCard());
      }
      //Initialise players and populate player hands
      p1  = new Player(); currentPlayer=p1; p2 = new Player();
      p1.addCardtoHand(Board.getForestCardsPile().drawCard());p1.addCardtoHand(Board.getForestCardsPile().drawCard());p1.addCardtoHand(Board.getForestCardsPile().drawCard());
      p2.addCardtoHand(Board.getForestCardsPile().drawCard());p2.addCardtoHand(Board.getForestCardsPile().drawCard());p2.addCardtoHand(Board.getForestCardsPile().drawCard());

      whoTurn.setText("Player 1 Turn");
      whoTurn.setFill(Color.GREEN);
      boolean p2plays=false;

      //puts images into forest cards Hbox

      forestCardsBox.getChildren().clear();
      CardList forestList = Board.getForest();
      for(int i = 0; i<forestList.size();i++){
        String nameForImage = forestList.getElementAt(i).getName().toLowerCase();
        Image image = new Image(new FileInputStream("img/"+nameForImage+".jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imageSizeCalc()*0.6);
        imageView.setFitHeight(imageSizeCalc());
        forestCardsBox.getChildren().addAll(imageView);
        if(forestList.getElementAt(i).getType() == CardType.NIGHTMUSHROOM){
          forestCardsBox.getChildren().addAll(new Label("<- N"));
        }
      }


      //make buttons for actions
      Button action1Butt = new Button("Take Card From Forest");
      Button action2Butt = new Button("Take From Decay");
      Button action3Butt = new Button("Cook Mushrooms");
      Button action4Butt = new Button("Sell Mushrooms");
      Button action5Butt = new Button("Put Down Pan");
      //calls function for code when button clicked
      action1Butt.setOnAction(e -> updateActionChosenBox(1));
      action2Butt.setOnAction(e -> updateActionChosenBox(2));
      action3Butt.setOnAction(e -> updateActionChosenBox(3));
      action4Butt.setOnAction(e -> updateActionChosenBox(4));
      action5Butt.setOnAction(e -> updateActionChosenBox(5));

      actionsChooserFlow.getChildren().clear();
      actionsChooserFlow.getChildren().addAll(action1Butt,action2Butt,action3Butt,action4Butt,action5Butt);

      actionZone.getChildren().clear();
      actionZone.getChildren().addAll(badMoveT,theActionChosenBox,actionsChooserFlow);
      cardLeftT.setText("Cards left :" + Board.getForestCardsPile().pileSize());
      updatePlayerZones();
      updateDecayImg();

      stage.setScene(scene);
      stage.setTitle("Fungi");
      stage.show();
    }

    public void setMush(String choice){
      mushChose = choice;
    }

    public String getMush(){
      return mushChose;
    }

    public void updatePlayerZones() throws FileNotFoundException{
      p1Zone.getChildren().clear();
      p2Zone.getChildren().clear();
      p1Display.getChildren().clear();
      p2Display.getChildren().clear();

      //get players 1 Hand
      Hand p1Hand = p1.getHand();
      for(int i = 0; i<p1Hand.size();i++){
        String nameForImage = p1Hand.getElementAt(i).getName().toLowerCase();
        Image image = new Image(new FileInputStream("img/"+nameForImage+".jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imageSizeCalc()*0.6);
        imageView.setFitHeight(imageSizeCalc());
        p1Zone.getChildren().addAll(imageView);
        if(p1Hand.getElementAt(i).getType() == CardType.NIGHTMUSHROOM){
          p1Zone.getChildren().addAll(new Label("<- N"));
        }
      }

      //get players 2 Hand
      Hand p2Hand = p2.getHand();
      for(int i = 0; i<p2Hand.size();i++){
        String nameForImage = p2Hand.getElementAt(i).getName().toLowerCase();
        Image image = new Image(new FileInputStream("img/"+nameForImage+".jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imageSizeCalc()*0.6);
        imageView.setFitHeight(imageSizeCalc());
        p2Zone.getChildren().addAll(imageView);
        if(p2Hand.getElementAt(i).getType() == CardType.NIGHTMUSHROOM){
          p2Zone.getChildren().addAll(new Label("<- N"));
        }
      }


      //get players 1 Display
      Label p1ScoreT = new Label("P1 Score: " + p1.getScore());
      Label p1SticksT = new Label("P1 Sticks: " + p1.getStickNumber());
      Label p1Size = new Label("P1 Hand Size: " + p1.getHand().size() + " out of " + p1.getHandLimit());
      p1Display.getChildren().addAll(p1ScoreT,p1SticksT);
      Display p1Dis = p1.getDisplay();
      for(int i = 0; i<p1Dis.size();i++){
        String nameForImage = p1Dis.getElementAt(i).getName().toLowerCase();
        Image image = new Image(new FileInputStream("img/"+nameForImage+".jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imageSizeCalc()*0.6);
        imageView.setFitHeight(imageSizeCalc());
        p1Display.getChildren().addAll(imageView);
      }



      //get players 2 Display
      Label p2ScoreT = new Label("P2 Score: " + p2.getScore());
      Label p2SticksT = new Label("P2 Sticks: " + p2.getStickNumber());
      Label p2Size = new Label("P1 Hand Size: " + p2.getHand().size() + " out of " + p2.getHandLimit());
      p2Display.getChildren().addAll(p2ScoreT,p2SticksT);
      Display p2Dis = p2.getDisplay();
      for(int i = 0; i<p2Dis.size();i++){
        String nameForImage = p2Dis.getElementAt(i).getName().toLowerCase();
        Image image = new Image(new FileInputStream("img/"+nameForImage+".jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imageSizeCalc()*0.6);
        imageView.setFitHeight(imageSizeCalc());
        p2Display.getChildren().addAll(imageView);
      }


      Label displayT1 = new Label("P1 Display: ");
      Label displayT2 = new Label("P2 Display: ");

      Label handT1 = new Label("P1 Hand: ");
      Label handT2 = new Label("P2 Hand: ");

      Label p1header = new Label("Player 1 Area");
      p1header.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
      p1header.setTextFill(Color.GREEN);
      Label p2header = new Label("Player 2 Area");
      p2header.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
      p2header.setTextFill(Color.BLUE);


      p1AreaVBox.getChildren().clear();
      p1AreaVBox.getChildren().addAll(p1header,displayT1,p1Display,p1ScoreT,p1SticksT,p1Size,handT1,p1Zone);

      p2AreaVBox.getChildren().clear();
      p2AreaVBox.getChildren().addAll(p2header,displayT2,p2Display,p2ScoreT,p2SticksT,p2Size,handT2,p2Zone);
    }

    public void updateActionChosenBox(int choice) {
      Text boxT = new Text();

      //create grid of mushrooms to choose from if needed
      Button mushChoice1 = new Button("Birch Bolete");
      Button  mushChoice2 = new Button("Chanterelle");
      Button  mushChoice3 = new Button("HenOfWoods");
      Button  mushChoice4 = new Button("Morel");
      Button  mushChoice5 = new Button("LawyersWig");
      Button  mushChoice6 = new Button("HoneyFungus");
      Button  mushChoice7 = new Button("Porcini");
      Button  mushChoice8 = new Button("Shiitake");
      Button  mushChoice9 = new Button("TreeEar");

      GridPane mushChoiceGrid = new GridPane();
      mushChoiceGrid.add(mushChoice1, 0,0);
      mushChoiceGrid.add(mushChoice2, 0,1);
      mushChoiceGrid.add(mushChoice3, 0,2);
      mushChoiceGrid.add(mushChoice4, 1,0);
      mushChoiceGrid.add(mushChoice5, 1,1);
      mushChoiceGrid.add(mushChoice6, 1,2);
      mushChoiceGrid.add(mushChoice7, 2,0);
      mushChoiceGrid.add(mushChoice8, 2,1);
      mushChoiceGrid.add(mushChoice9, 2,2);
      String name = "";

      mushChoice1.setOnAction(e -> setMush("birchbolete"));
      mushChoice2.setOnAction(e -> setMush("chanterelle"));
      mushChoice3.setOnAction(e -> setMush("henofwoods"));
      mushChoice4.setOnAction(e -> setMush("morel"));
      mushChoice5.setOnAction(e -> setMush("lawyerswig"));
      mushChoice6.setOnAction(e -> setMush("honeyfungus"));
      mushChoice7.setOnAction(e -> setMush("porcini"));
      mushChoice8.setOnAction(e -> setMush("shiitake"));
      mushChoice9.setOnAction(e -> setMush("treeear"));


      //creates all the necessary button based on actions
      switch(choice){
        case 1:
        //WHAT ABOUT PAN COOKING
          //allows user to choose a card from forest
          boxT.setText("Choose which card you want to take (8-left, 1-Right)");
          Button card8Button = new Button("Card 8 (6 sticks)");
          Button card7Button = new Button("Card 7 (5 sticks)");
          Button card6Button = new Button("Card 6 (4 sticks)");
          Button card5Button = new Button("Card 5 (3 sticks)");
          Button card4Button = new Button("Card 4 (2 sticks)");
          Button card3Button = new Button("Card 3 (1 sticks)");
          Button card2Button = new Button("Card 2");
          Button card1Button = new Button("Card 1");


          //carry out action if pressed
          card8Button.setOnAction(e -> {try {CarryOutAction1(8);} catch (FileNotFoundException ex) {}});;
          card7Button.setOnAction(e -> {try {CarryOutAction1(7);} catch (FileNotFoundException ex) {}});;
          card6Button.setOnAction(e -> {try {CarryOutAction1(6);} catch (FileNotFoundException ex) {}});;
          card5Button.setOnAction(e -> {try {CarryOutAction1(5);} catch (FileNotFoundException ex) {}});;

          card4Button.setOnAction(e -> {try {CarryOutAction1(4);} catch (FileNotFoundException ex) {}});;
          card3Button.setOnAction(e -> {try {CarryOutAction1(3);} catch (FileNotFoundException ex) {}});;
          card2Button.setOnAction(e -> {try {CarryOutAction1(2);} catch (FileNotFoundException ex) {}});;
          card1Button.setOnAction(e -> {try {CarryOutAction1(1);} catch (FileNotFoundException ex) {}});;

          FlowPane cardChoices = new FlowPane(card8Button,card7Button,card6Button,card5Button,card4Button,card3Button,card2Button,card1Button);
          theActionChosenBox.getChildren().clear();
          theActionChosenBox.getChildren().addAll(boxT,cardChoices);
          break;
        case 2:

          boxT.setText("Press button if you want to take the cards from the decay else choose another action");
          Button confirmDeTake = new Button("Take All the Cards From Decay");
          confirmDeTake.setOnAction(e -> {try {CarryOutAction2();} catch (FileNotFoundException ex) {}});;

          theActionChosenBox.getChildren().clear();
          theActionChosenBox.getChildren().addAll(boxT,confirmDeTake);
          break;
        case 3:
          boxT.setText("Cook mushrooms to get flavour point ");

          Label labelNumOfMushr = new Label("Enter num of mushrooms (night = 2x):");
          TextField numOfMushrInp = new TextField ();

          Label labelNumOfButter = new Label("Enter num of butter (0 for none): ");
          TextField numOfButterInp = new TextField ();

          Label labelNumOfCider = new Label("Enter num of cider (0 for none): ");
          TextField numOfCiderInp = new TextField ();

          Label labelNumOfNight = new Label("Enter num of night mushrooms used (0 for none) ");
          TextField numOfNightInp = new TextField ();

          Button panInHand = new Button("Use a Pan from the hand");
          Button pandInDisp = new Button("Use a Pan from the display");
          panInHand.setOnAction(e ->setPanHand());
          pandInDisp.setOnAction(e ->setPanDisp());

          Button confirmCook = new Button("Click This After Filling Information");
          confirmCook.setOnAction(e ->{try {CarryOutAction3(getMush(),numOfMushrInp.getText(),numOfNightInp.getText(), numOfButterInp.getText(), numOfCiderInp.getText());} catch (FileNotFoundException ex) {}});;

          theActionChosenBox.getChildren().clear();
          theActionChosenBox.setSpacing(10);
          theActionChosenBox.getChildren().addAll(boxT, mushChoiceGrid,labelNumOfMushr,numOfMushrInp,labelNumOfNight,numOfNightInp,labelNumOfButter,numOfButterInp,labelNumOfCider,numOfCiderInp, panInHand,pandInDisp, confirmCook);
          break;
        case 4:
          boxT.setText("Sell your mushrooms for sticks or pick another action");

          Label labelNumOfMush = new Label("Enter num of mushrooms (night counts as 2):");
          TextField numOfMushInp = new TextField ();

          Button confirmSell = new Button("Click This After Filling Information");
          confirmSell.setOnAction(e ->{try {CarryOutAction4(getMush(),numOfMushInp.getText());} catch (FileNotFoundException ex) {}});;

          FlowPane infoToSubmit = new FlowPane(mushChoiceGrid,labelNumOfMush,numOfMushInp,confirmSell);

          theActionChosenBox.getChildren().clear();
          theActionChosenBox.setSpacing(20);
          theActionChosenBox.getChildren().addAll(boxT,infoToSubmit);
          break;
        case 5:
          boxT.setText("Press button if you want to place a pan from your hand else choose another action");
          Button confirmPanPl = new Button("Place Pan in Display");
          confirmPanPl.setOnAction(e -> {try {CarryOutAction5();} catch (FileNotFoundException ex) {}});;

          theActionChosenBox.getChildren().clear();
          theActionChosenBox.getChildren().addAll(boxT,confirmPanPl);
          break;
      }
    }

    public void updateDecayImg() throws FileNotFoundException{
      decayFlowPane.getChildren().clear();
        Label labelDecayPile = new Label("Decay Pile: ");


        decayFlowPane.getChildren().addAll(labelDecayPile);
      //puts images into decay
      ArrayList<Card> decayList = Board.getDecayPile();
      for(int i = 0; i<decayList.size();i++){
        String nameForImage = decayList.get(i).getName().toLowerCase();
        Image image = new Image(new FileInputStream("img/"+nameForImage+".jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imageSizeCalc()*0.6);
        imageView.setFitHeight(imageSizeCalc());
        decayFlowPane.getChildren().addAll(imageView);
        if(decayList.get(i).getType() == CardType.NIGHTMUSHROOM){
          decayFlowPane.getChildren().addAll(new Label("<-N "));
        }

      }
    }


    public void CarryOutAction1(int choice) throws FileNotFoundException{
      boolean succesfullMove=false;
      //draw the card
      if (currentPlayer.takeCardFromTheForest(choice)) {
        if (Board.getForestCardsPile().pileSize()>0) {
          Board.getForest().add(Board.getForestCardsPile().drawCard());
        }
        succesfullMove=true;
      }

      if (succesfullMove) {
        //updates inside board
        if (Board.getForest().size()>0) {
          Board.updateDecayPile();
        }
        if (Board.getForestCardsPile().pileSize()>0) {
          Board.getForest().add(Board.getForestCardsPile().drawCard());
        }

        p2plays=!p2plays;
        if (p2plays){
          //updates whos playing
          currentPlayer=p2;
          whoTurn.setText("Player 2 Turn");
          whoTurn.setFill(Color.BLUE);
        }
        else{
          currentPlayer=p1;
          whoTurn.setText("Player 1 Turn");
          whoTurn.setFill(Color.GREEN);
        }
        badMoveT.setFill(Color.BLACK);
        badMoveT.setText("Last Move Ok");
        theActionChosenBox.getChildren().clear();
        Label newTurnL = new Label("New Turn");
        newTurnL.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        theActionChosenBox.getChildren().addAll(newTurnL);
    }
    else{
      badMoveT.setFill(Color.RED);
      badMoveT.setText("LAST MOVE WAS BAD !!!!");
    }
    updateBoard();
  }

    public void CarryOutAction2() throws FileNotFoundException{
    boolean succesfullMove = false;
    if (currentPlayer.takeFromDecay()) {
      succesfullMove=true;
    }

    if (succesfullMove) {
        //updates inside board
    if (Board.getForest().size()>0) {
      Board.updateDecayPile();
      }
    if (Board.getForestCardsPile().pileSize()>0) {
      Board.getForest().add(Board.getForestCardsPile().drawCard());
    }

      p2plays=!p2plays;
      if (p2plays){
          //updates whos playing
          currentPlayer=p2;
          whoTurn.setText("Player 2 Turn");
          whoTurn.setFill(Color.BLUE);
          }
      else{
        currentPlayer=p1;
        whoTurn.setText("Player 1 Turn");
        whoTurn.setFill(Color.GREEN);
      }
      badMoveT.setFill(Color.BLACK);
      badMoveT.setText("Last Move Ok");
      theActionChosenBox.getChildren().clear();
      Label newTurnL = new Label("New Turn");
      newTurnL.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
      theActionChosenBox.getChildren().addAll(newTurnL);
      updateBoard();

  }
  else{
    badMoveT.setFill(Color.RED);
    badMoveT.setText("LAST MOVE WAS BAD !!!!");
  }
}

    public void CarryOutAction3(String name, String num, String nights, String butters, String cider) throws FileNotFoundException{

      //format before the check
        Integer numberOfMush = Integer.parseInt(num);
        Integer numberOfCiders = Integer.parseInt(cider);
        Integer numberOfButters = Integer.parseInt(butters);
        Integer numberOfNights = Integer.parseInt(nights);

        Hand currentHand = currentPlayer.getHand();
        Stack<Integer> handIndex = new Stack<Integer>();


        int mushInHand = 0;
        int butterInHand = 0;
        int ciderInHand = 0;
        int nightInHand = 0;
        //checks if actually has the things they claim
        for(int i =0;i<currentHand.size();i++){
          if (currentHand.getElementAt(i).getName().equals(name)){
            if(currentHand.getElementAt(i).getType()  == CardType.NIGHTMUSHROOM){
              if(mushInHand<numberOfMush){
                handIndex.push(i);
                mushInHand += 2;
                nightInHand += 1;
              }
            }
            else{
              if(mushInHand<numberOfMush){
                handIndex.push(i);
                mushInHand += 1;
              }
            }
          }
        }
        for(int i =0;i<currentHand.size();i++){
          if (currentHand.getElementAt(i).getName().equals("Cider")){
            if(ciderInHand<numberOfCiders){
              ciderInHand += 1;
              handIndex.push(i);
            }
          }
        }

        for(int i =0;i<currentHand.size();i++){
          if (currentHand.getElementAt(i).getName().equals("Butter")){
            if(butterInHand<numberOfButters){
              butterInHand += 1;
              handIndex.push(i);
            }
          }
        }

        boolean PanExist = false;
        boolean isPanInHand = false;

        if (panCheck.equals("Hand")){
          for(int i =0;i<currentHand.size();i++){
            if (currentHand.getElementAt(i).getName().equals("Pan")){
              if(PanExist == false){
                PanExist = true;
                isPanInHand = true;
                handIndex.push(i);
              }
            }
          }
        }
        else{
          Display currentDisplay = currentPlayer.getDisplay();
          for(int i =0;i<currentDisplay.size();i++){
            if (currentDisplay.getElementAt(i).getName().equals("Pan")){
              if(PanExist == false){
                PanExist = true;
                isPanInHand = false;
                handIndex.push(i);
              }
            }
        }
      }

      boolean validMove = true;
        if (mushInHand < numberOfMush){
          validMove = false;
        }
        if (butterInHand < numberOfButters){
          validMove = false;
        }
        if (ciderInHand < numberOfCiders){
          validMove = false;
        }
        if (nightInHand < numberOfNights){
          validMove = false;
        }
        if (PanExist != true){
          validMove = false;
        }

        if(validMove == true){
          ArrayList<Card> cookingmushrooms = new ArrayList<Card>();
          cookingmushrooms.add(new Pan());
          int numOfDayMush = numberOfMush - numberOfNights;
          // adds the day mushrooms
          for(int i =0; i<numOfDayMush; i++){
            switch(name){
              case "birchbolete":
                cookingmushrooms.add(new BirchBolete(CardType.DAYMUSHROOM));
                break;
              case "chanterelle":
                cookingmushrooms.add(new Chanterelle(CardType.DAYMUSHROOM));
                break;
              case "henofwoods":
                cookingmushrooms.add(new HenOfWoods(CardType.DAYMUSHROOM));
                break;
              case "morel":
                cookingmushrooms.add(new Morel(CardType.DAYMUSHROOM));
                break;
              case "lawyerswig":
                cookingmushrooms.add(new LawyersWig(CardType.DAYMUSHROOM));
                break;
              case "honeyfungus":
                cookingmushrooms.add(new HoneyFungus(CardType.DAYMUSHROOM));
                break;
              case "porcini":
                cookingmushrooms.add(new Porcini(CardType.DAYMUSHROOM));
                break;
              case "shiitake":
                cookingmushrooms.add(new Shiitake(CardType.DAYMUSHROOM));
                break;
              case "treeear":
                cookingmushrooms.add(new TreeEar(CardType.DAYMUSHROOM));
                break;
            }
          }
            //add nights
          if(numberOfNights>0){
            for(int i =0; i<numberOfNights; i++){
              switch(name){
                case "birchbolete":
                cookingmushrooms.add(new BirchBolete(CardType.NIGHTMUSHROOM));
                break;
                case "chanterelle":
                cookingmushrooms.add(new Chanterelle(CardType.NIGHTMUSHROOM));
                break;
                case "henofwoods":
                cookingmushrooms.add(new HenOfWoods(CardType.NIGHTMUSHROOM));
                break;
                case "morel":
                cookingmushrooms.add(new Morel(CardType.NIGHTMUSHROOM));
                break;
                case "lawyerswig":
                cookingmushrooms.add(new LawyersWig(CardType.NIGHTMUSHROOM));
                break;
                case "honeyfungus":
                cookingmushrooms.add(new HoneyFungus(CardType.NIGHTMUSHROOM));
                break;
                case "porcini":
                cookingmushrooms.add(new Porcini(CardType.NIGHTMUSHROOM));
                break;
                case "shiitake":
                cookingmushrooms.add(new Shiitake(CardType.NIGHTMUSHROOM));
                break;
                case "treeear":
                cookingmushrooms.add(new TreeEar(CardType.NIGHTMUSHROOM));
                break;
              }

          }

          }
          //add ciders and butter
          if(numberOfButters>0){
            for (int i=0; i<numberOfButters;i++){
              cookingmushrooms.add(new Butter());
            }
          }
          if(numberOfCiders>0){
            for (int i=0; i<numberOfCiders;i++){
              cookingmushrooms.add(new Cider());
            }
          }

          if (currentPlayer.cookMushrooms(cookingmushrooms) == true){

            if (p2plays){
              //updates whos playing
              currentPlayer=p2;
              whoTurn.setText("Player 2 Turn");
              whoTurn.setFill(Color.BLUE);
            }
            else{
              currentPlayer=p1;
              whoTurn.setText("Player 1 Turn");
              whoTurn.setFill(Color.GREEN);
            }
            badMoveT.setFill(Color.BLACK);
            badMoveT.setText("Last Move Ok");
            theActionChosenBox.getChildren().clear();
            Label newTurnL = new Label("New Turn");
            newTurnL.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
            theActionChosenBox.getChildren().addAll(newTurnL);
          }
          else{
            badMoveT.setFill(Color.RED);
            badMoveT.setText("LAST MOVE WAS BAD !!!!");
          }

        }
        else{
          badMoveT.setFill(Color.RED);
          badMoveT.setText("LAST MOVE WAS BAD !!!!");
          handIndex.clear();
        }

        updateBoard();


    }


    public void CarryOutAction4(String name, String number) throws FileNotFoundException{
      boolean succesfullMove = false;
      int numberOfMush = Integer.parseInt(number);
      if (currentPlayer.sellMushrooms(name,numberOfMush)) {
        succesfullMove=true;
      }
      if (succesfullMove) {
          //updates inside board
      if (Board.getForest().size()>0) {
        Board.updateDecayPile();
        }
      if (Board.getForestCardsPile().pileSize()>0) {
        Board.getForest().add(Board.getForestCardsPile().drawCard());
      }

      p2plays=!p2plays;
      if (p2plays){
    //updates whos playing
        currentPlayer=p2;
        whoTurn.setText("Player 2 Turn");
        whoTurn.setFill(Color.BLUE);
      }
      else{
        currentPlayer=p1;
        whoTurn.setText("Player 1 Turn");
        whoTurn.setFill(Color.GREEN);
      }
      badMoveT.setFill(Color.BLACK);
      badMoveT.setText("Last Move Ok");
      theActionChosenBox.getChildren().clear();
      Label newTurnL = new Label("New Turn");
      newTurnL.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
      theActionChosenBox.getChildren().addAll(newTurnL);
      updateBoard();


    }
    else{
      badMoveT.setFill(Color.RED);
      badMoveT.setText("LAST MOVE WAS BAD !!!!");

    }
  }

    public void CarryOutAction5() throws FileNotFoundException{
  boolean succesfullMove = false;
  if (currentPlayer.putPanDown()) {
    succesfullMove=true;
  }

  if (succesfullMove) {
      //updates inside board
  if (Board.getForest().size()>0) {
    Board.updateDecayPile();
    }
  if (Board.getForestCardsPile().pileSize()>0) {
    Board.getForest().add(Board.getForestCardsPile().drawCard());
  }

    p2plays=!p2plays;
    if (p2plays){
        //updates whos playing
        currentPlayer=p2;
        whoTurn.setText("Player 2 Turn");
        whoTurn.setFill(Color.BLUE);
        }
    else{
      currentPlayer=p1;
      whoTurn.setText("Player 1 Turn");
      whoTurn.setFill(Color.GREEN);
    }
    badMoveT.setFill(Color.BLACK);
    badMoveT.setText("Last Move Ok");
    theActionChosenBox.getChildren().clear();
    Label newTurnL = new Label("New Turn");
    newTurnL.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
    theActionChosenBox.getChildren().addAll(newTurnL);
    updateBoard();

}
else{
  badMoveT.setFill(Color.RED);
  badMoveT.setText("LAST MOVE WAS BAD !!!!");
  }
}
    //calcs size of image
    public int imageSizeCalc(){
      int sizeOfD = 5;
      int sizeOfH = 5;

      if(p1.getDisplay().size()>p2.getDisplay().size()){
         sizeOfD = p1.getDisplay().size();
      }
      else{
        sizeOfD = p2.getDisplay().size();
      }

      if(p1.getHand().size()>p2.getHand().size()){
         sizeOfH = p1.getHand().size();
      }
      else{
         sizeOfH = p2.getHand().size();
      }

      int tot = sizeOfH + sizeOfD;
      int rows = 4;
      if(tot < 10){
        rows = 4;
      }
      else if(tot <30 ){
        rows = 5;
      }
      else if(tot <50){
        rows = 6;
      }


      int imgHeight = 625/rows;

      return imgHeight;

    }


    public void updateBoard() throws FileNotFoundException{
      //puts images into forest cards Hbox

      forestCardsBox.getChildren().clear();

      CardList forestList = Board.getForest();
      for(int i = 0; i<forestList.size();i++){
        String nameForImage = forestList.getElementAt(i).getName().toLowerCase();
        Image image = new Image(new FileInputStream("img/"+nameForImage+".jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imageSizeCalc()*0.6);
        imageView.setFitHeight(imageSizeCalc());
        forestCardsBox.getChildren().addAll(imageView);
        if(forestList.getElementAt(i).getType() == CardType.NIGHTMUSHROOM){
          forestCardsBox.getChildren().addAll(new Label("<- N"));
        }
      }

      cardLeftT.setText("Cards left :" + Board.getForestCardsPile().pileSize());
      if(Board.getForestCardsPile().pileSize() == 0){
        if(Board.getForest().size() == 0){
          showEnd();
        }
      }

      updatePlayerZones();
      updateDecayImg();
    }

    public void setPanHand(){
      this.panCheck = "Hand";
    }

    public void setPanDisp(){
      this.panCheck = "Disp";
    }

    public void showEnd(){
      topLayerVbox.getChildren().clear();
      if(p1.getScore() == p2.getScore()){
        Label sentence = new Label("Tie!");
        Label p1SL = new Label("P1 Score:" + p1.getScore());
        Label p2SL = new Label("P2 Score:" + p2.getScore());

        sentence.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        p1SL.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        p2SL.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        topLayerVbox.getChildren().addAll(sentence,p1SL,p2SL);
      }
      else if(p1.getScore() > p2.getScore()){
        Label sentence = new Label("P1 Wins!");
        Label p1SL = new Label("P1 Score:" + p1.getScore());
        Label p2SL = new Label("P2 Score:" + p2.getScore());

        sentence.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        p1SL.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        p2SL.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        topLayerVbox.getChildren().addAll(sentence,p1SL,p2SL);
      }
      else{
        Label sentence = new Label("P2 Wins!");
        Label p1SL = new Label("P1 Score:" + p1.getScore());
        Label p2SL = new Label("P2 Score:" + p2.getScore());

        sentence.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        p1SL.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        p2SL.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        topLayerVbox.getChildren().addAll(sentence,p1SL,p2SL);
      }


    }

    public static void main(String args[]){
    launch(args);
    }

}
