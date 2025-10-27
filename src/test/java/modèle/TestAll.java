package modèle;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	testFactureService.class,
	testStockService.class,
	testPanierService.class,
	TomatesApparenteesTest.class,
	TomatesTest.class
	
})
public class TestAll {
    // This class remains empty. It is used only as a holder for the above annotations.
}

