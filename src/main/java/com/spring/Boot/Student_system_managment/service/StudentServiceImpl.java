package com.spring.Boot.Student_system_managment.service;

import com.spring.Boot.Student_system_managment.Entity.Address;
import com.spring.Boot.Student_system_managment.Entity.Course;
import com.spring.Boot.Student_system_managment.Entity.Department;
import com.spring.Boot.Student_system_managment.Entity.Student;
import com.spring.Boot.Student_system_managment.Projection.StudentProjection;
import com.spring.Boot.Student_system_managment.dto.*;
import com.spring.Boot.Student_system_managment.exception.DuplicateEmailException;
import com.spring.Boot.Student_system_managment.exception.ResourcesNotFoundException;
import com.spring.Boot.Student_system_managment.exception.StudentNotFoundException;
import com.spring.Boot.Student_system_managment.repository.CourseRepository;
import com.spring.Boot.Student_system_managment.repository.DepartmentRepository;
import com.spring.Boot.Student_system_managment.repository.StudentRepository;
import com.spring.Boot.Student_system_managment.specification.StudentSpecification;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;





    @Transactional
    @Override
    public StudentResponseDto saveStudent(StudentRequestDto dto) {
        if(studentRepository.existsByEmail(dto.getEmail())){
            throw new DuplicateEmailException("Student already exists with email "+dto.getEmail());
        }

        List<Course> courses = courseRepository.findAllById(dto.getCourseIds());


        //Department
        Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(()->
                new ResourcesNotFoundException("Department is not found with id: "+dto.getDepartmentId()));

//        Address entity
        Address address = new Address();
        address.setCity(dto.getAddress().getCity());
        address.setState(dto.getAddress().getState());
        address.setCountry(dto.getAddress().getCountry());

        // Student entity
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
//        student.setCreatedAt(LocalDateTime.now());

        student.setDepartment(department);
        student.setAddress(address);
        student.setCourses(courses);
       Student saveStudent = studentRepository.save(student);
//       try {
//           sendConfirmationEmail();} catch (Exception e) {
//           log.error("failed to send confirmation e ",e);
//       }
        return mapToResponseDto(saveStudent);
    }
    private void sendConfirmationEmail(){
        throw new RuntimeException("Something went wrong!");
    }

    @Override
    public List<StudentResponseDto> getAllStudent() {
        log.info("=========== fetching all students ============");
        List<StudentResponseDto> student = studentRepository.findAllWithDetails()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
        log.info("successfully Fetched {} student",student.size());
        return student;
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {
        log.debug("Fetching student with id: {}",id);
        Student student = studentRepository.findById(id).orElseThrow(()-> {
            log.warn("Student not found with id: {}",id);
             return   new StudentNotFoundException("Student not found with id: "+id);
        });
        log.info("Student successfully fetched with id: {}",id);
        return mapToResponseDto(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(()->
                new StudentNotFoundException("Student not found with id: "+id));
        student.setDeleted(true);
       studentRepository.save(student);
    }

    @Override
    public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(()->
                new StudentNotFoundException("Student not found with id: "+id));
        if(!existingStudent.getVersion().equals(dto.getVersion())){
            throw new OptimisticLockException("Students was already updated by another user");
        }

        if (studentRepository.existsByEmail(dto.getEmail()) && !existingStudent.getEmail().equals(dto.getEmail())) {
            throw new DuplicateEmailException("Student already exists with email: "+dto.getEmail());
        }
            existingStudent.setName(dto.getName());
            existingStudent.setEmail(dto.getEmail());
            existingStudent.setPassword(dto.getPassword());
            Address address = existingStudent.getAddress();
            if(address == null){
                address = new Address();
            }
              address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setCountry(dto.getAddress().getCountry());
               existingStudent.setAddress(address);

               Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(()->
                       new ResourcesNotFoundException("Department not found with id: "+dto.getDepartmentId()));
               existingStudent.setDepartment(department);

            Student updateStudent = studentRepository.save(existingStudent);
            return mapToResponseDto(updateStudent);

    }

    @Override
    public StudentResponseDto patchStudent(Long id, StudentRequestDto dto) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(()->
                new StudentNotFoundException("Student not found with id: "+id));

            if (dto.getName() != null) {
                existingStudent.setName(dto.getName());
            }

            if (dto.getEmail() != null) {
                if (studentRepository.existsByEmail(dto.getEmail()) && !existingStudent.getEmail().equals(dto.getEmail())) {
                    throw new DuplicateEmailException("Student already exists with email: "+dto.getEmail());
                }
                existingStudent.setEmail(dto.getEmail());
            }
            if(dto.getPassword() != null){
                existingStudent.setPassword(dto.getPassword());
            }

            if(dto.getAddress() != null){
                Address address= existingStudent.getAddress();

              if(address == null){
                  address=new Address();
              }
              if(dto.getAddress().getCity() != null){
                  address.setCity(dto.getAddress().getCity());
              }
                if(dto.getAddress().getState() != null){
                    address.setState(dto.getAddress().getState());
                }
                if(dto.getAddress().getCountry() != null){
                    address.setCountry(dto.getAddress().getCountry());
                }
                existingStudent.setAddress(address);

            }
            if(dto.getDepartmentId()!=null){
                Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(()->
                        new ResourcesNotFoundException("Department not found with id: "+id));
                existingStudent.setDepartment(department);
            }

            Student patchStudent = studentRepository.save(existingStudent);
            return mapToResponseDto(patchStudent);

    }

    @Override
    public StudentResponseDto getStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow(()->
                new ResourcesNotFoundException("Student not found with email: "+email));
        return mapToResponseDto(student);
    }

    @Override
    public Long getStudentCountByCourse(String courseName) {
        return studentRepository.countStudentByCourse(courseName);
    }

    @Transactional
    @Override
    public void deleteStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow(()->
                new StudentNotFoundException("student not found with email : "+email));
        student.setDeleted(true);
        studentRepository.save(student);
    }

    @Override
    public List<StudentResponseDto> getStudentByNameAndCourse(String name, String course) {
        List<Student> students=studentRepository.findByNameAndCourse(name,course);
        return students.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public StudentResponseDto getStudentByEmailNative(String email) {
        Student student= studentRepository.findStudentByEmailNative(email).orElseThrow(()->
                new StudentNotFoundException("Student not found with email: "+email));
        return mapToResponseDto(student);
    }

    @Override
    public StudentResponseDto getStudentByIdNative(Long id) {
        Student student = studentRepository.findStudentByIdNative(id).orElseThrow(()->
                new StudentNotFoundException("Student not found with id: "+id));
        return mapToResponseDto(student);
    }

    @Override
    public List<StudentResponseDto> getAllStudentsSortedByName() {
        Sort sort = Sort.by("name").ascending().and(Sort.by("email").descending());
        List<Student> student=studentRepository.findAll(sort);
        return student.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public Page<StudentResponseDto> getStudent(Pageable pageable) {
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(this::mapToResponseDto);
    }

    @Override
    public List<StudentProjectionDto> getStudentProjection() {
        return studentRepository.getStudentProjection();
    }

//    @Override
//    public List<StudentResponseDto> searchStudents(String name, String email, String course) {
//        Specification<Student> specification=Specification.allOf(
//                StudentSpecification.hasName(name),
//                StudentSpecification.hasEmail(email),
//                StudentSpecification.hasCourse(course));
//        List<Student> students=studentRepository.findAll(specification);
//        return students.stream().map(this::mapToResponseDto).toList();
//    }

    @Override
    public List<StudentResponseDto> searchStudents(
            String name,
            String email,
            String course) {

        List<Specification<Student>> specifications = new ArrayList<>();

        Specification<Student> nameSpec =
                StudentSpecification.hasName(name);

        Specification<Student> emailSpec =
                StudentSpecification.hasEmail(email);

        Specification<Student> courseSpec =
                StudentSpecification.hasCourse(course);

        if (nameSpec != null) {
            specifications.add(nameSpec);
        }

        if (emailSpec != null) {
            specifications.add(emailSpec);
        }

        if (courseSpec != null) {
            specifications.add(courseSpec);
        }

        Specification<Student> specification =
                Specification.allOf(specifications);

        List<Student> students =
                studentRepository.findAll(specification);

        return students.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<StudentResponseDto> getAllStudentCustom() {
        List<Student> students = studentRepository.findStudentCustom();
        return students.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<StudentResponseDto> getStudentByDepartmentCustom(String departmentName) {
        List<Student> students=studentRepository.findStudentByDepartment(departmentName);
        return students.stream().map(this::mapToResponseDto).toList();
    }


    private StudentResponseDto mapToResponseDto(Student student){
        StudentResponseDto dto = new StudentResponseDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());
        dto.setCreatedBy(student.getCreatedBy());
        dto.setLastModifiedBy(student.getLastModifiedBy());
            AddressResponseDTO addressDto = new AddressResponseDTO();
            addressDto.setCity(student.getAddress().getCity());
            addressDto.setState(student.getAddress().getState());
            addressDto.setCountry(student.getAddress().getCountry());
            dto.setAddress(addressDto);
        dto.setDepartmentName(student.getDepartment().getDepartmentName());
        dto.setCourses(
                student.getCourses()
                        .stream()
                        .map(course -> new
                                CourseResponseDto(
                                course.getId(),
                                course.getCourseName(),
                                course.getDuration(),
                                course.getFees(),
                                course.getInstructorName()
                        )).toList()
        );
            dto.setVersion(student.getVersion());
        return dto;
    }


}
