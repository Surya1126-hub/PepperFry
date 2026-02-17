package pages;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ExcelUtil;
import utilities.ExcelWriter;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Logger;



public class BookShelves {
    private WebDriver driver;
    //private static final Logger logger =LogManager.getLogger(this.getClass());
    
    private static final Logger logger = LogManager.getLogger(BookShelves.class);
    
    private By filters = By.xpath("//*[text()=' More Filters ']");
    private By price = By.xpath("//*[@class='panel-title']//accordion-heading[text()=' Price ']");
    private By maxPrice = By.xpath("//*[@formcontrolname='inputMax']");
    private By material = By.xpath("//*[@class='panel-title']//accordion-heading[text()=' Material ']");
    private By engWood = By.xpath("//*[@for='Engineered Wood']");
    private By apply = By.xpath("//*[text()='APPLY']");
    private By assure = By.xpath("//*[@for='checkedAssured']");
    private By title = By.xpath("//*[@class='product-card']//*[starts-with(@class,'product-details')]//h2");
    private By prices = By.xpath("//*[@class='product-card']//*[starts-with(@class,'product-price')]//span[1]");
    private By luxury = By.xpath("//*[@id='Luxury' and @data-test='menuData']");
    private By subMenu = By.xpath("//*[@id='meta-Furniture']//descendant::div[@class='hd-menu-main-category hd-hover-col ng-star-inserted']");
    private By gift = By.xpath("//*[text()='GIFT CARDS']");

    public BookShelves(WebDriver driver){
        this.driver=driver;
        logger.info("BookShelves page object initialized");
    }
    
    // actions 
    
    public void giftCard(){
    	logger.info("Navigating to Gift Cards section");
        WebElement giftEle = driver.findElement(gift);
        Actions action = new Actions(driver);
        action.moveToElement(giftEle).doubleClick().perform();
        logger.debug("Gift Cards element double-clicked");
    }

    
    public void displaySubMenu() {

        List<WebElement> category = driver.findElements(subMenu);

        String path = "C:\\Users\\2461930\\Downloads\\Displaybookshelves 1\\Displaybookshelves\\TableData.xlsx";
        String sheetName = "Luxury SubMenu";

        for (int i = 0; i < category.size(); i++) {

            WebElement cname = category.get(i);

            //  Get ONLY the heading
            String heading = cname.findElement(By.xpath(".//h3|.//h2|.//h1|.//a[1]|.//span[1]")).getText().trim();

            //  Get items under the heading
            List<WebElement> items = cname.findElements(By.xpath(".//ul/li"));


            String[] arr = new String[items.size() + 1];
            arr[0] = heading;


            for (int j = 0; j < items.size(); j++) {
                arr[j + 1] = items.get(j).getText().trim();
            }


//            for (String s : arr) System.out.println(s);

            String[] arr1 = new String[arr.length-1];

            for (int j=1;j< arr.length;j++){
                arr1[j-1]=arr[j];
            }


            ExcelUtil.tableData(path, sheetName, arr1, i + 1);
        }
    }

    public void hoverLuxury(){
        WebElement luxuryEle = driver.findElement(luxury);
        Actions action = new Actions(driver);
        action.moveToElement(luxuryEle).perform();
    }
    
    
    public void displayDetails(){
    	
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        List<WebElement> titleList = driver.findElements(title);
        List<WebElement> priceList = driver.findElements(prices);

        for(int i=0;i<3;i++){
        		//textContent
            String product=titleList.get(i).getText();
        	String price=priceList.get(i).getText();
        		
            System.out.println(titleList.get(i).getText());
            System.out.println(priceList.get(i).getText());
            
            System.out.println("********************");
            
            try {
            		ExcelWriter.appendRow("output1.xlsx","Bookshelves",new Object[] {product,price});
            }
            
            catch(IOException e) {
            		e.printStackTrace();
            }
        }
    }
//    public void chkAssure(){
//    	
//    	logger.info("Applying 'Pepperfry Assured' filter");
//        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
//        WebElement assureBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(assure));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", assureBtn);
//        logger.debug("'Pepperfry Assured' filter applied");
//        
//    }
    
    public void clickApply(){
    	logger.info("Clicking Apply button");
        WebElement applyBtn = driver.findElement(apply);
        applyBtn.click();
        logger.debug("Apply button clicked");
    }
    public void checkWood(){
    	logger.info("Selecting Engineered Wood filter if not already selected");
        WebElement woodBtn = driver.findElement(engWood);
        if(!woodBtn.isSelected()){
            woodBtn.click();
            logger.debug("Engineered Wood filter selected");
        }else {
        	logger.debug("Engineered Wood filter was already selected");
        }
    }
    
    public void priceInput(String price){
    	logger.info("Entering max price filter value: 15000");
        WebElement maxInput = driver.findElement(maxPrice);
        maxInput.clear();
        maxInput.sendKeys(price);
        logger.debug("Max price input set to 15000");
    }
    public void clickFilters() {
    	logger.info("Clicking More Filters option");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(filters));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        logger.debug("More Filters clicked");
    }

    public void priceBtn(){
    	logger.info("Expanding Price filter section");
        driver.findElement(price).click();
        logger.debug("Price filter section expanded");
    }

    public void materialBtn(){
    	logger.info("Expanding Material filter section");
        driver.findElement(material).click();
        logger.debug("Material filter section expanded");
    }
}