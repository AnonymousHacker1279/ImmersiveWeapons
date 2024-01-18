This minor update includes a huge sum of improvements that were backported from v1.28.0 and the latest MC 1.20.4
development branches.

### Feature Changes / Additions

- Add Lure and Aqua Affinity to the Skygazer max enchant cap config
- Optimize JAR size by minimizing JSON on build, optimizing PNGs, and splitting datagen into a separate source set
- Expose some data like base velocity, base reload time, KB strength, selected powder, and selected ammunition through
  shift tooltips on guns
	- Remove selected powder/ammo from the debug overlay as a result
- Bear trap adjustments:
	- Prevent bear traps from trapping entities under the bosses tag
	- Minor optimization tweak to bear traps by storing the empty collision shape
- Optimize the block entities for Field Medic and Minutemen statues to improve performance. Additionally, reduces
  duplicated code.
- Super Hans adjustments:
	- Store XP drop amount so that it doesn't reset between saves
	- Adjust ground smash attack so that the KB works properly
	- The cooldown is also now variable based on the current health and the difficulty
- Adjust Evil Eye model to rotate 180 degrees so that it doesn't have to be rotated in the renderer every frame
- Optimize panic alarm by removing unnecessary tick handler
- Optimize fog render event handler by rearranging expensive calls

### Bugfixes

- Attempt to fix duplicate UUID logspam in certain scenarios by removing UUID tags of passengers loaded from NBT
- Fix "Unknown recipe category" logspam when joining worlds by marking all custom recipes as special
- Fix iron panels not rotating properly with structures
- Fix the Biodome Life Support Unit x-raying nearby blocks by disabling occlusion
- Fix base bullet KB being overriden by a gun's base KB. The bullet's base KB is now additive.
- Fix bear traps not resetting entity states when broken