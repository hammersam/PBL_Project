package main.model;


/**
 * 联盟/联赛（有四个小组GroupA, GroupB, GroupC, GroupD）
 */
public class League {

    /** {{{
     *
     * 联盟姓名
     */
    private String name;

    /**
     * 男子A组
     */
    private Group GroupA;

    /**
     * 男子B组
     */
    private Group GroupB;

    /**
     * 女子组
     */
    private Group GroupC;

    /**
     * }}}
     *
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroupA() {
        return GroupA;
    }

    public void setGroupA(Group groupA) {
        GroupA = groupA;
    }

    public Group getGroupB() {
        return GroupB;
    }

    public void setGroupB(Group groupB) {
        GroupB = groupB;
    }

    public Group getGroupC() {
        return GroupC;
    }

    public void setGroupC(Group groupC) {
        GroupC = groupC;
    }

    public Group getGroupD() {
        return GroupD;
    }

    public void setGroupD(Group groupD) {
        GroupD = groupD;
    }

    /**
     * 成年人组
     */
    private Group GroupD;

}
