package main.css;

import main.model.Group;
import main.model.Player;
import main.model.Team;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.util.Optional;


public class Main extends Application {


    /**
     * 4组报名人数记录
     */
    private int x1;
    private int x2;
    private int x3;
    private int x4;

    /*
    *记录这几个组，共四组
     */
    private Group group1;
    private Group group2;
    private Group group3;
    private Group group4;

    /*
    *先寄存记录这几个组的已报名的队伍，各组有16队
     */
    private Team[] teams1 = new Team[16];
    private Team[] teams2 = new Team[16];
    private Team[] teams3 = new Team[16];
    private Team[] teams4 = new Team[16];

    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {//Stage类为javaFX最高层容器


        Application.setUserAgentStylesheet(getClass().getResource("style.css")
                .toExternalForm());


        initial();//初始化四组，每组16支队伍

        /**
         * 设置按钮样式
         */
        TilePane root = new TilePane();//大小一致的“title”孩子
        root.setId("main-land");
        root.setPadding(new Insets(50,50,50,70));//设置内边距

//        root.setHgap(0);//水平间隔
//        root.setVgap(0);//垂直间隔
        root.setPrefTileHeight(150);
        root.setPrefTileWidth(110);

        /*
        *设置按钮，并将按钮设置为TitlePane类的孩子
         */

        Button inputGroup1Information = new Button("成年组     ");
//        inputGroup1Information.setStyle(" -fx-background-color: rgb(40,170,230);");

        inputGroup1Information.getAlignment();
        inputGroup1Information.getStyleClass().add("button1");
        inputGroup1Information.setId("adult");
//        inputGroup1Information.setStyle("-fx-base: rgb(40,170,230);");
        root.getChildren().add(inputGroup1Information);






        Button inputGroup2Information = new Button("校园男子甲组");
        inputGroup2Information.getStyleClass().add("button1");//设置一个叫“button1 ”的类型选择器
        inputGroup2Information.setId("schoolBoyA");//设置一个叫“schoolBoyA”的id选择器
        root.getChildren().add(inputGroup2Information);

        Button inputGroup3Information = new Button("校园男子乙组");
        inputGroup3Information.getStyleClass().add("button1");
        inputGroup3Information.setId("schoolBoyB");
        root.getChildren().add(inputGroup3Information);

        Button inputGroup4Information = new Button("  校园女子组 ");
        inputGroup4Information.getStyleClass().add("button1");
        inputGroup4Information.setId("schoolGirl");
        root.getChildren().add(inputGroup4Information);

        Button inputField = new Button("   场地   ");
        inputField.getStyleClass().add("button1");
        inputField.setId("court");
        root.getChildren().add(inputField);

        Button inputReferee = new Button("   裁判   ");
        inputReferee.getStyleClass().add("button1");
        inputReferee.setId("judge");
        root.getChildren().add(inputReferee);

        Button inputRefereeAssistant = new Button("裁判助理");
        inputRefereeAssistant.getStyleClass().add("button1");
        inputRefereeAssistant.setId("assistant");
        root.getChildren().add(inputRefereeAssistant);

        Button informationbtn = new Button("比赛信息");
        informationbtn.getStyleClass().add("button1");
        informationbtn.setId("gameInformation");
        root.getChildren().add(informationbtn);

        Button startbtn = new Button("进行比赛");
        startbtn.getStyleClass().add("button1");
        startbtn.setId("gaming");
        root.getChildren().add(startbtn);

        Button end = new Button("比赛结束");
        end.getStyleClass().add("button1");
        end.setId("gameOver");
        root.getChildren().add(end);


        /**
         * 设置顶层容器标题，内容区容器样式，顶层容器与底层容器的组合
         */
        Scene scene = new Scene(root,575,575);//Scene是场景图中所有内容的容器，该容器430x400，并且装入root
        primaryStage.setScene(scene);//顶层容器装入scene
        primaryStage.setTitle("      足球联赛系统   -@author Team CXZZW");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("img/icons.jpg")));//设置主界面左上角icon图标
        /**
         * 展示已写好的窗口
         */
        primaryStage.show();


        /*
        *分别设置按钮点击之后的结果
         */

        /*
        *成年组按钮点击之后的事件
         */
        inputGroup1Information.setOnAction(new EventHandler<ActionEvent>() {


            /**
             * 处理，未录到16支队伍，继续输入
             * @param event
             */
            @Override
            public void handle(ActionEvent event) {
                if(getX1()<=15)
                    inputGroup1();
                else
                    error();
            }
        });

        /*
         *校园男子甲组组按钮点击之后的事件
         */
        inputGroup2Information.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(getX2()<=15){
                    inputGroup2();
                }
                else{
                    error();
                }
            }
        });

        /*
         *校园男子乙组组按钮点击之后的事件
         */
        inputGroup3Information.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(getX3()<=15){
                    inputGroup3();
                }
                else{
                    error();
                }
            }
        });

        /*
         *校园女子组组按钮点击之后的事件
         */
        inputGroup4Information.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(getX4()<15){
                    inputGroup4();
                }
                else{
                    error();
                }
            }
        });

        /*
        *比赛结束按钮点击之后的事件
         */
        end.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        /*
         *比赛场地按钮点击之后的事件
         */
        inputField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        /*
         *裁判按钮点击之后的事件
         */
        inputReferee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        /*
         *裁判助理按钮点击之后的事件
         */
        inputRefereeAssistant.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        /*
         *比赛信息按钮点击之后的事件
         */

        informationbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        /*
        *进行比赛按钮点击之后的事件
         */
        startbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }

    /*
    *如果这个组队伍已达到16个还报名弹出错误
     */
    public void error(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        /**
         * 显示左上角icon图标
         */
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("img/icons.jpg")));


        alert.setTitle("出现错误");
        alert.setHeaderText("错误信息");
        alert.setContentText("这个组的队伍已满!");

        alert.showAndWait();
    }
    /*
    *初始化，四组，每组16支队伍
     */
    public void initial(){
        for(int i=0;i<16;i++){
            teams1[i] = new Team();
        }
        for(int i=0;i<16;i++){
            teams2[i] = new Team();
        }
        for(int i=0;i<16;i++){
            teams3[i] = new Team();
        }
        for(int i=0;i<16;i++){
            teams4[i] = new Team();
        }
    }

    /*
     * 输入成年组的队伍
     */
    public void inputGroup1(){
        teams1[getX1()] = inputTeam(getX1(),"成年组");
        if(teams1[getX1()]==null)
            return ;
        if(getX1()==15){
            group1.setGroup(teams1);
        }
        setX1(getX1()+1);
    }

    /*
    输出校园男子甲组的队伍
     */
    public void inputGroup2(){
        teams2[getX2()] = inputTeam(getX2(),"校园男子甲组");
        if(teams2[getX2()]==null)
            return ;
        if(getX2()==15){
            group2.setGroup(teams2);
        }
        setX2(getX2()+1);
    }

    /*
    *输入校园男子乙组的队伍
     */
    public void inputGroup3(){
        teams3[getX3()] = inputTeam(getX3(),"校园男子乙组");
        if(teams3[getX3()]==null)
            return ;
        if(getX3()==15){
            group3.setGroup(teams3);
        }
        setX3(getX3()+1);
    }

    /*
    *输入校园女子组的队伍
     */
    public void inputGroup4(){
        teams4[getX4()] = inputTeam(getX4(),"校园女子组");
        if(teams4[getX4()]==null)
            return ;
        if(getX4()==15){
            group4.setGroup(teams4);
        }
        setX4(getX4()+1);
    }

    /*
    *输入每个对的队员
     */
    public Information inputPlayer(int n){
        HBox context = new HBox();//输入单行无格式文本
        TextField player_name = new TextField();
        TextField player_gender = new TextField();
        TextField player_age = new TextField();

        Label pname = new Label((n+1)+"球员号姓名: ");
        pname.getStyleClass().add("pame");//设置一个叫“pame”的类型选择器

        Label pgender = new Label("球员性别(男/女): ");
        pgender.getStyleClass().add("pgender");

        Label page = new Label("球员年龄: ");
        page.getStyleClass().add("page");


        context.getChildren().addAll(pname,player_name);
        context.getChildren().addAll(pgender,player_gender);
        context.getChildren().addAll(page,player_age);

        Information i = new Information(player_name,player_gender,player_age,context);
        return i;
    }

    /*
    *输入每个组的队伍
     */
    public Team inputTeam(int n,String string){


        Team team = new Team();

        VBox context = new VBox();//把它的孩子，排成垂直一列
        context.setId("v-box");//设置一个叫“v-box”的id选择器
        TextField team_name = new TextField();//输入单行无格式文本内容
        team_name.setId("team_name");
        Label tname = new Label("队伍名字: ");//label，不可编辑文本
        tname.getStyleClass().add("tname");
        context.getChildren().addAll(tname,team_name);//加入垂直分布的行列
        Information[] h = new Information[11];//单行文本输入信息数组
        for(int i=0;i<11;i++){
            h[i] = inputPlayer(i);//输入队员
            context.getChildren().add(h[i].gethBox());//成行排布
        }

        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(context);

        ButtonType ok = new ButtonType("提交",ButtonBar.ButtonData.OK_DONE);

        ButtonType end = new ButtonType("返回",ButtonBar.ButtonData.NO);

        dialogPane.getButtonTypes().add(end);
        dialogPane.getButtonTypes().add(ok);

        Dialog<ButtonType> dlg = new Dialog<ButtonType>();
        dlg.setDialogPane(dialogPane);
        dlg.setTitle(string+"第"+(n+1)+"队球队报名信息");

        /**
         * 设置左上角的icon图标
         */
        Stage stage = (Stage) dlg.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("img/icons.jpg")));


        Optional<ButtonType> result = dlg.showAndWait();
        if(result.isPresent()&&result.get().getButtonData()== ButtonBar.ButtonData.NO){
            return null;
        }
        if(result.isPresent()&&result.get().getButtonData()== ButtonBar.ButtonData.OK_DONE){
            String name = team_name.getText();
            team.setName(name);
            Player[] players = new Player[11];
            for(int i=0;i<11;i++){
                players[i] = new Player();
            }
            for(int i=0;i<11;i++){
                String name1 = h[i].getPlayer_name().getText();
                String gender = h[i].getPlayer_gender().getText();
                int age = Integer.parseInt(h[i].getPlayer_age().getText());

                players[i].setName(name1);
                if(gender.equals("男")){
                    // players[i].setGender(Gender.MALE);
                }
                else if(gender.equals("女")){
                    // players[i].setGender(Gender.FEMALE);
                }
                players[i].setAge(age);
            }
            team.setTeam(players);
        }
        return team;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getX3() {
        return x3;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }

    public int getX4() {
        return x4;
    }

    public void setX4(int x4) {
        this.x4 = x4;
    }

    public Group getGroup1() {
        return group1;
    }

    public void setGroup1(Group group1) {
        this.group1 = group1;
    }

    public Group getGroup2() {
        return group2;
    }

    public void setGroup2(Group group2) {
        this.group2 = group2;
    }

    public Group getGroup3() {
        return group3;
    }

    public void setGroup3(Group group3) {
        this.group3 = group3;
    }

    public Group getGroup4() {
        return group4;
    }

    public void setGroup4(Group group4) {
        this.group4 = group4;
    }
}
