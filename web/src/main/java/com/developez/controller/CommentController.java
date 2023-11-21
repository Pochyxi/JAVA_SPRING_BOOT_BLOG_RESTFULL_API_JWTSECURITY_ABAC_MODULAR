package com.developez.controller;

import com.developez.DTO.CommentDto;
import com.developez.service.CommentService;
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
@RequestMapping("/api/")
@Tag(
        name = "CRUD REST APIs per Comment"
)
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }

    @Operation(
            summary = "Creazione di un Commento",
            description = "Creazione di un Commento",
            tags = { "CRUD REST APIs per Comment" }
    )
    @ApiResponse(
            responseCode = "201",
            description = "Commento creato con successo"
    )
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Bear Authentication"
            )
    })
    @PostMapping("/posts/{postId}/comments")
    @PreAuthorize( "hasAuthority('POST_COMMENT')" )
    public ResponseEntity<CommentDto> createComment( @PathVariable("postId") long postId,
                                                     @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Recupero di tutti i Commenti",
            description = "Recupero di tutti i Commenti",
            tags = { "CRUD REST APIs per Comment" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Commenti recuperati con successo"
    )
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Bear Authentication"
            )
    })
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId( @PathVariable("postId") long postId ) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @Operation(
            summary = "Recupero di un Commento",
            description = "Recupero di un Commento",
            tags = { "CRUD REST APIs per Comment" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Commento recuperato con successo"
    )
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Bear Authentication"
            )
    })
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById( @PathVariable("postId") long postId,
                                                      @PathVariable("commentId") long commentId ) {
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @Operation(
            summary = "Aggiornamento di un Commento",
            description = "Aggiornamento di un Commento",
            tags = { "CRUD REST APIs per Comment" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Commento aggiornato con successo"
    )
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Bear Authentication"
            )
    })
    @PutMapping("/posts/{postId}/comments/{commentId}")
    @PreAuthorize( "hasAuthority('MODIFY_COMMENT')" )
    public ResponseEntity<CommentDto> updateComment( @PathVariable("postId") long postId,
                                                     @PathVariable("commentId") long commentId,
                                                     @Valid @RequestBody CommentDto commentDto ) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminazione di un Commento",
            description = "Eliminazione di un Commento",
            tags = { "CRUD REST APIs per Comment" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Commento eliminato con successo"
    )
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "Bear Authentication"
            )
    })
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @PreAuthorize( "hasAuthority('DELETE_COMMENT')" )
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "commentId") Long commentId) {

        commentService.deleteComment( postId, commentId );
        return new ResponseEntity<>( "Commento eliminato con successo", HttpStatus.OK );
    }
}
