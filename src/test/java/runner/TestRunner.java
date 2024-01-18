package runner;


import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



/*
    Features klasörünü belirtiyoruz
     O featureda ki koşturmak istediğimiz senaryo taglerini belirtiyoruz
      step def. klasörünü belirtiyoruz
 */
@CucumberOptions(
        features = "src/test/features",
        tags = "@Scenario1",
        glue = {"stepdefs"})

/*

 */
public class TestRunner{
    private TestNGCucumberRunner testNGCucumberRunner;

    // Her test senaryosu başlamadan önce bu metot çalışacak
    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    // Cucumber senaryolarını çalıştıran TestNG test metodu
    @Test(groups = "cucumber", description = "Run Cucumber Features.", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    // Senaryo verilerini sağlayan DataProvider metodu
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    // Her test senaryosu tamamlandıktan sonra bu metot çalışacak
    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();
    }


}