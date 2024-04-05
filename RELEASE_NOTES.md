This minor update includes a handful of bugfixes but mostly QoL improvements.

### Feature Changes / Additions

- Remove unused Astral and Starstorm pike head items
- Tweak battlefield biome generation parameters
	- Should hopefully reduce the occurrence of extremely thin biome zones
	- The trench carver has been adjusted as well
- Soldier-type entity improvements (Minutemen/Dying Soldiers)
	- Improve behavior when these entities pick up weapons they don't typically have
		- For example, Minutemen picking up pistols or muskets, or Dying Soldiers picking up a blunderbuss
	- Abstract more of the gun firing code so that entities using firearms perform the same way a player would
	- Soldiers now fire based on their current rotation rather than calculating the delta between it and the target
		- This makes it possible for them to miss by firing too far to the side if they are turned away from their
		  target
	- Soldiers can now use melee weapons if picked up rather than standing around idle
	- Attack intervals for firearms now include the base cooldown of the weapon in use
- Add dedicated flare gun velocity / inaccuracy configuration entries rather than copying the pistol values
- The Commander can only pick up new guns if the base fire velocity is higher than his old one
- Play bullet whizzing sounds when bullets pass near the player
- Rework Tiltros biome theme tracks
	- Added a second Starlight Plains theme
	- Added a new Tiltros Wastes theme (replacing both of the old ones)
	- Added two new Deadman's Desert themes (replacing the old one)
	- Add music discs for the new themes
		- Can be found in dungeon chests of their respective biomes
- Update Immersive Weapons flag recipe to better match its texture
- Buff the Dragon's Breath Bow
	- Hit effects of arrows fired are now applied to entities within the arrow's explosion radius

### Bugfixes

- Fix Astral and Starstorm sword recipes using an extra obsidian rod
- Slight tweaks to the way the `forceSmokeGrenadeParticles` configuration option is handled, to ensure the server is the
  one defining the value
- Improve mixin compatibility (port of the fix
  in [v1.27.7](https://github.com/AnonymousHacker1279/ImmersiveWeapons/releases/tag/v1.27.7)
  for [#114](https://github.com/AnonymousHacker1279/ImmersiveWeapons/issues/114))