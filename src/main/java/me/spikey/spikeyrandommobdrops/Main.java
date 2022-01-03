package me.spikey.spikeyrandommobdrops;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public class Main extends JavaPlugin implements Listener {
    private Random random;
    private List<Material> mats;
    private List<Material> blacklist;

    private List<EntityType> entities;
    public static boolean dropRandomItem = true;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        random = new Random();
        Bukkit.getPluginManager().registerEvents(this, this);

        mats = Lists.newArrayList(Material.values());
        blacklist = Lists.newArrayList();

        entities = Lists.newArrayList();

        for (Material material : Material.values()) {
            if (material.isAir()) mats.remove(material);
            if (material.isLegacy()) mats.remove(material);
            if (!material.isItem()) mats.remove(material);
        }

        mats.remove(Material.VOID_AIR);
        mats.remove(Material.AIR);
        mats.remove(Material.CAVE_AIR);

        for (String str : getConfig().getStringList("itemBlacklist")) {
            if (Material.valueOf(str.toUpperCase()) == null) continue;
            mats.remove(Material.valueOf(str.toUpperCase()));
            blacklist.add(Material.valueOf(str.toUpperCase()));
        }

        for (String str : getConfig().getStringList("entities")) {
            if (EntityType.valueOf(str.toUpperCase()) == null) continue;
            entities.add(EntityType.valueOf(str.toUpperCase()));
        }

        getCommand("togglerandomitems").setExecutor(new ToggleDropItem());
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {
        if (!dropRandomItem) return;
        if (event.getEntity().getKiller() == null) return;
        if (!entities.contains(event.getEntityType())) return;
        if (!event.getEntity().getKiller().hasPermission("randommobs.dropitems")) return;
        event.getDrops().add(new ItemStack(mats.get(random.nextInt(0, 100000) % mats.size())));
    }
}
