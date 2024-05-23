package Server.Data;

import Client.LibraryPage;
import Server.Manager.BookManager;
import Server.Models.Book;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class APIBNF {
    public static String searchBook (String search) {

        String query = "((bib.author all " + "\"" + search + "\"" + ") or (bib.title all " + "\"" + search + "\"" + ")) and (bib.doctype all \"a\")";
        return query;
    }

    public static List<String> extractData(String text, String parentTag, String subfieldTag) {
        List<String> dataList = new ArrayList<>();

        int recordStartIndex = text.indexOf("<srw:record>");

        while (recordStartIndex != -1) {
            int recordEndIndex = text.indexOf("</srw:record>", recordStartIndex);

            if (recordEndIndex != -1) {
                String recordText = text.substring(recordStartIndex, recordEndIndex);

                int parentIndex = recordText.indexOf(parentTag);
                if (parentIndex != -1) {
                    int subfieldIndex = recordText.indexOf(subfieldTag, parentIndex);
                    if (subfieldIndex != -1) {
                        int contentStart = recordText.indexOf(">", subfieldIndex) + 1;
                        int contentEnd = recordText.indexOf("</mxc:subfield>", contentStart);
                        if (contentEnd != -1) {
                            String data = recordText.substring(contentStart, contentEnd);
                            dataList.add(data);
                        } else {
                            dataList.add(null);
                        }
                    } else {
                        dataList.add(null);
                    }
                } else {
                    dataList.add(null);
                }
            } else {
                dataList.add(null);
                break;
            }

            recordStartIndex = text.indexOf("<srw:record>", recordEndIndex);
        }

        return dataList;
    }

    public static List<List<String>> retrieveBookList(String query) {
        List<List<String>> bookList = new ArrayList<>();
        List<String> isbnList = new ArrayList<>();
        List<String> langueList = new ArrayList<>();
        List<String> titreList = new ArrayList<>();
        List<String> auteurList = new ArrayList<>();
        List<String> editeurList = new ArrayList<>();
        List<String> dateParutionList = new ArrayList<>();

        try {
            int startRecord = 1;
            int maximumRecords = 10;
            boolean hasMoreRecords = true;

            while (hasMoreRecords) {
                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                String apiUrl = "http://catalogue.bnf.fr/api/SRU?version=1.2&operation=searchRetrieve&query="
                        + encodedQuery + "&startRecord=" + startRecord + "&maximumRecords=" + maximumRecords;

                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode != 200) {
                    System.err.println("HTTP Error: " + responseCode + " for URL: " + apiUrl);
                    break;
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String extractedText = response.toString();

                List<String> isbns = extractData(extractedText, "<mxc:datafield tag=\"073\" ind1=\" \" ind2=\"0\">", "<mxc:subfield code=\"a\">");
                List<String> langues = extractData(extractedText, "<mxc:datafield tag=\"102\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"a\">");
                List<String> titres = extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"a\">");
                List<String> auteurs = extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"f\">");
                List<String> editeurs = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"c\">");
                List<String> datesParution = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"d\">");

                isbnList.addAll(isbns);
                titreList.addAll(titres);
                auteurList.addAll(auteurs);
                editeurList.addAll(editeurs);
                langueList.addAll(langues);
                dateParutionList.addAll(datesParution);

                if (isbns.size() < maximumRecords) {
                    hasMoreRecords = false;
                } else {
                    startRecord += maximumRecords;
                }

                conn.disconnect();
            }

            for (int i = isbnList.size() - 1; i >= 0; i--) {
                if (isbnList.get(i) == null || langueList.get(i) == null || titreList.get(i) == null || auteurList.get(i) == null || editeurList.get(i) == null || dateParutionList.get(i) == null) {
                    isbnList.remove(i);
                    langueList.remove(i);
                    titreList.remove(i);
                    auteurList.remove(i);
                    editeurList.remove(i);
                    dateParutionList.remove(i);
                }
            }

            bookList.add(isbnList);
            bookList.add(titreList);
            bookList.add(auteurList);
            bookList.add(editeurList);
            bookList.add(langueList);
            bookList.add(dateParutionList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public static Book retrieveBook_isbn(String isbn) {
        String query1 = "bib.isbn all " + "\"" + isbn + "\"";
        try {
            // URL de l'API BNF avec la requête
            String encodedQuery = URLEncoder.encode(query1, "UTF-8");
            String apiUrl = "http://catalogue.bnf.fr/api/SRU?version=1.2&operation=searchRetrieve&query=" + encodedQuery;

            // Création de l'URL
            URL url = new URL(apiUrl);

            // Ouverture de la connexion HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configuration de la méthode de requête
            conn.setRequestMethod("GET");

            // Lecture de la réponse
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Texte extrait de l'API XML
            String extractedText = response.toString();
            List<String> titre = APIBNF.extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"a\">");
            List<String> langue = extractData(extractedText, "<mxc:datafield tag=\"102\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"a\">");
            List<String> auteur = extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"f\">");
            List<String> editeur = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"c\">");
            List<String> date_parution = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"d\">");

            Book book = new Book(isbn, titre.get(0), auteur.get(0), editeur.get(0), langue.get(0),date_parution.get(0));
            return book;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

