package com.luisgalvan.blog.service;

import com.luisgalvan.blog.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts(int pageNo, int pageSize);

    PostDto getPostByID(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePost(long id);
}
