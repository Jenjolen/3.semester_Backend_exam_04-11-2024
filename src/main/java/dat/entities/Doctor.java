package dat.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dat.dtos.DoctorDTO;
import dat.enums.Specialty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    public int id;

    @Column(name = "name", nullable = false)
    public String name;
    @Column(name = "date_of_birth", nullable = false)
    public LocalDate dateOfBirth;
    @Column(name = "year_of_graduation", nullable = false)
    public int yearOfGraduation;
    @Column(name = "name_of_clinic", nullable = false)
    public String nameOfClinic;
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "specialty", nullable = false)
    public Specialty specialty;


    @Column(name = "created_at", nullable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "doctor")
    public List<Appointment> appointments;


    public Doctor(int id, String name, LocalDate dateOfBirth, int yearOfGraduation, String nameOfClinic, Specialty specialty) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.yearOfGraduation = yearOfGraduation;
        this.nameOfClinic = nameOfClinic;
        this.specialty = specialty;
    }

    public Doctor(DoctorDTO doctorDTO) {
        this.id = doctorDTO.id;
        this.name = doctorDTO.name;
        this.dateOfBirth = doctorDTO.dateOfBirth;
        this.yearOfGraduation = doctorDTO.yearOfGraduation;
        this.nameOfClinic = doctorDTO.nameOfClinic;
        this.specialty = doctorDTO.specialty;
    }

    public Doctor (String name, LocalDate dateOfBirth, int yearOfGraduation, String nameOfClinic, Specialty specialty) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.yearOfGraduation = yearOfGraduation;
        this.nameOfClinic = nameOfClinic;
        this.specialty = specialty;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor doctor)) return false;
        return getYearOfGraduation() == doctor.getYearOfGraduation() && Objects.equals(getName(), doctor.getName()) && Objects.equals(getDateOfBirth(), doctor.getDateOfBirth()) && Objects.equals(getNameOfClinic(), doctor.getNameOfClinic()) && getSpecialty() == doctor.getSpecialty() && Objects.equals(getCreatedAt(), doctor.getCreatedAt()) && Objects.equals(getUpdatedAt(), doctor.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDateOfBirth(), getYearOfGraduation(), getNameOfClinic(), getSpecialty(), getCreatedAt(), getUpdatedAt());
    }
}
