package com.brgl.RelatedBooksAlbums.dataaccess;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.brgl.RelatedBooksAlbums.dto.RelatedItemDto;
import com.brgl.RelatedBooksAlbums.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class RelatedBooksWS {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelatedBooksWS.class);
    private static final String googleBooksURL = "https://www.googleapis.com/books/v1/volumes";
    private static final int googleBooksTimeout = 5000;

    @Autowired
    protected MessageSource messageSource;

    public void findRelatedBooksInGoogleBooksApi(RelatedItemDto relatedBooks[], String userInput) {

        try {
            JsonObject response = doGet(googleBooksURL + "/?q=+intitle:" + userInput);
            ArrayList<JsonObject> googleBookList = findBook(userInput, response.getAsJsonArray("items"));

            for (int i = 0; i < googleBookList.size(); i++) {
                if (googleBookList.get(i) != null) {
                    try {
                        List<String> authors = new ArrayList<>();
                        JsonArray authorsArray = googleBookList.get(i).get("volumeInfo").getAsJsonObject().get("authors").getAsJsonArray();
                        for (int count = 0; count < authorsArray.size(); count++) {
                            authors.add(authorsArray.get(count).getAsString());
                        }
                        relatedBooks[i] = new RelatedItemDto(
                                googleBookList.get(i).get("volumeInfo").getAsJsonObject().get("title").getAsString(),
                                authors.toString(),
                                googleBookList.get(i).get("volumeInfo").getAsJsonObject().get("description") != null
                                        ? googleBookList.get(i).get("volumeInfo").getAsJsonObject().get("description").getAsString()
                                        : ""
                        );
                    } catch (Exception e) {
                        LOGGER.error("[GOOGLE INTEGRATION] Error: " + e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("[GOOGLE INTEGRATION] Error: " + e, e);
        }
    }

    private ArrayList<JsonObject> findBook(String userInput, JsonArray items) {
        int i = 0;
        ArrayList<JsonObject> googleBookList = new ArrayList<>();
        JsonObject item;
        String googleTitle;

        // Firstly get books that user input equals to book title
        while (i < items.size() && googleBookList.size() != AppConstants.MAX_COUNT_OF_EACH_RELATED_ITEM) {
            item = items.get(i).getAsJsonObject();
            googleTitle = item.get("volumeInfo").getAsJsonObject().get("title").getAsString();
            if (googleTitle.equalsIgnoreCase(userInput)) {
                googleBookList.add(item);
            }
            i++;
        }

        // Secondly, if array has more empty space than get books that book title contains user input
        i = 0;
        while (i < items.size() && googleBookList.size() != AppConstants.MAX_COUNT_OF_EACH_RELATED_ITEM) {
            item = items.get(i).getAsJsonObject();
            googleTitle = item.get("volumeInfo").getAsJsonObject().get("title").getAsString();
            if (googleTitle.contains(userInput) && !googleTitle.equalsIgnoreCase(userInput)) {
                googleBookList.add(item);
            }
            i++;
        }

        return googleBookList;
    }

    private JsonObject doGet(String url) throws Exception {
        ResponseEntity<String> response = getRestTemplate().getForEntity(url, String.class);

        if (!response.getStatusCode().is2xxSuccessful())
            throw new Exception(messageSource.getMessage("generic.error.offset",
                    null, Locale.getDefault()));

        return new Gson().fromJson(response.getBody(), JsonObject.class);
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
        SimpleClientHttpRequestFactory rf = (SimpleClientHttpRequestFactory) restTemplate
                .getRequestFactory();
        rf.setReadTimeout(googleBooksTimeout);
        rf.setConnectTimeout(googleBooksTimeout);

        return restTemplate;
    }

}