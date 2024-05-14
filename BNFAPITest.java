import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class BNFAPITest {

    public static void main(String[] args) {
        try {
            // URL de l'API BNF avec la requête
            String query = "(bib.author all \"romain gary\") and (bib.title all \"la promesse de l'aube\") not (bib.doctype any \"g h v\")";
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

            // Affichage de la réponse
            System.out.println(response.toString());

            // Texte extrait de l'API XML
            String extractedText = response.toString();

            // Extraction de l'ISBN
            String isbn = extractDataWithAttributes(extractedText, "<mxc:datafield tag=\"073\" ind1=\" \" ind2=\"0\">", "<mxc:subfield code=\"a\">");

            // Extraction de la langue
            String langue = extractDataWithAttributes(extractedText, "<mxc:datafield tag=\"102\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"a\">");

            // Extraction du titre
            String titre = extractDataWithAttributes(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"a\">");

            // Extraction de l'auteur
            String auteur = extractDataWithAttributes(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"f\">");

            //Extraction de l'éditeur
            String editeur = extractDataWithAttributes(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"c\">");

            //Extraction de la date de parution
            String date_parution = extractDataWithAttributes(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"d\">");

            // Affichage des informations extraites
            System.out.println("ISBN: " + isbn);
            System.out.println("Langue: " + langue);
            System.out.println("Titre: " + titre);
            System.out.println("L'auteur: " + auteur);
            System.out.println("L'éditeur: " + editeur);
            System.out.println("Année de parution: " + date_parution);


            // Fermeture de la connexion
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static String extractData(String text, String startTag, String endTag) {
        int startIndex = text.indexOf(startTag);
        int endIndex = text.indexOf(endTag);
        if (startIndex != -1 && endIndex != -1) {
            return text.substring(startIndex + startTag.length(), endIndex);
        } else {
            return "";
        }
    }

    private static String extractDataWithAttributes(String text, String parentTag, String subfieldTag) {
        // Recherche de la balise parent
        int parentIndex = text.indexOf(parentTag);
        if (parentIndex != -1) {
            // Recherche de la balise <mxc:subfield code="a"> à l'intérieur de la balise parent
            int subfieldIndex = text.indexOf(subfieldTag, parentIndex);
            if (subfieldIndex != -1) {
                // Trouver le début du contenu de la balise
                int contentStart = subfieldIndex + subfieldTag.length();
                // Trouver la fin du contenu de la balise
                int contentEnd = text.indexOf("</mxc:subfield>", contentStart);
                if (contentEnd != -1) {
                    // Extraction du contenu de la balise <mxc:subfield code="a">
                    return text.substring(contentStart, contentEnd);
                }
            }
        }
        return ""; // Retourne une chaîne vide si le contenu n'est pas trouvé
    }
}
