package Server.Data;

import Server.Models.Book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods to interact with the BnF (Bibliothèque nationale de France) API for searching books.
 */
public class APIBNF {

    /**
     * Constructs a search query based on the provided search term and criteria.
     *
     * @param search   The search term entered by the user
     * @param criteria The search criteria (by title, author, or ISBN)
     * @return The constructed search query
     */
    public static String searchBook(String search, String criteria) {
        // Initialize the query string
        String query = "";

        // Determine the query based on the provided search criteria
        switch (criteria) {
            case ("PAR TITRE"):
                query = "(bib.title all " + "\"" + search + "\"" + ") and (bib.doctype all \"a\")";
                break;
            case ("PAR AUTEUR"):
                query = "(bib.author all " + "\"" + search + "\"" + ") and (bib.doctype all \"a\")";
                break;
            case ("PAR ISBN"):
                query = "(bib.isbn all " + "\"" + search + "\"" + ") and (bib.doctype all \"a\")";
                break;
            default:
                query = "(bib.title all " + "\"" + search + "\"" + ") and (bib.doctype all \"a\")";
                break;
        }
        return query;
    }

    /**
     * Extracts data from the XML response returned by the BnF API.
     *
     * @param text        The XML response from the API
     * @param parentTag   The parent XML tag containing the data
     * @param subfieldTag The subfield XML tag containing the specific data
     * @return A list containing the extracted data
     */
    public static List<String> extractData(String text, String parentTag, String subfieldTag) {
        // Initialize a list to store the extracted data
        List<String> dataList = new ArrayList<>();

        // Find the index of the first record tag in the XML response
        int recordStartIndex = text.indexOf("<srw:record>");

        // Loop through each record in the XML response
        while (recordStartIndex != -1) {
            // Find the index of the closing tag of the current record
            int recordEndIndex = text.indexOf("</srw:record>", recordStartIndex);

            // If the end of the record is found
            if (recordEndIndex != -1) {
                // Extract the text of the current record
                String recordText = text.substring(recordStartIndex, recordEndIndex);

                // Find the index of the parent tag within the record
                int parentIndex = recordText.indexOf(parentTag);
                if (parentIndex != -1) {
                    // Find the index of the subfield tag within the parent tag
                    int subfieldIndex = recordText.indexOf(subfieldTag, parentIndex);
                    if (subfieldIndex != -1) {
                        // Find the index of the start and end of the content within the subfield tag
                        int contentStart = recordText.indexOf(">", subfieldIndex) + 1;
                        int contentEnd = recordText.indexOf("</mxc:subfield>", contentStart);
                        if (contentEnd != -1) {
                            // Extract the content between the start and end indices
                            String data = recordText.substring(contentStart, contentEnd);
                            dataList.add(data);
                        } else {
                            // If content end is not found, add null to the list
                            dataList.add(null);
                        }
                    } else {
                        // If subfield tag is not found, add null to the list
                        dataList.add(null);
                    }
                } else {
                    // If parent tag is not found, add null to the list
                    dataList.add(null);
                }
            } else {
                // If end of record is not found, add null to the list and break the loop
                dataList.add(null);
                break;
            }

            // Find the index of the next record tag
            recordStartIndex = text.indexOf("<srw:record>", recordEndIndex);
        }

        return dataList;
    }

