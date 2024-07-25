package io.github.leocklaus.url_sortener.api;

import io.github.leocklaus.url_sortener.api.dto.*;
import io.github.leocklaus.url_sortener.api.exceptionhandler.ExceptionModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "User Controller", description = "Endpoints to create and login users")
public interface IUserController {

    @Operation(
            summary = "Create a new User",
            description = "A new user will be created with correct info provided. Email should be unique."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "User created",
                            responseCode = "201",
                            headers = @Header(
                                    name = "Location",
                                    description = "Location of the User"
                            ),
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UserOutputDTO.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Email Already Taken Exception",
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
    @PostMapping
    ResponseEntity<UserOutputDTO> addUser(@RequestBody @Valid UserInputDTO dto);

    @Operation(
            summary = "User Login endpoint",
            description = "If info provided is correct, a JWT token will be provided in response"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "User logged in",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = LoginResponseDTO.class
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
                    )
            }
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDTO authDTO);
}
