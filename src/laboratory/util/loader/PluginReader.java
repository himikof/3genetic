package laboratory.util.loader;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import laboratory.util.functional.Functor1;

public class PluginReader {

    public static class LoaderFromJar<Loader> implements Functor1<File, Loader> {
        @Override
        @SuppressWarnings("unchecked")
        public Loader apply(File file) {
            try {
                JarFile jr = new JarFile(file);
                final Attributes attributes = jr.getManifest().getMainAttributes();
                String mainClass = attributes.getValue(Attributes.Name.MAIN_CLASS);
                ClassLoader cl = new URLClassLoader(new URL[]{file.toURI().toURL()});
                Class<Loader> c = (Class<Loader>) cl.loadClass(mainClass);
                return c.getConstructor(JarFile.class).newInstance(jr);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class LoaderFromDir<Loader> implements Functor1<File, Loader> {
        @Override
        @SuppressWarnings("unchecked")
        public Loader apply(File dir) {
            try {
                final File file = new File(dir, dir.getName() + ".jar");
                JarFile jr = new JarFile(file);
                final Attributes attributes = jr.getManifest().getMainAttributes();
                String mainClass = attributes.getValue(Attributes.Name.MAIN_CLASS);
                ClassLoader cl = new URLClassLoader(new URL[]{file.toURI().toURL()});
                Class<Loader> c = (Class<Loader>) cl.loadClass(mainClass);
                return c.getConstructor(JarFile.class, File.class).newInstance(jr, dir);
            } catch (Exception e) {
                throw new RuntimeException(e.toString() + "\n" + dir, e);
            }
        }
    }

    public static class ObjectFromJar<T> implements Functor1<File, T> {
        @Override
        @SuppressWarnings("unchecked")
        public T apply(File file) {
            try {
                JarFile jr = new JarFile(file);
                final Attributes attributes = jr.getManifest().getMainAttributes();
                String mainClass = attributes.getValue(Attributes.Name.MAIN_CLASS);
                ClassLoader cl = new URLClassLoader(new URL[]{file.toURI().toURL()});
                Class<T> c = (Class<T>) cl.loadClass(mainClass);
                return c.getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<File> getJars(File dir) {
        return Arrays.asList(dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        }));
    }

    public static List<File> getDirs(File dir){
        return Arrays.asList(dir.listFiles(new FileFilter(){
            @Override
            public boolean accept(File dir){
                return (dir.isDirectory() && (dir.getName().charAt(0) != '.'));
            }
        }));
    }

}