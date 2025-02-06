This major update adds some new features after quite some time. It continues to target MC 1.21.1.

### Feature Changes / Additions

- New material tier, Void
    - An End-themed set containing new tools, armor, weapons, and blocks
        - An upgrade to both Astral and Starstorm sets
    - Dragon's Breath Cannon
        - An upgrade to the Hand Cannon, launching Dragon Fireballs
    - Teleporter Block
        - Teleporters can be linked together using pliers, to instantly warp entities between two locations
    - Void Blessing accessory
        - Upgrade to Hans' Blessing, adding protection to falling into the void by warping the player back to their
          spawn
- Replace overworld biome implementation (Battlefields)
    - Replaced BiomeSquisher for Biolith due to lack of maintenance
    - Reimplemented MC 1.20.1-era surface rules for the Battlefield
    - Deep Dark biomes should now be discoverable
      again (an issue with the library
      itself, [BiomeSquisher#47](https://github.com/lukebemishprojects/BiomeSquisher/issues/47)
      and [BiomeSquisher#48](https://github.com/lukebemishprojects/BiomeSquisher/issues/48))
    - This can affect the generation of existing worlds

### Bugfixes

- Add missing entries to ore tags