package nl.pse.site.seproject.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/nl/pse/site/seproject/cucumber/",strict = true)
public class CucumberTest {


}
