This minor update fixes several bugs.

### Feature Changes / Additions

- Add Celestial Altar block
	- Effectively replaces the Skygazer enchantment upgrade trades
		- This fixes several issues related to the way trades are handled in MC 1.21, due to them not having stack
		  context
	- Acquired via a trade with a Skygazer

### Bugfixes

- Fix Super Hans being killable before defeating the Champion Tower
- Fix Super Hans not being respawnable by tossing a Hans' Blessing into fire on a Champion Tower
- Fix random bear trap `NullPointerException` crashes with some entities
- Fix max enchant color not applying correctly
- Fix a trade for the Skeleton Merchant being zero-cost and therefore not being able to save properly when it is rolled