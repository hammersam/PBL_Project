package main.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import main.dao.DBConnection;
import main.model.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {

    /**
     * Tableview{{{
     */
    @FXML
    private TableView<Record> scheduleTableView;

    @FXML
    private TableColumn<Record, String> columnStage;

    @FXML
    private TableColumn<Record, String> columnSituation;

    @FXML
    private TableColumn<Record, String> columnTeamA;

    @FXML
    private TableColumn<Record, String> columnScore;

    @FXML
    private TableColumn<Record, String> columnTeamB;

    /**
     * }}}
     *
     */

    /**
     * Competition Btn {{{
     *
     */
    @FXML
    private Button groupMatch;

    @FXML
    private Button knockoff;

    /**
     * }}}
     *
     */

    public ObservableList<Record> getList() {
        return list;
    }

    public void setList(ObservableList<Record> list) {
        this.list = list;
    }

    private DBConnection dc;

    private ObservableList<Record> list;

    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public ScheduleController(ObservableList<Record> list, Group group) {
        setList(list);
        setGroup(group);
    }



    public void doGroupMatch() {
        try {

            Random rand = new Random();
            Connection connection = dc.connection();

            /**
             * 获取数据
             */
            String getScheduleSql = "SELECT * FROM `" + group.getName() + "Schedule` WHERE `ID` >= 1";
            ResultSet scheduleRS = connection.createStatement().executeQuery(getScheduleSql);


            /**
             * 进行比赛
             */
            while (scheduleRS.next()) {
                /**
                 * 更新比赛结果
                 */
                int TeamAGoals = rand.nextInt(10);
                int TeamBGoals = rand.nextInt(10);
                String updateRecord = "UPDATE `" + group.getName() + "Schedule` SET Situation = 'Finished', TeamAGoals = " + TeamAGoals + ", TeamBGoals = " + TeamBGoals + " WHERE ID = " + scheduleRS.getInt(1);
                PreparedStatement pstmt = connection.prepareStatement(updateRecord);

                /**
                 * 更新球队信息
                 */
                String teamAName = scheduleRS.getString(4);
                String teamBName = scheduleRS.getString(7);
                String getTeamAInfoSql = "SELECT * FROM `" + group.getName() + "` WHERE `Name` = '" + teamAName + "'";
                String getTeamBInfoSql = "SELECT * FROM `" + group.getName() + "` WHERE `Name` = '" + teamBName + "'";
                ResultSet teamAInfo = connection.createStatement().executeQuery(getTeamAInfoSql);
                ResultSet teamBInfo = connection.createStatement().executeQuery(getTeamBInfoSql);
                teamAInfo.next();
                teamBInfo.next();
                System.out.println("\n" + group.getName() + "  " + teamAName);
                System.out.println(group.getName() + "   " + teamBName);

                int aPoints = 0, bPoints = 0;

                if (TeamAGoals > TeamBGoals) {
                    aPoints = 3;
                } else if (TeamAGoals < TeamBGoals) {
                    bPoints = 3;
                } else {
                    aPoints = bPoints = 1;
                }

                String updateTeamARecord = "UPDATE `" + group.getName() + "` SET Played = " + (teamAInfo.getInt(3) + 1) + ", Won = " + (teamAInfo.getInt(4) + (aPoints == 3 ? 1 : 0)) + ", Drawn = " + (teamAInfo.getInt(5) + (aPoints == 1 ? 1 : 0)) +
                        ", Lost = " + (teamAInfo.getInt(6) + (aPoints == 0 ? 1 : 0)) + ", GF = " + (teamAInfo.getInt(7) + TeamAGoals) + ", GA = " +
                        (teamAInfo.getInt(8) + TeamBGoals) + ", GD = " + (teamAInfo.getInt(9) + TeamAGoals - TeamBGoals) + ", Points = " + (teamAInfo.getInt(10) + aPoints) +
                        " WHERE Name = '" + scheduleRS.getString(4) + "'";

                String updateTeamBRecord = "UPDATE `" + group.getName() + "` SET Played = " + (teamBInfo.getInt(3) + 1) + ", Won = " + (teamBInfo.getInt(4) + (bPoints == 3 ? 1 : 0)) + ", Drawn = " + (teamBInfo.getInt(5) + (bPoints == 1 ? 1 : 0)) +
                        ", Lost = " + (teamBInfo.getInt(6) + (bPoints == 0 ? 1 : 0)) + ", GF = " + (teamBInfo.getInt(7) + TeamBGoals) + ", GA = " +
                        (teamBInfo.getInt(8) + TeamAGoals) + ", GD = " + (teamBInfo.getInt(9) + TeamBGoals - TeamAGoals) + ", Points = " + (teamAInfo.getInt(10) + bPoints) +
                        " WHERE Name = '" + scheduleRS.getString(7) + "'";

                PreparedStatement updataTeamA = connection.prepareStatement(updateTeamARecord);
                PreparedStatement updateTeamB = connection.prepareStatement(updateTeamBRecord);
                updataTeamA.executeUpdate();
                updateTeamB.executeUpdate();


                pstmt.executeUpdate();



                /**
                 * 更新球队球员信息
                 */


                for (int i = 0; i < TeamAGoals; i++) {
                    /**
                     * 随机确定进球的选手
                     */
                    int PlayerID = rand.nextInt(11) + 1;

                    /**
                     * 获取该选手old的数据
                     */
                    String teamAUpdateSql = "SELECT * FROM `" + teamAName + "` WHERE ID = " + PlayerID;
                    ResultSet teamAPlayerInfo = connection.createStatement().executeQuery(teamAUpdateSql);
                    teamAPlayerInfo.next();

                    int goalNumber = rand.nextInt(10);
                    int foulNumber = rand.nextInt(10);
                    String playerUpdateSql = "UPDATE `" + teamAName + "` SET Goals = " + (teamAPlayerInfo.getInt(6) + 1) + ", NG = " + (teamAPlayerInfo.getInt(7) + ((goalNumber >= 0 && goalNumber <= 6) ? 1 : 0)) + ", PK = " +
                            (teamAPlayerInfo.getInt(8) + (goalNumber >= 7 && goalNumber <= 8 ? 1 : 0)) + ", OG = " + (teamAPlayerInfo.getInt(9) + (goalNumber == 9 ? 1 : 0)) +
                            ", Fouls = " + (teamAPlayerInfo.getInt(10) + (foulNumber == 1 ? 1 : 0)) + " WHERE ID = " + PlayerID;

                    PreparedStatement playerUpdatePstmt = connection.prepareStatement(playerUpdateSql);
                    playerUpdatePstmt.executeUpdate();

                    playerUpdatePstmt.close();
                }


                for (int i = 0; i < TeamBGoals; i++) {

                    int PlayerID = rand.nextInt(11) + 1;

                    String teamBUpdateSql = "SELECT * FROM `" + teamBName + "` WHERE ID >= 1";
                    ResultSet teamBPlayerInfo = connection.createStatement().executeQuery(teamBUpdateSql);
                    teamBPlayerInfo.next();

                    int goalNumber = rand.nextInt(10);
                    int foulNumber = rand.nextInt(10);
                    String playerUpdateSql = "UPDATE `" + teamBName + "` SET Goals = " + (teamBPlayerInfo.getInt(6) + 1) + ", NG = " + (teamBPlayerInfo.getInt(7) + ((goalNumber >= 0 && goalNumber <= 6) ? 1 : 0)) + ", PK = " +
                            (teamBPlayerInfo.getInt(8) + (goalNumber >= 7 && goalNumber <= 8 ? 1 : 0)) + ", OG = " + (teamBPlayerInfo.getInt(9) + (goalNumber == 9 ? 1 : 0)) +
                            ", Fouls = " + (teamBPlayerInfo.getInt(10) + (foulNumber == 1 ? 1 : 0)) + " WHERE ID = " + PlayerID;

                    PreparedStatement playerUpdatePstmt = connection.prepareStatement(playerUpdateSql);
                    playerUpdatePstmt.executeUpdate();

                    playerUpdatePstmt.close();
                }




                updataTeamA.close();
                updateTeamB.close();
                pstmt.close();

            }
            /**
             * 比赛结束
             */

            /**
             * 面板更新
             */
            String paneUpdateSql = "SELECT * FROM `" + group.getName() + "Schedule` WHERE ID >= 1";
            ResultSet paneUpdateRS = connection.createStatement().executeQuery(paneUpdateSql);
            for (int i = 0; paneUpdateRS.next() && i < 120; i++) {
                String stage = paneUpdateRS.getString(2);
                String situation = paneUpdateRS.getString(3);
                Team teamA = new Team(paneUpdateRS.getString(4));
                int teamAGoals = paneUpdateRS.getInt(5);
                int teamBGoals = paneUpdateRS.getInt(6);
                Team teamB = new Team(paneUpdateRS.getString(7));
                Referee referee = new Referee(paneUpdateRS.getString(8), new RefereeAssistant(paneUpdateRS.getString(9)), new RefereeAssistant(paneUpdateRS.getString(10)));
                Field field = new Field(paneUpdateRS.getString(11));

                list.set(i, new Record(teamA, teamB, referee, referee.getRefereeAssistantA(), referee.getRefereeAssistantB(), teamAGoals, teamBGoals, field, stage, situation));
            }

            update();
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void doKnockoff() {

    }

    public void update() {

        columnStage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> recordStringCellDataFeatures) {
                StringProperty sp = new SimpleStringProperty();
                sp.setValue(recordStringCellDataFeatures.getValue().getStage());
                return sp;
            }
        });

        columnSituation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> recordStringCellDataFeatures) {
                StringProperty sp = new SimpleStringProperty();
                sp.setValue(recordStringCellDataFeatures.getValue().getSituation());
                return sp;
            }
        });

        columnTeamA.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> recordStringCellDataFeatures) {
                StringProperty sp = new SimpleStringProperty();
                sp.setValue(recordStringCellDataFeatures.getValue().getTeamA().getName());
                return sp;
            }
        });

        columnScore.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> recordStringCellDataFeatures) {
                StringProperty sp = new SimpleStringProperty();
                sp.setValue(recordStringCellDataFeatures.getValue().getaPoints() + "-" + recordStringCellDataFeatures.getValue().getbPoints());
                return sp;
            }
        });

        columnTeamB.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> recordStringCellDataFeatures) {
                StringProperty sp = new SimpleStringProperty();
                sp.setValue(recordStringCellDataFeatures.getValue().getTeamB().getName());
                return sp;
            }
        });

        scheduleTableView.setItems(null);
        scheduleTableView.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dc = new DBConnection();

        columnStage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> recordStringCellDataFeatures) {
                StringProperty sp = new SimpleStringProperty();
                sp.setValue(recordStringCellDataFeatures.getValue().getStage());
                return sp;
            }
        });

        columnSituation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> recordStringCellDataFeatures) {
                StringProperty sp = new SimpleStringProperty();
                sp.setValue(recordStringCellDataFeatures.getValue().getSituation());
                return sp;
            }
        });

        columnTeamA.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> recordStringCellDataFeatures) {
                StringProperty sp = new SimpleStringProperty();
                sp.setValue(recordStringCellDataFeatures.getValue().getTeamA().getName());
                return sp;
            }
        });

        columnScore.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> recordStringCellDataFeatures) {
                StringProperty sp = new SimpleStringProperty();
                sp.setValue(recordStringCellDataFeatures.getValue().getaPoints() + "-" + recordStringCellDataFeatures.getValue().getbPoints());
                return sp;
            }
        });

        columnTeamB.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> recordStringCellDataFeatures) {
                StringProperty sp = new SimpleStringProperty();
                sp.setValue(recordStringCellDataFeatures.getValue().getTeamB().getName());
                return sp;
            }
        });

        scheduleTableView.setItems(null);
        scheduleTableView.setItems(list);


    }
}
