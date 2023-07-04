package com.gametracker.backend;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.jordi.tvshowlibrary.steps"},
        monochrome = true)
public class CucumberIntegrationTest {
}
