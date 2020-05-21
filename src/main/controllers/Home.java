package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import main.dao.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {

    @FXML
    private Label label;

    @FXML
    private Button btnBoyA;

    @FXML
    private Button btnBoyB;

    @FXML
    private Button btnGirl;

    @FXML
    private Button btnAdult;

    @FXML
    private Button groupConfigBtn;


    private DBConnection dc;

    @FXML
    public void goToBoyA(ActionEvent event) throws IOException {

        goToGroup(event);

    }


    @FXML
    public void goToBoyB(ActionEvent event) throws IOException {

        goToGroup(event);

    }


    @FXML
    public void goToGirl(ActionEvent event) throws IOException {

        goToGroup(event);

    }


    @FXML
    public void goToAdult(ActionEvent event) throws IOException {

        goToGroup(event);

    }

    @FXML
    public void configGroupDetail(ActionEvent event) {


        try {

            /**
             * 加载面板
             */
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/groupConfiguration.fxml"));

            /**
             * 生成控制器
             */
            Button button = (Button) event.getSource();
            GroupConfigurationController controller = new GroupConfigurationController(button.getText());
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
     * 跳转到对应的小组中
     * 通过btn.getText()获取到组名称，从而生成controller，
     * 完成"传值"功能 ：）
     */
    public void goToGroup(ActionEvent event) throws IOException {

        /**
         * 获取group name
         */
        Button btn = (Button) event.getSource();
        String dbText = btn.getText();

        /**
         * normal rice
         */
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/group.fxml"));

        /**
         * 生成controller
         */
        GroupController group = new GroupController();
        group.setDbText(dbText);
        loader.setController(group);

        /**
         * normal rice
         */
        Parent root = (Parent) loader.load();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dc = new DBConnection();

    }
}
