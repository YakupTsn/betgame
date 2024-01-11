package com.spottoto.bet.betround.controllers;

import com.spottoto.bet.betround.concretes.BetRoundDto;
import com.spottoto.bet.betround.concretes.requests.concretes.BetRoundRequest;
import com.spottoto.bet.betround.enums.PlayableStatus;
import com.spottoto.bet.betround.enums.Score;
import com.spottoto.bet.betround.services.BetRoundService;
import com.spottoto.bet.exceptions.ErrorResponse;
import com.spottoto.bet.security.annotations.IsAuthentificated;
import com.spottoto.bet.security.annotations.OnlyAdmin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/betround")
@RequiredArgsConstructor
public class BetRoundController {
    private final BetRoundService betRoundService;

    @IsAuthentificated
    @Operation(
            summary = "Adds a betting round. ",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BetRoundDto.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveBetRound(@RequestBody BetRoundRequest betRoundRequest) {
        betRoundService.saveBetRound(betRoundRequest);
    }

    @OnlyAdmin
    @Operation(
            summary = "Adds game result to do bet round. ",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BetRoundDto.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Entity Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void saveGameResult(@RequestParam Score score,
                               @RequestParam Long serverBetRoundId,
                               @RequestParam String gameId) {
        betRoundService.saveGameResult(serverBetRoundId, gameId, score);
    }

    @OnlyAdmin
    @Operation(
            summary = "Finalizes user bet rounds. ",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BetRoundDto.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Entity Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @PatchMapping("/finalizes")
    @ResponseStatus(HttpStatus.OK)
    public void saveBetRoundIsSuccessAndSendMail(@RequestParam Long betRoundId) {
        betRoundService.saveBetRoundIsSuccessAndSendMail(betRoundId);
    }

    @IsAuthentificated
    @Operation(
            summary = "Get all bet rounds. ",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BetRoundDto.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Entity Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BetRoundDto> getAllBetRounds(@Nullable @RequestParam PlayableStatus playableStatus, Pageable pageable) {
        return betRoundService.getAllBetRounds(playableStatus, pageable);
    }

}
