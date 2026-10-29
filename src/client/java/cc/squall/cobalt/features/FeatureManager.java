package cc.squall.cobalt.features;

import cc.squall.cobalt.Client;
import cc.squall.cobalt.features.impl.misc.ClientCommands;
import cc.squall.cobalt.features.impl.movement.Sprint;
import cc.squall.cobalt.features.impl.render.Fullbright;
import cc.squall.cobalt.features.impl.render.HUD;
import cc.squall.cobalt.utils.datatypes.collections.BiMap;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {

    @Getter
    private final BiMap<String, Feature> featuresMap = new BiMap<>();

    public void enable(String featureName) {
        Feature current = getFeature(featureName);
        current.enable();
    }
    public void disable(String featureName) {
        Feature current = getFeature(featureName);
        current.disable();
    }

    public boolean isEnabled(String featureName) {
        return getFeature(featureName).isEnabled();
    }
    public boolean isDisabled(String featureName) {
        return getFeature(featureName).isDisabled();
    }

    public List<Feature> getFeatures() {
        return new ArrayList<>(featuresMap.getValues());
    }
    public Feature getFeature(String featureName) {
        return featuresMap.get(featureName);
    }
    public void create(Feature feature) {
        getFeaturesMap().put(feature.name, feature);
    }
    public void createFeatures() {
        create(new ClientCommands());
        if (Client.getInstance().devMode) {
            enable("ClientCommands");
        }

        create(new Sprint());
        create(new Fullbright());
        create(new HUD());

    }
}
