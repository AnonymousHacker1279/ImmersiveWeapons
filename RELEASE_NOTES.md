This major update ports to MC 26.3, and fixes a large number of bugs found during a full codebase review.

### Feature Changes / Additions

- Brewing recipes for IW potions are now standard datapack recipes, so they can be changed or removed by datapacks
- Gunpowder is now consumed at its listed chance again
    - This had been inverted since v1.28.0, causing powder to be used up far more slowly than intended
- Ore and vegetation generation now follows the vanilla placement order
    - Ore veins are now spread out as intended, which greatly increases the amount of Cobalt, Molten, Tesla, and Void
      ore
    - Trees, flowers, and other features now generate at the correct height
- Removed Mud, Dried Mud, and Hardened Mud (and their stairs, slabs, and windows), as vanilla has its own mud blocks
    - Existing blocks and items will disappear from old worlds. Mud Balls are now crafted from vanilla mud
- Landmine Traps now use their own structure seed, so they no longer share placement with Abandoned Factories
- Added armor trim palettes for IW materials

### Bugfixes

- Fixed Cloud Marble Brick, Blood Sandstone, Burned Oak, and Stardust stairs, slabs, and walls using default block
  properties
    - They broke instantly, and did not require a tool
- Fixed Cut Blood Sandstone Slab using the wrong side texture
- Fixed Stardust Leaves being tagged as requiring stone tools to harvest
- Fixed a crash when the Bloody Sacrifice curse rolled a second set of drops
- Fixed Scorch Shot igniting for 100 seconds per level instead of 5
- Fixed Void Blessing warping to the wrong location, and not preventing the void damage
- Fixed Broken Armor having no effect against attacks without a weapon
- Fixed the Velocity enchantment stacking on arrows that were reloaded with their chunk
- Fixed Barbed Wire only hurting when moving in one direction
- Fixed the Mortar repairing the flint and steel, and landmines exploding twice
- Fixed Celestial Lanterns being counted more than once, or never removed, which could prevent Celestial Towers from
  spawning
- Fixed several block bugs: Biodome Life Support Unit reactivating on every redstone update, Flags consuming the item
  when misplaced, floating Mineral Deposits, and Teleporters not molding bread across dimensions
- Fixed several mob bugs: Hans reacting to zero-damage hits, wave-summoning bosses not counting minions or targeting
  players to the east, mobs staying frozen after escaping a Bear Trap, and the Field Medic ignoring line of sight
- Fixed waterlogged blocks not updating flowing water, and several blocks not waterlogging when placed in water
- Fixed bleed chance and knockback accessories not applying to mace and spear attacks
- Fixed armor toggle keybinds and cooldowns being shared between players in multiplayer
- The server now validates Void and Ventus armor abilities, Star Forge selections, and Ammunition Table density
    - This fixes a crash in the Star Forge caused by invalid selections
- Reduced network traffic from projectiles by only syncing arrows with custom gravity
- Minor fixes to Potent Sulfur, Punji Sticks, Log Shards, the Ammunition Table, Shelf locking, and accessory syncing
- Fixed Morphine not converting to a Used Syringe after use
- Fixed the Storm Creeper explosion radius being smaller than expected
- Fixed the Starmite loot table not dropping Starstorm Shards

### Removals

- The Graveyard structure has been removed