import javafx.beans.property.*;
import javafx.collections.FXCollections;

public class TicTacToeViewModel {
    private TicTacToeGame game;
    private StringProperty labelText = new SimpleStringProperty("Put X in an empty box");
    private BooleanProperty gameDone = new SimpleBooleanProperty();
    private ListProperty<TicTacToeView.GameResultObject> results = new SimpleListProperty<>(FXCollections.observableArrayList());
    private Integer oScore;
    private Integer xScore;

    public boolean isGameDone() {
        return gameDone.get();
    }

    public BooleanProperty gameDoneProperty() {
        return gameDone;
    }

    public void setGameDone(boolean gameDone) {
        this.gameDone.set(gameDone);
    }

    public ListProperty<TicTacToeView.GameResultObject> resultsProperty() {
        return results;
    }

    public void setLabelText(String labelTextProperty) {
        this.labelText.set(labelTextProperty);
    }

    public StringProperty labelTextProperty() {
        return labelText;
    }

    public TicTacToeViewModel() {
        game = new TicTacToeGame();
        oScore = 0;
        xScore = 0;
        results.add(new TicTacToeView.GameResultObject(0, 0));
        gameDone.bindBidirectional(game.gameDoneProperty());
    }

    public String processBoxClick(int i, int j) {
        int clickResult = game.processMove(i , j);
        if (isGameDone() && game.getGameResult().equals("Draw")) {
            xScore++;
            oScore++;
            setGameScore();
            setLabelText(game.getGameResult());

            if (clickResult == 1) {
                return "X";
            } else {
                return "O";
            }
        } else if (!game.getGameResult().equals("Draw")) {
            if (clickResult == 1) {
                if (isGameDone()) {
                    xScore++;
                    setGameScore();
                    setLabelText(game.getGameResult());
                } else
                    setLabelText("Put O in an empty box");

                return "X";

            } else if (clickResult == 0) {
                if (isGameDone()) {
                    oScore++;
                    setGameScore();
                    setLabelText(game.getGameResult());
                } else
                    setLabelText("Put X in an empty box");

                return "O";

                // При нажатии на зянятые ячейки
            } else if (clickResult == 2) {
                return "O";
            } else if (clickResult == 3) {
                return "X";
            }
        }
        return "Something";
    }

    private void setGameScore() {
        results.get(0).crossWins().setValue(xScore.toString());
        results.get(0).naughtWins().setValue(oScore.toString());
    }

    public void processNext() {
        game.resetGame();
        setLabelText("Put X in an empty box");
    }

    public void processNew() {
        if(xScore != 0 || oScore != 0)
            results.add(0, new TicTacToeView.GameResultObject(0, 0));
        setLabelText("Put X in an empty box");
        oScore = 0;
        xScore = 0;
        setGameDone(false);
        game.resetGame();
    }

    // TODO implement file methods
    public void processSave() { }
    public void processLoad() { }
}