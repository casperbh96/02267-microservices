package com.customer.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * @author Danial
 * runs all the test with cucumber class
 */
@RunWith(Cucumber.class)

/**
 * set pathing for cucumber testing
 */
@CucumberOptions(features={"src/test/java/com/customer/cucumber/features"},
        snippets=SnippetType.CAMELCASE,
        glue= {"com.customer.cucumber.steps"})
public class CucumberTest {

}
