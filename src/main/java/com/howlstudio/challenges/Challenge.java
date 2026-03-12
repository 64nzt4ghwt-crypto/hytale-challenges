package com.howlstudio.challenges;
public class Challenge {
    public enum Type { KILLS, DEATHS, PLAYTIME_MIN, COMMANDS }
    private final String id,name,description,reward;
    private final Type type;
    private final int goal;
    private final boolean weekly;
    public Challenge(String id,String name,String description,String reward,Type type,int goal,boolean weekly){
        this.id=id;this.name=name;this.description=description;this.reward=reward;this.type=type;this.goal=goal;this.weekly=weekly;
    }
    public String getId(){return id;} public String getName(){return name;}
    public String getDescription(){return description;} public String getReward(){return reward;}
    public Type getType(){return type;} public int getGoal(){return goal;}
    public boolean isWeekly(){return weekly;}
    public String getTypeTag(){return weekly?"§5[Weekly]§r":"§b[Daily]§r";}
}
