package com.noxus.controllers.docs;

import com.noxus.data.dto.security.AccountCredentialsDTO;
import com.noxus.data.dto.security.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface AuthControllerDocs {

    @Operation(
        summary = "Authenticate user",
        description = "Authenticates a user using username and password, returning a JWT access token.",
        tags = {"Authentication"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TokenDTO.class)
                )
            ),
            @ApiResponse(description = "Bad Request - Invalid input data", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized - Invalid credentials", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<?> signin(
        @Parameter(description = "User credentials", required = true)
        AccountCredentialsDTO credentials
    );

    @Operation(
        summary = "Refresh access token",
        description = "Generates a new access token using a valid refresh token and username.",
        tags = {"Authentication"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TokenDTO.class)
                )
            ),
            @ApiResponse(description = "Bad Request - Missing parameters", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized - Invalid or expired token", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found - User not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<?> refreshToken(
        @Parameter(description = "Username of the authenticated user", required = true)
        String username,

        @Parameter(description = "Refresh token (Authorization header)", required = true)
        String refreshToken
    );

    @Operation(
        summary = "Create a new user",
        description = "Registers a new user in the system. The password will be securely encrypted before being stored.",
        tags = {"User Management"},
        responses = {
            @ApiResponse(
                description = "User created successfully",
                responseCode = "200",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AccountCredentialsDTO.class)
                )
            ),
            @ApiResponse(description = "Bad Request - Invalid input data", responseCode = "400", content = @Content),
            @ApiResponse(description = "Conflict - User already exists", responseCode = "409", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    AccountCredentialsDTO create(
        @Parameter(description = "User data to be created", required = true)
        AccountCredentialsDTO credentials
    );
}