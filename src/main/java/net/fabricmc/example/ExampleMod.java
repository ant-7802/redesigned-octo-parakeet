import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class HideChatMod implements ClientModInitializer {

    private static boolean isChatHidden = true;

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(InputUtil.fromTranslationKey("key.hide_chat"), GLFW.GLFW_KEY_T, "key.categories.misc");
        KeyBindingHelper.registerKeyBinding(InputUtil.fromTranslationKey("key.hide_chat"), GLFW.GLFW_KEY_SLASH, "key.categories.misc");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen instanceof ChatScreen) {
                ChatScreen chatScreen = (ChatScreen) client.currentScreen;
                if (isChatHidden) {
                    if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_T) ||
                        InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_SLASH)) {
                        isChatHidden = false;
                        chatScreen.setMessage(new LiteralText(""));
                    }
                } else {
                    if (!ScreenExtensions.getText(chatScreen).equals(new LiteralText(""))) {
                        isChatHidden = true;
                        chatScreen.setMessage(new LiteralText(""));
                    }
                }
            }
        });
    }
}
