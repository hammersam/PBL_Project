package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.dao.DBConnection;
import main.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;


public class GroupController implements Initializable {

    /**
     * TableView/TableColumn{{{
     */
    @FXML
    private TableView<Team> tableUser;

    @FXML
    private TableColumn<Team, Integer> columnID;

    @FXML
    private TableColumn<Team, String> columnName;

    @FXML
    private TableColumn<Team, Integer> columnPlayed;

    @FXML
    private TableColumn<Team, Integer> columnWon;

    @FXML
    private TableColumn<Team, Integer> columnDrawn;

    @FXML
    private TableColumn<Team, Integer> columnLost;

    @FXML
    private TableColumn<Team, Integer> columnGF;

    @FXML
    private TableColumn<Team, Integer> columnGA;

    @FXML
    private TableColumn<Team, Integer> columnGD;

    @FXML
    private TableColumn<Team, Integer> columnPoints;

    /**
     * }}}
     */

    /**
     * TextField {{{
     */

    @FXML
    private TextField textID;

    @FXML
    private TextField textName;

    @FXML
    private TextField textPlayed;

    @FXML
    private TextField textWon;

    @FXML
    private TextField textDrawn;

    @FXML
    private TextField textLost;

    @FXML
    private TextField textGF;

    @FXML
    private TextField textGA;

    @FXML
    private TextField textGD;

    @FXML
    private TextField textPoints;

    /**
     * }}}
     */

    /**
     * several Btns  {{{
     */
    @FXML
    private Button groupAddBtn;

    @FXML
    private Button groupDeleteBtn;

    @FXML
    private Button showBestTeam;

    @FXML
    private Button showBestShotter;

    @FXML
    private Button groupUpdateBtn;

    @FXML
    private Button scheduleBtn;

    @FXML
    private Button renewBtn;

    @FXML
    private Button showScheduleBtn;

    @FXML
    private Button registerBtn;
    /**
     * }}}
     *
     */


    public String getDbText() {
        return dbText;
    }


    public void setDbText(String dbText) {
        this.dbText = dbText;
    }


    /**
     * get connection of database
     */
    private DBConnection dc;


    private String dbText;


    public GroupController() {

    }


