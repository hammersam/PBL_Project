package main.model;


/**
 * 主裁判（一个主裁判与两个助手相组合）
 */
public class Referee {

    /** {{{
     *
     * 姓名
     */
    private String name;

    /**
     * 一个Referee配合两个RefereeAssistant
     *
     */
    private RefereeAssistant refereeAssistantA;

    private RefereeAssistant refereeAssistantB;

    /**
     *
     * }}}
     */


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


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


}
