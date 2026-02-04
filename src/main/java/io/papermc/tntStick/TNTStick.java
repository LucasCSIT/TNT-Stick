package io.papermc.tntStick;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;

/**
 * {@code TNTStick} is a plugin for Minecraft that grants the wielder of a stick abilities related to TNT.
 * The player is immune to all explosions.
 * @author Lucas Garcia
 * @version 0.2.0
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

    if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
      fireTNT(player);
    }

    if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
      explodeAroundPlayer(player);
    }
  }

  /**
   * Events related to the player taking explosive damage due to the TNT Stick.
   * @param event
   */
  @EventHandler
  private void onPlayerTakeExplosionDamage(EntityDamageEvent event) {
    if (event.getEntity() instanceof Player player) {
      Material mainHandItem = player.getInventory().getItemInMainHand().getType();
      if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && mainHandItem == Material.STICK) {
        event.setCancelled(true);
      }
    }
  }

  /**
   * Shoots out TNT in front of the player. The TNT explodes after a short time.
   * @param player
   */
  private void fireTNT (Player player){
    Entity entity = player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.TNT);
    entity.setVelocity(player.getLocation().getDirection().multiply(3));
  }

  /**
   * Creates an explosion around the player.
   * @param player
   */
  private void explodeAroundPlayer(Player player){
    // TODO: Explosion occurring 3 times. Should be just once.
    player.getWorld().createExplosion(player.getLocation(), 10);
    player.sendMessage("exploded");
    return;
  }

  /**
   * Checks if the player is holding a stick in their main hand.
   * @param player
   * @return {@code True} if the item in the player's main hand is a stick.
   */
  private boolean isStickInMainHand (Player player){
    return player.getInventory().getItemInMainHand().getType() == Material.STICK;
  }
}