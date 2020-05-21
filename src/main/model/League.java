package main.model;


/**
 * 联赛
 */
public class League {

    /**
     * 联盟姓名
     */
    private String name;

    /**
     * 男子A组
     */
    private Group BoyA;

    /**
     * 男子B组
     */
    private Group BoyB;

    /**
     * 女子组
     */
    private Group Girl;

    /**
     * 成年人组
     */
    private Group Adult;

    /**
     * 比赛记录表与比赛日程表
     */
    private Schedule schedule;


    public League(String name, Group BoyA, Group BoyB, Group Girl, Group Adult) {
        setName(name);
        setBoyA(BoyA);
        setBoyB(BoyB);
        setGirl(Girl);
        setAdult(Adult);
    }

    /**
     * 联盟进行比赛
     */
    public void play(Group group, Schedule schedule) {
        Team[] teams = group.getGroup();
        for (int n = 1; n < 16; n++) {
            for (int i = 0, j = n == 1 ? 2 : 1; i + n < 16 || j + n < 16; i += n, j += n) {
                /**
                 * 同一个小组可以同时进行两场比赛
                 */
                if (i + n < 16) {
                    play(teams[i], teams[i + n], group.getRefereeA(), group.getRefereeAssistantA(),
                            group.getRefereeAssistantB(), group.getStage(), group.getFieldA(), schedule);
                }
                if (j + n < 16) {
                    play(teams[j], teams[j + n], group.getRefereeB(), group.getRefereeAssistantC(),
                            group.getRefereeAssistantD(), group.getStage(), group.getFieldB(), schedule);
                }
            }
        }
    }

    public void generateSchedule(Group groupA, Group groupB, Group groupC, Group groupD, String stage) {

        if (stage == "knockout") {
            schedule.setArrangements(new Arrangement[10000]);
        }

        Team[] teamsA = groupA.getGroup();
        Team[] teamsB = groupB.getGroup();
        Team[] teamsC = groupC.getGroup();
        Team[] teamsD = groupD.getGroup();

        for (int n = 1; n < 16; n++) {
            for (int i = 0, j = n == 1 ? 2 : 1; i + n < 16 || j + n < 16; i += n, j += n) {

                /**
                 * 同一个小组可以同时进行两场比赛
                 * 四个小组可以同时进行八场比赛
                 */

                if (i + n < 16) {
                    schedule.generateArrangement(teamsA[i], teamsA[i + n], groupA.getRefereeA(), groupA.getRefereeAssistantA(),
                            groupA.getRefereeAssistantB(), groupA.getFieldA(), groupA.getStage());
                    schedule.generateArrangement(teamsB[i], teamsB[i + n], groupB.getRefereeA(), groupB.getRefereeAssistantA(),
                            groupB.getRefereeAssistantB(), groupB.getFieldA(), groupB.getStage());
                    schedule.generateArrangement(teamsC[i], teamsC[i + n], groupC.getRefereeA(), groupC.getRefereeAssistantA(),
                            groupC.getRefereeAssistantB(), groupC.getFieldA(), groupC.getStage());
                    schedule.generateArrangement(teamsD[i], teamsD[i + n], groupD.getRefereeA(), groupD.getRefereeAssistantA(),
                            groupD.getRefereeAssistantB(), groupD.getFieldA(), groupD.getStage());
                }

                if (j + n < 16) {
                    schedule.generateArrangement(teamsA[j], teamsA[j + n], groupA.getRefereeA(), groupA.getRefereeAssistantA(),
                            groupA.getRefereeAssistantB(), groupA.getFieldA(), groupA.getStage());
                    schedule.generateArrangement(teamsB[j], teamsB[j + n], groupB.getRefereeA(), groupB.getRefereeAssistantA(),
                            groupB.getRefereeAssistantB(), groupB.getFieldA(), groupB.getStage());
                    schedule.generateArrangement(teamsC[j], teamsC[j + n], groupC.getRefereeA(), groupC.getRefereeAssistantA(),
                            groupC.getRefereeAssistantB(), groupC.getFieldA(), groupC.getStage());
                    schedule.generateArrangement(teamsD[j], teamsD[j + n], groupD.getRefereeA(), groupD.getRefereeAssistantA(),
                            groupD.getRefereeAssistantB(), groupD.getFieldA(), groupD.getStage());
                }
            }
        }

    }

    public void play(Team teamA, Team teamB, Referee refereeA, RefereeAssistant refereeAssistantA,
                     RefereeAssistant refereeAssistantB, String stage, Field field, Schedule schedule) {
        refereeA.judge(teamA, teamB, refereeA, refereeAssistantA, refereeAssistantB, stage, field, schedule);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getBoyA() {
        return BoyA;
    }

    public void setBoyA(Group boyA) {
        BoyA = boyA;
    }

    public Group getBoyB() {
        return BoyB;
    }

    public void setBoyB(Group boyB) {
        BoyB = boyB;
    }

    public Group getGirl() {
        return Girl;
    }

    public void setGirl(Group girl) {
        Girl = girl;
    }

    public Group getAdult() {
        return Adult;
    }

    public void setAdult(Group adult) {
        Adult = adult;
    }
}
