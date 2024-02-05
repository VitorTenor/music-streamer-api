package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.assembler.UserAssembler;
import com.music.musicStreamer.api.v1.assembler.UserLoginAssembler;
import com.music.musicStreamer.api.v1.model.input.CreateUserInput;
import com.music.musicStreamer.api.v1.model.input.LoginInput;
import com.music.musicStreamer.api.v1.model.output.UserLoginOutput;
import com.music.musicStreamer.api.v1.model.output.UserOutput;
import com.music.musicStreamer.api.v1.openApi.UserControllerOpenApi;
import com.music.musicStreamer.usecase.user.CreateUserUseCase;
import com.music.musicStreamer.usecase.user.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController extends AbstractController implements UserControllerOpenApi  {
    private final LoginUseCase loginUseCase;
    private final CreateUserUseCase createUserUseCase;
    /*
    * - Assembler to convert the response from the use case to the response of the API
    */
    private final UserLoginAssembler userLoginAssembler;
    private final UserAssembler userAssembler;

    @Override
    @PostMapping
    public ResponseEntity<UserOutput> create(@RequestBody @Valid CreateUserInput input) {
        info(this.getClass(),"Create user");
        info(this.getClass(),"User email => " + input.email());

        var response = userAssembler.toOutput(createUserUseCase.execute(input.toEntity()));

        return buildResponseEntity(HttpStatus.CREATED, response);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<UserLoginOutput> login(@RequestBody @Valid LoginInput input) {
        info(this.getClass(),"Login user");
        info(this.getClass(),"User email:" + input.email());

        var response = userLoginAssembler.toOutput(loginUseCase.execute(input.toEntity()));

        return buildResponseEntity(HttpStatus.OK, response);
    }
}
