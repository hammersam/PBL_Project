package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import main.model.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerProfileController implements Initializable {

    /**
     * player profile data {{{
     */
    @FXML
    private Text Name;

    @FXML
    private Text ID;

    @FXML
    private Text Position;

    @FXML
    private Text Gender;

    @FXML
    private Text Age;

    @FXML
    private Text Height;

    @FXML
    private Text Weight;

    @FXML
    private Text Club;

    /**
     * }}}
     *
     */

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerProfileController(Player player) {
        setPlayer(player);
    }

    private Player player;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Name.setText(player.getName());
        ID.setText("" + player.getID());
        Position.setText(player.getPosition());
        Gender.setText(player.getGender());
        Age.setText("" + player.getAge());
        Height.setText("" + player.getHeight());
        Weight.setText("" + player.getWeight());
        Club.setText("" + player.getClub());


    }
}
