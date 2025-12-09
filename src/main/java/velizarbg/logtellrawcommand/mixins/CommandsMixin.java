package velizarbg.logtellrawcommand.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import velizarbg.logtellrawcommand.commands.LogTellrawCommand;

@Mixin(Commands.class)
public class CommandsMixin {
	@Shadow @Final private CommandDispatcher<CommandSourceStack> dispatcher;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void onRegister(CallbackInfo ci, @Local(argsOnly = true) CommandBuildContext commandBuildContext) {
		LogTellrawCommand.register(dispatcher, commandBuildContext);
	}
}
