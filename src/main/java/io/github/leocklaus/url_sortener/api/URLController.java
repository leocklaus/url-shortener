package io.github.leocklaus.url_sortener.api;

import io.github.leocklaus.url_sortener.api.dto.URLCountOutputDTO;
import io.github.leocklaus.url_sortener.api.dto.URLInputDTO;
import io.github.leocklaus.url_sortener.api.dto.URLOutputDTO;
import io.github.leocklaus.url_sortener.api.exceptionhandler.ExceptionModel;
import io.github.leocklaus.url_sortener.domain.service.StatsService;
import io.github.leocklaus.url_sortener.domain.service.URLService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class URLController implements IURLController {

    @Autowired
    private URLService urlService;
    @Autowired
    private StatsService statsService;

    @Override
    @PostMapping("/shorten-url")
    public ResponseEntity<URLOutputDTO> shortenURL(@RequestBody @Valid URLInputDTO dto){
        URLOutputDTO shortenedURL = urlService.shortenURL(dto);
        URI uri = URI.create("/" + shortenedURL.shortenedURL());
        return ResponseEntity.created(uri).body(shortenedURL);
    }

    @Override
    @GetMapping("/{url}")
    public ResponseEntity<Void> redirect(
            @Parameter(description = "Shortened ID", required = true) @PathVariable String url){
        String originalUrl = urlService.getOriginalURLByShortenedURLOrThrowsExceptionIfNotExists(url);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(originalUrl));

        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).build();
    }

    @Override
    @GetMapping("/stats/{url}/")
    public ResponseEntity<URLCountOutputDTO> getViews(
            @Parameter(description = "Shortened ID", required = true) @PathVariable String url){

        URLCountOutputDTO totalViewsByURL = statsService.getTotalViewsByURL(url);

        return ResponseEntity.ok(totalViewsByURL);
    }


}
