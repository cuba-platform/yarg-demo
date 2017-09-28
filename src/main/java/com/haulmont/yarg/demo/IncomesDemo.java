package com.haulmont.yarg.demo;

import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.loaders.factory.DefaultLoaderFactory;
import com.haulmont.yarg.loaders.impl.GroovyDataLoader;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.xml.impl.DefaultXmlReader;
import com.haulmont.yarg.util.groovy.DefaultScriptingImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class IncomesDemo {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws Exception {
        // prepare work dir
        new File("./work/").mkdirs();

        // copy template to work
        File templateFile = new File("./work/incomes-template.xlsx");
        if (templateFile.exists()) {
            templateFile.delete();
        }
        InputStream inputStream = IncomesDemo.class.getResourceAsStream("/incomes-template.xlsx");
        FileUtils.copyInputStreamToFile(inputStream, templateFile);

        // run report
        Report report = new DefaultXmlReader()
                .parseXml(IOUtils.toString(IncomesDemo.class.getResource("/incomes-report.xml")));

        Reporting reporting = new Reporting();
        reporting.setFormatterFactory(new DefaultFormatterFactory());
        reporting.setLoaderFactory(new DefaultLoaderFactory()
                .setGroovyDataLoader(new GroovyDataLoader(new DefaultScriptingImpl())));

        File resultFile = new File("./work/incomes-result.xlsx");
        if (resultFile.exists()) {
            resultFile.delete();
        }

        ReportOutputDocument reportOutputDocument = reporting.runReport(
                new RunParams(report), new FileOutputStream(resultFile));

        System.out.println("Report result: " + reportOutputDocument.getDocumentName());
    }
}