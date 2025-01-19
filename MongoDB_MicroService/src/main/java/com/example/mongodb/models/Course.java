package com.example.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "courses")
public class Course {
    @Id
    private String id; // MongoDB automatically generates a unique ID
    private String courseName;
    private String professorId;
    private List<EvaluationProbe> evaluationProbes= new ArrayList<>();;
    private List<Material> materials;

    public double getTotalWeight() {
        return evaluationProbes.stream().mapToDouble(EvaluationProbe::getWeight).sum();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public List<EvaluationProbe> getEvaluationProbes() {
        return evaluationProbes;
    }

    public void setEvaluationProbes(List<EvaluationProbe> evaluationProbes) {
        this.evaluationProbes = evaluationProbes;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    // Supporting classes
    public static class EvaluationProbe {
        private String type;
        private double weight;
        // Getters and Setters
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public double getWeight() {
            return weight;
        }
        public void setWeight(double weight) {
            this.weight = weight;
        }
    }

    public static class Material {
        private String name;
        private String type; // e.g., "PDF", "Chapter"
        private String content; // Base64 encoded or a file path
        // Getters and Setters
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
    }
}
