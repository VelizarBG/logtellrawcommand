package velizarbg.logtellrawcommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class LogTellrawCommand {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogTellrawCommand.class);

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext) {
		dispatcher.register(literal("logtellraw")
			.requires(source -> source.hasPermission(2))
			.then(literal("targetless")
				.then(argument("message", ComponentArgument.textComponent(commandBuildContext))
					.executes(context -> {
						LOGGER.info((ComponentArgument.getResolvedComponent(context, "message", null)).getString());
						return 0;
					})
				)
			)
			.then(literal("targets")
				.then(argument("targets", EntityArgument.players())
					.then(argument("message", ComponentArgument.textComponent(commandBuildContext))
						.executes(context -> {
							LOGGER.info((ComponentArgument.getResolvedComponent(context, "message", null)).getString());
							int i = 0;
							try {
								for (ServerPlayer player : EntityArgument.getPlayers(context, "targets")) {
									player.sendSystemMessage(ComponentArgument.getResolvedComponent(context, "message", player), false);
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
