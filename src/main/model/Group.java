package main.model;

import java.util.Arrays;

/**
 * 小组
 */
public class Group {

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * 小组在league中的编号
     */
    private int ID;
    /**
     * 小组名
     */
    private String name;

    /**
     * 使用数组保存小组队伍
     */
    private Team[] group;

    /**
     * 冠军、亚军、季军
     */
    private Team first_place;

    private Team second_place;

    private Team third_place;

    /**
     * 比赛阶段（小组赛/淘汰赛）
     */
    private String stage;

    /**
     * 每个小组有两名裁判员和四名辅助裁判员
     */
    private Referee refereeA;

    private Referee refereeB;

    private RefereeAssistant refereeAssistantA;

    private RefereeAssistant refereeAssistantB;

    private RefereeAssistant refereeAssistantC;

    private RefereeAssistant refereeAssistantD;

    /**
     * 每个小组有两个比赛场地
     */
    private Field fieldA;

    private Field fieldB;

    public Group(int id, String name, Referee refereeA, Referee refereeB, RefereeAssistant refereeAssistantA, RefereeAssistant refereeAssistantB, RefereeAssistant refereeAssistantC,
                 RefereeAssistant refereeAssistantD, Field fieldA, Field fieldB) {
        setID(id);
        setName(name);
        setRefereeA(refereeA);
        setRefereeB(refereeB);
        setRefereeAssistantA(refereeAssistantA);
        setRefereeAssistantB(refereeAssistantB);
        setRefereeAssistantC(refereeAssistantC);
        setRefereeAssistantD(refereeAssistantD);
        setFieldA(fieldA);
        setFieldB(fieldB);

    }

    public Group(String name, Referee refereeA, Referee refereeB, RefereeAssistant refereeAssistantA, RefereeAssistant refereeAssistantB, RefereeAssistant refereeAssistantC,
                 RefereeAssistant refereeAssistantD, Field fieldA, Field fieldB) {
        setName(name);
        setRefereeA(refereeA);
        setRefereeB(refereeB);
        setRefereeAssistantA(refereeAssistantA);
        setRefereeAssistantB(refereeAssistantB);
        setRefereeAssistantC(refereeAssistantC);
        setRefereeAssistantD(refereeAssistantD);
        setFieldA(fieldA);
        setFieldB(fieldB);

    }

    public Group(String name, Team[] group) {
        setName(name);
        setGroup(group);
    }

    public Group() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team[] getGroup() {
        return group;
    }

    public void setGroup(Team[] group) {
        if (group.length == 16) {
            this.group = group;
        } else {
            System.err.printf("Error: One group can only have 16 teams");
        }
    }

    public void sortByPoints() {
        for (int i = 0; i < 16 && group[i] != null; i++) {
            for (int j = i + 1; j < 16 && group[j] != null; j++) {
                if (group[i].getPoints() < group[j].getPoints()) {
                    Team tmp = group[i];
                    group[i] = group[j];
                    group[j] = tmp;
                }
            }
        }
    }

    public void doRanking() {
        for (int i = 0; i < 16 && group[i] != null; i++) {
            group[i].setRank(i + 1);
        }
    }

    public Team getFirst_place() {
        return first_place;
    }

    public void setFirst_place(Team first_place) {
        this.first_place = first_place;
    }

    public Team getSecond_place() {
        return second_place;
    }

    public void setSecond_place(Team second_place) {
        this.second_place = second_place;
    }

    public Team getThird_place() {
        return third_place;
    }

    public void setThird_place(Team third_place) {
        this.third_place = third_place;
    }

    public Referee getRefereeA() {
        return refereeA;
    }

    public void setRefereeA(Referee refereeA) {
        this.refereeA = refereeA;
    }

    public Referee getRefereeB() {
        return refereeB;
    }

    public void setRefereeB(Referee refereeB) {
        this.refereeB = refereeB;
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

    public RefereeAssistant getRefereeAssistantC() {
        return refereeAssistantC;
    }

    public void setRefereeAssistantC(RefereeAssistant refereeAssistantC) {
        this.refereeAssistantC = refereeAssistantC;
    }

    public RefereeAssistant getRefereeAssistantD() {
        return refereeAssistantD;
    }

    public void setRefereeAssistantD(RefereeAssistant refereeAssistantD) {
        this.refereeAssistantD = refereeAssistantD;
    }

    public Field getFieldA() {
        return fieldA;
    }

    public void setFieldA(Field fieldA) {
        this.fieldA = fieldA;
    }

    public Field getFieldB() {
        return fieldB;
    }

    public void setFieldB(Field fieldB) {
        this.fieldB = fieldB;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getRefereeAName() {
        return this.refereeA.getName();
    }

    public String getRefereeBName() {
        return this.refereeB.getName();
    }

    public String getRefereeAssistantAName() {
        return this.refereeAssistantA.getName();
    }

    public String getRefereeAssistantBName() {
        return this.refereeAssistantB.getName();
    }

    public String getRefereeAssistantCName() {
        return this.refereeAssistantC.getName();
    }

    public String getRefereeAssistantDName() {
        return this.refereeAssistantD.getName();
    }

    public String getFieldAName() {
        return this.fieldA.getFieldName();
    }

    public String getFieldBName() {
        return this.fieldB.getFieldName();
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", group=" + Arrays.toString(group) +
                '}';
    }

}
