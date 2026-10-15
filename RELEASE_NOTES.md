This major update reworks firearms and ammunition: new firearm controls and animations, a Gunsmith villager, and the
Ammunition Forge.

**GeckoLib and Player Animation Library are now required dependencies.**

### Feature Changes / Additions

- Firearms have been reworked, and now work like a crossbow
    - Left click to fire, or to reload when empty. Reloading must be held for the full reload time, and ammunition
      and gunpowder are only consumed when it completes
    - Right click to aim down the sights or scope. Aiming reduces inaccuracy by 65%, slows movement, and zooms the view
    - Firearms store their loaded ammunition, which is shown in the tooltip
    - Reload and fire animations for all firearms, for players (first and third person) and for mobs
    - Firing and aiming are rebindable keybinds, and a gun in the offhand is only used when the main hand is empty
    - Gunpowder smoke now uses a shader, and misfires show a flash in the pan
    - Added a client config option to always aim when crouching instead of interacting with blocks and entities
- Added the Gunsmith villager profession, which sells firearms, scopes, molds, and ammunition
    - Gunsmith houses now generate in villages
    - Added the Ammunition Forge as the Gunsmith's workstation
- Added the Ammunition Forge, which smelts ingots into musket balls using a mold
    - Basic molds forge Gold, Copper, Iron, and Cobalt balls, Advanced molds forge Netherite and Molten balls, and
      Master molds forge Tesla, Astral, and Starstorm balls
    - Astral and Starstorm balls are now made in the Star Forge, and Wooden, Stone, Diamond, Ventus, and Void balls
      are bought from the Gunsmith
    - Added a JEI category (via the Compatibility Bridge)
- Smoke grenades and flashbangs are now sold by Weaponsmith villagers
- Smoke grenades now use shaders when Fancy Smoke Grenades is enabled, flood-fill the area, and adapt to the
  environment
    - Smoke grenades and flashbangs have improved throwing physics and a configurable fuse time
- Flares now cast dynamic light using shaders (can be disabled in the client config)
- Dying Soldiers and Minutemen now carry a supply of ammunition and powder, reload their firearms, pick up ammunition
  from
  the ground, and flee when out of ammunition
    - Ammunition regenerates over time
- Bleed effect cooldown reduction per level has been reduced

### Bugfixes

- Fixed bullets not removing invulnerability frames on impact, and the same for the Bleed and Hellfire effects

### Removals

- Removed the Small Parts Table and Ammunition Table
    - Existing tables, and any density-modified musket balls, will disappear from old worlds
    - The density system has been removed entirely
- Removed all Small Parts-related items
    - Scopes are now bought from the Gunsmith, and Pliers are a Toolsmith trade
    - Smoke grenades and flashbangs can no longer be crafted