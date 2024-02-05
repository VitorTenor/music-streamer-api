package com.music.musicStreamer.api.v1.assembler;

import com.music.musicStreamer.api.v1.model.output.ImageOutput;
import com.music.musicStreamer.entity.image.ImageEntity;
import org.springframework.stereotype.Component;

@Component
public class ImageAssembler implements Assembler<ImageEntity, ImageOutput>{
    @Override
    public ImageOutput toOutput(ImageEntity entity) {
        return ImageOutput.builder()
                .id(entity.id())
                .pathName(entity.pathName())
                .url(entity.url())
                .build();
    }
}
