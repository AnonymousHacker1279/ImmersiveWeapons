This major update ports to MC 1.21.5.

### Feature Changes / Additions

- Changes to Tiltros:
    - Increased Tiltros max logical height from 192 to 256 blocks.
    - Warmed up the Deadman's Desert slightly to reduce the amount of snow.
    - Skybox now has a different texture more similar to The End.
    - Portal has a new set of sound effects.
    - Ambient light has been disabled to allow glowing blocks to be more visible.
    - Firefly bushes naturally generate in Starlight Plains.
- Adjusted various block models to consistently face the same direction in the inventory.
- Flag blocks now check to ensure they have stable ground.
- Changes to the scoped musket:
    - The zoom animation is smoother.
    - No longer a separate item; it is handled with a data component.
    - Adding a scope to the musket is now done via an anvil.
- Changes to the Lava Revenant:
    - Hitboxes have been significantly adjusted.
    - A configuration option has been added to adjust the strength of blocks it can destroy.
- Evil Eye attributes are now affected by their size.
- Added the Dying Soldier and The Commander to the `minecraft:raiders` tag, allowing bells to identify them.
- The explosive chocolate bar is now almost entirely indistinguishable from a normal chocolate bar.

### Bugfixes

- Unclamped damage resistance attributes on accessories; this fixes the Berserker's Amulet not having a damage
  vulnerability downside.
- Fix occasional crashes when some soldier-type entities lose their firearms and try to perform melee attacks.