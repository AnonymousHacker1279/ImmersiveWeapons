This major update ports to MC 1.20.4 and adds a huge sum of new features!

### Feature Changes / Additions

- New Staffs
	- Sculk Staff, which allows the player to create sonic blasts like the Warden
	- Recovery Staff, which allows the player to heal themselves or a target based on the last amount of damage they
	  took
- The Sword
	- Combines the effects of all endgame swords
- Star Forge
	- A multiblock crafting structure which is now used to create Astral/Starstorm gear
- New Tiltros entry portal
	- The three-part Warrior Statue and Azul Stained Orchid have been removed
	- A new Tiltros Portal Frame block has been added which can be crafted and activated with Azul Keystones
	- The new portal is formed by making a minimum of a 4x4 frame (corners optional), in a shape like the End Portal
- More advancements
- Throwable Improvements
	- Add Flashbangs, which blind players and stun mobs
	- Smoke grenades now clear targets of nearby entities
	- These throwables are able to bounce off surfaces and entities, and throws can be charged
- Ventus Staff can now reflect projectiles, like the armor effect
- Flares can now direct Minutemen to attack specific targets
	- Can be fired into an entity to mark it specifically as a target
	- Can be fired near a group, and the closest one to the flare will be marked as a target
- The Commander
	- A new boss entity that spawns in Battlefields and controls waves of Dying Soldiers
- Commander Pedestal
	- Dropped by The Commander, and allows Minutemen/Field Medic statues placed on top to activate outside of
	  Battlefield biomes
	- Can be upgraded with Pedestal Augments, which improve aspects of the spawned entities
- Blaze Powder can now be used with firearms
	- It has a higher velocity boost compared to other powder types, at 10%
	- Projectiles will have a flame trail behind them when using Blaze Powder
- Other QoL improvements:
	- Make some items compostable
	- Add more vibration-triggering events to items/blocks/entities
	- General improvements to the codebase, possibly improving performance and stability
- Implement [Cobalt Config](https://github.com/AnonymousHacker1279/CobaltConfig)
	- This is my new configuration library, designed to be more user-friendly and powerful than the old one
	- Configurations now exist as JSON files and can be edited either manually or via the new in-game editor

### Bugfixes

(Includes all fixes backported to v1.27.x on MC 1.20.1 so far, most of which are not present on the v1.28.x MC 1.20.2
release)

Additional fixes include:

- Fix Skygazer enchantment upgrades past level 127 resulting in overflows and resetting back to zero
	- A hard-enforced cap at 255 is now present due to game limitations