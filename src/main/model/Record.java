package main.model;

/**
 * 单条比赛记录
 */
public class Record {

    /**
     * 一场比赛记录由两支队伍、一个主裁判、两个辅助裁判、一块场地、两支队伍的进球得分组成（因为是单循环，故没有主客场）
     */
    private Team teamA;

    private Team teamB;

    private Referee referee;

    private RefereeAssistant refereeAssistantA;

    private RefereeAssistant refereeAssistantB;

    private Field field;

    private int aPoints;

    private int bPoints;

    private String stage;

    private String situation;

    /**
     * }}}
     *
     */

    public Record(Team teamA, Team teamB, Referee referee, RefereeAssistant refereeAssistantA,
                  RefereeAssistant refereeAssistantB, Field field, int aPoints, int bPoints, String stage) {
        setTeamA(teamA);
        setTeamB(teamB);
        setReferee(referee);
        setRefereeAssistantA(refereeAssistantA);
        setRefereeAssistantB(refereeAssistantB);
        setField(field);
        setaPoints(aPoints);
        setbPoints(bPoints);
    }

    public Record(Team teamA, Team teamB, Referee referee, RefereeAssistant refereeAssistantA,
                  RefereeAssistant refereeAssistantB, int aPoints, int bPoints, Field field, String stage, String situation) {
        setTeamA(teamA);
        setTeamB(teamB);
        setReferee(referee);
        setRefereeAssistantA(refereeAssistantA);
        setRefereeAssistantB(refereeAssistantB);
        setaPoints(aPoints);
        setbPoints(bPoints);
        setField(field);
        setStage(stage);
        setSituation(situation);
    }


    public Record(Team teamA, Team teamB, Referee referee, RefereeAssistant refereeAssistantA,
                  RefereeAssistant refereeAssistantB, Field field, String stage, String situation) {
        setTeamA(teamA);
        setTeamB(teamB);
        setReferee(referee);
        setRefereeAssistantA(refereeAssistantA);
        setRefereeAssistantB(refereeAssistantB);
        setField(field);
        setStage(stage);
        setSituation(situation);
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Record(String stage, String situation, Team teamA, int aPoints, int bPoints, Team teamB) {
        setStage(stage);
        setSituation(situation);
        setTeamA(teamA);
        setaPoints(aPoints);
        setbPoints(bPoints);
        setTeamB(teamB);
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public RefereeAssistant getRefereeAssistantA() {
        return refereeAssistantA;
    }

    public void setRefereeAssistantA(RefereeAssistant refereeAssistantA) {
        this.refereeAssistantA = refereeAssistantA;
    }

    public RefereeAssistant getRefereeAssistantB() {
        return refereeAssistantB;
    }

    public void setRefereeAssistantB(RefereeAssistant refereeAssistantB) {
        this.refereeAssistantB = refereeAssistantB;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getaPoints() {
        return aPoints;
    }

    public void setaPoints(int aPoints) {
        this.aPoints = aPoints;
    }

    public int getbPoints() {
        return bPoints;
    }

    public void setbPoints(int bPoints) {
        this.bPoints = bPoints;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
