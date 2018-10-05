package com.vol.chatbot.sql;

import org.junit.Test;

import java.io.File;


public class DDLGenerator {

    @Test
    public void generate() throws Exception {
        HibernateExporter exporter = new HibernateExporter("org.hibernate.dialect.PostgreSQL95Dialect",
            "com.vol.chatbot.model");
        File f = new File(".");
        String outputDir = f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 2) + "\\target\\test-classes\\";
        exporter.generate(outputDir);
    }

}