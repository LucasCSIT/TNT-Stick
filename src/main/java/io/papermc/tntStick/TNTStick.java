package io.papermc.tntStick;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;

public class TNTStick extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    private void onPlayerHasStick(PlayerInteractEvent event) {
      Action action = event.getAction();
      Player player = event.getPlayer();

      if (!isStickInMainHand(player)) {
        return;
      }

      if (action.equals(Action.LEFT_CLICK_AIR)) {
        fireTNT(event, player);
      }
    }

    private void fireTNT(PlayerInteractEvent event, Player player) {
      Entity entity = event.getPlayer().getWorld().spawnEntity(player.getEyeLocation(), EntityType.TNT);

      entity.setVelocity(player.getLocation().getDirection().multiply(3));
    }

    private boolean isStickInMainHand(Player player) {
      return player.getInventory().getItemInMainHand().getType() == Material.STICK;
    }
}