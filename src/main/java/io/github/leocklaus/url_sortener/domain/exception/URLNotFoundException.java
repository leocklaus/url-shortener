package io.github.leocklaus.url_sortener.domain.exception;

public class URLNotFoundException extends URLException{
    public URLNotFoundException(String URL) {
        super("URL not found: " + URL);
    }

}
