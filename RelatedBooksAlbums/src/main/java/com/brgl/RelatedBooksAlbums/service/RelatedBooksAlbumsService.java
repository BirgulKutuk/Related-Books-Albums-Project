package com.brgl.RelatedBooksAlbums.service;

import com.brgl.RelatedBooksAlbums.dataaccess.RelatedAlbumsWS;
import com.brgl.RelatedBooksAlbums.dataaccess.RelatedBooksWS;
import com.brgl.RelatedBooksAlbums.dto.RelatedItemDto;
import com.brgl.RelatedBooksAlbums.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class RelatedBooksAlbumsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelatedBooksAlbumsService.class);

    @Autowired
    private RelatedBooksWS booksWS;

    @Autowired
    private RelatedAlbumsWS albumsWS;


    public RelatedItemDto[] getRelatedBooksAndAlbumsByUserInput (String userInput) {

        RelatedItemDto relatedBooks[] = new RelatedItemDto[AppConstants.MAX_COUNT_OF_EACH_RELATED_ITEM];
        booksWS.findRelatedBooksInGoogleBooksApi(relatedBooks, userInput);

        RelatedItemDto relatedAlbums[] = new RelatedItemDto[AppConstants.MAX_COUNT_OF_EACH_RELATED_ITEM];
        albumsWS.findRelatedAlbumsInITunesApi(relatedAlbums, userInput);

        RelatedItemDto relatedItems[] = Stream.concat(Arrays.stream(relatedBooks), Arrays.stream(relatedAlbums))
                .filter(item -> item != null)
                .toArray(RelatedItemDto[]::new);

        return relatedItems;
    }

}
