package kz.edu.astanait.picland.controller;

import kz.edu.astanait.picland.dto.InterestDto;
import kz.edu.astanait.picland.model.Interest;
import kz.edu.astanait.picland.service.InterestService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interests")
@AllArgsConstructor
public class InterestController {

    private final InterestService interestService;

    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public List<Interest> getAllInterests(){
        return interestService.findAllInterests();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public Interest getInterest(@PathVariable("id") Long id){
        return interestService.findInterest(id);
    }

    @PreAuthorize("hasRole('ADMIN') or #username == principal.getUsername()")
    @GetMapping("/user/{username}")
    public List<Interest> getUserInterests(@PathVariable("username") String username){
        return interestService.findUserInterests(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createInterest(@RequestBody InterestDto interestDto) {
        try{
            Interest interest = modelMapper.map(interestDto, Interest.class);
            interestService.saveInterest(interest);
            return ResponseEntity.ok("Interest was successfully created");
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("")
    public ResponseEntity<?> updateInterest(@RequestBody InterestDto interestDto){
        try{
            Interest interest = modelMapper.map(interestDto, Interest.class);
            return ResponseEntity.ok(interestService.updateInterest(interest));
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeInterest(@PathVariable("id") Long id){
        try{
            interestService.deleteInterest(id);
            return ResponseEntity.ok("Interest was successfully removed");
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
