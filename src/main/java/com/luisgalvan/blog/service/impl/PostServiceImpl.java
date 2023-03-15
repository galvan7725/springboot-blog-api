package com.luisgalvan.blog.service.impl;

import com.luisgalvan.blog.dto.PostDto;
import com.luisgalvan.blog.exception.ResourceNotFoundException;
import com.luisgalvan.blog.model.Post;
import com.luisgalvan.blog.repository.PostRepository;
import com.luisgalvan.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);
       Post newPost = postRepository.save(post);
       //convert entity to DTO
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize) {
        Pageable pageable =  PageRequest.of(pageNo,pageSize);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        return listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostByID(long id) {
    Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post ","id" ,id));
    PostDto postDto = mapToDTO(post);
    return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id ) {
        //comprobamos que exista el post
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        Post post1 = mapToEntity(postDto);
        post1.setId(id);
        Post postUpdated = postRepository.save(post1);

        return mapToDTO(postUpdated);
    }

    @Override
        public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        if (post != null){
            postRepository.deleteById(id);
        }
    }

    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());

        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
