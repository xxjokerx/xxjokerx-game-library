package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.GameCopyService;
import org.motoc.gamelibrary.dto.GameCopyDto;
import org.motoc.gamelibrary.mapper.GameCopyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class GameCopyController {

    private static final Logger logger = LoggerFactory.getLogger(GameCopyController.class);


    private final GameCopyService service;

    private final GameCopyMapper mapper;

    @Autowired
    public GameCopyController(GameCopyService service) {
        this.service = service;
        this.mapper = GameCopyMapper.INSTANCE;
    }

    @GetMapping("/admin/game-copies/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/game-copies/{id}")
    GameCopyDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return mapper.copyToDto(service.findById(id));
    }

    @GetMapping("/admin/game-copies/object-code/{objectCode}")
    GameCopyDto findByObjectCode(@PathVariable @Pattern(regexp = "^[0-9]{1,5}$") String objectCode) {
        logger.trace("findByObjectCode(objectCode) called");
        return mapper.copyToDto(service.findByObjectCode(objectCode));
    }

    @PostMapping("/admin/game-copies")
    GameCopyDto save(@RequestBody @Valid GameCopyDto copyDto) {
        logger.trace("save(gameCopy) called");
        return mapper.copyToDto(service.save(mapper.dtoToCopy(copyDto)));
    }

    @PutMapping("/admin/game-copies/{id}")
    GameCopyDto edit(@RequestBody @Valid GameCopyDto copyDto,
                     @PathVariable Long id) {
        logger.trace("edit(gameCopy) called");
        return mapper.copyToDto(service.edit(mapper.dtoToCopy(copyDto), id));
    }

    /* Maybe replace this endpoint by a endpoint in GAME */
    @GetMapping("/admin/game-copies")
    List<GameCopyDto> findAll(@RequestParam(value = "loan-ready", required = false, defaultValue = "false") boolean loanReady) {
        logger.trace("findAll() called");
        if (!loanReady)
            return mapper.copiesToDto(service.findAll());
        else
            return mapper.copiesToDto(service.findLoanReady());
    }

    /* Maybe replace this endpoint by a endpoint in GAME */
    @GetMapping("/admin/game-copies/page")
    Page<GameCopyDto> findAll(@RequestParam(value = "loan-ready", required = false, defaultValue = "false") boolean loanReady,
                              Pageable pageable) {
        logger.trace("findAll() called");
        if (!loanReady)
            return mapper.pageToPageDto(service.findPage(pageable));
        else
            return mapper.pageToPageDto(service.findLoanReadyPage(pageable));
    }

    @PostMapping("/admin/game-copies/{copyId}/add-seller/{sellerId}")
    GameCopyDto addSeller(@PathVariable Long copyId,
                          @PathVariable Long sellerId) {
        logger.trace("addSeller() called");
        return mapper.copyToDto(service.addSeller(copyId, sellerId));
    }

    @DeleteMapping("/admin/game-copies/{copyId}/unlink-seller/{sellerId}")
    GameCopyDto unlinkSeller(@PathVariable Long copyId,
                             @PathVariable Long sellerId) {
        logger.trace("unlinkSeller() called");
        return mapper.copyToDto(service.removeSeller(copyId, sellerId));
    }

    @PostMapping("/admin/game-copies/{copyId}/add-publisher/{publisherId}")
    GameCopyDto addPublisher(@PathVariable Long copyId,
                             @PathVariable Long publisherId) {
        logger.trace("addPublisher() called");
        return mapper.copyToDto(service.addPublisher(copyId, publisherId));
    }

    @DeleteMapping("/admin/game-copies/{copyId}/unlink-publisher/{publisherId}")
    GameCopyDto unlinkPublisher(@PathVariable Long copyId,
                                @PathVariable Long publisherId) {
        logger.trace("unlinkPublisher() called");
        return mapper.copyToDto(service.removePublisher(copyId, publisherId));
    }


}