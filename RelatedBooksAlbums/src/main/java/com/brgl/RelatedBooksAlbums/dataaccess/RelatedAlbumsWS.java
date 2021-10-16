package com.brgl.RelatedBooksAlbums.dataaccess;

import be.ceau.itunesapi.Search;
import be.ceau.itunesapi.request.Country;
import be.ceau.itunesapi.request.search.Attribute;
import be.ceau.itunesapi.request.search.Media;
import be.ceau.itunesapi.response.Response;
import be.ceau.itunesapi.response.Result;
import com.brgl.RelatedBooksAlbums.dto.RelatedItemDto;
import com.brgl.RelatedBooksAlbums.utils.AppConstants;
import org.springframework.stereotype.Service;

@Service
public class RelatedAlbumsWS {

    public void findRelatedAlbumsInITunesApi(RelatedItemDto relatedAlbums[], String userInput) {
        Result album;

        Response response = new Search(userInput)
                .setCountry(Country.NETHERLANDS)
                .setAttribute(Attribute.ALBUM_TERM)
                .setMedia(Media.MUSIC)
                .setLimit(AppConstants.MAX_COUNT_OF_EACH_RELATED_ITEM)
                .execute();

        for (int i = 0; i < response.getResultCount(); i++) {
            album = response.getResults().get(i);
            relatedAlbums[i] = new RelatedItemDto(
                    album.getTrackName(),
                    album.getArtistName(),
                    album.getDescription()
            );
        }
    }

}
