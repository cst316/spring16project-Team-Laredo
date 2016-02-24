package net.sf.memoranda.test;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventImplTest.class,
        DefectListCreationTest.class,
        DefectListPullingFromFileTest.class,
        StatisticsTest.class,
        StopwatchTest.class,
        TaskImplTest.class
})
public class MemorandaTestSuite {
}
