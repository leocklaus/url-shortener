package io.github.leocklaus.url_sortener.api;

import io.github.leocklaus.url_sortener.api.dto.URLCountOutputDTO;
import io.github.leocklaus.url_sortener.api.dto.URLInputDTO;
import io.github.leocklaus.url_sortener.api.dto.URLOutputDTO;
import io.github.leocklaus.url_sortener.api.exceptionhandler.ExceptionModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "URL Controller", description = "Endpoints to create and manipulate URLs")
public interface IURLController {

    @Operation(
            summary = "Create a new Shorten URL",
            description = "A Shortened URL will be generated with size between 5 and 10 characters"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "Shortened URL created",
                            responseCode = "201",
                            headers = @Header(
                                    name = "Location",
                                    description = "Location of the shortened URL"
                            ),
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = URLOutputDTO.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ExceptionModel.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "User ID is not the same as User Token ID",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ExceptionModel.class
                                    )
                            )
                    )
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/shorten-url")
    ResponseEntity<URLOutputDTO> shortenURL(@RequestBody @Valid URLInputDTO dto);

    @Operation(
            summary = "Redirects to Original URL",
            description = "The shortened URL should be provided in the request, and if it is correct + " +
                    "the request will be redirected to the original URL"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "Redirects to original URL",
                            responseCode = "302",
                            headers = @Header(
                                    name = "Location",
                                    description = "Location of the original URL"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Shortened URL Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ExceptionModel.class
                                    )
                            )
                    )
            }
    )
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{url}")
    ResponseEntity<Void> redirect(
            @Parameter(description = "Shortened ID", required = true) @PathVariable String url);

    @Operation(
            summary = "Get the number os views from an URL",
            description = "This endpoint is useful to check the number of views from a specific" +
                    " URL, which should be provided on parameters. User also has to provide a token + " +
                    "that matches the same user id a the provided URL owner id."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "Returns the URL number of views",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = URLCountOutputDTO.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Shortened URL Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ExceptionModel.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User has to provide a valid token",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ExceptionModel.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "User ID is not the same as User Token ID",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ExceptionModel.class
                                    )
                            )
                    )
            }
    )
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/stats/{url}/")
    ResponseEntity<URLCountOutputDTO> getViews(
            @Parameter(description = "Shortened ID", required = true) @PathVariable String url);
}
