package main.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Team;

import java.net.URL;
import java.util.ResourceBundle;

public class TopTeamController implements Initializable {

    /**
     * TableView {{{
     */
    @FXML
    private TableView<Team> topTeamTable;

    @FXML
    private TableColumn<Team, Integer> rankColumn;

    @FXML
    private TableColumn<Team, String> nameColumn;

    @FXML
    private TableColumn<Team, Integer> playedColumn;

    @FXML
    private TableColumn<Team, Integer> wonColumn;

    @FXML
    private TableColumn<Team, Integer> drawnColumn;

    @FXML
    private TableColumn<Team, Integer> lostColumn;

    @FXML
    private TableColumn<Team, Integer> gfColumn;

    @FXML
    private TableColumn<Team, Integer> gaColumn;

    @FXML
    private TableColumn<Team, Integer> gdColumn;

    @FXML
    private TableColumn<Team, Integer> pointsColumn;


    /**
     * }}}
     *
     */


    public ObservableList<Team> getTeamRecord() {
        return teamRecord;
    }


    public void setTeamRecord(ObservableList<Team> teamRecord) {
        this.teamRecord = teamRecord;
    }


    public TopTeamController(ObservableList<Team> records) {
        setTeamRecord(records);
    }

    /**
     * 用于显示在面板在Team容器
     */
    private ObservableList<Team> teamRecord;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * 将teamRecord中的记录的属性（例如rank, name, won, points...等属性）与tableColumn（例如Rank Column, Name Column, ...）相对应（或者说相连接）
         */
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playedColumn.setCellValueFactory(new PropertyValueFactory<>("played"));
        wonColumn.setCellValueFactory(new PropertyValueFactory<>("won"));
        drawnColumn.setCellValueFactory(new PropertyValueFactory<>("drawn"));
        lostColumn.setCellValueFactory(new PropertyValueFactory<>("lost"));
        gfColumn.setCellValueFactory(new PropertyValueFactory<>("GF"));
        gaColumn.setCellValueFactory(new PropertyValueFactory<>("GA"));
        gdColumn.setCellValueFactory(new PropertyValueFactory<>("GD"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        topTeamTable.setItems(null);
        topTeamTable.setItems(teamRecord);

    }


}
