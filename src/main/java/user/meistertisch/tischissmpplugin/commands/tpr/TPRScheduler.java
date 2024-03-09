package user.meistertisch.tischissmpplugin.commands.tpr;

import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TPRScheduler {
    private static ScheduledExecutorService service;
    private static ScheduledFuture scheduledFuture;
    //countdown of key
    private static HashMap<TeleportationRequest, Integer> countdown = new HashMap<>();

    public static void setup(){
        service = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = service.scheduleAtFixedRate(() -> {
            if(!countdown.isEmpty()) {
                for (TeleportationRequest request : countdown.keySet()) {
                    if (countdown.get(request) <= 0) {
                        request.getSender().sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_requestExpiredSender)
                                .replace("%player%", request.getTarget().getDisplayName()), TextTypes.NO_SUCCESS));
                        request.getTarget().sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_requestExpiredTarget)
                                .replace("%sender%", request.getSender().getDisplayName()), TextTypes.NO_SUCCESS));
                        countdown.remove(request);
                    } else {
                        countdown.put(request, countdown.get(request) - 1);
                        if(!request.getSender().isOnline()){
                            countdown.put(request, 0);
                        }
                        if(!request.getTarget().isOnline()){
                            countdown.put(request, 0);
                        }
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void request(Player player, Player target, boolean isReversed){
        countdown.put(new TeleportationRequest(player, target, isReversed), 30);
    }

    public static void removeRequest(Player sender, boolean silent){
        for(TeleportationRequest request : countdown.keySet()){
            if(request.getSender() == sender){
                if(!silent){
                    request.getSender().sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_requestCanceled), TextTypes.NO_SUCCESS));
                    request.getTarget().sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_requestCanceledTarget), TextTypes.NO_SUCCESS));
                }
                countdown.remove(request);
            }
        }
    }

    public static void stopScheduler(){
        scheduledFuture.cancel(true);
        service.shutdown();
    }

    public static boolean isSender(Player player){
        for(TeleportationRequest request : countdown.keySet()){
            if(request.getSender() == player){
                return true;
            }
        }
        return false;
    }

    public static Player getTarget(Player sender){
        for(TeleportationRequest request : countdown.keySet()){
            if(request.getSender() == sender){
                return request.getTarget();
            }
        }
        return null;
    }

    public static Player getSender(Player target){
        for(TeleportationRequest request : countdown.keySet()){
            if(request.getTarget() == target){
                return request.getSender();
            }
        }
        return null;
    }

    public static boolean isTarget(Player player){
        for(TeleportationRequest request : countdown.keySet()){
            if(request.getTarget() == player){
                return true;
            }
        }
        return false;
    }

    public static HashMap<TeleportationRequest, Integer> getCountdown() {
        return countdown;
    }
}
