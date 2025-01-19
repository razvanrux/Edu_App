package professor;

import exceptions.ResourceConflictException;
import exceptions.ResourceNotFoundException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProfessors(
            @RequestParam(required = false) ProfessorEntity.GradDidactic acadRank,
            @RequestParam(required = false) String name
    ) {
        try {
            List<ProfessorEntity> professors;

            // Apply filtering logic based on query parameters
            if (acadRank != null) {
                professors = professorService.getProfessorsByRank(acadRank);
            } else if (name != null) {
                professors = professorService.getProfessorsByName(name);
            } else {
                professors = professorService.getAllProfessors();
            }

            if (professors.isEmpty()) {
                throw new ResourceNotFoundException(
                        acadRank != null
                                ? "No professors found with academic rank: " + acadRank
                                : name != null
                                ? "No professors found with name: " + name
                                : "No professors found."
                );
            }

            return ResponseEntity.ok(Map.of(
                    "data", professors.stream()
                            .map(professor -> Map.of(
                                    "data", professor,
                                    "links", List.of(
                                            Map.of(
                                                    "rel", "self",
                                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                                            .getProfessorById(professor.getId())).toUri().toString(),
                                                    "method", "GET"
                                            ),
                                            Map.of(
                                                    "rel", "delete-professor",
                                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                                            .deleteProfessor(professor.getId())).toUri().toString(),
                                                    "method", "DELETE"
                                            )
                                    )
                            ))
                            .collect(Collectors.toList()),
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                            .getProfessors(acadRank, name)).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", HttpStatus.NOT_FOUND.value(),
                    "message", ex.getMessage(),
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                            .getProfessors(null, null)).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "code", HttpStatus.BAD_REQUEST.value(),
                    "message", ex.getMessage()
            ));
        }
    }


    // Get professor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProfessorById(@PathVariable int id) {
        try {
            // Fetch the professor by ID
            ProfessorEntity professor = professorService.getProfessorById(id);

            return ResponseEntity.ok(Map.of(
                    "data", professor,
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                            .getProfessorById(id)).toUri().toString(),
                                    "method", "GET"
                            ),
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                            .getProfessors(null, null)).toUri().toString(),
                                    "method", "GET"
                            ),
                            Map.of(
                                    "rel", "delete-professor",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                            .deleteProfessor(id)).toUri().toString(),
                                    "method", "DELETE"
                            )
                    )
            ));
        }
        catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", HttpStatus.NOT_FOUND.value(),
                    "message", ex.getMessage(),
                    "links", List.of(
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                            .getProfessors(null, null)).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of(
                    "code", HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    "message", ex.getMessage()
            ));
        }
    }


    // Add a new professor
    @PostMapping
    public ResponseEntity<Map<String, Object>> addProfessor(@RequestBody ProfessorEntity professor) {
        try {
            if (professor.getTipAsociere() == null) {
                throw new IllegalArgumentException("tipAsociere is required.");
            }
            if (professor.getNume() == null) {
                throw new IllegalArgumentException("Nume is required.");
            }
            if (professor.getPrenume() == null) {
                throw new IllegalArgumentException("Prenume is required.");
            }
            if (professor.getEmail() == null) {
                throw new IllegalArgumentException("email is required.");
            }
            // Save the professor
            ProfessorEntity savedProfessor = professorService.addProfessor(professor);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "data", savedProfessor,
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                            .getProfessorById(savedProfessor.getId())).toUri().toString(),
                                    "method", "GET"
                            ),
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                            .getProfessors(null, null)).toUri().toString(),
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


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProfessor(@PathVariable int id) {
        try {
            // Attempt to delete the professor
            professorService.deleteProfessor(id);

            return ResponseEntity.ok(Map.of(
                    "message", "Professor deleted successfully",
                    "links", List.of(
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                            .getProfessors(null, null)).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        } catch (ResourceNotFoundException ex) {
            // Handle case where professor is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", HttpStatus.NOT_FOUND.value(),
                    "message", ex.getMessage(),
                    "links", List.of(
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProfessorController.class)
                                            .getProfessors(null, null)).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of(
                    "code", HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    "message", ex.getMessage()
            ));
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message", "An unexpected error occurred: " + ex.getMessage()
            ));
        }
    }

}

