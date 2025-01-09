package org.example;

import org.openqa.selenium.WebElement;
import webdriver.EnvConfig;

public class Main12122024 {
    public static void main(String[] args) {

        EnvConfig config = new EnvConfig();

        config.goToPage(EnvConfig.Browser.CHROME, "https://it.wikipedia.org/wiki/Pagina_principale");
        WebElement element = config.getElementByNameAttribute("search");
        config.typeText(element, "Selenium");
        System.out.println("Testo scritto nella search");
        config.sendESCkey();
        config.click(config.getElementByCssSelector("#searchform > div > button"));
        System.out.println("Search button clicked!");

        //config.quit();

    }
}