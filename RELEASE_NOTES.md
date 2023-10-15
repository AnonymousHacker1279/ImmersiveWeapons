This major update reworks ammunition crafting and tweaks firearms. As usual, there is a handful of other new features.

### Feature Changes / Additions

- Rework ammunition crafting
	- Added a new Ammunition Table, which is used to craft musket balls
	- Materials are added into the table (ingots, nuggets, shards, etc.), and the table will calculate the amount of
	  ammunition that can be crafted from the inputs
	- The table provides a "Density" modifier, which allows ammunition to be weighted at the cost of more materials
		- Higher density ammunition will deal more damage, but will not travel as far
- Implement powder system
	- Firearms now require a form of powder to be used. Either gunpowder, blackpowder, or sulfur dust can be used
		- Potassium Nitrate ore has been added, the drops of which can be used to craft gunpowder or blackpowder
		- The quality of powders, from best to worst, is gunpowder, blackpowder, and sulfur dust
			- Powder quality affects the following:
				- Fire velocity
				- Firearm damage taken
				- Smoke produced
				- Chance to consume powder
- Firearms are now affected by the environment
	- When underwater, there is a 90% misfire chance and bullet velocity is reduced by 50%
	- When raining, bullet velocity is reduced by 30%
	- When in powdered snow, bullet velocity is reduced by 20%
	- These effects can be partially mitigated by equipping the Powder Horn accessory
- Increased generation rate of sulfur ore in the overworld
- Added a shift tooltip to bullet-like items, exposing some of the hidden stats
- Added three new bows
	- Ice Bow - arrows slow targets on impact
	- Dragon's Breath Bow - arrows explode on impact
	- Aurora Bow - arrows experience 4x less gravity
- Added knockback resistance to Super Hans

### Bugfixes

- Fix Minutemen spawned from statues not having weapons
- Add missing endgame ingots to the `ingots.json` tag
- Update death message translations to address some missing cases and fix some existing ones not displaying as expected
- Fix quick stacking out of Tesla Synthesizer slots
- Fix item stacks getting empty tags out of
  nowhere ([Fixes #105](https://github.com/AnonymousHacker1279/ImmersiveWeapons/issues/105))
- Fix Velocity enchantment not being applicable to crossbows

### Other Improvements

- Massive cleanups to projectile-related classes, significantly reducing code duplication across similar projectiles