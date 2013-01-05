package laboratory.plugin.functor.mean;

import laboratory.common.loader.FunctorLoader;
import laboratory.common.Functor;

import java.util.jar.JarFile;

public class MeanLoader implements FunctorLoader {
    @Override
    public Functor loadFunctor() {
        return new Mean();
    }

    @Override
    public String getName() {
        return "Mean";
    }

    public MeanLoader(JarFile jar) {}
}
