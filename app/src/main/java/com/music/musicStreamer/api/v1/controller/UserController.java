package com.music.musicStreamer.api.v1.controller;

import com.music.musicStreamer.api.v1.assembler.UserLoginAssembler;
import com.music.musicStreamer.api.v1.assembler.UserRegisterAssembler;
import com.music.musicStreamer.api.v1.model.input.CreateUserInput;
import com.music.musicStreamer.api.v1.model.output.CreateUserOutput;
import com.music.musicStreamer.api.v1.openApi.UserControllerOpenApi;
import com.music.musicStreamer.api.v1.request.UserLoginRequest;
import com.music.musicStreamer.api.v1.response.UserLoginResponse;
import com.music.musicStreamer.usecase.user.CreateUserUseCase;
import com.music.musicStreamer.usecase.user.LoginUserUseCase;
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
    private final LoginUserUseCase loginUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    /*
    * - Assembler to convert the response from the use case to the response of the API
    */
    private final UserLoginAssembler userLoginAssembler;
    private final UserRegisterAssembler userRegisterAssembler;

    @Override
    @PostMapping
    public ResponseEntity<CreateUserOutput> create(@RequestBody @Valid CreateUserInput input) {
        info(this.getClass(),"Create user");
        info(this.getClass(),"User email => " + input.email());

        var response = userRegisterAssembler.toOutput(createUserUseCase.execute(input.toEntity()));

        return buildResponseEntity(HttpStatus.CREATED, response);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request) {
        info(this.getClass(),"Login user");
        info(this.getClass(),"User email:" + request.email());

        var response = userLoginAssembler.toOutput(loginUserUseCase.execute(request.toEntity()));

        return buildResponseEntity(HttpStatus.OK, response);
    }
}
