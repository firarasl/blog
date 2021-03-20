package com.example.portfolio.mapper;

import com.example.portfolio.dto.SubfiteDto;
import com.example.portfolio.model.Post;
import com.example.portfolio.model.Subfite;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring")

public interface SubfiteMapper {
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subfite.getPosts()))")
    SubfiteDto mapSubfiteToDto(Subfite subfite);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subfite mapDtoToSubfite(SubfiteDto subfiteDto);

}
