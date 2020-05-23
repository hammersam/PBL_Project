package main.model;

/**
 * 球队
 */
public class Team {

    /** {{{
     *
     * 球队名称
     */
    private String name;

    /**
     * 使用数组保存球员
     */
    private Player[] team;

    /**
     * 球队得分
     */
    private int points = 0;

    /**
     * 球队在小组中的编号
     */
    private int ID;

    private int rank;

    /**
     * 球队已踢、获胜、平局、输掉的场次
     */
    private int played = 0;

    private int won = 0;

    private int drawn = 0;

    private int lost = 0;

    /**
     * 进球数、失球数、净胜球数
     */
    private int GF = 0;

    private int GA = 0;

    private int GD = 0;

    /**
     * }}}
     */


    public Team() {}


    public Team(int ID, String name, int played, int won, int drawn, int lost, int GF, int GA, int GD, int points) {
        setID(ID);
        setName(name);
        setPlayed(played);
        setWon(won);
        setDrawn(drawn);
        setLost(lost);
        setGF(GF);
        setGA(GA);
        setGD(GD);
        setPoints(points);
    }


    public Team(String name) {
        setName(name);
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Player[] getTeam() {
        return team;
    }


    public void setTeam(Player[] team) {
        this.team = team;
    }


    public int getPoints() {
        return points;
    }


    public void setPoints(int points) {
        this.points = points;
    }


    public int getID() {
        return ID;
    }


    public void setID(int ID) {
        this.ID = ID;
    }


    public int getRank() {
        return rank;
    }


    public void setRank(int rank) {
        this.rank = rank;
    }


    public int getPlayed() {
        return played;
    }


    public void setPlayed(int played) {
        this.played = played;
    }


    public int getWon() {
        return won;
    }


    public void setWon(int won) {
        this.won = won;
    }


    public int getDrawn() {
        return drawn;
    }


    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }


    public int getLost() {
        return lost;
    }


    public void setLost(int lost) {
        this.lost = lost;
    }


    public int getGF() {
        return GF;
    }


    public void setGF(int GF) {
        this.GF = GF;
    }


    public int getGA() {
        return GA;
    }


    public void setGA(int GA) {
        this.GA = GA;
    }


    public int getGD() {
        return GD;
    }


    public void setGD(int GD) {
        this.GD = GD;
    }

}

