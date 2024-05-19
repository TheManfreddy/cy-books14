import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class APIBNF{

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

        try {
            // URL de l'API BNF avec la requête
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
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


            List<String> isbn = extractData(extractedText, "<mxc:datafield tag=\"073\" ind1=\" \" ind2=\"0\">", "<mxc:subfield code=\"a\">");
            List<String> langue = extractData(extractedText, "<mxc:datafield tag=\"102\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"a\">");
            List<String> titre = extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"a\">");
            List<String> auteur = extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"f\">");
            List<String> editeur = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"c\">");
            List<String> date_parution = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"d\">");

            // Supprimer les éléments indésirables en parcourant les listes à l'envers
            for (int i = isbn.size() - 1; i >= 0; i--) {
                // Si l'un des éléments de la liste est null, supprimez tous les éléments correspondants de toutes les listes
                if (isbn.get(i) == null || langue.get(i) == null || titre.get(i) == null || auteur.get(i) == null || editeur.get(i) == null || date_parution.get(i) == null) {
                    isbn.remove(i);
                    langue.remove(i);
                    titre.remove(i);
                    auteur.remove(i);
                    editeur.remove(i);
                    date_parution.remove(i);
                }
            }



            bookList.add(isbn);
            bookList.add(langue);
            bookList.add(titre);
            bookList.add(auteur);
            bookList.add(editeur);
            bookList.add(date_parution);





            // Fermeture de la connexion
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return bookList;
    }


}