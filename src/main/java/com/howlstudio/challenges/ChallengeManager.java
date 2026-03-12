package com.howlstudio.challenges;
import com.hypixel.hytale.component.Ref; import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.nio.file.*; import java.util.*;
public class ChallengeManager {
    private final Path dataDir;
    private final List<Challenge> challenges=new ArrayList<>();
    private final Map<UUID,PlayerProgress> playerData=new HashMap<>();
    public ChallengeManager(Path d){this.dataDir=d;try{Files.createDirectories(d);}catch(Exception e){}loadDefaults();load();}
    private void loadDefaults(){
        challenges.add(new Challenge("kill5","Warrior","Get 5 kills today","100 coins",Challenge.Type.KILLS,5,false));
        challenges.add(new Challenge("play30","Dedicated","Play for 30 minutes","50 coins",Challenge.Type.PLAYTIME_MIN,30,false));
        challenges.add(new Challenge("kill25","Weekly Slayer","Get 25 kills this week","500 coins",Challenge.Type.KILLS,25,true));
        challenges.add(new Challenge("play180","Weekly Regular","Play 3 hours this week","300 coins",Challenge.Type.PLAYTIME_MIN,180,true));
    }
    public int getChallengeCount(){return challenges.size();}
    public PlayerProgress getProgress(UUID uid){return playerData.computeIfAbsent(uid,PlayerProgress::new);}
    public void addKill(UUID uid){PlayerProgress p=getProgress(uid);for(Challenge c:challenges)if(c.getType()==Challenge.Type.KILLS&&!p.isCompleted(c.getId())){p.addProgress(c.getId(),1);checkComplete(uid,c,p);}}
    private void checkComplete(UUID uid,Challenge c,PlayerProgress p){if(p.getProgress(c.getId())>=c.getGoal()&&!p.isCompleted(c.getId())){p.markCompleted(c.getId());System.out.println("[Challenges] "+uid+" completed: "+c.getName());}}
    public void save(){try{StringBuilder sb=new StringBuilder();for(Map.Entry<UUID,PlayerProgress> e:playerData.entrySet()){sb.append(e.getKey()).append(":");}Files.writeString(dataDir.resolve("data.txt"),sb.toString());}catch(Exception ex){}}
    private void load(){}
    public AbstractPlayerCommand getChallengesCommand(){
        return new AbstractPlayerCommand("challenges","View server challenges. /challenges [daily|weekly]"){
            @Override protected void execute(CommandContext ctx,Store<EntityStore> store,Ref<EntityStore> ref,PlayerRef playerRef,World world){
                String filter=ctx.getInputString().trim().toLowerCase();
                boolean showWeekly=filter.equals("weekly");boolean showDaily=filter.equals("daily")||filter.isEmpty();
                PlayerProgress p=getProgress(playerRef.getUuid());
                playerRef.sendMessage(Message.raw("=== Challenges ==="));
                for(Challenge c:challenges){
                    if(showWeekly&&!c.isWeekly())continue;
                    if(showDaily&&c.isWeekly())continue;
                    int prog=p.getProgress(c.getId());boolean done=p.isCompleted(c.getId());
                    String bar=done?"§a[DONE]§r":"§e"+prog+"/"+c.getGoal()+"§r";
                    playerRef.sendMessage(Message.raw(c.getTypeTag()+" §6"+c.getName()+"§r — "+c.getDescription()+" | "+bar+" | Reward: "+c.getReward()));
                }
            }
        };
    }
}
