package com.developez.controller;

import com.developez.DTO.PostDto;
import com.developez.DTO.PostResponse;
import com.developez.service.PostService;
import com.developez.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST APIs per Post"
)
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    // Creazione di un Post
    @Operation(
            summary = "Creazione di un Post",
            description = "Creazione di un Post",
            tags = { "CRUD REST APIs per Post" }
    )
    @ApiResponse(
            responseCode = "201",
            description = "Post creato con successo"
    )
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Bear Authentication"
            )
    })
    @PreAuthorize( "hasAuthority('CREATE_POST')" )
    @PostMapping
    public ResponseEntity<PostDto> createPost( @Valid @RequestBody PostDto postDto ){
        return new ResponseEntity<>( postService.createPost( postDto ), HttpStatus.CREATED );
    }


    // Recupero di tutti i Post con paginazione e oggetto PostResponse
    // Utilizzo del sorting
    // Utilizzo di sort direction
    // Utilizzo di AppConstants
    @Operation(
            summary = "Recupero di tutti i Post con paginazione e oggetto PostResponse",
            description = "Recupero di tutti i Post con paginazione e oggetto PostResponse",
            tags = { "CRUD REST APIs per Post" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Recupero di tutti i Post con paginazione e oggetto PostResponse"
    )
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Bear Authentication"
            )
    })
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>( postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK );
    }


    // Recupero di un Post tramite ID
    @Operation(
            summary = "Recupero di un Post tramite ID",
            description = "Recupero di un Post tramite ID",
            tags = { "CRUD REST APIs per Post" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Recupero di un Post tramite ID"
    )
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Bear Authentication"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById( @PathVariable("id") Long id ){
        return new ResponseEntity<>( postService.getPostById( id ), HttpStatus.OK );
    }


    // Aggiornamento di un Post tramite ID
    @Operation(
            summary = "Aggiornamento di un Post tramite ID",
            description = "Aggiornamento di un Post tramite ID",
            tags = { "CRUD REST APIs per Post" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Aggiornamento di un Post tramite ID"
    )
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Bear Authentication"
            )
    })
    @PreAuthorize( "hasAuthority('MODIFY_POST')" )
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") Long id ){
        return new ResponseEntity<>( postService.updatePost( postDto, id ), HttpStatus.OK );
    }

    // Eliminazione di un Post tramite ID
    @Operation(
            summary = "Eliminazione di un Post tramite ID",
            description = "Eliminazione di un Post tramite ID",
            tags = { "CRUD REST APIs per Post" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Eliminazione di un Post tramite ID"
    )
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Bear Authentication"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize( "hasAuthority('DELETE_POST')" )
    public ResponseEntity<String> deletePostById( @PathVariable("id") Long id ){
        postService.deletePostById( id );
        return new ResponseEntity<>( "Post eliminato con successo.", HttpStatus.OK );
    }

    // Recupero di tutti i Post tramite ID di Category
    // http://localhost:8080/api/posts/category/{{categoryId}}
    @Operation(
            summary = "Recupero di tutti i Post tramite ID di Category",
            description = "Recupero di tutti i Post tramite ID di Category",
            tags = { "CRUD REST APIs per Post" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Recupero di tutti i Post tramite ID di Category"
    )
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Bear Authentication"
            )
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategory( @PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok( postService.getAllPostsByCategoryId( categoryId ) );
    }
}
