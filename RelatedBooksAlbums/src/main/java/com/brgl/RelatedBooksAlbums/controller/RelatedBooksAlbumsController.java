package com.brgl.RelatedBooksAlbums.controller;

import com.brgl.RelatedBooksAlbums.dto.RelatedItemDto;
import com.brgl.RelatedBooksAlbums.service.RelatedBooksAlbumsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(value = "Related Books & Albums API Documentation")
public class RelatedBooksAlbumsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelatedBooksAlbumsController.class);

    @Autowired
    private RelatedBooksAlbumsService relatedBooksAlbumsService;


    @RequestMapping(value = "/getRelatedBooksAndAlbums/{user_input}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns 5 related books and albums")
    public ResponseEntity<RelatedItemDto[]> getRelatedBooksAndAlbums(@PathVariable String user_input) {

        LOGGER.info("Related Books & Albums /getRelatedBooksAndAlbums web service user input : " + user_input);
        RelatedItemDto[] relatedItemDtoArr = relatedBooksAlbumsService.getRelatedBooksAndAlbumsByUserInput(user_input);

        return new ResponseEntity<>(relatedItemDtoArr, HttpStatus.OK);
    }

}
