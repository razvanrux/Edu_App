package utilizatori;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import exceptions.ResourceConflictException;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utilizatori")
public class UtilizatorController {

    @Autowired
    private UtilizatorService utilizatorService;

// Get all Users
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUtilizatori() {
        try {
            List<Utilizator> utilizatori = utilizatorService.getAllUtilizatori();

            if (utilizatori.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
                        "message", "No users found"
                ));
            }

            return ResponseEntity.ok(Map.of(
                    "data", utilizatori.stream()
                            .map(utilizator -> Map.of(
                                    "data", utilizator,
                                    "links", List.of(
                                            Map.of(
                                                    "rel", "self",
                                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                                            .getUtilizatorById(utilizator.getId())).toUri().toString(),
                                                    "method", "GET"
                                            ),
                                            Map.of(
                                                    "rel", "delete-utilizator",
                                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                                            .deleteUtilizator(utilizator.getId())).toUri().toString(),
                                                    "method", "DELETE"
                                            )
                                    )
                            ))
                            .collect(Collectors.toList()),
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                            .getAllUtilizatori()).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred while fetching users", ex);
        }
    }

// Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUtilizatorById(@PathVariable Long id) {
        try {
            Utilizator utilizator = utilizatorService.getUtilizatorById(id)
                    .orElseThrow(() -> new NoSuchElementException("Utilizator with ID " + id + " not found"));

            return ResponseEntity.ok(Map.of(
                    "data", utilizator,
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                            .getUtilizatorById(id)).toUri().toString(),
                                    "method", "GET"
                            ),
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                            .getAllUtilizatori()).toUri().toString(),
                                    "method", "GET"
                            ),
                            Map.of(
                                    "rel", "delete-utilizator",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                            .deleteUtilizator(id)).toUri().toString(),
                                    "method", "DELETE"
                            )
                    )
            ));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", HttpStatus.NOT_FOUND.value(),
                    "message", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message", "An unexpected error occurred: " + ex.getMessage()
            ));
        }
    }


    // Add a new utilizator
    @PostMapping
    public ResponseEntity<Map<String, Object>> addUtilizator(@RequestBody Utilizator utilizator) {
        try {
            // Validate input fields
            if (utilizator.getEmail() == null || utilizator.getEmail().isEmpty()) {
                throw new IllegalArgumentException("Email is required.");
            }
            if (utilizator.getParola() == null || utilizator.getParola().isEmpty()) {
                throw new IllegalArgumentException("Password is required.");
            }
            if (utilizatorService.existsByEmail(utilizator.getEmail())) {
                throw new ResourceConflictException("A user with the email " + utilizator.getEmail() + " already exists.");
            }

            // Save the utilizator
            Utilizator savedUtilizator = utilizatorService.addUtilizator(utilizator);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "data", savedUtilizator,
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                            .getUtilizatorById(savedUtilizator.getId())).toUri().toString(),
                                    "method", "GET"
                            ),
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                            .getAllUtilizatori()).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        } catch (ResourceConflictException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "code", HttpStatus.CONFLICT.value(),
                    "message", ex.getMessage()
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of(
                    "code", HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    "message", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message", "An unexpected error occurred: " + ex.getMessage()
            ));
        }
    }



    // Update a utilizator
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUtilizator(@PathVariable Long id, @RequestBody Utilizator utilizator) {
        try {
            // Validate the input
            if (utilizator.getEmail() == null || utilizator.getEmail().isEmpty()) {
                throw new IllegalArgumentException("Name is required.");
            }

            if (utilizator.getEmail() == null || utilizator.getEmail().isEmpty()) {
                throw new IllegalArgumentException("Email is required.");
            }

            // Check if the user exists
            if (!utilizatorService.existsByEmail(utilizator.getEmail())) {
                throw new NoSuchElementException("Utilizator with ID " + id + " not found.");
            }

            // Check for email conflicts (if changing the email)
            Utilizator existingUtilizator = utilizatorService.getUtilizatorById(id).orElseThrow();
            if (!existingUtilizator.getEmail().equals(utilizator.getEmail())
                    && utilizatorService.existsByEmail(utilizator.getEmail())) {
                throw new ResourceConflictException("A user with the email " + utilizator.getEmail() + " already exists.");
            }

            // Update the user
            Utilizator updatedUtilizator = utilizatorService.updateUtilizator(id, utilizator);

            return ResponseEntity.ok(Map.of(
                    "data", updatedUtilizator,
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                            .getUtilizatorById(id)).toUri().toString(),
                                    "method", "GET"
                            ),
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                            .getAllUtilizatori()).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", HttpStatus.NOT_FOUND.value(),
                    "message", ex.getMessage()
            ));
        } catch (ResourceConflictException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "code", HttpStatus.CONFLICT.value(),
                    "message", ex.getMessage()
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of(
                    "code", HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    "message", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message", "An unexpected error occurred: " + ex.getMessage()
            ));
        }
    }


    // Delete a utilizator by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUtilizator(@PathVariable Long id) {
        try {
            // Check if the user exists before attempting deletion
            if (!utilizatorService.existsById(id)) {
                throw new NoSuchElementException("Utilizator with ID " + id + " not found.");
            }

            // Perform the deletion
            utilizatorService.deleteUtilizator(id);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "message", "Utilizator deleted successfully",
                    "links", List.of(
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                            .getAllUtilizatori()).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", HttpStatus.NOT_FOUND.value(),
                    "message", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message", "An unexpected error occurred: " + ex.getMessage()
            ));
        }
    }


    // Get utilizatori by role
    @GetMapping("/role/{rol}")
    public ResponseEntity<Map<String, Object>> getUtilizatoriByRol(@PathVariable Utilizator.Rol rol) {
        try {
            // Retrieve utilizatori by role
            List<Utilizator> utilizatori = utilizatorService.getUtilizatoriByRol(rol);

            if (utilizatori.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
                        "message", "No users found with role " + rol
                ));
            }

            return ResponseEntity.ok(Map.of(
                    "data", utilizatori.stream()
                            .map(utilizator -> Map.of(
                                    "data", utilizator,
                                    "links", List.of(
                                            Map.of(
                                                    "rel", "self",
                                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                                            .getUtilizatorById(utilizator.getId())).toUri().toString(),
                                                    "method", "GET"
                                            ),
                                            Map.of(
                                                    "rel", "delete-utilizator",
                                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                                            .deleteUtilizator(utilizator.getId())).toUri().toString(),
                                                    "method", "DELETE"
                                            )
                                    )
                            ))
                            .collect(Collectors.toList()),
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                            .getUtilizatoriByRol(rol)).toUri().toString(),
                                    "method", "GET"
                            ),
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UtilizatorController.class)
                                            .getAllUtilizatori()).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message", "An unexpected error occurred: " + ex.getMessage()
            ));
        }
    }

}

