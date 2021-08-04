package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.ProductLineService;
import org.motoc.gamelibrary.dto.ProductLineDto;
import org.motoc.gamelibrary.dto.ProductLineNameDto;
import org.motoc.gamelibrary.mapper.ProductLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Defines product line endpoints
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProductLineController {

    private static final Logger logger = LoggerFactory.getLogger(ProductLineController.class);

    private final ProductLineService service;

    private final ProductLineMapper mapper;

    @Autowired
    public ProductLineController(ProductLineService service) {
        this.service = service;
        this.mapper = ProductLineMapper.INSTANCE;
    }

    @GetMapping("/admin/product-lines/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/product-lines/names")
    List<ProductLineNameDto> findNames() {
        logger.trace("findNames called");
        return service.findNames();
    }

    @GetMapping("/admin/product-lines/{id}")
    ProductLineDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return mapper.productLineToDto(service.findById(id));
    }

    @GetMapping("/admin/product-lines")
    List<ProductLineDto> findAll() {
        logger.trace("findAll() called");
        return mapper.productLinesToDto(service.findAll());
    }

    @GetMapping("/admin/product-lines/page")
    Page<ProductLineDto> findPage(Pageable pageable,
                                  @RequestParam(name = "search", required = false) String keyword) {
        logger.trace("findPage(pageable) called");
        if (keyword == null) {
            return mapper.pageToPageDto(service.findPage(pageable));
        } else {
            logger.trace("findPage(" + keyword + ", pageable) called");
            return mapper.pageToPageDto(service.quickSearch(keyword, pageable));
        }
    }

    @PostMapping("/admin/product-lines")
    ProductLineDto save(@RequestBody @Valid ProductLineDto productLineDto) {
        logger.trace("save(productLine) called");
        return mapper.productLineToDto(service.save(mapper.dtoToProductLine(productLineDto)));
    }

    @PutMapping("/admin/product-lines/{id}")
    ProductLineDto edit(@RequestBody @Valid ProductLineDto productLineDto,
                        @PathVariable Long id) {
        logger.trace("edit(productLine, id) called");
        return mapper.productLineToDto(service.edit(mapper.dtoToProductLine(productLineDto), id));
    }

    @DeleteMapping("/admin/product-lines/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }

}