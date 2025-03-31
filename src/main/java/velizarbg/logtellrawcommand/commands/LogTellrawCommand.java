package velizarbg.logtellrawcommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class LogTellrawCommand {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogTellrawCommand.class);

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
		dispatcher.register(literal("logtellraw")
			.requires(source -> source.hasPermissionLevel(2))
			.then(literal("targetless")
				.then(argument("message", TextArgumentType.text(commandRegistryAccess))
					.executes(context -> {
						LOGGER.info((TextArgumentType.parseTextArgument(context, "message", null)).getString());
						return 0;
					})
				)
			)
			.then(literal("targets")
				.then(argument("targets", EntityArgumentType.players())
					.then(argument("message", TextArgumentType.text(commandRegistryAccess))
						.executes(context -> {
							LOGGER.info((TextArgumentType.parseTextArgument(context, "message", null)).getString());
							int i = 0;
							try {
								for (ServerPlayerEntity serverPlayerEntity : EntityArgumentType.getPlayers(context, "targets")) {
									serverPlayerEntity.sendMessageToClient(TextArgumentType.parseTextArgument(context, "message", serverPlayerEntity), false);
									++i;
								}
							} catch (CommandSyntaxException ignored) {
							}
							return i;
						})
					)
				)
			)
		);
	}
}
