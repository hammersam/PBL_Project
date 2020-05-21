package main.model;

/**
 * 日程表与比赛记录表
 */
public class Schedule {
    /**
     * 比赛记录
     */
    private Record[] records = new Record[10000];

    private int recordsPointer = 0;

    /**
     * 比赛安排
     */
    private Arrangement[] arrangements = new Arrangement[10000];

    private int arrangementsPointer = 0;


    public void generateRecord(Team teamA, Team teamB, Referee referee, RefereeAssistant refereeAssistantA,
                               RefereeAssistant refereeAssistantB, Field field, int aPoints, int bPoints, String stage) {
        this.records[recordsPointer++] = new Record(teamA, teamB, referee, refereeAssistantA,
                                                    refereeAssistantB, field, aPoints, bPoints, stage);
    }

    public void generateArrangement(Team teamA, Team teamB, Referee referee, RefereeAssistant refereeAssistantA,
                                    RefereeAssistant refereeAssistantB, Field field, String stage) {
        this.arrangements[arrangementsPointer++] = new Arrangement(teamA, teamB, referee, refereeAssistantA,
                                                                   refereeAssistantB, field, stage);
    }

    public Record[] getRecords() {
        return records;
    }

    public void setRecords(Record[] records) {
        this.records = records;
    }

    public Arrangement[] getArrangements() {
        return arrangements;
    }

    public void setArrangements(Arrangement[] arrangements) {
        this.arrangements = arrangements;
    }
}
