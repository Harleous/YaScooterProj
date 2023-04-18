package mainPageFaqOpenText.Firefox;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class MainPageFaqFirefox {
    WebDriver driver;

    private final int index;
    private final String panelText;
    private final boolean result;


    public MainPageFaqFirefox(int index, String panelText, boolean result) {
        this.index = index;
        this.panelText = panelText;
        this.result = result;
    }
    @Parameterized.Parameters
    public static Object[][] openFaqAnswer() {
        return new Object[][] {

                {0,"Сутки — 400 рублей. Оплата курьеру — наличными или картой.", true},
                {1,"Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.", true},
                {2,"Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", true},
                {3,"Только начиная с завтрашнего дня. Но скоро станем расторопнее.", true},
                {4,"Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.", true},
                {5,"Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.", true},
                {6,"Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.", true},
                {7,"Да, обязательно. Всем самокатов! И Москве, и Московской области.", true},
        };
    }
    @Before
    public void setUp(){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        //options.addArguments("--remote-allow-origins=*");
        driver = new FirefoxDriver(options);
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf( driver.findElement(By.xpath(".//div[@class = 'accordion']"))));



    }


    @Test
    public void mainPage_fAQ_checkText(){
        var headingId = "accordion__heading-" + index;
        var panelId = "accordion__panel-" + index;

        WebElement heading = driver.findElement(By.id(headingId));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",
                heading);
        /*new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable( driver.findElement(By.id(headingId))));*/
        driver.findElement(By.id(headingId)).click();

        WebElement panel = driver.findElement(By.id(panelId));
        assertEquals(result, panel.isDisplayed());
        assertEquals(panelText, panel.getText());





    }
       @After
        public void tearDown(){
            driver.quit();
        }
}


