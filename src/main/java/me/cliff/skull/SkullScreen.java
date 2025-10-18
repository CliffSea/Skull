package me.cliff.skull;

import com.mojang.blaze3d.opengl.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Pool;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SkullScreen extends Screen {
    private ShaderLoader shaderLoader;
    private PostEffectProcessor postProcessor;
    private Identifier chosenTexture;
    private static final Identifier SHADER = Identifier.of(Skull.modid, "grayscale");

    private final int texWidth = 64;
    private final int texHeight = 64;

    protected SkullScreen() {
        super(Text.of(""));
        chooseRandomTexture();
    }

    @Override
    protected void applyBlur(DrawContext context) {
        return;
    }

    private void tryLoadProcessor() {
        if (this.shaderLoader == null) return;
        if (this.postProcessor != null) return;

        try {
            this.postProcessor = this.shaderLoader.loadPostEffect(SHADER, DefaultFramebufferSet.MAIN_ONLY);
        } catch (Exception e) {
            this.postProcessor = null;
            e.printStackTrace();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (this.shaderLoader == null) {
            this.shaderLoader = this.client.getShaderLoader();
        }

        if (this.postProcessor == null) {
            tryLoadProcessor();
        }

        if (this.postProcessor != null) {
            GlStateManager._disableDepthTest();
            try {
                this.postProcessor.render(this.client.getFramebuffer(), Pool.TRIVIAL);
            } catch (Exception e) {
                try {
                    this.postProcessor.close();
                } catch (Exception ignored) {}
                this.postProcessor = null;
            }
        }

        GlStateManager._enableDepthTest();
        super.render(context, mouseX, mouseY, deltaTicks);

        int x = (this.width - texWidth) / 2;
        int y = (this.height - texHeight) / 2;

        int yOff = y + 60;

        context.drawTexture(RenderPipelines.GUI_TEXTURED, chosenTexture,x,yOff,0,0,texWidth, texHeight,texWidth,texHeight);

    }

    @Override
    public void removed() {
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }


    @Override
    protected void init() {
        this.shaderLoader = this.client.getShaderLoader();
        tryLoadProcessor();
        InputUtil.setCursorParameters(this.client.getWindow(), 212995, 0, 0);
        if (chosenTexture == null) chooseRandomTexture();

    }

    private void chooseRandomTexture() {
        int i = (int) (Math.random() * 17);
        chosenTexture = Identifier.of(Skull.modid, "textures/gui/"+i+".png");
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        return;
    }

    @Override
    public boolean shouldPause() {
        return true;
    }

}
