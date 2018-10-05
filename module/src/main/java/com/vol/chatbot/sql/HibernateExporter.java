package com.vol.chatbot.sql;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class HibernateExporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateExporter.class);
    private static final String CANT_GET_CLASS_LOADER="Can't get class loader.";

    private String dialect;
    private String entityPackage;

    public HibernateExporter(String dialect, String entityPackage) {
        this.dialect = dialect;
        this.entityPackage = entityPackage;

    }

    private List<Class> getClasses(String packageName) throws ClassNotFoundException {
        File directory = null;
        try {
            ClassLoader cld = getClassLoader();
            URL resource = getResource(packageName, cld);
            directory = new File(resource.getFile());
        } catch (NullPointerException ex) {
            LOGGER.warn("error", ex);
            throw new ClassNotFoundException(packageName + " (" + directory + ") does not appear to be a valid package", ex);
        }
        return collectClasses(packageName, directory);
    }

    private ClassLoader getClassLoader() throws ClassNotFoundException {
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        if (cld == null) {
            LOGGER.warn(CANT_GET_CLASS_LOADER);
            throw new ClassNotFoundException(CANT_GET_CLASS_LOADER);
        }
        return cld;
    }

    private URL getResource(String packageName, ClassLoader cld) throws ClassNotFoundException {
        String path = packageName.replace('.', '/');
        URL resource = cld.getResource(path);
        if (resource == null) {
            LOGGER.warn(CANT_GET_CLASS_LOADER);
            throw new ClassNotFoundException("No resource for " + path);
        }
        return resource;
    }

    @SuppressWarnings("rawtypes")
    private List<Class> collectClasses(String packageName, File directory) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (directory.exists()) {
            String[] files = directory.list();
            for (String file : files) {
                if (file.endsWith(".class")) {
                    // removes the .class extension
                    classes.add(Class.forName(packageName + '.' + file.substring(0, file.length() - 6)));
                }
            }
        } else {
            LOGGER.warn("{} is not a valid package", packageName);
            throw new ClassNotFoundException(packageName + " is not a valid package");
        }
        return classes;
    }


    public void generate(String directory) throws Exception {

        MetadataSources metadata = new MetadataSources(
            new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", dialect)
                .build());

        LOGGER.info("packageName: {}", entityPackage);
        for (Class clazz : getClasses(entityPackage)) {
            LOGGER.info("Class: {}", clazz);
            metadata.addAnnotatedClass(clazz);
        }

        MetadataImplementor metadataImplementor = (MetadataImplementor) metadata.buildMetadata();
        SchemaExport export = new SchemaExport();

        export.setDelimiter(";");
        String filename = directory + "ddl_" + dialect.toLowerCase() + ".sql";
        LOGGER.info("Output file name {}", filename);
        export.setOutputFile(filename);
        export.setFormat(true);

        //can change the output here
        EnumSet<TargetType> enumSet = EnumSet.of(TargetType.STDOUT);
        export.execute(enumSet, SchemaExport.Action.CREATE, metadataImplementor);

    }
}
