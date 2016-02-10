package net.sf.memoranda.test;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventImplTest.class, DefectListCreationTest.class, DefectListPullingFromFileTest.class
})
public class MemorandaTestSuite {}
