package com.token.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * runs all the test with cucumber class
 */
@RunWith(Cucumber.class)

/**
 * set pathing for cucumber testing
 */
@CucumberOptions(features={"src/test/java/com/token/cucumber/features"},
                 snippets=SnippetType.CAMELCASE,
                 glue= {"com.token.cucumber.steps"})
public class CucumberTest {

}
