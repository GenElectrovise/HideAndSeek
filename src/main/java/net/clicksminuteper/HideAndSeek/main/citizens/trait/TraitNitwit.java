package net.clicksminuteper.HideAndSeek.main.citizens.trait;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.persistence.DelegatePersistence;
import net.citizensnpcs.api.persistence.LocationPersister;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import net.clicksminuteper.HideAndSeek.main.HideAndSeek;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

/**
 * <a href=https://wiki.citizensnpcs.co/API></a>
 * 
 * @author GenElectrovise
 *
 */
public class TraitNitwit extends Trait {

	JavaPlugin plugin;

	protected TraitNitwit() {
		super("hns_nitwit");
		plugin = JavaPlugin.getPlugin(HideAndSeek.class);
	}

	// see the 'Persistence API' section on wiki (linked in class javadoc)
	@Persist("location")
	@DelegatePersistence(LocationPersister.class)
	Location npcLocation;

	/**
	 * Here you should load up any values you have previously saved (optional). This
	 * does NOT get called when applying the trait for the first time, only loading
	 * onto an existing npc at server start. This is called AFTER onAttach so you
	 * can load defaults in onAttach and they will be overridden here. This is
	 * called BEFORE onSpawn, npc.getBukkitEntity() will return null.
	 */
	public void load(DataKey key) {
		SeekLog.info("Loaded nitwit's IQ! It's a smart one at IQ " + key.getInt("nitwitIQ"));
	}

	/**
	 * Saves a trait to the citizens data file
	 */
	public void save(DataKey key) {
		key.setInt("nitwitIQ", 3);
	}

	/**
	 * Automatically registered as a Listener. Handle a click on this NPC.
	 * 
	 * @param event
	 */
	@EventHandler
	public void click(NPCRightClickEvent event) {
		if (event.getNPC() == this.getNPC()) {
			event.getClicker().sendMessage("Hewooo! I is Nitwit.");
		}

	}

	// Called every tick
	@Override
	public void run() {
		SeekLog.debug("Nitwit running! Wait... they DO something??");
	}

	/**
	 * Run code when your trait is attached to a NPC. This is called BEFORE onSpawn,
	 * so npc.getBukkitEntity() will return null This would be a good place to load
	 * configurable defaults for new NPCs.
	 */
	@Override
	public void onAttach() {
		plugin.getServer().getLogger().info(npc.getName() + "has been assigned Nitwit!");
	}

	/**
	 * Run code when the NPC is despawned. This is called before the entity actually
	 * despawns so npc.getBukkitEntity() is still valid.
	 */
	@Override
	public void onDespawn() {
		SeekLog.info("Nitwit despawned.... WOOO!");
	}

	/**
	 * Run code when the NPC is spawned. Note that npc.getBukkitEntity() will be
	 * null until this method is called. This is called AFTER onAttach and AFTER
	 * Load when the server is started.
	 */
	@Override
	public void onSpawn() {
		SeekLog.info("Nitwit spawned.... not another one!");
	}

	/**
	 * Called on removal. Use to destroy any repeating tasks.
	 */
	@Override
	public void onRemove() {
		SeekLog.info("Phew! Nitwit removed!");
	}

}
