/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Miglob
 * 
 * Classe que manipula e visualiza os dados que estão anexos aos modelos gerados.
 */
public class Log implements Serializable {

    private LocalDateTime timestamp;
    private String destinationPageTitle;
    private String originPageTitle;
    private String url;
    private int numberOfHyperlinks;

    public Log(String destinationPageTitle, String originPageTitle, String url, int numberOfHyperlinks) {

        if (validateInput(destinationPageTitle,originPageTitle, url, numberOfHyperlinks)) {
            this.timestamp = LocalDateTime.now();
            this.destinationPageTitle = destinationPageTitle;
            this.originPageTitle = originPageTitle;
            this.url = url;
            this.numberOfHyperlinks = numberOfHyperlinks;
        }
    }
    
    private boolean validateInput(String destinationPageTitle, String originPageTitle, String url, int numberOfHyperlinks){
        return destinationPageTitle != null && originPageTitle != null && url != null && numberOfHyperlinks >= 0;
    }
    
/**
 * Método para saber o exacto momento que se produziu o modelo
 * @return a altura exacta que se gerou o modelo
 */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
/**
 * Método criador de um dado momento de um modelo
 * @param timestamp o formato com a altura exacta do modelo pretendido
 */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
/**
 * Método usado para se saber a página de destino.
 * @return um textocom a página de destino
 */
    public String getDestinationPageTitle() {
        return destinationPageTitle;
    }
/**
 * Método para designar a página de destino que se pretende.
 * @param destinationPageTitle a página de destino do modelo que se deseja
 */
    public void setDestinationPageTitle(String destinationPageTitle) {
        this.destinationPageTitle = destinationPageTitle;
    }
/**
 * 
 * Método para se saber a página de origem que se pretende.
 * @return um texto com a pagina de origem
 */
    public String getOriginPageTitle() {
        return originPageTitle;
    }
/**
 * Método para designar a página de origem que se pretende.
 * @param originPageTitle a página de destino do modelo que se deseja
 */
    public void setOriginPageTitle(String originPageTitle) {
        this.originPageTitle = originPageTitle;
    }
/**
 * Método para se saber o url de determinada página
 * @return um texto com o url
 */
    public String getUrl() {
        return url;
    }
/**
 * Método para designar o url da página que se pretende.
 * @param url o url da página que se deseja
 */
    public void setUrl(String url) {
        this.url = url;
    }
/**
 * Método parase saber o número de links de determinado modelo
 * @return um inteiro com o número de links
 */
    public int getNumberOfHyperlinks() {
        return numberOfHyperlinks;
    }
/**
 * Método para designar o número de links de um modelo
 * @param numberOfHyperlinks o número de links do modelo
 */
    public void setNumberOfHyperlinks(int numberOfHyperlinks) {
        this.numberOfHyperlinks = numberOfHyperlinks;
    }
/**
 * Método para formatar o texto em que se mostra a informação do modelo com os 
 * seus atributos de classe
 * @return um texto formatado com a informação do modelo
 */
    @Override
    public String toString() {
        
        return String.format("%-20s | %-60s | %-100s | %-60s | %-60s", getFormattedTimeStamp(), destinationPageTitle, url, originPageTitle, numberOfHyperlinks);
        
        //return getFormattedTimeStamp() + "\t|\t" + destinationPageTitle + "\t|\t" + url + "\t|\t" + originPageTitle + "\t|\t" + numberOfHyperlinks;
    }

    private String getFormattedTimeStamp() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedTimeStamp = timestamp.format(dateTimeFormatter);

        return formattedTimeStamp;
    }

}
