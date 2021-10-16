package com.brgl.RelatedBooksAlbums.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User Api model documentation", description = "Model")
public class RelatedItemDto {

    @ApiModelProperty(value = "Title field of user object")
    private String title;

    @ApiModelProperty(value = "Authors field of user object")
    private String authors;

    @ApiModelProperty(value = "Information field of user object")
    private String information;

}
