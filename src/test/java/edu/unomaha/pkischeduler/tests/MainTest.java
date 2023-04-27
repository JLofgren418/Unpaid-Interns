package edu.unomaha.pkischeduler.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * NOTE:
 *  Test does not start the Tomcat server.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest {
    String rootUrl= "http://localhost:8080";

    WebDriver driver;
    static ChromeOptions chromeOptions = new ChromeOptions();

    static String testClassName = "Test"+  LocalTime.now() ;
    String courseCode="TEST 9920";



    @BeforeAll
    public static void setupDriver(){
        chromeOptions.addArguments("ignore-certificate-errors" ) ;   // chrome 111 or newer
        chromeOptions.addArguments("--remote-allow-origins=*" );     // chrome security restrictions
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver(chromeOptions);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    /**
     * Testing CRUD, adding a class
     */
    @Test
    void _00_testAddingClass(){
        driver.get( rootUrl +"/edit" );
        WebDriverWait wait = new WebDriverWait(driver,  java.time.Duration.ofSeconds(10 ) );

        var appLayoutXpath=  By.xpath("//vaadin-app-layout" );
        wait.until(ExpectedConditions.presenceOfElementLocated( appLayoutXpath ));
        WebElement shadowHost = driver.findElement( appLayoutXpath );

        // shdow element -> https://stackoverflow.com/questions/37384458/how-to-handle-elements-inside-shadow-dom-from-selenium
        var click = ( (JavascriptExecutor) driver).executeScript( "document.querySelector('vaadin-crud').shadowRoot.querySelector('vaadin-button').click()");

        var formCourseNameXpath=   By.xpath("//vaadin-text-field//label[contains(text(), 'Course Title')]/following-sibling::input[1]" );
        var formCourseName = driver.findElement( formCourseNameXpath );
        formCourseName.click();
        formCourseName.sendKeys( testClassName );


        var section = driver.findElement(  By.xpath("//vaadin-text-field//label[contains(text(), 'Section Type')]/following-sibling::input[1]" ) );
        section.click();
        section.sendKeys( "Lecture" );

        var meetTime = driver.findElement(  By.xpath("//vaadin-text-field//label[contains(text(), 'Meeting Time')]/following-sibling::input[1]" ) );
        meetTime.click();
        meetTime.sendKeys( "5:30pm-8:10pm" );

        var maxEnrollment = driver.findElement(  By.xpath("//vaadin-number-field//label[contains(text(), 'Max Enrollment')]/following-sibling::input[1]" ) );
        maxEnrollment.click();
        maxEnrollment.sendKeys( "33" );

        var room = driver.findElement(  By.xpath("//vaadin-combo-box//label[contains(text(), 'Room')]/following-sibling::input[1]" ) );
        room.click();
        room.sendKeys("Peter Kiewit Institute 108"+ Keys.ENTER);

        var courseCode = driver.findElement(  By.xpath("//vaadin-text-field//label[contains(text(), 'Course Code')]/following-sibling::input[1]" ) );
        courseCode.click();
        courseCode.sendKeys( this.courseCode );

        var meetingDays = driver.findElement(  By.xpath("//vaadin-text-field//label[contains(text(), 'Meeting Days')]/following-sibling::input[1]" ) );
        meetingDays.click();
        meetingDays.sendKeys( "MWF" );

        var crossListings = driver.findElement(  By.xpath("//vaadin-text-field//label[contains(text(), 'Cross Listings')]/following-sibling::input[1]" ) );
        crossListings.click();
        crossListings.sendKeys( "None" );

        var sectionNumber = driver.findElement(  By.xpath("//vaadin-text-field//label[contains(text(), 'Section Number')]/following-sibling::input[1]" ) );
        sectionNumber.click();
        sectionNumber.sendKeys("111");

        var instructor = driver.findElement(  By.xpath("//vaadin-combo-box//label[contains(text(), 'Instructor')]/following-sibling::input[1]" ) );
        instructor.click();
        instructor.sendKeys(Keys.ARROW_DOWN , Keys.ENTER);

        By addContactBtnTag = By.xpath("//vaadin-button[contains(text(), 'Save')]" );
        wait.until(ExpectedConditions.presenceOfElementLocated( addContactBtnTag ));
        WebElement btn = driver.findElement( addContactBtnTag  ) ;
        btn.click();

    }


    /**
     * Testing CRUD, validate class added
     */
    @Test
    void _01_verifyClassAdded(){
        driver.get( rootUrl +"/edit" );
        WebDriverWait wait = new WebDriverWait(driver,  java.time.Duration.ofSeconds(15) );

        var  inputPath =  By.xpath("//*[@id=\"input-vaadin-text-field-33\"]" );
        wait.until(ExpectedConditions.presenceOfElementLocated( inputPath ));

        var textSearchField = driver.findElement( inputPath );
        textSearchField.click();
        textSearchField.sendKeys( courseCode + Keys.ENTER);

        wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath("//vaadin-grid-cell-content[contains(text(), '"+ courseCode +"')]" ) ) );
        var appData = driver.findElement( By.xpath("//*/vaadin-app-layout/vaadin-vertical-layout/vaadin-crud/vaadin-grid"));
        var containsAddedName = appData.getText().contains(testClassName);
        assertTrue( containsAddedName );
    }


    @Test
    void _02_deleteAllClasses(){
        driver.get( rootUrl +"/edit" );
        WebDriverWait wait = new WebDriverWait(driver,  java.time.Duration.ofSeconds(10 ) );

        var appLayoutXpath=  By.xpath("//vaadin-app-layout" );
        wait.until(ExpectedConditions.presenceOfElementLocated( appLayoutXpath ));
        WebElement shadowHost = driver.findElement( appLayoutXpath );

        // shdow element -> https://stackoverflow.com/questions/37384458/how-to-handle-elements-inside-shadow-dom-from-selenium
        var click = ( (JavascriptExecutor) driver).executeScript( " document.querySelector('* > vaadin-app-layout > vaadin-vertical-layout > vaadin-horizontal-layout > vaadin-horizontal-layout:nth-child(2) > vaadin-button').click()" );

        var overlayDialog=   By.xpath("//*[@id=\"overlay\"]" );
        wait.until(ExpectedConditions.presenceOfElementLocated( overlayDialog ));

        var deleteBtn = driver.findElement( By.xpath("//*[@id=\"overlay\"]/vaadin-button[3]" ) );
        deleteBtn.click();
    }


}
