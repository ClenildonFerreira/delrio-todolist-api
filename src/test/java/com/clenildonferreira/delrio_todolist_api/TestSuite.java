package com.clenildonferreira.delrio_todolist_api;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.clenildonferreira.delrio_todolist_api")
@IncludeClassNamePatterns(".*Test")
public class TestSuite {
}