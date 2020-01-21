package com.transaction.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/com/transaction/cucumber/features", snippets=SnippetType.CAMELCASE, glue= {"com.transaction.cucumber.steps"})
public class CucumberTest {

}
