package com.example.mysql;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "disciplina_de_studii")
public class MySQLCourse {
    @Id
    private String cod; // Maps to COD in MySQL
    private String courseName; // Maps to numeDisciplina
    private int studyYear; // Maps to anStudiu

    // Getters and Setters
    public String getCod() {
        return cod;
    }

    public void setCod(String id) {
        this.cod = cod;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }
}
