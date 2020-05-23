package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import main.dao.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {

    /**
     * {{{
     */
    @FXML
    private Button groupConfigBtn;

    /**
     * }}}
     */


    /**
     * connection of database
     */
    private DBConnection dc;


    @FXML
    public void configGroupDetail() {


        try {

            /**
             * 加载面板
             */
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/League.fxml"));

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dc = new DBConnection();
    }
}
