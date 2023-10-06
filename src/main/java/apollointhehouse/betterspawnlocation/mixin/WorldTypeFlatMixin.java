package apollointhehouse.betterspawnlocation.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.type.WorldTypeFlat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WorldTypeFlat.class, remap = false)
public class WorldTypeFlatMixin {
	@Inject(method = "isValidSpawn", at = @At("RETURN"), remap = false, cancellable = true)
	void isValidSpawn(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(
			world.getBlockId(x, y, z) == Block.grass.id
		);
	}
}
