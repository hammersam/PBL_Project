package main.css;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/*
*这个类是为Main中的inputPlayer方法提供返回类型的，使inputPlayer能够返回HBox类型的信息以及TextField类型的信息
 */
public class Information {
    private TextField player_name;//单行文本输入
    private TextField player_gender;
    private TextField player_age;
    private HBox  hBox;//将孩子拍成水平一行

    /**
     * 初始化名字、性别、年龄，排版方式
     * @param player_name
     * @param player_gender
     * @param player_age
     * @param hBox
     */
    public Information(TextField player_name, TextField player_gender, TextField player_age, HBox hBox) {
        this.player_name = player_name;
        this.player_gender = player_gender;
        this.player_age = player_age;
        this.hBox = hBox;
    }

    /**
     * 未输入，无反应
     */
    public Information() {
    }


    /*
    get、set系列，获取、更改私有属性
     */
    public TextField getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(TextField player_name) {
        this.player_name = player_name;
    }

    public TextField getPlayer_gender() {
        return player_gender;
    }

    public void setPlayer_gender(TextField player_gender) {
        this.player_gender = player_gender;
    }

    public TextField getPlayer_age() {
        return player_age;
    }

    public void setPlayer_age(TextField player_age) {
        this.player_age = player_age;
    }

    public HBox gethBox() {
        return hBox;
    }

    public void sethBox(HBox hBox) {
        this.hBox = hBox;
    }

    /**
     * 私有属性展示
     * @return
     */
    @Override
    public String toString() {
        return "Information{" +
                "player_name=" + player_name +
                ", player_gender=" + player_gender +
                ", player_age=" + player_age +
                ", hBox=" + hBox +
                '}';
    }
}
