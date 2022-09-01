import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java class that makes the game Wordle.
 *
 * @author rverma83
 * @version 1.0
 */
public class Jordle extends Application {


    /**
     * Main method of program.
     * @param args Arguments passed in
     */
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        ImageView title = getAnimation(1);
        String w = getWord();
        primaryStage.setTitle("Jordle");
        Group s = makeGroup(w);
        VBox c = new VBox(title, s);
        c.setAlignment(Pos.CENTER);
        Scene t = new Scene(c);
        primaryStage.setScene(t);
        primaryStage.show();
        primaryStage.centerOnScreen();
        playRow(s, w, 0, title);
    }


    /**
     * Gets an animation to play at the top of the window.
     * @param x number to choose either default, win, or lost animation
     * @return ImageView with the animation in it
     */
    public ImageView getAnimation(int x) {
        if (x == 1) {
            ImageView animation = new ImageView("animation.gif");
            animation.setX(60);
            return animation;
        }
        if (x == 2) {
            ImageView animation = new ImageView("win.gif");
            animation.setX(60);
            return animation;
        }
        ImageView animation = new ImageView("lose.gif");
        animation.setX(60);
        return animation;
    }

    /**
     * Makes a group for the game with a 6x5 grid, instruction button, restart button, and status label.
     * @param w Word that must be guessed.
     * @return Group with necessary contents
     */
    public Group makeGroup(String w) {
        HBox y = new HBox(instruction(), restart());
        y.setAlignment(Pos.CENTER);
        y.setSpacing(10);
        VBox x = new VBox(grid(), y, setStatus(new Label(), 0, w));
        x.setAlignment(Pos.CENTER);
        x.setSpacing(10);
        return new Group(x);
    }

    /**
     * Creates a label with text that describes the game's status.
     * @param l A label in which to fill the status text
     * @param i number to select status from either default, defeat, or victory
     * @param w the Word that must be guessed
     * @return a label
     */
    public Label setStatus(Label l, int i, String w) {
        if (i == 0) {
            l.setText("Try guessing a word!");
        }
        if (i == 1) {
            l.setText("Game over. The word was " + w);
        }
        if (i == 2) {
            l.setText("Congratulations! You’ve guessed the word!");
        }
        l.setMinWidth(400);
        l.setAlignment(Pos.CENTER);
        l.setTextAlignment(TextAlignment.CENTER);
        l.setPadding(new Insets(0, 10, 10, 10));

        return l;
    }

    /**
     * Creates restart button.
     * @return A button with text "Restart"
     */
    public Button restart() {
        return new Button("Restart");
    }

    /**
     * Creates a button that pops up a window with instructions on how to play jordle.
     * This method has my Anonymous class requirement.
     * @return the Instructions window
     */
    public Button instruction() {

        Button instructions = new Button("Instructions");
        instructions.setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent ae) {
                        Stage x = new Stage();
                        x.setAlwaysOnTop(true);
                        x.toFront();
                        x.setTitle("Instructions");
                        Text t = new Text("The user has 6 tries to guess a 5-letter word. "
                                + "For each guess the user makes, the letters will "
                                + "either appear green (correct letter in the correct place), "
                                + "yellow (correct letter in the wrong place), or "
                                + "grey (wrong letter in the wrong place). The goal is to guess the word correctly.");
                        t.setTextAlignment(TextAlignment.CENTER);
                        t.setWrappingWidth(380);
                        GridPane p = new GridPane();
                        p.add(t, 0, 0);
                        p.setPadding(new Insets(10, 10, 10, 10));
                        x.setScene(new Scene(p));
                        x.show();
                        x.centerOnScreen();
                    }
                }
        );
        return instructions;
    }

    /**
     * Creates the 6x5 game grid with 6 HBoxes in it.
     * @return a VBox grid
     */
    public VBox grid() {
        VBox x = new VBox(getHBox(), getHBox(), getHBox(), getHBox(), getHBox(), getHBox());
        x.setAlignment(Pos.CENTER);
        x.setSpacing(10);
        x.setPadding(new Insets(10, 0, 0, 0));
        return x;
    }

    /**
     * Randomly selects a word from the provided Words class file. It works with regular and repeated-letter words.
     * @return String word that must be guessed
     */
    public String getWord() {
        return Words.list.get((int) (Math.random() * Words.list.size()));
    }

    /**
     * Creates a Button for the text to be entered into.
     * @return a Button with no text
     */
    public Button getButton() {
        Button b = new Button("");
        b.setPrefSize(50, 50);
        return b;
    }

    /**
     * Creates an HBox with 5 buttons. The HBox represents a word attempt, while each button within is a letter.
     * @return an HBox that is 1x6
     */
    public HBox getHBox() {
        HBox row = new HBox(getButton(), getButton(), getButton(), getButton(), getButton());
        row.setDisable(true);
        row.setAlignment(Pos.CENTER);
        row.setSpacing(10);
        return row;
    }

    /**
     * Runs the game recursively. Has lambda expressions that satisfy the requirement.
     * @param s the Group containing the Game's contents
     * @param w the word that must be guessed
     * @param q an int that tracks which row (or attempt) (of the 6 rows) that the game is currently at
     * @param a the ImageView that contains the animation gif
     * @return an int representing how many characters were in the right spot of an attempt (number of green boxes)
     */
    public int playRow(Group s, String w, int q, ImageView a) {
        if (q >= 6) {
            ((Label) ((VBox) (s.getChildren().get(0))).getChildren().get(2)).setText(setStatus(new Label(), 1, w)
                    .getText());
            a.setImage(getAnimation(3).getImage());
            return 69;
        }
        ((Button) ((HBox) ((VBox) (s.getChildren().get(0))).getChildren().get(1)).getChildren().get(1)).setOnAction(
            ActionEvent -> {
                String next = getWord();
                s.getChildren().remove(0, 1);
                s.getChildren().add(makeGroup(next).getChildren().get(0));
                a.setImage(getAnimation(1).getImage());
                playRow(s, next, 0, a);
            }
        );
        HBox h = (HBox) ((VBox) ((VBox) (s.getChildren().get(0))).getChildren().get(0)).getChildren().get(q);
        h.setDisable(false);
        AtomicInteger finalP = new AtomicInteger(0);
        AtomicBoolean finalE = new AtomicBoolean(false);

        for (int y = 0; y < 5; y++) {
            int finalY = y;
            h.getChildren().get(y).setOnKeyPressed(
                keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        if (((Button) h.getChildren().get(0)).getText().equals("")
                                || ((Button) h.getChildren().get(1)).getText().equals("")
                                || ((Button) h.getChildren().get(2)).getText().equals("")
                                || ((Button) h.getChildren().get(3)).getText().equals("")
                                || ((Button) h.getChildren().get(4)).getText().equals("")) {
                            Stage x = new Stage();
                            x.setAlwaysOnTop(true);
                            x.toFront();
                            x.setTitle("Alert");
                            Text t = new Text("Invalid guess. Please provide a 5-letter word.");
                            t.setWrappingWidth(380);
                            t.setTextAlignment(TextAlignment.CENTER);
                            GridPane p = new GridPane();
                            p.add(t, 0, 0);
                            p.setPadding(new Insets(10, 10, 10, 10));
                            x.setScene(new Scene(p));
                            x.show();
                            x.centerOnScreen();
                            finalE.getAndSet(true);
                            playRow(s, w, q, a);
                        }

                        if (!finalE.get()) {
                            h.setDisable(true);
                            for (int z = 0; z < 5; z++) {
                                if (((Button) h.getChildren().get(z)).getText().equals(w.substring(z, z + 1))) {
                                    h.getChildren().get(z).setStyle("-fx-background-color: #3F704D; ");
                                    h.getChildren().get(z).setOpacity(1.0);
                                    finalP.getAndIncrement();
                                } else if (w.contains(((Button) h.getChildren().get(z)).getText())) {
                                    h.getChildren().get(z).setStyle("-fx-background-color: #FCE883; ");
                                    h.getChildren().get(z).setOpacity(1.0);
                                }
                            }
                            if (finalP.get() == 5) {
                                ((Label) ((VBox) (s.getChildren().get(0))).getChildren().get(2))
                                        .setText(setStatus(new Label(), 2, w).getText());
                                a.setImage(getAnimation(2).getImage());
                            } else if (finalP.get() != 5) {
                                playRow(s, w, q + 1, a);
                            }
                        }
                    } else if (keyEvent.getCode().isLetterKey()) {
                        ((Button) h.getChildren().get(finalY)).setText(keyEvent.getText().substring(0, 1));
                        if (finalY < 4) {
                            h.getChildren().get(finalY + 1).requestFocus();
                        }
                    } else if (keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.DELETE) {
                        ((Button) h.getChildren().get(finalY)).setText("");
                        if (finalY > 0) {
                            h.getChildren().get(finalY - 1).requestFocus();
                        }
                    }
                }
            );
        }
        return finalP.get();
    }
}
