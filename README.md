Simple YARG library demo
========================

See main YARG repository: https://github.com/cuba-platform/yarg

Here we easily enable YARG using JCenter repository:
```
repositories {
    jcenter()
}

apply plugin: 'java'

dependencies {
    compile 'com.haulmont.yarg:yarg:2.0.7'
}
```

After that we can prepare report XML description, XLSX report template and run report using YARG:

```java
Report report = new DefaultXmlReader()
        .parseXml(IOUtils.toString(IncomesDemo.class.getResource("/incomes-report.xml")));

Reporting reporting = new Reporting();
reporting.setFormatterFactory(new DefaultFormatterFactory());
reporting.setLoaderFactory(new DefaultLoaderFactory()
        .setGroovyDataLoader(new GroovyDataLoader(new DefaultScriptingImpl())));

ReportOutputDocument reportOutputDocument = reporting.runReport(
                new RunParams(report), new FileOutputStream(resultFile));
```

That's it!

Start demo:

    > gradlew run

See template and result in ./work directory