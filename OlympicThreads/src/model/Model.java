package Model;

public class Model {
    private boolean[][] boardGame;

    public Model(int filas, int columnas) {
        this.boardGame = new boolean[filas][columnas];
    }

    public void setBoardGame(boolean[][] boardGame) {
        this.boardGame = boardGame;
    }

    public boolean[][] getBoardGame() {
        return boardGame;
    }

    public synchronized boolean checkifNew(Coordinate c) {
        if (this.boardGame[c.getFilas()][c.getColumnas()]) {
            return false;
        } else {
        this.boardGame[c.getFilas()][c.getColumnas()] = true;
        return true;
    }
    }
}
