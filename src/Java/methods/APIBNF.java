package methods;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class APIBNF {

    //private changé en public pour utilisation dans displayUser
    public static List<String> extractData(String text, String parentTag, String subfieldTag) {
        List<String> dataList = new ArrayList<>();

        // Déterminer la première occurrence de la balise <srw:record>
        int recordStartIndex = text.indexOf("<srw:record>");

        while (recordStartIndex != -1) {
            // Déterminer la fin de la balise <srw:record>
            int recordEndIndex = text.indexOf("</srw:record>", recordStartIndex);

            if (recordEndIndex != -1) {
                // Extraire le texte entre les balises <srw:record> et </srw:record>
                String recordText = text.substring(recordStartIndex, recordEndIndex);

                // Recherche de la balise parent à l'intérieur de chaque balise <srw:record>
                int parentIndex = recordText.indexOf(parentTag);
                if (parentIndex != -1) {
                    // Recherche de la balise <mxc:subfield code="a"> à l'intérieur de la balise parent
                    int subfieldIndex = recordText.indexOf(subfieldTag, parentIndex);
                    if (subfieldIndex != -1) {
                        // Trouver le début du contenu de la balise
                        int contentStart = recordText.indexOf(">", subfieldIndex) + 1;
                        // Trouver la fin du contenu de la balise
                        int contentEnd = recordText.indexOf("</mxc:subfield>", contentStart);
                        if (contentEnd != -1) {
                            // Extraction du contenu de la balise <mxc:subfield code="a">
                            String data = recordText.substring(contentStart, contentEnd);
                            dataList.add(data);
                        } else {
                            dataList.add(null); // Ajouter explicitement null si aucune donnée n'est trouvée
                        }
                    } else {
                        dataList.add(null); // Ajouter explicitement null si aucune balise subfield n'est trouvée
                    }
                } else {
                    dataList.add(null); // Ajouter explicitement null si aucune balise parent n'est trouvée
                }
            } else {
                dataList.add(null); // Ajouter explicitement null si aucune fin de balise </srw:record> n'est trouvée
                break; // Sortir de la boucle pour éviter une boucle infinie
            }

            // Chercher la prochaine occurrence de la balise <srw:record> après la balise </srw:record>
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
            int maximumRecords = 10; // Nombre de résultats par page
            boolean hasMoreRecords = true;

            while (hasMoreRecords) {
                // URL de l'API BNF avec la requête et les paramètres de pagination
                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                String apiUrl = "http://catalogue.bnf.fr/api/SRU?version=1.2&operation=searchRetrieve&query="
                        + encodedQuery + "&startRecord=" + startRecord + "&maximumRecords=" + maximumRecords;

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

                // Extraction des données
                List<String> isbns = extractData(extractedText, "<mxc:datafield tag=\"073\" ind1=\" \" ind2=\"0\">", "<mxc:subfield code=\"a\">");
                List<String> langues = extractData(extractedText, "<mxc:datafield tag=\"102\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"a\">");
                List<String> titres = extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"a\">");
                List<String> auteurs = extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"f\">");
                List<String> editeurs = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"c\">");
                List<String> datesParution = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"d\">");

                // Ajouter les données extraites aux listes globales
                isbnList.addAll(isbns);
                langueList.addAll(langues);
                titreList.addAll(titres);
                auteurList.addAll(auteurs);
                editeurList.addAll(editeurs);
                dateParutionList.addAll(datesParution);

                // Vérifier si tous les résultats ont été récupérés
                if (isbns.size() < maximumRecords) {
                    hasMoreRecords = false;
                } else {
                    startRecord += maximumRecords;
                }

                // Fermeture de la connexion
                conn.disconnect();
            }

            // Supprimer les éléments indésirables en parcourant les listes à l'envers
            for (int i = isbnList.size() - 1; i >= 0; i--) {
                // Si l'un des éléments de la liste est null, supprimez tous les éléments correspondants de toutes les listes
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
            bookList.add(langueList);
            bookList.add(titreList);
            bookList.add(auteurList);
            bookList.add(editeurList);
            bookList.add(dateParutionList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookList;
    }

    public static List<List<String>> retrieveBook_isbn(String isbn) {
        List<List<String>> book = new ArrayList<>();

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

            book.add(titre);
            book.add(langue);
            book.add(auteur);
            book.add(editeur);
            book.add(date_parution);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (book);

    }

}