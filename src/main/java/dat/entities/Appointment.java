package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dat.enums.Specialty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    public int id;

    @Column(name = "client_name", nullable = false)
    public String clientName;

    @Column(name="date", nullable = false)
    public LocalDate date;

    @Column(name="time", nullable = false)
    public LocalTime time;

    @Column(name = "comment", nullable = false)
    public String comment;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonBackReference
    public Doctor doctor;

    public Appointment(String clientName, LocalDate date, LocalTime time, String comment, Doctor doctor) {
        this.clientName = clientName;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment that)) return false;
        return Objects.equals(getClientName(), that.getClientName()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getTime(), that.getTime()) && Objects.equals(getComment(), that.getComment()) && Objects.equals(getDoctor(), that.getDoctor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientName(), getDate(), getTime(), getComment(), getDoctor());
    }
}
