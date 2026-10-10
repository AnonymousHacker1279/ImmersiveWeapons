package tech.anonymoushacker1279.immersiveweapons.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.resources.model.cuboid.ItemTransform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/// Computes where each firearm sits in first person when fully aimed.
///
/// Every model has its own `firstperson_*hand` display transform, which is tuned for the hip position. To aim, that
/// transform is cancelled and replaced by a universal one: the model's sight point (the horizontal center and the top
/// of its bounds) is placed at a fixed point in front of the camera, with the display scale kept so the gun stays the
/// same size. This works the same for both hands. The sight point's position can be tuned per gun in
/// `gun_aim_offsets.json`, and applied with a resource reload (F3+T).
public class GunAimOffsets extends SimplePreparableReloadListener<Map<String, GunAimOffsets.Source>> {

	public static final Identifier ID = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "gun_aim_offsets");
	private static final Identifier FILE = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "gun_aim_offsets.json");
	private static final Vector3fc FALLBACK_VIEW = new Vector3f(0.04f, -0.02f, -0.5f);

	/// GeckoLib places a model's origin here relative to the item origin (see `GeoItemRenderer`).
	private static final float[] GECKOLIB_ORIGIN = {0.5f, 0.51f, 0.5f};

	private static volatile Map<String, Poses> poses = Map.of();

	/// A pose to apply in place of the hip position.
	public record AimPose(Vector3f translation, Quaternionf rotation, Vector3f scale) {
	}

	public record Poses(AimPose right, AimPose left) {
	}

	/// Raw data read from resources, before the poses are computed.
	public record Source(Vector3f min, Vector3f max, ItemTransform right, ItemTransform left) {
	}

	/// Get the aimed pose for an item in the given arm, or null if unknown.
	public static AimPose getPose(Item item, HumanoidArm arm) {
		Poses found = poses.get(BuiltInRegistries.ITEM.getKey(item).getPath());
		if (found == null) {
			return null;
		}

		return arm == HumanoidArm.RIGHT ? found.right() : found.left();
	}

	@Override
	protected Map<String, Source> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
		Map<String, Source> sources = new HashMap<>();

		for (Item item : BuiltInRegistries.ITEM) {
			if (!(item instanceof AbstractGunItem)) {
				continue;
			}

			Identifier itemId = BuiltInRegistries.ITEM.getKey(item);
			Identifier modelFile = Identifier.fromNamespaceAndPath(itemId.getNamespace(), "models/item/" + itemId.getPath() + ".json");

			resourceManager.getResource(modelFile).ifPresent(resource -> {
				try (BufferedReader reader = resource.openAsReader()) {
					sources.put(itemId.getPath(), readModel(JsonParser.parseReader(reader).getAsJsonObject()));
				} catch (Exception e) {
					ImmersiveWeapons.LOGGER.error("Failed to read firearm model {} for aiming", modelFile, e);
				}
			});
		}

		return sources;
	}

	private static Source readModel(JsonObject model) {
		Vector3f min = new Vector3f(Float.MAX_VALUE);
		Vector3f max = new Vector3f(-Float.MAX_VALUE);

		for (JsonElement element : model.getAsJsonArray("elements")) {
			JsonObject cube = element.getAsJsonObject();
			Vector3f from = readVector(cube.getAsJsonArray("from"), 0);
			Vector3f to = readVector(cube.getAsJsonArray("to"), 0);

			min.min(from).min(to);
			max.max(from).max(to);
		}

		JsonObject display = model.has("display") ? model.getAsJsonObject("display") : new JsonObject();
		return new Source(min, max,
				readTransform(display, "firstperson_righthand"),
				readTransform(display, "firstperson_lefthand"));
	}

	private static Vector3f readVector(JsonArray array, float fallback) {
		if (array == null) {
			return new Vector3f(fallback);
		}

		return new Vector3f(array.get(0).getAsFloat(), array.get(1).getAsFloat(), array.get(2).getAsFloat());
	}

	/// Read a display transform the same way vanilla does: translation is in 1/16ths of a block and clamped.
	private static ItemTransform readTransform(JsonObject display, String key) {
		if (!display.has(key)) {
			return ItemTransform.NO_TRANSFORM;
		}

		JsonObject object = display.getAsJsonObject(key);
		Vector3f rotation = readVector(object.has("rotation") ? object.getAsJsonArray("rotation") : null, 0.0f);
		Vector3f translation = readVector(object.has("translation") ? object.getAsJsonArray("translation") : null, 0.0f);
		Vector3f scale = readVector(object.has("scale") ? object.getAsJsonArray("scale") : null, 1.0f);

		translation.mul(0.0625f);
		translation.set(Mth.clamp(translation.x, -5.0f, 5.0f), Mth.clamp(translation.y, -5.0f, 5.0f), Mth.clamp(translation.z, -5.0f, 5.0f));
		scale.set(Mth.clamp(scale.x, -4.0f, 4.0f), Mth.clamp(scale.y, -4.0f, 4.0f), Mth.clamp(scale.z, -4.0f, 4.0f));

		return new ItemTransform(rotation, translation, scale);
	}

	@Override
	protected void apply(Map<String, Source> sources, ResourceManager resourceManager, ProfilerFiller profiler) {
		Map<String, Vector3f> views = new HashMap<>();
		Vector3f defaultView = new Vector3f(FALLBACK_VIEW);

		resourceManager.getResource(FILE).ifPresent(resource -> {
			try (BufferedReader reader = resource.openAsReader()) {
				JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

				for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
					if (entry.getValue() instanceof JsonObject object) {
						Vector3f view = new Vector3f(object.get("x").getAsFloat(), object.get("y").getAsFloat(), object.get("z").getAsFloat());

						if (entry.getKey().equals("default")) {
							defaultView.set(view);
						} else {
							views.put(entry.getKey(), view);
						}
					}
				}
			} catch (Exception e) {
				ImmersiveWeapons.LOGGER.error("Failed to load gun aim offsets", e);
			}
		});

		Map<String, Poses> computed = new HashMap<>();
		sources.forEach((path, source) -> {
			Vector3f view = views.getOrDefault(path, defaultView);

			// The sight point: horizontal center, top, and depth center of the model, relative to the model center
			Vector3f sight = new Vector3f(
					(source.min().x + source.max().x) * 0.5f - 8.0f,
					source.max().y - 8.0f,
					(source.min().z + source.max().z) * 0.5f - 8.0f
			).mul(0.0625f);

			computed.put(path, new Poses(
					computePose(source.right(), false, view, sight),
					computePose(source.left(), true, view, sight)
			));
		});

		poses = Map.copyOf(computed);
	}

	/// Compute the pose that places the sight point at `view`, given the model's display transform.
	private static AimPose computePose(ItemTransform display, boolean leftHand, Vector3f view, Vector3f sight) {
		// What the renderer applies after our transform: the display transform, then GeckoLib's origin shift
		PoseStack renderer = new PoseStack();
		display.apply(leftHand, renderer.last());
		renderer.translate(GECKOLIB_ORIGIN[0], GECKOLIB_ORIGIN[1], GECKOLIB_ORIGIN[2]);

		Matrix4f inverse = new Matrix4f(renderer.last().pose()).invert();

		// Desired result: the sight point at the view position, keeping the display scale
		Vector3fc scale = display.scale();
		Matrix4f desired = new Matrix4f()
				.translate(view)
				.scale(scale.x(), scale.y(), scale.z())
				.translate(-sight.x, -sight.y, -sight.z);

		Matrix4f ours = desired.mul(inverse);

		return new AimPose(
				ours.getTranslation(new Vector3f()),
				ours.getNormalizedRotation(new Quaternionf()),
				ours.getScale(new Vector3f())
		);
	}
}
