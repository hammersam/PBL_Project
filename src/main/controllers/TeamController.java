package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.dao.DBConnection;
import main.model.Player;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class TeamController implements Initializable {

    /**
     * TableView {{{
     */
    @FXML
    private TableView<Player> playerTable;

    @FXML
    private TableColumn<Player, Integer> columnID;

    @FXML
    private TableColumn<Player, String> columnName;

    @FXML
    private TableColumn<Player, String> columnPosition;

    @FXML
    private TableColumn<Player, Integer> columnGoals;

    @FXML
    private TableColumn<Player, Integer> columnNG;

    @FXML
    private TableColumn<Player, Integer> columnPK;

    @FXML
    private TableColumn<Player, Integer> columnOG;

    @FXML
    private TableColumn<Player, Integer> columnFouls;

    /**
     * }}}
     */

    /**
     * textInput {{{
     */


    @FXML
    private TextField textName;

    @FXML
    private TextField textAge;

    @FXML
    private TextField textGender;

    @FXML
    private TextField textPosition;

    @FXML
    private TextField textGoals;

    @FXML
    private TextField textNG;

    @FXML
    private TextField textPK;

    @FXML
    private TextField textOG;

    @FXML
    private TextField textFouls;

    @FXML
    private TextField textClub;

    @FXML
    private TextField textHeight;

    @FXML
    private TextField textWeight;

    /**
     * }}}
     */

    /**
     * Button{{{
     */

    @FXML
    private Button teamAddBtn;

    @FXML
    private Button teamDeleteBtn;

    @FXML
    private Button refreshTableBtn;

    @FXML
    private Button teamUpdateBtn;

    @FXML
    private Button reloadBtn;

    @FXML
    private Button makeSomePlayerDataBtn;
    /**
     * }}}
     */


    /**
     * 与数据库连接
     */
    private DBConnection dc;


    /**
     * 所属的Group的name
     */
    private String dbText;


    public TeamController(String dbText) {
        setDbText(dbText);
    }


    public String getDbText() {
        return dbText;
    }


    public void setDbText(String dbText) {
        this.dbText = dbText;
    }


    /**
     * 从数据库中获取数据
     *
     */
    @FXML
    private ObservableList<Player> loadDataFromDatabase(String sql) {

        Connection conn = dc.connection();
        ObservableList<Player> data = FXCollections.observableArrayList();

        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                data.add(new Player(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                        rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
                        rs.getString(11), rs.getDouble(12), rs.getDouble(13)));
            }

            for (Player player : data) {
                System.out.printf(player.getName().getClass().getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }


    /**
     * 双击tableView中的信息栏，弹出player的详细信息
     *
     */
    @FXML
    public void setOnMouseClicked(MouseEvent event) throws IOException {

        if (event.getClickCount() == 2) {
            /**
             * 获取点击信息栏的球员的名字
             */
            TableView<Player> table = (TableView<Player>) event.getSource();
            String playerName = table.getSelectionModel().getSelectedItem().getName();

            System.out.println(playerName);

            /**
             * 获取球员的信息
             */
            String sql = "SELECT * FROM `" + dbText + "` WHERE `Name` LIKE '" + playerName + "'";

            System.out.println(sql);

            Connection conn = dc.connection();


            try {
                /**
                 * 获得球员的信息
                 */
                ResultSet rs = conn.createStatement().executeQuery(sql);
                rs.next();

                Player player = new Player(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                        rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
                        rs.getString(11), rs.getDouble(12), rs.getDouble(13));

                /**
                 * 加载fxml文件
                 */
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/playerProfile.fxml"));

                /**
                 * 生成controller，传递要显示的player的信息
                 */
                PlayerProfileController controller = new PlayerProfileController(player);
                loader.setController(controller);

                /**
                 * 显示面板
                 */
                Parent root = (Parent) loader.load();
                stage.setScene(new Scene(root));
                stage.show();


                conn.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    @FXML
    private ObservableList<Player> loadDataFromDatabase(String sql, Connection connection) {

        Connection conn = connection;
        ObservableList<Player> data = FXCollections.observableArrayList();

        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                data.add(new Player(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                        rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
                        rs.getString(11), rs.getDouble(12), rs.getDouble(13)));
            }

            for (Player player : data) {
                System.out.printf(player.getName().getClass().getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;


    }

    public void teamDeleteAction() {
        int ID = playerTable.getSelectionModel().getSelectedItem().getID();
        String deletionSql = "DELETE FROM `" + dbText + "` where `ID`=" + ID;
        System.out.println(deletionSql);

        Connection connection = dc.connection();

        /**
         *
         * 执行删除语句
         *
         */
        try {

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(deletionSql);

            update(connection);

            stmt.close();
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        /**
         * TableView live update
         */

    }


    public void teamAddAction() {
        Connection connection = dc.connection();

        String addSql = "insert into `" + dbText + "`(Name, Age, Gender, Position, Goals, NG, PK, OG, Fouls, Club, Height, Weight)" +
                " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement pstmt = connection.prepareStatement(addSql);


            pstmt.setString(1, textName.getText());
            pstmt.setInt(2, Integer.parseInt(textAge.getText()));
            pstmt.setString(3, textGender.getText());
            pstmt.setString(4, textPosition.getText());
            pstmt.setInt(5, Integer.parseInt(textGoals.getText()));
            pstmt.setInt(6, Integer.parseInt(textNG.getText()));
            pstmt.setInt(7, Integer.parseInt(textPK.getText()));
            pstmt.setInt(8, Integer.parseInt(textOG.getText()));
            pstmt.setInt(9, Integer.parseInt(textFouls.getText()));
            pstmt.setString(10, dbText);
            pstmt.setDouble(11, Double.parseDouble(textHeight.getText()));
            pstmt.setDouble(12, Double.parseDouble(textWeight.getText()));

            pstmt.executeUpdate();

            update(connection);

            pstmt.close();
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }


    /**
     * 更新队伍成员信息 @teamUpdateBtn
     *
     */
    public void teamUpdateAction() {

        try {
            /**
             * 获取需要update信息的player
             */
            Player player = playerTable.getSelectionModel().getSelectedItem();
            int ID = player.getID();

            /**
             * 根据TextField更新信息
             */
            String updateName = Objects.equals(textName.getText(), "") ? player.getName() : textName.getText();
            int updateAge = Objects.equals(textAge.getText(), "") ? player.getAge() : Integer.parseInt(textAge.getText());
            String updateGender = Objects.equals(textGender.getText(), "") ? player.getGender() : textGender.getText();
            String updatePosition = Objects.equals(textPosition.getText(), "") ? player.getPosition() : textPosition.getText();
            int updateGoals = Objects.equals(textGoals.getText(), "") ? player.getGoals() : player.getGoals() + Integer.parseInt(textGoals.getText());
            int updateNG = Objects.equals(textNG.getText(), "") ? player.getNG() : player.getNG() + Integer.parseInt(textNG.getText());
            int updatePK = Objects.equals(textPK.getText(), "") ? player.getPK() : player.getPK() + Integer.parseInt(textPK.getText());
            int updateOG = Objects.equals(textOG.getText(), "") ? player.getOG() : player.getOG() + Integer.parseInt(textOG.getText());
            int updateFouls = Objects.equals(textFouls.getText(), "") ? player.getFouls() : player.getFouls() + Integer.parseInt(textFouls.getText());
            Double updateHeight = Objects.equals(textHeight.getText(), "") ? player.getHeight() : Double.parseDouble(textHeight.getText());
            Double updateWeight = Objects.equals(textWeight.getText(), "") ? player.getWeight() : Double.parseDouble(textWeight.getText());


            String updateSql = "UPDATE `" + dbText + "` SET Name = '" + updateName + "', Age = " + updateAge + ", Gender = '" + updateGender + "', Position = '" + updatePosition + "', " +
                    "Goals = " + updateGoals + ", NG = " + updateNG + ", PK = " + updatePK + ", OG = " + updateOG + ", Fouls = " + updateFouls + ", " +
                    "Height = " + updateHeight + ", Weight = " + updateWeight + " WHERE `" + dbText + "`.`ID` = " + ID;

            /**
             * 创建连接执行sql语句
             */
            Connection connection = dc.connection();
            PreparedStatement pstmt = connection.prepareStatement(updateSql);
            pstmt.executeUpdate();

            update(connection);

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * 删除所有player数据
     *
     */
    public void refreshTableAction() {
        try {

            Connection connection = dc.connection();

            System.out.println("refreshTableAction: dbtext->" + dbText);
            String deleteTableSql = "DROP TABLE IF EXISTS `" + dbText + "`";
            PreparedStatement deletePstmt = connection.prepareStatement(deleteTableSql);
            deletePstmt.executeUpdate();
            deletePstmt.close();

            String makeTableSql = "CREATE TABLE IF NOT EXISTS `" + dbText + "` (" +
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
            PreparedStatement makeTablePstmt = connection.prepareStatement(makeTableSql);
            makeTablePstmt.executeUpdate();
            makeTablePstmt.close();

            update(connection);

            connection.close();;

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }


    /**
     * 重新加载数据
     */
    @FXML
    public void reloadAction() {
        try {

            Connection connection = dc.connection();

            update(connection);
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }


    @FXML
    public void makeSomePlayerDataAction() {
        try {
            Connection connection = dc.connection();

            String makeSql = "INSERT INTO `" + dbText + "` (ID, Name, Age, Gender, Position, Goals, NG, PK, OG, Fouls, Club, Height, Weight) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            for (int i = 1; i <= 11; i++) {
                PreparedStatement makePstmt = connection.prepareStatement(makeSql);

                makePstmt.setInt(1, i);
                makePstmt.setString(2, "Player" + i);
                makePstmt.setInt(3, 30);
                makePstmt.setString(4, "male");
                makePstmt.setString(5, "forward");
                makePstmt.setInt(6, 0);
                makePstmt.setInt(7, 0);
                makePstmt.setInt(8, 0);
                makePstmt.setInt(9, 0);
                makePstmt.setInt(10, 0);
                makePstmt.setString(11, dbText);
                makePstmt.setDouble(12, 1.8);
                makePstmt.setDouble(13, 65);

                makePstmt.executeUpdate();
                makePstmt.close();

            }

            update(connection);
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    public void update(Connection connection) {

        String sql = "SELECT * FROM `" + dbText + "` WHERE `ID` >= 1";

        columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
        columnGoals.setCellValueFactory(new PropertyValueFactory<>("Goals"));
        columnNG.setCellValueFactory(new PropertyValueFactory<>("NG"));
        columnPK.setCellValueFactory(new PropertyValueFactory<>("PK"));
        columnOG.setCellValueFactory(new PropertyValueFactory<>("OG"));
        columnFouls.setCellValueFactory(new PropertyValueFactory<>("Fouls"));

        // System.out.printf("location: " + location.toString()  + " resources: " + resources.toString());

        playerTable.setItems(null);
        playerTable.setItems(loadDataFromDatabase(sql, connection));

        /**
         * 输入之后将TextField设置为空
         */
        textName.clear();
        textAge.clear();
        textGender.clear();
        textPosition.clear();
        textGoals.clear();
        textNG.clear();
        textPK.clear();
        textOG.clear();
        textFouls.clear();
        textHeight.clear();
        textWeight.clear();

    }


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
     *
     * 打开面板进行初始化
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            System.out.println("Look here, it is " + dbText);
            dc = new DBConnection();
            Connection connection = dc.connection();


            /**
             * 如何数据库中没有球队表，则创建
             */
            String checkSql = "CREATE TABLE IF NOT EXISTS `" + dbText + "` (" +
                    "`ID` INT UNSIGNED AUTO_INCREMENT," +
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
             * 初始化显示table
             */
            String sql = "SELECT * FROM `" + dbText + "` WHERE `ID` >= 1";

            /**
             * 在输入栏右侧显示'Name', 'Position', 'Goals', 'Fouls'
             *
             */
            columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            columnPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
            columnGoals.setCellValueFactory(new PropertyValueFactory<>("Goals"));
            columnNG.setCellValueFactory(new PropertyValueFactory<>("NG"));
            columnPK.setCellValueFactory(new PropertyValueFactory<>("PK"));
            columnOG.setCellValueFactory(new PropertyValueFactory<>("OG"));
            columnFouls.setCellValueFactory(new PropertyValueFactory<>("Fouls"));

            playerTable.setItems(null);
            playerTable.setItems(loadDataFromDatabase(sql));

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
