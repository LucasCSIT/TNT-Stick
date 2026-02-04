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

/**
 * {@code TNTStick} is a plugin for Minecraft that grants the wielder of a stick abilities related to TNT.
 * @author Lucas Garcia
 * @version 0.0.1
 */
public class TNTStick extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    /**
     * Events related to the player holding a stick in their main hand.
     * @param event
     */
    @EventHandler
    private void onPlayerHasStick(PlayerInteractEvent event) {
      Action action = event.getAction();
      Player player = event.getPlayer();

      if (!isStickInMainHand(player)) {
        return;
      }

      if (action.equals(Action.LEFT_CLICK_AIR)) {
        fireTNT(player);
      }
    }

    /**
     * Shoots out TNT in front of the player. The TNT explodes after a short time.
     * @param player
     */
    private void fireTNT(Player player) {
      Entity entity = player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.TNT);
      entity.setVelocity(player.getLocation().getDirection().multiply(3));
    }

    /**
     * Checks if the player is holding a stick in their main hand.
     * @param player
     * @return {@code True} if the item in the player's main hand is a stick.
     */
    private boolean isStickInMainHand(Player player) {
      return player.getInventory().getItemInMainHand().getType() == Material.STICK;
    }
}