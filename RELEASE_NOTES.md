This major update adds a new endgame challenge, Champion Towers. Of course, there are a bunch of other new features and
bugfixes as well.

### Feature Changes / Additions

- Champion Tower structures - a rarely generating tower with high-level mobs inside on each floor. At the top is
  Super Hans, a much more deadly variant of Hans.
- Padded Leather Armor - while it may not be the most protective, it neutralizes movement vibrations entirely
- Kill Count weapon system - track weapon kills and unlock special name tiers as you go
- Hand Cannon - a powerful but slow firing weapon that launches cannonballs (or their exploding variants)
- Nine new accessory items

### Bugfixes

- Fix bleed effect not bypassing armor and enchantment defense
- Fix damage vulnerability effect calculation being too high past level one
- Fix wandering warrior equipment bugs when spawning
- Fix berserk spawn chances for wandering warriors not being very dynamic between difficulties
- Fix projectiles seemingly zigzagging at high velocity
- Fix Celestial Tower armor values being too high on Hard difficulty, making it nearly invulnerable

### Other Improvements

- Tweak bleed effect to deal more damage based on level, with each level increasing damage by 25%
- Include the mortar damage type in the `is_projectile` tag
- Tweak damage vulnerability effect to only be half as effective against entities in the `bosses` tag
- Various Evil Eye improvements:
	- Slightly increase player scanning range based on difficulty and size
	- Now target players when damaged by them
	- Now only drop loot when killed by a player
- Various internal cleanups