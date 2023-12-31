package com.developez.service;

import com.developez.DTO.PostDto;
import com.developez.DTO.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost( PostDto postDto );

    PostResponse getAllPosts( int pageNo, int pageSize, String sortBy, String sortDir );

    PostDto getPostById( Long id );

    PostDto updatePost(PostDto postDto, Long id);

    void deletePostById(Long id);

    List<PostDto> getAllPostsByCategoryId( Long categoryId );
}
