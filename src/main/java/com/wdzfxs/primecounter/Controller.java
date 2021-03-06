package com.wdzfxs.primecounter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    private final MainService mainService;

    public Controller(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping("/calculate")
    Map<Integer, Integer> calculate(@RequestBody List<Integer> integerList) {
        return mainService.calculate(integerList);
    }
}