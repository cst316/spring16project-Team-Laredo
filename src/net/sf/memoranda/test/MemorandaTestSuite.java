package net.sf.memoranda.test;


import net.sf.memoranda.StatisticsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventImplTest.class, DefectListCreationTest.class, DefectListPullingFromFileTest.class, StatisticsTest.class
})
public class MemorandaTestSuite {
}
