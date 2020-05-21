package main.model;

/**
 * 单个比赛安排
 */

public class Arrangement {

    /**
     * 比赛安排(一场比赛由两支队伍、一个主裁判、两个辅助裁判、一个场地组成)
     */
    private Team teamA;

    private Team teamB;

    private Referee referee;

    private RefereeAssistant refereeAssistantA;

    private RefereeAssistant refereeAssistantB;

    private Field field;

    private String stage;

    public Arrangement(Team teamA, Team teamB, Referee referee, RefereeAssistant refereeAssistantA,
                       RefereeAssistant refereeAssistantB, Field field, String stage) {
        setTeamA(teamA);
        setTeamB(teamB);
        setReferee(referee);
        setRefereeAssistantA(refereeAssistantA);
        setRefereeAssistantB(refereeAssistantB);
        setField(field);
        setStage(stage);
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}

