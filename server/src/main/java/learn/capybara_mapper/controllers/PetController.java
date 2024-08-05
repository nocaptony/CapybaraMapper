package learn.capybara_mapper.controllers;

import learn.capybara_mapper.domain.PetService;
import learn.capybara_mapper.domain.Result;
import learn.capybara_mapper.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> findAll() {
        return petService.findAll();
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> findById(@PathVariable int petId) {
        Result<Pet> result = petService.findById(petId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result.getPayload());
    }

    @GetMapping("/findByLocation")
    public List<Pet> findByLocation(@RequestBody Location location) {
        return petService.findByLocation(location);
    }

    @GetMapping("/findByType")
    public List<Pet> findByType(@RequestBody PetType type) {
        return petService.findByType(type);
    }
}
