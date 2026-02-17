package stepdefinitions;

import base.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pages.BookShelves;
import pages.GiftCards;
import pages.HomePage;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.ExcelWriter;

import java.io.IOException;

public class PepperfrySteps {

    WebDriver driver;
    HomePage hp;
    BookShelves bs;
    GiftCards gc;
 //public Logger logger=LogManager.getLogger(this.getClass());;
//    
//    logger =LogManager.getLogger(this.getClass());
    private static final Logger logger = LogManager.getLogger(PepperfrySteps.class);
    
    public PepperfrySteps() {

        this.driver = BaseClass.getDriver();

        hp = new HomePage(driver);
        bs = new BookShelves(driver);
        gc = new GiftCards(driver);
        logger.info("PepperfrySteps initialized with WebDriver");
    }

    @Given("I open the Pepperfry website")
    public void i_open_the_pepperfry_website() {
        driver.get(ConfigReader.getProperty("url"));
        logger.info("The pepper webpage was opened");

        driver.manage().window().maximize();
//        hp.closePopup();
        
    }


    @When("I hover on {string} and select {string}")
    public void i_hover_on_and_select(String menu, String subMenu) {
        if (menu.equalsIgnoreCase("Furniture") && subMenu.equalsIgnoreCase("Bookshelves")) {
            hp.hoverFurniture();
            hp.openBookShelves();
        }
    }

    @When("I filter bookshelves under {string}")
    public void i_filter_bookshelves_under(String price) {
        bs.clickFilters();
        bs.priceBtn();
        bs.priceInput(price);
    }

    @When("I apply material filter {string}")
    public void i_apply_material_filter(String material) {
        bs.materialBtn();
        bs.checkWood();
    }

    @When("I enable the {string} filter")
    public void i_enable_the_filter(String filterName) {
        bs.clickApply();
//        bs.chkAssure();
    }

    @Then("I select the first 3 bookshelf products")
    public void i_select_the_first_3_bookshelf_products() {

        bs.displayDetails();
    }

    //second feature

    @When("I navigate to the {string} section")
    public void i_navigate_to_the_section(String section) {
        bs.hoverLuxury();
    }

    @Then("I select a submenu under Luxury")
    public void i_select_a_submenu_under_luxury() {
    	
        bs.displaySubMenu();
    }

    @When("I open the Gift Cards page")
    public void i_open_the_gift_cards_page() {
        bs.giftCard();
    }

    @When("I select the {string} gift card")
    public void i_select_the_gift_card(String cardName) {
        gc.selectHBD();
    }

    @When("I enter invalid gift card details")
    public void i_enter_invalid_gift_card_details() throws IOException, InterruptedException {
        
    	
        String path="C:\\Users\\2461930\\Downloads\\Displaybookshelves 1\\Displaybookshelves\\testdata\\BookDisplayShell.xlsx";
        Object[][] data = ExcelReader.getData(path, "Sheet1");


        for (int i = 0; i < data.length; i++) {

            System.out.println(">>> Testing Excel Row: " + i);

            String rName  = (String) data[i][0];
            String rMob   = (String) data[i][1];
            String rEmail = (String) data[i][2];
            String msg    = (String) data[i][3];
            String sName  = (String) data[i][4];
            String sMob   = (String) data[i][5];
            String sEmail = (String) data[i][6];


            gc.fillReceiverDetails(rName, rMob, rEmail, msg);
            gc.fillSenderDetails(sName, sMob, sEmail);


            gc.screenshot();
            gc.displayError();


            if (i < data.length - 1) {
                driver.navigate().refresh();
            }
        }
    }

    
    @Then("I should capture and print the error message shown")
    public void i_should_capture_and_print_the_error_message_shown() {    	
        gc.screenshot();
        gc.displayError();
    }
}