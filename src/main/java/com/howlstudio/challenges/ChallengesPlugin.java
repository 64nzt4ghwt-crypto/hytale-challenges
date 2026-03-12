package com.howlstudio.challenges;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/** ChallengeSystem — Daily and weekly player challenges with progress tracking and rewards. */
public final class ChallengesPlugin extends JavaPlugin {
    private ChallengeManager mgr;
    public ChallengesPlugin(JavaPluginInit init){super(init);}
    @Override protected void setup(){
        System.out.println("[Challenges] Loading...");
        mgr=new ChallengeManager(getDataDirectory());
        new ChallengeListener(mgr).register();
        CommandManager.get().register(mgr.getChallengesCommand());
        System.out.println("[Challenges] Ready. "+mgr.getChallengeCount()+" challenges.");
    }
    @Override protected void shutdown(){if(mgr!=null)mgr.save();System.out.println("[Challenges] Stopped.");}
}
