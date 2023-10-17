package model;

public class Model {
    private final int molesNumber;
    private int points;
    private final boolean[][] boardGame; //True if there's a mole in the position

    public Model(int boardGameSize, int moles) {
        this.molesNumber = moles;
        this.points = 0;
        this.boardGame = new boolean[boardGameSize][boardGameSize];
    }

    public int getBoardGameSize() {
        return this.boardGame.length;
    }

    public int getMolesNumber() {
        return molesNumber;
    }

    /**
     * Function that increases the total points of the user and shows them.
     */
    public void increasePoints() {
        this.points++;
        System.out.println("Total points: " + this.points);
    }

    /**
     * Function that checks if a position is occupied by a mole or not. If it isn't it reserves the position
     * for the actual mole that is checking.
     * @param c
     * @return true if there isn't a mole in the position, otherwise returns false.
     */
    public synchronized boolean checkIfNew(Coordinate c) {
        if (this.boardGame[c.getRow()][c.getCol()]) {
            return false;
        }

        this.boardGame[c.getRow()][c.getCol()] = true;
        return true;
    }

    /**
     * Sets a position to false in order to let other moles occupied it.
     * @param c
     */
    public synchronized void resetPosition(Coordinate c) {
        this.boardGame[c.getRow()][c.getCol()] = false;
    }
}
