package com.tradingsimulator.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.tradingsimulator.steps","com.tradingsimulator.hooks"},
        plugin = {
                "pretty",
                "summary",
                "json:reports/cucumber.json",
                "html:reports/cucumber.html"
        },
        tags = "@ui",
        publish = false
)
public class TestRunner extends AbstractTestNGCucumberTests {}