    /**
     * Retrieves a list of books from the BnF API based on the provided search query.
     *
     * @param query The search query constructed based on user input
     * @return A list containing book details such as ISBN, title, author, publisher, language, and publication date
     */
    public static List<List<String>> retrieveBookList(String query) {
        // Initialize a list to store the details of multiple books
        List<List<String>> bookList = new ArrayList<>();

        // Initialize lists to store details of individual books
        List<String> isbnList = new ArrayList<>();
        List<String> langueList = new ArrayList<>();
        List<String> titreList = new ArrayList<>();
        List<String> auteurList = new ArrayList<>();
        List<String> editeurList = new ArrayList<>();
        List<String> dateParutionList = new ArrayList<>();

        try {
            // Initialize variables for pagination
            int startRecord = 1;
            int maximumRecords = 10;
            boolean hasMoreRecords = true;

            // Continue fetching records until no more records are available
            while (hasMoreRecords) {
                // Encode the query for URL
                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                // Construct the API URL
                String apiUrl = "http://catalogue.bnf.fr/api/SRU?version=1.2&operation=searchRetrieve&query="
                        + encodedQuery + "&startRecord=" + startRecord + "&maximumRecords=" + maximumRecords;

                // Create a URL object
                URL url = new URL(apiUrl);
                // Open a connection to the API URL
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // Set the request method
                conn.setRequestMethod("GET");

                // Get the response code
                int responseCode = conn.getResponseCode();
                // Check if response is successful
                if (responseCode != 200) {
                    // Print error message and break the loop if response is not successful
                    System.err.println("HTTP Error: " + responseCode + " for URL: " + apiUrl);
                    break;
                }

                // Read the response from the connection
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Convert the response to a string
                String extractedText = response.toString();

                // Extract data for each book from the response
                List<String> isbns = extractData(extractedText, "<mxc:datafield tag=\"073\" ind1=\" \" ind2=\"0\">", "<mxc:subfield code=\"a\">");
                List<String> langues = extractData(extractedText, "<mxc:datafield tag=\"102\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"a\">");
                List<String> titres = extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"a\">");
                List<String> auteurs = extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"f\">");
                List<String> editeurs = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"c\">");
                List<String> datesParution = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"d\">");

                // Add the extracted data to the respective lists
                isbnList.addAll(isbns);
                titreList.addAll(titres);
                auteurList.addAll(auteurs);
                editeurList.addAll(editeurs);
                langueList.addAll(langues);
                dateParutionList.addAll(datesParution);

                // Check if there are more records available
                if (isbns.size() < maximumRecords) {
                    hasMoreRecords = false;
                } else {
                    startRecord += maximumRecords;
                }

                // Disconnect the connection
                conn.disconnect();
            }

            // Remove incomplete records from the lists
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

            // Add the lists of book details to the bookList
            bookList.add(isbnList);
            bookList.add(titreList);
            bookList.add(auteurList);
            bookList.add(editeurList);
            bookList.add(langueList);
            bookList.add(dateParutionList);

        } catch (Exception e) {
            // Print stack trace if an exception occurs
            e.printStackTrace();
        }
        return bookList;
    }

    /**
     * Retrieves details of a book from the BnF API based on the provided ISBN.
     *
     * @param isbn The ISBN of the book to retrieve
     * @return An instance of the Book class containing details of the book
     */
    public static Book retrieveBook_isbn(String isbn) {
        // Construct a query based on the ISBN
        String query1 = "bib.isbn all " + "\"" + isbn + "\"";
        try {
            // Encode the query for URL
            String encodedQuery = URLEncoder.encode(query1, "UTF-8");
            // Construct the API URL
            String apiUrl = "http://catalogue.bnf.fr/api/SRU?version=1.2&operation=searchRetrieve&query=" + encodedQuery;

            // Create a URL object
            URL url = new URL(apiUrl);

            // Open a connection to the API URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set the request method
            conn.setRequestMethod("GET");

            // Read the response from the connection
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Convert the response to a string
            String extractedText = response.toString();

            // Extract details of the book from the response
            List<String> titre = extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"a\">");
            List<String> langue = extractData(extractedText, "<mxc:datafield tag=\"102\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"a\">");
            List<String> auteur = extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"f\">");
            List<String> editeur = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"c\">");
            List<String> date_parution = extractData(extractedText, "<mxc:datafield tag=\"210\" ind1=\" \" ind2=\" \">", "<mxc:subfield code=\"d\">");

            // Create a Book object with the extracted details
            Book book = new Book(isbn, titre.get(0), auteur.get(0), editeur.get(0), langue.get(0), date_parution.get(0));
            return book;

        } catch (Exception e) {
            // Print stack trace if an exception occurs
            e.printStackTrace();
        }

        // Return null if book details could not be retrieved
        return null;
    }
}

