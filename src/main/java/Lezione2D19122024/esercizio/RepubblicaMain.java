package Lezione2D19122024.esercizio;

/*
Lezione 2 (19 Dicembre 2024)

Andare su Repubblica e vedere se esiste 'i fatti del giorno', se esiste apri l'articolo in una nuova tab e se ne recuperi:
    - Titolo
    - Oggetto (parte sotto il titolo)
    - Autore
Se esiste anche il 10 va recuperato con le medesime condizioni del primo articolo
*/

import Lezione2D19122024.articolo.Articolo;
import org.openqa.selenium.WebElement;
import utils.log.Log;
import webdriver.EnvConfig;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RepubblicaMain {

    public static void main(String[] args){

        AtomicInteger i = new AtomicInteger();
        List lista;
        List articles = new LinkedList<Articolo>();
        WebElement element;

        //disabilito il Log
        Log.setEnabled(false);

        EnvConfig envConfig = new EnvConfig();

        //apro il sito di Repubblica su browser chrome
        envConfig.goToPage(EnvConfig.Browser.CHROME, "https://www.repubblica.it/");

        //accetto i coockie
        envConfig.coockieAccept(envConfig.getElementByCssSelector("button.iubenda-cs-accept-btn.iubenda-cs-btn-primary"));
        
        //scroll fino a 'I Fatti del giorno'
        element = envConfig.getElementByAttribute("data-bid", "RIAPERTURA-BRONZO", null);
        envConfig.scrollToElement(element);

        //scrollo tutta la pagina
        envConfig.scrollFullPage();

        //recupero tutti gli elementi con attributo data-bid = 'fattidelgiornoX' e li aggiungo ad una lista
        lista = envConfig.getElementsByAttribute("data-bid", "fattidelgiorno", "^");
        //recupero tutti gli elementi con attributo data-bid = 'ifattidelgiornoX' e li accodo alla stessa lista di prima
        lista.addAll(envConfig.getElementsByAttribute("data-bid", "ifattidelgiorno", "^"));
        //aggiungo i testa alla lista il primo elemento dei fatti del giorno
        lista.addFirst(element);

        //creo una lista di articoli
        lista.forEach(x->{
            //System.out.println((i.getAndIncrement()) + ">. " + envConfig.getTextFromElement((WebElement) x) + "\n");
            articles.add(
                    new Articolo(envConfig.getTextFromElement(envConfig.getChildFromParent("entry__overtitle", (WebElement) x, "className")),
                            envConfig.getTextFromElement(envConfig.getChildFromParent("entry__title", (WebElement) x, "className")),
                            envConfig.getTextFromElement(envConfig.getChildFromParent("entry__author", (WebElement) x, "className"))
                    ));
        });

        System.out.println("\n");

        //se la lista non è vuota
        if(!articles.isEmpty()){
            System.out.println("First article:\n" + articles.getFirst().toString() + "\n");
            //se ci sono almeno 10 articoli
            if(articles.size() >= 9) {
                System.out.println("10th article:\n" + articles.get(9).toString() + "\n");
            }
        }

        //esco dal driver
        envConfig.quit(1500L);

    }

}
