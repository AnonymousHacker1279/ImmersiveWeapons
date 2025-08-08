This major update ports to MC 1.21.8.

### Feature Changes / Additions

- Increased the vanilla armor cap from 30 to 100.
    - AttributeFix was previously used for this, but due to lack of updates it has been natively implemented.
- Bumped Starstorm tool damage by 1 point.
- Replaced the Debug Overlay
    - The debug overlay has been removed and replaced with separate features
        - Added damage indicator particles (disabled by default, configurable)
        - Added a player stats tooltip (accessible by holding shift while hovering over the player inventory model)

### Bugfixes

- Fix Tesla Shovel damage being lower than expected.