package flaxbeard.thaumicexploration.misc;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;

public class FauxAspect extends Aspect{

	public FauxAspect(String tag, int color, Aspect[] components,
			ResourceLocation image, int blend) {
		super(tag, color, components, image, blend);
		aspects.remove(tag);
	}

}
