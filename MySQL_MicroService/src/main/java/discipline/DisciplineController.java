package discipline;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping
    public Map<String, Object> getDisciplines(
            @RequestParam(required = false) DisciplineEntity.TipDisciplina type,
            @RequestParam(required = false) DisciplineEntity.CategorieDisciplina category
    ) {
        List<DisciplineEntity> disciplines;

        // Apply filtering logic
        if (type != null && category != null) {
            disciplines = disciplineService.getDisciplinesByTypeAndCategory(type, category);
        } else if (type != null) {
            disciplines = disciplineService.getDisciplinesByType(type);
        } else if (category != null) {
            disciplines = disciplineService.getDisciplinesByCategorie(category);
        } else {
            disciplines = disciplineService.getAllDisciplines();
        }

        return Map.of(
                "data", disciplines.stream()
                        .map(discipline -> Map.of(
                                "data", discipline,
                                "links", List.of(
                                        Map.of(
                                                "rel", "self",
                                                "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DisciplineController.class)
                                                        .getDisciplineByCod(discipline.getCod())).toUri().toString(),
                                                "method", "GET"
                                        ),
                                        Map.of(
                                                "rel", "delete-discipline",
                                                "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DisciplineController.class)
                                                        .deleteDiscipline(discipline.getCod())).toUri().toString(),
                                                "method", "DELETE"
                                        )
                                )
                        ))
                        .collect(Collectors.toList()),
                "links", List.of(
                        Map.of(
                                "rel", "self",
                                "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DisciplineController.class)
                                        .getDisciplines(type, category)).toUri().toString(),
                                "method", "GET"
                        )
                )
        );
    }



    @GetMapping("/{cod}")
    public ResponseEntity<Map<String, Object>> getDisciplineByCod(@PathVariable String cod) {
        try {
            DisciplineEntity discipline = disciplineService.getDisciplineByCod(cod);
            List<Map<String, String>> links = List.of(
                    Map.of(
                            "rel", "self",
                            "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DisciplineController.class)
                                    .getDisciplineByCod(cod)).toUri().toString(),
                            "method", "GET"
                    ),
                    Map.of(
                            "rel", "all-disciplines",
                            "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DisciplineController.class)
                                    .getDisciplines(null, null)).toUri().toString(),
                            "method", "GET"
                    ),
                    Map.of(
                            "rel", "delete-discipline",
                            "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DisciplineController.class)
                                    .deleteDiscipline(cod)).toUri().toString(),
                            "method", "DELETE"
                    ),
                    Map.of(
                            "rel", "parent",
                            "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DisciplineController.class)
                                    .getDisciplines(null, null)).toUri().toString(),
                            "method", "GET"
                    )
            );
            return ResponseEntity.ok(Map.of(
                    "data", discipline,
                    "links", links
            ));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> addDiscipline(@RequestBody DisciplineEntity discipline) {
        DisciplineEntity savedDiscipline = disciplineService.addDiscipline(discipline);
        List<Map<String, String>> links = List.of(
                Map.of(
                        "rel", "self",
                        "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DisciplineController.class)
                                .getDisciplineByCod(savedDiscipline.getCod())).toUri().toString(),
                        "method", "GET"
                ),
                Map.of(
                        "rel", "all-disciplines",
                        "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DisciplineController.class)
                                .getDisciplines(null, null)).toUri().toString(),
                        "method", "GET"
                ),
                Map.of(
                        "rel", "delete-discipline",
                        "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DisciplineController.class)
                                .deleteDiscipline(savedDiscipline.getCod())).toUri().toString(),
                        "method", "DELETE"
                )
        );
        return ResponseEntity.ok(Map.of(
                "data", savedDiscipline,
                "links", links
        ));
    }


    @DeleteMapping("/{cod}")
    public ResponseEntity<Map<String, Object>> deleteDiscipline(@PathVariable String cod) {
        disciplineService.deleteDiscipline(cod);
        Map<String, String> parentLink = Map.of(
                "rel", "parent",
                "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DisciplineController.class)
                        .getDisciplines(null, null)).toUri().toString(),
                "method", "GET"
        );
        return ResponseEntity.ok(Map.of(
                "message", "Discipline deleted successfully",
                "links", List.of(parentLink)
        ));
    }

}
