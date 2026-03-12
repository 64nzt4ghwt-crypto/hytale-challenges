package com.howlstudio.challenges;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
public class ChallengeListener {
    private final ChallengeManager mgr;
    public ChallengeListener(ChallengeManager m){this.mgr=m;}
    public void register(){
        HytaleServer.get().getEventBus().registerGlobal(PlayerReadyEvent.class,e->{
            Player p=e.getPlayer();if(p==null)return;
            PlayerRef ref=p.getPlayerRef();if(ref==null)return;
            ref.sendMessage(com.hypixel.hytale.server.core.Message.raw("[Challenges] Type /challenges to view today's challenges!"));
        });
    }
}
