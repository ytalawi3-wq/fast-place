# FastClick (Fabric 1.21.11)

Client-side mod: when you hold the mouse button it hits / places many times per tick.

- Hold **left click** on an entity -> up to `attacksPerTick` attacks per tick
- Hold **right click** with a block or End Crystal -> up to `placesPerTick` placements per tick

## Commands
- `/fastclick` - show status
- `/fastclick toggle` - mod on/off
- `/fastclick attack 20` - attacks per tick (1-50)
- `/fastclick place 20` - places per tick (1-50)
- `/fastclick attack false` / `/fastclick place false` - turn one feature off

Settings are saved in `.minecraft/config/fastclick.json`.

## Build
Requires JDK 21 and internet access.

    ./gradlew build        (or: gradle build)

The jar is created in `build/libs/fastclick-1.0.0.jar` (not the `-sources` one).
Put it in `.minecraft/mods/` together with Fabric API.

If Gradle says a version does not exist, update `gradle.properties` with the
latest versions from https://fabricmc.net/develop/ .
