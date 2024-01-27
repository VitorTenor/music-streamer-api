package com.music.musicStreamer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class MusicStreamerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicStreamerApplication.class, args);
	}


}
