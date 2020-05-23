package main.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import java.util.IllegalFormatPrecisionException;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private String groupName;

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

    public ScheduleController(ObservableList<Record> list, String groupName) {
        setList(list);
        setGroupName(groupName);
    }

    public ScheduleController(ObservableList<Record> list) {
        setList(list);
    }



    public void doGroupMatch() {
        try {

            Random rand = new Random();
            Connection connection = dc.connection();

            /**
             * 获取数据
             */
            String getScheduleSql = "SELECT * FROM `" + group.getName() + "Schedule` WHERE `ID` >= 1";
            System.out.println(getScheduleSql);
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

                int aPoints = teamAInfo.getInt(10), bPoints = teamBInfo.getInt(10), updateWonA = teamAInfo.getInt(4), updateWonB = teamBInfo.getInt(4),
                        updateDrawnA = teamAInfo.getInt(5), updateDrawnB = teamBInfo.getInt(5), updateLostA = teamAInfo.getInt(6), updateLostB = teamBInfo.getInt(6);

                if (TeamAGoals > TeamBGoals) {

                    aPoints += 3;
                    updateWonA += 1;
                    updateLostB += 1;

                } else if (TeamAGoals < TeamBGoals) {

                    bPoints += 3;
                    updateLostA += 1;
                    updateWonB += 1;

                } else {

                    aPoints += 1;
                    bPoints += 1;
                    updateDrawnA += 1;
                    updateDrawnB += 1;
                }

                int updatePlayedA = teamAInfo.getInt(3) + 1;
                int updatePlayedB = teamBInfo.getInt(3) + 1;

                int updateGFA = teamAInfo.getInt(7) + TeamAGoals;
                int updateGAA = teamAInfo.getInt(8) + TeamBGoals;
                int updateGDA = teamAInfo.getInt(9) + TeamAGoals - TeamBGoals;

                int updateGFB = teamBInfo.getInt(7) + TeamBGoals;
                int updateGAB = teamBInfo.getInt(8) + TeamAGoals;
                int updateGDB = teamBInfo.getInt(9) + TeamBGoals - TeamAGoals;



                String updateTeamARecord = "UPDATE `" + group.getName() + "` SET Played = " + updatePlayedA + ", Won = " + updateWonA + ", Drawn = " + updateDrawnA +
                        ", Lost = " + updateLostA + ", GF = " + updateGFA + ", GA = " +
                        updateGAA + ", GD = " + updateGDA + ", Points = " + aPoints +
                        " WHERE Name = '" + scheduleRS.getString(4) + "'";

                String updateTeamBRecord = "UPDATE `" + group.getName() + "` SET Played = " + updatePlayedB + ", Won = " + updateWonB + ", Drawn = " + updateDrawnB +
                        ", Lost = " + updateLostB + ", GF = " + updateGFB + ", GA = " +
                        updateGAB + ", GD = " + updateGDB + ", Points = " + bPoints +
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

        /**
         * 进行淘汰赛（决出一二三名）
         */
        try {

            Connection connection = dc.connection();

            /**
             * 获取数据库中的队伍数据
             */
            System.out.println(groupName);
            String getTeamDataSql = "SELECT * FROM `" + group.getName() + "` WHERE ID >= 1";
            ResultSet teamDataRs = connection.createStatement().executeQuery(getTeamDataSql);

            String getTeamConfigSql = "SELECT * FROM `League` WHERE Name = '" + group.getName() + "'";
            ResultSet teamConfigRs = connection.createStatement().executeQuery(getTeamConfigSql);
            teamConfigRs.next();

            /**
             * 实例化group
             */
            Team[] teams = new Team[16];

            for (int i = 0; teamDataRs.next(); i++) {

                teams[i] = new Team(teamDataRs.getInt(1), teamDataRs.getString(2), teamDataRs.getInt(3), teamDataRs.getInt(4), teamDataRs.getInt(5),
                        teamDataRs.getInt(6), teamDataRs.getInt(7), teamDataRs.getInt(8), teamDataRs.getInt(9), teamDataRs.getInt(10));

            }

            Referee refereeA = new Referee(teamConfigRs.getString(6), new RefereeAssistant(teamConfigRs.getString(8)), new RefereeAssistant(teamConfigRs.getString(9)));
            Referee refereeB = new Referee(teamConfigRs.getString(7), new RefereeAssistant(teamConfigRs.getString(10)), new RefereeAssistant(teamConfigRs.getString(11)));

            Field fieldA = new Field(teamConfigRs.getString(12));
            Field fieldB = new Field(teamConfigRs.getString(13));

            Group group = new Group(teamConfigRs.getInt(1), teamConfigRs.getString(2), refereeA, refereeB,
                    refereeA.getRefereeAssistantA(), refereeA.getRefereeAssistantB(), refereeB.getRefereeAssistantA(), refereeB.getRefereeAssistantB(),
                    fieldA, fieldB, "Knockoff");

            group.setGroup(teams);

            /**
             * 根据积分排序
             */
            group.sortByPoints();
            group.doRanking();

            /**
             * 生成日程并插入至数据库中
             */
            String insertSql = "INSERT INTO `" + group.getName() + "Schedule`(Stage, Situation, TeamA, TeamAGoals, TeamBGoals, TeamB, Referee, RefereeAssistantA, RefereeAssistantB, Field) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Team[] rankedGroup = group.getGroup();
            ObservableList<Record> list = FXCollections.observableArrayList();
            Random rand = new Random();

            Team[] semifinalTeam = new Team[4];

            Team[] testTeams = group.getGroup();
            for (int i = 0; i < 16; i++) {

                System.out.println(testTeams[i].getName());

            }

            /**
             * 1/4 final
             */
            for (int i = 0, j = 7; i < j; i++, j--) {
                Team teamA = rankedGroup[i];
                Team teamB = rankedGroup[j];

                int teamAGoals = rand.nextInt(10);
                int teamBGoals;
                for (; (teamBGoals = rand.nextInt(10)) == teamAGoals;)
                    ;

                PreparedStatement insertPstmt = connection.prepareStatement(insertSql);
                Referee referee = i % 2 != 0 ? group.getRefereeA() : group.getRefereeB();
                Field field = i % 2 != 0 ? group.getFieldA() : group.getFieldB();

                insertPstmt.setString(1, "1/4final");
                insertPstmt.setString(2, "Finished");
                insertPstmt.setString(3, teamA.getName());
                insertPstmt.setInt(4, teamAGoals);
                insertPstmt.setInt(5, teamBGoals);
                insertPstmt.setString(6, teamB.getName());
                insertPstmt.setString(7, referee.getName());
                insertPstmt.setString(8, referee.getRefereeAssistantA().getName());
                insertPstmt.setString(9, referee.getRefereeAssistantB().getName());
                insertPstmt.setString(10, field.getFieldName());

                insertPstmt.executeUpdate();
                insertPstmt.close();

                /**
                 * 添加至容器中以便显示
                 */
                list.add(new Record(teamA, teamB, referee, referee.getRefereeAssistantA(), referee.getRefereeAssistantB(), teamAGoals, teamBGoals, field, "1/4final", "Finished"));


                semifinalTeam[i] = teamAGoals > teamBGoals ? teamA : teamB;

            }


            Team[] finalTeam = new Team[2];
            Team[] teamsCompeteForThirdPlace = new Team[2];
            /**
             * Semifinal
             */
            for (int i = 0, j = 3; i < j; i++, j--) {

                Team teamA = rankedGroup[i];
                Team teamB = rankedGroup[j];

                int teamAGoals = rand.nextInt(10);
                int teamBGoals;
                for (; (teamBGoals = rand.nextInt(10)) == teamAGoals;)
                    ;

                PreparedStatement insertPstmt = connection.prepareStatement(insertSql);
                Referee referee = i % 2 != 0 ? group.getRefereeA() : group.getRefereeB();
                Field field = i % 2 != 0 ? group.getFieldA() : group.getFieldB();

                insertPstmt.setString(1, "Semifinal");
                insertPstmt.setString(2, "Not Started");
                insertPstmt.setString(3, teamA.getName());
                insertPstmt.setInt(4, teamAGoals);
                insertPstmt.setInt(5, teamBGoals);
                insertPstmt.setString(6, teamB.getName());
                insertPstmt.setString(7, referee.getName());
                insertPstmt.setString(8, referee.getRefereeAssistantA().getName());
                insertPstmt.setString(9, referee.getRefereeAssistantB().getName());
                insertPstmt.setString(10, field.getFieldName());

                insertPstmt.executeUpdate();
                insertPstmt.close();

                /**
                 * 添加至容器中以便显示
                 */
                list.add(new Record(teamA, teamB, referee, referee.getRefereeAssistantA(), referee.getRefereeAssistantB(), teamAGoals, teamBGoals, field, "Semifinal", "Finished"));


                finalTeam[i] = teamAGoals > teamBGoals ? teamA : teamB;
                teamsCompeteForThirdPlace[i] = teamAGoals > teamBGoals ? teamB : teamA;

            }


            /**
             * 三四名决赛
             */
            Team thirdPlaceCompetitionTeamA = teamsCompeteForThirdPlace[0];
            Team thirdPlaceCompetitionTeamB = teamsCompeteForThirdPlace[1];

            int tPCTeamAGoals = rand.nextInt(10);
            int tPCTeamBGoals;
            for (; (tPCTeamBGoals = rand.nextInt(10)) == tPCTeamAGoals;)
                ;
            PreparedStatement tPCInsertPstmt = connection.prepareStatement(insertSql);
            tPCInsertPstmt.setString(1, "3/4final");
            tPCInsertPstmt.setString(2, "Finished");
            tPCInsertPstmt.setString(3, thirdPlaceCompetitionTeamA.getName());
            tPCInsertPstmt.setInt(4, tPCTeamAGoals);
            tPCInsertPstmt.setInt(5, tPCTeamBGoals);
            tPCInsertPstmt.setString(6, thirdPlaceCompetitionTeamB.getName());
            tPCInsertPstmt.setString(7, group.getRefereeA().getName());
            tPCInsertPstmt.setString(8, group.getRefereeA().getRefereeAssistantA().getName());
            tPCInsertPstmt.setString(9, group.getRefereeA().getRefereeAssistantB().getName());
            tPCInsertPstmt.setString(10, fieldA.getFieldName());

            tPCInsertPstmt.executeUpdate();
            tPCInsertPstmt.close();

            Team thirdPlace = tPCTeamAGoals > tPCTeamBGoals ? thirdPlaceCompetitionTeamA : thirdPlaceCompetitionTeamB;

            list.add(new Record(thirdPlaceCompetitionTeamA, thirdPlaceCompetitionTeamB, refereeA, refereeA.getRefereeAssistantA(),
                    refereeA.getRefereeAssistantB(), tPCTeamAGoals, tPCTeamBGoals, fieldA, "3/4final", "Finished"));



            /**
             * final
             */
            Team finalTeamA = finalTeam[0];
            Team finalTeamB = finalTeam[1];

            int finalTeamAGoals = rand.nextInt(10);
            int finalTeamBGoals;
            for (; (finalTeamBGoals = rand.nextInt(10)) == finalTeamAGoals;)
                ;
            PreparedStatement finalPstmt = connection.prepareStatement(insertSql);
            finalPstmt.setString(1, "final");
            finalPstmt.setString(2, "Finished");
            finalPstmt.setString(3, finalTeamA.getName());
            finalPstmt.setInt(4, finalTeamAGoals);
            finalPstmt.setInt(5, finalTeamBGoals);
            finalPstmt.setString(6, finalTeamB.getName());
            finalPstmt.setString(7, group.getRefereeB().getName());
            finalPstmt.setString(8, group.getRefereeB().getRefereeAssistantA().getName());
            finalPstmt.setString(9, group.getRefereeB().getRefereeAssistantB().getName());
            finalPstmt.setString(10, fieldB.getFieldName());

            list.add(new Record(finalTeamA, finalTeamB, refereeB, refereeB.getRefereeAssistantA(),
                    refereeB.getRefereeAssistantB(), finalTeamAGoals, finalTeamBGoals, fieldB, "final", "Finished"));

            finalPstmt.executeUpdate();
            finalPstmt.close();

            Team firstPlace = finalTeamAGoals > finalTeamBGoals ? finalTeamA : finalTeamB;
            Team SecondPlace = finalTeamAGoals > finalTeamBGoals ? finalTeamB : finalTeamA;


            /**
             * 更新小组数据
             */
            String updateSql = "UPDATE `League` SET First_Place = '" + firstPlace.getName() + "', Second_Place = '" + SecondPlace.getName() + "', Third_Place = '" + thirdPlace.getName() + "' " +
                    "WHERE Name = '" + group.getName() + "'";
            PreparedStatement updatePstmt = connection.prepareStatement(updateSql);
            updatePstmt.executeUpdate();
            updatePstmt.close();


            update(list);
            connection.close();



        } catch (SQLException e) {

            e.printStackTrace();

        }


    }

    public void update(ObservableList<Record> list) {

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
