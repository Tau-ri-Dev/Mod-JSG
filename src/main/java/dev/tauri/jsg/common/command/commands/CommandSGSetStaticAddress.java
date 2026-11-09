package dev.tauri.jsg.common.command.commands;

import com.mojang.brigadier.context.CommandContext;
import dev.tauri.jsg.api.stargate.network.address.StargateAddressDynamic;
import dev.tauri.jsgcore.common.helper.BlockHelper;
import dev.tauri.jsgcore.common.symbol.SymbolType;
import dev.tauri.jsgcore.common.symbol.SymbolUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class CommandSGSetStaticAddress extends CommandSGSetAddress {
    @Override
    public String getName() {
        return "sgsetstaticaddress";
    }

    @Override
    public String getGeneralUsage() {
        return "sgsetstaticaddress [x y z] <map=> *glyphs";
    }

    @Override
    public String getDescription() {
        return "Sets the static address of the specified gate.";
    }

    @Override
    protected int setAddress(@Nullable BlockPos position, ResourceLocation map, List<String> glyphs, @NotNull CommandContext<CommandSourceStack> ctx) {
        var stargate = getStargate(position, ctx);
        if (stargate == null) return 0;

        if (map == null || glyphs == null) {
            baseCommand.sendErrorMess(ctx.getSource(), "commands.sgsetstaticaddress.noaddressspace");
            return 0;
        }

        var symbolType = SymbolType.byId(map);
        if (symbolType == null) {
            baseCommand.sendErrorMess(ctx.getSource(), "commands.sgsetstaticaddress.noaddressspace");
            return 0;
        }

        var address = new StargateAddressDynamic(symbolType);
        var i = 0;
        for (var s : glyphs) {
            i++;
            if (i > 8)
                break;
            var symbol = SymbolUtil.getSymbolFromNameIndex(symbolType, s.replace("_", " "));
            if (symbol == null || !symbol.isValidForAddress()) {
                baseCommand.sendErrorMess(ctx.getSource(), "commands.sgsetstaticaddress.wrongsymbol", i);
                return 0;
            }
            if (address.contains(symbol)) {
                baseCommand.sendErrorMess(ctx.getSource(), "commands.sgsetstaticaddress.duplicatesymbol", i);
                return 0;
            }
            address.addSymbol(symbol);
        }
        if (i < 8 || address.getSize() < 8) {
            baseCommand.sendErrorMess(ctx.getSource(), "commands.sgsetstaticaddress.wrongaddress");
            return 0;
        }

        stargate.setStaticGateAddress(symbolType, address.toImmutable());
        baseCommand.sendSuccessMess(ctx.getSource(), "commands.sgsetstaticaddress.success", BlockHelper.blockPosToBetterString(position), address.toString());
        return 1;
    }
}
