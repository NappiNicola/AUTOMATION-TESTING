package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.log.Log;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class EnvConfig {

    public enum Browser {
        CHROME,FIREFOX,EDGE,IE
    }

    private WebDriver driver;
    private Actions action = null;
    private WebDriverWait wait = null;
    private JavascriptExecutor js = null;

    /*****************************************************************************/
    /*                        METODO DI SERVIZIO                                 */
    /*****************************************************************************/
    private void init(){
        WebDriver.Options options = driver.manage();
        options.window().maximize();
        action = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        action = new Actions(driver);

        Log.info("Setup finished!");
    }

    /*****************************************************************************/
    /*                        METODO DI SERVIZIO                                 */
    /*****************************************************************************/
    private void setup(Browser browser) {

        switch (browser){
            case CHROME:
                driver = new ChromeDriver();
                Log.info("Chrome driver loaded");
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                Log.info("Firefox driver loaded");
                break;
            case EDGE:
                driver = new EdgeDriver();
                Log.info("Edge driver loaded");
                break;
            case IE:
                driver = new InternetExplorerDriver();
                Log.info("Internet Explorer driver loaded");
                break;
            default:
                Log.info("Invalid browser");
                return;
        }
        init();
    }

    /*****************************************************************************/
    /*                        METODO PRINCIPALE                                  */
    /*****************************************************************************/
    public void goToPage(Browser browser, String url) {
        setup(browser);
        driver.get(url);
        Log.info("Page loaded: " + url);
    }

    /*****************************************************************************/
    /*                          ACCETTA COOCKIE                                  */
    /*****************************************************************************/
    public void coockieAccept(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
        Log.info("Coockie Accepted!");
    }

    /*****************************************************************************/
    /*                         SCRIVI DEL TESTO                                  */
    /*****************************************************************************/
    public void typeText(WebElement element, String text) {
        element.sendKeys(text);
        Log.info("Type: " + text);
    }

    /*****************************************************************************/
    /*                            CLICK INVIO                                    */
    /*****************************************************************************/
    public void sendEnter(WebElement element) {
        WebElement element1 = element;
        wait.until(ExpectedConditions.elementToBeClickable(element));
        Log.info("Sending Text with ENTER button!");
        element.sendKeys(Keys.ENTER);
        Log.info("Done sending text!");
    }

    /*****************************************************************************/
    /*                              CLICK ESC                                    */
    /*****************************************************************************/
    public void sendESCkey(){
        try {
            Log.info("I'm in freeze!");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Log.info("Clicking ESC button!");
        action.sendKeys(Keys.ESCAPE).perform();
    }

    /*****************************************************************************/
    /*                        CLICK IN ELEMENT                                  */
    /*****************************************************************************/
    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        String text = element.getText();
        element.click();
        Log.info("Element: (" + text + ") clicked!");
    }

    /*****************************************************************************/
    /*                            MOUSE HOVER                                    */
    /*****************************************************************************/
    public void mouseHover(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            action.moveToElement(element).perform();
            Log.info("Mouse hovered on element: " + element + "!");
        } catch (Exception e) {
            Log.info("Error while hovering element: " + element + "!");
            Log.info(e.getMessage());
        }
    }

    /*****************************************************************************/
    /*                        ATTENDI CHE IL TESTO SIA...                        */
    /*****************************************************************************/
    public void waitUntilTextIs(WebElement element, String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    /*****************************************************************************/
    /*   SPECIFICA UN ATTRIBUTO E IL SUO VALORE E RECUPERANE TUTTI GLI ELEMENTI  */
    /*****************************************************************************/
    public List<WebElement> getElementsByAttribute(String attribute, String value, String type){

        if(type == null){
            String selector = "[" + attribute + "="+value+"]";
            Log.info("Get all elements by selector: " + selector);
            return driver.findElements(By.cssSelector(selector));
        }

        String selector = "[" + attribute + type + "="+value+"]";
        Log.info("Get all elements by selector: " + selector);
        return driver.findElements(By.cssSelector(selector));
    }

    /*****************************************************************************/
    /*   SPECIFICA UN ATTRIBUTO E IL SUO VALORE E RECUPERANE IL PRIMO ELEMENTO   */
    /*****************************************************************************/
    public WebElement getElementByAttribute(String attribute, String value, String type){

        if(type == null){
            String selector = "[" + attribute + "="+value+"]";
            Log.info("Get all elements by selector: " + selector);
            return driver.findElement(By.cssSelector(selector));
        }

        String selector = "[" + attribute + type + "="+value+"]";
        Log.info("Get all elements by selector: " + selector);
        return driver.findElement(By.cssSelector(selector));
    }

    /*****************************************************************************/
    /*      RECUPERA UN ELEMENTO SPECIFICANDO IL VALORE DELL'ATTRIBUTO NAME      */
    /*****************************************************************************/
    public WebElement getElementByNameAttribute(String name){
        return driver.findElement(By.cssSelector("[name="+name+"]"));
    }

    /*****************************************************************************/
    /*          RECUPERA UN ELEMENTO FIGLIO DA UN ELEMENTO PADRE                 */
    /*****************************************************************************/
    public WebElement getChildFromParent(String child, WebElement parent, String type) {
        Log.info("Get child from parent: " + parent);

        if (child == null || type == null) {
            System.out.println("Child or Type is null.");
            return null;
        }

        try {
            if (type.equalsIgnoreCase("cssSelector")) {
                return parent.findElement(By.cssSelector(child));
            } else if (type.equalsIgnoreCase("tagName")) {
                return parent.findElement(By.tagName(child));
            } else if (type.equalsIgnoreCase("id")) {
                return parent.findElement(By.id(child));
            } else if (type.equalsIgnoreCase("className")) {
                return parent.findElement(By.className(child));
            } else {
                System.out.println("No valid type specified for child: " + child);
                return null;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Child not found: " + child + " with type: " + type);
            return null;
        }
    }


    /*****************************************************************************/
    /*                      RECUPERA UN ELEMENTO PER ID                          */
    /*****************************************************************************/
    public WebElement getElementById(String id){
        return driver.findElement(By.id(id));
    }

    /*****************************************************************************/
    /*                   RECUPERA UN ELEMENTO PER CLASS NAME                     */
    /*****************************************************************************/
    public WebElement getElementByClassName(String className){
        return driver.findElement(By.className(className));
    }

    /*****************************************************************************/
    /*                      RECUPERA UN ELEMENTO PER TAG NAME                    */
    /*****************************************************************************/
    public ArrayList<WebElement> getElementsByTagName(String tagName){
        return (ArrayList<WebElement>) driver.findElements(By.tagName(tagName));
    }

    /*****************************************************************************/
    /*                 RECUPERA UN ELEMENTO TRAMITE CSS SELECTOR                 */
    /*****************************************************************************/
    public WebElement getElementByCssSelector(String cssSelector){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
        return driver.findElement(By.cssSelector(cssSelector));
    }

    /*****************************************************************************/
    /*                      RECUPERA UN ELEMENTO PER X PATH                      */
    /*****************************************************************************/
    public WebElement getElementByXPath(String xpath){
        return driver.findElement(By.xpath(xpath));
    }

    /*****************************************************************************/
    /*                             SCROLL FULL                                   */
    /*****************************************************************************/
    public void scrollFullPage(){
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /*****************************************************************************/
    /*                       SCROLL FINO AD UN ELEMENTO                          */
    /*****************************************************************************/
    public void scrollToElement(WebElement element){
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        Log.info("Scroll to element: " + element + "!");
    }

    /*****************************************************************************/
    /*                            SCROLL ORIZZONTALE                             */
    /*****************************************************************************/
    public void scrollOrizzontal(){
        js.executeScript("window.scrollBy(500,0)");
    }

    /*****************************************************************************/
    /*                    RECUPERA IL TESTO DA UN ELEMENTO                       */
    /*****************************************************************************/
    public String getTextFromElement(WebElement element){
        if(element == null){
            return "";
        }
        return element.getText();
    }

    /*****************************************************************************/
    /*                      APRI UN LINK IN UN NUOVO TAB                         */
    /*****************************************************************************/
    public void openLinkInNewTab(String link){
        js.executeScript("window.open('" + link + "', '_blank');");

        // Passa al nuovo tab (opzionale)
        for (String tab : driver.getWindowHandles()) {
            driver.switchTo().window(tab);
        }

    }

    /*****************************************************************************/
    /*                                  QUIT                                     */
    /*****************************************************************************/
    public void quit(Long millisecondsTooWait) {
        try{
            Thread.sleep(millisecondsTooWait);
        } catch (InterruptedException e) {
            Log.info("An error occurred while quitting!");
        }
        driver.quit();
        Log.info("Driver successfully quit!");

        Log.close();
    }

}
