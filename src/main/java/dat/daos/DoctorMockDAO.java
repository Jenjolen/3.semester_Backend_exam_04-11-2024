package dat.daos;

import dat.dtos.DoctorDTO;
import dat.enums.Specialty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorMockDAO implements iDAO<DoctorDTO, Integer> {

    public List <DoctorDTO> doctorList = new ArrayList<>(setDoctorList(new ArrayList<>()));

    public Integer idCounter = 1; // vi bruger denne til at finde det næste ledige id - godt hvis også havde en delete metode

    public List<DoctorDTO> setDoctorList(List<DoctorDTO> doctorList) {
        DoctorDTO doctor1 = new DoctorDTO(1, "Dr. Alice Smith", LocalDate.of(1975, 4, 12), 2000, "City Health Clinic", Specialty.FAMILY_MEDICINE);
        DoctorDTO doctor2 = new DoctorDTO(2, "Dr. Bob Johnson", LocalDate.of(1980, 8, 5), 2005, "Downtown Medical Center", Specialty.SURGERY);
        DoctorDTO doctor3 = new DoctorDTO(3, "Dr. Clara Lee", LocalDate.of(1983, 7, 22), 2008, "Green Valley Hospital", Specialty.PEDIATRICS);
        DoctorDTO doctor4 = new DoctorDTO(4, "Dr. David Park", LocalDate.of(1978, 11, 15), 2003, "Hillside Medical Practice", Specialty.PSYCHIATRY);
        DoctorDTO doctor5 = new DoctorDTO(5, "Dr. Emily White", LocalDate.of(1982, 9, 30), 2007, "Metro Health Center", Specialty.GERIATRICS);
        DoctorDTO doctor6 = new DoctorDTO(6, "Dr. Fiona Martinez", LocalDate.of(1985, 2, 17), 2010, "Riverside Wellness Clinic", Specialty.SURGERY);
        DoctorDTO doctor7 = new DoctorDTO(7, "Dr. George Kim", LocalDate.of(1979, 5, 29), 2004, "Summit Health Institute", Specialty.FAMILY_MEDICINE);
        doctorList.add(doctor1);
        doctorList.add(doctor2);
        doctorList.add(doctor3);
        doctorList.add(doctor4);
        doctorList.add(doctor5);
        doctorList.add(doctor6);
        doctorList.add(doctor7);
        return doctorList;
    }   // vi laver en liste med 7 læger


    @Override
    public List<DoctorDTO> readAll() {
        List <DoctorDTO> readDoctors = doctorList;
        return readDoctors;
    }

    @Override
    public DoctorDTO read(Integer id) {
       return doctorList.stream().filter(doctorDTO -> doctorDTO.id == id).findFirst().orElse(null);
       //        for (DoctorDTO doctor : doctorList) {
//            if (doctor.id == id) {
//                return doctor;
//            }
//        }
// return null;
    }

    public List<DoctorDTO> doctorBySpecialty(Specialty specialty) {

        return doctorList.stream().filter(doctorDTO -> doctorDTO.specialty.equals(specialty)).toList();


//        List<DoctorDTO> specialDoctors = new ArrayList<>();
//        for (DoctorDTO doctor : doctorList) {
//            if (doctor.specialty.equals(specialty)) {
//                specialDoctors.add(doctor);
//            }
//        }
//        return specialDoctors;
    }

    public List<DoctorDTO> doctorByBirthdateRange(LocalDate from, LocalDate to) {
       return doctorList.stream().filter(doctorDTO -> doctorDTO.dateOfBirth.isAfter(from) && doctorDTO.dateOfBirth.isBefore(to) || doctorDTO.dateOfBirth.equals(from) || doctorDTO.dateOfBirth.equals(to))
                .toList();


        //        List<DoctorDTO> sortedDoctors = new ArrayList<>();
//        for (DoctorDTO doctor : doctorList) {
//            if (doctor.dateOfBirth.isAfter(from) && doctor.dateOfBirth.isBefore(to) || doctor.dateOfBirth.equals(from) || doctor.dateOfBirth.equals(to)) {
//                sortedDoctors.add(doctor);
//            }
//        }
//        return sortedDoctors;

    }

    @Override
    public DoctorDTO create(DoctorDTO doctor) {
        int newId = doctorList.stream()
                .mapToInt(DoctorDTO::getId)
                .max()
                .orElse(0) + 1;
        doctor.setId(newId); // vi sørger for at der er lagt id på der passer med listen
        doctorList.add(doctor);
        return doctor;
    }



    @Override
    public DoctorDTO update(Integer id, DoctorDTO doctor) {

        for (DoctorDTO doctorDTO : doctorList) {
            if (doctorDTO.id == id) {
                doctorDTO.setName(doctor.getName());
                doctorDTO.setDateOfBirth(doctor.getDateOfBirth());
                doctorDTO.setYearOfGraduation(doctor.getYearOfGraduation());
                doctorDTO.setNameOfClinic(doctor.getNameOfClinic());
                doctorDTO.setSpecialty(doctor.getSpecialty());
                return doctorDTO;
            }
        }
        return null;
    }


}
