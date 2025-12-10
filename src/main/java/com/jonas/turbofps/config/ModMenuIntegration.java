package com.jonas.turbofps.config;

import com.jonas.turbofps.TurboFPSClient;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return TurboConfigScreen::new;
    }

    public static class TurboConfigScreen extends Screen {
        private final Screen parent;
        private final TurboConfig config;
        private Category currentCategory = Category.PRESETS;

        // Sodium-style colors
        private static final int SIDEBAR_BG = 0xFF0B0B0F;
        private static final int SIDEBAR_SELECTED = 0xFF1E1E28;
        private static final int CONTENT_BG = 0xFF15151C;
        private static final int OPTION_BG = 0xFF1A1A24;
        private static final int OPTION_BG_HOVER = 0xFF20202C;
        private static final int ACCENT_COLOR = 0xFF5B9FFF;
        private static final int TEXT_COLOR = 0xFFFFFFFF;
        private static final int TEXT_SECONDARY = 0xFFB0B0B0;

        public TurboConfigScreen(Screen parent) {
            super(Text.literal("TurboFPS Settings"));
            this.parent = parent;
            this.config = TurboFPSClient.getConfig();
        }

        private enum Category {
            PRESETS("Performance Presets", "⚡"),
            PERFORMANCE("Performance", "🎮"),
            ADVANCED("Advanced", "⚙"),
            ABOUT("About", "ℹ");

            private final String name;
            private final String icon;

            Category(String name, String icon) {
                this.name = name;
                this.icon = icon;
            }

            public String getName() {
                return name;
            }

            public String getIcon() {
                return icon;
            }
        }

        @Override
        protected void init() {
            super.init();

            int sidebarWidth = 150;
            int categoryHeight = 28;
            int categoryStartY = 50;

            int contentX = sidebarWidth + 15;
            int contentWidth = this.width - contentX - 15;

            // Category buttons (Sodium-style sidebar)
            for (int i = 0; i < Category.values().length; i++) {
                Category category = Category.values()[i];
                int buttonY = categoryStartY + (i * categoryHeight);
                boolean isSelected = currentCategory == category;

                SodiumCategoryButton categoryButton = new SodiumCategoryButton(
                        5, buttonY, sidebarWidth - 10, 24,
                        category, isSelected,
                        button -> {
                            currentCategory = category;
                            this.clearAndInit();
                        }
                );

                this.addDrawableChild(categoryButton);
            }

            // Content area based on selected category
            int currentY = 60;
            int spacing = 8;

            switch (currentCategory) {
                case PRESETS:
                    currentY = addPresetsContent(contentX, contentWidth - 20, currentY, spacing);
                    break;
                case PERFORMANCE:
                    currentY = addPerformanceContent(contentX, contentWidth - 20, currentY, spacing);
                    break;
                case ADVANCED:
                    currentY = addAdvancedContent(contentX, contentWidth - 20, currentY, spacing);
                    break;
                case ABOUT:
                    currentY = addAboutContent(contentX, contentWidth - 20, currentY, spacing);
                    break;
            }

            // Bottom buttons (Sodium-style)
            int buttonWidth = 100;
            int buttonSpacing = 8;
            int totalWidth = (buttonWidth * 2) + buttonSpacing;
            int startX = (this.width - totalWidth) / 2;

            this.addDrawableChild(new SodiumButton(
                    startX, this.height - 35, buttonWidth, 24,
                    Text.literal("Done").formatted(Formatting.GREEN),
                    button -> {
                        if (this.client != null) {
                            this.client.setScreen(parent);
                        }
                    }
            ));

            this.addDrawableChild(new SodiumButton(
                    startX + buttonWidth + buttonSpacing, this.height - 35, buttonWidth, 24,
                    Text.literal("Cancel").formatted(Formatting.RED),
                    button -> {
                        if (this.client != null) {
                            this.client.setScreen(parent);
                        }
                    }
            ));
        }

        private int addPresetsContent(int startX, int width, int startY, int spacing) {
            int currentY = startY;

            // Section header
            currentY += 5;

            // Preset selector buttons
            for (PerformancePreset preset : PerformancePreset.values()) {
                boolean isActive = config.getCurrentPreset() == preset;

                SodiumPresetButton presetButton = new SodiumPresetButton(
                        startX, currentY, width, 50,
                        preset, isActive,
                        button -> {
                            config.applyPreset(preset);
                            TurboFPSClient.saveConfig();
                            this.clearAndInit();
                        }
                );

                this.addDrawableChild(presetButton);
                currentY += 54;
            }

            return currentY;
        }

        private int addPerformanceContent(int startX, int width, int startY, int spacing) {
            int currentY = startY + 5;

            // Entity Culling
            this.addDrawableChild(new SodiumToggleButton(
                    startX, currentY, width, 32,
                    "Entity Culling",
                    "Reduce entities rendered outside view",
                    config.isEntityCulling(),
                    button -> {
                        config.setEntityCulling(!config.isEntityCulling());
                        TurboFPSClient.saveConfig();
                        this.clearAndInit();
                    }
            ));
            currentY += 36;

            // Particle Reduction
            this.addDrawableChild(new SodiumToggleButton(
                    startX, currentY, width, 32,
                    "Particle Reduction",
                    "Decrease particle spawn rate",
                    config.isParticleReduction(),
                    button -> {
                        config.setParticleReduction(!config.isParticleReduction());
                        TurboFPSClient.saveConfig();
                        this.clearAndInit();
                    }
            ));
            currentY += 36;

            // Render Optimization
            this.addDrawableChild(new SodiumToggleButton(
                    startX, currentY, width, 32,
                    "Render Optimization",
                    "Optimize rendering pipeline",
                    config.isRenderOptimization(),
                    button -> {
                        config.setRenderOptimization(!config.isRenderOptimization());
                        TurboFPSClient.saveConfig();
                        this.clearAndInit();
                    }
            ));
            currentY += 36;

            return currentY;
        }

        private int addAdvancedContent(int startX, int width, int startY, int spacing) {
            int currentY = startY + 5;

            // Auto Render Distance
            this.addDrawableChild(new SodiumToggleButton(
                    startX, currentY, width, 32,
                    "Auto Render Distance",
                    "Automatically adjust render distance",
                    config.isAutoRenderDistance(),
                    button -> {
                        config.setAutoRenderDistance(!config.isAutoRenderDistance());
                        TurboFPSClient.saveConfig();
                        this.clearAndInit();
                    }
            ));
            currentY += 36;

            // Auto Graphics Settings
            this.addDrawableChild(new SodiumToggleButton(
                    startX, currentY, width, 32,
                    "Auto Graphics Settings",
                    "Dynamically adjust graphics settings",
                    config.isAutoGraphicsSettings(),
                    button -> {
                        config.setAutoGraphicsSettings(!config.isAutoGraphicsSettings());
                        TurboFPSClient.saveConfig();
                        this.clearAndInit();
                    }
            ));
            currentY += 36;

            return currentY;
        }

        private int addAboutContent(int startX, int width, int startY, int spacing) {
            return startY;
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            // Background
            context.fill(0, 0, this.width, this.height, CONTENT_BG);

            // Sidebar background
            int sidebarWidth = 150;
            context.fill(0, 0, sidebarWidth, this.height, SIDEBAR_BG);

            // Title bar
            context.fill(0, 0, this.width, 40, 0xFF0A0A0E);
            context.drawTextWithShadow(this.textRenderer,
                    Text.literal("⚡ TurboFPS").formatted(Formatting.BOLD),
                    15, 15, ACCENT_COLOR);

            // Subtitle in title bar
            context.drawText(this.textRenderer,
                    Text.literal("Performance Optimization"),
                    15, 27, TEXT_SECONDARY, false);

            // Category title in content area
            int contentX = sidebarWidth + 15;
            context.drawTextWithShadow(this.textRenderer,
                    Text.literal(currentCategory.getName()).formatted(Formatting.BOLD),
                    contentX, 45, TEXT_COLOR);

            // Category-specific info
            if (currentCategory == Category.ABOUT) {
                int aboutX = contentX + 10;
                int aboutY = 70;
                context.fill(aboutX - 5, aboutY - 5, aboutX + 300, aboutY + 120, OPTION_BG);

                context.drawTextWithShadow(this.textRenderer,
                        Text.literal("TurboFPS").formatted(Formatting.BOLD, Formatting.GOLD),
                        aboutX + 5, aboutY + 5, TEXT_COLOR);
                context.drawText(this.textRenderer,
                        Text.literal("Version 1.0.0"),
                        aboutX + 5, aboutY + 20, TEXT_SECONDARY, false);
                context.drawText(this.textRenderer,
                        Text.literal("Created by Jonas"),
                        aboutX + 5, aboutY + 35, TEXT_SECONDARY, false);
                context.drawText(this.textRenderer,
                        Text.literal("The ultimate Minecraft optimization mod"),
                        aboutX + 5, aboutY + 55, TEXT_SECONDARY, false);
                context.drawText(this.textRenderer,
                        Text.literal("with smart presets for maximum performance."),
                        aboutX + 5, aboutY + 67, TEXT_SECONDARY, false);
                context.drawText(this.textRenderer,
                        Text.literal("Thank you for using TurboFPS!").formatted(Formatting.ITALIC),
                        aboutX + 5, aboutY + 90, ACCENT_COLOR, false);
            }

            super.render(context, mouseX, mouseY, delta);
        }

        @Override
        public void close() {
            if (this.client != null) {
                this.client.setScreen(parent);
            }
        }

        // Sodium-style Category Button
        private class SodiumCategoryButton extends ButtonWidget {
            private final Category category;
            private final boolean selected;

            public SodiumCategoryButton(int x, int y, int width, int height, Category category,
                                        boolean selected, PressAction onPress) {
                super(x, y, width, height, Text.literal(category.getName()), onPress, DEFAULT_NARRATION_SUPPLIER);
                this.category = category;
                this.selected = selected;
            }

            @Override
            public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
                boolean hovered = this.isHovered();
                int color = selected ? SIDEBAR_SELECTED : (hovered ? 0xFF18181F : SIDEBAR_BG);

                context.fill(this.getX(), this.getY(), this.getX() + this.width,
                        this.getY() + this.height, color);

                if (selected) {
                    context.fill(this.getX(), this.getY(), this.getX() + 3,
                            this.getY() + this.height, ACCENT_COLOR);
                }

                int textColor = selected ? TEXT_COLOR : TEXT_SECONDARY;
                context.drawText(textRenderer,
                        Text.literal(category.getIcon() + " " + category.getName()),
                        this.getX() + 12, this.getY() + (this.height - 8) / 2,
                        textColor, false);
            }
        }

        // Sodium-style Toggle Button
        private class SodiumToggleButton extends ButtonWidget {
            private final String label;
            private final String description;
            private final boolean enabled;

            public SodiumToggleButton(int x, int y, int width, int height, String label,
                                      String description, boolean enabled, PressAction onPress) {
                super(x, y, width, height, Text.literal(label), onPress, DEFAULT_NARRATION_SUPPLIER);
                this.label = label;
                this.description = description;
                this.enabled = enabled;
            }

            @Override
            public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
                boolean hovered = this.isHovered();
                int bgColor = hovered ? OPTION_BG_HOVER : OPTION_BG;

                context.fill(this.getX(), this.getY(), this.getX() + this.width,
                        this.getY() + this.height, bgColor);

                // Label
                context.drawText(textRenderer, Text.literal(label),
                        this.getX() + 8, this.getY() + 6, TEXT_COLOR, false);

                // Description
                context.drawText(textRenderer, Text.literal(description),
                        this.getX() + 8, this.getY() + 18, TEXT_SECONDARY, false);

                // Toggle indicator
                int toggleX = this.getX() + this.width - 50;
                int toggleY = this.getY() + 8;
                int toggleColor = enabled ? 0xFF4CAF50 : 0xFF666666;

                context.fill(toggleX, toggleY, toggleX + 40, toggleY + 16, toggleColor);
                context.drawCenteredTextWithShadow(textRenderer,
                        Text.literal(enabled ? "ON" : "OFF"),
                        toggleX + 20, toggleY + 4, TEXT_COLOR);
            }
        }

        // Sodium-style Preset Button
        private class SodiumPresetButton extends ButtonWidget {
            private final PerformancePreset preset;
            private final boolean active;

            public SodiumPresetButton(int x, int y, int width, int height,
                                      PerformancePreset preset, boolean active, PressAction onPress) {
                super(x, y, width, height, Text.literal(preset.getDisplayName()), onPress, DEFAULT_NARRATION_SUPPLIER);
                this.preset = preset;
                this.active = active;
            }

            @Override
            public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
                boolean hovered = this.isHovered();
                int bgColor = active ? 0xFF2A5C2A : (hovered ? OPTION_BG_HOVER : OPTION_BG);

                context.fill(this.getX(), this.getY(), this.getX() + this.width,
                        this.getY() + this.height, bgColor);

                if (active) {
                    context.fill(this.getX(), this.getY(), this.getX() + 4,
                            this.getY() + this.height, 0xFF4CAF50);
                }

                // Preset name
                String displayName = (active ? "✓ " : "") + preset.getDisplayName();
                context.drawText(textRenderer, Text.literal(displayName).formatted(Formatting.BOLD),
                        this.getX() + 10, this.getY() + 6, active ? 0xFF4CAF50 : TEXT_COLOR, false);

                // Description
                context.drawText(textRenderer, Text.literal(preset.getDescription()),
                        this.getX() + 10, this.getY() + 20, TEXT_SECONDARY, false);

                // Render distance info
                context.drawText(textRenderer,
                        Text.literal("Render Distance: " + preset.getDefaultRenderDistance() + " chunks"),
                        this.getX() + 10, this.getY() + 34, 0xFF888888, false);
            }
        }

        // Sodium-style regular button
        private class SodiumButton extends ButtonWidget {
            public SodiumButton(int x, int y, int width, int height, Text message, PressAction onPress) {
                super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
            }

            @Override
            public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
                boolean hovered = this.isHovered();
                int bgColor = hovered ? 0xFF2A2A35 : OPTION_BG;

                context.fill(this.getX(), this.getY(), this.getX() + this.width,
                        this.getY() + this.height, bgColor);

                context.drawCenteredTextWithShadow(textRenderer, this.getMessage(),
                        this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, TEXT_COLOR);
            }
        }
    }
}