package user.meistertisch.tischissmpplugin.players.teleportation;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.BoundingBox;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ParticlesForTeleportation {
    private Location locFrom;
    private Location locTo;
    private int count;
    private final Particle.DustOptions red = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1.0F);
    private final Particle.DustOptions green = new Particle.DustOptions(Color.fromRGB(0, 255, 0), 1.0F);

    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture scheduledFuture;


    public ParticlesForTeleportation(Location locFrom, Location locTo) {
        this.locFrom = locFrom;
        this.locTo = locTo;
        count = 0;
        start();
    }

    private void start(){
        scheduledFuture = service.scheduleAtFixedRate(() -> {
            if(count < 16){
                double x = locFrom.getBlock().getBoundingBox().getCenterX();
                double y = locFrom.getBlock().getBoundingBox().getCenterY();
                double z = locFrom.getBlock().getBoundingBox().getCenterZ();
//                locFrom.getWorld().spawnParticle(Particle.REDSTONE, x,y+1,z, 25, 0,1,0, green);

                x = locTo.getX();
                y = locTo.getY();
                z = locTo.getZ();
                locTo.getWorld().spawnParticle(Particle.REDSTONE, x,y+1,z, 25, 0,0.75,0, red);

                count++;
            } else {
                scheduledFuture.cancel(true);
            }
        }, 0, 200, TimeUnit.MILLISECONDS);
    }
}
