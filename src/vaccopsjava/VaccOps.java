package vaccopsjava;

import vaccopsjava.IVaccOps;

import javax.print.Doc;
import java.util.*;
import java.util.stream.Collectors;

public class VaccOps implements IVaccOps {
    Map<Doctor, List<Patient>> dataDoctor;
//    List<Patient> dataPatient;


    public VaccOps() {
        this.dataDoctor = new HashMap<>();
//        this.dataPatient = new ArrayList<>();
    }

    public void addDoctor(Doctor d) {
        if (exist(d)) {
            throw new IllegalArgumentException();
        }

        this.dataDoctor.put(d, new ArrayList<>());
    }

    public void addPatient(Doctor d, Patient p) {
        if (!exist(d)) {
            throw new IllegalArgumentException();
        }
        this.dataDoctor.get(d).add(p);
//        this.dataPatient.add(p);

    }

    public Collection<Doctor> getDoctors() {
        return new ArrayList<>(this.dataDoctor.keySet());
    }

    public Collection<Patient> getPatients() {
        List<Patient> returnAllPatient = new ArrayList<>();
        for (List<Patient> value : dataDoctor.values()) {
            returnAllPatient.addAll(value);
        }

//        return this.dataPatient;
        return returnAllPatient;
    }

    public boolean exist(Doctor d) {
        for (Doctor doctor : dataDoctor.keySet()) {
            if (doctor.name.equals(d.name)) {
                return true;
            }
        }
        return false;
    }

    public boolean exist(Patient p) {

        Collection<Patient> patients = this.getPatients();
        for (Patient patient : patients) {
            if (p.name.equals(patient.name)) {
                return true;
            }
        }
        return false;
//        return this.dataPatient.contains(p);
    }


    public Doctor removeDoctor(String name) {
        List<Patient> removePatient = new ArrayList<>();
        for (Doctor doctor : dataDoctor.keySet()) {
            if (doctor.name.equals(name)) {
                removePatient = this.dataDoctor.get(doctor);
                this.dataDoctor.remove(doctor);

                return doctor;
            }

//            for (Patient patient : removePatient) {
//                        this.dataPatient.remove(patient);
//            }


        }

        throw new IllegalArgumentException();
    }

    public void changeDoctor(Doctor from, Doctor to, Patient p) {
        if (exist(from) || exist(to) || exist(p)) {
            throw new IllegalArgumentException();
        }

        List<Patient> fromDoctor = this.dataDoctor.get(from);
        if (!fromDoctor.contains(p)) {
            throw new IllegalArgumentException();
        }
        fromDoctor.remove(p);

        this.dataDoctor.replace(from, fromDoctor);

        List<Patient> toDoctor = this.dataDoctor.get(to);

        toDoctor.add(p);
        this.dataDoctor.replace(to, toDoctor);


    }

    public Collection<Doctor> getDoctorsByPopularity(int populariry) {

        return this.getDoctors()
                .stream().
                        filter(e -> e.popularity == populariry).
                        collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsByTown(String town) {
        return this.getPatients()
                .stream()
                .filter(p -> p.town.equals(town))
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsInAgeRange(int lo, int hi) {

        return this.getPatients()
                .stream()
                .filter(r -> r.age >= lo && r.age <= hi)
                .collect(Collectors.toList());
    }

    public Collection<Doctor> getDoctorsSortedByPatientsCountDescAndNameAsc() {

        Map<Doctor, Integer> doctorsCount = new HashMap<>();

        for (Map.Entry<Doctor, List<Patient>> doctorListEntry : dataDoctor.entrySet()) {

            doctorsCount.putIfAbsent(doctorListEntry.getKey(), doctorListEntry.getValue().size());

        }

        LinkedHashMap<Doctor, Integer> sortedMap = new LinkedHashMap<>();


        doctorsCount.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));


        return new ArrayList<>(sortedMap.keySet());
    }

    public Collection<Patient> getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {
        return null;
    }

}
