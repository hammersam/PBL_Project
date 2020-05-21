package main.model;

import java.util.Random;

/**
 * 裁判助手
 */
public class RefereeAssistant {

    /**
     * 姓名
     */
    private String name;

    public RefereeAssistant(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void judge(Team team, int goals, int fouls) {
        Random rand = new Random();

        /**
         * 把进球随机分配到球队的球员身上
         */
        for (int i = 0; i < goals; i++) {
            int randomNum = rand.nextInt(11);
            team.getTeam()[randomNum].setGoals(1);
        }

        /**
         * 把犯规随机分配到球队的球员身上
         */
        for (int i = 0; i < fouls; i++) {
            int randomNum = rand.nextInt(11);
            team.getTeam()[randomNum].setGoals(1);
        }
    }
}
