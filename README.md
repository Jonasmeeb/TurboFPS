# TurboFPS

The ultimate Minecraft optimization mod with smart presets for maximum performance!

## Features

🚀 **5 Performance Presets**
- **Potato PC** - Maximum FPS for ultra-low-end hardware
- **Low End** - High FPS for older computers
- **Balanced** - Perfect mix of FPS and quality (Recommended)
- **High End** - High quality with good FPS
- **Ultra** - Maximum quality for powerful systems

⚡ **Optimizations Include**
- Smart entity culling (don't render distant entities)
- Particle reduction system
- Render distance optimization
- Automatic graphics settings
- Chunk loading optimization
- Sky update optimization
- Entity animation throttling

🎮 **Easy to Use**
- Install and choose your preset
- Works out of the box
- In-game configuration via Mod Menu
- No complicated setup required

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/)
2. Download [Fabric API](https://modrinth.com/mod/fabric-api)
3. Download TurboFPS from [Modrinth](https://modrinth.com/mod/turbofps)
4. Place both mods in your `.minecraft/mods` folder
5. Launch Minecraft and enjoy better FPS!

## Configuration

Access the configuration through:
- Mod Menu → TurboFPS → Config
- Or edit `config/turbofps.json` directly

### Presets

| Preset | Render Distance | Best For |
|--------|----------------|----------|
| Potato PC | 4 chunks | Very weak hardware, maximum FPS |
| Low End | 6 chunks | Older computers, integrated graphics |
| Balanced | 10 chunks | Most users, good balance |
| High End | 16 chunks | Gaming PCs with dedicated GPUs |
| Ultra | 24 chunks | High-end systems, content creators |

## Requirements

- Minecraft 1.21.4+ (Fabric)
- Fabric Loader 0.16.0+
- Fabric API
- Java 21+

## Optional Dependencies

- [Mod Menu](https://modrinth.com/mod/modmenu) - For in-game configuration

## Building from Source

```bash
git clone https://github.com/Jonas/TurboFPS.git
cd TurboFPS
./gradlew build
```

The built jar will be in `build/libs/`

## FAQ

**Q: Will this work with shaders?**  
A: Yes, but some optimizations may conflict with certain shader packs. Try the Balanced or High End preset if you experience issues.

**Q: Can I use this with Optifine?**  
A: TurboFPS is designed for Fabric. For Optifine alternatives, check out [Sodium](https://modrinth.com/mod/sodium) and [Iris](https://modrinth.com/mod/iris).

**Q: Is this compatible with other optimization mods?**  
A: TurboFPS works great alongside Sodium, Lithium, and other Fabric optimization mods!

**Q: My FPS didn't improve much?**  
A: Try a lower preset. Some systems may be bottlenecked by CPU or RAM rather than rendering.

## Performance Tips

1. Start with **Balanced** preset
2. Lower render distance if needed
3. Turn off resource-intensive features in video settings
4. Allocate at least 4GB RAM to Minecraft
5. Update your graphics drivers

## Credits

Created by **Jonas**

Special thanks to:
- The Fabric team
- The Minecraft modding community
- All contributors and testers

## License

MIT License - See LICENSE file for details

## Support

- [Report Issues](https://github.com/Jonasmeeb/TurboFPS/issues)
- [Modrinth Page](https://modrinth.com/mod/turbofps)
- [Discord Community](#) (coming soon!)

---

⭐ If you enjoy TurboFPS, please leave a review on Modrinth!

Made with ❤️ by Jonas