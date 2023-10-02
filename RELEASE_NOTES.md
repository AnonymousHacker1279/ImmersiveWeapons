This minor update includes another batch of bugfixes and improvements, before development begins on the next content
update.

### Feature Changes / Additions

- Improve performance of Celestial Towers
	- When spawning waves, entities are no longer spawned all at once. Instead, they are added to a queue and up to
	  three mobs per tick are spawned during a summoning wave. This should help reduce lag spikes especially with higher
	  waves.
	- Skeletons and zombies that are spawned are now tagged. When checking to progress the wave, the tower now scans
	  for this tag instead of doing two separate skeleton/zombie type scans.
- Celestial Towers are now added to the `bosses` entity tag
- Celestial Towers can no longer be pushed by other entities
- Improve combat AI of some entities
	- Improve combat responsiveness by adjusting attack goal weights in Dying Soldiers and Minutemen
	- Improve healing tasks in Field Medics
		- Healing is now handled with a separate dedicated goal
		- Area scanning for hurt entities is improved
		- Healing other entities now also removes bleed effects
	- Slight adjustments to speed and armor attributes of these entities
- Rock Spider step height has been reduced by 50% compared to regular spiders

### Bugfixes

- Call `finalizeSpawn` when respawning Super Hans (which properly sets XP drop values and attributes based on
  difficulty)
- Fix an error in the advancement for obtaining padded leather armor that corresponded to the wrong armor set