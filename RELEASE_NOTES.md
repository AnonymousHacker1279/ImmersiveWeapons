This minor update fixes a few bugs and improves performance.

### Feature Changes / Additions

- Optimize render performance of bullet entities
    - Use a simplified model for musket balls/flares
        - Reduced the hitbox size of these projectiles from 0.25 to 0.15 blocks
    - Use a simplified (but identical) model for cannonballs
        - Reduced the hitbox size of these projectiles from 0.5 to 0.35 blocks
- Optimize render performance of firearms
    - Cull hidden faces on firearm item models
- New configuration option to allow bullet despawn time to be adjusted
- Add crafting recipes to the Minuteman and Medic statues

### Bugfixes

- Prevent the Velocity enchantment from being applied to staff items.
- Items inside the Star Forge are now dropped when the controller is broken