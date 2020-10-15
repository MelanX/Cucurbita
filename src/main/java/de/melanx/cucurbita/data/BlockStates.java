package de.melanx.cucurbita.data;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.registration.ModBlocks;
import io.github.noeppi_noeppi.libx.data.provider.BlockStateProviderBase;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProviderBase {
    public BlockStates(DataGenerator generator, ExistingFileHelper helper) {
        super(Cucurbita.getInstance(), generator, helper);
        manualModel(ModBlocks.HOLLOWED_PUMPKIN);
        manualModel(ModBlocks.HOMEMADE_REFINERY);
    }
}
