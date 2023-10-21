package apollointhehouse.betterspawnlocation.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.type.WorldTypeOverworld;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Arrays;

@Mixin(value = WorldTypeOverworld.class, remap = false)
public class WorldTypeOverworldMixin {

	@Unique
	int[] invalidSpawnBlocks;

	@Inject(method = "<init>", at = @At("TAIL"))
	void init(String languageKey, Weather defaultWeather, WindManager windManager, SeasonConfig defaultSeasonConfig, CallbackInfo ci) {
		invalidSpawnBlocks = new int[] {
			0,
			Block.fluidLavaFlowing.id,
			Block.fluidLavaStill.id,
			Block.fluidWaterFlowing.id,
			Block.fluidWaterStill.id,
			Block.fire.id,
			Block.spikes.id,
			Block.algae.id,
			Block.cactus.id
		};
	}

	@Inject(method = "isValidSpawn", at = @At("RETURN"), cancellable = true)
	void isValidSpawn(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(
			Arrays.stream(invalidSpawnBlocks)
				.noneMatch(id -> id == world.getBlockId(x, y, z))
		);
	}
}
