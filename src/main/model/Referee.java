package main.model;

import java.util.Random;

/**
 * 主裁判
 */
public class Referee {

    /**
     * 姓名
     */
    private String name;

    /**
     * 一个Referee配合两个RefereeAssistant
     *
     */
    private RefereeAssistant refereeAssistantA;

    public Referee(String name, RefereeAssistant refereeAssistantA, RefereeAssistant refereeAssistantB) {
        setName(name);
        setRefereeAssistantA(refereeAssistantA);
        setRefereeAssistantB(refereeAssistantB);
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

    private RefereeAssistant refereeAssistantB;

    /**
     * Referee判断给出比赛结果
     */
    public void judge(Team teamA, Team teamB, Referee referee, RefereeAssistant refereeAssistantA,
                      RefereeAssistant refereeAssistantB, String stage, Field field, Schedule schedule) {
        Random rand = new Random();
        /**
         * 小组赛
         */
        if (stage == "groupMatches") {
            int aGoals = rand.nextInt(10);
            int bGoals = rand.nextInt(10);
            int aFouls = rand.nextInt(10);
            int bFouls = rand.nextInt(10);
            if (aGoals > bGoals) {
                teamA.setWon(1);
                teamB.setLost(1);
            } else if (aGoals < bGoals) {
                teamA.setLost(1);
                teamB.setWon(1);
            } else {
                teamA.setDrawn(1);
                teamA.setDrawn(1);
            }
            teamA.setGF(aGoals);
            teamA.setGA(bGoals);
            teamA.setGD(aGoals - bGoals);
            teamB.setGF(bGoals);
            teamB.setGA(aGoals);
            teamB.setGD(bGoals - aGoals);
            refereeAssistantA.judge(teamA, aGoals, aFouls);
            refereeAssistantB.judge(teamB, bGoals, bFouls);
            schedule.generateRecord(teamA, teamB, referee, refereeAssistantA, refereeAssistantB, field, aGoals, bGoals, stage);
        }

        /**
         * 淘汰赛(没有平局)
         */
        if (stage == "knockout") {
            int aGoals = rand.nextInt(10);
            int bGoals = rand.nextInt(10);
            int aFouls = rand.nextInt(10);
            int bFouls = rand.nextInt(10);
            if (aGoals > bGoals) {
                teamA.setWon(1);
                teamB.setLost(1);
            }
            if (aGoals < bGoals) {
                teamA.setLost(1);
                teamB.setWon(1);
            }
            teamA.setGF(aGoals);
            teamA.setGA(bGoals);
            teamA.setGD(aGoals - bGoals);
            teamB.setGF(bGoals);
            teamB.setGA(aGoals);
            teamB.setGD(bGoals - aGoals);
            refereeAssistantA.judge(teamA, aGoals, aFouls);
            refereeAssistantB.judge(teamB, bGoals, bFouls);
            schedule.generateRecord(teamA, teamB, referee, refereeAssistantA, refereeAssistantB, field, aGoals, bGoals, stage);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
