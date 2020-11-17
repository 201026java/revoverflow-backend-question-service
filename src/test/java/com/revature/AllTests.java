package com.revature;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ QuestionControllerTests.class, QuestionServiceTest.class })
public class AllTests {

}
