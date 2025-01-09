package lezione1D12122024.esercizio;

/*
Esercizio assegnato nella lezione 1 (12 Dicembre 2024)
*/

import webdriver.EnvConfig;

public class LezioneUnoMain {

    public static void main(String[] args) {

        EnvConfig config = new EnvConfig();
        String text;

        //Apro la paina web
        config.goToPage(EnvConfig.Browser.CHROME, "https://www.google.com/");

        //accetto i coockie
        config.coockieAccept(config.getElementByCssSelector("#L2AGLb > div"));
        //scrivo nella barra di ricerca
        config.typeText(config.getElementByCssSelector("#APjFqb"), "Chrome Driver");
        //clicco Invio
        config.sendEnter(config.getElementByCssSelector("#APjFqb"));
        //clicco il primo link
        config.click(config.getElementByCssSelector("div > span > a > h3"));
        //clicco su 'dashboard di disponibilità di Chrome per i Test'
        config.click(config.getElementByCssSelector("aside > span > a:nth-child(1)"));
        //scrolla fino alla tabella
        config.scrollToElement(config.getElementByCssSelector("div:nth-child(3) > table > tbody"));
        //recupero testo per il link dalla tabella
        text = config.getTextFromElement(config.getElementByCssSelector("#stable > div:nth-child(3) > table > tbody > tr:nth-child(5) > td:nth-child(3) > code"));
        //Apro in un nuovo tab
        config.openLinkInNewTab(text);
        //Chiudo ed esco dal browser
        config.quit(1000L);

    }

}
