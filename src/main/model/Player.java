package main.model;


/**
 * 球员
 */
public class Player {

    /**
     * 球衣号码
     */
    private int ID;


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * 射手榜排名
     */
    private int rank;

    /**
     * 球员名字
     */
    private String name;

    /**
     * 所属队伍的名字
     */
    private String team;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * 球员年龄
     */
    private int age;

    /**
     * 球员性别
     */
    private String gender;

    /**
     * 位置
     */
    private String position;


    /**
     * 球员总进球数
     */
    private int goals = 0;

    /**
     * 正常进球数
     */
    private int NG = 0;

    /**
     * 球员点球进球数
     */
    private int PK = 0;

    /**
     * 乌龙球
     */
    private int OG = 0;

    /**
     *
     */

    /**
     * 球员犯规数
     */
    private int Fouls = 0;


    /**
     * 所属俱乐部
     */
    private String club;


    /**
     * 球员身高
     */
    private Double height;

    /**
     * 球员体重
     */
    private Double weight;



    public Player(int ID, String name, int age, String gender, String position, int goals, int ng, int pk, int og, int fouls, String club, Double height, Double weight) {
        setID(ID);
        setName(name);
        setAge(age);
        setGender(gender);
        setPosition(position);
        setGoals(goals);
        setNG(ng);
        setPK(pk);
        setOG(og);
        setFouls(fouls);
        setClub(club);
        setHeight(height);
        setWeight(weight);
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public Double getHeight() {
        return height;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getNG() {
        return NG;
    }

    public void setNG(int NG) {
        this.NG = NG;
    }

    public int getPK() {
        return PK;
    }

    public void setPK(int PK) {
        this.PK = PK;
    }

    public int getOG() {
        return OG;
    }

    public void setOG(int OG) {
        this.OG = OG;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public int getFouls() {
        return Fouls;
    }

    public void setFouls(int fouls) {
        Fouls += fouls;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

}
