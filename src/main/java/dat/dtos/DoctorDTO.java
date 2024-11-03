package dat.dtos;

import dat.entities.Doctor;
import dat.enums.Specialty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDTO {
    public int id;
    public String name;
    public LocalDate dateOfBirth;
    public int yearOfGraduation;
    public String nameOfClinic;
    public Specialty specialty;


    public DoctorDTO(int id, String name, LocalDate dateOfBirth, int yearOfGraduation, String nameOfClinic, Specialty specialty) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.yearOfGraduation = yearOfGraduation;
        this.nameOfClinic = nameOfClinic;
        this.specialty = specialty;
    }

    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.dateOfBirth = doctor.getDateOfBirth();
        this.yearOfGraduation = doctor.getYearOfGraduation();
        this.nameOfClinic = doctor.getNameOfClinic();
        this.specialty = doctor.getSpecialty();
    }

    public DoctorDTO(String name, LocalDate dateOfBirth, int yearOfGraduation, String nameOfClinic, Specialty specialty) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.yearOfGraduation = yearOfGraduation;
        this.nameOfClinic = nameOfClinic;
        this.specialty = specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorDTO doctorDTO)) return false;
        return getYearOfGraduation() == doctorDTO.getYearOfGraduation() && Objects.equals(getName(), doctorDTO.getName()) && Objects.equals(getDateOfBirth(), doctorDTO.getDateOfBirth()) && Objects.equals(getNameOfClinic(), doctorDTO.getNameOfClinic()) && getSpecialty() == doctorDTO.getSpecialty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDateOfBirth(), getYearOfGraduation(), getNameOfClinic(), getSpecialty());
    }
}
