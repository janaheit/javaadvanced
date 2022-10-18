package be.abis.exercise.test;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({PersonTest.class})
@IncludeTags("AgeTests")
public class AgeSuite {
}
