package ma.enset.jpaap;

import ma.enset.jpaap.entities.Patient;
import ma.enset.jpaap.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaApApplication implements CommandLineRunner {
    @Autowired
private PatientRepository patientrepository;
    public static void main(String[] args) {
        SpringApplication.run(JpaApApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0;i<1000;i++){
            patientrepository.save(
            new Patient(null,"omar",new Date(),Math.random()>0.5?true:false,(int)(Math.random()*100)));
        }
        patientrepository.save(
                new Patient(null,"hassan",new Date(),false,56));
        patientrepository.save(
                new Patient(null,"imane",new Date(),true,100));
        patientrepository.save(
                new Patient(null,"salma",new Date(),Math.random()>0.5?true:false,210));
        Page<Patient> patients =patientrepository.findAll(PageRequest.of(0,5));
        System.out.println("Total pages : "+patients.getTotalPages());
        System.out.println("Total elements : "+patients.getTotalElements());
        System.out.println("NUm Page : "+patients.getNumber());
        List<Patient> content=patients.getContent();
        Page<Patient> byMalade=patientrepository.findByMalade(true,PageRequest.of(0,4));
        List<Patient>patientList=patientrepository.chercherPatients("%o%",40);
        patientList.forEach(p->{
            System.out.println("====================");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });
        System.out.println("*******************");
        Patient patient=patientrepository.findById(1L).orElse(null);
        if (patient!=null){
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());
        }
        patient.setScore(870);
        patientrepository.save(patient);
        patientrepository.deleteById(1L);
    }
}
