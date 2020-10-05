package de.melanx.cucurbita.blocks;

import de.melanx.cucurbita.blocks.base.BlockBase;
import de.melanx.cucurbita.blocks.tiles.TileHollowedPumpkin;
import de.melanx.cucurbita.core.Registration;
import de.melanx.cucurbita.util.Util;
import de.melanx.cucurbita.util.VanillaPacketDispatcher;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockHollowedPumpkin extends BlockBase {

    private static final VoxelShape INSIDE = makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE), IBooleanFunction.ONLY_FIRST);

    public BlockHollowedPumpkin() {
        super(Properties.create(Material.ORGANIC)
                .harvestTool(ToolType.AXE)
                .harvestLevel(1)
                .hardnessAndResistance(2));
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileHollowedPumpkin) {
            if (player.isSneaking()) {
                Util.withdrawFromInventory(((TileHollowedPumpkin) tile).getInventory(), player);
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(tile);
                return ActionResultType.SUCCESS;
            } else {
                if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() != Registration.ITEM_PUMPKIN_WAND.get()) {
                    ((TileHollowedPumpkin) tile).addToInventory(player.getHeldItemMainhand());
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileHollowedPumpkin) {
                InventoryHelper.dropInventoryItems(world, pos, ((TileHollowedPumpkin) tile).getInventory().toIInventory());
                world.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader worldIn) {
        return new TileHollowedPumpkin();
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getRenderShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos) {
        return SHAPE;
    }
}
