package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.assembler.ImageAssembler;
import com.music.musicStreamer.api.v1.model.input.ImageInput;
import com.music.musicStreamer.api.v1.model.output.ImageOutput;
import com.music.musicStreamer.usecase.image.GetImageUseCase;
import com.music.musicStreamer.usecase.image.UploadImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/images")
public class ImageController extends AbstractController {
    /*
     * Use case
     */
    private final GetImageUseCase getImageUseCase;
    private final UploadImageUseCase uploadImageUseCase;
    /*
     * Assembler
     */
    private final ImageAssembler imageAssembler;

    @PostMapping
    public ResponseEntity<ImageOutput> upload(@ModelAttribute @Valid ImageInput input) {
        info(this.getClass(), "Upload image");

        final var response = imageAssembler.toOutput(
                uploadImageUseCase.execute(input.toEntity())
        );
        info(this.getClass(), "Image uploaded");

        return buildResponseEntity(HttpStatus.CREATED, response);
    }

    @GetMapping("/{pathName}")
    public ResponseEntity<byte[]> getImage(@PathVariable(value = "pathName") String input) {
        info(this.getClass(), "Get image");

        final var response = getImageUseCase.execute(input);
        info(this.getClass(), "Image retrieved");

        return buildResponseEntity(HttpStatus.OK, MediaType.IMAGE_JPEG, response);
    }
}
