package com.example.wineshop;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class Recommend {
    private final WineRepository wineRepository;
    private final WineModelAssembler assembler;

    Recommend(WineRepository wineRepository, WineModelAssembler assembler) {
        this.wineRepository = wineRepository;
        this.assembler = assembler;
    }

    @GetMapping("/recommend/best")
    CollectionModel<EntityModel<Wine>> bestWines(@RequestParam(value = "top", defaultValue = "10") int top) {
        top = sanitizeParam(top);
        List<EntityModel<Wine>> wines = wineRepository.findAllByBestRating(top).stream()
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(wines, linkTo(methodOn(WineController.class).all()).withSelfRel());
    }

    @GetMapping("/recommend/expensive")
    CollectionModel<EntityModel<Wine>> expensiveWines(@RequestParam(value = "top", defaultValue = "10") int top) {
        top = sanitizeParam(top);
        List<EntityModel<Wine>> wines = wineRepository.findAllByBestPrice(top).stream()
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(wines, linkTo(methodOn(WineController.class).all()).withSelfRel());
    }

    @GetMapping("/recommend/bang")
    CollectionModel<EntityModel<Wine>> bangForTheBuckWines(@RequestParam(value = "top", defaultValue = "10") int top) {
        top = sanitizeParam(top);
        List<EntityModel<Wine>> wines = wineRepository.findAllByRatingPriceRatio(top).stream()
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(wines, linkTo(methodOn(WineController.class).all()).withSelfRel());
    }

    @GetMapping("/recommend/vintage")
    CollectionModel<EntityModel<Wine>> vintageWines(@RequestParam(value = "top", defaultValue = "10") int top) {
        top = sanitizeParam(top);
        List<EntityModel<Wine>> wines = wineRepository.findAllByVintage(top).stream()
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(wines, linkTo(methodOn(WineController.class).all()).withSelfRel());
    }

    /**
     * This sanitizes the input of the top parameter, so we have no negatives, nor we request too many.
     * @param top Parameter to sanitize
     * @return It'll return top if it's between 1 and 500, or 10 if it's not within the range
     */
    private int sanitizeParam(int top) {
        if (top < 1 || top > 500)
            top = 10;
        return top;
    }
}
