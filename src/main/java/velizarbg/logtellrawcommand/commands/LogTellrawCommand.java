package velizarbg.logtellrawcommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Texts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class LogTellrawCommand {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogTellrawCommand.class);

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(literal("logtellraw")
			.requires(source -> source.hasPermissionLevel(2))
			.then(literal("targetless")
				.then(argument("message", TextArgumentType.text())
					.executes(context -> {
						LOGGER.info(((StringVisitable) Texts.parse(context.getSource(), TextArgumentType.getTextArgument(context, "message"), null, 0)).getString());
						return 0;
					})
				)
			)
			.then(literal("targets")
				.then(argument("targets", EntityArgumentType.players())
					.then(argument("message", TextArgumentType.text())
						.executes(context -> {
							LOGGER.info(((StringVisitable) Texts.parse(context.getSource(), TextArgumentType.getTextArgument(context, "message"), null, 0)).getString());
							int i = 0;
							try {
								for (ServerPlayerEntity serverPlayerEntity : EntityArgumentType.getPlayers(context, "targets")) {
									serverPlayerEntity.sendMessage(Texts.parse(context.getSource(), TextArgumentType.getTextArgument(context, "message"), serverPlayerEntity, 0), false);
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