    /**
     * 从数据库中获取数据
     *
     */
    @FXML
    private ObservableList<Team> loadDataFromDatabase(String sql) {

        Connection connection = dc.connection();
        ObservableList<Team> data = FXCollections.observableArrayList();

        try {
            ResultSet rs = connection.createStatement().executeQuery(sql);

            while (rs.next()) {
                data.add(new Team(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10)));
            }

            for (Team team : data) {
                System.out.printf(team.getName().getClass().getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }


    /**
     * 从数据库中获取数据(传递connection也许会加快速度？)
     *
     */
    @FXML
    private ObservableList<Team> loadDataFromDatabase(String sql, Connection conn) {

        Connection connection = conn;
        ObservableList<Team> data = FXCollections.observableArrayList();

        try {
            ResultSet rs = connection.createStatement().executeQuery(sql);

            while (rs.next()) {
                data.add(new Team(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10)));
            }

            for (Team team : data) {
                System.out.printf(team.getName().getClass().getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;


    }


    /**
     * 点击tableColumn进入球队面板
     */
    @FXML
    public void setOnMouseClicked(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {

            TableView<Team> table = (TableView<Team>) event.getSource();
            String teamName = table.getSelectionModel().getSelectedItem().getName();
            System.out.println(teamName);


            /**
             * normal rice
             */
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/team.fxml"));

            /**
             * 生成controller
             */
            TeamController team = new TeamController();
            team.setDbText(teamName);
            loader.setController(team);

            /**
             * normal rice
             */
            Parent root = (Parent) loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        }

    }


    /**
     * 点击Delete按钮删除球队记录 @DeletionBtn
     */
    public void groupDeleteAction() {
        Team team = tableUser.getSelectionModel().getSelectedItem();
        int ID = team.getID();
        String tableName = team.getName();
        String deletionSql = "DELETE FROM " + dbText + " where ID = " + ID;
        String tableDeletionSql = "DROP TABLE IF EXISTS " + tableName;

        Connection connection = dc.connection();

        /**
         *
         * 执行删除语句
         *
         */
        try {

            PreparedStatement pstmt = connection.prepareStatement(deletionSql);
            PreparedStatement pstmt2 = connection.prepareStatement(tableDeletionSql);

            pstmt.executeUpdate();
            pstmt2.executeUpdate();

            update(connection);

            pstmt.close();
            pstmt2.close();
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        /**
         * TableView live update
         */

    }


    /**
     * 添加球队记录 @AddBtn
     */
    public void groupAddAction() {
        Connection connection = dc.connection();

        String addSql = "insert into " + dbText + "(Name, Played, Won, Drawn, Lost, GF, GA, GD, Points)" +
                " values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement pstmt = connection.prepareStatement(addSql);

            System.out.println(textName.getText());

            pstmt.setString(1, textName.getText());
            pstmt.setInt(2, Integer.parseInt(textPlayed.getText()));
            pstmt.setInt(3, Integer.parseInt(textWon.getText()));
            pstmt.setInt(4, Integer.parseInt(textDrawn.getText()));
            pstmt.setInt(5, Integer.parseInt(textLost.getText()));
            pstmt.setInt(6, Integer.parseInt(textGF.getText()));
            pstmt.setInt(7, Integer.parseInt(textGA.getText()));
            pstmt.setInt(8, Integer.parseInt(textGD.getText()));
            pstmt.setInt(9, Integer.parseInt(textPoints.getText()));

            pstmt.executeUpdate();

            update(connection);

            pstmt.close();
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }


    /**
     * 射手榜 @BestShotterBtn
     */
    public void showBestShotterBoard() {
        try {

            /**
             * 获取数据库中的team
             */
            Team[] teams = new Team[16];
            Connection connection = dc.connection();
            String getTeamsSql = "SELECT * FROM `" + dbText + "` WHERE `ID` >= 1";
            ResultSet teamRs = connection.createStatement().executeQuery(getTeamsSql);

            /**
             * 将数据库中team的数据添加至teams中
             */
            for (int i = 0; teamRs.next(); i++) {
                teams[i] = new Team(teamRs.getInt(1), teamRs.getString(2), teamRs.getInt(3), teamRs.getInt(4), teamRs.getInt(5),
                        teamRs.getInt(6), teamRs.getInt(7), teamRs.getInt(8), teamRs.getInt(9), teamRs.getInt(10));
            }

            /**
             * 一个小组至多有16 * 11个球员
             */
            Player[] players = new Player[16 * 11];
            int j = 0;
            {
                int i = 0;
                while (i < 16 && teams[i] != null) {

                    /**
                     * 如果数据库中不存在该team table，则创建
                     */
                    String checkSql = "CREATE TABLE IF NOT EXISTS `" + teams[i].getName() + "` (" +
                            "`ID` INT UNSIGNED NOT NULL," +
                            "`Name` VARCHAR(100) NOT NULL," +
                            "`Age` INT UNSIGNED NOT NULL," +
                            "`Gender` VARCHAR(100) NOT NULL," +
                            "`Position` VARCHAR(100) NOT NULL," +
                            "`Goals` INT UNSIGNED NOT NULL," +
                            "`NG` INT UNSIGNED NOT NULL," +
                            "`PK` INT UNSIGNED NOT NULL," +
                            "`OG` INT UNSIGNED NOT NULL," +
                            "`Fouls` INT UNSIGNED NOT NULL," +
                            "`Club` VARCHAR(100) NOT NULL," +
                            "`Height` DOUBLE UNSIGNED NOT NULL," +
                            "`Weight` DOUBLE UNSIGNED NOT NULL," +
                            "PRIMARY KEY ( `ID` )) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
                    checkTable(checkSql, connection);

                    /**
                     * 获取小组中每个球队的球员
                     */
                    String getPlayersSql = "SELECT * FROM `" + teams[i].getName() + "` WHERE `ID` >= 1";
                    ResultSet playerRs = connection.createStatement().executeQuery(getPlayersSql);

                    /**
                     * 将球员信息添加至players中
                     */
                    for (; playerRs.next(); j++) {
                        players[j] = new Player(playerRs.getInt(1), playerRs.getString(2), playerRs.getInt(3), playerRs.getString(4), playerRs.getString(5),
                                playerRs.getInt(6), playerRs.getInt(7), playerRs.getInt(8), playerRs.getInt(9), playerRs.getInt(10), playerRs.getString(11),
                                playerRs.getDouble(12), playerRs.getDouble(13));
                    }

                    i++;
                }
            }

            /**
             * 将球员信息添加至ObservableList中，用于显示
             */
            ObservableList<Player> playerList = FXCollections.observableArrayList();

            /**
             * 排序&&添加（可以改进）
             */
            for (int i = 0; i < 16 * 11 && players[i] != null; i++) {
                for (int k = i + 1; k < 16 * 11 && players[k] != null; k++) {
                    if (players[i].getGoals() < players[k].getGoals()) {
                        Player tmp = players[i];
                        players[i] = players[k];
                        players[k] = tmp;
                    }
                }
                players[i].setRank(i + 1);
                playerList.add(players[i]);
            }


            /**
             * 加载画板
             */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/topShotter.fxml"));
            TopShotterController controller = new TopShotterController(playerList);
            loader.setController(controller);

            Stage stage = new Stage();
            Parent root = (Parent) loader.load();
            stage.setScene(new Scene(root));
            stage.show();

            /**
             * 关闭连接
             */
            connection.close();



        } catch (SQLException | IOException e) {

            e.printStackTrace();

        }

    }

    /**
     * 积分榜 @BestTeamBtn
     */
    public void showBestTeamBoard() {

        /**
         * 获取数据
         */
        try {

            /**
             * 一个小组有16支队伍
             */
            Team[] groupArray = new Team[16];
            Connection connection = dc.connection();
            String sql = "SELECT * FROM `" + dbText + "` WHERE `ID` >= 1";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            /**
             * 初始化group数组
             */
            for (int i = 0; rs.next(); i++) {
                groupArray[i] = new Team(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
                        rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10));
            }

            /**
             * 初始化group
             */
            Group _group = new Group(dbText, groupArray);

            /**
             * 根据points排序
             */
            _group.sortByPoints();
            _group.doRanking();


            ObservableList<Team> list = FXCollections.observableArrayList();


            /**
             * 将team record记录到list中，一边在tableView中显示
             */
            Team[] rankTeams = _group.getGroup();
            for (int i = 0;  i < 16 && rankTeams[i] != null; i++) {
                list.add(rankTeams[i]);
            }

            /**
             * 加载画板
             */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/topTeam.fxml"));
            TopTeamController controller = new TopTeamController(list);
            loader.setController(controller);

            Stage stage = new Stage();
            Parent root = (Parent) loader.load();
            stage.setScene(new Scene(root));
            stage.show();

            /**
             * 关闭连接
             */
            connection.close();

        } catch (SQLException | IOException e) {

            e.printStackTrace();

        }
    }


    @FXML
    public void groupUpdateAction() {

        try {

            Connection connection = dc.connection();

            /**
             * 获取需要Update的tableItem
             */
            Team team = tableUser.getSelectionModel().getSelectedItem();
            String teamName = team.getName();


            /**
             * 未输入的数据项则不更新，输入的数据项加上已有的数据形成新的数据项
             */
            int updatePlayed = textPlayed.getText() == "" ? team.getPlayed() : Integer.parseInt(textPlayed.getText()) + team.getPlayed();
            int updateWon = textWon.getText() == "" ? team.getWon() : Integer.parseInt(textWon.getText()) + team.getWon();
            int updateDrawn = textDrawn.getText() == "" ? team.getDrawn() : Integer.parseInt(textDrawn.getText()) + team.getDrawn();
            int updateLost = textLost.getText() == "" ? team.getLost() : Integer.parseInt(textLost.getText()) + team.getLost();
            int updateGF = textGF.getText() == "" ? team.getGF() : Integer.parseInt(textGF.getText()) + team.getGF();
            int updateGA = textGA.getText() == "" ? team.getGA() : Integer.parseInt(textGA.getText()) + team.getGA();
            int updateGD = textGD.getText() == "" ? team.getGD() : Integer.parseInt(textGD.getText()) + team.getGD();
            int updatePoints = textPoints.getText() == "" ? team.getPoints() : Integer.parseInt(textPoints.getText()) + team.getPoints();


            /**
             * update的sql语句
             */
            String updateSql = "UPDATE " + dbText + " SET Played = " + updatePlayed + ", Won = " + updateWon + ", Drawn = " + updateDrawn + ", Lost = " + updateLost + ", " +
                    "GF = " + updateGF + ", GA = " + updateGA + ", GD = " + updateGD + ", Points = " + updatePoints + " WHERE " + dbText + ".Name = '" + teamName + "'";


            /**
             * 创建连接
             */
            PreparedStatement pstmt = connection.prepareStatement(updateSql);
            pstmt.executeUpdate();

            /**
             * 更新画板
             */
            update(connection);
            pstmt.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    /**
     * schedule
     */
    @FXML
    public void arrangeScheduleAndShow() {

        try {
            Connection connection = dc.connection();

            /**
             * 获取有关Group的信息
             */
            String sql = "SELECT * FROM `League` WHERE `ID` >= 1";
            ResultSet groupRS = connection.createStatement().executeQuery(sql);
            groupRS.next();

            /**
             * 从数据库中获取数据并创建Group实例，并添加到data中
             */
            RefereeAssistant refereeAssistantA = new RefereeAssistant(groupRS.getString(8));
            RefereeAssistant refereeAssistantB = new RefereeAssistant(groupRS.getString(9));
            RefereeAssistant refereeAssistantC = new RefereeAssistant(groupRS.getString(10));
            RefereeAssistant refereeAssistantD = new RefereeAssistant(groupRS.getString(11));

            Referee refereeA = new Referee(groupRS.getString(6), refereeAssistantA, refereeAssistantB);
            Referee refereeB = new Referee(groupRS.getString(7), refereeAssistantC, refereeAssistantD);

            Field fieldA = new Field(groupRS.getString(12));
            Field fieldB = new Field(groupRS.getString(13));

            /**
             * 实例化一个group
             */
            Group group = new Group(groupRS.getInt(1), groupRS.getString(2), refereeA, refereeB, refereeAssistantA, refereeAssistantB, refereeAssistantC, refereeAssistantD, fieldA, fieldB);
            Team[] teams = new Team[16];

            /**
             * 获取数据库中的队伍数据
             */
            String getTeamSql = "SELECT * FROM " + dbText + " WHERE ID >= 1";

            /**
             * 获取ResultSet
             */
            ResultSet teamRS = connection.createStatement().executeQuery(getTeamSql);

            /**
             * 输入队伍数据
             */
            for (int i = 0; i < 16 && teamRS.next(); i++) {
                teams[i] = new Team(teamRS.getInt(1), teamRS.getString(2), teamRS.getInt(3), teamRS.getInt(4), teamRS.getInt(5),
                        teamRS.getInt(6), teamRS.getInt(7), teamRS.getInt(8), teamRS.getInt(9), teamRS.getInt(10));
            }

            group.setGroup(teams);


            /**
             * 删除日程如果存在
             */
            String deleteScheduleSql = "DROP TABLE IF EXISTS `" + dbText + "Schedule`";
            PreparedStatement deletePstmt = connection.prepareStatement(deleteScheduleSql);
            deletePstmt.executeUpdate();
            deletePstmt.close();


            /**
             * 创建日程表
             */
            String scheduleSql = "CREATE TABLE IF NOT EXISTS `" + dbText + "Schedule` (" +
                    "`ID` INT UNSIGNED AUTO_INCREMENT," +
                    "`Stage` VARCHAR(100) NOT NULL," +
                    "`Situation` VARCHAR(100) NOT NULL," +
                    "`TeamA` VARCHAR(100) NOT NULL," +
                    "`TeamAGoals` INT UNSIGNED," +
                    "`TeamBGoals` INT UNSIGNED," +
                    "`TeamB` VARCHAR(100) NOT NULL," +
                    "`Referee` VARCHAR(100) NOT NULL," +
                    "`RefereeAssistantA` VARCHAR(100) NOT NULL," +
                    "`RefereeAssistantB` VARCHAR(100) NOT NULL," +
                    "`Field` VARCHAR(100) NOT NULL, " +
                    "PRIMARY KEY ( `ID` )) ENGINE=InnoDB DEFAULT CHARSET=utf8;";

            /**
             * 执行scheduleSql语句（不获取ResultSet）
             */
            PreparedStatement createTablePstmt = connection.prepareStatement(scheduleSql);
            createTablePstmt.executeUpdate();
            createTablePstmt.close();


            /**
             * 安排日程
             */
            String insertSql = "insert into " + dbText + "Schedule(Stage, Situation, TeamA, TeamB, Referee, RefereeAssistantA, RefereeAssistantB, Field)" +
                    " values(?, ?, ?, ?, ?, ?, ?, ?)";

            /**
             * 设置ObservableList，用于显示
             */
            ObservableList<Record> list = FXCollections.observableArrayList();

            int id = 0;
            Record recordArray[] = new Record[16 * 15 / 2];
            for (int i = 0; i < 15; i++) {
                for (int j = i + 1; j < 16; j++) {
                    PreparedStatement insertPstmt = connection.prepareStatement(insertSql);

                    /**
                     * 未进行比赛，只是安排日程，故无法存储两只队伍的进球数
                     */

                    Team teamA = teams[i];
                    Team teamB = teams[j];
                    /**
                     * 当比赛的id号为奇数时，使用A球场，主裁判员A
                     */
                    Referee referee = ++id % 2 != 0 ? group.getRefereeA() : group.getRefereeB();
                    RefereeAssistant referAssintA = referee.getRefereeAssistantA();
                    RefereeAssistant referAssintB = referee.getRefereeAssistantB();
                    Field field = id % 2 != 0 ? group.getFieldA() : group.getFieldB();

                    recordArray[id - 1] = new Record(teamA, teamB, referee, referAssintA, referAssintB, field, "GroupMatch", "NotStarted");


                }

            }

            messUp(recordArray);

            for (int i = 0; i < 16 * 15 / 2; i++) {
                Record record = recordArray[i];
                PreparedStatement insertPstmt = connection.prepareStatement(insertSql);
                insertPstmt.setString(1, "GroupMatch");
                insertPstmt.setString(2, "NotStarted");
                insertPstmt.setString(3, record.getTeamA().getName());
                insertPstmt.setString(4, record.getTeamB().getName());
                insertPstmt.setString(5, record.getReferee().getName());
                insertPstmt.setString(6, record.getReferee().getRefereeAssistantA().getName());
                insertPstmt.setString(7, record.getReferee().getRefereeAssistantB().getName());
                insertPstmt.setString(8, record.getField().getFieldName());

                list.add(record);

                insertPstmt.executeUpdate();
                insertPstmt.close();
            }

            /**
             * 将schedule加载进ObservableList，用于显示在fxml面板中
             */
            ObservableList<Record> schedule = FXCollections.observableArrayList();

            /**
             * 加载fxml面板
             */
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/schedule.fxml"));

            /**
             * 生成controller
             */
            ScheduleController team = new ScheduleController(list, group);
            loader.setController(team);

            /**
             * normal rice
             */
            Parent root = (Parent) loader.load();
            stage.setScene(new Scene(root));
            stage.show();


        } catch (SQLException | IOException e) {

            e.printStackTrace();

        }


    }


    public void showScheduleAction() {

        try {

            ObservableList<Record> list = FXCollections.observableArrayList();
            Connection connection = dc.connection();
            /**
             * 获取数据库中record数据
             */
            String getRecordSql = "SELECT * FROM `" + dbText + "Schedule` WHERE ID >= 1";
            ResultSet rs = connection.createStatement().executeQuery(getRecordSql);

            while (rs.next()) {

                Team teamA = new Team(rs.getString(4));
                Team teamB = new Team(rs.getString(7));
                Referee referee = new Referee(rs.getString(8), new RefereeAssistant(rs.getString(9)), new RefereeAssistant(rs.getString(10)));
                Field field = new Field(rs.getString(11));

                /**
                 * 创建Record实例并输入进list中以便显示
                 */
                list.add(new Record(teamA, teamB, referee, referee.getRefereeAssistantA(), referee.getRefereeAssistantB(), rs.getInt(5), rs.getInt(6),
                        field, rs.getString(2), rs.getString(3)));


            }

            ScheduleController controller = new ScheduleController(list);
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/schedule.fxml"));
            loader.setController(controller);

            Parent root = (Parent) loader.load();
            stage.setScene(new Scene(root));
            stage.show();


        } catch (SQLException | IOException e) {

            e.printStackTrace();

        }
    }


    @FXML
    public void renewGroupAction() {
        try {

            Connection connection = dc.connection();

            update(connection);
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    @FXML
    public void clearAllDataAction() {

        try {

            Connection connection = dc.connection();
            /**
             * 将Group表中有关Team的数据删除
             * UPDATE `GroupA` SET `GD` = '01' WHERE `GroupA`.`ID` = 14;
             */
            System.out.println("\n" + dbText);
            String clearGroupSql = "UPDATE `" + dbText +"` SET `Played` = '0', `Won` = '0', `Drawn` = '0', `Lost` = '0', `GF` = '0', `GA` = '0', `GD` = '0', `Points` = '0' WHERE `" + dbText + "`.`ID` >= 1";
            PreparedStatement clearPstmt = connection.prepareStatement(clearGroupSql);
            clearPstmt.executeUpdate();

            /**
             * 将Team表中有关Player的数据删除
             */

            /**
             * 获取Group中的Team
             */
            String getTeamSql = "SELECT * FROM `" + dbText + "` WHERE ID >= 1";
            ResultSet teamRS = connection.createStatement().executeQuery(getTeamSql);

            while (teamRS.next()) {

                String clearTeamSql = "UPDATE `" + teamRS.getString(2) + "` SET `Goals` = '0', `NG` = '0', `PK` = '0', `OG` = '0', `Fouls` = '0' WHERE `" + teamRS.getString(2) + "`.`ID` >= 1";
                PreparedStatement clearTeamPstmt = connection.prepareStatement(clearTeamSql);
                clearTeamPstmt.executeUpdate();

                clearTeamPstmt.close();

            }


            /**
             * 删除日程如果存在
             */
            String deleteScheduleSql = "DROP TABLE IF EXISTS `" + dbText + "Schedule`";
            PreparedStatement deletePstmt = connection.prepareStatement(deleteScheduleSql);
            deletePstmt.executeUpdate();
            deletePstmt.close();


            /**
             * 创建日程表
             */
            String scheduleSql = "CREATE TABLE IF NOT EXISTS `" + dbText + "Schedule` (" +
                    "`ID` INT UNSIGNED AUTO_INCREMENT," +
                    "`Stage` VARCHAR(100) NOT NULL," +
                    "`Situation` VARCHAR(100) NOT NULL," +
                    "`TeamA` VARCHAR(100) NOT NULL," +
                    "`TeamAGoals` INT UNSIGNED," +
                    "`TeamBGoals` INT UNSIGNED," +
                    "`TeamB` VARCHAR(100) NOT NULL," +
                    "`Referee` VARCHAR(100) NOT NULL," +
                    "`RefereeAssistantA` VARCHAR(100) NOT NULL," +
                    "`RefereeAssistantB` VARCHAR(100) NOT NULL," +
                    "`Field` VARCHAR(100) NOT NULL, " +
                    "PRIMARY KEY ( `ID` )) ENGINE=InnoDB DEFAULT CHARSET=utf8;";

            /**
             * 执行scheduleSql语句（不获取ResultSet）
             */
            PreparedStatement createTablePstmt = connection.prepareStatement(scheduleSql);
            createTablePstmt.executeUpdate();
            createTablePstmt.close();





            clearPstmt.close();
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void messUp(Record[] recordArray) {

        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int randomNum1 = rand.nextInt(120);
            int randomNum2;
            for (; (randomNum2 = rand.nextInt(120)) == randomNum1;)
                ;
            Record tmp = recordArray[randomNum1];
            recordArray[randomNum1] = recordArray[randomNum2];
            recordArray[randomNum2] = tmp;
        }

    }

    public void makeSomePlayerForTeam(Connection conn) {

        try {

            Connection connection = conn;
            String getTeamSql = "SELECT * FROM `" + dbText + "` WHERE ID >= 1";
            ResultSet teamResult = connection.createStatement().executeQuery(getTeamSql);

            while (teamResult.next()) {

                String teamName = teamResult.getString(2);

                /**
                 * 丢弃球队表 {{{
                 */
                String dropSql = "DROP TABLE `" + teamName + "`";
                PreparedStatement dropPlayerPstmt = connection.prepareStatement(dropSql);
                dropPlayerPstmt.executeUpdate();

                dropPlayerPstmt.close();

                /**
                 * }}}
                 */

                /**
                 * 创建球队表 {{{
                 *
                 */
                String makeTeamTableSql = "CREATE TABLE IF NOT EXISTS `" + teamName + "` (" +
                        "`ID` INT UNSIGNED NOT NULL," +
                        "`Name` VARCHAR(100) NOT NULL," +
                        "`Age` INT UNSIGNED NOT NULL," +
                        "`Gender` VARCHAR(100) NOT NULL," +
                        "`Position` VARCHAR(100) NOT NULL," +
                        "`Goals` INT UNSIGNED NOT NULL," +
                        "`NG` INT UNSIGNED NOT NULL," +
                        "`PK` INT UNSIGNED NOT NULL," +
                        "`OG` INT UNSIGNED NOT NULL," +
                        "`Fouls` INT UNSIGNED NOT NULL," +
                        "`Club` VARCHAR(100) NOT NULL," +
                        "`Height` DOUBLE UNSIGNED NOT NULL," +
                        "`Weight` DOUBLE UNSIGNED NOT NULL," +
                        "PRIMARY KEY ( `ID` )) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
                PreparedStatement makeTeamTablePstmt = connection.prepareStatement(makeTeamTableSql);
                makeTeamTablePstmt.executeUpdate();

                makeTeamTablePstmt.close();

                for (int i = 0; i < 11; i++) {

                    System.out.println(teamName);
                    String initializeTeamSql = "insert into `" + teamName + "`(ID, Name, Age, Gender, Position, Goals, NG, PK, OG, Fouls, Club, Height, Weight)" +
                            " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement updatePstmt = connection.prepareStatement(initializeTeamSql);

                    updatePstmt.setInt(1, i + 1);
                    updatePstmt.setString(2, "player" + (i + 1));
                    updatePstmt.setInt(3, 30);
                    updatePstmt.setString(4, "male");
                    updatePstmt.setString(5, "forward");
                    updatePstmt.setInt(6, 0);
                    updatePstmt.setInt(7, 0);
                    updatePstmt.setInt(8, 0);
                    updatePstmt.setInt(9, 0);
                    updatePstmt.setInt(10, 0);
                    updatePstmt.setString(11, teamName);
                    updatePstmt.setDouble(12, 1.8);
                    updatePstmt.setDouble(13, 65);

                    updatePstmt.executeUpdate();
                    updatePstmt.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Click Register Btn and jump to Registeration Page
     */
    @FXML
    public void goToRegisterPage() {

        try {

            /**
             * 加载面板
             */
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/groupConfiguration.fxml"));

            /**
             * 生成控制器
             */
            LeagueController controller = new LeagueController();
            loader.setController(controller);

            /**
             * 显示面板
             */
            Parent root = (Parent) loader.load();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 每次删除或者添加球队记录，则重新载入球队记录
     *
     */
    public void update(Connection connection) {

        String sql = "SELECT * FROM `" + dbText + "` WHERE `ID` >= 1";

        columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        columnPlayed.setCellValueFactory(new PropertyValueFactory<>("Played"));
        columnWon.setCellValueFactory(new PropertyValueFactory<>("Won"));
        columnDrawn.setCellValueFactory(new PropertyValueFactory<>("Drawn"));
        columnLost.setCellValueFactory(new PropertyValueFactory<>("Lost"));
        columnGF.setCellValueFactory(new PropertyValueFactory<>("GF"));
        columnGA.setCellValueFactory(new PropertyValueFactory<>("GA"));
        columnGD.setCellValueFactory(new PropertyValueFactory<>("GD"));
        columnPoints.setCellValueFactory(new PropertyValueFactory<>("Points"));

        // System.out.printf("location: " + location.toString()  + " resources: " + resources.toString());

        tableUser.setItems(null);
        tableUser.setItems(loadDataFromDatabase(sql, connection));

        /**
         * 输入之后将TextField设置为空
         */
        textName.clear();
        textPlayed.clear();
        textWon.clear();
        textDrawn.clear();
        textLost.clear();
        textGF.clear();
        textGA.clear();
        textGD.clear();
        textPoints.clear();

    }


    /**
     * 初始化时检查小组表存不存在，如果不存在，则创建
     *
     */
    public void checkTable(String sql, Connection connection) {
        try {

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }


    /**
     * 载入面板时进行初始化
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dc = new DBConnection();
            Connection connection = dc.connection();





            /**
             * 如果数据库中没有group table，则创建一个
             */
            String checkSql = "CREATE TABLE IF NOT EXISTS `" + dbText + "` (" +
                    "`ID` INT UNSIGNED AUTO_INCREMENT," +
                    "`Name` VARCHAR(100) NOT NULL," +
                    "`Played` INT UNSIGNED NOT NULL," +
                    "`Won` INT UNSIGNED NOT NULL," +
                    "`Drawn` INT UNSIGNED NOT NULL," +
                    "`Lost` INT UNSIGNED NOT NULL," +
                    "`GF` INT UNSIGNED NOT NULL," +
                    "`GA` INT UNSIGNED NOT NULL," +
                    "`GD` INT NOT NULL," +
                    "`Points` INT UNSIGNED NOT NULL," +
                    "PRIMARY KEY ( `ID` )) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            checkTable(checkSql, connection);

            /**
             * 如果球队没有球员，则创建
             */
            // makeSomePlayerForTeam(connection);

            /**
             * 从数据库中加载数据，并显示出来
             */
            String initializeSql = "SELECT * FROM `" + dbText + "` WHERE `ID` >= 1;";

            columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            columnPlayed.setCellValueFactory(new PropertyValueFactory<>("Played"));
            columnWon.setCellValueFactory(new PropertyValueFactory<>("Won"));
            columnDrawn.setCellValueFactory(new PropertyValueFactory<>("Drawn"));
            columnLost.setCellValueFactory(new PropertyValueFactory<>("Lost"));
            columnGF.setCellValueFactory(new PropertyValueFactory<>("GF"));
            columnGA.setCellValueFactory(new PropertyValueFactory<>("GA"));
            columnGD.setCellValueFactory(new PropertyValueFactory<>("GD"));
            columnPoints.setCellValueFactory(new PropertyValueFactory<>("Points"));

            // System.out.printf("location: " + location.toString()  + " resources: " + resources.toString());

            tableUser.setItems(null);
            tableUser.setItems(loadDataFromDatabase(initializeSql, connection));


            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

