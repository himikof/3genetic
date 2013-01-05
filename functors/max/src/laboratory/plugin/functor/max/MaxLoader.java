package laboratory.plugin.functor.max;

import laboratory.common.loader.FunctorLoader;
import laboratory.common.Functor;

import java.util.jar.JarFile;

public class MaxLoader implements FunctorLoader {
    @Override
    public Functor loadFunctor() {
        return new MaxGAFunctor();
    }

    @Override
    public String getName() {
        return "Max";
    }

    public MaxLoader(JarFile jar) {}
}
