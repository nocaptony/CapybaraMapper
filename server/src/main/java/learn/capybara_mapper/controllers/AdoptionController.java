package learn.capybara_mapper.controllers;


import learn.capybara_mapper.domain.AdoptionService;
import learn.capybara_mapper.domain.Result;
import learn.capybara_mapper.models.Adoption;
import learn.capybara_mapper.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/adoption")
public class AdoptionController {

    private final AdoptionService adoptionService;

    public AdoptionController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @GetMapping
    public List<Adoption> findAll() {
        return adoptionService.findAll();
    }

    @GetMapping("/{adoptionId}")
    public ResponseEntity<Adoption> findById(@PathVariable int adoptionId) {
        Result<Adoption> result = adoptionService.findById(adoptionId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result.getPayload());
    }

    @GetMapping("/findByUser")
    public List<Adoption> findByUser(@RequestBody User user) {
        return adoptionService.findByUser(user);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Adoption adoption) {
        Result<Adoption> result = adoptionService.add(adoption);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{adoptionId}")
    public ResponseEntity<Object> update(@PathVariable int adoptionId, @RequestBody Adoption adoption) {
        if (adoptionId != adoption.getAdoptionId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Adoption> result = adoptionService.update(adoption);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{adoptionId}")
    public ResponseEntity<Void> deleteById(@PathVariable int adoptionId) {
        if (adoptionService.deleteById(adoptionId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
