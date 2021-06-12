package kz.edu.astanait.picland.controller;

import kz.edu.astanait.picland.exception.EntityNotFoundException;
import kz.edu.astanait.picland.model.Pica;
import kz.edu.astanait.picland.model.User;
import kz.edu.astanait.picland.service.PicaService;
import kz.edu.astanait.picland.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/picas")
@AllArgsConstructor
public class PicaController {

    private final PicaService picaService;

    private final UserService userService;

    @GetMapping("/rec")
    public List<Pica> getUserRecs(Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        return picaService.getUserRecs(user);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Pica> getAllPicas() {
        return picaService.findAllPicas();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/d")
    public List<Pica> search(@RequestParam(value = "search") String search) {
        List<Pica> picas = picaService.findPicasByTitle(search);
        return picas;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public Pica getPica(@PathVariable("id") Long id) {
        return picaService.findPica(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/user/{username}")
    public List<Pica> getUserPicas(@PathVariable("username") String username) {
        return picaService.findUserPicas(username);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<?> createPica(@RequestBody Pica pica) {
        try {
            picaService.savePica(pica);
            return ResponseEntity.ok(pica);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<?> updatePica(@RequestBody Pica pica) {
        try {
            picaService.updatePica(pica);
            return ResponseEntity.ok(pica);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("#ownerId == principal.getUser().userId or hasRole('ADMIN')")
    @DeleteMapping("/remove/{id}/owner/{ownerId}")
    public ResponseEntity<?> removePica(@PathVariable("id") Long id,
                                        @PathVariable("ownerId") Long ownerId) {
        try {
            picaService.deletePica(id);
            return ResponseEntity.ok("Pica successfully removed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
