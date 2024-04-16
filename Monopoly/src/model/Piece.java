package model;

public class Piece {
    private static final int START_BOX_INDEX_ON_BOARD = 0;
    private static final int JAIL_EXIT_BOX_INDEX_ON_BOARD = 6;
    private static final int JAIL_BOX_INDEX_ON_BOARD = 18;
    private static final int TURNS_IN_JAIL = 3;

    //FIELDS
    private int positionIndex;
    private Box positionBox;
    private boolean inJail;
    private int turnsInJail;
    private Game game;

    //CONSTRUCTORS
    public Piece(Game game) {
        this.positionIndex = START_BOX_INDEX_ON_BOARD;
        this.positionBox = game.getBoard().getBox(START_BOX_INDEX_ON_BOARD);
        this.inJail = false;
        this.turnsInJail = 0;
        this.game = game;
    }

    //METHODS
    public void move(int displacement) {
        if (!inJail) {
            int previousPosition = this.positionIndex;
            int newPosition = (previousPosition + displacement) % game.getBoard().getBoxes().size();
            this.positionIndex = newPosition;
            this.positionBox = game.getBoard().getBoxes().get(newPosition);

            if (this.positionBox.getName().equals("Jail")) {
                this.goToPrison();
            }
        } else {
            this.turnsInJail--;
            if (this.turnsInJail == 0) {
                this.inJail = false;
                this.positionBox = game.getBoard().getBoxes().get(JAIL_EXIT_BOX_INDEX_ON_BOARD);
            }
        }
    }

    public void goToPrison() {
        this.inJail = true;
        this.turnsInJail = TURNS_IN_JAIL;
        this.positionBox = game.getBoard().getBoxes().get(JAIL_BOX_INDEX_ON_BOARD);
    }

    public Box getBox() {
        return this.positionBox;
    }

    public int getIndexBox() {
        return this.positionIndex;
    }

    public boolean isInJail() {
        return this.inJail;
    }
}