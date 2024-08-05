package learn.capybara_mapper.controllers;

import learn.capybara_mapper.domain.PetTypeService;
import learn.capybara_mapper.domain.Result;
import learn.capybara_mapper.models.PetType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/petType")
public class PetTypeController {

    private final PetTypeService petTypeService;

    public PetTypeController(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @GetMapping
    public List<PetType> findAll() {
        return petTypeService.findAll();
    }

    @GetMapping("/{petTypeId}")
    public ResponseEntity<PetType> findById(@PathVariable int petTypeId) {
        Result<PetType> result = petTypeService.findById(petTypeId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result.getPayload());
    }
}
