package com.howlstudio.challenges;
import java.util.*;
public class PlayerProgress {
    private final UUID uuid;
    private final Map<String,Integer> progress=new HashMap<>();
    private final Set<String> completed=new HashSet<>();
    private long lastReset=0;
    public PlayerProgress(UUID uuid){this.uuid=uuid;}
    public UUID getUuid(){return uuid;}
    public int getProgress(String challengeId){return progress.getOrDefault(challengeId,0);}
    public void addProgress(String challengeId,int amount){progress.merge(challengeId,amount,Integer::sum);}
    public boolean isCompleted(String challengeId){return completed.contains(challengeId);}
    public void markCompleted(String challengeId){completed.add(challengeId);}
    public void resetDaily(){progress.entrySet().removeIf(e->true);completed.clear();lastReset=System.currentTimeMillis();}
}
