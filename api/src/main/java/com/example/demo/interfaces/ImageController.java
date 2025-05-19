package com.example.demo.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController()
@RequestMapping(value = "/media")
@Validated
public class ImageController {


    @PostMapping(value = "/image", consumes = "multipart/form-data")
    public Mono<ResponseEntity<Map<String, List<String>>>> uploadImage(@RequestPart("file") Flux<FilePart> files) {
        // Initialize a collection to store file URLs
        List<String> fileUrls = new ArrayList<>();

        return files.flatMap(file -> {
                    // Generate the filename with UUID for each file
                    String filenameWithUuid = UUID.randomUUID() + "_" + file.filename();

                    // Define the path where the file will be saved
                    Path path = Paths.get("/data/images/" + filenameWithUuid);

                    // Transfer each file to the defined path
                    return file.transferTo(path)
                            .then(Mono.defer(() -> {
                                // Construct the file URL for each file using the saved filename with UUID
                                String fileUrl = "/images/" + filenameWithUuid;
                                fileUrls.add(fileUrl);
                                return Mono.empty();
                            }));
                })
                .then(Mono.defer(() -> {
                    // Return all file URLs once the upload is complete
                    return Mono.just(ResponseEntity.ok(Map.of("urls", fileUrls)));
                }))
                .onErrorResume(e -> {
                    // Adjusting the error response to match the expected Map<String, List<String>> type
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Map.of("error", List.of(e.toString()))));
                });
    }

//
//    @GetMapping("/images/{filename}")
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//        Path path = Paths.get("/data/images/" + filename);
//        Resource resource = new FileSystemResource(path);
//
//        if (!resource.exists()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        String mimeType = URLConnection.guessContentTypeFromName(filename);
//        MediaType mediaType = mimeType != null ? MediaType.parseMediaType(mimeType) : MediaType.APPLICATION_OCTET_STREAM;
//
//        return ResponseEntity.ok()
//                .contentType(mediaType)
//                .body(resource);
//    }


}
