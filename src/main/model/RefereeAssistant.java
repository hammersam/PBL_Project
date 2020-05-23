package main.model;

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

}
