package main.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Player;

import java.net.URL;
import java.util.ResourceBundle;


public class TopShotterController implements Initializable {

    /**
     * TableView {{{
     */
    @FXML
    private TableView<Player> playerTableView;

    @FXML
    private TableColumn<Player, Integer> rankColumn;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, String> teamColumn;

    @FXML
    private TableColumn<Player, Integer> totalGoalsColumn;

    @FXML
    private TableColumn<Player, Integer> normalGoalsColumn;

    @FXML
    private TableColumn<Player, Integer> positionKickColumn;

    @FXML
    private TableColumn<Player, Integer> ownGoalsColumn;

    /**
     * }}}
     *
     */

    public ObservableList<Player> getList() {
        return list;
    }

    public void setList(ObservableList<Player> list) {
        this.list = list;
    }

    private ObservableList<Player> list;

    public TopShotterController(ObservableList<Player> list) {
        setList(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        totalGoalsColumn.setCellValueFactory(new PropertyValueFactory<>("goals"));
        normalGoalsColumn.setCellValueFactory(new PropertyValueFactory<>("NG"));
        positionKickColumn.setCellValueFactory(new PropertyValueFactory<>("PK"));
        ownGoalsColumn.setCellValueFactory(new PropertyValueFactory<>("OG"));

        playerTableView.setItems(null);
        playerTableView.setItems(list);

    }
}
