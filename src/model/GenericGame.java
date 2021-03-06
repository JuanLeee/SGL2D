package model;


import javafx.util.Pair;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

// TODO: CHANGE GOAL TO BE NON-ESSENTIAL.

/**
 * Generic Game class
 */
public class GenericGame extends Observable {
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 600;
    private static int COLUMNS = 10;
    private static int ROWS = 10;

    private List<Sprite> sprites;
    private List<List<Sprite>> gridPositions;
    private Player player;
    private Goal goal;
    private Observable observers;
    private Boolean isGameOver;
    private Boolean isVictory;
    private int valueCounter1;
    private int valueCounter2;
    private int valueCounter3;
    private int spriteCounter;

    /**
     * Constructor for a GenericGame
     */
    public GenericGame() {
        sprites = new ArrayList<Sprite>();
        gridPositions = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            gridPositions.add(new ArrayList<>());
        }
        observers = new Observable();
        isGameOver = false;
        valueCounter1 = 0;
        valueCounter2 = 0;
        valueCounter3 = 0;
        spriteCounter = 0;
    }

    // TODO: ADD WHEN FINISHED
    /**
     * Updates game parameters, notifies observers, and repaints panel.
     */
    public void update() {
        // repaint game panel in game panel... (DONE)
        setChanged();
        notifyObservers();
        // checkVictory(); NOTE: NOT THE SAME AS isVICTORY, must notify all observers and end the game.
        checkVictory();
        // checkGameOver(); NOTE: See above
        checkGameOver();
    }


    /**
     * Checks if game is over
     * Notifies all observers that game is over and end game
     */
    private void checkGameOver() {
        if(isGameOver()){
            setChanged();
            notifyObservers("endGame");
        }
    }

    /**
     * Checks if victory has been acheived
     * Notifies all observers that victory is acheived and end game
     */
    private void checkVictory(){
        if(isVictory()){
            setChanged();
            notifyObservers("endGame");
        }
    }

    /**
     * Notifies all observers that there was change
     */
    public void notifyObservers(){
        observers.notifyObservers();
    }

    /**
     * KeyEvent listener for the game with basic arrow key listeners and an exit through escape.
     * @param keyCode Key press input
     */
    public void keyPressed(int keyCode) { // ** Probably change to only on down button unless an delay is added. Update changed when
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
            movePlayer("LEFT");
            update();
        }
        else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT) {
            movePlayer("RIGHT");
            update();
        }
        else if (keyCode == KeyEvent.VK_KP_UP || keyCode == KeyEvent.VK_UP) {
            movePlayer("UP");
            update();
        }
        else if (keyCode == KeyEvent.VK_KP_DOWN || keyCode == KeyEvent.VK_DOWN) {
            movePlayer("DOWN");
            update();
        }
        else if (keyCode == KeyEvent.VK_ESCAPE)
            System.exit(0);
    }

    /**
     * Handles
     * @param g panel to paint over
     */
    public void paint(Graphics g) {
        int columnSize = gridPositions.size();
        int rowSize = gridPositions.get(0).size();

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                Pair<Integer, Integer> position = convertGridCoordinates(j, i);
                if (gridPositions.get(i).get(j) != null) {
                    gridPositions.get(i).get(j).paint(g, position.getKey(), position.getValue());
                }
            }
        }
        if (goal != null) {
            goal.paint(g);
        }
        if (player != null) {
            player.paint(g);
        }
    }

    /**
     * Adds a new sprite to sprites if it exists otherwise do nothing.
     * @param name Name of the new sprite
     * @throws Exception If the name of the sprite is "goal" or player", throws IllegalArgumentException
     */
    public void addSprite(String name) throws Exception {
        if (name.toLowerCase().equals("goal") || name.toLowerCase().equals("player")) {
            throw new IllegalArgumentException("Invalid Arguments: Cannot name sprite: " + name + ".");
        }

        if (!containsSprite(name)) {
            sprites.add(new Sprite(name.toLowerCase()));
        }
    }

    /**
     * Creates a new Player if player is not null.
     */
    public void createPlayer() {
        if (player == null) {
            player = new Player("player");
        }
    }

    /**
     * Setter for the properties of a player(XCoords,YCoords)
     * if no player exists makes a player
     * @param xCords X-coordinates of player
     * @param yCords Y-coordinates of player
     */
    public void setPlayerCords(int xCords, int yCords){
        createPlayer();
        player.setxCoord(xCords);
        player.setxCoord(yCords);
    }

    /**
     * Setter for the properties of a player(Color)
     * if no player exists makes a player
     * @param color Color of player
     */
    public void setPlayerColor(String color){
        createPlayer();
        player.setColor(color);
    }

    /**
     * Creates a new Goal if goal is not null.
     */
    public void createGoal() {
        if (goal == null) {
            goal = new Goal("goal");
        }
    }

    /**
     * Setter for the properties of a goal(XCoords)
     * if no goal exists makes a goal
     * @param xCords X-coordinates of goal
     */
    public void setGoalY(int xCords){
        createGoal();
        goal.setxCoord(xCords);
    }

    /**
     * Setter for the properties of a goal(YCoords)
     * if no goal exists makes a goal
     * @param yCords Y-coordinates of goal
     */
    public void setGoalX(int yCords){
        createGoal();
        goal.setyCoord(yCords);
    }

    /**
     * Setter for the properties of a goal(Color)
     * if no goal exists makes a goal
     * @param color Color of goal
     */
    public void setGoalColor(String color){
        createGoal();
        goal.setColor(color);
    }

    /**
     * Checks if the game is over.
     * @return isGameOver
     */
    public Boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Checks if the game won.
     * @return isVictory
     */
    public Boolean isVictory() {
        return isVictory;
    }

    /**
     * Getter for valueCounter1.
     * @return valueCounter1
     */
    public int getValueCounter1() {
        return valueCounter1;
    }

    /**
     * Getter for valueCounter2.
     * @return valueCounter2
     */
    public int getValueCounter2() {
        return valueCounter2;
    }

    /**
     * Getter for valueCounter3.
     * @return valueCounter3
     */
    public int getValueCounter3() {
        return valueCounter3;
    }

    /**
     * Setter for valueCounter1.
     * @param value the value to set valueCounter1 to
     */
    public void setValueCounter1(int value) {
        valueCounter1 = value;
    }

    /**
     * Setter for valueCounter2.
     * @param value the value to set valueCounter2 to
     */
    public void setValueCounter2(int value) {
        valueCounter2 = value;
    }

    /**
     * Setter for valueCounter3.
     * @param value the value to set valueCounter3 to
     */
    public void setValueCounter3(int value) {
        valueCounter3 = value;
    }

    /**
     * Setter for spriteCounter
     * @param value the value to set the spriteCounter to
     */
    public void setSpriteCounter(int value) {
        spriteCounter = value;
    }

    /**
     * Sets the flag for the command on the goal
     * @param command Name of command
     */
    public void setGoalCommand(String command){
        Goal sp = goal;
        setAbstractSpriteCommand(sp,command);
    }

    /**
     * Sets the flag for the command on the sprite
     * @param sprite Name of sprite
     * @param command Name of command
     */
    public void setSpriteCommand(String sprite,String command){
        Sprite sp = findSprite(sprite);
        setAbstractSpriteCommand(sp,command);
    }

    /**
     * Helper Function to take in AbstractSprite and parse command function
     * @param as
     * @param command
     */
    private void setAbstractSpriteCommand(AbstractSprite as, String command){
        AbstractSprite sp = as;
        if(command.length() == 3){
            sp.setEventWinFlag(true);
        }else if(command.length() == 8){
            sp.setEventGameOverFlag(true);
        }else{
            String commandName = command.substring(0,11);
            int counterNum = 0;
            String word = "";
            switch (commandName){
                case "INCCOUNTER1":
                    counterNum = Integer.parseInt(command.substring(12));
                    sp.setEventIncCounter1Flag(true);
                    sp.setActionInt1(counterNum);
                    break;
                case "DECCOUNTER1":
                    counterNum = Integer.parseInt(command.substring(12));
                    sp.setEventDecCounter1Flag(true);
                    sp.setActionInt1(counterNum);
                    break;
                case "INCCOUNTER2":
                    counterNum = Integer.parseInt(command.substring(12));
                    sp.setEventIncCounter2Flag(true);
                    sp.setActionInt1(counterNum);
                    break;
                case "DECCOUNTER2":
                    counterNum = Integer.parseInt(command.substring(12));
                    sp.setEventDecCounter2Flag(true);
                    sp.setActionInt1(counterNum);
                    break;
                case "INCCOUNTER3":
                    counterNum = Integer.parseInt(command.substring(12));
                    sp.setEventIncCounter3Flag(true);
                    sp.setActionInt1(counterNum);
                    break;
                case "DECCOUNTER3":
                    counterNum = Integer.parseInt(command.substring(12));
                    sp.setEventDecCounter3Flag(true);
                    sp.setActionInt1(counterNum);
                    break;
                case "INCSPRITECO":
                    counterNum = Integer.parseInt(command.substring(17));
                    sp.setEventIncSpriteCounterFlag(true);
                    sp.setActionInt1(counterNum);
                    break;
                case "DECSPRITECO":
                    counterNum = Integer.parseInt(command.substring(17));
                    sp.setEventDecSpriteCounterFlag(true);
                    sp.setActionInt1(counterNum);
                    break;
                case "SETSPRITECO":
                    counterNum = Integer.parseInt(command.substring(17));
                    sp.setEventSetSpriteCounterFlag(true);
                    sp.setEventSpriteCounter(counterNum);
                    sp.setActionInt1(counterNum);
                    break;
                case "TRANSFORMON":
                    word = command.substring(23);
                    sp.setEventTransformOnZeroCounterFlag(true);
                    sp.setActionString(word);
                    break;
                case "GAMEOVERONZ":
                    sp.setEventGameOverOnZeroCounterFlag(true);
                    break;
                case "WINONZEROCO":
                    sp.setEventWinOnZeroCounterFlag(true);
                    break;
                case "MOVETORANDO":
                    word = command.substring(13);
                    sp.setEventMoveToRandomFlag(true);
                    sp.setActionString(word);
                    break;
                case "TRANSFORMTO":
                    word = command.substring(18);
                    sp.setEventTransformToSpriteFlag(true);
                    sp.setActionString(word);
                    break;
                case "MOVEPLAYERT":
                    int x = command.indexOf("X=");
                    int y = command.indexOf("Y=");
                    int xInt = 0;
                    int yInt = 0;
                    if(x > y){
                        yInt = Integer.parseInt(command.substring(y+1,x));
                        xInt = Integer.parseInt(command.substring(x+1));
                    }else{
                        xInt = Integer.parseInt(command.substring(x+1,y));
                        yInt = Integer.parseInt(command.substring(y+1));
                    }
                    sp.setEventMovePlayerToFlag(true);
                    sp.setActionInt1(xInt);
                    sp.setActionInt2(yInt);
                    break;
            }
        }
    }

    /**
     * Finds a sprite given the name in sprites if it exists otherwise return null.
     * @param name the name of the sprite to find
     * @return the sprite matching the name or null
     */
    private Sprite findSprite(String name) {
        if (sprites == null) {
            return null;
        }

        for (Sprite aSprite : sprites) {
            if (aSprite.getName().equals(name.toLowerCase())) {
                return aSprite;
            }
        }
        return null;
    }
    /**
     * Setter for Sprite color if sprite exists
     * if no sprite do nothing
     * @param name name is name of sprite
     * @param color is the color set for the sprite
     */
    public void setSpriteColor(String name,String color){
        Sprite tempSprite = findSprite(name);
        if(tempSprite != null){
            tempSprite.setColor(color);
        }
    }

    /**
     * Setter for Sprite solid property if sprite exists
     * if no sprite do nothing
     * @param name name is name of sprite
     * @param solid is the solid boolean for the sprite
     */
    public void setSpriteSoild(String name,Boolean solid){
        Sprite tempSprite = findSprite(name);
        if(tempSprite != null){
            tempSprite.setSolid(solid);
        }
    }

    /**
     * Setter for COLUMNS
     * @param YINT is the value of the COLUMNS
     */
    public void setColumns(int YINT){
        COLUMNS = YINT;
    }

    /**
     * Setter for ROWS
     * @param XINT is the value of the COLUMNS
     */
    public void setRows(int XINT){
        ROWS = XINT;
    }

    /**
     * Checks if sprites contains a given sprite name.
     * @param name the name of the sprite to find
     * @return true or false
     */
    private Boolean containsSprite(String name) {
        if (sprites == null) {
            return false;
        }

        for (Sprite aSprite : sprites) {
            if (aSprite.getName().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts given grid coordinates to position coordinates in the GamePanel.
     * @param xPos x-position in grid coordinates
     * @param yPos y-position in grid coordinates
     * @return a Pair containing x-coordinate and y-coordinate
     */
    private Pair<Integer,Integer> convertGridCoordinates(int xPos, int yPos) {
        return new Pair<Integer,Integer>(xPos*GAME_WIDTH/COLUMNS,yPos*GAME_HEIGHT/ROWS);
    }

    /**
     * Converts given position coordinates to grid coordinates in the GenericGame.
     * @param xCoord x-coordinate
     * @param yCoord y-coordinate
     * @return a Pair containing x-position and y-position
     */
    private Pair<Integer,Integer> convertToGridCoordinates(int xCoord, int yCoord) {
        return new Pair<Integer,Integer>(xCoord/(GAME_WIDTH/COLUMNS),yCoord/(GAME_HEIGHT/ROWS));
    }

    /**
     * KeyHandler for arrow key movement.
     * @param direction the string containing the direction to move the player
     */
    private void movePlayer(String direction) {
        Pair<Integer,Integer> playerPos = convertToGridCoordinates(player.getxCoord(), player.getyCoord());
        switch(direction) {
            case("LEFT"):
                if (checkBoundary(playerPos.getKey(), playerPos.getValue())) {
                    if (checkLocationMovable(playerPos.getKey()-1, playerPos.getValue())) {
                        Pair<Integer,Integer> playerCoord = convertGridCoordinates(playerPos.getKey()-1, playerPos.getValue());
                        player.setxCoord(playerCoord.getKey());
                        player.setyCoord(playerCoord.getValue());
                        performSpriteEvents(playerPos.getKey(), playerPos.getValue()-1);
                    }
                }
                break;
            case("RIGHT"):
                if (checkBoundary(playerPos.getKey(), playerPos.getValue())) {
                    if (checkLocationMovable(playerPos.getKey()+1, playerPos.getValue())) {
                        Pair<Integer,Integer> playerCoord = convertGridCoordinates(playerPos.getKey()+1, playerPos.getValue());
                        player.setxCoord(playerCoord.getKey());
                        player.setyCoord(playerCoord.getValue());
                        performSpriteEvents(playerPos.getKey(), playerPos.getValue()-1);
                    }
                }
                break;
            case("DOWN"):
                if (checkBoundary(playerPos.getKey(), playerPos.getValue())) {
                    if (checkLocationMovable(playerPos.getKey(), playerPos.getValue()+1)) {
                        Pair<Integer,Integer> playerCoord = convertGridCoordinates(playerPos.getKey(), playerPos.getValue()+1);
                        player.setxCoord(playerCoord.getKey());
                        player.setyCoord(playerCoord.getValue());
                        performSpriteEvents(playerPos.getKey(), playerPos.getValue()-1);
                    }
                }
                break;
            case("UP"):
                if (checkBoundary(playerPos.getKey(), playerPos.getValue())) {
                    if (checkLocationMovable(playerPos.getKey(), playerPos.getValue()-1)) {
                        Pair<Integer,Integer> playerCoord = convertGridCoordinates(playerPos.getKey(), playerPos.getValue()-1);
                        player.setxCoord(playerCoord.getKey());
                        player.setyCoord(playerCoord.getValue());
                        performSpriteEvents(playerPos.getKey(), playerPos.getValue()-1);
                    }
                }
                break;
        }
    }

    /**
     * Checks if a given position coordinate is out of bounds.
     * @param xPos x-position
     * @param yPos y-position
     * @return true or false
     */
    private Boolean checkBoundary(int xPos, int yPos) {
        if (xPos < 0 || xPos > COLUMNS-1) {
            return false;
        }
        if (yPos < 0 || yPos > ROWS-1) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a given position coordinate contains a sprite that is a solid.
     * @param xPos x-position
     * @param yPos y-position
     * @return true or false
     */
    private Boolean checkLocationMovable(int xPos, int yPos) {
        return !gridPositions.get(xPos).get(yPos).getSolid();
    }

    /**
     * SpriteHandler for sprite events after player movement.
     * @param xPos x-position
     * @param yPos y-position
     */
    private void performSpriteEvents(int xPos, int yPos) {
        Sprite eventSprite = gridPositions.get(xPos).get(yPos);
        if (eventSprite.eventIncCounter1Flag.equals(true)) {
            setValueCounter1(valueCounter1 + 1);
        }
        if (eventSprite.eventDecCounter1Flag.equals(true)) {
            setValueCounter1(valueCounter1 - 1);
        }
        if (eventSprite.eventIncCounter2Flag.equals(true)) {
            setValueCounter2(valueCounter2 + 1);
        }
        if (eventSprite.eventDecCounter2Flag.equals(true)) {
            setValueCounter2(valueCounter2 - 1);
        }
        if (eventSprite.eventIncCounter3Flag.equals(true)) {
            setValueCounter3(valueCounter2 + 1);
        }
        if (eventSprite.eventDecCounter3Flag.equals(true)) {
            setValueCounter3(valueCounter2 - 1);
        }
        if (eventSprite.eventIncSpriteCounterFlag.equals(true)){
            setSpriteCounter(spriteCounter + 1);
        }
        if (eventSprite.eventDecSpriteCounterFlag.equals(true)){
            setSpriteCounter(spriteCounter - 1);
        }
        if (eventSprite.eventSetSpriteCounterFlag.equals(true)){
            setSpriteCounter(eventSprite.getActionInt1());
        }
        if (eventSprite.eventTransformOnZeroCounterFlag.equals(true)){

        }
        if (eventSprite.eventGameOverOnZeroCounterFlag.equals(true)){

        }
        if (eventSprite.eventWinOnZeroCounterFlag.equals(true)){

        }
        if (eventSprite.eventMoveToRandomFlag.equals(true)){

        }
        if (eventSprite.eventTransformToSpriteFlag.equals(true)){

        }
        if (eventSprite.eventMovePlayerToFlag.equals(true)){

        }
        if (eventSprite.eventWinFlag.equals(true)){
            isVictory = true;
        }
        if (eventSprite.eventGameOverFlag.equals(true)){
            isGameOver = true;
        }
    }
}
