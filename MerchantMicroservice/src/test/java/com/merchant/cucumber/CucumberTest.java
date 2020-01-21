package com.merchant.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
/**
 * @author Ismael
 * runs all the test with cucumber class
 */
@RunWith(Cucumber.class)

/**
 * set pathing for cucumber testing
 */

@CucumberOptions(features={"src/test/java/com/merchant/cucumber/features"},
        snippets=SnippetType.CAMELCASE,
        glue= {"com.merchant.cucumber.steps"})
public class CucumberTest {

}
