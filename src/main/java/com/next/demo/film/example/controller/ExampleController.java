package com.next.demo.film.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/example/")
@Api("exampleController 相关 api")
public class ExampleController {

    @ApiOperation(value="测试SwaggerValue",notes="测试SwaggerNotes")
    @ApiImplicitParam(name = "test",value="入参test",paramType="query",required = true,dataType = "string")
    @RequestMapping(value = "test")
    public String test(String test){
        System.out.println("test="+test);
        return "test="+test;
    }

}
