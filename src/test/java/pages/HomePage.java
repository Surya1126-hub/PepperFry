package pages;

import org.openqa.selenium.By;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    private By furniture = By.xpath("//*[@id='Furniture' and @data-test='menuData']");
    private By popupCross = By.xpath("//a[@class='close-modal']");
    private By bookShelves = By.xpath("(//*[@id='meta-Furniture']//following::div[3]//a[text()='Book Shelves '])[1]");

    public HomePage(WebDriver driver){
        this.driver=driver;
        logger.info("HomePage object initialized");
    }
    public void openBookShelves(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(bookShelves)).click();
    }

    public void closePopup(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        WebElement clsBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(popupCross));
        clsBtn.click();
//        driver.manage().window().maximize();
    }
    public void hoverFurniture(){
        WebElement furnitureEle =driver.findElement(furniture);
        Actions action = new Actions(driver);
        action.moveToElement(furnitureEle).perform();
    }


}
